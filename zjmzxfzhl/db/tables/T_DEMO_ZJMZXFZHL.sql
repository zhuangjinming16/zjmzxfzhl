/**【T_DEMO_ZJMZXFZHL】开始**/
drop table if exists T_DEMO_ZJMZXFZHL;
CREATE TABLE T_DEMO_ZJMZXFZHL
(
	ZJMZXFZHL_ID varchar(32) NOT NULL COMMENT 'ID',
	ZJMZXFZHL_NAME varchar(32) NOT NULL COMMENT '名称',
	ZJMZXFZHL_CODE_INFO varchar(1) NULL COMMENT '代码信息',
	ZJMZXFZHL_DATE date NULL COMMENT '日期格式',
	ZJMZXFZHL_DATETIME datetime NULL COMMENT '时间格式',
	ORG_ID varchar(32) NULL COMMENT '所属机构ID',
	ZJMZXFZHL_DBPARAM1 varchar(3) NULL COMMENT '参数1',
	ZJMZXFZHL_DBPARAM2 int(3) NULL COMMENT '参数2',
	FILTER_OPERATOR_EQ varchar(3) NULL COMMENT '等于',
	FILTER_OPERATOR_NE int(3) NULL COMMENT '不等于',
	FILTER_OPERATOR_LT float(5,2) NULL COMMENT '小于',
	FILTER_OPERATOR_LE int(3) NULL COMMENT '小于等于',
	FILTER_OPERATOR_GT int(3) NULL COMMENT '大于',
	FILTER_OPERATOR_GE float(5,2) NULL COMMENT '大于等于',
	FILTER_OPERATOR_IN varchar(3) NULL COMMENT 'IN',
	FILTER_OPERATOR_BETWEEN int(3) NULL COMMENT 'BETWEEN',
	FILTER_OPERATOR_LIKE varchar(32) NULL COMMENT '模糊',
	FILTER_OPERATOR_LIKE_LEFT varchar(32) NULL COMMENT '左模糊',
	FILTER_OPERATOR_LIKE_RIGHT varchar(32) NULL COMMENT '右模糊',
	CREATE_BY varchar(32) NULL COMMENT '创建人',
	CREATE_DATE date NULL COMMENT '创建日期',
	CREATE_TIME datetime NULL COMMENT '创建时间',
	UPDATE_BY varchar(32) NULL COMMENT '修改人',
	UPDATE_DATE date NULL COMMENT '修改日期',
	UPDATE_TIME datetime NULL COMMENT '修改时间',
	PRIMARY KEY (ZJMZXFZHL_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='DEMO01_开发示例';

/**【T_DEMO_ZJMZXFZHL】结束**/
--INSERT INTO T_SYS_MENU VALUES ('demoZjmzxfzhl', '开发示例01', 'demo', 'list', 'zjmzxfzhl', 'demo:zjmzxfzhl:list,demo:zjmzxfzhl:listByQw', 'views/demo/zjmzxfzhl/index', NULL, '0', '1', 'DemoZjmzxfzhl', '1', '0', '1', '19100', NULL, NULL, NULL, NULL, NULL, NULL);
--INSERT INTO T_SYS_FUNC VALUES ('demoZjmzxfzhl-1', '新增', 'demoZjmzxfzhl', 'demo:zjmzxfzhl:save', '', '1', NULL, NULL, NULL, NULL, NULL, NULL);
--INSERT INTO T_SYS_FUNC VALUES ('demoZjmzxfzhl-2', '修改', 'demoZjmzxfzhl', 'demo:zjmzxfzhl:update', '', '2', NULL, NULL, NULL, NULL, NULL, NULL);
--INSERT INTO T_SYS_FUNC VALUES ('demoZjmzxfzhl-3', '删除', 'demoZjmzxfzhl', 'demo:zjmzxfzhl:delete', '', '3', NULL, NULL, NULL, NULL, NULL, NULL);

