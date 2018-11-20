-- 2018-11-14 ����
create table union_product_detail(
	`id` int(11) NOT NULL AUTO_INCREMENT,
  `mer_id` varchar(36) NOT NULL comment '�̻�����',
  `order_id` varchar(40) NOT NULL COMMENT '�̻������ţ�8-40λ������ĸ�����ܺ���-����_�����������ж��ƹ���	',
	`access_type` varchar(2) NOT NULL comment '�������ͣ��̻�������0 �������޸ģ�0��ֱ���̻��� 1�� �յ����� 2��ƽ̨�̻���',
  `txn_time` varchar(36) NOT NULL COMMENT '��������ʱ�䣬ȡϵͳʱ�䣬��ʽΪYYYYMMDDhhmmss������ȡ��ǰʱ�䣬����ᱨtxnTime��Ч',
  `txn_amt` varchar(36) NOT NULL COMMENT '���׽�� ��λΪ�֣����ܴ�С����',
	`currency_code` varchar(10) NOT NULL COMMENT '�����̻��̶� 156 �����',
	`sign_method` varchar(2) NOT NULL COMMENT 'ǩ������',
	`txn_type` varchar(2) NOT NULL COMMENT '�������� 01:����',
	`txn_sub_type` varchar(2) NOT NULL COMMENT '�������� 07���������Ѷ�ά��',
	`biz_type` varchar(10) NOT NULL COMMENT '��д000000',
	`channel_type` varchar(2) NOT NULL COMMENT '�������� 08�ֻ�',
	`qr_code` varchar(200) DEFAULT NULL COMMENT '��ά��֧����ַ',
	`back_url` varchar(200) NOT NULL COMMENT '�첽֪ͨ��ַ',
	`front_url` varchar(200) default NULL COMMENT 'ǰ����Ӧ��ת��ַ',
	`reqReserved` varchar(200) default NULL COMMENT '�̻��Զ��屣���򣬽���Ӧ��ʱ��ԭ�����أ�url���� http:www.baidu.com)',
  PRIMARY KEY (`id`)
) engine = innodb default charset=utf8 comment '������Ʒ�����';

create table union_pay(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`query_id` varchar(36) NOT NULL comment '���ѽ��׵���ˮ�ţ���������ѯ��',
  `mer_id` varchar(36) NOT NULL comment '�̻�����',
  `order_id` varchar(40) NOT NULL COMMENT '�̻������ţ�8-40λ������ĸ�����ܺ���-����_�����������ж��ƹ���	',
  `trace_time` varchar(36) NOT NULL COMMENT 'traceTime����ʽΪYYYYMMDDhhmmss',
	`status` varchar(2) NOT NULL COMMENT '0 ʧ�ܣ� 1 �ɹ�',
  PRIMARY KEY (`id`)
) engine = innodb default charset=utf8 comment '����֧����¼';

-- 2018-11-19
create table union_merchant(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`app_id` int NOT NULL comment 'Ӧ��id����ʱ��',
	`number` varchar(36) NOT NULL comment '�̻���',
  `make_time` datetime default null COMMENT '����ʱ��',
	`valid_flag` varchar(2) default 'Y' COMMENT 'Y ��Ч�� N ��Ч',
	PRIMARY KEY (`id`)
)engine = innodb default charset=utf8 comment '�����̻�';



INSERT INTO `ecampus_new`.`union_merchant` (`id`, `app_id`, `number`, `make_time`, `valid_flag`) 
VALUES ('1', '1', '777290058110048', '2018-11-19 13:54:31', 'Y');






