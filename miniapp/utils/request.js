/**
 * 网络请求工具类
 * 功能：封装微信小程序的网络请求,统一处理请求和响应
 */

const app = getApp();

/**
 * 发起网络请求
 * 功能：统一处理HTTP请求,自动添加token和错误处理
 */
function request(options) {
  return new Promise((resolve, reject) => {
    // 获取token
    const token = app.globalData.token || wx.getStorageSync('token');

    wx.request({
      url: app.globalData.baseUrl + options.url,
      method: options.method || 'GET',
      data: options.data || {},
      header: {
        'Content-Type': 'application/json',
        'satoken': token || ''
      },
      success: (res) => {
        // 请求成功
        if (res.statusCode === 200) {
          const data = res.data;
          // 判断业务是否成功（使用==以兼容String和Integer类型）
          if (data.code == 200) {
            resolve(data.data);
          } else if (data.code == 401) {
            // token失效，跳转到登录页
            wx.showToast({
              title: '登录已过期，请重新登录',
              icon: 'none'
            });
            app.clearUserInfo();
            wx.reLaunch({
              url: '/pages/login/login'
            });
            reject(data);
          } else {
            // 业务失败
            wx.showToast({
              title: data.message || '请求失败',
              icon: 'none'
            });
            reject(data);
          }
        } else {
          // HTTP请求失败
          wx.showToast({
            title: '网络请求失败',
            icon: 'none'
          });
          reject(res);
        }
      },
      fail: (err) => {
        // 请求失败
        wx.showToast({
          title: '网络连接失败',
          icon: 'none'
        });
        reject(err);
      }
    });
  });
}

/**
 * GET请求
 */
function get(url, data) {
  return request({
    url: url,
    method: 'GET',
    data: data
  });
}

/**
 * POST请求
 */
function post(url, data) {
  return request({
    url: url,
    method: 'POST',
    data: data
  });
}

/**
 * PUT请求
 */
function put(url, data) {
  return request({
    url: url,
    method: 'PUT',
    data: data
  });
}

/**
 * DELETE请求
 */
function del(url, data) {
  return request({
    url: url,
    method: 'DELETE',
    data: data
  });
}

module.exports = {
  request,
  get,
  post,
  put,
  delete: del,  // 导出delete作为别名
  del
}
