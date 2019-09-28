package com.zjmzxfzhl.codeCreate.single;

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

import com.zjmzxfzhl.codeCreate.util.CodeUtil;
import com.zjmzxfzhl.codeCreate.util.TableObject;
import com.zjmzxfzhl.common.util.CommonUtil;

public class CreateMybatisMapperXml {

	@SuppressWarnings("resource")
	public static void create(String createTableName, String mybatisSqlFileName) {
		StringBuffer stringBufferAll = new StringBuffer();

		HashMap<String, ArrayList<TableObject>> hashMap = CodeUtil.readExcelDesignInfo(createTableName);
		String moduleId = StringUtils.substringAfter(createTableName, "T_");// T_SYS_CODE_TYPE变成SYS_CODE_TYPE
		// String _SysCodeType = CodeUtil.getTuoFengName(moduleId, true);// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
		// String _sysCodeType = CodeUtil.getTuoFengName(moduleId, false);// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解

		String _SYS = StringUtils.substringBefore(moduleId, "_");// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
		String _sys = _SYS.toLowerCase();// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
		String _Sys = CodeUtil.getTuoFengName(_sys, true);// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解

		String _CODE_TYPE = StringUtils.substringAfter(moduleId, "_").toUpperCase();
		String _CodeType = CodeUtil.getTuoFengName(StringUtils.substringAfter(moduleId, "_"), true);// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
		String _codeType = CodeUtil.getTuoFengName(StringUtils.substringAfter(moduleId, "_"), false);// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解

		// 读取模板内容
		File serviceFile = new File(CodeUtil.projectPath + "src\\main\\java\\com\\zjmzxfzhl\\codeCreate\\template\\" + mybatisSqlFileName + ".txt");
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
		ArrayList<TableObject> list = hashMap.get(createTableName); // 读到的表名对象list.
		String tableNameCn = ""; // 中文表名
		String tableName = ""; // 英文表名
		String primaryKey = ""; // 主键

		for (int i = 0; i < list.size(); i++) {
			TableObject tableObject = list.get(i);

			// 中文表名和列表字段
			if (i == 0) {
				tableNameCn = tableObject.getTableNameCn();
				tableNameCn = StringUtils.substringAfter(tableNameCn, "_");// 取出中文表名中的前缀,A01_系统参数表.
				tableNameCn = StringUtils.substringBeforeLast(tableNameCn, "表");

				tableName = tableObject.getTableName();
			}

			if (CommonUtil.isExist("UUID主键,数据库生成主键,前台输入主键", tableObject.getIsNull(), ",")) {
				primaryKey = tableObject.getColumnNameEn();
			}
		}

		// 【2】得到查询的searchColumns和whereConditions
		String searchColumns = getSearchColumns(list); // 查询的字段
		String whereConditions = getWhereConditions(list); // where条件--多表
		String whereConditions2 = getWhereConditions2(list); // where条件--单表
		String orderBy = getOrderBy(primaryKey); // where条件--单表

		// 【3】得到插入的insertColumns和insertValues
		String insertColumns = getInsertColumns(list);// 插入列
		String insertValues = getInsertValues(list);// 插入值

		// 【4】得到updateSetValues
		String updateSetValues = getUpdateSetValues(list);// 更新值

		try {
			String filePath = CodeUtil.projectPath + "src\\main\\java\\com\\zjmzxfzhl\\modules\\" + _sys + "\\mapper\\xml\\";
			File file = new File(filePath);
			file.mkdirs();
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(filePath + _Sys + _CodeType + "Mapper.xml")));

			// 根据表名替换:关键词
			String temp = stringBufferAll.toString();
			temp = StringUtils.replace(temp, "{_SYS}", _SYS);
			temp = StringUtils.replace(temp, "{_Sys}", _Sys);
			temp = StringUtils.replace(temp, "{_sys}", _sys);
			temp = StringUtils.replace(temp, "{_CODE_TYPE}", _CODE_TYPE);
			temp = StringUtils.replace(temp, "{_CodeType}", _CodeType);
			temp = StringUtils.replace(temp, "{_codeType}", _codeType);

			temp = StringUtils.replace(temp, "{tableNameCn}", tableNameCn);
			temp = StringUtils.replace(temp, "{tableName}", tableName);
			temp = StringUtils.replace(temp, "{searchColumns}", searchColumns);// 查询的字段
			temp = StringUtils.replace(temp, "{whereConditions}", whereConditions);// where条件--多表
			temp = StringUtils.replace(temp, "{whereConditions2}", whereConditions2);// where条件--单表
			temp = StringUtils.replace(temp, "{orderBy}", orderBy);// where条件--单表
			temp = StringUtils.replace(temp, "{insertColumns}", insertColumns);// 插入列
			temp = StringUtils.replace(temp, "{insertValues}", insertValues);// 插入值
			temp = StringUtils.replace(temp, "{updateSetValues}", updateSetValues);// 更新值

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
				String columnNameEn = CodeUtil.getTuoFengName(tableObject.getColumnNameEn(), false);// 要被搜索的字段名,驼峰法变成小写

