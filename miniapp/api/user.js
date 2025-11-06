/**
 * 用户相关API接口
 * 功能：提供用户信息查询、修改等接口
 */

const request = require('../utils/request');

/**
 * 获取用户信息
 * 功能：获取当前登录用户的详细信息
 */
function getUserInfo() {
  return request.get('/user/info');
}

/**
 * 更新用户信息
 * 功能：更新用户的基本信息
 */
function updateUserInfo(data) {
  return request.put('/user/info', data);
}

/**
 * 获取用户档案
 * 功能：获取用户的健康档案信息
 */
function getUserProfile() {
  return request.get('/user/profile');
}

/**
 * 更新用户档案
 * 功能：更新用户的健康档案信息
 */
function updateUserProfile(data) {
  return request.put('/user/profile', data);
}

module.exports = {
  getUserInfo,
  updateUserInfo,
  getUserProfile,
  updateUserProfile
}
