package com.zjmzxfzhl.codegenerator.first;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.zjmzxfzhl.codegenerator.util.CodeUtil;
import com.zjmzxfzhl.codegenerator.util.TableObject;
import com.zjmzxfzhl.common.core.util.CommonUtil;

/**
 * @author 庄金明
 * @date 2020年3月22日
 */
public class CreateVue {

    private final static String DATE_TYPE = "日期型";
    private final static String DATETIME_TYPE = "时间型";

    /**
     * 
     * @param createTableName
     *            需要创建的表名
     * @param templetPath
     *            模板路径
     */
    public static void create(String createTableName, String templetPath) {
        StringBuffer stringBufferAll = new StringBuffer();
        HashMap<String, ArrayList<TableObject>> hashMap = CodeUtil.readExcelDesignInfo(createTableName);
        // T_SYS_CODE_TYPE变成SYS_CODE_TYPE
        String moduleId = StringUtils.substringAfter(createTableName, "T_");
        // 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
        String sysUpper = StringUtils.substringBefore(moduleId, "_");
        String sys = sysUpper.toLowerCase();
        String aSys = CodeUtil.getTuoFengName(sys, true);
        String aCodeType = CodeUtil.getTuoFengName(StringUtils.substringAfter(moduleId, "_"), true);
        String codeType = CodeUtil.getTuoFengName(StringUtils.substringAfter(moduleId, "_"), false);
        String searchConditions = "", searchConditionsData = "", searchColumnsList = "", primaryKey = "",
                allColumnsDialog = "", allColumnsData = "", allColumnsRules = "", loadDicts = "";
        Map<String, String> dicts = new LinkedHashMap<>();

        readStringBufferAll(templetPath, stringBufferAll);
        // 读取中文表名
        // 读到的表名对象list.
        ArrayList<TableObject> list = hashMap.get(createTableName);
        // 中文表名
        String tableNameCn = "";
        for (int i = 0; i < list.size(); i++) {
            TableObject tableObject = list.get(i);
            if (i == 0) {
                tableNameCn = tableObject.getTableNameCn();
                // 取出中文表名中的前缀,A01_系统参数表.
                tableNameCn = StringUtils.substringAfter(tableNameCn, "_");
                tableNameCn = StringUtils.substringBeforeLast(tableNameCn, "表");
            }

            // 字段名称,定义变量,方便引用,用CODE_TYPE_ID进行举例
            // String _CodeTypeId = CodeUtil.getTuoFengName(tableObject.getColumnNameEn(), true);
            String codeTypeId = CodeUtil.getTuoFengName(tableObject.getColumnNameEn(), false);
            if ("createBy".equals(codeTypeId) || "createDate".equals(codeTypeId) || "createTime".equals(codeTypeId)
                    || "updateBy".equals(codeTypeId) || "updateDate".equals(codeTypeId)
                    || "updateTime".equals(codeTypeId)) {
                continue;
            }
            Boolean isPrimaryKey = CommonUtil.isExist("UUID主键,数据库生成主键,前台输入主键", tableObject.getIsNull(), ",");
            Boolean isNotNull = CommonUtil.isExist("UUID主键,数据库生成主键,前台输入主键,不空", tableObject.getIsNull(), ",");
            String readOnlyStr = "";
            if (isPrimaryKey) {
                primaryKey = codeTypeId;
                readOnlyStr = " :readonly=\"dialogStatus==='update'\"";
                allColumnsData += "                    " + codeTypeId + ": undefined," + "\r\n";
            } else {
                allColumnsData += "                    " + codeTypeId + ": ''," + "\r\n";
            }
            // 该字段是查询条件
            if (tableObject.getIsSearch()) {
                searchConditions = resolveSearchConditions(searchConditions, tableObject, codeTypeId);
                searchConditionsData += "                    " + codeTypeId + ": undefined," + "\r\n";
            }
            // 该字段是列表字段
            if (tableObject.getIsList()) {
                searchColumnsList = resolveSearchColumnsList(searchColumnsList, tableObject, codeTypeId);
            }
            allColumnsDialog = resolveAllColumnsDialog(allColumnsDialog, tableObject, codeTypeId, readOnlyStr);
            if (isNotNull) {
                allColumnsRules += "                    " + codeTypeId
                        + ": [{required: true, message: '该项不能为空', trigger: 'change'}]," + "\r\n";
            }
            if (StringUtils.isNotEmpty(tableObject.getCodeTypeId())) {
                dicts.put(tableObject.getCodeTypeId(), tableObject.getCodeTypeId());
            }
        }
        searchConditions = replaceLast(searchConditions, "\r\n", "");
        searchColumnsList = replaceLast(searchColumnsList, "\r\n", "");
        allColumnsDialog = replaceLast(allColumnsDialog, "\r\n", "");
        searchConditionsData = replaceLast(searchConditionsData, ",\r\n", "");
        allColumnsData = replaceLast(allColumnsData, ",\r\n", "");
        allColumnsRules = replaceLast(allColumnsRules, ",\r\n", "");
        String dictsStr = "";
        for (String key : dicts.keySet()) {
            dictsStr += key + ",";
        }
        dictsStr = replaceLast(dictsStr, ",", "");
        if (StringUtils.isNotBlank(dictsStr)) {
            loadDicts = "            this.getDicts('" + dictsStr + "').then(({data}) => {this.dicts = data})";
        }
        writeFile(stringBufferAll, sysUpper, sys, aSys, aCodeType, codeType, searchConditions, searchConditionsData,
                searchColumnsList, primaryKey, allColumnsDialog, allColumnsData, allColumnsRules, loadDicts,
                tableNameCn);
    }

