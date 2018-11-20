-- 2018-11-14 银联
create table union_product_detail(
	`id` int(11) NOT NULL AUTO_INCREMENT,
  `mer_id` varchar(36) NOT NULL comment '商户号码',
  `order_id` varchar(40) NOT NULL COMMENT '商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	',
	`access_type` varchar(2) NOT NULL comment '接入类型，商户接入填0 ，不需修改（0：直连商户， 1： 收单机构 2：平台商户）',
  `txn_time` varchar(36) NOT NULL COMMENT '订单发送时间，取系统时间，格式为YYYYMMDDhhmmss，必须取当前时间，否则会报txnTime无效',
  `txn_amt` varchar(36) NOT NULL COMMENT '交易金额 单位为分，不能带小数点',
	`currency_code` varchar(10) NOT NULL COMMENT '境内商户固定 156 人民币',
	`sign_method` varchar(2) NOT NULL COMMENT '签名方法',
	`txn_type` varchar(2) NOT NULL COMMENT '交易类型 01:消费',
	`txn_sub_type` varchar(2) NOT NULL COMMENT '交易子类 07：申请消费二维码',
	`biz_type` varchar(10) NOT NULL COMMENT '填写000000',
	`channel_type` varchar(2) NOT NULL COMMENT '渠道类型 08手机',
	`qr_code` varchar(200) DEFAULT NULL COMMENT '二维码支付地址',
	`back_url` varchar(200) NOT NULL COMMENT '异步通知地址',
	`front_url` varchar(200) default NULL COMMENT '前端响应跳转地址',
	`reqReserved` varchar(200) default NULL COMMENT '商户自定义保留域，交易应答时会原样返回（url：如 http:www.baidu.com)',
  PRIMARY KEY (`id`)
) engine = innodb default charset=utf8 comment '银联产品详情表';

create table union_pay(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`query_id` varchar(36) NOT NULL comment '消费交易的流水号，供后续查询用',
  `mer_id` varchar(36) NOT NULL comment '商户号码',
  `order_id` varchar(40) NOT NULL COMMENT '商户订单号，8-40位数字字母，不能含“-”或“_”，可以自行定制规则	',
  `trace_time` varchar(36) NOT NULL COMMENT 'traceTime，格式为YYYYMMDDhhmmss',
	`status` varchar(2) NOT NULL COMMENT '0 失败， 1 成功',
  PRIMARY KEY (`id`)
) engine = innodb default charset=utf8 comment '银联支付记录';

-- 2018-11-19
create table union_merchant(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`app_id` int NOT NULL comment '应用id（暂时）',
	`number` varchar(36) NOT NULL comment '商户号',
  `make_time` datetime default null COMMENT '创建时间',
	`valid_flag` varchar(2) default 'Y' COMMENT 'Y 有效， N 无效',
	PRIMARY KEY (`id`)
)engine = innodb default charset=utf8 comment '银联商户';



INSERT INTO `ecampus_new`.`union_merchant` (`id`, `app_id`, `number`, `make_time`, `valid_flag`) 
VALUES ('1', '1', '777290058110048', '2018-11-19 13:54:31', 'Y');






