package com.zjmzxfzhl.codegenerator.first;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.zjmzxfzhl.codegenerator.util.CodeUtil;
import com.zjmzxfzhl.codegenerator.util.TableObject;
import com.zjmzxfzhl.common.core.util.CommonUtil;

/**
 * @author 庄金明
 * @date 2020年3月22日
 */
public class CreateMybatisMapperXml {

    @SuppressWarnings("resource")
    public static void create(String createTableName, String templetPath) {
        StringBuffer stringBufferAll = new StringBuffer();
        HashMap<String, ArrayList<TableObject>> hashMap = CodeUtil.readExcelDesignInfo(createTableName);
        // T_SYS_CODE_TYPE变成SYS_CODE_TYPE
        String moduleId = StringUtils.substringAfter(createTableName, "T_");
        // 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
        // String _SysCodeType = CodeUtil.getTuoFengName(moduleId, true);
        // String _sysCodeType = CodeUtil.getTuoFengName(moduleId, false);
        String sysUpper = StringUtils.substringBefore(moduleId, "_");
        String sys = sysUpper.toLowerCase();
        String aSys = CodeUtil.getTuoFengName(sys, true);
        // String _CODE_TYPE = StringUtils.substringAfter(moduleId, "_").toUpperCase();
        String aCodeType = CodeUtil.getTuoFengName(StringUtils.substringAfter(moduleId, "_"), true);
        String codeType = CodeUtil.getTuoFengName(StringUtils.substringAfter(moduleId, "_"), false);
        // 读取模板内容
        File serviceFile = new File(CodeUtil.projectPath + "src\\main\\java\\com\\zjmzxfzhl\\codegenerator\\template\\"
                + templetPath + "\\MapperXml.txt");
        try {
            FileInputStream fileInputStream = new FileInputStream(serviceFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBufferAll.append(line + "\r\n");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        // 【1】 根据字段 循环得到中文表名和英文表名
        // 读到的表名对象list.
        ArrayList<TableObject> list = hashMap.get(createTableName);
        // 中文表名
        String tableNameCn = "", tableName = "", primaryKey = "";
        for (int i = 0; i < list.size(); i++) {
            TableObject tableObject = list.get(i);
            // 中文表名和列表字段
            if (i == 0) {
                tableNameCn = tableObject.getTableNameCn();
                // 取出中文表名中的前缀,A01_系统参数表.
                tableNameCn = StringUtils.substringAfter(tableNameCn, "_");
                tableNameCn = StringUtils.substringBeforeLast(tableNameCn, "表");
                tableName = tableObject.getTableName();
            }
            if (CommonUtil.isExist("UUID主键,数据库生成主键,前台输入主键", tableObject.getIsNull(), ",")) {
                primaryKey = tableObject.getColumnNameEn();
            }
        }
        // 【2】得到查询的searchColumns和whereConditions
        // 查询的字段
        String searchColumns = getSearchColumns(list);
        // where条件
        String whereConditions = getWhereConditions(list);
        String orderBy = getOrderBy(primaryKey);

        // 【3】得到插入的insertColumns和insertValues
        // 插入列
        String insertColumns = getInsertColumns(list);
        // 插入值
        String insertValues = getInsertValues(list);
        // 【4】得到updateSetValues 更新值
        String updateSetValues = getUpdateSetValues(list);
        try {
            String filePath = CodeUtil.projectPath + "src\\main\\java\\com\\zjmzxfzhl\\modules\\" + sys
                    + "\\mapper\\xml\\";
            File file = new File(filePath);
            file.mkdirs();
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new FileWriter(new File(filePath + aSys + aCodeType + "Mapper.xml")));
            // 根据表名替换:关键词
            String temp = stringBufferAll.toString();
            temp = StringUtils.replace(temp, "{_SYS}", sysUpper);
            temp = StringUtils.replace(temp, "{_Sys}", aSys);
            temp = StringUtils.replace(temp, "{_sys}", sys);
            // temp = StringUtils.replace(temp, "{_CODE_TYPE}", _CODE_TYPE);
            temp = StringUtils.replace(temp, "{_CodeType}", aCodeType);
            temp = StringUtils.replace(temp, "{_codeType}", codeType);
            temp = StringUtils.replace(temp, "{tableNameCn}", tableNameCn);
            temp = StringUtils.replace(temp, "{tableName}", tableName);
            temp = StringUtils.replace(temp, "{searchColumns}", searchColumns);
            temp = StringUtils.replace(temp, "{whereConditions}", whereConditions);
            temp = StringUtils.replace(temp, "{orderBy}", orderBy);
            temp = StringUtils.replace(temp, "{insertColumns}", insertColumns);
            temp = StringUtils.replace(temp, "{insertValues}", insertValues);
            temp = StringUtils.replace(temp, "{updateSetValues}", updateSetValues);
            bufferedWriter.write(temp);
            bufferedWriter.close();
            System.out.println("已经生成--mybatisMapper.xml文件!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到查询list的where条件字符串--多表查询
     * 
     * @param list
     * @return
     */
    private static String getWhereConditions(List<TableObject> list) {
        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < list.size(); i++) {
            TableObject tableObject = list.get(i);

            if (tableObject.getIsSearch()) {
                // 要被搜索的字段名,驼峰法变成小写
                String columnNameEn = CodeUtil.getTuoFengName(tableObject.getColumnNameEn(), false);

                if ("字符型".equals(tableObject.getDataType()) || "整数型".equals(tableObject.getDataType())
                        || "浮点型".equals(tableObject.getDataType())) {
                    // <if test="userId != null and userId !=''">
                    // <![CDATA[ a.USER_ID = #{userId} AND ]]>
                    stringBuffer.append("        " + "<if test=\"entity." + columnNameEn + " != null and entity."
                            + columnNameEn + " !=''\">" + "\r\n");
                    stringBuffer.append("            " + "<![CDATA[    AND a." + tableObject.getColumnNameEn()
                            + " = #{entity." + columnNameEn + "}    ]]>" + "\r\n");
                    stringBuffer.append("        " + "</if>" + "\r\n");
                }

            }
        }

        return stringBuffer.toString();
    }

    /**
     * 得到查询list的列表字段--字符串
     * 
     * @param list
     * @return
     */
    private static String getSearchColumns(List<TableObject> list) {
        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < list.size(); i++) {

            TableObject tableObject = list.get(i);
            if ("CREATE_BY".equalsIgnoreCase(tableObject.getColumnNameEn())
                    || "CREATE_DATE".equalsIgnoreCase(tableObject.getColumnNameEn())
                    || "CREATE_TIME".equalsIgnoreCase(tableObject.getColumnNameEn())
                    || "UPDATE_BY".equalsIgnoreCase(tableObject.getColumnNameEn())
                    || "UPDATE_DATE".equalsIgnoreCase(tableObject.getColumnNameEn())
                    || "UPDATE_TIME".equalsIgnoreCase(tableObject.getColumnNameEn())) {
                continue;
            }
            if (i == 0) {
                stringBuffer.append("            ");
            } else {
                stringBuffer.append(",\r\n            ");
            }
            stringBuffer.append("a." + tableObject.getColumnNameEn() + " as "
                    + CodeUtil.getTuoFengName(tableObject.getColumnNameEn(), false));

        }

        return stringBuffer.toString();
    }

    /**
     * 得到查询list的where条件字符串--单表查询
     * 
     * @param list
     * @return
     */
    private static String getOrderBy(String primaryKey) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("        " + "order by a." + primaryKey);
        return stringBuffer.toString();
    }

    /**
     * 得到insert的列
     * 
     * @param list
     * @return
     */
    private static String getInsertColumns(List<TableObject> list) {
        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < list.size(); i++) {
            TableObject tableObject = list.get(i);
            // 要被搜索的字段名,驼峰法变成小写
            String columnNameEn = CodeUtil.getTuoFengName(tableObject.getColumnNameEn(), false);

            // <if test="sampleId != null and sampleId !=''""> SAMPLE_ID, </if>
            stringBuffer.append("            " + "<if test=\"" + columnNameEn + " != null\">" + "\r\n");
            stringBuffer.append("                " + tableObject.getColumnNameEn() + "," + "\r\n");
            stringBuffer.append("            " + "</if>" + "\r\n");

        }

        return stringBuffer.toString();
    }

