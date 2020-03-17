package com.zjmzxfzhl.codeCreate.single;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.zjmzxfzhl.codeCreate.util.CodeUtil;
import com.zjmzxfzhl.codeCreate.util.TableObject;
import com.zjmzxfzhl.common.util.CommonUtil;

public class CreateVue {

	/**
	 * 
	 * @param createTableName
	 *            需要创建的表名
	 * @param templetPath
	 *            模板路径
	 */
	@SuppressWarnings("resource")
	public static void create(String createTableName, String templetPath) {
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

		String _searchConditions = ""; // 查询条件
		String _searchConditionsData = ""; // 查询条件
		String _searchColumnsList = ""; // 列表字段
		String _primaryKey = ""; // 主键
		String _allColumnsDialog = ""; // 弹出框所有属性
		String _allColumnsData = "";
		String _allColumnsRules = "";
		String _loadDicts = "";

		Map<String, String> dicts = new LinkedHashMap<>();
		// 读取模板内容
		File actionFile = new File(CodeUtil.projectPath + "src\\main\\java\\com\\zjmzxfzhl\\codeCreate\\template\\" + templetPath + "\\Vue.txt");
		try {
			FileInputStream fileInputStream = new FileInputStream(actionFile);
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
		String tableNameCn = "";// 中文表名
		for (int i = 0; i < list.size(); i++) {
			TableObject tableObject = list.get(i);

			if (i == 0) {
				tableNameCn = tableObject.getTableNameCn();
				tableNameCn = StringUtils.substringAfter(tableNameCn, "_");// 取出中文表名中的前缀,A01_系统参数表.
				tableNameCn = StringUtils.substringBeforeLast(tableNameCn, "表");
			}

			// 字段名称,定义变量,方便引用,用CODE_TYPE_ID进行举例
			// String _CodeTypeId = CodeUtil.getTuoFengName(tableObject.getColumnNameEn(), true);
			String _codeTypeId = CodeUtil.getTuoFengName(tableObject.getColumnNameEn(), false);

			if ("createBy".equals(_codeTypeId) || "createDate".equals(_codeTypeId) || "createTime".equals(_codeTypeId)
					|| "updateBy".equals(_codeTypeId) || "updateDate".equals(_codeTypeId) || "updateTime".equals(_codeTypeId)) {
				continue;
			}

			Boolean isPrimaryKey = CommonUtil.isExist("UUID主键,数据库生成主键,前台输入主键", tableObject.getIsNull(), ",");
			Boolean isNotNull = CommonUtil.isExist("UUID主键,数据库生成主键,前台输入主键,不空", tableObject.getIsNull(), ",");
			String readOnlyStr = "";
			if (isPrimaryKey) {
				_primaryKey = _codeTypeId;
				readOnlyStr = " :readonly=\"dialogStatus==='update'\"";
				_allColumnsData += "                    " + _codeTypeId + ": undefined," + "\r\n";
			} else {
				_allColumnsData += "                    " + _codeTypeId + ": ''," + "\r\n";
			}

			if (tableObject.getIsSearch()) {// 该字段是查询条件
				if (StringUtils.isNotEmpty(tableObject.getCodeTypeId())) {
					_searchConditions += "            <el-select v-model=\"listQuery." + _codeTypeId + "\" placeholder=\""
							+ tableObject.getColumnNameCn() + "\" class=\"filter-item\"><el-option v-for=\"(item, index) in dicts."
							+ tableObject.getCodeTypeId() + "\" :key=\"index\" :label=\"item.content\" :value=\"item.value\"></el-option></el-select>"
							+ "\r\n";
				} else {
					if ("日期型".equals(tableObject.getDataType())) {
						_searchConditions += "            <el-date-picker v-model=\"listQuery." + _codeTypeId + "\" placeholder=\""
								+ tableObject.getColumnNameCn() + "\" type=\"date\" style=\"width: 200px;\" class=\"filter-item\"></el-date-picker>"
								+ "\r\n";
					} else if ("时间型".equals(tableObject.getDataType())) {
						_searchConditions += "            <el-date-picker v-model=\"listQuery." + _codeTypeId + "\" placeholder=\""
								+ tableObject.getColumnNameCn()
								+ "\" type=\"datetime\" value-format=\"yyyy-MM-dd HH:mm:ss\" style=\"width: 200px;\" class=\"filter-item\"></el-date-picker>"
								+ "\r\n";
					} else {
						_searchConditions += "            <el-input v-model=\"listQuery." + _codeTypeId + "\" placeholder=\""
								+ tableObject.getColumnNameCn()
								+ "\" style=\"width: 200px;\" class=\"filter-item\" @keyup.enter.native=\"btnQuery\"/>" + "\r\n";
					}

				}
				_searchConditionsData += "                    " + _codeTypeId + ": undefined," + "\r\n";
			}

			if (tableObject.getIsList()) {// 该字段是列表字段
				if (StringUtils.isNotEmpty(tableObject.getCodeTypeId())) {
					_searchColumnsList += "            <el-table-column label=\"" + tableObject.getColumnNameCn() + "\" prop=\"" + _codeTypeId
							+ "\" align=\"center\"><template slot-scope=\"scope\"><span v-html=\"formatDictText(dicts." + tableObject.getCodeTypeId()
							+ ",scope.row." + _codeTypeId + ")\"></span></template></el-table-column>" + "\r\n";
				} else {
					_searchColumnsList += "            <el-table-column label=\"" + tableObject.getColumnNameCn() + "\" prop=\"" + _codeTypeId
							+ "\" align=\"center\"><template slot-scope=\"scope\"><span>{{ scope.row." + _codeTypeId
							+ " }}</span></template></el-table-column>" + "\r\n";
				}
			}

			if (StringUtils.isNotEmpty(tableObject.getCodeTypeId())) {
				_allColumnsDialog += "                <el-form-item label=\"" + tableObject.getColumnNameCn() + "\" prop=\"" + _codeTypeId
						+ "\"><el-select v-model=\"temp." + _codeTypeId + "\" placeholder=\"" + tableObject.getColumnNameCn()
						+ "\"><el-option v-for=\"(item, index) in dicts." + tableObject.getCodeTypeId()
						+ "\" :key=\"index\" :label=\"item.content\" :value=\"item.value\"></el-option></el-select></el-form-item>" + "\r\n";
			} else {

				if ("日期型".equals(tableObject.getDataType())) {
					_allColumnsDialog += "                <el-form-item label=\"" + tableObject.getColumnNameCn() + "\" prop=\"" + _codeTypeId
							+ "\"><el-date-picker v-model=\"temp." + _codeTypeId + "\" type=\"date\"></el-date-picker></el-form-item>" + "\r\n";
				} else if ("时间型".equals(tableObject.getDataType())) {
					_allColumnsDialog += "                <el-form-item label=\"" + tableObject.getColumnNameCn() + "\" prop=\"" + _codeTypeId
							+ "\"><el-date-picker v-model=\"temp." + _codeTypeId
							+ "\" type=\"datetime\" value-format=\"yyyy-MM-dd HH:mm:ss\"></el-date-picker></el-form-item>" + "\r\n";
				} else {
					_allColumnsDialog += "                <el-form-item label=\"" + tableObject.getColumnNameCn() + "\" prop=\"" + _codeTypeId
							+ "\"><el-input v-model=\"temp." + _codeTypeId + "\"" + readOnlyStr + "/></el-form-item>" + "\r\n";
				}
			}

			if (isNotNull) {
				_allColumnsRules += "                    " + _codeTypeId + ": [{required: true, message: '该项不能为空', trigger: 'change'}]," + "\r\n";
			}

			if (StringUtils.isNotEmpty(tableObject.getCodeTypeId())) {
				dicts.put(tableObject.getCodeTypeId(), tableObject.getCodeTypeId());
			}
		}

		_searchConditions = replaceLast(_searchConditions, "\r\n", "");
		_searchColumnsList = replaceLast(_searchColumnsList, "\r\n", "");
		_allColumnsDialog = replaceLast(_allColumnsDialog, "\r\n", "");
		_searchConditionsData = replaceLast(_searchConditionsData, ",\r\n", "");
		_allColumnsData = replaceLast(_allColumnsData, ",\r\n", "");
		_allColumnsRules = replaceLast(_allColumnsRules, ",\r\n", "");

		String dictsStr = "";
		for (String key : dicts.keySet()) {
			dictsStr += key + ",";
		}
		dictsStr = replaceLast(dictsStr, ",", "");
		if (StringUtils.isNotBlank(dictsStr)) {
			_loadDicts = "            this.getDicts('" + dictsStr + "').then(({data}) => {this.dicts = data})";
		}
		try {
			String filePath = CodeUtil.projectPath + "vue\\modules\\" + _sys + "\\" + _codeType + "\\";
			File file = new File(filePath);
			file.mkdirs();
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(filePath + "index.vue")));

			// 根据表名替换:关键词
			String temp = stringBufferAll.toString();
			temp = StringUtils.replace(temp, "{_SYS}", _SYS);
			temp = StringUtils.replace(temp, "{_Sys}", _Sys);
			temp = StringUtils.replace(temp, "{_sys}", _sys);
			temp = StringUtils.replace(temp, "{_CODE_TYPE}", _CODE_TYPE);
			temp = StringUtils.replace(temp, "{_CodeType}", _CodeType);
			temp = StringUtils.replace(temp, "{_codeType}", _codeType);

			temp = StringUtils.replace(temp, "{tableNameCn}", tableNameCn);

			temp = StringUtils.replace(temp, "{_searchConditions}", _searchConditions);
			temp = StringUtils.replace(temp, "{_searchConditionsData}", _searchConditionsData);
			temp = StringUtils.replace(temp, "{_searchColumnsList}", _searchColumnsList);
			temp = StringUtils.replace(temp, "{_primaryKey}", _primaryKey);
			temp = StringUtils.replace(temp, "{_allColumnsDialog}", _allColumnsDialog);
			temp = StringUtils.replace(temp, "{_allColumnsData}", _allColumnsData);
			temp = StringUtils.replace(temp, "{_allColumnsRules}", _allColumnsRules);
			temp = StringUtils.replace(temp, "{_loadDicts}", _loadDicts);

			bufferedWriter.write(temp);

			bufferedWriter.close();

			System.out.println("已经生成--Vue文件!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String replaceLast(String text, String strToReplace, String replaceWithThis) {
		return text.replaceFirst("(?s)" + strToReplace + "(?!.*?" + strToReplace + ")", replaceWithThis);
	}

	public static void main(String[] args) {
		CreateVue.create(CodeUtil.createTableName, "01CRUDE_I");
	}
}
