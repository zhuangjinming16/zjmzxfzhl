package com.zjmzxfzhl.codeCreate.single;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

import com.zjmzxfzhl.codeCreate.util.CodeUtil;
import com.zjmzxfzhl.codeCreate.util.TableObject;

public class CreateMybatisMapper {

	@SuppressWarnings("resource")
	public static void create(String createTableName, String templetPath) {
		StringBuffer stringBufferAll = new StringBuffer();// 所有Service代码

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
		File serviceFile = new File(CodeUtil.projectPath + "src\\main\\java\\com\\zjmzxfzhl\\codeCreate\\template\\" + templetPath + "\\Mapper.txt");
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

		// 读取中文表名
		ArrayList<TableObject> list = hashMap.get(createTableName); // 读到的表名对象list.
		String tableNameCn = "";
		for (int i = 0; i < list.size(); i++) {
			TableObject tableObject = list.get(i);
			tableNameCn = tableObject.getTableNameCn();
			tableNameCn = StringUtils.substringAfter(tableNameCn, "_");// 取出中文表名中的前缀,A01_系统参数表.
			tableNameCn = StringUtils.substringBeforeLast(tableNameCn, "表");
		}
		// Mapper的Mapper类.
		try {
			String filePath = CodeUtil.projectPath + "src\\main\\java\\com\\zjmzxfzhl\\modules\\" + _sys + "\\mapper\\";
			File file = new File(filePath);
			file.mkdirs();
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(filePath + _Sys + _CodeType + "Mapper.java")));

			// 根据表名替换:关键词
			String temp = stringBufferAll.toString();
			temp = StringUtils.replace(temp, "{_SYS}", _SYS);
			temp = StringUtils.replace(temp, "{_Sys}", _Sys);
			temp = StringUtils.replace(temp, "{_sys}", _sys);
			temp = StringUtils.replace(temp, "{_CODE_TYPE}", _CODE_TYPE);
			temp = StringUtils.replace(temp, "{_CodeType}", _CodeType);
			temp = StringUtils.replace(temp, "{_codeType}", _codeType);

			temp = StringUtils.replace(temp, "{tableNameCn}", tableNameCn);
			// 替换开发人员姓名
			if (StringUtils.isEmpty(CodeUtil.author)) {
				System.out.println("代码生成失败，请修改开发人员姓名!");
				return;
			} else {
				temp = StringUtils.replace(temp, "{author}", CodeUtil.author);// 替换--开发人员姓名
			}

			bufferedWriter.write(temp);

			bufferedWriter.close();

			System.out.println("已经生成--mybatisMapper.java类!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试驱动开发
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CreateService.create(CodeUtil.createTableName, "01CRUDE_I");
	}
}
