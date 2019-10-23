package com.zjmzxfzhl.codeCreate.util;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;

import com.zjmzxfzhl.common.util.CommonUtil;

public class CodeUtil {

	public static boolean isPringInsertMenuSql = true;// 是否打印插入菜单sql

	public static String author = "庄金明";// 开发人员姓名
	public static String databaseType = "mysql";// 数据库类型：oracle,mysql

	public static String projectPath = System.getProperty("user.dir") + "\\";// 开发工程所在系统绝对路径

	public static String createSqlPath = projectPath + "db\\tables\\";// 自动生成的建表语句的输出路径
	public static String excelDesignPath = projectPath + "db\\数据库设计【DEMO_示例管理】.xlsx";

	public static String createTableName = "T_DEMO_ZJMZXFZHL";// 必须大写

	/**
	 * 返回驼峰命名法结果.
	 * 
	 * @param name
	 *            如:CODE_TYPE;Menu
	 * @return 如: codeType, menu
	 */
	public static String getTuoFengName(String name, boolean firstUpper) {
		StringBuffer stringBuffer = new StringBuffer();

		char[] nameArray = name.toLowerCase().toCharArray();
		boolean nextNeedUpper = false; // 下一个字母要大写
		for (int i = 0; i < nameArray.length; i++) {
			String tmp = String.valueOf(nameArray[i]);

			// 如果第一个字母大写
			if (firstUpper && i == 0) {
				stringBuffer.append(tmp.toUpperCase());
			} else {
				if (tmp.equals("_")) {
					nextNeedUpper = true;
					continue;
				}

				// 下一个字母要大写
				if (nextNeedUpper) {
					stringBuffer.append(tmp.toUpperCase());
					nextNeedUpper = false;
				} else {
					stringBuffer.append(tmp);
				}
			}
		}

		return stringBuffer.toString();
	}

