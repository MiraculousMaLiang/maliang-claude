/**
 * 登录页面
 * 功能：提供微信一键登录和手机号密码登录
 */

const app = getApp();
const api = require('../../api/index');

Page({
  /**
   * 页面数据
   */
  data: {
    showPhoneLogin: false,
    phone: '',
    password: ''
  },

  /**
   * 微信一键登录
   * 功能：使用微信授权进行登录
   */
  wxLogin() {
    wx.showLoading({ title: '登录中...' });

    // 获取微信登录code
    wx.login({
      success: (res) => {
        if (res.code) {
          // 调用后端微信登录接口
          api.auth.wxLogin(res.code).then(result => {
            wx.hideLoading();
            // 保存token和用户信息
            app.setToken(result.token);
            app.setUserInfo(result.userInfo);
            // 跳转到首页
            wx.reLaunch({
              url: '/pages/index/index'
            });
            wx.showToast({
              title: '登录成功',
              icon: 'success'
            });
          }).catch(err => {
            wx.hideLoading();
            wx.showToast({
              title: err.message || '登录失败',
              icon: 'none'
            });
          });
        } else {
          wx.hideLoading();
          wx.showToast({
            title: '获取登录凭证失败',
            icon: 'none'
          });
        }
      },
      fail: () => {
        wx.hideLoading();
        wx.showToast({
          title: '微信登录失败',
          icon: 'none'
        });
      }
    });
  },

  /**
   * 显示手机号登录表单
   * 功能：切换显示手机号登录表单
   */
  showPhoneLoginForm() {
    this.setData({
      showPhoneLogin: true
    });
  },

  /**
   * 手机号输入
   * 功能：处理手机号输入事件
   */
  onPhoneInput(e) {
    this.setData({
      phone: e.detail.value
    });
  },

  /**
   * 密码输入
   * 功能：处理密码输入事件
   */
  onPasswordInput(e) {
    this.setData({
      password: e.detail.value
    });
  },

  /**
   * 手机号密码登录
   * 功能：使用手机号和密码进行登录
   */
  phoneLogin() {
    const { phone, password } = this.data;

    // 验证手机号
    if (!phone) {
      wx.showToast({
        title: '请输入手机号',
        icon: 'none'
      });
      return;
    }

    if (!/^1[3-9]\d{9}$/.test(phone)) {
      wx.showToast({
        title: '手机号格式不正确',
        icon: 'none'
      });
      return;
    }

    // 验证密码
    if (!password) {
      wx.showToast({
        title: '请输入密码',
        icon: 'none'
      });
      return;
    }

    wx.showLoading({ title: '登录中...' });

    // 调用登录接口
    api.auth.login(phone, password).then(result => {
      wx.hideLoading();
      // 保存token和用户信息
      app.setToken(result.token);
      app.setUserInfo(result.userInfo);
      // 跳转到首页
      wx.reLaunch({
        url: '/pages/index/index'
      });
      wx.showToast({
        title: '登录成功',
        icon: 'success'
      });
    }).catch(err => {
      wx.hideLoading();
      wx.showToast({
        title: err.message || '登录失败',
        icon: 'none'
      });
    });
  },

  /**
   * 跳转到注册页面
   * 功能：跳转到用户注册页面
   */
  goToRegister() {
    wx.showToast({
      title: '注册功能正在开发中',
      icon: 'none'
    });
    // wx.navigateTo({
    //   url: '/pages/register/register'
    // });
  }
})
