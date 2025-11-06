/**
 * 我的页面
 * 功能：展示用户信息和系统设置入口
 */

const app = getApp();
const api = require('../../api/index');

Page({
  /**
   * 页面数据
   */
  data: {
    userInfo: {}
  },

  /**
   * 页面显示
   * 功能：每次显示页面时加载用户信息
   */
  onShow() {
    this.loadUserInfo();
  },

  /**
   * 加载用户信息
   * 功能：从服务器获取当前登录用户的信息
   */
  loadUserInfo() {
    const userInfo = app.globalData.userInfo || wx.getStorageSync('userInfo');
    if (userInfo) {
      this.setData({ userInfo });
    }

    // 从服务器获取最新用户信息
    api.user.getUserInfo().then(userInfo => {
      this.setData({ userInfo });
      app.setUserInfo(userInfo);
    }).catch(err => {
      console.error('获取用户信息失败：', err);
    });
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
   * 退出登录
   * 功能：退出当前登录状态并跳转到登录页
   */
  logout() {
    wx.showModal({
      title: '提示',
      content: '确定要退出登录吗？',
      success: (res) => {
        if (res.confirm) {
          wx.showLoading({ title: '退出中...' });

          // 调用退出登录接口
          api.auth.logout().then(() => {
            wx.hideLoading();
            // 清除本地数据
            app.clearUserInfo();
            // 跳转到登录页
            wx.reLaunch({
              url: '/pages/login/login'
            });
            wx.showToast({
              title: '退出成功',
              icon: 'success'
            });
          }).catch(err => {
            wx.hideLoading();
            // 即使接口调用失败，也清除本地数据
            app.clearUserInfo();
            wx.reLaunch({
              url: '/pages/login/login'
            });
          });
        }
      }
    });
  }
})