    /**
     * 得到insert的值
     * 
     * @param list
     * @return
     */
    private static String getInsertValues(List<TableObject> list) {
        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < list.size(); i++) {
            TableObject tableObject = list.get(i);
            // 要被搜索的字段名,驼峰法变成小写
            String columnNameEn = CodeUtil.getTuoFengName(tableObject.getColumnNameEn(), false);

            // <if test="sampleId != null"> #{sampleId}, </if>
            stringBuffer.append("            " + "<if test=\"" + columnNameEn + " != null\">" + "\r\n");
            stringBuffer.append("                " + "<![CDATA[    #{" + columnNameEn + "},    ]]>" + "\r\n");
            stringBuffer.append("            " + "</if>" + "\r\n");

        }

        return stringBuffer.toString();
    }

    /**
     * 得到update的值
     * 
     * @param list
     * @return
     */
    private static String getUpdateSetValues(List<TableObject> list) {
        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < list.size(); i++) {
            TableObject tableObject = list.get(i);
            // 要被搜索的字段名,驼峰法变成小写
            String columnNameEn = CodeUtil.getTuoFengName(tableObject.getColumnNameEn(), false);

            // <if test="sampleId != null"> REMARK=#{remark}, </if>
            stringBuffer.append("            " + "<if test=\"" + columnNameEn + " != null\">" + "\r\n");
            stringBuffer.append("                " + "<![CDATA[    " + tableObject.getColumnNameEn() + " = #{"
                    + columnNameEn + "},    ]]>" + "\r\n");
            stringBuffer.append("            " + "</if>" + "\r\n");

        }

        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        CreateMybatisMapperXml.create(CodeUtil.createTableName, "first");
    }
}
