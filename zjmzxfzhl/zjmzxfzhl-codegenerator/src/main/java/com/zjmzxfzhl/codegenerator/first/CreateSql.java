
package com.zjmzxfzhl.codegenerator.first;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

import com.zjmzxfzhl.codegenerator.util.CodeUtil;
import com.zjmzxfzhl.codegenerator.util.TableObject;
import com.zjmzxfzhl.common.core.util.CommonUtil;

/**
 * @author 庄金明
 * @date 2020年3月22日
 */
public class CreateSql {
    private final static String ORACLE = "oracle";
    private final static String MYSQL = "mysql";
    private final static String DB2 = "db2";

    public static void create(String createTableName) {
        // 所有建表语句
        StringBuffer stringBufferAll = new StringBuffer();

        HashMap<String, ArrayList<TableObject>> hashMap = CodeUtil.readExcelDesignInfo(createTableName);

        if (ORACLE.equals(CodeUtil.databaseType)) {
            resolveOracle(createTableName, stringBufferAll, hashMap);
        } else if (MYSQL.equals(CodeUtil.databaseType)) {
            resolveMysql(stringBufferAll, hashMap);
        } else if (DB2.equals(CodeUtil.databaseType)) {
            resolveDb2(stringBufferAll, hashMap);
        }

        // 输出建表语句.
        try {
            File file = new File(CodeUtil.createSqlPath);
            file.mkdirs();
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new FileWriter(new File(CodeUtil.createSqlPath + createTableName + ".sql")));
            bufferedWriter.write(stringBufferAll.toString());

            bufferedWriter.close();

            System.out.println("已经生成--建表语句!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void resolveDb2(StringBuffer stringBufferAll, HashMap<String, ArrayList<TableObject>> hashMap) {
        for (Object tableName : hashMap.keySet()) {
            // 读到的表名对象list.
            ArrayList<TableObject> list = hashMap.get(tableName);
            // T_SYS_CODE_TYPE变成SYS_CODE_TYPE
            String moduleId = StringUtils.substringAfter((String) tableName, "T_");
            // 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
            String aSysCodeType = CodeUtil.getTuoFengName(moduleId, true);
            String sysCodeType = CodeUtil.getTuoFengName(moduleId, false);
            String sysUpper = StringUtils.substringBefore(moduleId, "_");
            String sys = sysUpper.toLowerCase();
            // String _CodeType = CodeUtil.getTuoFengName(StringUtils.substringAfter(moduleId, "_"), true);
            String codeType = CodeUtil.getTuoFengName(StringUtils.substringAfter(moduleId, "_"), false);
            // drop语句+create table
            StringBuffer stringBufferHead = new StringBuffer();
            // 中间字段循环代码
            StringBuffer stringBufferMid = new StringBuffer();
            // 主键代码+注释
            StringBuffer stringBufferFoot = new StringBuffer();

            // 注释--开始
            stringBufferHead.append("/**【" + tableName + "】开始**/" + "\r\n");
            stringBufferHead.append("DROP TABLE " + tableName + ";" + "\r\n");
            stringBufferHead.append("CREATE TABLE " + tableName + "\r\n");
            stringBufferHead.append("(" + "\r\n");

            stringBufferFoot.append(") " + "\r\n");

            resolveDb2Table(list, stringBufferMid, stringBufferFoot);

            // 输出插入菜单的insert语句
            stringBufferFoot.append("\r\n");
            // 注释--结束
            stringBufferFoot.append("/**【" + tableName + "】结束**/" + "\r\n");
            // 打印插入菜单的语句
            resolveMenuAndFuncSql(aSysCodeType, sysCodeType, sys, codeType, stringBufferFoot);
            // 每张表建表语句累加
            stringBufferAll.append(stringBufferHead).append(stringBufferMid).append("\r\n").append(stringBufferFoot)
                    .append("\r\n");
        }
    }

    private static void resolveDb2Table(ArrayList<TableObject> list, StringBuffer stringBufferMid,
            StringBuffer stringBufferFoot) {
        // 主键字段
        String pkColumn = "";
        for (int i = 0; i < list.size(); i++) {
            TableObject tableObject = list.get(i);

            // 根据数据库类型,组织建表语句中的字段.
            stringBufferMid.append("    " + tableObject.getColumnNameEn() + " ");

            // 处理数据类型
            if ("字符型".equals(tableObject.getDataType())) {
                stringBufferMid.append("VARCHAR(" + tableObject.getDataLength() + ") ");
            } else if ("整数型".equals(tableObject.getDataType())) {
                stringBufferMid.append("INT ");
            } else if ("浮点型".equals(tableObject.getDataType())) {
                stringBufferMid
                        .append("NUMBER(" + tableObject.getDataLength() + "," + tableObject.getDataPrecision() + ") ");
            } else if ("日期型".equals(tableObject.getDataType())) {
                stringBufferMid.append("DATE ");
            } else if ("时间型".equals(tableObject.getDataType())) {
                stringBufferMid.append("TIMESTAMP(" + tableObject.getDataLength() + ") ");
            } else if ("大文本".equals(tableObject.getDataType())) {
                stringBufferMid.append("CLOB ");
            } else if ("大文件".equals(tableObject.getDataType())) {
                stringBufferMid.append("BLOB ");
            }

            // 处理主键,可空,不空
            if (tableObject.getIsNull().indexOf("主键") > 0) {
                stringBufferMid.append("NOT NULL");

                // 处理几个字段为联合主键的情况
                if (CommonUtil.isEmptyStr(pkColumn)) {
                    pkColumn += tableObject.getColumnNameEn();
                } else {
                    pkColumn += "," + tableObject.getColumnNameEn();
                }
            } else if ("不空".equals(tableObject.getIsNull())) {
                stringBufferMid.append("NOT NULL");
            } else if ("可空".equals(tableObject.getIsNull())) {
                stringBufferMid.append("NULL");
            }

            // 判断是否是这张表的最后一个字段
            if (list.size() != i + 1) {
                stringBufferMid.append("," + "\r\n");
            } else {
                // 创建这张表的主键
                if (!CommonUtil.isEmptyStr(pkColumn)) {
                    stringBufferFoot.insert(3,
                            "IN IBRCH_DATA4K INDEX IN IBRCH_INDEX4K; \r\n ALTER TABLE " + tableObject.getTableName()
                                    + " ADD CONSTRAINT PK_" + tableObject.getTableName() + "  PRIMARY KEY (" + pkColumn
                                    + ");" + "\r\n");
                }
            }

            // 表的注释,第一行才有
            if (i == 0) {
                stringBufferFoot.append("COMMENT ON TABLE " + tableObject.getTableName() + " IS '"
                        + tableObject.getTableNameCn() + "';" + "\r\n");
            }

            // 字段注释
            stringBufferFoot.append("COMMENT ON COLUMN " + tableObject.getTableName() + "."
                    + tableObject.getColumnNameEn() + " IS '" + tableObject.getColumnNameCn() + "';" + "\r\n");
        }
    }

    private static void resolveMenuAndFuncSql(String aSysCodeType, String sysCodeType, String sys, String codeType,
            StringBuffer stringBufferFoot) {
        if (CodeUtil.isPringInsertMenuSql) {
            // stringBufferFoot.append("--INSERT INTO T_SYS_MENU VALUES ('" + _sys + "', 'XX管理', null, null, null, null,
            // '1', '999',
            // null);\r\n");
            // stringBufferFoot.append("--INSERT INTO T_SYS_MENU VALUES ('" + _sysCodeType + "', 'XX功能', '" + _sys + "',
            // '/" + _sys + "/" +
            // _codeType + "/" + _codeType + "List', null, null, '1', '999', null);\r\n");
            stringBufferFoot.append("--INSERT INTO T_SYS_MENU VALUES ('" + sysCodeType + "', 'XX功能', '" + sys
                    + "', 'list', '" + codeType + "', '" + sys + ":" + codeType + ":list', 'views/" + sys + "/"
                    + codeType + "/index', NULL, '0', '1', '" + aSysCodeType
                    + "', '1', '0', '1', '19100', NULL, NULL, NULL, NULL, NULL, NULL);\r\n");
            stringBufferFoot.append("--INSERT INTO T_SYS_FUNC VALUES ('" + sysCodeType + "-1', '新增', '" + sysCodeType
                    + "', '" + sys + ":" + codeType + ":save', '', '1', NULL, NULL, NULL, NULL, NULL, NULL);\r\n");
            stringBufferFoot.append("--INSERT INTO T_SYS_FUNC VALUES ('" + sysCodeType + "-2', '修改', '" + sysCodeType
                    + "', '" + sys + ":" + codeType + ":update', '', '2', NULL, NULL, NULL, NULL, NULL, NULL);\r\n");
            stringBufferFoot.append("--INSERT INTO T_SYS_FUNC VALUES ('" + sysCodeType + "-3', '删除', '" + sysCodeType
                    + "', '" + sys + ":" + codeType + ":delete', '', '3', NULL, NULL, NULL, NULL, NULL, NULL);\r\n");
        }
    }

    private static void resolveMysql(StringBuffer stringBufferAll, HashMap<String, ArrayList<TableObject>> hashMap) {
        for (Object tableName : hashMap.keySet()) {
            // 读到的表名对象list.
            ArrayList<TableObject> list = hashMap.get(tableName);
            // T_SYS_CODE_TYPE变成SYS_CODE_TYPE
            String moduleId = StringUtils.substringAfter((String) tableName, "T_");
            // 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
            String aSysCodeType = CodeUtil.getTuoFengName(moduleId, true);
            String sysCodeType = CodeUtil.getTuoFengName(moduleId, false);
            String sysUpper = StringUtils.substringBefore(moduleId, "_");
            String sys = sysUpper.toLowerCase();
            // String _CodeType = CodeUtil.getTuoFengName(StringUtils.substringAfter(moduleId, "_"), true);
            String codeType = CodeUtil.getTuoFengName(StringUtils.substringAfter(moduleId, "_"), false);

            // drop语句+create table
            StringBuffer stringBufferHead = new StringBuffer();
            // 中间字段循环代码
            StringBuffer stringBufferMid = new StringBuffer();
            // 主键代码+注释
            StringBuffer stringBufferFoot = new StringBuffer();

            // 注释--开始
            stringBufferHead.append("/**【" + tableName + "】开始**/" + "\r\n");
            stringBufferHead.append("drop table if exists " + tableName + ";" + "\r\n");
            stringBufferHead.append("CREATE TABLE " + tableName + "\r\n");
            stringBufferHead.append("(" + "\r\n");

            resolveMysqlTable(list, stringBufferMid, stringBufferFoot);

            // 输出插入菜单的insert语句
            stringBufferFoot.append("\r\n");
            // 注释--结束
            stringBufferFoot.append("/**【" + tableName + "】结束**/" + "\r\n");

            resolveMenuAndFuncSql(aSysCodeType, sysCodeType, sys, codeType, stringBufferFoot);
            // 每张表建表语句累加
            stringBufferAll.append(stringBufferHead).append(stringBufferMid).append(stringBufferFoot).append("\r\n");
        }
    }

    private static void resolveMysqlTable(ArrayList<TableObject> list, StringBuffer stringBufferMid,
            StringBuffer stringBufferFoot) {
        // 主键字段
        String pkColumn = "";
        // 主键是否数字，是的话，要自增长
        boolean isNumberPk = false;
        for (int i = 0; i < list.size(); i++) {
            TableObject tableObject = list.get(i);

            // 根据数据库类型,组织建表语句中的字段.
            stringBufferMid.append("    " + tableObject.getColumnNameEn() + " ");

            // 处理数据类型
            if ("字符型".equals(tableObject.getDataType())) {
                stringBufferMid.append("varchar(" + tableObject.getDataLength() + ") ");
            } else if ("整数型".equals(tableObject.getDataType())) {
                if (CommonUtil.isEmptyStr(tableObject.getDataLength())
                        || Integer.valueOf(tableObject.getDataLength()) < 11) {
                    stringBufferMid.append("int(" + tableObject.getDataLength() + ") ");
                } else {
                    stringBufferMid.append("bigint(" + tableObject.getDataLength() + ") ");
                }
            } else if ("浮点型".equals(tableObject.getDataType())) {
                stringBufferMid
                        .append("float(" + tableObject.getDataLength() + "," + tableObject.getDataPrecision() + ") ");
            } else if ("日期型".equals(tableObject.getDataType())) {
                stringBufferMid.append("date ");
            } else if ("时间型".equals(tableObject.getDataType())) {
                stringBufferMid.append("datetime ");
            } else if ("大文本".equals(tableObject.getDataType())) {
                // TinyText、Text、MediumText、LongText的大小区别(字节):255、65k、16M、4G。
                stringBufferMid.append("text ");
            } else if ("大文件".equals(tableObject.getDataType())) {
                // TinyBlob、Blob、MediumBlob、LongBlob的大小区别(字节):255、65k、16M、4G。
                stringBufferMid.append("blob ");
            }

            // 处理主键,可空,不空
            if (tableObject.getIsNull().indexOf("主键") > 0) {
                stringBufferMid.append("NOT NULL");
                // mysql，主键为整数型，数据库自增长
                if ("整数型".equals(tableObject.getDataType())) {
                    stringBufferMid.append(" AUTO_INCREMENT ");
                    isNumberPk = true;
                }

                // 处理几个字段为联合主键的情况
                if (CommonUtil.isEmptyStr(pkColumn)) {
                    pkColumn += tableObject.getColumnNameEn();
                } else {
                    pkColumn += "," + tableObject.getColumnNameEn();
                }
            } else if ("不空".equals(tableObject.getIsNull())) {
                stringBufferMid.append("NOT NULL");
            } else if ("可空".equals(tableObject.getIsNull())) {
                stringBufferMid.append("NULL");
            }

            // 字段注释
            stringBufferMid.append(" COMMENT '" + tableObject.getColumnNameCn() + "'");

            // 判断是否是这张表的最后一个字段
            if (list.size() != i + 1) {
                stringBufferMid.append("," + "\r\n");
            } else {
                // 创建这张表的主键
                if (!CommonUtil.isEmptyStr(pkColumn)) {
                    stringBufferMid.append("," + "\r\n");
                    stringBufferMid.append("    " + "PRIMARY KEY (" + pkColumn + ")" + "\r\n");
                }
                // mysql，主键为整数型，数据库自增长
                if (isNumberPk) {
                    stringBufferFoot.append(") ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8 COMMENT='"
                            + tableObject.getTableNameCn() + "';\r\n");
                } else {
                    // 建表语句结束
                    stringBufferFoot.append(
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='" + tableObject.getTableNameCn() + "';\r\n");
                }
            }
        }
    }

    private static void resolveOracle(String createTableName, StringBuffer stringBufferAll,
            HashMap<String, ArrayList<TableObject>> hashMap) {
        for (Object tableName : hashMap.keySet()) {
            // 读到的表名对象list.
            ArrayList<TableObject> list = hashMap.get(tableName);
            // T_SYS_CODE_TYPE变成SYS_CODE_TYPE
            String moduleId = StringUtils.substringAfter(createTableName, "T_");
            // 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
            String aSysCodeType = CodeUtil.getTuoFengName(moduleId, true);
            String sysCodeType = CodeUtil.getTuoFengName(moduleId, false);
            String sysUpper = StringUtils.substringBefore(moduleId, "_");
            String sys = sysUpper.toLowerCase();
            // String _Sys = CodeUtil.getTuoFengName(_sys, true);

            // String _CODE_TYPE = StringUtils.substringAfter(moduleId, "_").toUpperCase();
            // String _CodeType = CodeUtil.getTuoFengName(StringUtils.substringAfter(moduleId, "_"), true);
            String codeType = CodeUtil.getTuoFengName(StringUtils.substringAfter(moduleId, "_"), false);

            // drop语句+create table
            StringBuffer stringBufferHead = new StringBuffer();
            // 中间字段循环代码
            StringBuffer stringBufferMid = new StringBuffer();
            // 主键代码+注释
            StringBuffer stringBufferFoot = new StringBuffer();

            // 注释--开始
            stringBufferHead.append("/**【" + tableName + "】开始**/" + "\r\n");
            stringBufferHead.append("DROP TABLE " + tableName + ";" + "\r\n");
            stringBufferHead.append("CREATE TABLE " + tableName + "\r\n");
            stringBufferHead.append("(" + "\r\n");

            stringBufferFoot.append(");" + "\r\n");

            resolveOracleTable(list, stringBufferMid, stringBufferFoot);

            // 输出插入菜单的insert语句
            stringBufferFoot.append("\r\n");
            // stringBufferFoot.append("/**【" + tableName + "】结束**/" + "\r\n");// 注释--结束

            resolveMenuAndFuncSql(aSysCodeType, sysCodeType, sys, codeType, stringBufferFoot);

            // 每张表建表语句累加
            stringBufferAll.append(stringBufferHead).append(stringBufferMid).append("\r\n").append(stringBufferFoot)
                    .append("\r\n");
        }
    }

    private static void resolveOracleTable(ArrayList<TableObject> list, StringBuffer stringBufferMid,
            StringBuffer stringBufferFoot) {
        // 主键字段
        String pkColumn = "";
        for (int i = 0; i < list.size(); i++) {
            TableObject tableObject = list.get(i);

            // 根据数据库类型,组织建表语句中的字段.
            stringBufferMid.append("    " + tableObject.getColumnNameEn() + " ");

            // 处理数据类型
            if ("字符型".equals(tableObject.getDataType())) {
                stringBufferMid.append("VARCHAR2(" + tableObject.getDataLength() + ") ");
            } else if ("整数型".equals(tableObject.getDataType())) {
                stringBufferMid.append("NUMBER(" + tableObject.getDataLength() + ") ");
            } else if ("浮点型".equals(tableObject.getDataType())) {
                stringBufferMid
                        .append("NUMBER(" + tableObject.getDataLength() + "," + tableObject.getDataPrecision() + ") ");
            } else if ("日期型".equals(tableObject.getDataType())) {
                stringBufferMid.append("DATE ");
            } else if ("时间型".equals(tableObject.getDataType())) {
                stringBufferMid.append("DATE ");
            } else if ("大文本".equals(tableObject.getDataType())) {
                stringBufferMid.append("CLOB ");
            } else if ("大文件".equals(tableObject.getDataType())) {
                stringBufferMid.append("BLOB ");
            }

            // 处理主键,可空,不空
            if (tableObject.getIsNull().indexOf("主键") > 0) {
                stringBufferMid.append("NOT NULL");

                // 处理几个字段为联合主键的情况
                if (CommonUtil.isEmptyStr(pkColumn)) {
                    pkColumn += tableObject.getColumnNameEn();
                } else {
                    pkColumn += "," + tableObject.getColumnNameEn();
                }
            } else if ("不空".equals(tableObject.getIsNull())) {
                stringBufferMid.append("NOT NULL");
            } else if ("可空".equals(tableObject.getIsNull())) {
                stringBufferMid.append("NULL");
            }

            // 判断是否是这张表的最后一个字段
            if (list.size() != i + 1) {
                stringBufferMid.append("," + "\r\n");
            } else {
                // 创建这张表的主键
                if (!CommonUtil.isEmptyStr(pkColumn)) {
                    stringBufferFoot.insert(4, "ALTER TABLE " + tableObject.getTableName() + " ADD CONSTRAINT PK_"
                            + tableObject.getTableName() + "  PRIMARY KEY (" + pkColumn + ");" + "\r\n");
                }
            }

            // 表的注释,第一行才有
            if (i == 0) {
                stringBufferFoot.append("COMMENT ON TABLE " + tableObject.getTableName() + " IS '"
                        + tableObject.getTableNameCn() + "';" + "\r\n");
            }

            // 字段注释
            stringBufferFoot.append("COMMENT ON COLUMN " + tableObject.getTableName() + "."
                    + tableObject.getColumnNameEn() + " IS '" + tableObject.getColumnNameCn() + "';" + "\r\n");
        }
    }

    public static void main(String[] args) {
        CreateSql.create(CodeUtil.createTableName);
    }
}
