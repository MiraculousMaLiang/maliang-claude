/**
 * 体征监测页面
 * 功能：记录和查看体征数据
 */

const api = require('../../api/index');
const util = require('../../utils/util');

Page({
  data: {
    activeTab: 0,
    latestRecord: null,
    recentRecords: [],
    showAddPopup: false,
    showDatePickerPopup: false,
    formData: {
      recordDate: '',
      systolicPressure: '',
      diastolicPressure: '',
      weight: '',
      temperature: '',
      heartRate: '',
      bloodSugar: '',
      notes: ''
    },
    currentDate: new Date().getTime(),
    minDate: new Date(2020, 0, 1).getTime(),
    maxDate: new Date().getTime()
  },

  onLoad() {
    this.loadLatestRecord();
    this.loadRecentRecords();
  },

  onShow() {
    this.loadLatestRecord();
    this.loadRecentRecords();
  },

  onTabChange(event) {
    this.setData({ activeTab: event.detail.index });
  },

  loadLatestRecord() {
    api.health.getLatestVitalSigns().then(res => {
      this.setData({ latestRecord: res });
    }).catch(err => {
      console.error('获取最新记录失败', err);
    });
  },

  loadRecentRecords() {
    api.health.getRecentVitalSigns(7).then(res => {
      this.setData({ recentRecords: res || [] });
    }).catch(err => {
      console.error('获取历史记录失败', err);
    });
  },

  showAddPopup() {
    const today = util.formatDate(new Date());
    this.setData({
      showAddPopup: true,
      formData: {
        recordDate: today,
        systolicPressure: '',
        diastolicPressure: '',
        weight: '',
        temperature: '',
        heartRate: '',
        bloodSugar: '',
        notes: ''
      }
    });
  },

  closeAddPopup() {
    this.setData({ showAddPopup: false });
  },

  showDatePicker() {
    this.setData({ showDatePickerPopup: true });
  },

  closeDatePicker() {
    this.setData({ showDatePickerPopup: false });
  },

  onDateConfirm(event) {
    const date = new Date(event.detail);
    const dateStr = util.formatDate(date);
    this.setData({
      'formData.recordDate': dateStr,
      showDatePickerPopup: false
    });
  },

  onSystolicChange(event) {
    this.setData({ 'formData.systolicPressure': event.detail });
  },

  onDiastolicChange(event) {
    this.setData({ 'formData.diastolicPressure': event.detail });
  },

  onWeightChange(event) {
    this.setData({ 'formData.weight': event.detail });
  },

  onTemperatureChange(event) {
    this.setData({ 'formData.temperature': event.detail });
  },

  onHeartRateChange(event) {
    this.setData({ 'formData.heartRate': event.detail });
  },

  onBloodSugarChange(event) {
    this.setData({ 'formData.bloodSugar': event.detail });
  },

  onNotesChange(event) {
    this.setData({ 'formData.notes': event.detail });
  },

  saveVitalSigns() {
    const { formData } = this.data;

    if (!formData.recordDate) {
      wx.showToast({ title: '请选择记录日期', icon: 'none' });
      return;
    }

    wx.showLoading({ title: '保存中...' });

    const data = {
      ...formData,
      systolicPressure: formData.systolicPressure ? parseInt(formData.systolicPressure) : null,
      diastolicPressure: formData.diastolicPressure ? parseInt(formData.diastolicPressure) : null,
      weight: formData.weight ? parseFloat(formData.weight) : null,
      temperature: formData.temperature ? parseFloat(formData.temperature) : null,
      heartRate: formData.heartRate ? parseInt(formData.heartRate) : null,
      bloodSugar: formData.bloodSugar ? parseFloat(formData.bloodSugar) : null
    };

    api.health.addVitalSigns(data).then(() => {
      wx.hideLoading();
      wx.showToast({ title: '保存成功', icon: 'success' });
      this.closeAddPopup();
      this.loadLatestRecord();
      this.loadRecentRecords();
    }).catch(err => {
      wx.hideLoading();
      wx.showToast({ title: err.message || '保存失败', icon: 'none' });
    });
  }
});
