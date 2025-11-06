-- 孕产妇全周期健康管理系统数据库
-- 创建时间：2024-11-06

-- 创建数据库
CREATE DATABASE IF NOT EXISTS maternal_health DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE maternal_health;

-- ====================================
-- 1. 用户表
-- 功能：存储用户基本信息和登录凭证
-- ====================================
CREATE TABLE `sys_user` (
  `id` BIGINT NOT NULL COMMENT '用户ID（主键）',
  `openid` VARCHAR(128) DEFAULT NULL COMMENT '微信OpenID',
  `unionid` VARCHAR(128) DEFAULT NULL COMMENT '微信UnionID',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `password` VARCHAR(128) DEFAULT NULL COMMENT '密码（加密存储）',
  `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
  `real_name` VARCHAR(50) DEFAULT NULL COMMENT '真实姓名',
  `id_card` VARCHAR(18) DEFAULT NULL COMMENT '身份证号',
  `gender` TINYINT DEFAULT 2 COMMENT '性别：0-未知，1-男，2-女',
  `birthday` DATE DEFAULT NULL COMMENT '出生日期',
  `status` TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-正常',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_openid` (`openid`),
  UNIQUE KEY `uk_phone` (`phone`),
  KEY `idx_status` (`status`),
  KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ====================================
-- 2. 用户档案表
-- 功能：存储用户的健康档案信息
-- ====================================
CREATE TABLE `user_profile` (
  `id` BIGINT NOT NULL COMMENT '档案ID（主键）',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `height` DECIMAL(5,2) DEFAULT NULL COMMENT '身高（厘米）',
  `weight` DECIMAL(5,2) DEFAULT NULL COMMENT '体重（千克）',
  `blood_type` VARCHAR(10) DEFAULT NULL COMMENT '血型：A/B/O/AB/RH阴性等',
  `marriage_status` TINYINT DEFAULT NULL COMMENT '婚姻状况：0-未婚，1-已婚，2-离异，3-丧偶',
  `education` VARCHAR(50) DEFAULT NULL COMMENT '学历',
  `occupation` VARCHAR(50) DEFAULT NULL COMMENT '职业',
  `medical_history` TEXT DEFAULT NULL COMMENT '既往病史',
  `allergy_history` TEXT DEFAULT NULL COMMENT '过敏史',
  `family_history` TEXT DEFAULT NULL COMMENT '家族病史',
  `surgery_history` TEXT DEFAULT NULL COMMENT '手术史',
  `address` VARCHAR(255) DEFAULT NULL COMMENT '居住地址',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_id` (`user_id`),
  KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户档案表';

-- ====================================
-- 3. 孕期信息表
-- 功能：存储孕妇的孕期相关信息
-- ====================================
CREATE TABLE `pregnancy_info` (
  `id` BIGINT NOT NULL COMMENT '孕期信息ID（主键）',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `pregnancy_count` INT DEFAULT 1 COMMENT '怀孕次数',
  `birth_count` INT DEFAULT 0 COMMENT '生育次数',
  `last_period_date` DATE NOT NULL COMMENT '末次月经日期',
  `due_date` DATE NOT NULL COMMENT '预产期',
  `current_week` INT DEFAULT 0 COMMENT '当前孕周',
  `pregnancy_status` TINYINT DEFAULT 1 COMMENT '孕期状态：1-孕早期，2-孕中期，3-孕晚期，4-已分娩',
  `pregnancy_type` TINYINT DEFAULT 1 COMMENT '妊娠类型：1-单胎，2-双胎，3-多胎',
  `conception_method` TINYINT DEFAULT 1 COMMENT '受孕方式：1-自然受孕，2-辅助生殖',
  `risk_level` TINYINT DEFAULT 1 COMMENT '风险等级：1-低危，2-高危',
  `hospital_name` VARCHAR(100) DEFAULT NULL COMMENT '建档医院名称',
  `doctor_name` VARCHAR(50) DEFAULT NULL COMMENT '主治医生姓名',
  `doctor_phone` VARCHAR(20) DEFAULT NULL COMMENT '医生联系电话',
  `remarks` TEXT DEFAULT NULL COMMENT '备注信息',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_pregnancy_status` (`pregnancy_status`),
  KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='孕期信息表';

-- ====================================
-- 4. 产检记录表
-- 功能：记录产检的时间、项目和结果
-- ====================================
CREATE TABLE `checkup_record` (
  `id` BIGINT NOT NULL COMMENT '产检记录ID（主键）',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `pregnancy_id` BIGINT NOT NULL COMMENT '孕期信息ID',
  `checkup_date` DATE NOT NULL COMMENT '产检日期',
  `checkup_week` INT DEFAULT NULL COMMENT '产检孕周',
  `checkup_type` VARCHAR(50) DEFAULT NULL COMMENT '产检类型（如：常规产检、NT检查、唐筛等）',
  `hospital_name` VARCHAR(100) DEFAULT NULL COMMENT '医院名称',
  `doctor_name` VARCHAR(50) DEFAULT NULL COMMENT '医生姓名',
  `weight` DECIMAL(5,2) DEFAULT NULL COMMENT '体重（千克）',
  `blood_pressure` VARCHAR(20) DEFAULT NULL COMMENT '血压（如：120/80）',
  `fetal_heart_rate` INT DEFAULT NULL COMMENT '胎心率（次/分）',
  `fundal_height` DECIMAL(5,2) DEFAULT NULL COMMENT '宫高（厘米）',
  `abdominal_girth` DECIMAL(5,2) DEFAULT NULL COMMENT '腹围（厘米）',
  `result` TEXT DEFAULT NULL COMMENT '检查结果',
  `abnormal_flag` TINYINT DEFAULT 0 COMMENT '异常标记：0-正常，1-异常',
  `doctor_advice` TEXT DEFAULT NULL COMMENT '医生建议',
  `next_checkup_date` DATE DEFAULT NULL COMMENT '下次产检日期',
  `report_images` TEXT DEFAULT NULL COMMENT '检查报告图片（多张用逗号分隔）',
  `remarks` TEXT DEFAULT NULL COMMENT '备注',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_pregnancy_id` (`pregnancy_id`),
  KEY `idx_checkup_date` (`checkup_date`),
  KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='产检记录表';

-- ====================================
-- 5. 体征记录表
-- 功能：记录日常体征数据（体重、血压、血糖、体温等）
-- ====================================
CREATE TABLE `vital_signs` (
  `id` BIGINT NOT NULL COMMENT '体征记录ID（主键）',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `record_date` DATE NOT NULL COMMENT '记录日期',
  `record_time` TIME DEFAULT NULL COMMENT '记录时间',
  `weight` DECIMAL(5,2) DEFAULT NULL COMMENT '体重（千克）',
  `systolic_pressure` INT DEFAULT NULL COMMENT '收缩压',
  `diastolic_pressure` INT DEFAULT NULL COMMENT '舒张压',
  `blood_sugar` DECIMAL(5,2) DEFAULT NULL COMMENT '血糖值（mmol/L）',
  `blood_sugar_type` TINYINT DEFAULT NULL COMMENT '血糖类型：1-空腹，2-餐后1小时，3-餐后2小时',
  `temperature` DECIMAL(4,2) DEFAULT NULL COMMENT '体温（摄氏度）',
  `heart_rate` INT DEFAULT NULL COMMENT '心率（次/分）',
  `remarks` TEXT DEFAULT NULL COMMENT '备注',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_record_date` (`record_date`),
  KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='体征记录表';

-- ====================================
-- 6. 症状记录表
-- 功能：记录孕期不适症状和情绪状态
-- ====================================
CREATE TABLE `symptom_record` (
  `id` BIGINT NOT NULL COMMENT '症状记录ID（主键）',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `record_date` DATE NOT NULL COMMENT '记录日期',
  `record_time` TIME DEFAULT NULL COMMENT '记录时间',
  `symptom_type` VARCHAR(50) DEFAULT NULL COMMENT '症状类型（如：恶心、呕吐、腰痛等）',
  `severity` TINYINT DEFAULT NULL COMMENT '严重程度：1-轻微，2-中度，3-严重',
  `duration` VARCHAR(50) DEFAULT NULL COMMENT '持续时间',
  `description` TEXT DEFAULT NULL COMMENT '症状描述',
  `mood_status` TINYINT DEFAULT NULL COMMENT '情绪状态：1-愉快，2-平静，3-焦虑，4-抑郁',
  `sleep_quality` TINYINT DEFAULT NULL COMMENT '睡眠质量：1-很好，2-良好，3-一般，4-较差，5-很差',
  `sleep_duration` DECIMAL(4,2) DEFAULT NULL COMMENT '睡眠时长（小时）',
  `handled` TINYINT DEFAULT 0 COMMENT '处理标记：0-未处理，1-已处理',
  `handle_method` TEXT DEFAULT NULL COMMENT '处理方法',
  `remarks` TEXT DEFAULT NULL COMMENT '备注',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_record_date` (`record_date`),
  KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='症状记录表';

-- ====================================
-- 7. 膳食记录表
-- 功能：记录每日饮食情况
-- ====================================
CREATE TABLE `diet_record` (
  `id` BIGINT NOT NULL COMMENT '膳食记录ID（主键）',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `record_date` DATE NOT NULL COMMENT '记录日期',
  `meal_type` TINYINT NOT NULL COMMENT '餐次类型：1-早餐，2-午餐，3-晚餐，4-加餐',
  `meal_time` TIME DEFAULT NULL COMMENT '用餐时间',
  `food_name` VARCHAR(100) NOT NULL COMMENT '食物名称',
  `food_amount` VARCHAR(50) DEFAULT NULL COMMENT '食物份量',
  `calories` DECIMAL(8,2) DEFAULT NULL COMMENT '热量（千卡）',
  `protein` DECIMAL(8,2) DEFAULT NULL COMMENT '蛋白质（克）',
  `fat` DECIMAL(8,2) DEFAULT NULL COMMENT '脂肪（克）',
  `carbohydrate` DECIMAL(8,2) DEFAULT NULL COMMENT '碳水化合物（克）',
  `food_images` TEXT DEFAULT NULL COMMENT '食物图片（多张用逗号分隔）',
  `remarks` TEXT DEFAULT NULL COMMENT '备注',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_record_date` (`record_date`),
  KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='膳食记录表';

-- ====================================
-- 8. 运动记录表
-- 功能：记录运动情况
-- ====================================
CREATE TABLE `exercise_record` (
  `id` BIGINT NOT NULL COMMENT '运动记录ID（主键）',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `record_date` DATE NOT NULL COMMENT '记录日期',
  `exercise_type` VARCHAR(50) NOT NULL COMMENT '运动类型（如：散步、瑜伽、游泳等）',
  `start_time` TIME DEFAULT NULL COMMENT '开始时间',
  `duration` INT DEFAULT NULL COMMENT '运动时长（分钟）',
  `intensity` TINYINT DEFAULT NULL COMMENT '运动强度：1-轻度，2-中度，3-高强度',
  `calories_burned` DECIMAL(8,2) DEFAULT NULL COMMENT '消耗热量（千卡）',
  `heart_rate` INT DEFAULT NULL COMMENT '平均心率（次/分）',
  `feeling` TINYINT DEFAULT NULL COMMENT '运动感受：1-很好，2-良好，3-一般，4-疲劳',
  `remarks` TEXT DEFAULT NULL COMMENT '备注',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_record_date` (`record_date`),
  KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='运动记录表';

-- ====================================
-- 9. 胎动记录表
-- 功能：记录胎动情况
-- ====================================
CREATE TABLE `fetal_movement` (
  `id` BIGINT NOT NULL COMMENT '胎动记录ID（主键）',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `pregnancy_id` BIGINT NOT NULL COMMENT '孕期信息ID',
  `record_date` DATE NOT NULL COMMENT '记录日期',
  `record_time` TIME NOT NULL COMMENT '记录时间',
  `movement_count` INT NOT NULL COMMENT '胎动次数',
  `time_period` TINYINT NOT NULL COMMENT '时间段：1-早上，2-中午，3-晚上',
  `duration` INT DEFAULT NULL COMMENT '观察时长（分钟）',
  `strength` TINYINT DEFAULT NULL COMMENT '胎动强度：1-轻微，2-适中，3-强烈',
  `remarks` TEXT DEFAULT NULL COMMENT '备注',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_pregnancy_id` (`pregnancy_id`),
  KEY `idx_record_date` (`record_date`),
  KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='胎动记录表';

-- ====================================
-- 10. 知识文章表
-- 功能：存储孕期知识科普文章
-- ====================================
CREATE TABLE `knowledge_article` (
  `id` BIGINT NOT NULL COMMENT '文章ID（主键）',
  `category` VARCHAR(50) NOT NULL COMMENT '文章分类（如：孕期知识、育儿准备、营养指导等）',
  `title` VARCHAR(200) NOT NULL COMMENT '文章标题',
  `summary` VARCHAR(500) DEFAULT NULL COMMENT '文章摘要',
  `content` TEXT NOT NULL COMMENT '文章内容',
  `cover_image` VARCHAR(255) DEFAULT NULL COMMENT '封面图片',
  `author` VARCHAR(50) DEFAULT NULL COMMENT '作者',
  `source` VARCHAR(100) DEFAULT NULL COMMENT '来源',
  `tags` VARCHAR(200) DEFAULT NULL COMMENT '标签（多个用逗号分隔）',
  `view_count` INT DEFAULT 0 COMMENT '浏览次数',
  `like_count` INT DEFAULT 0 COMMENT '点赞次数',
  `collect_count` INT DEFAULT 0 COMMENT '收藏次数',
  `status` TINYINT DEFAULT 1 COMMENT '状态：0-下架，1-上架',
  `sort_order` INT DEFAULT 0 COMMENT '排序值',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_category` (`category`),
  KEY `idx_status` (`status`),
  KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='知识文章表';

-- ====================================
-- 11. 社区帖子表
-- 功能：存储用户发布的社区交流帖子
-- ====================================
CREATE TABLE `community_post` (
  `id` BIGINT NOT NULL COMMENT '帖子ID（主键）',
  `user_id` BIGINT NOT NULL COMMENT '发布用户ID',
  `title` VARCHAR(200) DEFAULT NULL COMMENT '帖子标题',
  `content` TEXT NOT NULL COMMENT '帖子内容',
  `images` TEXT DEFAULT NULL COMMENT '图片（多张用逗号分隔）',
  `topic` VARCHAR(50) DEFAULT NULL COMMENT '话题标签',
  `view_count` INT DEFAULT 0 COMMENT '浏览次数',
  `like_count` INT DEFAULT 0 COMMENT '点赞次数',
  `comment_count` INT DEFAULT 0 COMMENT '评论次数',
  `status` TINYINT DEFAULT 1 COMMENT '状态：0-隐藏，1-正常，2-精华',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_status` (`status`),
  KEY `idx_deleted` (`deleted`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='社区帖子表';

-- ====================================
-- 12. 评论表
-- 功能：存储帖子和文章的评论
-- ====================================
CREATE TABLE `comment` (
  `id` BIGINT NOT NULL COMMENT '评论ID（主键）',
  `user_id` BIGINT NOT NULL COMMENT '评论用户ID',
  `target_type` TINYINT NOT NULL COMMENT '评论对象类型：1-帖子，2-文章',
  `target_id` BIGINT NOT NULL COMMENT '评论对象ID',
  `parent_id` BIGINT DEFAULT 0 COMMENT '父评论ID（0表示一级评论）',
  `reply_to_user_id` BIGINT DEFAULT NULL COMMENT '回复的用户ID',
  `content` TEXT NOT NULL COMMENT '评论内容',
  `like_count` INT DEFAULT 0 COMMENT '点赞次数',
  `status` TINYINT DEFAULT 1 COMMENT '状态：0-隐藏，1-正常',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_target` (`target_type`, `target_id`),
  KEY `idx_parent_id` (`parent_id`),
  KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评论表';

-- ====================================
-- 13. 紧急联系人表
-- 功能：存储用户的紧急联系人信息
-- ====================================
CREATE TABLE `emergency_contact` (
  `id` BIGINT NOT NULL COMMENT '联系人ID（主键）',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `contact_name` VARCHAR(50) NOT NULL COMMENT '联系人姓名',
  `relationship` VARCHAR(20) NOT NULL COMMENT '关系（如：配偶、父母、朋友等）',
  `phone` VARCHAR(20) NOT NULL COMMENT '联系电话',
  `is_primary` TINYINT DEFAULT 0 COMMENT '是否主要联系人：0-否，1-是',
  `sort_order` INT DEFAULT 0 COMMENT '排序值',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='紧急联系人表';

-- ====================================
-- 14. 提醒设置表
-- 功能：存储用户的各种提醒设置
-- ====================================
CREATE TABLE `reminder_setting` (
  `id` BIGINT NOT NULL COMMENT '提醒设置ID（主键）',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `reminder_type` VARCHAR(50) NOT NULL COMMENT '提醒类型（如：产检、体征记录、运动、喝水等）',
  `reminder_time` TIME NOT NULL COMMENT '提醒时间',
  `reminder_days` VARCHAR(50) DEFAULT NULL COMMENT '提醒日期（如：1,2,3,4,5表示周一到周五）',
  `enabled` TINYINT DEFAULT 1 COMMENT '是否启用：0-禁用，1-启用',
  `remarks` VARCHAR(200) DEFAULT NULL COMMENT '备注',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='提醒设置表';

-- ====================================
-- 15. 产后恢复记录表
-- 功能：记录产后恢复情况
-- ====================================
CREATE TABLE `postpartum_recovery` (
  `id` BIGINT NOT NULL COMMENT '恢复记录ID（主键）',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `pregnancy_id` BIGINT NOT NULL COMMENT '孕期信息ID',
  `record_date` DATE NOT NULL COMMENT '记录日期',
  `days_postpartum` INT DEFAULT NULL COMMENT '产后天数',
  `weight` DECIMAL(5,2) DEFAULT NULL COMMENT '体重（千克）',
  `lochia_status` VARCHAR(50) DEFAULT NULL COMMENT '恶露情况',
  `uterine_recovery` VARCHAR(50) DEFAULT NULL COMMENT '子宫恢复情况',
  `wound_healing` VARCHAR(50) DEFAULT NULL COMMENT '伤口愈合情况',
  `breastfeeding_status` VARCHAR(50) DEFAULT NULL COMMENT '母乳喂养情况',
  `mood_status` TINYINT DEFAULT NULL COMMENT '情绪状态：1-良好，2-一般，3-抑郁倾向',
  `physical_feeling` TEXT DEFAULT NULL COMMENT '身体感受',
  `remarks` TEXT DEFAULT NULL COMMENT '备注',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_pregnancy_id` (`pregnancy_id`),
  KEY `idx_record_date` (`record_date`),
  KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='产后恢复记录表';

-- ====================================
-- 16. 新生儿信息表
-- 功能：存储新生儿基本信息
-- ====================================
CREATE TABLE `newborn_info` (
  `id` BIGINT NOT NULL COMMENT '新生儿信息ID（主键）',
  `user_id` BIGINT NOT NULL COMMENT '用户ID（母亲）',
  `pregnancy_id` BIGINT NOT NULL COMMENT '孕期信息ID',
  `baby_name` VARCHAR(50) DEFAULT NULL COMMENT '宝宝姓名',
  `baby_gender` TINYINT NOT NULL COMMENT '性别：1-男，2-女',
  `birth_date` DATE NOT NULL COMMENT '出生日期',
  `birth_time` TIME DEFAULT NULL COMMENT '出生时间',
  `birth_weight` DECIMAL(5,2) DEFAULT NULL COMMENT '出生体重（千克）',
  `birth_height` DECIMAL(5,2) DEFAULT NULL COMMENT '出生身长（厘米）',
  `head_girth` DECIMAL(5,2) DEFAULT NULL COMMENT '头围（厘米）',
  `delivery_method` TINYINT DEFAULT NULL COMMENT '分娩方式：1-顺产，2-剖宫产',
  `gestation_weeks` INT DEFAULT NULL COMMENT '孕周（周）',
  `apgar_score` VARCHAR(20) DEFAULT NULL COMMENT 'Apgar评分',
  `health_status` VARCHAR(100) DEFAULT NULL COMMENT '健康状况',
  `remarks` TEXT DEFAULT NULL COMMENT '备注',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_pregnancy_id` (`pregnancy_id`),
  KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='新生儿信息表';

-- ====================================
-- 17. 喂养记录表
-- 功能：记录新生儿喂养情况
-- ====================================
CREATE TABLE `feeding_record` (
  `id` BIGINT NOT NULL COMMENT '喂养记录ID（主键）',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `baby_id` BIGINT NOT NULL COMMENT '新生儿ID',
  `record_date` DATE NOT NULL COMMENT '记录日期',
  `record_time` TIME NOT NULL COMMENT '记录时间',
  `feeding_type` TINYINT NOT NULL COMMENT '喂养类型：1-母乳，2-配方奶，3-混合喂养',
  `feeding_side` VARCHAR(20) DEFAULT NULL COMMENT '喂养侧（母乳）：左侧、右侧、双侧',
  `duration` INT DEFAULT NULL COMMENT '喂养时长（分钟）',
  `milk_amount` INT DEFAULT NULL COMMENT '奶量（毫升）',
  `remarks` TEXT DEFAULT NULL COMMENT '备注',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_baby_id` (`baby_id`),
  KEY `idx_record_date` (`record_date`),
  KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='喂养记录表';

-- ====================================
-- 18. 疫苗接种表
-- 功能：记录新生儿疫苗接种情况
-- ====================================
CREATE TABLE `vaccination_record` (
  `id` BIGINT NOT NULL COMMENT '疫苗接种ID（主键）',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `baby_id` BIGINT NOT NULL COMMENT '新生儿ID',
  `vaccine_name` VARCHAR(100) NOT NULL COMMENT '疫苗名称',
  `vaccine_type` VARCHAR(50) DEFAULT NULL COMMENT '疫苗类型（一类/二类）',
  `dose_number` INT DEFAULT NULL COMMENT '接种剂次',
  `vaccination_date` DATE NOT NULL COMMENT '接种日期',
  `hospital_name` VARCHAR(100) DEFAULT NULL COMMENT '接种医院',
  `batch_number` VARCHAR(50) DEFAULT NULL COMMENT '疫苗批号',
  `next_vaccination_date` DATE DEFAULT NULL COMMENT '下次接种日期',
  `adverse_reaction` TEXT DEFAULT NULL COMMENT '不良反应',
  `remarks` TEXT DEFAULT NULL COMMENT '备注',
  `deleted` TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_baby_id` (`baby_id`),
  KEY `idx_vaccination_date` (`vaccination_date`),
  KEY `idx_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='疫苗接种表';
