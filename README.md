# 孕产妇全周期健康管理小程序

基于微信小程序和Spring Boot的孕产妇全周期健康管理系统，为孕产妇提供从备孕、怀孕到产后恢复的全方位健康管理服务。

## 项目结构

```
maliang-claude/
├── backend/                    # 后端项目
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/maternal/health/
│   │   │   │       ├── controller/      # 控制器层
│   │   │   │       ├── service/         # 业务逻辑层
│   │   │   │       ├── mapper/          # 数据访问层
│   │   │   │       ├── entity/          # 实体类
│   │   │   │       ├── dto/             # 数据传输对象
│   │   │   │       ├── vo/              # 视图对象
│   │   │   │       ├── config/          # 配置类
│   │   │   │       ├── common/          # 通用类
│   │   │   │       └── utils/           # 工具类
│   │   │   └── resources/
│   │   │       └── application.yml      # 配置文件
│   │   └── test/                        # 测试代码
│   └── pom.xml                          # Maven配置
│
├── miniapp/                    # 小程序前端
│   ├── pages/                  # 页面
│   ├── components/             # 组件
│   ├── utils/                  # 工具类
│   ├── api/                    # API接口
│   ├── app.js                  # 入口文件
│   ├── app.json               # 配置文件
│   └── app.wxss               # 全局样式
│
├── sql/                        # 数据库脚本
│   └── maternal_health.sql    # 建表SQL
│
└── docs/                       # 文档
    └── database_analysis.md   # 数据库设计分析
```

## 技术栈

### 后端技术
- Spring Boot 2.7.18
- MyBatis Plus 3.5.3.1
- MySQL 8.0
- Redis
- Sa-Token 1.37.0（权限认证）
- Hutool 5.8.23（工具类）
- Lombok

### 前端技术
- 微信小程序原生开发
- 微信小程序API

## 功能模块

### 1. 用户管理
- 微信授权登录
- 用户信息管理
- 健康档案管理
- 紧急联系人设置

### 2. 孕期管理
- 孕期计算器（预产期、孕周）
- 孕期日历
- 胎儿发育跟踪
- 胎动记录

### 3. 健康监测
- 体征记录（体重、血压、血糖、体温）
- 症状记录
- 数据可视化

### 4. 产检管理
- 产检计划
- 检查结果管理
- 医生沟通

### 5. 营养管理
- 膳食记录
- 营养建议
- 食谱推荐

### 6. 运动健身
- 运动计划
- 运动记录
- 安全提醒

### 7. 知识科普
- 孕期知识
- 育儿准备
- 专家文章

### 8. 社区交流
- 孕妈圈子
- 话题讨论
- 专家答疑

### 9. 产后管理
- 产后恢复跟踪
- 新生儿护理
- 母乳喂养记录

### 10. 紧急服务
- 紧急联系
- 急救指导
- 医院导航

## 快速开始

### 后端部署

#### 1. 环境要求
- JDK 1.8+
- Maven 3.6+
- MySQL 8.0+
- Redis 5.0+

#### 2. 数据库初始化
```bash
# 创建数据库并执行建表脚本
mysql -u root -p < sql/maternal_health.sql
```

#### 3. 修改配置
编辑 `backend/src/main/resources/application.yml`：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/maternal_health
    username: your_username
    password: your_password
  redis:
    host: localhost
    port: 6379
```

#### 4. 启动后端
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

访问测试接口：http://localhost:8080/api/test/health

### 前端部署

#### 1. 导入小程序项目
1. 使用微信开发者工具打开 `miniapp` 目录
2. 填写AppID（在 `project.config.json` 中配置）

#### 2. 修改配置
编辑 `miniapp/app.js`，修改后端地址：
```javascript
globalData: {
  baseUrl: 'http://your-server:8080/api'
}
```

#### 3. 编译运行
在微信开发者工具中点击"编译"即可预览

## 数据库设计

### 核心表结构（18张表）

1. **sys_user** - 用户表
2. **user_profile** - 用户档案表
3. **pregnancy_info** - 孕期信息表
4. **checkup_record** - 产检记录表
5. **vital_signs** - 体征记录表
6. **symptom_record** - 症状记录表
7. **diet_record** - 膳食记录表
8. **exercise_record** - 运动记录表
9. **fetal_movement** - 胎动记录表
10. **knowledge_article** - 知识文章表
11. **community_post** - 社区帖子表
12. **comment** - 评论表
13. **emergency_contact** - 紧急联系人表
14. **reminder_setting** - 提醒设置表
15. **postpartum_recovery** - 产后恢复记录表
16. **newborn_info** - 新生儿信息表
17. **feeding_record** - 喂养记录表
18. **vaccination_record** - 疫苗接种表

详细设计说明请查看：[数据库设计分析文档](docs/database_analysis.md)

### 数据库设计特点
- ✅ 主键ID统一使用BIGINT类型
- ✅ 时间类型规范（DATE/TIME/DATETIME）
- ✅ 无外键约束，使用逻辑外键
- ✅ 完善的逻辑删除机制
- ✅ 合理的索引设计
- ✅ UTF8MB4字符集支持

## 接口文档

### 后端接口

#### 测试接口
```
GET /api/test/health        # 健康检查
GET /api/test/hello         # 测试接口
```

#### 认证接口
```
POST /api/auth/wxLogin      # 微信登录
POST /api/auth/login        # 手机号登录
POST /api/auth/register     # 注册
POST /api/auth/logout       # 退出登录
```

#### 用户接口
```
GET  /api/user/info         # 获取用户信息
PUT  /api/user/info         # 更新用户信息
GET  /api/user/profile      # 获取用户档案
PUT  /api/user/profile      # 更新用户档案
```

更多接口将在后续迭代中完善...

## 开发规范

### 代码注释规范
每个功能方法前必须添加注释说明功能，格式如下：
```java
/**
 * 方法功能描述
 * 功能：详细说明该方法的作用
 */
```

### 代码编写规范
1. 不要方法套娃，保持代码简洁直接
2. 遵循单一职责原则
3. 统一的异常处理
4. 合理使用工具类

## 开发进度

### 第一阶段（已完成）✅
- [x] 项目目录结构搭建
- [x] Spring Boot后端基础框架
- [x] 微信小程序前端基础框架
- [x] 数据库表结构设计
- [x] 数据库设计合理性分析

### 第二阶段（待开发）
- [ ] 用户管理模块实现
- [ ] 孕期管理模块实现
- [ ] 健康监测模块实现

### 第三阶段（待开发）
- [ ] 产检管理模块实现
- [ ] 营养管理模块实现
- [ ] 运动健身模块实现

### 第四阶段（待开发）
- [ ] 知识科普模块实现
- [ ] 社区交流模块实现
- [ ] 产后管理模块实现

### 第五阶段（待开发）
- [ ] 紧急服务模块实现
- [ ] 系统优化和测试
- [ ] 部署上线

## 项目亮点

1. **全周期管理**：覆盖备孕、孕期、产后全流程
2. **智能提醒**：产检、用药、体征记录等智能提醒
3. **数据可视化**：体征数据趋势图表展示
4. **知识科普**：权威的孕产期知识库
5. **社区互助**：孕妈交流互助平台
6. **安全可靠**：完善的数据安全保护机制

## 注意事项

1. 本项目为学习和开发使用，涉及医疗健康建议仅供参考
2. 实际使用时应咨询专业医生
3. 敏感数据需要加密存储
4. 生产环境需要配置HTTPS

## 开发团队

孕产妇健康管理系统开发组

## 许可证

Copyright © 2024 All Rights Reserved.