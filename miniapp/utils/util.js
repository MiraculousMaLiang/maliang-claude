/**
 * 通用工具类
 * 功能：提供日期格式化、数据验证等通用工具方法
 */

/**
 * 格式化日期时间
 * 功能：将Date对象格式化为指定格式的字符串
 */
function formatTime(date, format = 'yyyy-MM-dd HH:mm:ss') {
  if (!date) return '';

  if (typeof date === 'string' || typeof date === 'number') {
    date = new Date(date);
  }

  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  const hour = date.getHours();
  const minute = date.getMinutes();
  const second = date.getSeconds();

  return format
    .replace('yyyy', year)
    .replace('MM', padZero(month))
    .replace('dd', padZero(day))
    .replace('HH', padZero(hour))
    .replace('mm', padZero(minute))
    .replace('ss', padZero(second));
}

/**
 * 格式化日期
 * 功能：将Date对象格式化为日期字符串
 */
function formatDate(date) {
  return formatTime(date, 'yyyy-MM-dd');
}

/**
 * 格式化时间（仅时分）
 * 功能：将Date对象格式化为时间字符串
 */
function formatTimeOnly(date) {
  return formatTime(date, 'HH:mm');
}

/**
 * 数字补零
 * 功能：给小于10的数字前面补0
 */
function padZero(n) {
  return n < 10 ? '0' + n : n;
}

/**
 * 计算预产期
 * 功能：根据末次月经日期计算预产期
 */
function calculateDueDate(lastPeriod) {
  if (!lastPeriod) return null;

  const date = new Date(lastPeriod);
  // 月份+9或-3
  let month = date.getMonth() + 9;
  let year = date.getFullYear();
  if (month > 11) {
    month = month - 12;
    year = year + 1;
  }
  // 日期+7
  const day = date.getDate() + 7;

  return new Date(year, month, day);
}

/**
 * 计算孕周
 * 功能：根据末次月经日期计算当前孕周
 */
function calculateWeeks(lastPeriod) {
  if (!lastPeriod) return 0;

  const now = new Date();
  const start = new Date(lastPeriod);
  const days = Math.floor((now - start) / (1000 * 60 * 60 * 24));
  const weeks = Math.floor(days / 7);

  return weeks;
}

/**
 * 计算距离预产期天数
 * 功能：计算当前日期距离预产期还有多少天
 */
function calculateDaysTodue(dueDate) {
  if (!dueDate) return 0;

  const now = new Date();
  const due = new Date(dueDate);
  const days = Math.floor((due - now) / (1000 * 60 * 60 * 24));

  return days;
}

/**
 * 验证手机号
 * 功能：验证手机号格式是否正确
 */
function validatePhone(phone) {
  const reg = /^1[3-9]\d{9}$/;
  return reg.test(phone);
}

/**
 * 验证身份证号
 * 功能：验证身份证号格式是否正确
 */
function validateIdCard(idCard) {
  const reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
  return reg.test(idCard);
}

/**
 * 节流函数
 * 功能：限制函数在指定时间内只能执行一次
 */
function throttle(fn, delay = 1000) {
  let timer = null;
  return function() {
    if (timer) return;
    timer = setTimeout(() => {
      fn.apply(this, arguments);
      timer = null;
    }, delay);
  }
}

/**
 * 防抖函数
 * 功能：延迟执行函数,如果在延迟时间内再次触发则重新计时
 */
function debounce(fn, delay = 500) {
  let timer = null;
  return function() {
    if (timer) clearTimeout(timer);
    timer = setTimeout(() => {
      fn.apply(this, arguments);
    }, delay);
  }
}

module.exports = {
  formatTime,
  formatDate,
  formatTimeOnly,
  padZero,
  calculateDueDate,
  calculateWeeks,
  calculateDaysTodue,
  validatePhone,
  validateIdCard,
  throttle,
  debounce
}