				if ("字符型".equals(tableObject.getDataType())) {
					// <if test="userId != null and userId !=''">
					// <![CDATA[ a.USER_ID = #{userId} AND ]]>
					stringBuffer.append("\t\t" + "<if test=\"entity." + columnNameEn + " != null and entity." + columnNameEn + " !=''\">" + "\r\n");
					stringBuffer.append("\t\t\t" + "<![CDATA[\tAND a." + tableObject.getColumnNameEn() + " = #{entity." + columnNameEn + "}\t]]>" + "\r\n");
					stringBuffer.append("\t\t" + "</if>" + "\r\n");
				}

			}
		}

		return stringBuffer.toString();
	}

	/**
	 * 得到查询list的where条件字符串--单表查询
	 * 
	 * @param list
	 * @return
	 */
	private static String getWhereConditions2(List<TableObject> list) {
		StringBuffer stringBuffer = new StringBuffer();

		for (int i = 0; i < list.size(); i++) {
			TableObject tableObject = list.get(i);

			String columnNameEn = CodeUtil.getTuoFengName(tableObject.getColumnNameEn(), false);// 要被搜索的字段名,驼峰法变成小写

			if ("字符型".equals(tableObject.getDataType())) {
				// <if test="userId != null and userId !=''">
				// <![CDATA[ a.USER_ID = #{userId} AND ]]>
				if (StringUtils.isNotEmpty(tableObject.getCodeTypeId())) {// 下拉框处理查询
					stringBuffer.append("\t\t\t" + "<if test=\"" + columnNameEn + " != null and " + columnNameEn + " !=''\">" + "\r\n");
					stringBuffer.append("\t\t\t\t" + "<![CDATA[\tAND a." + tableObject.getColumnNameEn() + " = #{" + columnNameEn + "}\t]]>" + "\r\n");
					stringBuffer.append("\t\t\t" + "</if>" + "\r\n");
				} else {// like查询
					stringBuffer.append("\t\t\t" + "<if test=\"" + columnNameEn + " != null and " + columnNameEn + " !=''\">" + "\r\n");
					stringBuffer.append("\t\t\t\t" + "<![CDATA[\tAND a." + tableObject.getColumnNameEn() + " = #{" + columnNameEn + "}\t]]>" + "\r\n");
					stringBuffer.append("\t\t\t" + "</if>" + "\r\n");
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

			if (i == 0) {
				stringBuffer.append("\t\t\t");
			} else {
				stringBuffer.append(",\r\n\t\t\t");
			}
			stringBuffer.append("a." + tableObject.getColumnNameEn() + " as " + CodeUtil.getTuoFengName(tableObject.getColumnNameEn(), false));

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
		stringBuffer.append("\t\t" + "order by a." + primaryKey);
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

			String columnNameEn = CodeUtil.getTuoFengName(tableObject.getColumnNameEn(), false);// 要被搜索的字段名,驼峰法变成小写

			// <if test="sampleId != null and sampleId !=''""> SAMPLE_ID, </if>
			stringBuffer.append("\t\t\t" + "<if test=\"" + columnNameEn + " != null\">" + "\r\n");
			stringBuffer.append("\t\t\t\t" + tableObject.getColumnNameEn() + "," + "\r\n");
			stringBuffer.append("\t\t\t" + "</if>" + "\r\n");

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

			String columnNameEn = CodeUtil.getTuoFengName(tableObject.getColumnNameEn(), false);// 要被搜索的字段名,驼峰法变成小写

			// <if test="sampleId != null"> #{sampleId}, </if>
			stringBuffer.append("\t\t\t" + "<if test=\"" + columnNameEn + " != null\">" + "\r\n");
			stringBuffer.append("\t\t\t\t" + "<![CDATA[\t#{" + columnNameEn + "},\t]]>" + "\r\n");
			stringBuffer.append("\t\t\t" + "</if>" + "\r\n");

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

			String columnNameEn = CodeUtil.getTuoFengName(tableObject.getColumnNameEn(), false);// 要被搜索的字段名,驼峰法变成小写

			// <if test="sampleId != null"> REMARK=#{remark}, </if>
			stringBuffer.append("\t\t\t" + "<if test=\"" + columnNameEn + " != null\">" + "\r\n");
			stringBuffer.append("\t\t\t\t" + "<![CDATA[\t" + tableObject.getColumnNameEn() + " = #{" + columnNameEn + "},\t]]>" + "\r\n");
			stringBuffer.append("\t\t\t" + "</if>" + "\r\n");

		}

		return stringBuffer.toString();
	}

	public static void main(String[] args) {
		CreateMybatisMapperXml.create(CodeUtil.createTableName, "Mapper");
	}
}
