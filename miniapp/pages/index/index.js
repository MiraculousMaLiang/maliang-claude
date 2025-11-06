/**
 * 首页
 * 功能：展示孕产妇健康管理的主要功能入口和概况信息
 */

const app = getApp();
const api = require('../../api/index');

Page({
  /**
   * 页面数据
   */
  data: {
    userInfo: {},
    pregnancyInfo: {
      currentWeek: 0,
      dueDate: '未设置',
      daysLeft: 0
    },
    reminders: []
  },

  /**
   * 页面加载
   * 功能：初始化页面数据,加载用户信息和孕期信息
   */
  onLoad() {
    this.checkLogin();
  },

  /**
   * 页面显示
   * 功能：每次显示页面时刷新数据
   */
  onShow() {
    if (app.globalData.token) {
      this.loadUserInfo();
      this.loadPregnancyInfo();
      this.loadReminders();
    }
  },

  /**
   * 检查登录状态
   * 功能：检查用户是否已登录,未登录则跳转到登录页
   */
  checkLogin() {
    const token = app.globalData.token || wx.getStorageSync('token');
    if (!token) {
      wx.reLaunch({
        url: '/pages/login/login'
      });
    } else {
      this.loadUserInfo();
      this.loadPregnancyInfo();
      this.loadReminders();
    }
  },

  /**
   * 加载用户信息
   * 功能：从服务器获取当前登录用户的信息
   */
  loadUserInfo() {
    api.user.getUserInfo().then(userInfo => {
      this.setData({ userInfo });
      app.setUserInfo(userInfo);
    }).catch(err => {
      console.error('获取用户信息失败：', err);
    });
  },

  /**
   * 加载孕期信息
   * 功能：获取孕期相关信息（孕周、预产期等）
   */
  loadPregnancyInfo() {
    // TODO: 调用孕期信息接口
    // 这里使用模拟数据
    const pregnancyInfo = {
      currentWeek: 20,
      dueDate: '2024-06-15',
      daysLeft: 140
    };
    this.setData({ pregnancyInfo });
  },

  /**
   * 加载今日提醒
   * 功能：获取今天的提醒事项
   */
  loadReminders() {
    // TODO: 调用提醒接口
    // 这里使用模拟数据
    const reminders = [
      { text: '产检：唐氏筛查', time: '10:00' },
      { text: '记录体重和血压', time: '14:00' }
    ];
    this.setData({ reminders });
  },

  /**
   * 页面跳转
   * 功能：跳转到指定页面
   */
  navigateTo(e) {
    const url = e.currentTarget.dataset.url;
    wx.showToast({
      title: '该功能正在开发中',
      icon: 'none'
    });
    // wx.navigateTo({ url });
  },

  /**
   * 下拉刷新
   * 功能：刷新页面数据
   */
  onPullDownRefresh() {
    this.loadUserInfo();
    this.loadPregnancyInfo();
    this.loadReminders();
    setTimeout(() => {
      wx.stopPullDownRefresh();
    }, 1000);
  }
})