	/**
	 * 
	 * @return 返回MAP,英文表名为key值.value是表的字段对象list
	 */
	public static LinkedHashMap<String, ArrayList<TableObject>> readExcelDesignInfo(String theTableName) {
		LinkedHashMap<String, ArrayList<TableObject>> hashMap = new LinkedHashMap<String, ArrayList<TableObject>>();

		File file = new File(CodeUtil.excelDesignPath);
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(file);
			Sheet sheet = workbook.getSheetAt(1);

			String tableName = "";
			String tableNameCn = "";
			ArrayList<TableObject> list = new ArrayList<TableObject>();
			for (int i = 0; i < sheet.getLastRowNum(); i++) {
				String cellValue4 = CodeUtil.getStringRowCellValue(sheet.getRow(i), 3);// 第4列内容

				if (StringUtils.isEmpty(cellValue4)) {
					if (list.size() > 1) {
						hashMap.put(tableName, list);// 将表结构put
						list = new ArrayList<TableObject>();
					}
					break;
				} else if ("字段英文名".equals(cellValue4)) {
					if (list.size() > 1) {
						hashMap.put(tableName, list);// 将表结构put
						list = new ArrayList<TableObject>();
					}
					continue;
				} else {
					// 第一列的表名
					String cellValue1 = CodeUtil.getStringRowCellValue(sheet.getRow(i), 0);// 第1列内容
					if (StringUtils.isNotEmpty(cellValue1)) {
						if (cellValue1.startsWith("T_")) {
							tableName = cellValue1;
							tableNameCn = CodeUtil.getStringRowCellValue(sheet.getRow(i + 1), 0);
						}
					}

					// 组织TableObject对象.
					TableObject tableObject = new TableObject();
					tableObject.setTableName(tableName);
					tableObject.setTableNameCn(tableNameCn);
					tableObject.setSortNo(CodeUtil.getStringRowCellValue(sheet.getRow(i), 1));
					tableObject.setColumnNameEn(CodeUtil.getStringRowCellValue(sheet.getRow(i), 3));
					tableObject.setColumnNameCn(CodeUtil.getStringRowCellValue(sheet.getRow(i), 2));

					// 数据类型
					String dataType = CodeUtil.getStringRowCellValue(sheet.getRow(i), 4);
					if (CommonUtil.isExist("字符型,整数型,浮点型,日期型,时间型,大文本,大文件", dataType, ",")) {
						tableObject.setDataType(dataType);
					} else {
						throw new Exception("excel设计中,第" + (i + 1) + "行【数据类型】取值错误!");
					}

					tableObject.setDataLength(CodeUtil.getStringRowCellValue(sheet.getRow(i), 5));
					tableObject.setDataPrecision(CodeUtil.getStringRowCellValue(sheet.getRow(i), 6));

					// 是否可空
					String isNull = CodeUtil.getStringRowCellValue(sheet.getRow(i), 7);
					if (CommonUtil.isExist("UUID主键,数据库生成主键,前台输入主键,不空,可空", isNull, ",")) {
						tableObject.setIsNull(isNull);
					} else {
						throw new Exception("excel设计中,第" + (i + 1) + "行【是否可空】取值错误!");
					}

					// 是否列表
					String isList = CodeUtil.getStringRowCellValue(sheet.getRow(i), 8);
					if (!CommonUtil.isEmptyStr(isList)) {
						if (CommonUtil.isExist("是,否", isList, ",")) {
							tableObject.setIsList("是".equals(isList));
						} else {
							throw new Exception("excel设计中,第" + (i + 1) + "行【是否列表】取值错误!");
						}
					}

					// 是否查询条件
					String doSearch = CodeUtil.getStringRowCellValue(sheet.getRow(i), 9);
					if (!CommonUtil.isEmptyStr(doSearch)) {
						if (CommonUtil.isExist("是,否", doSearch, ",")) {
							tableObject.setIsSearch("是".equals(doSearch));
						} else {
							throw new Exception("excel设计中,第" + (i + 1) + "行【是否查询】取值错误!");
						}
					}

					tableObject.setCodeTypeId(CodeUtil.getStringRowCellValue(sheet.getRow(i), 10));

					// 详细说明
					tableObject.setRemark(CodeUtil.getStringRowCellValue(sheet.getRow(i), 11));

					list.add(tableObject);

					// 到了最后一行,put数据
					if (i == sheet.getLastRowNum() - 1) {
						hashMap.put(tableName, list);// 将表结构put
						list = new ArrayList<TableObject>();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// System.out.println(hashMap);

		if (StringUtils.isNotEmpty(theTableName)) {
			LinkedHashMap<String, ArrayList<TableObject>> hm = new LinkedHashMap<String, ArrayList<TableObject>>();
			hm.put(theTableName, hashMap.get(theTableName));
			return hm;
		}

		return hashMap;
	}

	/**
	 * 读取excel的时候,取得String型的值.
	 * 
	 * @param row
	 *            excel行对象
	 * @param cellIndex
	 *            从0开始算
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getStringRowCellValue(Row row, int cellIndex) {
		String result = "";

		try {
			if (row != null) {
				Cell cell = row.getCell(cellIndex);
				if (cell != null) {
					switch (cell.getCellType()) {
					case XSSFCell.CELL_TYPE_STRING:
						result = StringUtils.trimToEmpty(cell.getStringCellValue());
						break;
					case XSSFCell.CELL_TYPE_NUMERIC:
						cell.setCellType(1);// 把数字型转换成 字符串
						result = StringUtils.trimToEmpty(cell.getStringCellValue());
						break;
					case XSSFCell.CELL_TYPE_BOOLEAN:
						result = String.valueOf(cell.getBooleanCellValue());
						break;
					case XSSFCell.CELL_TYPE_BLANK:
						result = "";
						break;
					default:
						result = "";
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 测试驱动开发
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// System.out.println(CodeUtil.getTuoFengName("CODE_TYPE", true));
		// CodeUtil.readExcelDesignInfo("T_DEMO_CONFIG");
	}
}
