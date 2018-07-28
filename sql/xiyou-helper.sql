USE xiyou_helper;

CREATE TABLE `test_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
);

# 用户信息表
CREATE TABLE user_message (
  sid VARCHAR(8) PRIMARY KEY COMMENT 'uid=学号',
  name VARCHAR(40) NOT NULL COMMENT '姓名',
  gender SMALLINT NOT NULL COMMENT '0 女 1 男',
  college VARCHAR(40) NOT NULL COMMENT '学院',
  major VARCHAR(40) NOT NULL COMMENT '专业',
  adclass VARCHAR(20) NOT NULL COMMENT '行政班级',
  level VARCHAR(4) NOT NULL COMMENT '级别',
  education VARCHAR(20) NOT NULL COMMENT '学历'
);

# 密码表
CREATE TABLE user_password (
  sid VARCHAR(8) PRIMARY KEY COMMENT 'uid=学号',
  edu_system VARCHAR(20) DEFAULT NULL COMMENT '教务系统密码',
  book_system VARCHAR(20) DEFAULT NULL COMMENT '图书馆密码'
) engine = InnoDB charset = utf8;


#学生成绩表
CREATE TABLE `user_achievement` (
  `num` VARCHAR(10) NOT NULL,
  `school_year` VARCHAR(10) NOT NULL,
  `semester` VARCHAR(2) NOT NULL,
  `classname` VARCHAR(100) NOT NULL,
  `achievement` VARCHAR(10) DEFAULT NULL,
  `ordinary` VARCHAR(10) DEFAULT NULL,
  `point` VARCHAR(10) DEFAULT NULL,
  `nature` VARCHAR(100) DEFAULT NULL,
  `credit` VARCHAR(10) DEFAULT NULL,
  `finalexam` VARCHAR(10) DEFAULT NULL,
  UNIQUE KEY `uc_PersonID` (`num`,`school_year`,`semester`,`classname`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


# 培养计划
CREATE TABLE `train_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `major` varchar(40) NOT NULL,
  `level` varchar(4) NOT NULL,
  `class_code` varchar(9) NOT NULL,
  `class_name` varchar(40) NOT NULL,
  `credit` double NOT NULL,
  `class_character` varchar(20) NOT NULL,
  `exam_type` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 培养计划状态
CREATE TABLE `train_plan_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `major` varchar(20) NOT NULL,
  `level` varchar(4) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `train_plan` (`major`, `level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