    private static void writeFile(StringBuffer stringBufferAll, String sysUpper, String sys, String aSys,
            String aCodeType, String codeType, String searchConditions, String searchConditionsData,
            String searchColumnsList, String primaryKey, String allColumnsDialog, String allColumnsData,
            String allColumnsRules, String loadDicts, String tableNameCn) {
        try {
            String filePath = CodeUtil.projectPath + "vue\\modules\\" + sys + "\\" + codeType + "\\";
            File file = new File(filePath);
            file.mkdirs();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(filePath + "index.vue")));

            // 根据表名替换:关键词
            String temp = stringBufferAll.toString();
            temp = StringUtils.replace(temp, "{_SYS}", sysUpper);
            temp = StringUtils.replace(temp, "{_Sys}", aSys);
            temp = StringUtils.replace(temp, "{_sys}", sys);
            // temp = StringUtils.replace(temp, "{_CODE_TYPE}", _CODE_TYPE);
            temp = StringUtils.replace(temp, "{_CodeType}", aCodeType);
            temp = StringUtils.replace(temp, "{_codeType}", codeType);

            temp = StringUtils.replace(temp, "{tableNameCn}", tableNameCn);

            temp = StringUtils.replace(temp, "{_searchConditions}", searchConditions);
            temp = StringUtils.replace(temp, "{_searchConditionsData}", searchConditionsData);
            temp = StringUtils.replace(temp, "{_searchColumnsList}", searchColumnsList);
            temp = StringUtils.replace(temp, "{_primaryKey}", primaryKey);
            temp = StringUtils.replace(temp, "{_allColumnsDialog}", allColumnsDialog);
            temp = StringUtils.replace(temp, "{_allColumnsData}", allColumnsData);
            temp = StringUtils.replace(temp, "{_allColumnsRules}", allColumnsRules);
            temp = StringUtils.replace(temp, "{_loadDicts}", loadDicts);

            bufferedWriter.write(temp);

            bufferedWriter.close();

