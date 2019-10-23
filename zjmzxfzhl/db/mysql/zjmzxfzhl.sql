/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : zjmzxfzhl

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2019-10-24 00:31:39
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
INSERT INTO `t_app_user` VALUES ('18888888888', '18888888888', '18888888888', 'dbf19aee38bf8c32dac8e4012d75cad843362ed398b58a301a80e608bcddacf1', '4bR1hXY6', null, null, null, '18888888888', '2019-10-24', '2019-10-24 00:26:23', null, null, null);

-- ----------------------------
-- Table structure for t_demo_zjmzxfzhl
-- ----------------------------
DROP TABLE IF EXISTS `t_demo_zjmzxfzhl`;
CREATE TABLE `t_demo_zjmzxfzhl` (
  `ZJMZXFZHL_ID` varchar(32) NOT NULL COMMENT 'ID',
  `ZJMZXFZHL_NAME` varchar(32) NOT NULL COMMENT '名称',
  `ZJMZXFZHL_CODE_INFO` varchar(1) DEFAULT NULL COMMENT '代码信息',
  `ZJMZXFZHL_DATE` date DEFAULT NULL COMMENT '日期格式',
  `ZJMZXFZHL_DATETIME` datetime DEFAULT NULL COMMENT '时间格式',
  `ORG_ID` varchar(32) DEFAULT NULL COMMENT '所属机构ID',
  `ZJMZXFZHL_DBPARAM1` varchar(3) DEFAULT NULL COMMENT '参数1',
  `ZJMZXFZHL_DBPARAM2` int(3) DEFAULT NULL COMMENT '参数2',
  `FILTER_OPERATOR_EQ` varchar(3) DEFAULT NULL COMMENT '等于',
  `FILTER_OPERATOR_NE` int(3) DEFAULT NULL COMMENT '不等于',
  `FILTER_OPERATOR_LT` float(5,2) DEFAULT NULL COMMENT '小于',
  `FILTER_OPERATOR_LE` int(3) DEFAULT NULL COMMENT '小于等于',
  `FILTER_OPERATOR_GT` int(3) DEFAULT NULL COMMENT '大于',
  `FILTER_OPERATOR_GE` float(5,2) DEFAULT NULL COMMENT '大于等于',
  `FILTER_OPERATOR_IN` varchar(3) DEFAULT NULL COMMENT 'IN',
  `FILTER_OPERATOR_BETWEEN` int(3) DEFAULT NULL COMMENT 'BETWEEN',
  `FILTER_OPERATOR_LIKE` varchar(32) DEFAULT NULL COMMENT '模糊',
  `FILTER_OPERATOR_LIKE_LEFT` varchar(32) DEFAULT NULL COMMENT '左模糊',
  `FILTER_OPERATOR_LIKE_RIGHT` varchar(32) DEFAULT NULL COMMENT '右模糊',
  `CREATE_BY` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATE_DATE` date DEFAULT NULL COMMENT '创建日期',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_BY` varchar(32) DEFAULT NULL COMMENT '修改人',
  `UPDATE_DATE` date DEFAULT NULL COMMENT '修改日期',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ZJMZXFZHL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='DEMO01_开发示例';

-- ----------------------------
-- Records of t_demo_zjmzxfzhl
-- ----------------------------
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-1', '开发示例1', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '9', '6.00', '5', '2', '9.00', '7', '3', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-10', '开发示例10', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '1', '3.00', '3', '2', '7.00', '4', '6', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-100', '开发示例100', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '9', '5.00', '2', '6', '1.00', '5', '7', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-101', '开发示例101', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '3', '2.00', '4', '6', '5.00', '8', '1', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-102', '开发示例102', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '4', '7.00', '2', '9', '8.00', '5', '8', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-103', '开发示例103', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '1', '5', '7.00', '4', '5', '6.00', '2', '7', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-104', '开发示例104', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '5', '9', '2.00', '3', '5', '2.00', '7', '1', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-105', '开发示例105', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '3', '9.00', '6', '3', '7.00', '9', '7', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-106', '开发示例106', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '7', '8', '5.00', '8', '8', '8.00', '2', '8', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-107', '开发示例107', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '2', '8.00', '8', '9', '5.00', '8', '8', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-108', '开发示例108', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '7', '9.00', '2', '4', '7.00', '4', '6', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-109', '开发示例109', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '1', '4.00', '1', '3', '6.00', '2', '3', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-11', '开发示例11', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '3', '8.00', '5', '1', '1.00', '6', '3', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-110', '开发示例110', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '5', '4.00', '3', '8', '5.00', '6', '6', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-111', '开发示例111', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '8', '9.00', '3', '4', '8.00', '4', '8', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-112', '开发示例112', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '4', '3.00', '2', '4', '9.00', '3', '6', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-113', '开发示例113', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '3', '2.00', '2', '8', '9.00', '1', '9', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-114', '开发示例114', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '1', '7', '9.00', '2', '4', '6.00', '2', '5', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-115', '开发示例115', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '7', '7.00', '2', '4', '4.00', '1', '3', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-116', '开发示例116', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '8', '9', '5.00', '4', '2', '9.00', '2', '7', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-117', '开发示例117', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '9', '8', '5.00', '6', '3', '9.00', '4', '5', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-118', '开发示例118', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '7', '8.00', '5', '7', '2.00', '4', '8', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-119', '开发示例119', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '8', '1.00', '5', '6', '4.00', '3', '8', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-12', '开发示例12', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '1', '3.00', '2', '6', '1.00', '7', '6', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-120', '开发示例120', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '1', '9.00', '8', '1', '7.00', '1', '9', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-121', '开发示例121', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '4', '1', '5.00', '8', '1', '5.00', '5', '5', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-122', '开发示例122', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '5', '9.00', '8', '8', '9.00', '1', '8', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-123', '开发示例123', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '2', '9.00', '3', '2', '9.00', '6', '9', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-124', '开发示例124', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '4', '8', '1.00', '4', '2', '5.00', '3', '1', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-125', '开发示例125', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '7', '4.00', '9', '1', '3.00', '5', '4', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-126', '开发示例126', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '3', '4.00', '1', '8', '1.00', '1', '2', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-127', '开发示例127', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '1', '2.00', '8', '7', '3.00', '8', '2', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-128', '开发示例128', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '4', '1.00', '4', '9', '8.00', '2', '4', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-129', '开发示例129', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '2', '3.00', '9', '5', '2.00', '3', '8', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-13', '开发示例13', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '3', '5', '8.00', '9', '5', '7.00', '5', '6', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-130', '开发示例130', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '6', '1', '4.00', '5', '5', '3.00', '5', '6', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-131', '开发示例131', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '6', '8.00', '8', '2', '4.00', '8', '5', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-132', '开发示例132', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '8', '2.00', '8', '6', '3.00', '4', '8', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-133', '开发示例133', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '8', '5.00', '5', '7', '4.00', '4', '6', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-134', '开发示例134', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '4', '4', '6.00', '4', '4', '2.00', '7', '3', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-135', '开发示例135', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '8', '5', '1.00', '5', '9', '3.00', '3', '9', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-136', '开发示例136', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '9', '5.00', '9', '5', '7.00', '8', '4', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-137', '开发示例137', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '8', '3.00', '6', '9', '2.00', '3', '2', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-138', '开发示例138', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '6', '7.00', '4', '3', '2.00', '9', '2', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-139', '开发示例139', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '4', '2.00', '4', '3', '7.00', '2', '2', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-14', '开发示例14', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '5', '9.00', '6', '3', '2.00', '2', '8', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-140', '开发示例140', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '1', '7', '4.00', '9', '6', '3.00', '2', '5', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-141', '开发示例141', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '4', '9.00', '6', '2', '8.00', '7', '8', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-142', '开发示例142', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '5', '3.00', '5', '4', '8.00', '1', '7', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-143', '开发示例143', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '5', '7.00', '6', '6', '3.00', '5', '2', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-144', '开发示例144', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '3', '1.00', '1', '3', '4.00', '5', '5', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-145', '开发示例145', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '4', '9.00', '2', '6', '9.00', '2', '3', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-146', '开发示例146', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '8', '9.00', '2', '8', '2.00', '5', '6', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-147', '开发示例147', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '7', '3.00', '2', '7', '2.00', '6', '9', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-148', '开发示例148', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '2', '7', '6.00', '8', '2', '8.00', '3', '6', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-149', '开发示例149', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '9', '3.00', '1', '1', '3.00', '1', '7', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-15', '开发示例15', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '1', '8', '9.00', '5', '1', '6.00', '6', '1', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-150', '开发示例150', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '3', '3.00', '6', '6', '7.00', '5', '3', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-151', '开发示例151', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '7', '7', '2.00', '3', '6', '8.00', '7', '5', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-152', '开发示例152', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '5', '9', '4.00', '1', '4', '5.00', '1', '9', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-153', '开发示例153', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '5', '2', '1.00', '9', '2', '9.00', '8', '7', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-154', '开发示例154', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '8', '2', '8.00', '8', '2', '8.00', '9', '1', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-155', '开发示例155', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '7', '2.00', '7', '3', '1.00', '1', '2', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-156', '开发示例156', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '4', '9.00', '5', '7', '2.00', '3', '9', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-157', '开发示例157', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '1', '3.00', '9', '8', '4.00', '5', '4', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-158', '开发示例158', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '4', '7.00', '7', '9', '2.00', '7', '4', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-159', '开发示例159', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '6', '2', '5.00', '6', '3', '9.00', '8', '3', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-16', '开发示例16', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '7', '3', '2.00', '1', '9', '3.00', '6', '7', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-160', '开发示例160', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '9', '7.00', '8', '7', '9.00', '1', '3', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-161', '开发示例161', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '4', '1.00', '6', '7', '1.00', '4', '6', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-162', '开发示例162', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '4', '8', '5.00', '3', '4', '8.00', '4', '4', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-163', '开发示例163', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '4', '9', '2.00', '9', '5', '1.00', '8', '3', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-164', '开发示例164', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '3', '3.00', '2', '7', '9.00', '3', '3', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-165', '开发示例165', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '4', '1.00', '5', '1', '6.00', '9', '6', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-166', '开发示例166', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '8', '9.00', '3', '6', '5.00', '6', '8', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-167', '开发示例167', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '6', '3.00', '9', '2', '8.00', '8', '5', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-168', '开发示例168', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '7', '3', '6.00', '4', '4', '4.00', '1', '9', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-169', '开发示例169', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '3', '7.00', '3', '5', '3.00', '3', '9', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-17', '开发示例17', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '8', '1', '8.00', '4', '5', '5.00', '5', '4', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-170', '开发示例170', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '3', '9', '3.00', '4', '1', '2.00', '1', '1', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-171', '开发示例171', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '1', '3.00', '8', '5', '8.00', '5', '1', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-172', '开发示例172', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '7', '9.00', '6', '8', '8.00', '9', '8', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-173', '开发示例173', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '2', '1', '7.00', '5', '5', '1.00', '9', '3', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-174', '开发示例174', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '4', '6', '9.00', '6', '6', '7.00', '9', '2', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-175', '开发示例175', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '5', '6', '6.00', '7', '4', '5.00', '2', '1', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-176', '开发示例176', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '7', '5.00', '7', '8', '1.00', '1', '8', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-177', '开发示例177', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '1', '8', '4.00', '1', '2', '8.00', '3', '2', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-178', '开发示例178', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '3', '9.00', '1', '1', '2.00', '2', '1', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-179', '开发示例179', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '2', '9.00', '6', '9', '1.00', '4', '4', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-18', '开发示例18', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '4', '9', '7.00', '1', '8', '1.00', '8', '5', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-180', '开发示例180', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '7', '3.00', '8', '7', '2.00', '5', '2', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-181', '开发示例181', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '1', '2', '9.00', '7', '7', '7.00', '5', '4', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-182', '开发示例182', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '5', '1.00', '9', '5', '8.00', '6', '1', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-183', '开发示例183', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '3', '1.00', '6', '7', '2.00', '1', '2', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-184', '开发示例184', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '5', '3', '6.00', '4', '2', '2.00', '6', '1', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-185', '开发示例185', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '4', '3', '2.00', '6', '8', '4.00', '7', '7', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-186', '开发示例186', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '4', '9', '5.00', '5', '2', '8.00', '1', '5', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-187', '开发示例187', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '8', '1', '2.00', '2', '3', '6.00', '7', '2', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-188', '开发示例188', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '4', '9.00', '2', '1', '4.00', '4', '6', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-189', '开发示例189', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '2', '4', '8.00', '5', '2', '8.00', '9', '3', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-19', '开发示例19', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '2', '7', '5.00', '8', '7', '2.00', '2', '2', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-190', '开发示例190', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '9', '2.00', '3', '8', '8.00', '5', '6', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-191', '开发示例191', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '3', '3.00', '8', '6', '3.00', '5', '1', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-192', '开发示例192', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '8', '1', '2.00', '6', '9', '1.00', '9', '7', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-193', '开发示例193', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '3', '3.00', '9', '7', '4.00', '5', '7', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-194', '开发示例194', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '4', '2.00', '5', '3', '6.00', '9', '3', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-195', '开发示例195', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '5', '8', '8.00', '3', '6', '2.00', '4', '7', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-196', '开发示例196', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '3', '4.00', '4', '9', '1.00', '9', '3', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-197', '开发示例197', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '4', '5.00', '9', '4', '2.00', '6', '7', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-198', '开发示例198', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '8', '5.00', '4', '6', '4.00', '3', '6', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-199', '开发示例199', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '8', '6.00', '7', '1', '3.00', '2', '9', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-2', '开发示例2', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '2', '2.00', '4', '7', '6.00', '6', '8', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-20', '开发示例20', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '2', '2', '7.00', '9', '3', '3.00', '1', '6', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-200', '开发示例200', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '7', '1.00', '3', '1', '1.00', '3', '4', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-201', '开发示例201', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '5', '7', '7.00', '4', '9', '4.00', '4', '2', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-202', '开发示例202', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '7', '7.00', '4', '5', '5.00', '7', '7', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-203', '开发示例203', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '1', '6.00', '7', '2', '3.00', '9', '4', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-204', '开发示例204', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '7', '2.00', '7', '2', '9.00', '2', '3', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-205', '开发示例205', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '4', '2.00', '8', '1', '6.00', '7', '8', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-206', '开发示例206', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '9', '6.00', '1', '3', '6.00', '1', '9', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-207', '开发示例207', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '2', '1.00', '4', '2', '3.00', '8', '6', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-208', '开发示例208', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '4', '6', '1.00', '2', '8', '3.00', '8', '6', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-209', '开发示例209', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '3', '5.00', '4', '5', '4.00', '6', '1', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-21', '开发示例21', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '8', '9.00', '2', '5', '9.00', '3', '4', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-210', '开发示例210', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '2', '3.00', '5', '7', '4.00', '3', '6', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-211', '开发示例211', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '5', '9', '1.00', '3', '8', '5.00', '3', '9', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-212', '开发示例212', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '2', '7.00', '9', '9', '2.00', '4', '6', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-213', '开发示例213', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '4', '4', '3.00', '1', '9', '7.00', '4', '6', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-214', '开发示例214', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '8', '4', '3.00', '9', '6', '4.00', '5', '2', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-215', '开发示例215', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '4', '8.00', '9', '4', '1.00', '7', '4', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-216', '开发示例216', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '7', '6.00', '2', '6', '2.00', '5', '2', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-217', '开发示例217', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '6', '9', '6.00', '4', '9', '6.00', '7', '9', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-218', '开发示例218', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '5', '3', '1.00', '1', '2', '2.00', '6', '8', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-219', '开发示例219', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '7', '3', '3.00', '4', '2', '5.00', '4', '1', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-22', '开发示例22', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '7', '7.00', '8', '2', '5.00', '9', '7', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-220', '开发示例220', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '9', '4.00', '6', '4', '3.00', '5', '7', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-221', '开发示例221', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '9', '8.00', '8', '9', '1.00', '7', '4', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-222', '开发示例222', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '4', '1.00', '1', '3', '1.00', '5', '8', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-223', '开发示例223', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '9', '5', '1.00', '6', '5', '7.00', '4', '7', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-224', '开发示例224', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '9', '4.00', '9', '6', '2.00', '9', '5', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-225', '开发示例225', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '8', '2', '4.00', '6', '2', '7.00', '9', '8', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-226', '开发示例226', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '4', '5.00', '2', '3', '7.00', '8', '1', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-227', '开发示例227', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '3', '4.00', '3', '5', '2.00', '3', '8', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-228', '开发示例228', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '3', '7.00', '7', '8', '4.00', '3', '5', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-229', '开发示例229', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '1', '4', '9.00', '2', '5', '7.00', '2', '5', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-23', '开发示例23', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '2', '2', '4.00', '1', '9', '4.00', '4', '4', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-230', '开发示例230', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '1', '1.00', '4', '5', '6.00', '1', '6', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-231', '开发示例231', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '2', '8.00', '8', '2', '6.00', '5', '7', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-232', '开发示例232', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '4', '7.00', '3', '3', '5.00', '1', '7', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-233', '开发示例233', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '3', '4.00', '7', '8', '4.00', '7', '8', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-234', '开发示例234', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '4', '3.00', '5', '4', '3.00', '2', '5', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-235', '开发示例235', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '4', '5', '4.00', '2', '6', '8.00', '3', '6', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-236', '开发示例236', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '2', '7.00', '9', '3', '8.00', '5', '5', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-237', '开发示例237', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '5', '5', '6.00', '5', '7', '6.00', '8', '1', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-238', '开发示例238', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '8', '4', '7.00', '5', '7', '8.00', '4', '2', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-239', '开发示例239', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '8', '1.00', '9', '8', '8.00', '6', '1', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-24', '开发示例24', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '5', '3.00', '3', '7', '1.00', '1', '8', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-240', '开发示例240', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '2', '4.00', '7', '7', '2.00', '6', '3', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-241', '开发示例241', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '2', '5.00', '6', '1', '6.00', '6', '5', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-242', '开发示例242', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '5', '4', '3.00', '3', '4', '4.00', '6', '8', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-243', '开发示例243', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '4', '6.00', '5', '3', '8.00', '7', '6', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-244', '开发示例244', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '6', '9', '9.00', '4', '9', '7.00', '6', '3', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-245', '开发示例245', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '8', '4.00', '5', '9', '7.00', '4', '6', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-246', '开发示例246', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '2', '8', '3.00', '4', '2', '4.00', '3', '7', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-247', '开发示例247', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '5', '1', '8.00', '8', '6', '1.00', '3', '2', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-248', '开发示例248', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '5', '9', '8.00', '6', '3', '7.00', '1', '9', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-249', '开发示例249', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '6', '5', '2.00', '8', '7', '6.00', '7', '1', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-25', '开发示例25', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '2', '5.00', '7', '5', '2.00', '4', '1', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-250', '开发示例250', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '3', '6.00', '8', '9', '4.00', '7', '2', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-251', '开发示例251', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '9', '7', '2.00', '3', '8', '5.00', '6', '3', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-252', '开发示例252', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '5', '5.00', '9', '1', '7.00', '8', '5', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-253', '开发示例253', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '2', '2.00', '6', '1', '1.00', '7', '2', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-254', '开发示例254', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '5', '6.00', '2', '4', '2.00', '4', '4', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-255', '开发示例255', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '6', '5.00', '3', '7', '4.00', '9', '1', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-256', '开发示例256', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '5', '6.00', '6', '8', '6.00', '1', '5', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-257', '开发示例257', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '8', '2.00', '4', '6', '6.00', '1', '9', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-258', '开发示例258', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '5', '5.00', '1', '3', '4.00', '4', '7', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-259', '开发示例259', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '5', '7', '8.00', '1', '6', '2.00', '8', '3', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-26', '开发示例26', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '2', '2', '9.00', '4', '9', '8.00', '1', '2', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-260', '开发示例260', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '5', '3.00', '1', '3', '4.00', '7', '4', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-261', '开发示例261', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '5', '1.00', '3', '7', '2.00', '8', '3', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-262', '开发示例262', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '2', '9.00', '2', '6', '8.00', '8', '7', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-263', '开发示例263', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '8', '9.00', '5', '7', '7.00', '9', '6', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-264', '开发示例264', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '3', '6.00', '2', '6', '8.00', '8', '6', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-265', '开发示例265', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '4', '3', '5.00', '3', '2', '8.00', '8', '6', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-266', '开发示例266', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '5', '4', '4.00', '8', '6', '5.00', '1', '6', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-267', '开发示例267', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '4', '5', '8.00', '4', '6', '1.00', '6', '5', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-268', '开发示例268', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '5', '1.00', '2', '6', '3.00', '6', '3', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-269', '开发示例269', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '2', '4.00', '9', '2', '1.00', '4', '7', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-27', '开发示例27', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '2', '5.00', '3', '1', '8.00', '6', '9', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-270', '开发示例270', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '5', '7.00', '8', '6', '3.00', '7', '1', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-271', '开发示例271', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '1', '3.00', '9', '9', '1.00', '4', '9', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-272', '开发示例272', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '1', '7', '8.00', '9', '8', '7.00', '6', '5', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-273', '开发示例273', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '8', '7', '2.00', '5', '7', '8.00', '4', '1', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-274', '开发示例274', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '8', '3', '7.00', '4', '5', '7.00', '2', '3', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-275', '开发示例275', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '4', '2.00', '5', '3', '3.00', '7', '8', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-276', '开发示例276', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '5', '1.00', '6', '9', '6.00', '8', '1', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-277', '开发示例277', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '4', '4', '9.00', '1', '8', '4.00', '5', '9', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-278', '开发示例278', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '4', '3', '2.00', '5', '8', '8.00', '7', '2', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-279', '开发示例279', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '7', '8.00', '6', '3', '7.00', '3', '2', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-28', '开发示例28', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '4', '2.00', '6', '6', '8.00', '2', '2', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-280', '开发示例280', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '3', '8.00', '3', '4', '3.00', '8', '6', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-281', '开发示例281', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '2', '3.00', '9', '7', '1.00', '5', '1', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-282', '开发示例282', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '5', '5.00', '8', '9', '5.00', '9', '7', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-283', '开发示例283', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '4', '5.00', '9', '3', '2.00', '2', '2', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-284', '开发示例284', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '6', '7', '4.00', '8', '1', '9.00', '5', '5', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-285', '开发示例285', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '9', '1.00', '9', '9', '3.00', '5', '7', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-286', '开发示例286', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '5', '1', '3.00', '6', '6', '2.00', '6', '6', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-287', '开发示例287', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '7', '8.00', '4', '1', '9.00', '1', '9', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-288', '开发示例288', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '4', '3.00', '8', '2', '7.00', '6', '2', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-289', '开发示例289', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '9', '8.00', '7', '1', '9.00', '9', '7', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-29', '开发示例29', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '6', '3.00', '3', '9', '6.00', '9', '8', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-290', '开发示例290', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '2', '8', '3.00', '4', '6', '8.00', '2', '5', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-291', '开发示例291', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '9', '6.00', '7', '1', '7.00', '8', '5', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-292', '开发示例292', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '7', '5.00', '5', '4', '9.00', '3', '3', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-293', '开发示例293', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '2', '5.00', '3', '5', '3.00', '1', '2', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-294', '开发示例294', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '1', '4.00', '7', '7', '2.00', '4', '4', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-295', '开发示例295', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '5', '1.00', '8', '3', '7.00', '4', '7', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-296', '开发示例296', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '7', '8.00', '8', '8', '1.00', '6', '8', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-297', '开发示例297', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '8', '1.00', '3', '9', '2.00', '3', '3', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-298', '开发示例298', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '4', '8', '5.00', '6', '1', '4.00', '2', '7', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-299', '开发示例299', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '2', '7.00', '5', '1', '3.00', '3', '7', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-3', '开发示例3', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '3', '6.00', '5', '5', '2.00', '3', '4', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-30', '开发示例30', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '1', '3.00', '9', '6', '6.00', '5', '9', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-300', '开发示例300', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '1', '1.00', '4', '6', '2.00', '9', '1', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-301', '开发示例301', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '8', '7.00', '7', '5', '8.00', '1', '7', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-302', '开发示例302', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '9', '6', '9.00', '3', '3', '4.00', '5', '8', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-303', '开发示例303', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '6', '1.00', '5', '1', '9.00', '8', '9', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-304', '开发示例304', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '4', '3', '8.00', '7', '7', '3.00', '9', '8', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-305', '开发示例305', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '9', '8.00', '7', '7', '3.00', '4', '8', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-306', '开发示例306', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '2', '3', '8.00', '4', '4', '8.00', '8', '5', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-307', '开发示例307', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '1', '5.00', '3', '4', '9.00', '2', '3', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-308', '开发示例308', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '3', '9', '4.00', '2', '1', '4.00', '1', '5', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-309', '开发示例309', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '8', '4', '2.00', '3', '3', '1.00', '2', '5', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-31', '开发示例31', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '5', '2.00', '1', '7', '2.00', '1', '8', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-310', '开发示例310', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '4', '6.00', '6', '1', '4.00', '4', '4', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-311', '开发示例311', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '4', '2', '1.00', '4', '5', '7.00', '4', '7', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-312', '开发示例312', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '2', '4.00', '5', '6', '9.00', '5', '2', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-313', '开发示例313', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '5', '4.00', '4', '8', '9.00', '2', '1', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-314', '开发示例314', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '9', '2.00', '8', '9', '2.00', '5', '3', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-315', '开发示例315', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '3', '4', '9.00', '2', '6', '4.00', '8', '9', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-316', '开发示例316', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '8', '4', '3.00', '1', '9', '6.00', '8', '7', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-317', '开发示例317', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '1', '8.00', '4', '7', '2.00', '9', '9', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-318', '开发示例318', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '8', '2', '2.00', '3', '5', '7.00', '6', '5', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-319', '开发示例319', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '5', '1.00', '4', '8', '2.00', '9', '7', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-32', '开发示例32', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '4', '2.00', '5', '5', '9.00', '1', '8', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-320', '开发示例320', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '7', '5.00', '5', '4', '8.00', '4', '6', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-321', '开发示例321', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '3', '5.00', '7', '9', '9.00', '2', '7', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-322', '开发示例322', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '7', '4.00', '4', '8', '8.00', '2', '9', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-323', '开发示例323', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '7', '1', '5.00', '9', '7', '3.00', '2', '4', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-324', '开发示例324', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '4', '4', '2.00', '8', '3', '3.00', '2', '1', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-325', '开发示例325', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '2', '7', '7.00', '5', '2', '3.00', '1', '4', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-326', '开发示例326', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '2', '6', '5.00', '8', '6', '3.00', '1', '3', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-327', '开发示例327', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '9', '5.00', '5', '9', '9.00', '7', '1', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-328', '开发示例328', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '6', '2.00', '9', '2', '8.00', '5', '2', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-329', '开发示例329', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '8', '4', '1.00', '3', '3', '1.00', '4', '5', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-33', '开发示例33', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '1', '1.00', '2', '3', '7.00', '4', '6', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-330', '开发示例330', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '2', '7.00', '1', '5', '6.00', '5', '3', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-331', '开发示例331', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '9', '5.00', '5', '6', '8.00', '9', '6', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-332', '开发示例332', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '7', '7.00', '6', '3', '9.00', '9', '7', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-333', '开发示例333', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '5', '3.00', '6', '7', '2.00', '7', '2', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-334', '开发示例334', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '9', '3', '8.00', '8', '6', '4.00', '7', '7', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-335', '开发示例335', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '5', '4', '7.00', '6', '9', '4.00', '7', '3', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-336', '开发示例336', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '3', '6.00', '9', '6', '3.00', '3', '4', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-337', '开发示例337', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '2', '4', '3.00', '3', '9', '8.00', '2', '3', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-338', '开发示例338', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '5', '7.00', '5', '1', '4.00', '8', '8', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-339', '开发示例339', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '6', '2.00', '6', '5', '7.00', '5', '7', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-34', '开发示例34', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '3', '2.00', '7', '1', '3.00', '3', '7', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-340', '开发示例340', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '6', '5.00', '6', '1', '6.00', '6', '4', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-341', '开发示例341', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '9', '1.00', '9', '9', '4.00', '4', '4', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-342', '开发示例342', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '8', '6.00', '6', '1', '5.00', '4', '6', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-343', '开发示例343', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '2', '1', '5.00', '2', '1', '7.00', '8', '3', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-344', '开发示例344', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '1', '3.00', '1', '7', '2.00', '5', '8', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-345', '开发示例345', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '1', '6.00', '9', '7', '3.00', '7', '9', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-346', '开发示例346', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '7', '7.00', '2', '6', '6.00', '3', '7', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-347', '开发示例347', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '3', '1.00', '7', '2', '9.00', '2', '1', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-348', '开发示例348', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '3', '4.00', '7', '1', '1.00', '8', '6', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-349', '开发示例349', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '2', '9.00', '1', '8', '9.00', '5', '4', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-35', '开发示例35', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '1', '9', '2.00', '4', '7', '4.00', '9', '1', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-350', '开发示例350', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '6', '1.00', '4', '3', '8.00', '8', '1', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-351', '开发示例351', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '1', '5.00', '1', '2', '6.00', '3', '2', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-352', '开发示例352', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '2', '6.00', '9', '1', '2.00', '6', '4', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-353', '开发示例353', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '9', '7.00', '5', '2', '4.00', '6', '7', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-354', '开发示例354', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '6', '7.00', '6', '2', '8.00', '7', '7', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-355', '开发示例355', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '3', '2.00', '2', '3', '6.00', '1', '4', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-356', '开发示例356', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '8', '6.00', '4', '8', '6.00', '7', '2', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-357', '开发示例357', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '5', '8.00', '3', '4', '1.00', '4', '4', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-358', '开发示例358', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '3', '5', '4.00', '1', '5', '7.00', '4', '3', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-359', '开发示例359', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '7', '6.00', '9', '8', '8.00', '8', '2', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-36', '开发示例36', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '6', '8.00', '4', '4', '8.00', '2', '1', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-360', '开发示例360', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '4', '7', '2.00', '1', '3', '8.00', '9', '4', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-361', '开发示例361', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '2', '4', '1.00', '1', '4', '8.00', '5', '1', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-362', '开发示例362', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '8', '4.00', '2', '8', '5.00', '4', '4', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-363', '开发示例363', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '1', '3.00', '7', '9', '6.00', '8', '6', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-364', '开发示例364', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '4', '6.00', '8', '9', '2.00', '1', '1', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-365', '开发示例365', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '3', '9.00', '2', '4', '1.00', '5', '5', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-366', '开发示例366', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '6', '1', '6.00', '3', '9', '1.00', '3', '2', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-367', '开发示例367', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '8', '4', '9.00', '4', '8', '4.00', '7', '5', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-368', '开发示例368', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '1', '4', '2.00', '8', '1', '1.00', '1', '5', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-369', '开发示例369', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '8', '1', '1.00', '7', '4', '4.00', '8', '6', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-37', '开发示例37', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '8', '8.00', '3', '2', '3.00', '9', '3', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-370', '开发示例370', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '9', '4.00', '8', '5', '9.00', '4', '5', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-371', '开发示例371', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '9', '9.00', '8', '1', '2.00', '9', '6', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-372', '开发示例372', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '6', '5.00', '9', '8', '5.00', '6', '7', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-373', '开发示例373', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '2', '7', '9.00', '8', '7', '1.00', '8', '7', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-374', '开发示例374', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '4', '2', '3.00', '8', '7', '5.00', '6', '7', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-375', '开发示例375', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '1', '3.00', '8', '5', '9.00', '2', '5', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-376', '开发示例376', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '8', '6', '3.00', '3', '6', '4.00', '7', '5', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-377', '开发示例377', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '4', '9.00', '9', '5', '1.00', '6', '5', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-378', '开发示例378', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '6', '3', '8.00', '1', '2', '3.00', '1', '6', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-379', '开发示例379', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '9', '8.00', '2', '4', '8.00', '4', '6', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-38', '开发示例38', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '9', '6', '7.00', '8', '5', '1.00', '4', '5', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-380', '开发示例380', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '4', '5.00', '3', '6', '2.00', '8', '2', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-381', '开发示例381', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '1', '8.00', '9', '6', '1.00', '9', '8', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-382', '开发示例382', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '2', '2', '3.00', '3', '1', '2.00', '7', '6', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-383', '开发示例383', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '9', '4.00', '7', '3', '2.00', '2', '8', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-384', '开发示例384', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '9', '8.00', '8', '8', '1.00', '5', '8', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-385', '开发示例385', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '4', '2', '2.00', '7', '6', '3.00', '4', '5', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-386', '开发示例386', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '5', '3.00', '2', '1', '7.00', '1', '4', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-387', '开发示例387', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '1', '6.00', '3', '2', '1.00', '6', '1', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-388', '开发示例388', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '4', '3.00', '2', '2', '8.00', '5', '7', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-389', '开发示例389', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '4', '7.00', '6', '4', '2.00', '7', '3', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-39', '开发示例39', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '4', '2', '6.00', '7', '8', '3.00', '4', '2', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-390', '开发示例390', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '9', '9.00', '1', '1', '8.00', '7', '8', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-391', '开发示例391', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '9', '5.00', '7', '5', '1.00', '4', '7', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-392', '开发示例392', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '5', '6', '5.00', '2', '4', '3.00', '1', '4', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-393', '开发示例393', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '8', '8.00', '9', '5', '3.00', '1', '1', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-394', '开发示例394', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '2', '9.00', '8', '3', '8.00', '1', '2', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-395', '开发示例395', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '6', '1.00', '8', '8', '9.00', '4', '5', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-396', '开发示例396', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '3', '9.00', '1', '8', '1.00', '1', '2', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-397', '开发示例397', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '7', '6.00', '4', '7', '7.00', '7', '6', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-398', '开发示例398', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '4', '1', '3.00', '6', '6', '7.00', '7', '2', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-399', '开发示例399', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '3', '9', '2.00', '1', '8', '8.00', '2', '4', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-4', '开发示例4', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '3', '1', '4.00', '3', '3', '5.00', '4', '3', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-40', '开发示例40', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '5', '8', '1.00', '2', '8', '6.00', '2', '9', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-400', '开发示例400', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '1', '7.00', '8', '5', '3.00', '2', '8', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-401', '开发示例401', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '8', '6.00', '8', '3', '5.00', '4', '2', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-402', '开发示例402', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '7', '2.00', '4', '5', '8.00', '6', '7', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-403', '开发示例403', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '6', '9.00', '3', '2', '2.00', '6', '7', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-404', '开发示例404', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '8', '8.00', '9', '9', '5.00', '1', '1', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-405', '开发示例405', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '8', '9.00', '5', '2', '4.00', '2', '8', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-406', '开发示例406', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '4', '6.00', '3', '9', '5.00', '1', '5', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-407', '开发示例407', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '7', '8.00', '7', '6', '6.00', '8', '6', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-408', '开发示例408', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '9', '5.00', '8', '1', '5.00', '3', '3', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-409', '开发示例409', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '2', '2', '5.00', '7', '1', '8.00', '5', '8', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-41', '开发示例41', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '4', '6', '9.00', '3', '4', '9.00', '2', '2', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-410', '开发示例410', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '5', '1', '9.00', '7', '7', '8.00', '4', '2', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-411', '开发示例411', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '2', '8', '7.00', '3', '5', '3.00', '9', '8', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-412', '开发示例412', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '4', '6', '2.00', '9', '2', '7.00', '6', '8', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-413', '开发示例413', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '1', '4.00', '7', '6', '5.00', '4', '5', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-414', '开发示例414', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '8', '5.00', '7', '9', '9.00', '7', '4', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-415', '开发示例415', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '8', '2', '5.00', '2', '5', '2.00', '1', '3', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-416', '开发示例416', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '4', '2', '9.00', '4', '1', '1.00', '4', '6', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-417', '开发示例417', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '8', '5.00', '1', '2', '7.00', '8', '8', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-418', '开发示例418', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '5', '7.00', '7', '4', '5.00', '8', '5', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-419', '开发示例419', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '5', '7.00', '9', '5', '1.00', '6', '9', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-42', '开发示例42', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '4', '8', '2.00', '5', '4', '4.00', '6', '5', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-420', '开发示例420', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '2', '9', '9.00', '2', '4', '5.00', '3', '3', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-421', '开发示例421', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '8', '6.00', '8', '2', '9.00', '7', '9', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-422', '开发示例422', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '7', '5.00', '8', '7', '8.00', '5', '7', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-423', '开发示例423', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '1', '9', '9.00', '1', '4', '4.00', '2', '2', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-424', '开发示例424', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '3', '1.00', '6', '2', '3.00', '9', '9', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-425', '开发示例425', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '8', '9.00', '7', '8', '2.00', '5', '8', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-426', '开发示例426', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '3', '4.00', '9', '9', '6.00', '7', '6', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-427', '开发示例427', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '9', '3.00', '6', '9', '7.00', '3', '5', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-428', '开发示例428', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '2', '1.00', '6', '3', '6.00', '7', '8', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-429', '开发示例429', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '6', '1', '7.00', '3', '4', '7.00', '1', '9', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-43', '开发示例43', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '1', '4.00', '9', '1', '7.00', '8', '3', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-430', '开发示例430', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '5', '3.00', '4', '6', '8.00', '5', '6', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-431', '开发示例431', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '4', '9.00', '2', '5', '4.00', '1', '5', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-432', '开发示例432', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '3', '9.00', '1', '1', '6.00', '4', '1', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-433', '开发示例433', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '1', '4', '5.00', '5', '1', '1.00', '2', '5', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-434', '开发示例434', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '7', '3.00', '9', '8', '2.00', '5', '8', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-435', '开发示例435', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '9', '5', '9.00', '4', '3', '2.00', '7', '1', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-436', '开发示例436', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '6', '1.00', '1', '9', '1.00', '6', '3', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-437', '开发示例437', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '5', '5.00', '7', '1', '4.00', '4', '8', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-438', '开发示例438', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '4', '6', '1.00', '2', '4', '5.00', '9', '8', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-439', '开发示例439', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '1', '4.00', '8', '9', '2.00', '5', '1', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-44', '开发示例44', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '9', '2.00', '8', '9', '9.00', '9', '6', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-440', '开发示例440', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '4', '9.00', '8', '5', '1.00', '9', '4', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-441', '开发示例441', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '6', '4.00', '8', '5', '3.00', '4', '8', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-442', '开发示例442', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '3', '2', '3.00', '4', '1', '6.00', '2', '4', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-443', '开发示例443', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '2', '1', '7.00', '2', '1', '2.00', '5', '4', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-444', '开发示例444', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '4', '4', '5.00', '6', '7', '2.00', '9', '2', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-445', '开发示例445', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '6', '4.00', '1', '2', '2.00', '1', '1', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-446', '开发示例446', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '7', '8.00', '9', '4', '2.00', '9', '2', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-447', '开发示例447', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '9', '6', '6.00', '9', '5', '9.00', '1', '9', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-448', '开发示例448', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '2', '8.00', '6', '5', '2.00', '4', '1', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-449', '���发实例449', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '4', '6.00', '9', '9', '7.00', '4', '6', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-45', '开发示例45', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '5', '4.00', '9', '5', '1.00', '8', '8', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-450', '开发示例450', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '6', '7', '2.00', '5', '5', '3.00', '2', '7', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-451', '开发示例451', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '8', '5.00', '9', '5', '5.00', '9', '6', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-452', '开发示例452', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '7', '5.00', '3', '6', '5.00', '1', '8', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-453', '开发示例453', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '6', '1.00', '2', '1', '4.00', '3', '3', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-454', '开发示例454', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '1', '3', '7.00', '3', '9', '5.00', '8', '3', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-455', '开发示例455', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '2', '1.00', '7', '5', '8.00', '2', '8', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-456', '开发示例456', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '4', '6.00', '3', '8', '7.00', '7', '1', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-457', '开发示例457', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '2', '6', '9.00', '8', '6', '9.00', '6', '8', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-458', '开发示例458', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '6', '5.00', '6', '5', '5.00', '7', '3', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-459', '开发示例459', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '5', '7', '7.00', '5', '8', '1.00', '9', '4', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-46', '开发示例46', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '9', '4.00', '7', '4', '6.00', '8', '4', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-460', '开发示例460', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '2', '7.00', '9', '9', '7.00', '9', '5', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-461', '开发示例461', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '4', '1.00', '7', '3', '9.00', '3', '9', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-462', '开发示例462', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '3', '7.00', '2', '1', '2.00', '8', '7', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-463', '开发示例463', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '4', '6', '3.00', '8', '6', '1.00', '8', '2', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-464', '开发示例464', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '3', '9', '9.00', '2', '1', '3.00', '9', '1', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-465', '开发示例465', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '5', '9', '5.00', '5', '4', '8.00', '6', '6', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-466', '开发示例466', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '2', '5.00', '9', '7', '4.00', '5', '4', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-467', '开发示例467', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '5', '1.00', '5', '7', '4.00', '6', '5', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-468', '开发示例468', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '7', '5.00', '6', '2', '5.00', '2', '8', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-469', '开发示例469', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '7', '4.00', '3', '1', '6.00', '5', '8', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-47', '开发示例47', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '5', '7', '9.00', '8', '5', '2.00', '2', '8', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-470', '开发示例470', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '5', '2', '7.00', '5', '6', '8.00', '4', '8', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-471', '开发示例471', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '7', '1', '1.00', '4', '7', '6.00', '7', '1', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-472', '开发示例472', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '3', '6.00', '7', '5', '5.00', '8', '1', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-473', '开发示例473', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '4', '9.00', '9', '2', '5.00', '7', '5', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-474', '开发示例474', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '1', '3.00', '5', '9', '5.00', '9', '6', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-475', '开发示例475', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '5', '5', '2.00', '6', '8', '1.00', '8', '7', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-476', '开发示例476', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '4', '6', '6.00', '8', '8', '9.00', '8', '8', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-477', '开发示例477', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '8', '7.00', '6', '1', '5.00', '4', '6', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-478', '开发示例478', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '5', '7.00', '1', '7', '6.00', '2', '4', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-479', '开发示例479', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '5', '7', '2.00', '1', '6', '1.00', '8', '2', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-48', '开发示例48', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '6', '8', '8.00', '2', '6', '6.00', '5', '7', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-480', '开发示例480', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '2', '6', '9.00', '1', '3', '5.00', '1', '4', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-481', '开发示例481', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '9', '6.00', '6', '4', '1.00', '2', '3', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-482', '开发示例482', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '8', '6', '9.00', '7', '9', '5.00', '4', '3', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-483', '开发示例483', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '3', '3.00', '4', '8', '3.00', '8', '4', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-484', '开发示例484', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '8', '6.00', '6', '4', '1.00', '4', '4', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-485', '开发示例485', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '8', '7', '7.00', '7', '2', '3.00', '2', '9', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-486', '开发示例486', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '9', '1', '9.00', '8', '3', '8.00', '5', '3', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-487', '开发示例487', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '5', '1', '1.00', '5', '8', '1.00', '6', '5', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-488', '开发示例488', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '5', '3.00', '2', '2', '7.00', '1', '9', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-489', '开发示例489', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '6', '4', '2.00', '8', '7', '4.00', '3', '8', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-49', '开发示例49', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '6', '4', '5.00', '5', '4', '1.00', '5', '6', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-490', '开发示例490', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '3', '4.00', '7', '9', '1.00', '9', '1', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-491', '开发示例491', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '3', '8.00', '6', '5', '1.00', '9', '8', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-492', '开发示例492', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '8', '2', '2.00', '6', '1', '4.00', '1', '9', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-493', '开发示例493', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '3', '5.00', '7', '8', '1.00', '4', '3', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-494', '开发示例494', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '9', '2.00', '2', '2', '8.00', '6', '1', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-495', '开发示例495', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '4', '1.00', '8', '6', '9.00', '5', '7', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-496', '开发示例496', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '4', '4.00', '5', '2', '5.00', '9', '9', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-497', '开发示例497', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '4', '8', '9.00', '5', '7', '5.00', '2', '3', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-498', '开发示例498', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '6', '6.00', '9', '8', '5.00', '1', '2', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-499', '开发示例499', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '2', '2.00', '5', '6', '6.00', '3', '8', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-5', '开发示例5', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '6', '1.00', '7', '8', '4.00', '3', '3', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-50', '开发示例50', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '6', '8.00', '4', '7', '9.00', '7', '3', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-500', '开发示例500', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '4', '5.00', '7', '7', '2.00', '5', '4', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-501', '开发示例501', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '6', '9', '9.00', '4', '7', '8.00', '5', '2', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-502', '开发示例502', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '5', '9.00', '4', '9', '8.00', '8', '8', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-503', '开发示例503', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '5', '2', '1.00', '9', '8', '6.00', '9', '5', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-504', '开发示例504', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '2', '3.00', '4', '4', '6.00', '4', '2', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-505', '开发示例505', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '5', '9', '2.00', '2', '6', '6.00', '5', '7', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-506', '开发示例506', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '7', '3.00', '4', '3', '3.00', '8', '9', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-507', '开发示例507', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '1', '7.00', '1', '4', '8.00', '6', '1', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-508', '开发示例508', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '3', '9.00', '8', '4', '2.00', '8', '4', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-509', '开发示例509', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '5', '2', '2.00', '1', '4', '5.00', '6', '6', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-51', '开发示例51', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '4', '6', '4.00', '2', '5', '1.00', '6', '2', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-510', '开发示例510', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '8', '1.00', '9', '4', '6.00', '8', '9', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-511', '开发示例511', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '9', '8.00', '5', '6', '9.00', '5', '6', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-512', '开发示例512', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '7', '7', '6.00', '5', '3', '2.00', '7', '3', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-513', '开发示例513', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '5', '7.00', '7', '6', '9.00', '1', '4', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-514', '开发示例514', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '2', '7.00', '9', '6', '6.00', '8', '3', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-515', '开发示例515', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '1', '6.00', '6', '6', '8.00', '7', '8', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-516', '开发示例516', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '5', '4', '9.00', '7', '8', '7.00', '7', '1', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-517', '开发示例517', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '6', '1.00', '8', '9', '4.00', '1', '6', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-518', '开发示例518', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '6', '6.00', '4', '4', '8.00', '3', '7', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-519', '开发示例519', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '8', '8', '4.00', '6', '1', '5.00', '8', '8', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-52', '开发示例52', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '2', '5.00', '6', '5', '9.00', '1', '2', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-520', '开发示例520', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '1', '2.00', '9', '4', '7.00', '2', '3', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-521', '开发示例521', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '6', '1', '7.00', '2', '1', '5.00', '9', '6', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-522', '开发示例522', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '5', '8.00', '3', '5', '4.00', '9', '3', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-523', '开发示例523', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '4', '3.00', '4', '9', '2.00', '2', '3', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-524', '开发示例524', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '2', '9', '5.00', '8', '3', '3.00', '4', '3', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-525', '开发示例525', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '5', '2', '6.00', '4', '1', '8.00', '7', '6', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-526', '开发示例526', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '6', '5.00', '4', '6', '4.00', '4', '3', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-527', '开发示例527', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '3', '8.00', '2', '9', '1.00', '4', '8', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-528', '开发示例528', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '2', '6.00', '2', '5', '4.00', '7', '1', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-529', '开发示例529', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '7', '5', '6.00', '7', '7', '3.00', '1', '1', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-53', '开发示例53', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '2', '4.00', '8', '3', '6.00', '5', '3', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-530', '开发示例530', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '1', '8.00', '8', '4', '5.00', '6', '9', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-531', '开发示例531', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '4', '2.00', '3', '2', '1.00', '8', '5', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-532', '开发示例532', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '6', '8.00', '5', '8', '4.00', '9', '7', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-533', '开发示例533', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '3', '8.00', '5', '6', '7.00', '1', '6', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-534', '开发示例534', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '2', '2.00', '7', '9', '9.00', '9', '8', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-535', '开发示例535', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '4', '2', '3.00', '4', '4', '5.00', '7', '6', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-536', '开发示例536', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '8', '3.00', '2', '4', '4.00', '6', '5', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-537', '开发示例537', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '9', '9', '4.00', '4', '8', '9.00', '5', '5', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-538', '开发示例538', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '1', '6', '4.00', '4', '1', '1.00', '5', '2', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-539', '开发示例539', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '1', '6.00', '1', '2', '2.00', '1', '1', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-54', '开发示例54', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '5', '4.00', '8', '1', '5.00', '6', '9', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-540', '开发示例540', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '5', '1.00', '5', '8', '3.00', '4', '7', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-541', '开发示例541', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '2', '8.00', '2', '3', '9.00', '1', '4', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-542', '开发示例542', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '9', '8.00', '5', '3', '1.00', '7', '7', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-543', '开发示例543', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '4', '6.00', '8', '5', '6.00', '5', '5', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-544', '开发示例544', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '5', '2.00', '2', '7', '9.00', '8', '3', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-545', '开发示例545', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '1', '1.00', '1', '3', '6.00', '8', '9', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-546', '开发示例546', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '3', '1.00', '9', '9', '8.00', '2', '6', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-547', '开发示例547', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '8', '5', '6.00', '7', '5', '4.00', '4', '2', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-548', '开发示例548', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '9', '2.00', '2', '2', '4.00', '3', '4', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-549', '开发示例549', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '4', '6', '2.00', '3', '7', '1.00', '7', '4', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-55', '开发示例55', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '1', '9.00', '1', '6', '6.00', '9', '6', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-550', '开发示例550', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '5', '1.00', '4', '4', '4.00', '5', '5', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-551', '开发示例551', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '6', '4', '1.00', '4', '3', '4.00', '1', '5', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-552', '开发示例552', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '1', '9.00', '1', '6', '3.00', '5', '5', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-553', '开发示例553', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '2', '9', '7.00', '4', '1', '7.00', '5', '3', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-554', '开发示例554', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '1', '3.00', '3', '9', '6.00', '2', '7', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-555', '开发示例555', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '9', '9.00', '4', '5', '1.00', '4', '8', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-556', '开发示例556', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '5', '2', '5.00', '1', '6', '1.00', '4', '4', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-557', '开发示例557', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '2', '4.00', '6', '2', '7.00', '6', '3', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-558', '开发示例558', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '6', '6.00', '1', '6', '2.00', '7', '8', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-559', '开发示例559', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '5', '1.00', '2', '9', '2.00', '4', '9', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-56', '开发示例56', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '5', '7.00', '2', '3', '9.00', '5', '1', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-560', '开发示例560', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '9', '6.00', '8', '2', '6.00', '8', '2', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-561', '开发示例561', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '7', '7', '8.00', '6', '9', '9.00', '5', '5', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-562', '开发示例562', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '5', '7', '4.00', '7', '5', '1.00', '7', '8', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-563', '开发示例563', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '9', '2', '4.00', '8', '7', '2.00', '4', '5', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-564', '开发示例564', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '1', '9.00', '2', '8', '8.00', '4', '3', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-565', '开发示例565', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '9', '5.00', '1', '4', '6.00', '6', '6', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-566', '开发示例566', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '4', '4', '7.00', '6', '7', '6.00', '7', '8', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-567', '开发示例567', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '7', '6.00', '9', '1', '2.00', '7', '5', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-568', '开发示例568', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '8', '2', '1.00', '6', '4', '4.00', '9', '8', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-569', '开发示例569', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '2', '7.00', '2', '9', '9.00', '7', '6', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-57', '开发示例57', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '6', '1.00', '1', '6', '4.00', '3', '3', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-570', '开发示例570', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '1', '1.00', '2', '3', '2.00', '5', '6', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-571', '开发示例571', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '3', '9.00', '4', '1', '5.00', '3', '1', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-572', '开发示例572', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '6', '2.00', '4', '4', '2.00', '5', '5', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-573', '开发示例573', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '2', '6.00', '6', '3', '1.00', '3', '4', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-574', '开发示例574', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '3', '2', '6.00', '6', '4', '5.00', '1', '8', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-575', '开发示例575', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '9', '9.00', '6', '9', '4.00', '4', '4', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-576', '开发示例576', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '3', '4.00', '8', '3', '3.00', '6', '9', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-577', '开发示例577', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '1', '7', '6.00', '3', '5', '1.00', '2', '2', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-578', '开发示例578', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '5', '5.00', '3', '2', '9.00', '1', '5', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-579', '开发示例579', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '2', '2', '7.00', '5', '6', '4.00', '7', '7', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-58', '开发示例58', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '5', '6.00', '1', '5', '9.00', '6', '7', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-580', '开发示例580', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '5', '9', '8.00', '9', '5', '4.00', '8', '3', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-581', '开发示例581', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '6', '7.00', '1', '4', '9.00', '2', '5', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-582', '开发示例582', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '5', '4.00', '6', '1', '8.00', '8', '8', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-583', '开发示例583', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '5', '7.00', '7', '5', '9.00', '4', '9', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-584', '开发示例584', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '3', '7.00', '9', '8', '2.00', '6', '1', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-585', '开发示例585', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '4', '6.00', '4', '6', '6.00', '1', '4', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-586', '开发示例586', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '7', '1.00', '3', '6', '5.00', '9', '6', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-587', '开发示例587', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '7', '6.00', '3', '9', '8.00', '4', '4', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-588', '开发示例588', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '6', '4.00', '9', '8', '2.00', '2', '5', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-589', '开发示例589', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '9', '6.00', '9', '3', '1.00', '4', '7', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-59', '开发示例59', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '3', '2.00', '2', '3', '3.00', '6', '2', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-590', '开发示例590', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '5', '3.00', '7', '7', '5.00', '1', '1', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-591', '开发示例591', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '1', '2', '1.00', '3', '8', '1.00', '1', '9', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-592', '开发示例592', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '4', '7.00', '5', '8', '2.00', '1', '9', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-593', '开发示例593', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '6', '8.00', '5', '7', '9.00', '3', '1', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-594', '开发示例594', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '5', '2', '3.00', '9', '2', '5.00', '5', '8', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-595', '开发示例595', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '2', '2.00', '7', '6', '2.00', '6', '8', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-596', '开发示例596', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '8', '7', '8.00', '5', '2', '2.00', '4', '2', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-597', '开发示例597', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '4', '9.00', '9', '9', '8.00', '5', '6', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-598', '开发示例598', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '7', '2', '6.00', '7', '3', '4.00', '6', '4', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-599', '开发示例599', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '5', '9.00', '3', '8', '6.00', '5', '1', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-6', '开发示例6', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '7', '1', '2.00', '4', '6', '6.00', '4', '5', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-60', '开发示例60', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '2', '4.00', '7', '8', '8.00', '4', '2', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-600', '开发示例600', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '5', '7', '8.00', '5', '7', '9.00', '6', '4', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-601', '开发示例601', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '6', '4.00', '4', '7', '5.00', '2', '5', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-602', '开发示例602', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '5', '8.00', '3', '6', '2.00', '9', '9', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-603', '开发示例603', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '6', '7.00', '5', '2', '6.00', '3', '3', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-604', '开发示例604', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '5', '6.00', '9', '7', '7.00', '6', '9', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-605', '开发示例605', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '8', '4.00', '6', '2', '5.00', '6', '8', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-606', '开发示例606', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '8', '2', '3.00', '6', '3', '2.00', '9', '5', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-607', '开发示例607', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '8', '8', '8.00', '5', '6', '9.00', '9', '5', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-608', '开发示例608', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '4', '9', '1.00', '9', '2', '6.00', '5', '8', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-609', '开发示例609', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '9', '8.00', '3', '2', '3.00', '1', '2', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-61', '开发示例61', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '2', '8.00', '6', '5', '4.00', '9', '4', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-610', '开发示例610', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '3', '2.00', '1', '4', '2.00', '7', '3', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-611', '开发示例611', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '9', '2', '4.00', '6', '4', '7.00', '4', '6', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-612', '开发示例612', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '2', '6', '5.00', '8', '5', '3.00', '1', '3', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-613', '开发示例613', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '8', '9.00', '6', '4', '3.00', '6', '2', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-614', '开发示例614', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '1', '1.00', '7', '3', '1.00', '2', '6', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-615', '开发示例615', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '5', '5', '1.00', '6', '7', '1.00', '6', '2', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-616', '开发示例616', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '7', '7', '8.00', '2', '5', '4.00', '3', '5', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-617', '开发示例617', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '5', '3.00', '7', '3', '8.00', '1', '9', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-618', '开发示例618', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '2', '2.00', '3', '5', '6.00', '4', '7', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-619', '开发示例619', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '2', '8.00', '7', '7', '4.00', '2', '9', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-62', '开发示例62', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '5', '4.00', '7', '5', '3.00', '9', '9', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-620', '开发示例620', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '4', '4.00', '4', '3', '1.00', '2', '4', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-621', '开发示例621', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '1', '9.00', '3', '9', '9.00', '5', '2', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-622', '开发示例622', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '3', '9.00', '6', '8', '6.00', '8', '3', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-623', '开发示例623', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '5', '6', '2.00', '9', '3', '8.00', '7', '6', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-624', '开发示例624', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '1', '4', '4.00', '4', '7', '4.00', '8', '6', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-625', '开发示例625', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '4', '1.00', '6', '7', '5.00', '2', '7', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-626', '开发示例626', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '4', '5', '6.00', '3', '9', '3.00', '9', '9', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-627', '开发示例627', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '6', '8.00', '7', '5', '5.00', '1', '5', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-628', '开发示例628', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '2', '8.00', '1', '3', '8.00', '8', '1', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-629', '开发示例629', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '7', '5.00', '2', '8', '9.00', '1', '4', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-63', '开发示例63', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '7', '8.00', '8', '4', '3.00', '5', '2', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-630', '开发示例630', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '7', '3.00', '8', '7', '1.00', '3', '1', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-631', '开发示例631', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '3', '5', '1.00', '1', '5', '8.00', '1', '8', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-632', '开发示例632', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '5', '8.00', '3', '2', '8.00', '9', '9', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-633', '开发示例633', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '8', '1.00', '8', '2', '6.00', '3', '7', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-634', '开发示例634', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '6', '6.00', '7', '3', '7.00', '2', '8', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-635', '开发示例635', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '5', '9.00', '3', '2', '9.00', '3', '4', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-636', '开发示例636', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '5', '9', '6.00', '6', '2', '1.00', '3', '4', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-637', '开发示例637', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '8', '5', '9.00', '4', '2', '3.00', '8', '2', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-638', '开发示例638', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '7', '5.00', '6', '6', '3.00', '7', '9', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-639', '开发示例639', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '7', '1.00', '9', '5', '6.00', '5', '4', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-64', '开发示例64', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '4', '5', '6.00', '1', '5', '5.00', '7', '1', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-640', '开发示例640', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '7', '2.00', '6', '7', '4.00', '3', '7', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-641', '开发示例641', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '1', '7.00', '7', '3', '7.00', '3', '4', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-642', '开发示例642', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '7', '5.00', '5', '5', '7.00', '3', '9', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-643', '开发示例643', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '2', '6.00', '5', '2', '5.00', '2', '4', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-644', '开发示例644', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '8', '7', '9.00', '8', '4', '5.00', '2', '1', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-645', '开发示例645', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '2', '3.00', '7', '2', '4.00', '2', '4', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-646', '开发示例646', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '6', '9.00', '3', '1', '5.00', '9', '9', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-647', '开发示例647', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '7', '1.00', '2', '5', '9.00', '4', '9', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-648', '开发示例648', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '5', '2.00', '5', '9', '9.00', '2', '8', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-649', '开发示例649', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '8', '5', '5.00', '9', '5', '2.00', '9', '7', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-65', '开发示例65', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '4', '4.00', '5', '9', '1.00', '4', '1', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-650', '开发示例650', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '4', '8.00', '2', '1', '7.00', '4', '6', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-651', '开发示例651', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '5', '3.00', '5', '7', '4.00', '3', '5', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-652', '开发示例652', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '2', '1', '7.00', '9', '4', '8.00', '4', '5', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-653', '开发示例653', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '5', '6', '6.00', '1', '4', '9.00', '2', '8', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-654', '开发示例654', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '4', '5.00', '6', '9', '8.00', '5', '2', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-655', '开发示例655', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '3', '7.00', '4', '2', '3.00', '1', '7', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-656', '开发示例656', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '9', '5.00', '2', '8', '3.00', '3', '8', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-657', '开发示例657', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '2', '7.00', '3', '1', '2.00', '7', '9', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-658', '开发示例658', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '3', '3', '4.00', '2', '4', '7.00', '4', '2', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-659', '开发示例659', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '5', '1', '9.00', '4', '3', '1.00', '1', '3', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-66', '开发示例66', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '1', '5.00', '8', '4', '3.00', '6', '9', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-660', '开发示例660', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '8', '2.00', '1', '8', '9.00', '4', '3', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-661', '开发示例661', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '4', '8', '4.00', '6', '2', '4.00', '7', '8', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-662', '开发示例662', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '4', '8', '8.00', '6', '3', '7.00', '9', '4', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-663', '开发示例663', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '9', '3.00', '2', '5', '3.00', '8', '4', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-664', '开发示例664', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '7', '9.00', '2', '7', '5.00', '7', '4', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-665', '开发示例665', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '5', '9.00', '8', '6', '4.00', '6', '4', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-666', '开发示例666', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '6', '7', '5.00', '7', '6', '1.00', '8', '4', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-667', '开发示例667', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '9', '2.00', '9', '1', '3.00', '2', '4', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-668', '开发示例668', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '5', '7', '6.00', '6', '1', '3.00', '1', '6', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-669', '开发示例669', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '8', '4.00', '7', '4', '9.00', '4', '8', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-67', '开发示例67', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '5', '4', '9.00', '1', '5', '8.00', '2', '3', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-670', '开发示例670', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '7', '8', '9.00', '5', '2', '2.00', '1', '5', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-671', '开发示例671', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '2', '3.00', '9', '8', '5.00', '3', '7', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-672', '开发示例672', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '5', '2.00', '3', '8', '6.00', '4', '7', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-673', '开发示例673', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '8', '9.00', '5', '6', '1.00', '2', '8', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-674', '开发示例674', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '8', '9.00', '5', '6', '2.00', '2', '4', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-675', '开发示例675', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '3', '3.00', '2', '1', '7.00', '8', '4', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-676', '开发示例676', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '4', '4.00', '8', '8', '7.00', '2', '2', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-677', '开发示例677', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '7', '5.00', '2', '2', '9.00', '3', '7', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-678', '开发示例678', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '9', '6.00', '5', '9', '9.00', '2', '1', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-679', '开发示例679', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '4', '2', '4.00', '4', '3', '5.00', '1', '9', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-68', '开发示例68', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '5', '3', '1.00', '5', '8', '7.00', '7', '7', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-680', '开发示例680', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '8', '8.00', '7', '4', '5.00', '4', '7', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-681', '开发示例681', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '5', '1.00', '1', '6', '6.00', '4', '4', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-682', '开发示例682', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '7', '6.00', '2', '3', '6.00', '6', '1', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-683', '开发示例683', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '5', '8.00', '3', '1', '6.00', '8', '1', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-684', '开发示例684', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '4', '2', '1.00', '5', '2', '4.00', '9', '4', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-685', '开发示例685', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '4', '1', '5.00', '1', '1', '3.00', '6', '7', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-686', '开发示例686', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '4', '8.00', '1', '7', '7.00', '8', '4', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-687', '开发示例687', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '2', '8.00', '2', '2', '1.00', '4', '2', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-688', '开发示例688', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '9', '1.00', '8', '1', '4.00', '1', '4', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-689', '开发示例689', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '1', '2', '2.00', '1', '9', '1.00', '8', '2', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-69', '开发示例69', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '5', '3.00', '7', '7', '5.00', '3', '9', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-690', '开发示例690', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '4', '8.00', '5', '7', '3.00', '1', '7', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-691', '开发示例691', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '6', '3.00', '7', '3', '3.00', '4', '8', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-692', '开发示例692', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '6', '8.00', '6', '1', '9.00', '5', '3', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-693', '开发示例693', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '7', '4.00', '7', '5', '2.00', '4', '3', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-694', '开发示例694', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '5', '2.00', '3', '6', '2.00', '6', '8', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-695', '开发示例695', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '6', '2.00', '3', '2', '4.00', '9', '6', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-696', '开发示例696', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '9', '9.00', '8', '3', '4.00', '7', '8', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-697', '开发示例697', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '9', '8', '9.00', '8', '1', '6.00', '1', '6', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-698', '开发示例698', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '9', '3', '8.00', '2', '6', '8.00', '9', '2', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-699', '开发示例699', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '3', '8.00', '5', '7', '5.00', '1', '8', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-7', '开发示例7', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '8', '8', '9.00', '9', '8', '9.00', '4', '1', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-70', '开发示例70', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '9', '8.00', '6', '7', '1.00', '3', '8', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-700', '开发示例700', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '2', '9', '1.00', '1', '4', '9.00', '5', '8', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-701', '开发示例701', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '1', '5.00', '9', '3', '3.00', '3', '8', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-702', '开发示例702', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '4', '9', '7.00', '9', '4', '9.00', '1', '1', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-703', '开发示例703', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '3', '5.00', '9', '9', '7.00', '5', '4', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-704', '开发示例704', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '9', '2.00', '6', '5', '1.00', '4', '3', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-705', '开发示例705', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '5', '4.00', '4', '5', '6.00', '5', '5', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-706', '开发示例706', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '5', '7.00', '3', '5', '6.00', '7', '3', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-707', '开发示例707', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '8', '1', '7.00', '4', '6', '1.00', '2', '4', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-708', '开发示例708', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '8', '5.00', '5', '2', '2.00', '4', '3', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-709', '开发示例709', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '6', '2.00', '5', '7', '9.00', '2', '3', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-71', '开发示例71', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '5', '3', '2.00', '3', '6', '9.00', '5', '4', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-710', '开发示例710', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '6', '8', '3.00', '9', '3', '1.00', '5', '4', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-711', '开发示例711', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '3', '9.00', '2', '5', '8.00', '2', '3', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-712', '开发示例712', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '5', '8', '4.00', '3', '3', '1.00', '9', '9', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-713', '开发示例713', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '4', '7.00', '1', '4', '5.00', '2', '9', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-714', '开发示例714', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '5', '8', '7.00', '9', '9', '1.00', '4', '2', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-715', '开发示例715', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '8', '3.00', '2', '7', '9.00', '4', '6', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-716', '开发示例716', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '5', '7.00', '4', '3', '7.00', '5', '9', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-717', '开发示例717', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '3', '8.00', '8', '9', '3.00', '7', '1', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-718', '开发示例718', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '2', '6.00', '1', '2', '6.00', '6', '7', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-719', '开发示例719', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '9', '9.00', '3', '2', '7.00', '4', '5', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-72', '开发示例72', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '5', '3.00', '8', '5', '4.00', '5', '5', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-720', '开发示例720', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '2', '8.00', '5', '4', '1.00', '9', '4', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-721', '开发示例721', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '4', '9.00', '8', '3', '5.00', '4', '7', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-722', '开发示例722', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '7', '7', '6.00', '3', '6', '3.00', '8', '5', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-723', '开发示例723', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '9', '5', '2.00', '1', '3', '2.00', '4', '9', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-724', '开发示例724', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '4', '2', '9.00', '3', '7', '7.00', '1', '8', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-725', '开发示例725', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '1', '3.00', '8', '8', '4.00', '9', '7', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-726', '开发示例726', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '3', '3.00', '2', '7', '8.00', '9', '5', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-727', '开发示例727', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '4', '8', '2.00', '3', '5', '3.00', '4', '9', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-728', '开发示例728', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '1', '5.00', '5', '3', '5.00', '6', '3', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-729', '开发示例729', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '1', '6', '7.00', '4', '8', '3.00', '5', '8', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-73', '开发示例73', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '3', '4.00', '6', '3', '2.00', '7', '2', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-730', '开发示例730', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '5', '3', '6.00', '9', '2', '9.00', '9', '5', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-731', '开发示例731', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '8', '6.00', '2', '1', '8.00', '5', '4', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-732', '开发示例732', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '8', '1', '4.00', '4', '1', '1.00', '4', '7', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-733', '开发示例733', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '2', '7.00', '7', '4', '3.00', '2', '4', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-734', '开发示例734', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '5', '2', '8.00', '7', '4', '7.00', '8', '6', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-735', '开发示例735', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '6', '2.00', '3', '9', '3.00', '6', '5', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-736', '开发示例736', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '7', '2.00', '9', '6', '7.00', '1', '7', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-737', '开发示例737', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '4', '3.00', '2', '3', '1.00', '7', '6', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-738', '开发示例738', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '1', '5.00', '6', '5', '9.00', '4', '6', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-739', '开发示例739', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '8', '2', '4.00', '9', '5', '5.00', '6', '1', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-74', '开发示例74', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '2', '4', '8.00', '1', '4', '3.00', '3', '6', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-740', '开发示例740', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '4', '8.00', '8', '6', '9.00', '4', '8', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-741', '开发示例741', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '3', '3.00', '1', '7', '8.00', '2', '8', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-742', '开发示例742', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '6', '8.00', '2', '1', '8.00', '1', '3', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-743', '开发示例743', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '5', '3', '1.00', '4', '7', '5.00', '9', '4', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-744', '开发示例744', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '3', '6', '7.00', '7', '5', '6.00', '1', '1', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-745', '开发示例745', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '6', '6', '6.00', '2', '1', '2.00', '5', '5', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-746', '开发示例746', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '2', '4', '9.00', '9', '2', '5.00', '3', '7', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-747', '开发示例747', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '2', '1.00', '8', '9', '6.00', '4', '8', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-748', '开发示例748', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '5', '2.00', '7', '2', '4.00', '6', '3', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-749', '开发示例749', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '3', '2.00', '1', '5', '9.00', '4', '6', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-75', '开发示例75', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '9', '9.00', '1', '7', '1.00', '9', '8', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-750', '开发示例750', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '9', '7.00', '7', '1', '4.00', '4', '5', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-751', '开发示例751', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '7', '9.00', '1', '6', '9.00', '5', '3', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-752', '开发示例752', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '7', '2', '5.00', '2', '5', '8.00', '4', '3', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-753', '开发示例753', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '9', '2.00', '5', '4', '1.00', '3', '2', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-754', '开发示例754', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '9', '1.00', '9', '8', '5.00', '9', '4', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-755', '开发示例755', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '4', '9.00', '4', '8', '2.00', '3', '6', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-756', '开发示例756', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '4', '4', '9.00', '5', '9', '9.00', '9', '4', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-757', '开发示例757', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '2', '1', '5.00', '7', '6', '7.00', '7', '3', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-758', '开发示例758', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '2', '1.00', '9', '7', '3.00', '9', '6', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-759', '开发示例759', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '9', '4.00', '5', '4', '1.00', '8', '9', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-76', '开发示例76', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '4', '5.00', '9', '2', '6.00', '2', '3', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-760', '开发示例760', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '7', '3.00', '5', '8', '9.00', '3', '2', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-761', '开发示例761', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '8', '6.00', '8', '3', '1.00', '2', '8', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-762', '开发示例762', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '8', '7', '4.00', '9', '8', '1.00', '8', '4', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-763', '开发示例763', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '3', '8.00', '3', '6', '8.00', '2', '3', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-764', '开发示例764', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '3', '7.00', '6', '8', '3.00', '5', '5', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-765', '开发示例765', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '7', '7.00', '8', '8', '7.00', '2', '7', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-766', '开发示例766', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '8', '8', '2.00', '4', '7', '7.00', '1', '7', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-767', '开发示例767', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '4', '7', '8.00', '9', '2', '5.00', '1', '8', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-768', '开发示例768', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '1', '6', '5.00', '7', '2', '9.00', '6', '7', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-769', '开发示例769', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '3', '5.00', '1', '3', '6.00', '7', '9', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-77', '开发示例77', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '2', '9', '3.00', '5', '2', '2.00', '6', '7', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-770', '开发示例770', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '8', '2.00', '9', '2', '9.00', '1', '3', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-771', '开发示例771', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '5', '1.00', '2', '7', '6.00', '7', '2', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-772', '开发示例772', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '7', '1.00', '9', '1', '8.00', '9', '7', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-773', '开发示例773', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '5', '1.00', '3', '7', '4.00', '7', '5', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-774', '开发示例774', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '1', '1.00', '3', '4', '3.00', '8', '2', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-775', '开发示例775', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '3', '7.00', '6', '7', '6.00', '2', '3', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-776', '开发示例776', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '9', '7.00', '1', '3', '2.00', '1', '9', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-777', '开发示例777', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '4', '6', '5.00', '1', '4', '9.00', '8', '1', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-778', '开发示例778', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '5', '1', '1.00', '3', '1', '5.00', '4', '1', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-779', '开发示例779', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '4', '3.00', '2', '1', '2.00', '5', '2', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-78', '开发示例78', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '5', '7.00', '1', '4', '4.00', '3', '9', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-780', '开发示例780', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '5', '6', '3.00', '3', '1', '5.00', '8', '3', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-781', '开发示例781', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '7', '3.00', '2', '5', '5.00', '5', '2', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-782', '开发示例782', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '3', '3.00', '8', '8', '8.00', '5', '9', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-783', '开发示例783', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '8', '6.00', '9', '5', '3.00', '4', '6', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-784', '开发示例784', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '4', '4', '2.00', '2', '2', '6.00', '4', '6', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-785', '开发示例785', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '8', '8.00', '4', '8', '6.00', '1', '7', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-786', '开发示例786', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '6', '8.00', '6', '3', '7.00', '6', '6', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-787', '开发示例787', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '9', '1.00', '2', '6', '8.00', '3', '8', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-788', '开发示例788', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '6', '3.00', '6', '6', '6.00', '2', '4', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-789', '开发示例789', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '1', '2.00', '3', '5', '9.00', '1', '2', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-79', '开发示例79', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '8', '8', '4.00', '2', '8', '8.00', '8', '3', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-790', '开发示例790', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '8', '9', '1.00', '1', '8', '9.00', '5', '7', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-791', '开发示例791', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '4', '3', '9.00', '2', '5', '4.00', '2', '4', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-792', '开发示例792', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '2', '1.00', '7', '8', '6.00', '1', '3', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-793', '开发示例793', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '9', '2.00', '4', '7', '5.00', '6', '1', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-794', '开发示例794', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '2', '9', '6.00', '9', '9', '3.00', '3', '9', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-795', '开发示例795', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '2', '2', '2.00', '1', '2', '1.00', '1', '7', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-796', '开发示例796', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '2', '7', '4.00', '7', '2', '4.00', '9', '3', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-797', '开发示例797', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '9', '9.00', '7', '3', '8.00', '5', '5', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-798', '开发示例798', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '7', '4', '9.00', '9', '4', '6.00', '4', '6', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-799', '开发示例799', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '7', '2.00', '5', '9', '7.00', '7', '2', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-8', '开发示例8', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '2', '8.00', '4', '6', '8.00', '8', '1', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-80', '开发示例80', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '5', '1', '9.00', '3', '3', '9.00', '5', '9', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-800', '开发示例800', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '3', '2.00', '5', '7', '9.00', '7', '9', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-801', '开发示例801', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '4', '9', '4.00', '2', '4', '3.00', '8', '5', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-802', '开发示例802', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '8', '3.00', '3', '1', '1.00', '7', '7', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-803', '开发示例803', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '4', '3.00', '2', '8', '1.00', '4', '6', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-804', '开发示例804', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '9', '7', '3.00', '2', '3', '3.00', '7', '6', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-805', '开发示例805', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '2', '6', '2.00', '8', '1', '7.00', '2', '5', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-806', '开发示例806', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '5', '1.00', '9', '3', '6.00', '6', '3', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-807', '开发示例807', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '8', '1.00', '4', '3', '2.00', '2', '4', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-808', '开发示例808', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '9', '4', '6.00', '8', '9', '3.00', '8', '7', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-809', '开发示例809', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '9', '1.00', '7', '5', '2.00', '6', '9', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-81', '开发示例81', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '9', '1.00', '5', '7', '1.00', '6', '1', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-810', '开发示例810', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '5', '6.00', '8', '4', '9.00', '1', '2', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-811', '开发示例811', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '6', '2.00', '4', '3', '5.00', '7', '4', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-812', '开发示例812', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '2', '8.00', '5', '9', '6.00', '6', '2', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-813', '开发示例813', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '5', '1', '1.00', '4', '1', '6.00', '6', '7', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-814', '开发示例814', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '8', '8', '1.00', '8', '2', '8.00', '3', '3', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-815', '开发示例815', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '2', '9.00', '2', '9', '7.00', '5', '2', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-816', '开发示例816', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '7', '9.00', '5', '7', '6.00', '9', '1', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-817', '开发示例817', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '5', '2.00', '5', '1', '3.00', '2', '8', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-818', '开发示例818', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '9', '2.00', '3', '1', '4.00', '3', '3', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-819', '开发示例819', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '8', '9.00', '1', '4', '1.00', '6', '5', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-82', '开发示例82', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '5', '2.00', '8', '9', '4.00', '9', '6', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-820', '开发示例820', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '1', '6.00', '2', '5', '1.00', '5', '4', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-821', '开发示例821', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '2', '8.00', '1', '8', '4.00', '7', '4', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-822', '开发示例822', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '1', '4.00', '8', '9', '5.00', '3', '6', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-823', '开发示例823', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '4', '6.00', '2', '4', '6.00', '7', '6', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-824', '开发示例824', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '3', '1.00', '7', '7', '3.00', '1', '6', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-825', '开发示例825', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '5', '9', '1.00', '6', '6', '7.00', '1', '2', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-826', '开发示例826', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '3', '2.00', '4', '2', '8.00', '5', '2', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-827', '开发示例827', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '4', '9', '3.00', '1', '4', '7.00', '1', '8', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-828', '开发示例828', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '1', '7.00', '2', '4', '1.00', '1', '3', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-829', '开发示例829', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '8', '5.00', '6', '5', '5.00', '6', '6', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-83', '开发示例83', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '5', '8', '3.00', '8', '2', '9.00', '2', '6', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-830', '开发示例830', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '2', '8.00', '7', '7', '1.00', '6', '4', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-831', '开发示例831', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '3', '5', '1.00', '7', '4', '9.00', '1', '1', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-832', '开发示例832', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '1', '2', '1.00', '2', '2', '1.00', '2', '4', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-833', '开发示例833', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '4', '3.00', '8', '1', '6.00', '7', '3', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-834', '开发示例834', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '5', '3', '8.00', '4', '8', '4.00', '6', '2', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-835', '开发示例835', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '6', '2.00', '1', '7', '7.00', '2', '9', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-836', '开发示例836', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '1', '2.00', '2', '8', '3.00', '7', '5', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-837', '开发示例837', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '3', '6.00', '4', '6', '5.00', '9', '1', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-838', '开发示例838', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '4', '7.00', '1', '8', '2.00', '1', '8', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-839', '开发示例839', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '3', '5', '6.00', '5', '8', '7.00', '4', '9', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-84', '开发示例84', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '3', '3.00', '8', '9', '6.00', '9', '1', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-840', '开发示例840', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '7', '4.00', '2', '1', '5.00', '8', '1', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-841', '开发示例841', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '6', '7.00', '2', '1', '1.00', '3', '6', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-842', '开发示例842', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '3', '8.00', '3', '8', '4.00', '9', '6', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-843', '开发示例843', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '1', '8.00', '3', '7', '3.00', '3', '1', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-844', '开发示例844', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '5', '8', '3.00', '5', '2', '9.00', '9', '1', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-845', '开发示例845', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '5', '3', '9.00', '4', '6', '6.00', '5', '3', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-846', '开发示例846', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '6', '7', '9.00', '6', '9', '4.00', '3', '4', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-847', '开发示例847', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '8', '5.00', '7', '6', '8.00', '4', '2', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-848', '开发示例848', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '1', '9', '9.00', '8', '8', '2.00', '8', '1', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-849', '开发示例849', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '2', '9', '7.00', '4', '3', '9.00', '2', '8', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-85', '开发示例85', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '3', '3.00', '7', '7', '4.00', '4', '1', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-850', '开发示例850', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '1', '1', '9.00', '5', '9', '5.00', '2', '9', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-851', '开发示例851', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '3', '7', '1.00', '8', '4', '7.00', '1', '6', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-852', '开发示例852', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '1', '4', '4.00', '5', '4', '8.00', '3', '3', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-853', '开发示例853', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '7', '1.00', '5', '4', '8.00', '9', '5', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-854', '开发示例854', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '3', '9.00', '2', '4', '8.00', '3', '4', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-855', '开发示例855', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '9', '7.00', '3', '3', '6.00', '6', '5', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-856', '开发示例856', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '8', '1.00', '5', '8', '9.00', '2', '7', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-857', '开发示例857', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '6', '1.00', '4', '4', '5.00', '3', '4', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-858', '开发示例858', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '2', '1.00', '2', '2', '8.00', '5', '8', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-859', '开发示例859', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '7', '7.00', '9', '1', '8.00', '6', '4', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-86', '开发示例86', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '2', '5.00', '5', '2', '4.00', '9', '2', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-860', '开发示例860', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '5', '7', '6.00', '8', '1', '4.00', '5', '3', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-861', '开发示例861', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '7', '6.00', '5', '9', '1.00', '7', '4', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-862', '开发示例862', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '5', '8.00', '9', '2', '4.00', '6', '1', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-863', '开发示例863', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '9', '5.00', '6', '5', '4.00', '3', '4', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-864', '开发示例864', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '6', '9', '8.00', '4', '2', '5.00', '1', '3', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-865', '开发示例865', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '9', '4.00', '3', '1', '6.00', '2', '7', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-866', '开发示例866', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '1', '4.00', '9', '5', '2.00', '4', '3', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-867', '开发示例867', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '5', '7.00', '7', '5', '4.00', '6', '6', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-868', '开发示例868', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '3', '4', '6.00', '4', '4', '6.00', '6', '5', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-869', '开发示例869', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '1', '8.00', '2', '7', '2.00', '1', '7', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-87', '开发示例87', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '4', '7.00', '7', '5', '8.00', '8', '3', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-870', '开发示例870', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '6', '7', '5.00', '6', '3', '7.00', '4', '1', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-871', '开发示例871', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '5', '5', '5.00', '8', '5', '3.00', '8', '8', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-872', '开发示例872', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '6', '5.00', '4', '7', '8.00', '5', '1', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-873', '开发示例873', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '5', '3', '6.00', '2', '7', '8.00', '3', '9', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-874', '开发示例874', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '8', '2.00', '5', '4', '6.00', '3', '8', '111', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-875', '开发示例875', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '2', '2', '1.00', '4', '5', '1.00', '1', '4', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-876', '开发示例876', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '5', '4.00', '7', '8', '9.00', '6', '4', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-877', '开发示例877', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '3', '2.00', '5', '9', '5.00', '3', '7', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-878', '开发示例878', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '5', '8', '5.00', '6', '7', '4.00', '5', '3', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-879', '开发示例879', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '7', '5.00', '7', '8', '6.00', '3', '9', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-88', '开发示例88', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '5', '8.00', '2', '7', '1.00', '8', '2', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-880', '开发示例880', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '4', '5.00', '4', '3', '6.00', '4', '3', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-881', '开发示例881', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '9', '4', '6.00', '9', '3', '8.00', '7', '5', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-882', '开发示例882', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '4', '3.00', '8', '2', '9.00', '2', '2', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-883', '开发示例883', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '6', '5.00', '2', '2', '6.00', '6', '6', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-884', '开发示例884', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '1', '2.00', '8', '2', '6.00', '4', '1', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-885', '开发示例885', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '8', '4', '4.00', '9', '5', '2.00', '6', '5', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-886', '开发示例886', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '9', '2.00', '2', '1', '7.00', '9', '4', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-887', '开发示例887', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '4', '2', '4.00', '1', '2', '7.00', '6', '7', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-888', '开发示例888', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '1', '4.00', '7', '4', '2.00', '5', '5', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-889', '开发示例889', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '4', '9', '9.00', '1', '2', '4.00', '1', '2', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-89', '开发示例89', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '2', '6.00', '3', '3', '6.00', '4', '7', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-890', '开发示例890', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '5', '2.00', '9', '4', '3.00', '5', '8', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-891', '开发示例891', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '5', '2.00', '5', '4', '8.00', '9', '7', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-892', '开发示例892', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '3', '9.00', '8', '5', '2.00', '3', '4', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-893', '开发示例893', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '5', '1', '2.00', '8', '7', '2.00', '7', '9', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-894', '开发示例894', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '5', '9', '7.00', '6', '2', '8.00', '9', '7', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-895', '开发示例895', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '3', '8.00', '9', '9', '7.00', '6', '6', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-896', '开发示例896', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '5', '9.00', '6', '6', '4.00', '3', '1', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-897', '开发示例897', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '4', '4', '9.00', '6', '8', '6.00', '5', '1', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-898', '开发示例898', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '5', '1', '1.00', '6', '6', '5.00', '9', '5', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-899', '开发示例899', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '8', '4', '9.00', '4', '6', '3.00', '5', '9', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-9', '开发示例9', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '9', '1.00', '8', '6', '1.00', '6', '2', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-90', '开发示例90', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '3', '7', '7.00', '9', '5', '3.00', '8', '1', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-900', '开发示例900', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '9', '1', '3.00', '6', '9', '5.00', '6', '9', '212', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-901', '开发示例901', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '4', '1', '7.00', '6', '9', '4.00', '1', '5', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-902', '开发示例902', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '6', '1.00', '7', '1', '4.00', '8', '4', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-903', '开发示例903', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '7', '1', '3.00', '1', '6', '3.00', '4', '6', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-904', '开发示例904', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '9', '3.00', '3', '7', '8.00', '5', '9', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-905', '开发示例905', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '1', '7.00', '8', '1', '6.00', '8', '4', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-906', '开发示例906', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '4', '6.00', '4', '1', '4.00', '4', '1', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-907', '开发示例907', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '4', '5.00', '7', '1', '1.00', '9', '3', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-908', '开发示例908', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '8', '4', '5.00', '8', '7', '1.00', '6', '2', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-909', '开发示例909', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '3', '2.00', '8', '5', '9.00', '2', '7', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-91', '开发示例91', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '1', '2.00', '5', '7', '7.00', '8', '5', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-910', '开发示例910', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '2', '8', '1.00', '7', '2', '2.00', '8', '7', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-911', '开发示例911', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '6', '5.00', '6', '4', '9.00', '5', '7', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-912', '开发示例912', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '7', '3', '8.00', '8', '5', '4.00', '6', '4', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-913', '开发示例913', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '6', '8', '3.00', '7', '6', '8.00', '7', '2', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-914', '开发示例914', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '1', '7.00', '6', '4', '5.00', '7', '6', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-915', '开发示例915', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '3', '4.00', '9', '1', '4.00', '8', '8', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-916', '开发示例916', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '6', '2.00', '6', '8', '7.00', '2', '2', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-917', '开发示例917', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '2', '9.00', '4', '8', '6.00', '6', '2', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-918', '开发示例918', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '2', '8.00', '2', '1', '9.00', '7', '3', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-919', '开发示例919', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '6', '7', '7.00', '1', '6', '2.00', '6', '8', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-92', '开发示例92', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '4', '5.00', '9', '6', '9.00', '9', '9', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-920', '开发示例920', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '4', '3.00', '5', '4', '2.00', '6', '4', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-921', '开发示例921', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '3', '9', '8.00', '8', '1', '2.00', '1', '9', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-922', '开发示例922', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '1', '5.00', '8', '8', '1.00', '2', '2', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-923', '开发示例923', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '8', '4.00', '1', '2', '9.00', '1', '5', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-924', '开发示例924', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '7', '8.00', '2', '3', '6.00', '3', '5', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-925', '开发示例925', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '5', '1', '7.00', '6', '7', '4.00', '2', '5', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-926', '开发示例926', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '7', '6.00', '1', '7', '1.00', '3', '8', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-927', '开发示例927', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '8', '1.00', '9', '9', '5.00', '3', '2', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-928', '开发示例928', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '6', '2.00', '1', '5', '8.00', '8', '5', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-929', '开发示例929', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '4', '6', '9.00', '9', '2', '8.00', '2', '4', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-93', '开发示例93', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '4', '3', '6.00', '4', '3', '9.00', '7', '5', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-930', '开发示例930', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '6', '1', '6.00', '2', '2', '1.00', '7', '6', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-931', '开发示例931', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '3', '6', '9.00', '7', '5', '3.00', '9', '1', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-932', '开发示例932', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '3', '7.00', '8', '6', '8.00', '3', '8', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-933', '开发示例933', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '4', '8', '7.00', '2', '8', '4.00', '7', '1', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-934', '开发示例934', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '6', '4.00', '6', '2', '3.00', '5', '8', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-935', '开发示例935', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '1', '8.00', '8', '6', '9.00', '2', '6', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-936', '开发示例936', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '7', '5.00', '9', '2', '4.00', '2', '3', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-937', '开发示例937', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '2', '9', '5.00', '1', '6', '3.00', '1', '1', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-938', '开发示例938', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '4', '6', '1.00', '3', '7', '6.00', '6', '1', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-939', '开发示例939', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '8', '7', '8.00', '3', '8', '2.00', '4', '8', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-94', '开发示例94', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '1', '9.00', '8', '5', '6.00', '4', '7', '111', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-940', '开发示例940', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '5', '5.00', '8', '9', '1.00', '6', '5', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-941', '开发示例941', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '5', '4', '6.00', '6', '2', '3.00', '7', '7', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-942', '开发示例942', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '4', '5', '5.00', '9', '2', '3.00', '4', '3', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-943', '开发示例943', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '4', '2', '9.00', '9', '4', '1.00', '4', '8', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-944', '开发示例944', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '1', '9.00', '8', '8', '3.00', '1', '6', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-945', '开发示例945', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '3', '9.00', '9', '2', '4.00', '4', '9', '123', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-946', '开发示例946', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '9', '4', '1.00', '1', '7', '8.00', '2', '8', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-947', '开发示例947', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '7', '3.00', '5', '6', '7.00', '2', '5', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-948', '开发示例948', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '2', '6.00', '5', '5', '3.00', '6', '7', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-949', '开发示例949', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '7', '8.00', '1', '6', '4.00', '8', '5', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-95', '开发示例95', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '8', '2.00', '2', '4', '1.00', '1', '1', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-950', '开发示例950', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '7', '6', '8.00', '9', '3', '4.00', '2', '5', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-951', '开发示例951', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '7', '4.00', '5', '1', '4.00', '1', '6', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-952', '开发示例952', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '4', '5.00', '4', '2', '4.00', '9', '6', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-953', '开发示例953', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '4', '5.00', '9', '9', '4.00', '9', '6', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-954', '开发示例954', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '1', '6', '3.00', '8', '9', '3.00', '8', '4', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-955', '开发示例955', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '5', '3.00', '9', '4', '8.00', '5', '3', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-956', '开发示例956', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '7', '8.00', '4', '1', '3.00', '8', '5', '111', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-957', '开发示例957', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '8', '8.00', '9', '7', '1.00', '6', '5', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-958', '开发示例958', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '2', '9', '8.00', '5', '9', '8.00', '6', '7', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-959', '开发示例959', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '9', '2', '8.00', '1', '4', '7.00', '3', '9', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-96', '开发示例96', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '4', '8.00', '8', '1', '8.00', '2', '1', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-960', '开发示例960', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '5', '5.00', '9', '3', '6.00', '3', '2', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-961', '开发示例961', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '6', '2.00', '3', '5', '9.00', '2', '6', '212', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-962', '开发示例962', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '8', '1.00', '6', '5', '6.00', '9', '1', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-963', '开发示例963', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '7', '4', '4.00', '3', '3', '9.00', '1', '5', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-964', '开发示例964', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '5', '6.00', '7', '8', '9.00', '5', '4', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-965', '开发示例965', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '1', '1.00', '3', '3', '3.00', '1', '5', '212', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-966', '开发示例966', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '1', '2', '2.00', '3', '5', '4.00', '9', '5', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-967', '开发示例967', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '8', '7.00', '3', '6', '1.00', '8', '8', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-968', '开发示例968', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '4', '9', '7.00', '4', '7', '9.00', '2', '5', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-969', '开发示例969', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '4', '3', '5.00', '9', '5', '6.00', '9', '7', '123', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-97', '开发示例97', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '8', '3.00', '9', '7', '5.00', '8', '8', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-970', '开发示例970', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '7', '2', '6.00', '4', '3', '4.00', '3', '8', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-971', '开发示例971', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '2', '4.00', '7', '7', '9.00', '1', '7', '111', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-972', '开发示例972', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '7', '2.00', '1', '1', '4.00', '7', '4', '123', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-973', '开发示例973', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '5', '2', '3.00', '9', '2', '5.00', '5', '6', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-974', '开发示例974', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '9', '5', '8.00', '4', '6', '5.00', '1', '3', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-975', '开发示例975', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '6', '5', '9.00', '4', '3', '2.00', '4', '8', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-976', '开发示例976', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '9', '9', '5.00', '5', '3', '4.00', '4', '1', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-977', '开发示例977', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '8', '8', '7.00', '1', '4', '8.00', '7', '5', '111', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-978', '开发示例978', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '8', '2.00', '9', '8', '4.00', '1', '7', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-979', '开发示例979', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '3', '3.00', '7', '9', '5.00', '5', '7', '212', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-98', '开发示例98', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '6', '2.00', '3', '9', '8.00', '1', '9', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-980', '开发示例980', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '1', '6', '4.00', '5', '5', '5.00', '6', '1', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-981', '开发示例981', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '2', '6.00', '9', '6', '9.00', '2', '2', '111', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-982', '开发示例982', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '8', '9', '1.00', '9', '1', '7.00', '5', '3', '123', '123', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-983', '开发示例983', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '3', '9', '2.00', '6', '5', '4.00', '7', '6', '212', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-984', '开发示例984', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '2', '1', '9.00', '6', '3', '7.00', '6', '8', '212', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-985', '开发示例985', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '2', '9', '8.00', '8', '1', '6.00', '5', '6', '111', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-986', '开发示例986', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '7', '5', '1.00', '6', '4', '6.00', '5', '6', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-987', '开发示例987', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '9', '9', '7.00', '6', '2', '8.00', '9', '3', '123', '111', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-988', '开发示例988', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '2', '8', '2.00', '6', '3', '6.00', '3', '1', '123', '212', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-989', '开发示例989', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '6', '6', '4.00', '3', '4', '5.00', '8', '6', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-99', '开发示例99', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '8', '5.00', '2', '1', '6.00', '8', '3', '111', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-990', '开发示例990', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '1', '1.00', '7', '3', '3.00', '5', '8', '123', '123', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-991', '开发示例991', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '8', '5.00', '4', '1', '8.00', '3', '5', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-992', '开发示例992', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '2', '8', '7.00', '3', '3', '1.00', '1', '2', '212', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-993', '开发示例993', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '3', '5', '5.00', '1', '7', '8.00', '2', '5', '123', '123', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-994', '开发示例994', '1', '2019-10-15', '2019-10-16 22:19:20', '1050501000', '1', '1', '3', '4', '1.00', '1', '9', '4.00', '1', '5', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-995', '开发示例995', '1', '2019-10-15', '2019-10-16 22:19:20', '1050000000', '1', '1', '7', '6', '1.00', '2', '2', '4.00', '4', '3', '123', '111', '123', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-996', '开发示例996', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '5', '9', '8.00', '4', '7', '7.00', '6', '4', '212', '212', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-997', '开发示例997', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '1', '3', '6.00', '8', '5', '9.00', '5', '4', '111', '212', '212', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-998', '开发示例998', '1', '2019-10-15', '2019-10-16 22:19:20', '1000000000', '1', '1', '8', '3', '3.00', '9', '6', '5.00', '8', '9', '212', '111', '111', null, null, null, null, null, null);
INSERT INTO `t_demo_zjmzxfzhl` VALUES ('zjmzxfzhl-999', '开发示例999', '1', '2019-10-15', '2019-10-16 22:19:20', '1050500000', '1', '1', '7', '5', '4.00', '5', '3', '4.00', '1', '4', '111', '212', '111', null, null, null, null, null, null);

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
INSERT INTO `t_sys_data_permission` VALUES ('1', '等于', '1', 'queryRole', 'T_DEMO_ZJMZXFZHL', 'com.zjmzxfzhl.modules.demo.entity.DemoZjmzxfzhl', 'FILTER_OPERATOR_EQ', '1', '=', '5', '', '', null, null, null, null, null, null);
INSERT INTO `t_sys_data_permission` VALUES ('10', 'LIKE_LEFT', '1', 'queryRole', 'T_DEMO_ZJMZXFZHL', 'com.zjmzxfzhl.modules.demo.entity.DemoZjmzxfzhl', 'FILTER_OPERATOR_LIKE_LEFT', '1', 'like', '%23', '', '', null, null, null, null, null, null);
INSERT INTO `t_sys_data_permission` VALUES ('11', 'LIKE_RIGHT', '1', 'queryRole', 'T_DEMO_ZJMZXFZHL', 'com.zjmzxfzhl.modules.demo.entity.DemoZjmzxfzhl', 'FILTER_OPERATOR_LIKE_RIGHT', '1', 'like', '21%', '', '', null, null, null, null, null, null);
INSERT INTO `t_sys_data_permission` VALUES ('12', '日期IN', '1', 'queryRole', 'T_DEMO_ZJMZXFZHL', 'com.zjmzxfzhl.modules.demo.entity.DemoZjmzxfzhl', 'ZJMZXFZHL_DATE', '1', 'in', '2019-10-15,2019-10-18', '', '', null, null, null, null, null, null);
INSERT INTO `t_sys_data_permission` VALUES ('13', '日期BETWEEN', '1', 'queryRole', 'T_DEMO_ZJMZXFZHL', 'com.zjmzxfzhl.modules.demo.entity.DemoZjmzxfzhl', 'ZJMZXFZHL_DATE', '1', 'between', '2019-10-16,2019-10-18', '', '', null, null, null, null, null, null);
INSERT INTO `t_sys_data_permission` VALUES ('2', '不等于', '1', 'queryRole', 'T_DEMO_ZJMZXFZHL', 'com.zjmzxfzhl.modules.demo.entity.DemoZjmzxfzhl', 'FILTER_OPERATOR_NE', '1', '!=', '5', '', '', null, null, null, null, null, null);
INSERT INTO `t_sys_data_permission` VALUES ('3', '小于', '1', 'queryRole', 'T_DEMO_ZJMZXFZHL', 'com.zjmzxfzhl.modules.demo.entity.DemoZjmzxfzhl', 'FILTER_OPERATOR_LT', '1', '<', '5', '', '', null, null, null, null, null, null);
INSERT INTO `t_sys_data_permission` VALUES ('4', '小于等于', '1', 'queryRole', 'T_DEMO_ZJMZXFZHL', 'com.zjmzxfzhl.modules.demo.entity.DemoZjmzxfzhl', 'FILTER_OPERATOR_LE', '1', '<=', '5', '', '', null, null, null, null, null, null);
INSERT INTO `t_sys_data_permission` VALUES ('5', '大于', '1', 'queryRole', 'T_DEMO_ZJMZXFZHL', 'com.zjmzxfzhl.modules.demo.entity.DemoZjmzxfzhl', 'FILTER_OPERATOR_GT', '1', '>', '5', '', '', null, null, null, null, null, null);
INSERT INTO `t_sys_data_permission` VALUES ('6', '大于等于', '1', 'queryRole', 'T_DEMO_ZJMZXFZHL', 'com.zjmzxfzhl.modules.demo.entity.DemoZjmzxfzhl', 'FILTER_OPERATOR_GE', '1', '>=', '5', '', '', null, null, null, null, null, null);
INSERT INTO `t_sys_data_permission` VALUES ('7', 'IN', '1', 'queryRole', 'T_DEMO_ZJMZXFZHL', 'com.zjmzxfzhl.modules.demo.entity.DemoZjmzxfzhl', 'FILTER_OPERATOR_IN', '1', 'in', '5,6,7,8', '', '', null, null, null, null, null, null);
INSERT INTO `t_sys_data_permission` VALUES ('8', 'BETWEEN', '1', 'queryRole', 'T_DEMO_ZJMZXFZHL', 'com.zjmzxfzhl.modules.demo.entity.DemoZjmzxfzhl', 'FILTER_OPERATOR_BETWEEN', '1', 'between', '1,6', '', '', null, null, null, null, null, null);
INSERT INTO `t_sys_data_permission` VALUES ('9', 'LIKE', '1', 'queryRole', 'T_DEMO_ZJMZXFZHL', 'com.zjmzxfzhl.modules.demo.entity.DemoZjmzxfzhl', 'FILTER_OPERATOR_LIKE', '1', 'like', '%12%', '', '', null, null, null, null, null, null);

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
INSERT INTO `t_sys_func` VALUES ('demoZjmzxfzhl-1', '新增', 'demoZjmzxfzhl', 'demo:zjmzxfzhl:save', '', '1', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('demoZjmzxfzhl-2', '修改', 'demoZjmzxfzhl', 'demo:zjmzxfzhl:update', '', '2', null, null, null, null, null, null);
INSERT INTO `t_sys_func` VALUES ('demoZjmzxfzhl-3', '删除', 'demoZjmzxfzhl', 'demo:zjmzxfzhl:delete', '', '3', null, null, null, null, null, null);
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
INSERT INTO `t_sys_menu` VALUES ('demo', '示例管理', '', 'tools', '/demo', null, '', '', '0', '1', 'Demo', '1', '0', '0', '20000', null, null, null, null, null, null);
INSERT INTO `t_sys_menu` VALUES ('demoHelloworld', 'HelloWorld', 'demo', 'example', 'helloworld', null, 'views/demo/helloworld/index', null, '0', '1', 'DemoHelloworld', '1', '0', '1', '21000', null, null, null, null, null, null);
INSERT INTO `t_sys_menu` VALUES ('demoZjmzxfzhl', '开发示例01', 'demo', 'list', 'zjmzxfzhl', 'demo:zjmzxfzhl:list,demo:zjmzxfzhl:listByQw', 'views/demo/zjmzxfzhl/index', null, '0', '1', 'DemoZjmzxfzhl', '1', '0', '1', '22000', null, null, null, null, null, null);
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
INSERT INTO `t_sys_role_permission` VALUES ('01af4d57a46b90f36a08c84f080c38d8', 'managerRole', '1', 'sysRole', 'admin', '2019-10-16', '2019-10-16 23:29:44', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('144e9c813df2cbe31a866b8af6f7ab06', 'admin', '2', 'sysUser-2', 'admin', '2019-09-16', '2019-09-16 23:43:09', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('35a43694d1e406046d4f80ff699d12cb', 'admin', '2', 'sysUser-3', 'admin', '2019-09-16', '2019-09-16 23:43:09', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('39a93f5e54bfedfc1eca6c6c504a491c', 'managerRole', '2', 'sysUser-1', 'admin', '2019-10-16', '2019-10-16 23:29:44', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('414cd82103837bf1cfba3c725d331ca4', 'queryRole', '1', 'demo', 'admin', '2019-10-21', '2019-10-21 20:56:20', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('5a37eeea253c8c1b492b7eb79c65ff85', 'admin', '1', 'sysUser', 'admin', '2019-09-16', '2019-09-16 23:43:09', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('5f2602309cf350019e5eb51d4896e348', 'admin', '2', 'sysUser-1', 'admin', '2019-09-16', '2019-09-16 23:43:09', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('6b2229c36636fd0b5b8966d0ba11b221', 'managerRole', '2', 'sysRole-5', 'admin', '2019-10-16', '2019-10-16 23:29:44', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('75c03f6e540edd580c2604369b2814d0', 'queryRole', '1', 'sysMenu', 'admin', '2019-10-21', '2019-10-21 20:56:20', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('7c79c92a1bff74e2ffa157c28b148191', 'queryRole', '1', 'sysFunc', 'admin', '2019-10-21', '2019-10-21 20:56:20', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('80bf79350534e0c49cc03f580a32b17c', 'admin', '1', 'sysRole', 'admin', '2019-09-16', '2019-09-16 23:43:09', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('8990d5c92e6c4bce92cc3528e15f3722', 'admin', '1', 'sys', 'admin', '2019-09-16', '2019-09-16 23:43:09', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('89e8b9702347c7548ac56734d5933b90', 'queryRole', '1', 'sysDataPermission', 'admin', '2019-10-21', '2019-10-21 20:56:20', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('8ecb592c8a924f85068f70c588dedb73', 'managerRole', '1', 'sysUser', 'admin', '2019-10-16', '2019-10-16 23:29:44', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('a219cc897983fdcf390a081112aa3bcd', 'queryRole', '1', 'sys', 'admin', '2019-10-21', '2019-10-21 20:56:20', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('a2aa705f04a891e28b16b62eeedd9706', 'queryRole', '1', 'sysCodeInfo', 'admin', '2019-10-21', '2019-10-21 20:56:20', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('a83ef9ab8b9efa2037290c86cc4a6769', 'managerRole', '1', 'sys', 'admin', '2019-10-16', '2019-10-16 23:29:44', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('ac567a60923307197d33c6ac2a46e00b', 'queryRole', '1', 'demoHelloworld', 'admin', '2019-10-21', '2019-10-21 20:56:20', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('ad4ad92fd08eee760ed0b41a651e13a8', 'queryRole', '1', 'demoZjmzxfzhl', 'admin', '2019-10-21', '2019-10-21 20:56:20', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('bd64ec3bfaa5080615c06bf691ebff0e', 'queryRole', '1', 'sysRole', 'admin', '2019-10-21', '2019-10-21 20:56:20', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('bded60e16227fca42e5b54b0ca98385f', 'managerRole', '2', 'sysUser-3', 'admin', '2019-10-16', '2019-10-16 23:29:44', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('c3752401c66299e7c2f4539920630f79', 'admin', '2', 'sysRole-5', 'admin', '2019-09-16', '2019-09-16 23:43:09', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('cdd6e050140abd8c5c0248af695cbcb1', 'managerRole', '2', 'sysUser-2', 'admin', '2019-10-16', '2019-10-16 23:29:44', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('d060476158e73c1e72dfc29c6598836e', 'queryRole', '1', 'sysLog', 'admin', '2019-10-21', '2019-10-21 20:56:20', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('de8dc931a424159e0635ce76e4bfe2cd', 'queryRole', '1', 'sysUser', 'admin', '2019-10-21', '2019-10-21 20:56:20', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('e085b79399e60d6e758314caebae3fed', 'queryRole', '1', 'sysCodeType', 'admin', '2019-10-21', '2019-10-21 20:56:20', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('e1fef2448a8b7ff6b605f8b40ab6b413', 'queryRole', '1', 'sysOrg', 'admin', '2019-10-21', '2019-10-21 20:56:20', null, null, null);
INSERT INTO `t_sys_role_permission` VALUES ('e43c8b3430eb35d44789b966baf3ee61', 'queryRole', '1', 'sysConfig', 'admin', '2019-10-21', '2019-10-21 20:56:20', null, null, null);

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
INSERT INTO `t_sys_user` VALUES ('lizhi1', '离职用户1', '902d802fdf9201d1f81d21efbafda7ed833dd3e404825830b1494f34b486d077', '9eG2YY1W', '1', 'queryRole', '1050501000', '18888888888', 'xxx', 'xxx@yyy.zz', '2', '5', '', 'admin', '2019-09-15', '2019-09-15 17:07:14', 'admin', '2019-09-15', '2019-09-15 21:42:59');
INSERT INTO `t_sys_user` VALUES ('lizhi2', '离职用户2', '3efefed2208103a4077e783869832d6ba310b4bec90d441afd51d753e25c842a', 'PgceWId6', '2', 'queryRole', '1050500000', '18888888888', 'xxx', 'xxx@yyy.zz', '2', '6', '', 'admin', '2019-09-15', '2019-09-15 16:28:23', null, null, null);
INSERT INTO `t_sys_user` VALUES ('zjm', '庄金明', 'c7c0493e05a82ce5d022e7db01376b28434f19d8320e32533cd3935e605ecabb', '0oyWsz6S', '1', 'managerRole', '1050501000', '18888888888', 'xxx', 'xxx@yyy.zz', '1', '3', '', 'admin', '2019-09-15', '2019-09-15 18:15:52', 'zjm', '2019-09-28', '2019-09-28 22:12:10');
INSERT INTO `t_sys_user` VALUES ('zjmzxfzhl', 'zjmzxfzhl', 'b639a87dad4ccede9bf23215e1f2a742b5aecddaa4f0f94ca514773651f3b0c3', 'hVtw6mZe', '1', 'queryRole', '1050500000', '18888888888', 'xxx', 'xxx@abc.com', '1', '7', '', 'zjm', '2019-09-28', '2019-09-28 22:08:41', 'admin', '2019-10-21', '2019-10-21 20:57:48');
INSERT INTO `t_sys_user` VALUES ('zxf', '庄晓芳', '80cb703ef5263788e70ec991fe5cedd8756c63dae6850e40b9cca8aab5b4d8b1', 'jjFcC4hd', '2', 'managerRole', '1050200000', '18888888888', 'xxx', 'xxx@yyy.zz', '1', '4', '', 'admin', '2019-09-15', '2019-09-15 16:53:39', 'zxf', '2019-09-15', '2019-09-15 22:35:58');
