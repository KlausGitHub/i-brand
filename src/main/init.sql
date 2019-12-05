
CREATE TABLE `t_customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `login_name` varchar(64) DEFAULT NULL COMMENT '登录名',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `vip_flag` char(1) DEFAULT NULL COMMENT '会员标识 Y:会员 N:非会员',
  `balance` decimal(10,2) DEFAULT NULL COMMENT '账户余额',
  `head_logo` varchar(256) DEFAULT NULL COMMENT '头像地址',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `disable_flag` char(1) DEFAULT NULL COMMENT '禁用标识 Y：禁用 N：不禁用',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `activation_code` varchar(64) DEFAULT NULL COMMENT '激活码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_customer_login_name` (`login_name`),
  UNIQUE KEY `t_customer_mobile` (`mobile`),
  UNIQUE KEY `t_customer_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8 COMMENT='客户表';


create table t_brand(
	id BIGINT not null PRIMARY KEY AUTO_INCREMENT,
	customer_id BIGINT NOT NULL DEFAULT -1,
	name varchar(256) COMMENT '名称',
	price DECIMAL(10,2)  COMMENT '价格',
	service_price DECIMAL(10,2)  COMMENT '服务费',
	category_fir varchar(20) COMMENT '商标一级分类',
	category_sec varchar(20) COMMENT '商标二级分类',
	banner_url varchar(256) COMMENT '头图',
	first_notice_no varchar(20) COMMENT '初审公告期号',
	first_notice_time DATETIME COMMENT '初审公告日期',
	apply_range varchar(100) COMMENT '适用范围',
	register_notice_no varchar(20) COMMENT '注册公告期号',
	register_notice_time DATETIME COMMENT '注册公告日期',
	inland_flag char(1) COMMENT 'Y:国内商标 N:国外商标',
	area_code varchar(20) COMMENT '商标归属区域 国内商标存省份、国际商标存国家',
	later_appoint_time DATETIME COMMENT '后期指定日期',
	expire_time DATETIME COMMENT '到期日期',
	priority_time DATETIME COMMENT '优先权日期',
	internation_register_time DATETIME COMMENT '国际注册日期',
	deadlineYear varchar(10) COMMENT '专用期限',
	public_flag char(1) COMMENT '是否共用商标',
	show_flag char(1) COMMENT '是否展示 Y：展示 N：不展示',
	delete_flag char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除 Y：删除 N：未删除',
	auditor BIGINT COMMENT '审核人',
	audit_time DATETIME COMMENT '审核时间',
	audit_desc VARCHAR(256) COMMENT '审核描述',
	vip_flag char(1) COMMENT '明标标识 Y:明标 N:非明标',
	create_time datetime DEFAULT NULL COMMENT '创建时间',
  modify_time datetime DEFAULT NULL COMMENT '修改时间',
	INDEX (customer_id)
) COMMENT '商标表';

-- INSERT INTO `ibrand`.`t_customer` (`id`, `name`, `login_name`, `mobile`, `email`, `password`, `vip_flag`, `balance`, `head_logo`, `create_time`, `disable_flag`, `last_login_time`) VALUES ('1', 'test', 'test', '13322221111', 'test@qq.com', 'e10adc3949ba59abbe56e057f20f883e', 'N', '0.00', '', '2019-05-12 16:08:17', 'N', '2019-05-12 16:08:50');

create table t_patent(
	id BIGINT not null PRIMARY KEY AUTO_INCREMENT,
	customer_id BIGINT NOT NULL DEFAULT -1 ,
	name varchar(256) COMMENT '名称',
	price DECIMAL(10,2)  COMMENT '价格',
	service_price DECIMAL(10,2)  COMMENT '服务费',
	banner_url varchar(256) COMMENT '头图',
	apply_no VARCHAR(50) COMMENT '申请号',
	apply_time DATETIME COMMENT '申请时间',
	category_fir VARCHAR(20) COMMENT '专利一级分类',
	category_sec VARCHAR(20) COMMENT '专利二级分类',
	transaction_type VARCHAR(20) COMMENT '交易类型',
	show_flag char(1) COMMENT '是否展示 Y：展示 N：不展示',
	delete_flag char(1) NOT NULL DEFAULT 'N' COMMENT '是否删除 Y：删除 N：未删除',
	auditor BIGINT COMMENT '审核人',
	audit_time DATETIME COMMENT '审核时间',
	audit_desc VARCHAR(256) COMMENT '审核描述',
	create_time datetime DEFAULT NULL COMMENT '创建时间',
  modify_time datetime DEFAULT NULL COMMENT '修改时间',
	INDEX (customer_id)
) COMMENT '专利表';

CREATE TABLE `t_apply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_id` bigint(20) NOT NULL COMMENT '客户id',
  `apply_type` tinyint(4) NOT NULL COMMENT '申请类型1-商标2-专利3-服务4-会员',
  `target_id` bigint(20) DEFAULT NULL COMMENT '申请的目标id',
  `status` tinyint(4) DEFAULT NULL COMMENT '申请状态0已申请1已成交2已拒绝',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '申请金额',
  `description` varchar(1024) DEFAULT '' COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` char(1) DEFAULT NULL COMMENT '逻辑删除标记Y已删除N未删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='客户服务申请表';


create table t_service(
	id BIGINT not null PRIMARY KEY AUTO_INCREMENT,
	name varchar(256) COMMENT '名称',
	price DECIMAL(10,2)  COMMENT '价格',
	total BIGINT DEFAULT 0 COMMENT '成交数',
	banner_url varchar(256) COMMENT '头图',
	content text COMMENT '内容',
	create_time datetime DEFAULT NULL COMMENT '创建时间',
  modify_time datetime DEFAULT NULL COMMENT '修改时间'
) COMMENT '服务表';


CREATE TABLE `t_config` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`key` varchar(128)  DEFAULT '' COMMENT 'key',
`value` text  DEFAULT NULL COMMENT 'value',
`create_time` datetime DEFAULT NULL COMMENT '创建时间',
`modify_time` datetime DEFAULT NULL COMMENT '修改时间',
`is_deleted` char(1)  DEFAULT '' COMMENT '逻辑删除标记Y是N否',
`creator` varchar(64)  DEFAULT NULL COMMENT '创建人',
`modifier` varchar(64)  DEFAULT NULL COMMENT '修改人',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='配置表';

alter table t_service add type tinyint(4) not Null
	COMMENT '类型：1：服务，2：商标知识，3：商标问问，4,：专利新闻，5：公司动态，6：关于我们，7：服务'  AFTER `name`;

alter table t_brand add status tinyint(4) DEFAULT NULL
	COMMENT '0:出售中，1：交易中心，2：已出售'  AFTER `service_price`;

alter table t_patent add status tinyint(4) DEFAULT NULL
	COMMENT '0:出售中，1：交易中心，2：已出售'  AFTER `service_price`;