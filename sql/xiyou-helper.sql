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