            System.out.println("已经生成--Vue文件!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readStringBufferAll(String templetPath, StringBuffer stringBufferAll) {
        // 读取模板内容
        File actionFile = new File(CodeUtil.projectPath + "src\\main\\java\\com\\zjmzxfzhl\\codegenerator\\template\\"
                + templetPath + "\\Vue.txt");
        BufferedReader bufferedReader = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(actionFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBufferAll.append(line + "\r\n");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String resolveAllColumnsDialog(String allColumnsDialog, TableObject tableObject, String codeTypeId,
            String readOnlyStr) {
        if (StringUtils.isNotEmpty(tableObject.getCodeTypeId())) {
            allColumnsDialog += "                <el-form-item label=\"" + tableObject.getColumnNameCn() + "\" prop=\""
                    + codeTypeId + "\"><el-select v-model=\"temp." + codeTypeId + "\" placeholder=\""
                    + tableObject.getColumnNameCn() + "\"><el-option v-for=\"(item, index) in dicts."
                    + tableObject.getCodeTypeId()
                    + "\" :key=\"index\" :label=\"item.content\" :value=\"item.value\"></el-option></el-select></el-form-item>"
                    + "\r\n";
        } else {
            if (DATE_TYPE.equals(tableObject.getDataType())) {
                allColumnsDialog += "                <el-form-item label=\"" + tableObject.getColumnNameCn()
                        + "\" prop=\"" + codeTypeId + "\"><el-date-picker v-model=\"temp." + codeTypeId
                        + "\" type=\"date\"></el-date-picker></el-form-item>" + "\r\n";
            } else if (DATETIME_TYPE.equals(tableObject.getDataType())) {
                allColumnsDialog += "                <el-form-item label=\"" + tableObject.getColumnNameCn()
                        + "\" prop=\"" + codeTypeId + "\"><el-date-picker v-model=\"temp." + codeTypeId
                        + "\" type=\"datetime\" value-format=\"yyyy-MM-dd HH:mm:ss\"></el-date-picker></el-form-item>"
                        + "\r\n";
            } else {
                allColumnsDialog += "                <el-form-item label=\"" + tableObject.getColumnNameCn()
                        + "\" prop=\"" + codeTypeId + "\"><el-input v-model=\"temp." + codeTypeId + "\"" + readOnlyStr
                        + "/></el-form-item>" + "\r\n";
            }
        }
        return allColumnsDialog;
    }

    private static String resolveSearchColumnsList(String searchColumnsList, TableObject tableObject,
            String codeTypeId) {
        if (StringUtils.isNotEmpty(tableObject.getCodeTypeId())) {
            searchColumnsList += "            <el-table-column label=\"" + tableObject.getColumnNameCn() + "\" prop=\""
                    + codeTypeId
                    + "\" align=\"center\"><template slot-scope=\"scope\"><span v-html=\"formatDictText(dicts."
                    + tableObject.getCodeTypeId() + ",scope.row." + codeTypeId
                    + ")\"></span></template></el-table-column>" + "\r\n";
        } else {
            searchColumnsList += "            <el-table-column label=\"" + tableObject.getColumnNameCn() + "\" prop=\""
                    + codeTypeId + "\" align=\"center\"><template slot-scope=\"scope\"><span>{{ scope.row." + codeTypeId
                    + " }}</span></template></el-table-column>" + "\r\n";
        }
        return searchColumnsList;
    }

    private static String resolveSearchConditions(String searchConditions, TableObject tableObject, String codeTypeId) {
        if (StringUtils.isNotEmpty(tableObject.getCodeTypeId())) {
            searchConditions += "            <el-select v-model=\"listQuery." + codeTypeId + "\" placeholder=\""
                    + tableObject.getColumnNameCn()
                    + "\" class=\"filter-item\"><el-option v-for=\"(item, index) in dicts."
                    + tableObject.getCodeTypeId()
                    + "\" :key=\"index\" :label=\"item.content\" :value=\"item.value\"></el-option></el-select>"
                    + "\r\n";
        } else {
            if (DATE_TYPE.equals(tableObject.getDataType())) {
                searchConditions += "            <el-date-picker v-model=\"listQuery." + codeTypeId
                        + "\" placeholder=\"" + tableObject.getColumnNameCn()
                        + "\" type=\"date\" style=\"width: 200px;\" class=\"filter-item\"></el-date-picker>" + "\r\n";
            } else if (DATETIME_TYPE.equals(tableObject.getDataType())) {
                searchConditions += "            <el-date-picker v-model=\"listQuery." + codeTypeId
                        + "\" placeholder=\"" + tableObject.getColumnNameCn()
                        + "\" type=\"datetime\" value-format=\"yyyy-MM-dd HH:mm:ss\" style=\"width: 200px;\" class=\"filter-item\"></el-date-picker>"
                        + "\r\n";
            } else {
                searchConditions += "            <el-input v-model=\"listQuery." + codeTypeId + "\" placeholder=\""
                        + tableObject.getColumnNameCn()
                        + "\" style=\"width: 200px;\" class=\"filter-item\" @keyup.enter.native=\"btnQuery\"/>"
                        + "\r\n";
            }
        }
        return searchConditions;
    }

    public static String replaceLast(String text, String strToReplace, String replaceWithThis) {
        return text.replaceFirst("(?s)" + strToReplace + "(?!.*?" + strToReplace + ")", replaceWithThis);
    }

    public static void main(String[] args) {
        CreateVue.create(CodeUtil.createTableName, "first");
    }
}
