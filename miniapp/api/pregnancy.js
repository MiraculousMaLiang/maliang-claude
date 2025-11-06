/**
 * 孕期管理API
 * 功能：孕期信息和胎动记录相关接口
 */

const request = require('../utils/request');

/**
 * 获取当前用户的孕期信息
 * 功能：获取当前登录用户的最新孕期信息
 */
function getPregnancyInfo() {
  return request.get('/pregnancy/info');
}

/**
 * 保存或更新孕期信息
 * 功能：添加或更新孕期信息
 */
function savePregnancyInfo(data) {
  return request.post('/pregnancy/info', data);
}

/**
 * 删除孕期信息
 * 功能：删除指定的孕期信息
 */
function deletePregnancyInfo(pregnancyId) {
  return request.delete(`/pregnancy/info/${pregnancyId}`);
}

/**
 * 添加胎动记录
 * 功能：添加一条胎动记录
 */
function addFetalMovement(data) {
  return request.post('/fetal-movement', data);
}

/**
 * 获取指定日期的胎动记录
 * 功能：查询指定日期的所有胎动记录
 */
function getFetalMovementsByDate(date) {
  return request.get(`/fetal-movement/date/${date}`);
}

/**
 * 获取指定日期范围的胎动记录
 * 功能：查询指定日期范围内的所有胎动记录
 */
function getFetalMovementsByDateRange(startDate, endDate) {
  return request.get('/fetal-movement/range', {
    startDate,
    endDate
  });
}

/**
 * 获取最近7天的胎动记录
 * 功能：查询最近7天的胎动记录
 */
function getRecentFetalMovements() {
  return request.get('/fetal-movement/recent');
}

/**
 * 删除胎动记录
 * 功能：删除指定的胎动记录
 */
function deleteFetalMovement(fetalMovementId) {
  return request.delete(`/fetal-movement/${fetalMovementId}`);
}

/**
 * 获取指定日期的胎动统计
 * 功能：统计指定日期的胎动次数和总时长
 */
function getFetalMovementStatistics(date) {
  return request.get(`/fetal-movement/statistics/${date}`);
}

module.exports = {
  getPregnancyInfo,
  savePregnancyInfo,
  deletePregnancyInfo,
  addFetalMovement,
  getFetalMovementsByDate,
  getFetalMovementsByDateRange,
  getRecentFetalMovements,
  deleteFetalMovement,
  getFetalMovementStatistics
};
