package com.zjmzxfzhl.codeCreate.single;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

import com.zjmzxfzhl.codeCreate.util.CodeUtil;
import com.zjmzxfzhl.codeCreate.util.TableObject;
import com.zjmzxfzhl.common.util.CommonUtil;

public class CreateSql {

	public static void create(String createTableName) {
		StringBuffer stringBufferAll = new StringBuffer();// 所有建表语句

		HashMap<String, ArrayList<TableObject>> hashMap = CodeUtil.readExcelDesignInfo(createTableName);

		if ("oracle".equals(CodeUtil.databaseType)) {
			for (Object tableName : hashMap.keySet()) {
				ArrayList<TableObject> list = hashMap.get(tableName); // 读到的表名对象list.

				String moduleId = StringUtils.substringAfter(createTableName, "T_");// T_SYS_CODE_TYPE变成SYS_CODE_TYPE
				String _SysCodeType = CodeUtil.getTuoFengName(moduleId, true);// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
				String _sysCodeType = CodeUtil.getTuoFengName(moduleId, false);// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解

				String _SYS = StringUtils.substringBefore(moduleId, "_");// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
				String _sys = _SYS.toLowerCase();// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
				// String _Sys = CodeUtil.getTuoFengName(_sys, true);// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解

				// String _CODE_TYPE = StringUtils.substringAfter(moduleId, "_").toUpperCase();
				// String _CodeType = CodeUtil.getTuoFengName(StringUtils.substringAfter(moduleId, "_"), true);// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
				String _codeType = CodeUtil.getTuoFengName(StringUtils.substringAfter(moduleId, "_"), false);// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解

				StringBuffer stringBufferHead = new StringBuffer(); // drop语句+create table
				StringBuffer stringBufferMid = new StringBuffer(); // 中间字段循环代码
				StringBuffer stringBufferFoot = new StringBuffer(); // 主键代码+注释

				stringBufferHead.append("/**【" + tableName + "】开始**/" + "\r\n");// 注释--开始
				stringBufferHead.append("DROP TABLE " + tableName + ";" + "\r\n");
				stringBufferHead.append("CREATE TABLE " + tableName + "\r\n");
				stringBufferHead.append("(" + "\r\n");

				stringBufferFoot.append(");" + "\r\n");

				String pkColumn = "";// 主键字段
				for (int i = 0; i < list.size(); i++) {
					TableObject tableObject = list.get(i);

					// 根据数据库类型,组织建表语句中的字段.
					stringBufferMid.append("\t" + tableObject.getColumnNameEn() + " ");

					// 处理数据类型
					if ("字符型".equals(tableObject.getDataType()))
						stringBufferMid.append("VARCHAR2(" + tableObject.getDataLength() + ") ");
					else if ("整数型".equals(tableObject.getDataType()))
						stringBufferMid.append("NUMBER(" + tableObject.getDataLength() + ") ");
					else if ("浮点型".equals(tableObject.getDataType()))
						stringBufferMid.append("NUMBER(" + tableObject.getDataLength() + "," + tableObject.getDataPrecision() + ") ");
					else if ("日期型".equals(tableObject.getDataType()))
						stringBufferMid.append("DATE ");
					else if ("时间型".equals(tableObject.getDataType()))
						stringBufferMid.append("DATE ");
					else if ("大文本".equals(tableObject.getDataType()))
						stringBufferMid.append("CLOB ");
					else if ("大文件".equals(tableObject.getDataType()))
						stringBufferMid.append("BLOB ");

					// 处理主键,可空,不空
					if (tableObject.getIsNull().indexOf("主键") > 0) {
						stringBufferMid.append("NOT NULL");

						// 处理几个字段为联合主键的情况
						if (CommonUtil.isEmptyStr(pkColumn))
							pkColumn += tableObject.getColumnNameEn();
						else
							pkColumn += "," + tableObject.getColumnNameEn();
					} else if ("不空".equals(tableObject.getIsNull()))
						stringBufferMid.append("NOT NULL");
					else if ("可空".equals(tableObject.getIsNull()))
						stringBufferMid.append("NULL");

					// 判断是否是这张表的最后一个字段
					if (list.size() != i + 1)
						stringBufferMid.append("," + "\r\n");
					else {
						// 创建这张表的主键
						if (!CommonUtil.isEmptyStr(pkColumn))
							stringBufferFoot.insert(4, "ALTER TABLE " + tableObject.getTableName() + " ADD CONSTRAINT PK_" + tableObject.getTableName() + "  PRIMARY KEY (" + pkColumn + ");" + "\r\n");
					}

					// 表的注释,第一行才有
					if (i == 0) {
						stringBufferFoot.append("COMMENT ON TABLE " + tableObject.getTableName() + " IS '" + tableObject.getTableNameCn() + "';" + "\r\n");
					}

					// 字段注释
					stringBufferFoot.append("COMMENT ON COLUMN " + tableObject.getTableName() + "." + tableObject.getColumnNameEn() + " IS '" + tableObject.getColumnNameCn() + "';" + "\r\n");
				}

				// 输出插入菜单的insert语句
				stringBufferFoot.append("\r\n");
				// stringBufferFoot.append("/**【" + tableName + "】结束**/" + "\r\n");// 注释--结束

				if (CodeUtil.isPringInsertMenuSql) {// 打印插入菜单的语句
					// stringBufferFoot.append("--INSERT INTO T_SYS_MENU VALUES ('" + _sys + "', 'XX管理', null, null, null, null, '1', '999', null);\r\n");
					// stringBufferFoot.append("--INSERT INTO T_SYS_MENU VALUES ('" + _sysCodeType + "', 'XX功能', '" + _sys + "', '/" + _sys + "/" + _codeType + "/" + _codeType + "List', null, null, '1', '999', null);\r\n");
					stringBufferFoot.append("--INSERT INTO T_SYS_MENU VALUES ('" + _sysCodeType + "', 'XX功能', '" + _sys + "', '', '" + _codeType + "', '" + _sys + ":" + _codeType + ":list," + _sys + ":" + _codeType + ":listByQw', 'views/" + _sys
							+ "/" + _codeType + "/index', NULL, '0', '1', '" + _SysCodeType + "', '1', '0', '1', '19100', NULL, NULL, NULL, NULL, NULL, NULL);\r\n");
					stringBufferFoot.append("--INSERT INTO T_SYS_FUNC VALUES ('" + _sysCodeType + "-1', '新增', '" + _sysCodeType + "', '" + _sys + ":" + _codeType + ":save', '', '1', NULL, NULL, NULL, NULL, NULL, NULL);\r\n");
					stringBufferFoot.append("--INSERT INTO T_SYS_FUNC VALUES ('" + _sysCodeType + "-2', '修改', '" + _sysCodeType + "', '" + _sys + ":" + _codeType + ":update', '', '2', NULL, NULL, NULL, NULL, NULL, NULL);\r\n");
					stringBufferFoot.append("--INSERT INTO T_SYS_FUNC VALUES ('" + _sysCodeType + "-3', '删除', '" + _sysCodeType + "', '" + _sys + ":" + _codeType + ":delete', '', '3', NULL, NULL, NULL, NULL, NULL, NULL);\r\n");
				}

				stringBufferAll.append(stringBufferHead).append(stringBufferMid).append("\r\n").append(stringBufferFoot).append("\r\n");// 每张表建表语句累加
			}
		} else if ("mysql".equals(CodeUtil.databaseType)) {
			for (Object tableName : hashMap.keySet()) {
				ArrayList<TableObject> list = hashMap.get(tableName); // 读到的表名对象list.

				String moduleId = StringUtils.substringAfter((String) tableName, "T_");// T_SYS_CODE_TYPE变成SYS_CODE_TYPE
				String _SysCodeType = CodeUtil.getTuoFengName(moduleId, true);// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
				String _sysCodeType = CodeUtil.getTuoFengName(moduleId, false);// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
				String _SYS = StringUtils.substringBefore(moduleId, "_");// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
				String _sys = _SYS.toLowerCase();// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
				// String _CodeType = CodeUtil.getTuoFengName(StringUtils.substringAfter(moduleId, "_"), true);// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
				String _codeType = CodeUtil.getTuoFengName(StringUtils.substringAfter(moduleId, "_"), false);// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解

				StringBuffer stringBufferHead = new StringBuffer(); // drop语句+create table
				StringBuffer stringBufferMid = new StringBuffer(); // 中间字段循环代码
				StringBuffer stringBufferFoot = new StringBuffer(); // 主键代码+注释

				stringBufferHead.append("/**【" + tableName + "】开始**/" + "\r\n");// 注释--开始
				stringBufferHead.append("drop table if exists " + tableName + ";" + "\r\n");
				stringBufferHead.append("CREATE TABLE " + tableName + "\r\n");
				stringBufferHead.append("(" + "\r\n");

				String pkColumn = "";// 主键字段
				boolean isNumberPk = false;// 主键是否数字，是的话，要自增长
				for (int i = 0; i < list.size(); i++) {
					TableObject tableObject = list.get(i);

					// 根据数据库类型,组织建表语句中的字段.
					stringBufferMid.append("\t" + tableObject.getColumnNameEn() + " ");

					// 处理数据类型
					if ("字符型".equals(tableObject.getDataType()))
						stringBufferMid.append("varchar(" + tableObject.getDataLength() + ") ");
					else if ("整数型".equals(tableObject.getDataType())) {
						if (CommonUtil.isEmptyStr(tableObject.getDataLength()) || Integer.valueOf(tableObject.getDataLength()) < 11) {
							stringBufferMid.append("int(" + tableObject.getDataLength() + ") ");
						} else {
							stringBufferMid.append("bigint(" + tableObject.getDataLength() + ") ");
						}
					} else if ("浮点型".equals(tableObject.getDataType()))
						stringBufferMid.append("float(" + tableObject.getDataLength() + "," + tableObject.getDataPrecision() + ") ");
					else if ("日期型".equals(tableObject.getDataType()))
						stringBufferMid.append("date ");
					else if ("时间型".equals(tableObject.getDataType()))
						stringBufferMid.append("datetime ");
					else if ("大文本".equals(tableObject.getDataType()))
						stringBufferMid.append("mediumtext ");// TinyText、Text、MediumText、LongText的大小区别(字节):255、65k、16M、4G。
					else if ("大文件".equals(tableObject.getDataType()))
						stringBufferMid.append("mediumblob ");// TinyBlob、Blob、MediumBlob、LongBlob的大小区别(字节):255、65k、16M、4G。

					// 处理主键,可空,不空
					if (tableObject.getIsNull().indexOf("主键") > 0) {
						stringBufferMid.append("NOT NULL");

						if ("整数型".equals(tableObject.getDataType())) {// mysql，主键为整数型，数据库自增长
							stringBufferMid.append(" AUTO_INCREMENT ");
							isNumberPk = true;
						}

						// 处理几个字段为联合主键的情况
						if (CommonUtil.isEmptyStr(pkColumn))
							pkColumn += tableObject.getColumnNameEn();
						else
							pkColumn += "," + tableObject.getColumnNameEn();
					} else if ("不空".equals(tableObject.getIsNull()))
						stringBufferMid.append("NOT NULL");
					else if ("可空".equals(tableObject.getIsNull()))
						stringBufferMid.append("NULL");

					// 字段注释
					stringBufferMid.append(" COMMENT '" + tableObject.getColumnNameCn() + "'");

					// 判断是否是这张表的最后一个字段
					if (list.size() != i + 1)
						stringBufferMid.append("," + "\r\n");
					else {
						// 创建这张表的主键
						if (!CommonUtil.isEmptyStr(pkColumn)) {
							stringBufferMid.append("," + "\r\n");
							stringBufferMid.append("\t" + "PRIMARY KEY (" + pkColumn + ")" + "\r\n");
						}

						if (isNumberPk) {// mysql，主键为整数型，数据库自增长
							stringBufferFoot.append(") ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8 COMMENT='" + tableObject.getTableNameCn() + "';\r\n");
						} else {
							// 建表语句结束
							stringBufferFoot.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='" + tableObject.getTableNameCn() + "';\r\n");
						}
					}
				}

				// 输出插入菜单的insert语句
				stringBufferFoot.append("\r\n");
				stringBufferFoot.append("/**【" + tableName + "】结束**/" + "\r\n");// 注释--结束

				if (CodeUtil.isPringInsertMenuSql) {// 打印插入菜单的语句
					// stringBufferFoot.append("--INSERT INTO T_SYS_MENU VALUES ('" + _sys + "', 'XX管理', null, null, null, null, '1', '999', null);\r\n");
					// stringBufferFoot.append("--INSERT INTO T_SYS_MENU VALUES ('" + _sysCodeType + "', 'XX功能', '" + _sys + "', '/" + _sys + "/" + _codeType + "/" + _codeType + "List', null, null, '1', '999', null);\r\n");
					stringBufferFoot.append("--INSERT INTO T_SYS_MENU VALUES ('" + _sysCodeType + "', 'XX功能', '" + _sys + "', '', '" + _codeType + "', '" + _sys + ":" + _codeType + ":list," + _sys + ":" + _codeType + ":listByQw', 'views/" + _sys
							+ "/" + _codeType + "/index', NULL, '0', '1', '" + _SysCodeType + "', '1', '0', '1', '19100', NULL, NULL, NULL, NULL, NULL, NULL);\r\n");
					stringBufferFoot.append("--INSERT INTO T_SYS_FUNC VALUES ('" + _sysCodeType + "-1', '新增', '" + _sysCodeType + "', '" + _sys + ":" + _codeType + ":save', '', '1', NULL, NULL, NULL, NULL, NULL, NULL);\r\n");
					stringBufferFoot.append("--INSERT INTO T_SYS_FUNC VALUES ('" + _sysCodeType + "-2', '修改', '" + _sysCodeType + "', '" + _sys + ":" + _codeType + ":update', '', '2', NULL, NULL, NULL, NULL, NULL, NULL);\r\n");
					stringBufferFoot.append("--INSERT INTO T_SYS_FUNC VALUES ('" + _sysCodeType + "-3', '删除', '" + _sysCodeType + "', '" + _sys + ":" + _codeType + ":delete', '', '3', NULL, NULL, NULL, NULL, NULL, NULL);\r\n");
				}

				stringBufferAll.append(stringBufferHead).append(stringBufferMid).append(stringBufferFoot).append("\r\n");// 每张表建表语句累加
			}
		} else if ("db2".equals(CodeUtil.databaseType)) {
			for (Object tableName : hashMap.keySet()) {
				ArrayList<TableObject> list = hashMap.get(tableName); // 读到的表名对象list.

				String moduleId = StringUtils.substringAfter((String) tableName, "T_");// T_SYS_CODE_TYPE变成SYS_CODE_TYPE
				String _SysCodeType = CodeUtil.getTuoFengName(moduleId, true);// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
				String _sysCodeType = CodeUtil.getTuoFengName(moduleId, false);// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
				String _SYS = StringUtils.substringBefore(moduleId, "_");// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
				String _sys = _SYS.toLowerCase();// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
				// String _CodeType = CodeUtil.getTuoFengName(StringUtils.substringAfter(moduleId, "_"), true);// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
				String _codeType = CodeUtil.getTuoFengName(StringUtils.substringAfter(moduleId, "_"), false);// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解

				StringBuffer stringBufferHead = new StringBuffer(); // drop语句+create table
				StringBuffer stringBufferMid = new StringBuffer(); // 中间字段循环代码
				StringBuffer stringBufferFoot = new StringBuffer(); // 主键代码+注释

				stringBufferHead.append("/**【" + tableName + "】开始**/" + "\r\n");// 注释--开始
				stringBufferHead.append("DROP TABLE " + tableName + ";" + "\r\n");
				stringBufferHead.append("CREATE TABLE " + tableName + "\r\n");
				stringBufferHead.append("(" + "\r\n");

				stringBufferFoot.append(") " + "\r\n");

				String pkColumn = "";// 主键字段
				for (int i = 0; i < list.size(); i++) {
					TableObject tableObject = list.get(i);

					// 根据数据库类型,组织建表语句中的字段.
					stringBufferMid.append("\t" + tableObject.getColumnNameEn() + " ");

					// 处理数据类型
					if ("字符型".equals(tableObject.getDataType()))
						stringBufferMid.append("VARCHAR(" + tableObject.getDataLength() + ") ");
					else if ("整数型".equals(tableObject.getDataType()))
						stringBufferMid.append("INT ");
					else if ("浮点型".equals(tableObject.getDataType()))
						stringBufferMid.append("NUMBER(" + tableObject.getDataLength() + "," + tableObject.getDataPrecision() + ") ");
					else if ("日期型".equals(tableObject.getDataType()))
						stringBufferMid.append("DATE ");
					else if ("时间型".equals(tableObject.getDataType()))
						stringBufferMid.append("TIMESTAMP(" + tableObject.getDataLength() + ") ");
					else if ("大文本".equals(tableObject.getDataType()))
						stringBufferMid.append("CLOB ");
					else if ("大文件".equals(tableObject.getDataType()))
						stringBufferMid.append("BLOB ");

					// 处理主键,可空,不空
					if (tableObject.getIsNull().indexOf("主键") > 0) {
						stringBufferMid.append("NOT NULL");

						// 处理几个字段为联合主键的情况
						if (CommonUtil.isEmptyStr(pkColumn))
							pkColumn += tableObject.getColumnNameEn();
						else
							pkColumn += "," + tableObject.getColumnNameEn();
					} else if ("不空".equals(tableObject.getIsNull()))
						stringBufferMid.append("NOT NULL");
					else if ("可空".equals(tableObject.getIsNull()))
						stringBufferMid.append("NULL");

					// 判断是否是这张表的最后一个字段
					if (list.size() != i + 1)
						stringBufferMid.append("," + "\r\n");
					else {
						// 创建这张表的主键
						if (!CommonUtil.isEmptyStr(pkColumn))
							stringBufferFoot.insert(3, "IN IBRCH_DATA4K INDEX IN IBRCH_INDEX4K; \r\n ALTER TABLE " + tableObject.getTableName() + " ADD CONSTRAINT PK_" + tableObject.getTableName() + "  PRIMARY KEY (" + pkColumn + ");" + "\r\n");
					}

					// 表的注释,第一行才有
					if (i == 0) {
						stringBufferFoot.append("COMMENT ON TABLE " + tableObject.getTableName() + " IS '" + tableObject.getTableNameCn() + "';" + "\r\n");
					}

					// 字段注释
					stringBufferFoot.append("COMMENT ON COLUMN " + tableObject.getTableName() + "." + tableObject.getColumnNameEn() + " IS '" + tableObject.getColumnNameCn() + "';" + "\r\n");
				}

				// 输出插入菜单的insert语句
				stringBufferFoot.append("\r\n");
				stringBufferFoot.append("/**【" + tableName + "】结束**/" + "\r\n");// 注释--结束

				if (CodeUtil.isPringInsertMenuSql) {// 打印插入菜单的语句
					// stringBufferFoot.append("--INSERT INTO T_SYS_MENU VALUES ('" + _sys + "', 'XX管理', null, null, null, null, '1', '999', null);\r\n");
					// stringBufferFoot.append("--INSERT INTO T_SYS_MENU VALUES ('" + _sysCodeType + "', 'XX功能', '" + _sys + "', '/" + _sys + "/" + _codeType + "/" + _codeType + "List', null, null, '1', '999', null);\r\n");
					stringBufferFoot.append("--INSERT INTO T_SYS_MENU VALUES ('" + _sysCodeType + "', 'XX功能', '" + _sys + "', '', '" + _codeType + "', '" + _sys + ":" + _codeType + ":list," + _sys + ":" + _codeType + ":listByQw', 'views/" + _sys
							+ "/" + _codeType + "/index', NULL, '0', '1', '" + _SysCodeType + "', '1', '0', '1', '19100', NULL, NULL, NULL, NULL, NULL, NULL);\r\n");
					stringBufferFoot.append("--INSERT INTO T_SYS_FUNC VALUES ('" + _sysCodeType + "-1', '新增', '" + _sysCodeType + "', '" + _sys + ":" + _codeType + ":save', '', '1', NULL, NULL, NULL, NULL, NULL, NULL);\r\n");
					stringBufferFoot.append("--INSERT INTO T_SYS_FUNC VALUES ('" + _sysCodeType + "-2', '修改', '" + _sysCodeType + "', '" + _sys + ":" + _codeType + ":update', '', '2', NULL, NULL, NULL, NULL, NULL, NULL);\r\n");
					stringBufferFoot.append("--INSERT INTO T_SYS_FUNC VALUES ('" + _sysCodeType + "-3', '删除', '" + _sysCodeType + "', '" + _sys + ":" + _codeType + ":delete', '', '3', NULL, NULL, NULL, NULL, NULL, NULL);\r\n");
				}

				stringBufferAll.append(stringBufferHead).append(stringBufferMid).append("\r\n").append(stringBufferFoot).append("\r\n");// 每张表建表语句累加
			}
		}

		// 输出建表语句.
		try {
			File file = new File(CodeUtil.createSqlPath);
			file.mkdirs();
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(CodeUtil.createSqlPath + createTableName + ".sql")));
			bufferedWriter.write(stringBufferAll.toString());

			bufferedWriter.close();

			System.out.println("已经生成--建表语句!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		CreateSql.create(CodeUtil.createTableName);
	}
}
