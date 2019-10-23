package com.zjmzxfzhl.codeCreate.single;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.zjmzxfzhl.codeCreate.util.CodeUtil;
import com.zjmzxfzhl.codeCreate.util.TableObject;
import com.zjmzxfzhl.common.util.CommonUtil;

public class CreateEntity {

	public static void create(String createTableName) {
		StringBuffer stringBufferAll = new StringBuffer();

		HashMap<String, ArrayList<TableObject>> hashMap = CodeUtil.readExcelDesignInfo(createTableName);
		String moduleId = StringUtils.substringAfter(createTableName, "T_");// T_SYS_CODE_TYPE变成SYS_CODE_TYPE
		String _SysCodeType = CodeUtil.getTuoFengName(moduleId, true);// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
		// String _sysCodeType = CodeUtil.getTuoFengName(moduleId, false);// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解

		String _SYS = StringUtils.substringBefore(moduleId, "_");// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
		String _sys = _SYS.toLowerCase();// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
		// String _Sys = CodeUtil.getTuoFengName(_sys, true);// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解

		// String _CODE_TYPE = StringUtils.substringAfter(moduleId, "_").toUpperCase();
		// String _CodeType = CodeUtil.getTuoFengName(StringUtils.substringAfter(moduleId, "_"), true);// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
		// String _codeType = CodeUtil.getTuoFengName(StringUtils.substringAfter(moduleId, "_"), false);// 用T_SYS_CODE_TYPE表的方式命名变量,更好理解

		for (Object tableName : hashMap.keySet()) {
			ArrayList<TableObject> list = hashMap.get(tableName); // 读到的表名对象list.

			StringBuffer stringBufferHead = new StringBuffer(); // package + import 部分
			StringBuffer stringBufferHead2 = new StringBuffer(); // public等声明部分
			StringBuffer stringBufferMid = new StringBuffer(); // 中间字段循环代码
			StringBuffer stringBufferFoot = new StringBuffer(); // 主键代码+注释

			// package部分
			stringBufferHead.append("package com.zjmzxfzhl.modules." + _sys + ".entity;" + "\r\n\r\n");

			// 避免import重复,这3个import是一定有的
			TreeMap<String, String> importHashMap = new TreeMap<String, String>();
			importHashMap.put("import javax.validation.constraints.NotNull;\r\n", "import javax.validation.constraints.NotNull;\r\n");
			importHashMap.put("import com.baomidou.mybatisplus.annotation.TableId;\r\n", "import com.baomidou.mybatisplus.annotation.TableId;\r\n");
			importHashMap.put("import com.baomidou.mybatisplus.annotation.TableName;\r\n", "import com.baomidou.mybatisplus.annotation.TableName;\r\n");
			importHashMap.put("import com.zjmzxfzhl.common.base.BaseEntity;\r\n", "import com.zjmzxfzhl.common.base.BaseEntity;\\r\\n");
			importHashMap.put("import com.zjmzxfzhl.common.validator.constraints.LengthForUTF8;\r\n", "import com.zjmzxfzhl.common.validator.constraints.LengthForUTF8;\r\n");
			importHashMap.put("import lombok.Getter;\r\n", "import lombok.Getter;\r\n");
			importHashMap.put("import lombok.Setter;\r\n", "import lombok.Setter;\r\n");

			for (int i = 0; i < list.size(); i++) {
				TableObject tableObject = list.get(i);
				if (i == 0) {
					String tableNameCn = tableObject.getTableNameCn();
					tableNameCn = StringUtils.substringAfter(tableNameCn, "_");// 取出中文表名中的前缀,A01_系统参数表.
					tableNameCn = StringUtils.substringBeforeLast(tableNameCn, "表");

					stringBufferHead2.append("/**" + "\r\n");
					stringBufferHead2.append(" * 【" + tableNameCn + "】实体类" + "\r\n");
					stringBufferHead2.append(" * " + "\r\n");
					stringBufferHead2.append(" * @author " + CodeUtil.author + "\r\n");
					stringBufferHead2.append(" */" + "\r\n");
					stringBufferHead2.append("@Getter" + "\r\n");
					stringBufferHead2.append("@Setter" + "\r\n");
					stringBufferHead2.append("@TableName(\"" + tableName + "\")" + "\r\n");
					stringBufferHead2.append("public class " + _SysCodeType + " extends BaseEntity {" + "\r\n\r\n");

					stringBufferHead2.append("\t" + "private static final long serialVersionUID = 1L;" + "\r\n\r\n");
				}

				// 字段名称,定义变量,方便引用,用CODE_TYPE_ID进行举例
				String _CodeTypeId = CodeUtil.getTuoFengName(tableObject.getColumnNameEn(), true);
				String _codeTypeId = CodeUtil.getTuoFengName(tableObject.getColumnNameEn(), false);

				if ("createBy".equals(_codeTypeId) || "createDate".equals(_codeTypeId) || "createTime".equals(_codeTypeId) || "updateBy".equals(_codeTypeId) || "updateDate".equals(_codeTypeId) || "updateTime".equals(_codeTypeId)) {
					continue;
				}

				if (CommonUtil.isExist("UUID主键", tableObject.getIsNull(), ",")) {
					stringBufferMid.append("\t" + "@TableId(type = IdType.UUID)" + "\r\n");
					importHashMap.put("import com.baomidou.mybatisplus.annotation.IdType;\r\n", "import com.baomidou.mybatisplus.annotation.IdType;\r\n");
				} else if (CommonUtil.isExist("数据库生成主键,前台输入主键", tableObject.getIsNull(), ",")) {
					stringBufferMid.append("\t" + "@TableId" + "\r\n");
				}
				if (CommonUtil.isExist("UUID主键,数据库生成主键,前台输入主键,不空", tableObject.getIsNull(), ",")) {
					stringBufferMid.append("\t" + "@NotNull" + "\r\n");
				}
				// 处理数据类型
				if ("字符型".equals(tableObject.getDataType())) {
					stringBufferMid.append("\t" + "@LengthForUTF8(max = " + tableObject.getDataLength() + ")" + "\r\n");
					stringBufferMid.append("\t" + "private String " + _codeTypeId + ";// " + tableObject.getColumnNameCn() + "\r\n\r\n");

					// 处理get,set方法
					stringBufferFoot.append("\t" + "public String get" + _CodeTypeId + "() {" + "\r\n");
					stringBufferFoot.append("\t\t" + "return this." + _codeTypeId + ";\r\n");
					stringBufferFoot.append("\t" + "}" + "\r\n\r\n");
					stringBufferFoot.append("\t" + "public void set" + _CodeTypeId + "(String " + _codeTypeId + ") {" + "\r\n");
					stringBufferFoot.append("\t\t" + "this." + _codeTypeId + " = " + _codeTypeId + ";\r\n");
					stringBufferFoot.append("\t" + "}" + "\r\n\r\n");
				} else if ("整数型".equals(tableObject.getDataType())) {
					if (CommonUtil.isEmptyStr(tableObject.getDataLength()) || Integer.valueOf(tableObject.getDataLength()) < 11) {
						stringBufferMid.append("\t" + "@Max(" + getMax(tableObject.getDataLength(), null) + ")" + "\r\n");
						importHashMap.put("import javax.validation.constraints.Max;\r\n", "import javax.validation.constraints.Max;\r\n");

						stringBufferMid.append("\t" + "private Integer " + _codeTypeId + ";// " + tableObject.getColumnNameCn() + "\r\n\r\n");

						// 处理get,set方法
						stringBufferFoot.append("\t" + "public Integer get" + _CodeTypeId + "() {" + "\r\n");
						stringBufferFoot.append("\t\t" + "return this." + _codeTypeId + ";\r\n");
						stringBufferFoot.append("\t" + "}" + "\r\n\r\n");
						stringBufferFoot.append("\t" + "public void set" + _CodeTypeId + "(Integer " + _codeTypeId + ") {" + "\r\n");
						stringBufferFoot.append("\t\t" + "this." + _codeTypeId + " = " + _codeTypeId + ";\r\n");
						stringBufferFoot.append("\t" + "}" + "\r\n\r\n");
					} else {
						stringBufferMid.append("\t" + "private Long " + _codeTypeId + ";// " + tableObject.getColumnNameCn() + "\r\n\r\n");

						// 处理get,set方法
						stringBufferFoot.append("\t" + "public Long get" + _CodeTypeId + "() {" + "\r\n");
						stringBufferFoot.append("\t\t" + "return this." + _codeTypeId + ";\r\n");
						stringBufferFoot.append("\t" + "}" + "\r\n\r\n");
						stringBufferFoot.append("\t" + "public void set" + _CodeTypeId + "(Long " + _codeTypeId + ") {" + "\r\n");
						stringBufferFoot.append("\t\t" + "this." + _codeTypeId + " = " + _codeTypeId + ";\r\n");
						stringBufferFoot.append("\t" + "}" + "\r\n\r\n");
					}
				} else if ("浮点型".equals(tableObject.getDataType())) {
					stringBufferMid.append("\t" + "@DecimalMax(\"" + getMax(tableObject.getDataLength(), tableObject.getDataPrecision()) + "\")" + "\r\n");
					importHashMap.put("import javax.validation.constraints.DecimalMax;\r\n", "import javax.validation.constraints.DecimalMax;\r\n");
					importHashMap.put("import java.math.BigDecimal;\r\n", "import java.math.BigDecimal;\r\n");

					stringBufferMid.append("\t" + "private BigDecimal " + _codeTypeId + ";// " + tableObject.getColumnNameCn() + "\r\n\r\n");

					// 处理get,set方法
					stringBufferFoot.append("\t" + "public BigDecimal get" + _CodeTypeId + "() {" + "\r\n");
					stringBufferFoot.append("\t\t" + "return this." + _codeTypeId + ";\r\n");
					stringBufferFoot.append("\t" + "}" + "\r\n\r\n");
					stringBufferFoot.append("\t" + "public void set" + _CodeTypeId + "(BigDecimal " + _codeTypeId + ") {" + "\r\n");
					stringBufferFoot.append("\t\t" + "this." + _codeTypeId + " = " + _codeTypeId + ";\r\n");
					stringBufferFoot.append("\t" + "}" + "\r\n\r\n");
				} else if ("日期型".equals(tableObject.getDataType())) {
					stringBufferMid.append("\t" + "@JsonFormat(pattern = \"yyyy-MM-dd\", timezone = \"GMT+8\")" + "\r\n");
					stringBufferMid.append("\t" + "@DateTimeFormat(pattern = \"yyyy-MM-dd\")" + "\r\n");

					stringBufferMid.append("\t" + "private Date " + _codeTypeId + ";// " + tableObject.getColumnNameCn() + "\r\n\r\n");

					// 处理get,set方法
					stringBufferFoot.append("\t" + "public Date get" + _CodeTypeId + "() {" + "\r\n");
					stringBufferFoot.append("\t\t" + "return this." + _codeTypeId + ";\r\n");
					stringBufferFoot.append("\t" + "}" + "\r\n\r\n");
					stringBufferFoot.append("\t" + "public void set" + _CodeTypeId + "(Date " + _codeTypeId + ") {" + "\r\n");
					stringBufferFoot.append("\t\t" + "this." + _codeTypeId + " = " + _codeTypeId + ";\r\n");
					stringBufferFoot.append("\t" + "}" + "\r\n\r\n");

					importHashMap.put("import java.util.Date;\r\n", "import java.util.Date;\r\n");
					importHashMap.put("import com.fasterxml.jackson.annotation.JsonFormat;\r\n", "import com.fasterxml.jackson.annotation.JsonFormat;\r\n");
					importHashMap.put("import org.springframework.format.annotation.DateTimeFormat;\r\n", "org.springframework.format.annotation.DateTimeFormat;\r\n");
				} else if ("时间型".equals(tableObject.getDataType())) {
					stringBufferMid.append("\t" + "@JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\", timezone = \"GMT+8\")" + "\r\n");
					stringBufferMid.append("\t" + "@DateTimeFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")" + "\r\n");

					stringBufferMid.append("\t" + "private Date " + _codeTypeId + ";// " + tableObject.getColumnNameCn() + "\r\n\r\n");

					// 处理get,set方法
					stringBufferFoot.append("\t" + "public Date get" + _CodeTypeId + "() {" + "\r\n");
					stringBufferFoot.append("\t\t" + "return this." + _codeTypeId + ";\r\n");
					stringBufferFoot.append("\t" + "}" + "\r\n\r\n");
					stringBufferFoot.append("\t" + "public void set" + _CodeTypeId + "(Date " + _codeTypeId + ") {" + "\r\n");
					stringBufferFoot.append("\t\t" + "this." + _codeTypeId + " = " + _codeTypeId + ";\r\n");
					stringBufferFoot.append("\t" + "}" + "\r\n\r\n");

					importHashMap.put("import java.util.Date;\r\n", "import java.util.Date;\r\n");
					importHashMap.put("import com.fasterxml.jackson.annotation.JsonFormat;\r\n", "import com.fasterxml.jackson.annotation.JsonFormat;\r\n");
					importHashMap.put("import org.springframework.format.annotation.DateTimeFormat;\r\n", "org.springframework.format.annotation.DateTimeFormat;\r\n");
				}
			}

			// stringBufferHead要加上import的内容
			for (Object importContent : importHashMap.keySet()) {
				stringBufferHead.append(importContent);
			}
			stringBufferHead.append("\r\n");

			// stringBufferAll.append(stringBufferHead).append(stringBufferHead2).append(stringBufferMid).append(stringBufferFoot).append("}");
			// 使用lombok，无需get/set方法
			stringBufferAll.append(stringBufferHead).append(stringBufferHead2).append(stringBufferMid).append("}");
		}

		// 实体类.
		try {
			String filePath = CodeUtil.projectPath + "src\\main\\java\\com\\zjmzxfzhl\\modules\\" + _sys + "\\" + "\\entity\\";
			File file = new File(filePath);
			file.mkdirs();
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(filePath + _SysCodeType + ".java")));
			bufferedWriter.write(stringBufferAll.toString());

			bufferedWriter.close();

			System.out.println("已经生成--entity实体类!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getMax(String dataLength, String dataPrecision) {
		int dataLenghInt = CommonUtil.isEmptyAfterTrim(dataLength) ? 0 : Integer.valueOf(dataLength);
		int dataPrecisionInt = CommonUtil.isEmptyAfterTrim(dataPrecision) ? 0 : Integer.valueOf(dataPrecision);

		String result = "";
		if (dataPrecisionInt > 0) {
			dataLenghInt = dataLenghInt - dataPrecisionInt - 1;
		}
		for (int i = 0; i < dataLenghInt; i++) {
			result += "9";
		}
		if (dataPrecisionInt > 0) {
			result += ".";
		}
		for (int i = 0; i < dataPrecisionInt; i++) {
			result += "9";
		}
		return result;
	}

	/**
	 * 测试驱动开发
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CreateEntity.create(CodeUtil.createTableName);
	}
}
