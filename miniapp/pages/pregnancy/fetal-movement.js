/**
 * 胎动记录页面
 * 功能：记录和查看胎动情况
 */

const api = require('../../api/index');
const util = require('../../utils/util');

Page({
  /**
   * 页面数据
   */
  data: {
    activeTab: 0,
    selectedDate: '',
    todayRecords: [],
    recentRecords: [],
    statistics: null,
    showAddPopup: false,
    showCalendarPopup: false,
    showDatePickerPopup: false,
    showTimePickerPopup: false,
    showTimePeriodPickerPopup: false,
    showStrengthPickerPopup: false,
    formData: {
      recordDate: '',
      recordTime: '',
      timePeriod: null,
      movementCount: '',
      duration: '',
      strength: null,
      notes: ''
    },
    timePeriods: ['早上', '中午', '晚上'],
    strengths: ['轻微', '适中', '强烈'],
    currentDate: new Date().getTime(),
    currentTime: '12:00',
    minDate: new Date(2020, 0, 1).getTime(),
    maxDate: new Date().getTime()
  },

  /**
   * 页面加载
   * 功能：初始化页面数据
   */
  onLoad() {
    const today = util.formatDate(new Date());
    this.setData({
      selectedDate: today
    });
    this.loadTodayRecords();
    this.loadStatistics();
  },

  /**
   * 页面显示
   * 功能：刷新页面数据
   */
  onShow() {
    this.loadTodayRecords();
    this.loadRecentRecords();
    this.loadStatistics();
  },

  /**
   * Tab切换
   * 功能：切换显示今日记录或最近7天记录
   */
  onTabChange(event) {
    const index = event.detail.index;
    this.setData({
      activeTab: index
    });

    if (index === 1 && this.data.recentRecords.length === 0) {
      this.loadRecentRecords();
    }
  },

  /**
   * 加载今日记录
   * 功能：获取今日的胎动记录
   */
  loadTodayRecords() {
    const { selectedDate } = this.data;
    wx.showLoading({ title: '加载中...' });

    api.pregnancy.getFetalMovementsByDate(selectedDate).then(res => {
      wx.hideLoading();
      this.setData({
        todayRecords: res || []
      });
    }).catch(err => {
      wx.hideLoading();
      console.error('获取今日记录失败', err);
    });
  },

  /**
   * 加载最近7天记录
   * 功能：获取最近7天的胎动记录
   */
  loadRecentRecords() {
    wx.showLoading({ title: '加载中...' });

    api.pregnancy.getRecentFetalMovements().then(res => {
      wx.hideLoading();
      this.setData({
        recentRecords: res || []
      });
    }).catch(err => {
      wx.hideLoading();
      console.error('获取最近记录失败', err);
    });
  },

  /**
   * 加载统计数据
   * 功能：获取选中日期的胎动统计
   */
  loadStatistics() {
    const { selectedDate } = this.data;

    api.pregnancy.getFetalMovementStatistics(selectedDate).then(res => {
      this.setData({
        statistics: res
      });
    }).catch(err => {
      console.error('获取统计数据失败', err);
    });
  },

  /**
   * 显示日历
   * 功能：显示日历选择器选择日期
   */
  showCalendar() {
    this.setData({
      showCalendarPopup: true
    });
  },

  /**
   * 关闭日历
   * 功能：关闭日历选择器
   */
  closeCalendar() {
    this.setData({
      showCalendarPopup: false
    });
  },

  /**
   * 日历日期确认
   * 功能：选择日期后加载该日期的记录
   */
  onCalendarConfirm(event) {
    const date = new Date(event.detail);
    const dateStr = util.formatDate(date);

    this.setData({
      selectedDate: dateStr,
      showCalendarPopup: false
    });

    this.loadTodayRecords();
    this.loadStatistics();
  },

  /**
   * 显示添加弹窗
   * 功能：打开添加胎动记录弹窗
   */
  showAddPopup() {
    const now = new Date();
    const today = util.formatDate(now);
    const currentTime = util.formatTimeOnly(now);

    this.setData({
      showAddPopup: true,
      formData: {
        recordDate: today,
        recordTime: currentTime,
        timePeriod: this.getTimePeriod(),
        movementCount: '',
        duration: '',
        strength: null,
        notes: ''
      }
    });
  },

  /**
   * 关闭添加弹窗
   * 功能：关闭添加弹窗
   */
  closeAddPopup() {
    this.setData({
      showAddPopup: false
    });
  },

  /**
   * 获取当前时间段
   * 功能：根据当前时间自动判断时间段
   */
  getTimePeriod() {
    const hour = new Date().getHours();
    if (hour < 12) {
      return 1; // 早上
    } else if (hour < 18) {
      return 2; // 中午
    } else {
      return 3; // 晚上
    }
  },

  /**
   * 显示日期选择器
   * 功能：显示记录日期选择器
   */
  showDatePicker() {
    const { formData } = this.data;
    let currentDate = new Date().getTime();

    if (formData.recordDate) {
      currentDate = new Date(formData.recordDate).getTime();
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
   * 功能：保存选择的记录日期
   */
  onDateConfirm(event) {
    const timestamp = event.detail;
    const date = new Date(timestamp);
    const dateStr = util.formatDate(date);

    this.setData({
      'formData.recordDate': dateStr,
      showDatePickerPopup: false
    });
  },

  /**
   * 显示时间选择器
   * 功能：显示记录时间选择器
   */
  showTimePicker() {
    const { formData } = this.data;
    let currentTime = '12:00';

    if (formData.recordTime) {
      currentTime = formData.recordTime;
    }

    this.setData({
      showTimePickerPopup: true,
      currentTime
    });
  },

  /**
   * 关闭时间选择器
   * 功能：关闭时间选择器
   */
  closeTimePicker() {
    this.setData({
      showTimePickerPopup: false
    });
  },

  /**
   * 确认时间选择
   * 功能：保存选择的记录时间
   */
  onTimeConfirm(event) {
    const time = event.detail;

    this.setData({
      'formData.recordTime': time,
      showTimePickerPopup: false
    });
  },

  /**
   * 显示时间段选择器
   * 功能：显示时间段选择器
   */
  showTimePeriodPicker() {
    this.setData({
      showTimePeriodPickerPopup: true
    });
  },

  /**
   * 关闭时间段选择器
   * 功能：关闭时间段选择器
   */
  closeTimePeriodPicker() {
    this.setData({
      showTimePeriodPickerPopup: false
    });
  },

  /**
   * 确认时间段选择
   * 功能：保存选择的时间段
   */
  onTimePeriodConfirm(event) {
    const { index } = event.detail;
    this.setData({
      'formData.timePeriod': index + 1,
      showTimePeriodPickerPopup: false
    });
  },

  /**
   * 显示强度选择器
   * 功能：显示胎动强度选择器
   */
  showStrengthPicker() {
    this.setData({
      showStrengthPickerPopup: true
    });
  },

  /**
   * 关闭强度选择器
   * 功能：关闭强度选择器
   */
  closeStrengthPicker() {
    this.setData({
      showStrengthPickerPopup: false
    });
  },

  /**
   * 确认强度选择
   * 功能：保存选择的胎动强度
   */
  onStrengthConfirm(event) {
    const { index } = event.detail;
    this.setData({
      'formData.strength': index + 1,
      showStrengthPickerPopup: false
    });
  },

  /**
   * 胎动次数输入
   * 功能：处理胎动次数输入
   */
  onMovementCountChange(event) {
    this.setData({
      'formData.movementCount': event.detail
    });
  },

  /**
   * 持续时长输入
   * 功能：处理持续时长输入
   */
  onDurationChange(event) {
    this.setData({
      'formData.duration': event.detail
    });
  },

  /**
   * 备注输入
   * 功能：处理备注输入
   */
  onNotesChange(event) {
    this.setData({
      'formData.notes': event.detail
    });
  },

  /**
   * 保存胎动记录
   * 功能：保存或更新胎动记录
   */
  saveFetalMovement() {
    const { formData } = this.data;

    // 验证必填字段
    if (!formData.recordDate) {
      wx.showToast({
        title: '请选择记录日期',
        icon: 'none'
      });
      return;
    }

    if (!formData.recordTime) {
      wx.showToast({
        title: '请选择记录时间',
        icon: 'none'
      });
      return;
    }

    if (!formData.timePeriod) {
      wx.showToast({
        title: '请选择时间段',
        icon: 'none'
      });
      return;
    }

    if (!formData.movementCount) {
      wx.showToast({
        title: '请输入胎动次数',
        icon: 'none'
      });
      return;
    }

    if (!formData.duration) {
      wx.showToast({
        title: '请输入持续时长',
        icon: 'none'
      });
      return;
    }

    if (!formData.strength) {
      wx.showToast({
        title: '请选择胎动强度',
        icon: 'none'
      });
      return;
    }

    wx.showLoading({ title: '保存中...' });

    // 转换数据类型
    const data = {
      ...formData,
      movementCount: parseInt(formData.movementCount),
      duration: parseInt(formData.duration)
    };

    api.pregnancy.addFetalMovement(data).then(() => {
      wx.hideLoading();
      wx.showToast({
        title: '保存成功',
        icon: 'success'
      });
      this.closeAddPopup();
      this.loadTodayRecords();
      this.loadRecentRecords();
      this.loadStatistics();
    }).catch(err => {
      wx.hideLoading();
      wx.showToast({
        title: err.message || '保存失败',
        icon: 'none'
      });
    });
  },

  /**
   * 删除记录
   * 功能：删除指定的胎动记录
   */
  deleteRecord(event) {
    const { id } = event.currentTarget.dataset;

    wx.showModal({
      title: '提示',
      content: '确定要删除这条胎动记录吗？',
      success: (res) => {
        if (res.confirm) {
          wx.showLoading({ title: '删除中...' });

          api.pregnancy.deleteFetalMovement(id).then(() => {
            wx.hideLoading();
            wx.showToast({
              title: '删除成功',
              icon: 'success'
            });
            this.loadTodayRecords();
            this.loadRecentRecords();
            this.loadStatistics();
          }).catch(err => {
            wx.hideLoading();
            wx.showToast({
              title: err.message || '删除失败',
              icon: 'none'
            });
          });
        }
      }
    });
  }
});
