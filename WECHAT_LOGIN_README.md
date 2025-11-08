# 微信小程序登录配置说明

## 📝 概述

本项目已完善微信小程序登录功能，支持通过微信授权快速登录，新用户自动注册。

## 🔧 后端配置

### 1. 获取微信小程序凭证

1. 登录[微信公众平台](https://mp.weixin.qq.com/)
2. 进入"开发" -> "开发管理" -> "开发设置"
3. 获取以下信息：
   - **AppID**（小程序ID）
   - **AppSecret**（小程序密钥）

### 2. 配置application.yml

打开 `backend/src/main/resources/application.yml`，找到微信配置部分：

```yaml
# 微信小程序配置
wechat:
  miniapp:
    # 小程序AppID（请替换为实际的AppID）
    appid: your-appid-here
    # 小程序AppSecret（请替换为实际的AppSecret）
    secret: your-secret-here
    # 微信API地址
    api-url: https://api.weixin.qq.com
```

**请将 `your-appid-here` 和 `your-secret-here` 替换为您的实际AppID和AppSecret！**

## 📱 前端配置

### 1. 配置小程序AppID

打开 `miniapp/project.config.json`，修改appid：

```json
{
  "appid": "your-appid-here",
  ...
}
```

### 2. 配置服务器域名

在微信公众平台配置服务器域名白名单：
1. 登录微信公众平台
2. 进入"开发" -> "开发管理" -> "开发设置" -> "服务器域名"
3. 添加以下域名：
   - **request合法域名**: `https://your-domain.com`（您的后端API地址）
   - **uploadFile合法域名**: `https://your-domain.com`
   - **downloadFile合法域名**: `https://your-domain.com`

## 🚀 使用流程

### 完整的微信登录流程

```
用户点击"微信登录"
    ↓
调用 wx.login() 获取 code
    ↓
调用后端 /api/auth/wxLogin 接口，传入 code
    ↓
后端调用微信 code2Session 接口，获取 openid
    ↓
根据 openid 查询或创建用户
    ↓
生成 token 并返回用户信息
    ↓
前端保存 token，完成登录
```

### 后端实现要点

1. **WechatUtil.java** - 微信API工具类
   - `code2Session(String code)` - 调用微信接口，用code换取openid

2. **AuthServiceImpl.wxLogin()** - 微信登录逻辑
   - 调用微信接口获取openid
   - 根据openid查询用户，不存在则自动注册
   - 支持更新用户昵称和头像
   - 生成token并返回

3. **WxLoginDTO** - 微信登录请求参数
   ```java
   {
       "code": "微信登录凭证",
       "nickname": "用户昵称（可选）",
       "avatar": "用户头像URL（可选）",
       "gender": 性别（可选，0-未知，1-男，2-女）
   }
   ```

### 前端调用示例

```javascript
// 在login.js中
wxLogin() {
  wx.showLoading({ title: '登录中...' });

  // 1. 获取微信登录code
  wx.login({
    success: (res) => {
      if (res.code) {
        // 2. 调用后端微信登录接口
        api.auth.wxLogin(res.code).then(result => {
          wx.hideLoading();
          // 3. 保存token和用户信息
          app.setToken(result.token);
          app.setUserInfo(result.userInfo);
          // 4. 跳转到首页
          wx.reLaunch({ url: '/pages/index/index' });
          wx.showToast({ title: '登录成功', icon: 'success' });
        }).catch(err => {
          wx.hideLoading();
          wx.showToast({ title: err.message || '登录失败', icon: 'none' });
        });
      }
    }
  });
}
```

## 🔒 安全说明

1. **AppSecret保护**
   - AppSecret非常重要，不要泄露或提交到公开仓库
   - 建议使用环境变量或配置中心管理
   - 生产环境与开发环境使用不同的配置

2. **Token安全**
   - Token有效期默认30天，可在application.yml中调整
   - 前端使用HTTPS与后端通信
   - Token存储在微信小程序的安全存储中

3. **接口限制**
   - code2Session接口每分钟调用次数有限制
   - 建议做好错误处理和重试机制

## 📊 数据库设计

sys_user表包含以下微信相关字段：

| 字段名 | 类型 | 说明 |
|--------|------|------|
| openid | VARCHAR(64) | 微信用户唯一标识 |
| unionid | VARCHAR(64) | 开放平台唯一标识 |
| nickname | VARCHAR(50) | 用户昵称 |
| avatar | VARCHAR(255) | 用户头像URL |
| gender | INT | 性别（0-未知，1-男，2-女）|

## ❓ 常见问题

### 1. 登录失败提示"微信登录失败，请重试"

**原因：**
- AppID或AppSecret配置错误
- code已过期（code只能使用一次，有效期5分钟）
- 网络问题

**解决方案：**
- 检查application.yml中的配置是否正确
- 确保code是最新获取的
- 查看后端日志，确认微信接口调用情况

### 2. 开发工具可以登录，真机无法登录

**原因：**
- 服务器域名未配置白名单
- 后端接口未使用HTTPS

**解决方案：**
- 在微信公众平台配置服务器域名
- 确保后端使用HTTPS协议

### 3. 获取不到unionid

**原因：**
- 小程序未绑定到微信开放平台
- 用户未关注公众号

**解决方案：**
- unionid是可选的，不影响基本登录功能
- 如需unionid，需要将小程序绑定到微信开放平台

## 📚 相关文档

- [微信小程序登录官方文档](https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/login.html)
- [code2Session接口文档](https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html)
- [Sa-Token官方文档](https://sa-token.cc/)

## 🎉 完成提示

配置完成后，重启后端服务，即可使用微信登录功能！
