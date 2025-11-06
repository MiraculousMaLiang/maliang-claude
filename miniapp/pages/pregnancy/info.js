/**
 * 孕期信息页面
 * 功能：显示和管理孕期信息
 */

const api = require('../../api/index');
const Dialog = require('@vant/weapp/dialog/dialog');

Page({
  /**
   * 页面数据
   */
  data: {
    pregnancyInfo: null,
    showEditPopup: false,
    editMode: 'add', // 'add' or 'edit'
    formData: {
      id: null,
      lastPeriodDate: '',
      pregnancyType: null,
      riskLevel: null,
      notes: ''
    },
    pregnancyTypes: ['单胎', '双胎', '多胎'],
    riskLevels: ['低危', '高危'],
    showDatePickerPopup: false,
    showPregnancyTypePickerPopup: false,
    showRiskLevelPickerPopup: false,
    currentDate: new Date().getTime(),
    minDate: new Date(2020, 0, 1).getTime(),
    maxDate: new Date().getTime()
  },

  /**
   * 页面加载
   * 功能：页面加载时获取孕期信息
   */
  onLoad() {
    this.loadPregnancyInfo();
  },

  /**
   * 页面显示
   * 功能：每次页面显示时刷新数据
   */
  onShow() {
    this.loadPregnancyInfo();
  },

  /**
   * 加载孕期信息
   * 功能：从服务器获取当前用户的孕期信息
   */
  loadPregnancyInfo() {
    wx.showLoading({ title: '加载中...' });

    api.pregnancy.getPregnancyInfo().then(res => {
      wx.hideLoading();
      this.setData({
        pregnancyInfo: res
      });
    }).catch(err => {
      wx.hideLoading();
      console.error('获取孕期信息失败', err);
    });
  },

  /**
   * 添加孕期信息
   * 功能：打开添加孕期信息的弹窗
   */
  addPregnancyInfo() {
    this.setData({
      editMode: 'add',
      showEditPopup: true,
      formData: {
        id: null,
        lastPeriodDate: '',
        pregnancyType: null,
        riskLevel: null,
        notes: ''
      }
    });
  },

  /**
   * 编辑孕期信息
   * 功能：打开编辑孕期信息的弹窗
   */
  editPregnancyInfo() {
    const { pregnancyInfo } = this.data;
    this.setData({
      editMode: 'edit',
      showEditPopup: true,
      formData: {
        id: pregnancyInfo.id,
        lastPeriodDate: pregnancyInfo.lastPeriodDate,
        pregnancyType: pregnancyInfo.pregnancyType,
        riskLevel: pregnancyInfo.riskLevel,
        notes: pregnancyInfo.notes || ''
      }
    });
  },

  /**
   * 关闭编辑弹窗
   * 功能：关闭编辑弹窗
   */
  closeEditPopup() {
    this.setData({
      showEditPopup: false
    });
  },

  /**
   * 显示日期选择器
   * 功能：显示末次月经日期选择器
   */
  showDatePicker() {
    const { formData } = this.data;
    let currentDate = new Date().getTime();

    if (formData.lastPeriodDate) {
      currentDate = new Date(formData.lastPeriodDate).getTime();
    }

    this.setData({
      showDatePickerPopup: true,
      currentDate
    });
  },

  /**
   * 关闭日期选择器
   * 功能：关闭日期选择器
   */
  closeDatePicker() {
    this.setData({
      showDatePickerPopup: false
    });
  },

  /**
   * 确认日期选择
   * 功能：保存选择的末次月经日期
   */
  onDateConfirm(event) {
    const timestamp = event.detail;
    const date = new Date(timestamp);
    const dateStr = this.formatDate(date);

    this.setData({
      'formData.lastPeriodDate': dateStr,
      showDatePickerPopup: false
    });
  },

  /**
   * 格式化日期
   * 功能：将日期对象转换为 yyyy-MM-dd 格式
   */
  formatDate(date) {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  },

  /**
   * 显示妊娠类型选择器
   * 功能：显示妊娠类型选择器
   */
  showPregnancyTypePicker() {
    this.setData({
      showPregnancyTypePickerPopup: true
    });
  },

  /**
   * 关闭妊娠类型选择器
   * 功能：关闭妊娠类型选择器
   */
  closePregnancyTypePicker() {
    this.setData({
      showPregnancyTypePickerPopup: false
    });
  },

  /**
   * 确认妊娠类型选择
   * 功能：保存选择的妊娠类型
   */
  onPregnancyTypeConfirm(event) {
    const { value, index } = event.detail;
    this.setData({
      'formData.pregnancyType': index + 1,
      showPregnancyTypePickerPopup: false
    });
  },

  /**
   * 显示风险等级选择器
   * 功能：显示风险等级选择器
   */
  showRiskLevelPicker() {
    this.setData({
      showRiskLevelPickerPopup: true
    });
  },

  /**
   * 关闭风险等级选择器
   * 功能：关闭风险等级选择器
   */
  closeRiskLevelPicker() {
    this.setData({
      showRiskLevelPickerPopup: false
    });
  },

  /**
   * 确认风险等级选择
   * 功能：保存选择的风险等级
   */
  onRiskLevelConfirm(event) {
    const { value, index } = event.detail;
    this.setData({
      'formData.riskLevel': index + 1,
      showRiskLevelPickerPopup: false
    });
  },

  /**
   * 备注输入
   * 功能：处理备注输入事件
   */
  onNotesChange(event) {
    this.setData({
      'formData.notes': event.detail
    });
  },

  /**
   * 保存孕期信息
   * 功能：保存或更新孕期信息
   */
  savePregnancyInfo() {
    const { formData } = this.data;

    // 验证必填字段
    if (!formData.lastPeriodDate) {
      wx.showToast({
        title: '请选择末次月经日期',
        icon: 'none'
      });
      return;
    }

    if (!formData.pregnancyType) {
      wx.showToast({
        title: '请选择妊娠类型',
        icon: 'none'
      });
      return;
    }

    if (!formData.riskLevel) {
      wx.showToast({
        title: '请选择风险等级',
        icon: 'none'
      });
      return;
    }

    wx.showLoading({ title: '保存中...' });

    api.pregnancy.savePregnancyInfo(formData).then(() => {
      wx.hideLoading();
      wx.showToast({
        title: '保存成功',
        icon: 'success'
      });
      this.closeEditPopup();
      this.loadPregnancyInfo();
    }).catch(err => {
      wx.hideLoading();
      wx.showToast({
        title: err.message || '保存失败',
        icon: 'none'
      });
    });
  }
});
