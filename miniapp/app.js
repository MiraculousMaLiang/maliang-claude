/**
 * 小程序入口文件
 * 功能：小程序启动时的初始化逻辑,管理全局数据
 */
App({
  /**
   * 全局数据
   */
  globalData: {
    userInfo: null,      // 用户信息
    token: null,         // 登录token
    baseUrl: 'http://localhost:8080/api'  // 后端接口地址
  },

  /**
   * 小程序启动时执行
   * 功能：初始化应用,检查登录状态
   */
  onLaunch() {
    console.log('小程序启动');
    // 获取本地存储的token
    const token = wx.getStorageSync('token');
    if (token) {
      this.globalData.token = token;
    }
    // 获取本地存储的用户信息
    const userInfo = wx.getStorageSync('userInfo');
    if (userInfo) {
      this.globalData.userInfo = userInfo;
    }
  },

  /**
   * 小程序显示时执行
   * 功能：小程序从后台进入前台时触发
   */
  onShow() {
    console.log('小程序显示');
  },

  /**
   * 小程序隐藏时执行
   * 功能：小程序从前台进入后台时触发
   */
  onHide() {
    console.log('小程序隐藏');
  },

  /**
   * 设置用户信息
   * 功能：保存用户信息到全局数据和本地存储
   */
  setUserInfo(userInfo) {
    this.globalData.userInfo = userInfo;
    wx.setStorageSync('userInfo', userInfo);
  },

  /**
   * 设置token
   * 功能：保存token到全局数据和本地存储
   */
  setToken(token) {
    this.globalData.token = token;
    wx.setStorageSync('token', token);
  },

  /**
   * 清除用户信息
   * 功能：退出登录时清除用户信息和token
   */
  clearUserInfo() {
    this.globalData.userInfo = null;
    this.globalData.token = null;
    wx.removeStorageSync('userInfo');
    wx.removeStorageSync('token');
  }
})
