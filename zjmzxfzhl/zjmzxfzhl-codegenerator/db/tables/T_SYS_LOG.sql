/**【T_SYS_LOG】开始**/
drop table if exists T_SYS_LOG;
CREATE TABLE T_SYS_LOG
(
    LOG_ID varchar(32) NOT NULL COMMENT 'UUID',
    LOG_TYPE varchar(2) NOT NULL COMMENT '日志类型',
    LOG_CONTENT varchar(100) NOT NULL COMMENT '日志内容',
    OPERATE_TYPE varchar(2) NULL COMMENT '操作类型',
    USER_ID varchar(32) NULL COMMENT '操作用户',
    USER_NAME varchar(100) NULL COMMENT '操作用户姓名',
    IP varchar(100) NULL COMMENT 'IP地址',
    METHOD varchar(255) NULL COMMENT '请求方法',
    REQUEST_URL varchar(255) NULL COMMENT '请求路径',
    REQUEST_PARAM text NULL COMMENT '请求参数',
    REQUEST_TYPE varchar(10) NULL COMMENT '请求类型',
    OPERATE_RESULT text NULL COMMENT '操作结果',
    COST_TIME bigint(20) NULL COMMENT '耗时',
    CREATE_BY varchar(32) NULL COMMENT '创建人',
    CREATE_DATE date NULL COMMENT '创建日期',
    CREATE_TIME datetime NULL COMMENT '创建时间',
    UPDATE_BY varchar(32) NULL COMMENT '修改人',
    UPDATE_DATE date NULL COMMENT '修改日期',
    UPDATE_TIME datetime NULL COMMENT '修改时间',
    PRIMARY KEY (LOG_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='SYS12_系统日志表';

/**【T_SYS_LOG】结束**/
--INSERT INTO T_SYS_MENU VALUES ('sysLog', 'XX功能', 'sys', 'list', 'log', 'sys:log:list', 'views/sys/log/index', NULL, '0', '1', 'SysLog', '1', '0', '1', '19100', NULL, NULL, NULL, NULL, NULL, NULL);
--INSERT INTO T_SYS_FUNC VALUES ('sysLog-1', '新增', 'sysLog', 'sys:log:save', '', '1', NULL, NULL, NULL, NULL, NULL, NULL);
--INSERT INTO T_SYS_FUNC VALUES ('sysLog-2', '修改', 'sysLog', 'sys:log:update', '', '2', NULL, NULL, NULL, NULL, NULL, NULL);
--INSERT INTO T_SYS_FUNC VALUES ('sysLog-3', '删除', 'sysLog', 'sys:log:delete', '', '3', NULL, NULL, NULL, NULL, NULL, NULL);

