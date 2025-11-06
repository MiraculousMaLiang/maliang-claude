/**
 * 认证相关API接口
 * 功能：提供登录、注册、微信授权等认证接口
 */

const request = require('../utils/request');

/**
 * 微信登录
 * 功能：使用微信code进行登录
 */
function wxLogin(code) {
  return request.post('/auth/wxLogin', { code });
}

/**
 * 手机号登录
 * 功能：使用手机号和密码进行登录
 */
function login(phone, password) {
  return request.post('/auth/login', { phone, password });
}

/**
 * 注册
 * 功能：注册新用户账号
 */
function register(data) {
  return request.post('/auth/register', data);
}

/**
 * 退出登录
 * 功能：退出当前登录状态
 */
function logout() {
  return request.post('/auth/logout');
}

module.exports = {
  wxLogin,
  login,
  register,
  logout
}
