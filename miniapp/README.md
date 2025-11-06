# 孕产妇健康管理小程序 - 前端

## 安装 Vant Weapp UI 组件库

### 步骤 1：安装依赖

在 `miniapp` 目录下执行：

```bash
cd miniapp
npm install
```

### 步骤 2：构建 npm

1. 打开微信开发者工具
2. 点击菜单栏：**工具** → **构建 npm**
3. 等待构建完成，会生成 `miniprogram_npm` 目录

### 步骤 3：验证安装

构建成功后，项目中会出现 `miniapp/miniprogram_npm/@vant/weapp` 目录。

## Vant Weapp 使用说明

### 在页面 JSON 中引入组件

```json
{
  "usingComponents": {
    "van-button": "@vant/weapp/button/index",
    "van-cell": "@vant/weapp/cell/index",
    "van-field": "@vant/weapp/field/index"
  }
}
```

### 在 WXML 中使用组件

```xml
<van-button type="primary">按钮</van-button>
<van-field placeholder="请输入用户名" />
```

## 常用组件

- **van-button** - 按钮
- **van-cell** - 单元格
- **van-field** - 输入框
- **van-icon** - 图标
- **van-image** - 图片
- **van-dialog** - 弹窗
- **van-toast** - 轻提示
- **van-loading** - 加载
- **van-tabs** - 标签页
- **van-card** - 卡片
- **van-grid** - 宫格

## 官方文档

https://vant-contrib.gitee.io/vant-weapp/

## 注意事项

1. 首次使用需要先安装依赖并构建 npm
2. 每次拉取代码后需要重新执行 `npm install`
3. 修改 `package.json` 后需要重新构建 npm
