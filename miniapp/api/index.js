/**
 * API接口统一导出
 * 功能：统一管理所有API接口
 */

const user = require('./user');
const auth = require('./auth');

module.exports = {
  user,
  auth
}
