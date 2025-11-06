/**
 * 健康监测API
 * 功能：体征监测和症状记录相关接口
 */

const request = require('../utils/request');

// ========== 体征监测 ==========

/**
 * 添加体征记录
 */
function addVitalSigns(data) {
  return request.post('/vital-signs', data);
}

/**
 * 更新体征记录
 */
function updateVitalSigns(data) {
  return request.put('/vital-signs', data);
}

/**
 * 获取指定日期的体征记录
 */
function getVitalSignsByDate(date) {
  return request.get(`/vital-signs/date/${date}`);
}

/**
 * 获取最新体征记录
 */
function getLatestVitalSigns() {
  return request.get('/vital-signs/latest');
}

/**
 * 获取最近N天的体征记录
 */
function getRecentVitalSigns(days = 7) {
  return request.get('/vital-signs/recent', { days });
}

/**
 * 删除体征记录
 */
function deleteVitalSigns(id) {
  return request.delete(`/vital-signs/${id}`);
}

// ========== 症状记录 ==========

/**
 * 添加症状记录
 */
function addSymptomRecord(data) {
  return request.post('/symptom-record', data);
}

/**
 * 更新症状记录
 */
function updateSymptomRecord(data) {
  return request.put('/symptom-record', data);
}

/**
 * 获取指定日期的症状记录
 */
function getSymptomRecordsByDate(date) {
  return request.get(`/symptom-record/date/${date}`);
}

/**
 * 获取最近N天的症状记录
 */
function getRecentSymptomRecords(days = 7) {
  return request.get('/symptom-record/recent', { days });
}

/**
 * 获取症状详情
 */
function getSymptomRecordById(id) {
  return request.get(`/symptom-record/${id}`);
}

/**
 * 删除症状记录
 */
function deleteSymptomRecord(id) {
  return request.delete(`/symptom-record/${id}`);
}

module.exports = {
  addVitalSigns,
  updateVitalSigns,
  getVitalSignsByDate,
  getLatestVitalSigns,
  getRecentVitalSigns,
  deleteVitalSigns,
  addSymptomRecord,
  updateSymptomRecord,
  getSymptomRecordsByDate,
  getRecentSymptomRecords,
  getSymptomRecordById,
  deleteSymptomRecord
};
