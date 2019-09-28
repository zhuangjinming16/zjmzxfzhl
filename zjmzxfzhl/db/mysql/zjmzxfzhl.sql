/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : zjmzxfzhl

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2019-09-28 22:14:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_app_user
-- ----------------------------
DROP TABLE IF EXISTS `t_app_user`;
CREATE TABLE `t_app_user` (
  `USER_ID` varchar(32) NOT NULL COMMENT '用户ID',
  `USER_NAME` varchar(100) NOT NULL COMMENT '用户姓名',
  `MOBILE` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `PASSWORD` varchar(100) NOT NULL COMMENT '密码',
  `SALT` varchar(8) DEFAULT NULL COMMENT '密码盐',
  `SEX` varchar(1) DEFAULT NULL COMMENT '性别',
  `ID_CARD_NO` varchar(20) DEFAULT NULL COMMENT '身份证号码',
  `EMAIL` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `CREATE_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATE_DATE` date DEFAULT NULL COMMENT '创建日期',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(32) DEFAULT NULL COMMENT '修改人',
  `UPDATE_DATE` date DEFAULT NULL COMMENT '修改日期',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='APP01_用户表';

-- ----------------------------
-- Records of t_app_user
-- ----------------------------
INSERT INTO `t_app_user` VALUES ('18888888888', '18888888888', '18888888888', '3382d5429c508dcee1257606f5fc19ecc04a3c916a43217cff813e807288cba6', 'QH59CG9h', null, null, null, '18888888888', '2019-08-25', '2019-08-25 21:53:51', null, null, null);

-- ----------------------------
-- Table structure for t_sys_code_info
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_code_info`;
CREATE TABLE `t_sys_code_info` (
  `CODE_INFO_ID` varchar(32) NOT NULL COMMENT '代码信息ID',
  `CODE_TYPE_ID` varchar(32) NOT NULL COMMENT '代码类别ID',
  `VALUE` varchar(100) NOT NULL COMMENT '下拉框值',
  `CONTENT` varchar(100) NOT NULL COMMENT '下拉框内容',
  `PARENT_VALUE` varchar(100) DEFAULT NULL COMMENT '上级联动下拉框值',
  `SORT_NO` int(4) NOT NULL COMMENT '排序号',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '备注',
  `IS_OK` varchar(1) DEFAULT NULL COMMENT '是否显示',
  `CREATE_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATE_DATE` date DEFAULT NULL COMMENT '创建日期',
  `CREATE_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(32) DEFAULT NULL COMMENT '修改人',
  `UPDATE_DATE` date DEFAULT NULL COMMENT '修改日期',
  `UPDATE_TIME` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`CODE_INFO_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='SYS03_代码信息表';

-- ----------------------------
-- Records of t_sys_code_info
-- ----------------------------
INSERT INTO `t_sys_code_info` VALUES ('entityType-1', 'entityType', '1', '角色', '', '1', '', '1', 'admin', '2019-09-01', '2019-09-01 11:40:55', null, null, null);
INSERT INTO `t_sys_code_info` VALUES ('entityType-2', 'entityType', '2', '用户', '', '2', '', '1', 'admin', '2019-09-01', '2019-09-01 11:41:12', null, null, null);
INSERT INTO `t_sys_code_info` VALUES ('logType-1', 'logType', '1', '登录日志', '', '1', '', '1', 'user2', '2019-09-03', '2019-09-03 00:52:34', null, null, null);
INSERT INTO `t_sys_code_info` VALUES ('logType-2', 'logType', '2', '操作日志', '', '2', '', '1', 'user2', '2019-09-03', '2019-09-03 00:52:56', null, null, null);
INSERT INTO `t_sys_code_info` VALUES ('logType-3', 'logType', '3', '定时任务', '', '3', '', '1', 'user2', '2019-09-03', '2019-09-03 00:52:09', null, null, null);
INSERT INTO `t_sys_code_info` VALUES ('orgLevel-1', 'orgLevel', '1', '一级', null, '1', null, '1', null, null, null, null, null, null);
INSERT INTO `t_sys_code_info` VALUES ('orgLevel-2', 'orgLevel', '2', '二级', '', '2', '', '1', null, null, null, null, null, null);
INSERT INTO `t_sys_code_info` VALUES ('orgLevel-3', 'orgLevel', '3', '三级', '', '3', '', '1', null, null, null, null, null, null);
INSERT INTO `t_sys_code_info` VALUES ('orgLevel-4', 'orgLevel', '4', '四级', '', '4', '', '1', 'admin', '2019-09-07', '2019-09-07 21:09:18', null, null, null);
INSERT INTO `t_sys_code_info` VALUES ('orgType-1', 'orgType', '1', '普通机构', null, '1', null, '1', null, null, null, null, null, null);
INSERT INTO `t_sys_code_info` VALUES ('orgType-2', 'orgType', '2', '部门', '', '2', '', '1', null, null, null, null, null, null);
INSERT INTO `t_sys_code_info` VALUES ('sourceStrategy-1', 'sourceStrategy', '1', '用户录入', '', '1', '', '1', 'admin', '2019-09-01', '2019-09-01 13:58:04', null, null, null);
INSERT INTO `t_sys_code_info` VALUES ('sourceStrategy-2', 'sourceStrategy', '2', '系统提供', '', '2', '', '1', 'admin', '2019-09-01', '2019-09-01 11:40:19', null, null, null);
INSERT INTO `t_sys_code_info` VALUES ('userSex-1', 'userSex', '1', '男', '', '1', '', '1', null, null, null, null, null, null);
INSERT INTO `t_sys_code_info` VALUES ('userSex-2', 'userSex', '2', '女', '', '2', '', '1', null, null, null, null, null, null);
INSERT INTO `t_sys_code_info` VALUES ('userSex-3', 'userSex', '3', '未知', '', '3', '', '1', null, null, null, null, null, null);
INSERT INTO `t_sys_code_info` VALUES ('userStatus-1', 'userStatus', '1', '在职', '', '1', '', '1', null, null, null, null, null, null);
INSERT INTO `t_sys_code_info` VALUES ('userStatus-2', 'userStatus', '2', '离职', '', '2', '', '1', null, null, null, null, null, null);
INSERT INTO `t_sys_code_info` VALUES ('yesOrNo-0', 'yesOrNo', '0', '否', '', '2', '', '', null, null, null, null, null, null);
INSERT INTO `t_sys_code_info` VALUES ('yesOrNo-1', 'yesOrNo', '1', '是', '', '1', '', '', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for t_sys_code_type
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_code_type`;
CREATE TABLE `t_sys_code_type` (
  `CODE_TYPE_ID` varchar(32) NOT NULL COMMENT '代码类别ID',
  `CODE_TYPE_NAME` varchar(100) NOT NULL COMMENT '代码类别名称',
  `SORT_NO` int(4) NOT NULL COMMENT '排序号',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '备注',
  `CREATE_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATE_DATE` date DEFAULT NULL COMMENT '创建日期',
  `CREATE_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(32) DEFAULT NULL COMMENT '修改人',
  `UPDATE_DATE` date DEFAULT NULL COMMENT '修改日期',
  `UPDATE_TIME` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`CODE_TYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='SYS02_代码类别表';

-- ----------------------------
-- Records of t_sys_code_type
-- ----------------------------
INSERT INTO `t_sys_code_type` VALUES ('entityType', '实体类型', '5', '', 'admin', '2019-09-01', '2019-09-01 11:40:19', null, null, null);
INSERT INTO `t_sys_code_type` VALUES ('logType', '日志类型', '7', '', 'user2', '2019-09-03', '2019-09-03 00:52:09', null, null, null);
INSERT INTO `t_sys_code_type` VALUES ('orgLevel', '机构级别', '3', null, null, null, null, 'admin', '2019-09-01', '2019-09-01 17:06:53');
INSERT INTO `t_sys_code_type` VALUES ('orgType', '机构类型', '4', null, null, null, null, 'admin', '2019-09-01', '2019-09-01 17:01:27');
INSERT INTO `t_sys_code_type` VALUES ('sourceStrategy', '数据源策略', '6', '', 'admin', '2019-09-01', '2019-09-01 13:04:14', 'admin', '2019-09-01', '2019-09-01 17:06:49');
INSERT INTO `t_sys_code_type` VALUES ('userSex', '用户性别', '2', '', null, null, null, 'admin', '2019-08-21', '2019-08-21 23:27:09');
INSERT INTO `t_sys_code_type` VALUES ('yesOrNo', '是否', '1', null, null, null, null, 'admin', '2019-09-08', '2019-09-08 14:34:34');

-- ----------------------------
-- Table structure for t_sys_config
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_config`;
CREATE TABLE `t_sys_config` (
  `CONFIG_ID` varchar(32) NOT NULL COMMENT '系统参数ID',
  `CONFIG_NAME` varchar(100) NOT NULL COMMENT '系统参数名称',
  `CONFIG_VALUE` varchar(255) NOT NULL COMMENT '系统参数值',
  `SORT_NO` int(4) NOT NULL COMMENT '排序号',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '备注',
  `CREATE_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATE_DATE` date DEFAULT NULL COMMENT '创建日期',
  `CREATE_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(32) DEFAULT NULL COMMENT '修改人',
  `UPDATE_DATE` date DEFAULT NULL COMMENT '修改日期',
  `UPDATE_TIME` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`CONFIG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='SYS01_系统参数表';

-- ----------------------------
-- Records of t_sys_config
-- ----------------------------
INSERT INTO `t_sys_config` VALUES ('defaultPassword', '新建用户默认密码', '1', '1', '', 'admin', '2019-09-11', '2019-09-11 23:03:25', 'admin', '2019-09-21', '2019-09-21 12:22:34');

-- ----------------------------
-- Table structure for t_sys_data_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_data_permission`;
CREATE TABLE `t_sys_data_permission` (
  `DATA_PERMISSION_ID` varchar(32) NOT NULL COMMENT '数据权限ID',
  `DATA_PERMISSION_NAME` varchar(100) DEFAULT NULL COMMENT '数据权限名称',
  `ENTITY_TYPE` varchar(1) DEFAULT NULL COMMENT '实体类型(1-角色2-用户)',
  `ENTITY_ID` varchar(32) DEFAULT NULL COMMENT '实体ID(用户ID或角色ID)',
  `TABLE_NAME` varchar(100) DEFAULT NULL COMMENT '业务表名',
  `CLASS_NAME` varchar(255) DEFAULT NULL COMMENT '类名',
  `COLUMN_NAME` varchar(100) DEFAULT NULL COMMENT '属性名',
  `SOURCE_STRATEGY` varchar(1) DEFAULT NULL COMMENT '数据源策略(1-用户录入2-系统提供算法)',
  `OPERATE` varchar(100) DEFAULT NULL COMMENT '查询表达式',
  `VALUE` varchar(255) DEFAULT NULL COMMENT '查询条件',
  `SOURCE_PROVIDER` varchar(255) DEFAULT NULL COMMENT '系统提供的数据源class类',
  `SOURCE_PROVIDER_PARAMS` varchar(255) DEFAULT NULL COMMENT '系统提供的数据源class类参数注入JSON格式',
  `CREATE_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATE_DATE` date DEFAULT NULL COMMENT '创建日期',
  `CREATE_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(32) DEFAULT NULL COMMENT '修改人',
  `UPDATE_DATE` date DEFAULT NULL COMMENT '修改日期',
  `UPDATE_TIME` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`DATA_PERMISSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='SYS09_数据权限表';

-- ----------------------------
-- Records of t_sys_data_permission
-- ----------------------------
INSERT INTO `t_sys_data_permission` VALUES ('1', '查询在职用户', '1', 'queryRole', 'T_SYS_USER', 'com.zjmzxfzhl.modules.sys.entity.SysUser', 'STATUS', '1', '=', '1', null, null, null, null, null, 'admin', '2019-09-15', '2019-09-15 16:53:39');
INSERT INTO `t_sys_data_permission` VALUES ('2', '查询男性用户', '1', 'queryRole', 'T_SYS_USER', 'com.zjmzxfzhl.modules.sys.entity.SysUser', 'SEX', '1', '=', '1', '', '', 'admin', '2019-09-15', '2019-09-15 18:15:52', null, null, null);

-- ----------------------------
-- Table structure for t_sys_func
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_func`;
CREATE TABLE `t_sys_func` (
  `FUNC_ID` varchar(32) NOT NULL COMMENT '功能ID',
  `FUNC_NAME` varchar(100) NOT NULL COMMENT '功能名称',
  `MENU_ID` varchar(32) NOT NULL COMMENT '菜单ID',
  `FUNC_PERMISSIONS` varchar(255) DEFAULT '' COMMENT '可访问路径',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '备注',
  `SORT_NO` int(4) DEFAULT NULL COMMENT '排序号',
  `CREATE_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATE_DATE` date DEFAULT NULL COMMENT '创建日期',
  `CREATE_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(32) DEFAULT NULL COMMENT '修改人',
  `UPDATE_DATE` date DEFAULT NULL COMMENT '修改日期',
  `UPDATE_TIME` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`FUNC_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='SYS05_功能表';

-- ----------------------------
-- Records of t_sys_func
-- ----------------------------
INSERT INTO `t_sys_func` VALUES ('sysCodeInfo-1', '新增', 'sysCodeInfo', 'sys:codeInfo:save', '', '1', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysCodeInfo-2', '修改', 'sysCodeInfo', 'sys:codeInfo:update', '', '2', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysCodeInfo-3', '删除', 'sysCodeInfo', 'sys:codeInfo:delete', '', '3', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysCodeType-1', '新增', 'sysCodeType', 'sys:codeType:save', '', '1', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysCodeType-2', '修改', 'sysCodeType', 'sys:codeType:update', '', '2', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysCodeType-3', '删除', 'sysCodeType', 'sys:codeType:delete', '', '3', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysConfig-1', '新增', 'sysConfig', 'sys:config:save', '', '1', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysConfig-2', '修改', 'sysConfig', 'sys:config:update', '', '2', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysConfig-3', '删除', 'sysConfig', 'sys:config:delete', '', '3', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysDataPermission-1', '新增', 'sysDataPermission', 'sys:dataPermission:save', '', '1', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysDataPermission-2', '修改', 'sysDataPermission', 'sys:dataPermission:update', '', '2', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysDataPermission-3', '删除', 'sysDataPermission', 'sys:dataPermission:delete', '', '3', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysFunc-1', '新增', 'sysFunc', 'sys:func:save', '', '1', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysFunc-2', '修改', 'sysFunc', 'sys:func:update', '', '2', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysFunc-3', '删除', 'sysFunc', 'sys:func:delete', '', '3', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysMenu-1', '新增', 'sysMenu', 'sys:menu:save', '', '1', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysMenu-2', '修改', 'sysMenu', 'sys:menu:update', '', '2', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysMenu-3', '删除', 'sysMenu', 'sys:menu:delete', '', '3', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysOrg-1', '新增', 'sysOrg', 'sys:org:save', '', '1', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysOrg-2', '修改', 'sysOrg', 'sys:org:update', '', '2', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysOrg-3', '删除', 'sysOrg', 'sys:org:delete', '', '3', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysRole-1', '新增', 'sysRole', 'sys:role:save', '', '1', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysRole-2', '修改', 'sysRole', 'sys:role:update', '', '2', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysRole-3', '删除', 'sysRole', 'sys:role:delete', '', '3', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysRole-4', '角色授权', 'sysRole', 'sys:role:getRolePermissions,sys:role:saveRolePermissions', '', '4', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysRole-5', '分配用户', 'sysRole', 'sys:role:getRoleUser,sys:role:saveRoleUsers,sys:role:deleteRoleUsers', '', '5', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('sysUser-1', '新增', 'sysUser', 'sys:user:save,sys:org:getTreeData', '', '1', null, null, null, 'admin', '2019-09-08', '2019-09-08 17:51:12');
INSERT INTO `t_sys_func` VALUES ('sysUser-2', '修改', 'sysUser', 'sys:user:update,sys:org:getTreeData', '', '2', null, null, null, 'admin', '2019-09-08', '2019-09-08 17:51:19');
INSERT INTO `t_sys_func` VALUES ('sysUser-3', '删除', 'sysUser', 'sys:user:delete', '', '3', null, null, null, null, null, null);

-- ----------------------------
-- Table structure for t_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_log`;
CREATE TABLE `t_sys_log` (
  `LOG_ID` varchar(32) NOT NULL,
  `LOG_TYPE` int(2) DEFAULT NULL COMMENT '日志类型（1登录日志，2操作日志）',
  `LOG_CONTENT` varchar(255) DEFAULT NULL COMMENT '日志内容',
  `OPERATE_TYPE` int(2) DEFAULT NULL COMMENT '操作类型',
  `USER_ID` varchar(32) DEFAULT NULL COMMENT '操作用户账号',
  `USER_NAME` varchar(100) DEFAULT NULL COMMENT '操作用户名称',
  `IP` varchar(100) DEFAULT NULL COMMENT 'IP',
  `METHOD` varchar(255) DEFAULT NULL COMMENT '请求JAVA方法',
  `REQUEST_URL` varchar(255) DEFAULT NULL COMMENT '请求路径',
  `REQUEST_PARAM` varchar(2048) DEFAULT NULL COMMENT '请求参数',
  `REQUEST_TYPE` varchar(10) DEFAULT NULL COMMENT '请求类型',
  `OPERATE_RESULT` varchar(1024) DEFAULT NULL COMMENT '操作结果',
  `COST_TIME` bigint(20) DEFAULT NULL COMMENT '耗时',
  `CREATE_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATE_DATE` date DEFAULT NULL COMMENT '创建日期',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(32) DEFAULT NULL COMMENT '修改人',
  `UPDATE_DATE` date DEFAULT NULL COMMENT '修改日期',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`LOG_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='系统日志表';

-- ----------------------------
-- Records of t_sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for t_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_menu`;
CREATE TABLE `t_sys_menu` (
  `MENU_ID` varchar(32) NOT NULL COMMENT '菜单ID',
  `MENU_NAME` varchar(100) NOT NULL COMMENT '菜单名称',
  `PARENT_MENU_ID` varchar(32) DEFAULT NULL COMMENT '上级菜单ID',
  `MENU_ICON` varchar(100) DEFAULT NULL COMMENT '图标',
  `MENU_URL` varchar(255) DEFAULT NULL COMMENT '菜单URL',
  `MENU_PERMISSIONS` varchar(255) DEFAULT NULL COMMENT '授权',
  `COMPONENT` varchar(255) DEFAULT NULL COMMENT '组件',
  `REDIRECT` varchar(255) DEFAULT NULL COMMENT '重定向URL',
  `HIDDEN` varchar(1) DEFAULT NULL COMMENT '是否隐藏',
  `IS_ROUTE` varchar(1) DEFAULT NULL COMMENT '是否路由',
  `ROUTE_NAME` varchar(255) DEFAULT NULL COMMENT '路由名称',
  `IS_CACHE` varchar(1) DEFAULT NULL COMMENT 'tagsView是否缓存 1-缓存 0-不缓存',
  `AFFIX` varchar(1) DEFAULT NULL COMMENT '是否常驻菜单1-是0-否',
  `IS_LEAF` varchar(1) DEFAULT NULL COMMENT '是否叶子节点',
  `SORT_NO` int(5) DEFAULT NULL COMMENT '排序号',
  `CREATE_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATE_DATE` date DEFAULT NULL COMMENT '创建日期',
  `CREATE_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(32) DEFAULT NULL COMMENT '修改人',
  `UPDATE_DATE` date DEFAULT NULL COMMENT '修改日期',
  `UPDATE_TIME` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`MENU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='SYS04_菜单表';

-- ----------------------------
-- Records of t_sys_menu
-- ----------------------------
INSERT INTO `t_sys_menu` VALUES ('sys', '系统管理', '', 'tools', '/sys', null, '', '', '0', '1', 'Sys', '1', '0', '0', '10000', null, null, null, 'admin', '2019-09-28', '2019-09-28 16:40:56');
INSERT INTO `t_sys_menu` VALUES ('sysCodeInfo', '代码信息', 'sys', 'list', 'codeInfo', 'sys:codeType:list,sys:codeInfo:list,sys:codeInfo:listByQw', 'views/sys/codeInfo/index', null, '0', '1', 'SysCodeInfo', '1', '0', '1', '10300', null, null, null, 'admin', '2019-09-17', '2019-09-17 22:03:31');
INSERT INTO `t_sys_menu` VALUES ('sysCodeType', '代码类别', 'sys', 'list', 'codeType', 'sys:codeType:list,sys:codeType:listByQw', 'views/sys/codeType/index', null, '0', '1', 'SysCodeType', '1', '0', '1', '10200', null, null, null, 'admin', '2019-09-17', '2019-09-17 22:03:48');
INSERT INTO `t_sys_menu` VALUES ('sysConfig', '参数管理', 'sys', 'list', 'config', 'sys:config:list,sys:config:listByQw', 'views/sys/config/index', null, '0', '1', 'SysConfig', '1', '0', '1', '10100', null, null, null, 'admin', '2019-09-28', '2019-09-28 17:26:07');
INSERT INTO `t_sys_menu` VALUES ('sysDataPermission', '数据权限', 'sys', 'list', 'dataPermission', 'sys:dataPermission:list,sys:dataPermission:listByQw', 'views/sys/dataPermission/index', null, '0', '1', 'SysDataPermission', '1', '0', '1', '10900', null, null, null, 'admin', '2019-09-17', '2019-09-17 22:04:01');
INSERT INTO `t_sys_menu` VALUES ('sysFunc', '功能管理', 'sys', 'tree', 'func', 'sys:func:list,sys:func:listByQw,sys:menu:getTreeData', 'views/sys/func/index', null, '0', '1', 'SysFunc', '1', '0', '1', '10500', null, null, null, 'admin', '2019-09-17', '2019-09-17 22:03:30');
INSERT INTO `t_sys_menu` VALUES ('sysLog', '系统日志', 'sys', 'list', 'log', 'sys:log:list,sys:log:listByQw', 'views/sys/log/index', null, '0', '1', 'SysLog', '1', '0', '1', '11000', null, null, null, 'admin', '2019-09-17', '2019-09-17 22:03:17');
INSERT INTO `t_sys_menu` VALUES ('sysMenu', '菜单管理', 'sys', 'tree', 'menu', 'sys:menu:list,sys:menu:listByQw,sys:menu:getTreeData', 'views/sys/menu/index', null, '0', '1', 'SysMenu', '1', '0', '1', '10400', null, null, null, 'admin', '2019-09-17', '2019-09-17 22:10:50');
INSERT INTO `t_sys_menu` VALUES ('sysOrg', '机构管理', 'sys', 'tree', 'org', 'sys:org:list,sys:org:listByQw,sys:org:getTreeData', 'views/sys/org/index', null, '0', '1', 'SysOrg', '1', '0', '1', '10600', null, null, null, 'admin', '2019-09-17', '2019-09-17 22:44:53');
INSERT INTO `t_sys_menu` VALUES ('sysRole', '角色管理', 'sys', 'list', 'role', 'sys:role:list,sys:role:listByQw', 'views/sys/role/index', null, '0', '1', 'SysRole', '1', '0', '1', '10700', null, null, null, 'admin', '2019-09-17', '2019-09-17 23:25:12');
INSERT INTO `t_sys_menu` VALUES ('sysUser', '用户管理', 'sys', 'list', 'user', 'sys:user:list,sys:user:listByQw,sys:role:listAll', 'views/sys/user/index', null, '0', '1', 'SysUser', '1', '0', '1', '10800', null, null, null, 'admin', '2019-09-17', '2019-09-17 22:03:16');

-- ----------------------------
-- Table structure for t_sys_org
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_org`;
CREATE TABLE `t_sys_org` (
  `ORG_ID` varchar(32) NOT NULL COMMENT '机构ID',
  `ORG_NAME` varchar(100) NOT NULL COMMENT '机构名称',
  `PARENT_ORG_ID` varchar(32) DEFAULT NULL COMMENT '上级机构ID',
  `SHORT_NAME` varchar(120) DEFAULT NULL COMMENT '机构简称',
  `ORG_TYPE` varchar(4) DEFAULT NULL COMMENT '机构类型',
  `ORG_LEVEL` varchar(2) NOT NULL COMMENT '机构级次',
  `ORG_LEVEL_CODE` varchar(200) NOT NULL COMMENT '组织机构路径',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '备注',
  `IS_LEAF` varchar(1) DEFAULT NULL COMMENT '是否叶子节点',
  `CREATE_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATE_DATE` date DEFAULT NULL COMMENT '创建日期',
  `CREATE_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(32) DEFAULT NULL COMMENT '修改人',
  `UPDATE_DATE` date DEFAULT NULL COMMENT '修改日期',
  `UPDATE_TIME` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ORG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='SYS06_机构表';

-- ----------------------------
-- Records of t_sys_org
-- ----------------------------
INSERT INTO `t_sys_org` VALUES ('1000000000', '总行', '', '总行', '1', '1', '1000000000', '', '0', null, null, null, 'admin', '2019-09-08', '2019-09-08 16:37:49');
INSERT INTO `t_sys_org` VALUES ('1010000000', '北京分行', '1000000000', '', '1', '2', '1000000000,1010000000', '', '0', 'admin', '2019-09-07', '2019-09-07 21:01:14', 'admin', '2019-09-07', '2019-09-07 21:15:12');
INSERT INTO `t_sys_org` VALUES ('1050000000', '福建分行', '1000000000', '', '1', '2', '1000000000,1050000000', '', '0', 'admin', '2019-09-07', '2019-09-07 21:34:00', 'admin', '2019-09-07', '2019-09-07 21:01:15');
INSERT INTO `t_sys_org` VALUES ('1050100000', '福州分行', '1050000000', '', '1', '3', '1000000000,1050000000,1050100000', '', '1', 'admin', '2019-09-07', '2019-09-07 21:15:12', 'admin', '2019-09-07', '2019-09-07 21:10:19');
INSERT INTO `t_sys_org` VALUES ('1050200000', '厦门分行', '1050000000', '', '1', '3', '1000000000,1050000000,1050200000', '', '1', 'admin', '2019-09-07', '2019-09-07 21:10:19', 'admin', '2019-09-07', '2019-09-07 21:19:11');
INSERT INTO `t_sys_org` VALUES ('1050300000', '莆田分行', '1050000000', '', '1', '3', '1000000000,1050000000,1050300000', '', '1', 'admin', '2019-09-07', '2019-09-07 21:04:31', 'admin', '2019-09-07', '2019-09-07 21:04:31');
INSERT INTO `t_sys_org` VALUES ('1050400000', '漳州分行', '1050000000', '', '1', '3', '1000000000,1050000000,1050400000', '', '1', 'admin', '2019-09-07', '2019-09-07 21:34:00', 'admin', '2019-09-07', '2019-09-07 21:21:36');
INSERT INTO `t_sys_org` VALUES ('1050500000', '泉州分行', '1050000000', '', '1', '3', '1000000000,1050000000,1050500000', '', '0', 'admin', '2019-09-07', '2019-09-07 21:01:14', 'admin', '2019-09-07', '2019-09-07 21:34:00');
INSERT INTO `t_sys_org` VALUES ('1050501000', '丰泽支行', '1050500000', '', '1', '4', '1000000000,1050000000,1050500000,1050501000', '', '1', 'admin', '2019-09-07', '2019-09-07 21:15:12', 'admin', '2019-09-07', '2019-09-07 21:09:18');
INSERT INTO `t_sys_org` VALUES ('1050502000', '鲤城支行', '1050500000', '', '1', '4', '1000000000,1050000000,1050500000,1050502000', '', '1', 'admin', '2019-09-07', '2019-09-07 21:10:19', 'admin', '2019-09-07', '2019-09-07 21:01:14');

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role` (
  `ROLE_ID` varchar(32) NOT NULL COMMENT '角色ID',
  `ROLE_NAME` varchar(100) NOT NULL COMMENT '角色名称',
  `SORT_NO` int(4) NOT NULL COMMENT '排序号',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '备注',
  `CREATE_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATE_DATE` date DEFAULT NULL COMMENT '创建日期',
  `CREATE_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(32) DEFAULT NULL COMMENT '修改人',
  `UPDATE_DATE` date DEFAULT NULL COMMENT '修改日期',
  `UPDATE_TIME` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='SYS07_角色表';

-- ----------------------------
-- Records of t_sys_role
-- ----------------------------
INSERT INTO `t_sys_role` VALUES ('admin', 'admin', '1', '超级管理员', null, null, null, 'admin', '2019-09-15', '2019-09-15 16:52:45');
INSERT INTO `t_sys_role` VALUES ('managerRole', '管理角色', '3', '管理角色', 'admin', '2019-09-15', '2019-09-15 18:15:46', 'admin', '2019-09-15', '2019-09-15 17:07:14');
INSERT INTO `t_sys_role` VALUES ('queryRole', '查询角色', '2', '查询角色', 'admin', '2019-09-07', '2019-09-07 21:10:19', 'admin', '2019-09-15', '2019-09-15 18:15:52');

-- ----------------------------
-- Table structure for t_sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_permission`;
CREATE TABLE `t_sys_role_permission` (
  `ROLE_PERMISSION_ID` varchar(32) NOT NULL COMMENT '操作权限ID',
  `ROLE_ID` varchar(32) NOT NULL COMMENT '角色ID',
  `PERMISSION_TYPE` varchar(1) NOT NULL COMMENT '权限类型',
  `MENU_OR_FUNC_ID` varchar(32) NOT NULL COMMENT '菜单或功能ID',
  `CREATE_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATE_DATE` date DEFAULT NULL COMMENT '创建日期',
  `CREATE_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(32) DEFAULT NULL COMMENT '修改人',
  `UPDATE_DATE` date DEFAULT NULL COMMENT '修改日期',
  `UPDATE_TIME` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ROLE_PERMISSION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='SYS08_操作权限表';

-- ----------------------------
-- Records of t_sys_role_permission
-- ----------------------------
INSERT INTO `t_sys_role_permission` VALUES ('0a5e437c3117d5705c24b5bf6ca0de0a', 'queryRole', '1', 'sysCodeInfo', 'admin', '2019-09-08', '2019-09-08 17:51:16', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('0cc3512eb93d630607aabc861c8f66c6', 'queryRole', '1', 'sysFunc', 'admin', '2019-09-08', '2019-09-08 17:51:16', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('0f02c31fa91e961f5c7d0e9bbadb3ca1', 'queryRole', '1', 'sysOrg', 'admin', '2019-09-08', '2019-09-08 17:51:16', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('144e9c813df2cbe31a866b8af6f7ab06', 'admin', '2', 'sysUser-2', 'admin', '2019-09-16', '2019-09-16 23:43:09', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('1bbbb72ec57f72f045c6044d4fa43c40', 'managerRole', '1', 'sysUser', 'admin', '2019-09-28', '2019-09-28 22:05:53', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('22e588331fd00df3ff903c5425a7bff9', 'queryRole', '1', 'sysRole', 'admin', '2019-09-08', '2019-09-08 17:51:16', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('2d61c9f76cc222d564b142095b9d4ef7', 'managerRole', '1', 'sys', 'admin', '2019-09-28', '2019-09-28 22:05:53', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('35a43694d1e406046d4f80ff699d12cb', 'admin', '2', 'sysUser-3', 'admin', '2019-09-16', '2019-09-16 23:43:09', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('3b2903ceee913ba12105c971a448bcc6', 'queryRole', '1', 'sysLog', 'admin', '2019-09-08', '2019-09-08 17:51:16', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('4a6691435487d1e638e213f284b35241', 'queryRole', '1', 'sysUser', 'admin', '2019-09-08', '2019-09-08 17:51:16', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('5a37eeea253c8c1b492b7eb79c65ff85', 'admin', '1', 'sysUser', 'admin', '2019-09-16', '2019-09-16 23:43:09', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('5c583c88bdc2a3a41d20edfb69ff7499', 'queryRole', '1', 'sysCodeType', 'admin', '2019-09-08', '2019-09-08 17:51:16', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('5f2602309cf350019e5eb51d4896e348', 'admin', '2', 'sysUser-1', 'admin', '2019-09-16', '2019-09-16 23:43:09', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('619029fabba65f44c1b2829ee17af685', 'managerRole', '1', 'sysRole', 'admin', '2019-09-28', '2019-09-28 22:05:53', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('6e53609856947ece82a4224d35e17299', 'managerRole', '2', 'sysUser-1', 'admin', '2019-09-28', '2019-09-28 22:05:53', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('783457862a30ef807b22d2f2d5098b7a', 'managerRole', '2', 'sysUser-2', 'admin', '2019-09-28', '2019-09-28 22:05:53', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('789baa7a119df2300eaf4dc93e7bd837', 'managerRole', '2', 'sysRole-5', 'admin', '2019-09-28', '2019-09-28 22:05:53', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('80bf79350534e0c49cc03f580a32b17c', 'admin', '1', 'sysRole', 'admin', '2019-09-16', '2019-09-16 23:43:09', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('8990d5c92e6c4bce92cc3528e15f3722', 'admin', '1', 'sys', 'admin', '2019-09-16', '2019-09-16 23:43:09', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('a1e04035efa6e1c23a181ebd5b2d0fa9', 'managerRole', '2', 'sysUser-3', 'admin', '2019-09-28', '2019-09-28 22:05:53', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('b29279812991e8076c56efbee14e1ae1', 'queryRole', '1', 'sys', 'admin', '2019-09-08', '2019-09-08 17:51:16', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('c3752401c66299e7c2f4539920630f79', 'admin', '2', 'sysRole-5', 'admin', '2019-09-16', '2019-09-16 23:43:09', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('cd8d8b65d90aeff06d9cfa2c4631d1b9', 'queryRole', '1', 'sysDataPermission', 'admin', '2019-09-08', '2019-09-08 17:51:16', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('de1642051f876429215573f89d606e2f', 'queryRole', '1', 'sysMenu', 'admin', '2019-09-08', '2019-09-08 17:51:16', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('fd0722ca670b8b471237b3ee69bac819', 'queryRole', '1', 'sysConfig', 'admin', '2019-09-08', '2019-09-08 17:51:16', null, null, null);

-- ----------------------------
-- Table structure for t_sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_user`;
CREATE TABLE `t_sys_role_user` (
  `ROLE_USER_ID` varchar(32) NOT NULL COMMENT 'UUID',
  `ROLE_ID` varchar(32) NOT NULL COMMENT '角色ID',
  `USER_ID` varchar(32) NOT NULL COMMENT '用户ID',
  `CREATE_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATE_DATE` date DEFAULT NULL COMMENT '创建日期',
  `CREATE_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(32) DEFAULT NULL COMMENT '修改人',
  `UPDATE_DATE` date DEFAULT NULL COMMENT '修改日期',
  `UPDATE_TIME` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ROLE_USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='SYS11_角色和用户关系表';

-- ----------------------------
-- Records of t_sys_role_user
-- ----------------------------
INSERT INTO `t_sys_role_user` VALUES ('admin-admin', 'admin', 'admin', null, null, null, null, null, null);
INSERT INTO `t_sys_role_user` VALUES ('admin-lizhi2', 'admin', 'lizhi2', 'zjm', '2019-09-28', '2019-09-28 22:07:00', null, null, null);
INSERT INTO `t_sys_role_user` VALUES ('managerRole-zjm', 'managerRole', 'zjm', 'admin', '2019-09-25', '2019-09-25 00:34:23', null, null, null);
INSERT INTO `t_sys_role_user` VALUES ('managerRole-zjmzxfzhl', 'managerRole', 'zjmzxfzhl', 'zjm', '2019-09-28', '2019-09-28 22:08:41', null, null, null);
INSERT INTO `t_sys_role_user` VALUES ('managerRole-zxf', 'managerRole', 'zxf', 'admin', '2019-09-25', '2019-09-25 00:34:23', null, null, null);
INSERT INTO `t_sys_role_user` VALUES ('queryRole-lizhi1', 'queryRole', 'lizhi1', 'admin', '2019-09-15', '2019-09-15 17:07:14', null, null, null);
INSERT INTO `t_sys_role_user` VALUES ('queryRole-lizhi2', 'queryRole', 'lizhi2', 'admin', '2019-09-15', '2019-09-15 16:28:23', null, null, null);
INSERT INTO `t_sys_role_user` VALUES ('queryRole-zjm', 'queryRole', 'zjm', 'admin', '2019-09-28', '2019-09-28 22:06:08', null, null, null);
INSERT INTO `t_sys_role_user` VALUES ('queryRole-zjmzxfzhl', 'queryRole', 'zjmzxfzhl', 'admin', '2019-09-28', '2019-09-28 22:10:48', null, null, null);

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `USER_ID` varchar(32) NOT NULL COMMENT '用户ID',
  `USER_NAME` varchar(100) NOT NULL COMMENT '用户姓名',
  `PASSWORD` varchar(100) NOT NULL COMMENT '密码',
  `SALT` varchar(8) DEFAULT NULL COMMENT '密码盐',
  `SEX` varchar(1) DEFAULT NULL COMMENT '性别',
  `ROLE_ID` varchar(32) DEFAULT NULL COMMENT '所属角色ID',
  `ORG_ID` varchar(32) DEFAULT NULL COMMENT '所属机构ID',
  `MOBILE` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `ID_CARD_NO` varchar(20) DEFAULT NULL COMMENT '身份证号码',
  `EMAIL` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `STATUS` varchar(1) DEFAULT NULL COMMENT '用户状态',
  `SORT_NO` int(6) DEFAULT NULL COMMENT '排序号',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '备注',
  `CREATE_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATE_DATE` date DEFAULT NULL COMMENT '创建日期',
  `CREATE_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(32) DEFAULT NULL COMMENT '修改人',
  `UPDATE_DATE` date DEFAULT NULL COMMENT '修改日期',
  `UPDATE_TIME` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='SYS10_用户表';

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------
INSERT INTO `t_sys_user` VALUES ('admin', 'admin', '26c9817af1b179ba9f87f9d03c6fb966b777217beaca5f2dcbb866c12876b523', 'EZWxbpPx', '1', 'admin', '1000000000', '18888888888', 'xxx', 'xxx@yyy.zz', '1', '1', '1', null, null, null, 'admin', '2019-09-07', '2019-09-07 21:15:12');
INSERT INTO `t_sys_user` VALUES ('lizhi1', '离职用户1', '902d802fdf9201d1f81d21efbafda7ed833dd3e404825830b1494f34b486d077', '9eG2YY1W', '1', 'queryRole', '1050501000', '18888888888', '1', '1', '2', '5', '', 'admin', '2019-09-15', '2019-09-15 17:07:14', 'admin', '2019-09-15', '2019-09-15 21:42:59');
INSERT INTO `t_sys_user` VALUES ('lizhi2', '离职用户2', '3efefed2208103a4077e783869832d6ba310b4bec90d441afd51d753e25c842a', 'PgceWId6', '2', 'queryRole', '1050500000', '18888888888', '1', '1', '2', '6', '', 'admin', '2019-09-15', '2019-09-15 16:28:23', null, null, null);
INSERT INTO `t_sys_user` VALUES ('zjm', '庄金明', 'c7c0493e05a82ce5d022e7db01376b28434f19d8320e32533cd3935e605ecabb', '0oyWsz6S', '1', 'managerRole', '1050501000', '18888888888', '1', '1', '1', '3', '', 'admin', '2019-09-15', '2019-09-15 18:15:52', 'zjm', '2019-09-28', '2019-09-28 22:12:10');
INSERT INTO `t_sys_user` VALUES ('zjmzxfzhl', 'zjmzxfzhl', 'b639a87dad4ccede9bf23215e1f2a742b5aecddaa4f0f94ca514773651f3b0c3', 'hVtw6mZe', '1', 'queryRole', '1050500000', '18888888888', 'xxx', 'xxx@abc.com', '1', '4', '', 'zjm', '2019-09-28', '2019-09-28 22:08:41', 'zjmzxfzhl', '2019-09-28', '2019-09-28 22:11:07');
INSERT INTO `t_sys_user` VALUES ('zxf', '庄晓芳', '80cb703ef5263788e70ec991fe5cedd8756c63dae6850e40b9cca8aab5b4d8b1', 'jjFcC4hd', '2', 'managerRole', '1050200000', '18888888888', '1', '1', '1', '4', '', 'admin', '2019-09-15', '2019-09-15 16:53:39', 'zxf', '2019-09-15', '2019-09-15 22:35:58');
