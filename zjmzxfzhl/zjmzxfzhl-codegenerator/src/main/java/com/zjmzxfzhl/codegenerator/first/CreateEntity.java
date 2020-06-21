package com.zjmzxfzhl.codegenerator.first;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.zjmzxfzhl.codegenerator.util.CodeUtil;
import com.zjmzxfzhl.codegenerator.util.TableObject;
import com.zjmzxfzhl.common.core.util.CommonUtil;

/**
 * @author 庄金明
 * @date 2020年3月22日
 */
public class CreateEntity {

    public static void create(String createTableName) {
        StringBuffer stringBufferAll = new StringBuffer();
        HashMap<String, ArrayList<TableObject>> hashMap = CodeUtil.readExcelDesignInfo(createTableName);
        // T_SYS_CODE_TYPE变成SYS_CODE_TYPE
        String moduleId = StringUtils.substringAfter(createTableName, "T_");
        // 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
        String aSysCodeType = CodeUtil.getTuoFengName(moduleId, true);
        // 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
        // String _sysCodeType = CodeUtil.getTuoFengName(moduleId, false);
        // 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
        String sysUpper = StringUtils.substringBefore(moduleId, "_");
        // 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
        String sys = sysUpper.toLowerCase();
        // 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
        // String _Sys = CodeUtil.getTuoFengName(_sys, true);
        // String _CODE_TYPE = StringUtils.substringAfter(moduleId, "_").toUpperCase();
        // String _CodeType = CodeUtil.getTuoFengName(StringUtils.substringAfter(moduleId, "_"), true);//
        // 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
        // String _codeType = CodeUtil.getTuoFengName(StringUtils.substringAfter(moduleId, "_"), false);//
        // 用T_SYS_CODE_TYPE表的方式命名变量,更好理解
        for (Object tableName : hashMap.keySet()) {
            // 读到的表名对象list.
            ArrayList<TableObject> list = hashMap.get(tableName);
            // package + import 部分
            StringBuffer stringBufferHead = new StringBuffer();
            // public等声明部分
            StringBuffer stringBufferHead2 = new StringBuffer();
            // 中间字段循环代码
            StringBuffer stringBufferMid = new StringBuffer();
            // 主键代码+注释
            StringBuffer stringBufferFoot = new StringBuffer();
            // package部分
            stringBufferHead.append("package com.zjmzxfzhl.modules." + sys + ".entity;" + "\r\n\r\n");
            // 避免import重复,这3个import是一定有的
            TreeMap<String, String> importHashMap = new TreeMap<String, String>();
            importHashMap.put("import javax.validation.constraints.NotNull;\r\n",
                    "import javax.validation.constraints.NotNull;\r\n");
            importHashMap.put("import com.baomidou.mybatisplus.annotation.TableId;\r\n",
                    "import com.baomidou.mybatisplus.annotation.TableId;\r\n");
            importHashMap.put("import com.baomidou.mybatisplus.annotation.TableName;\r\n",
                    "import com.baomidou.mybatisplus.annotation.TableName;\r\n");
            importHashMap.put("import com.zjmzxfzhl.common.base.BaseEntity;\r\n",
                    "import com.zjmzxfzhl.common.base.BaseEntity;\\r\\n");
            importHashMap.put("import com.zjmzxfzhl.common.validator.constraints.LengthForUtf8;\r\n",
                    "import com.zjmzxfzhl.common.validator.constraints.LengthForUtf8;\r\n");
            importHashMap.put("import lombok.Data;\r\n", "import lombok.Data;\r\n");

            processingList(aSysCodeType, tableName, list, stringBufferHead2, stringBufferMid, stringBufferFoot,
                    importHashMap);

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
            String filePath = CodeUtil.projectPath + "src\\main\\java\\com\\zjmzxfzhl\\modules\\" + sys + "\\"
                    + "\\entity\\";
            File file = new File(filePath);
            file.mkdirs();
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new FileWriter(new File(filePath + aSysCodeType + ".java")));
            bufferedWriter.write(stringBufferAll.toString());

            bufferedWriter.close();

            System.out.println("已经生成--entity实体类!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processingList(String sysCodeTypeFirstUpper, Object tableName, ArrayList<TableObject> list,
            StringBuffer stringBufferHead2, StringBuffer stringBufferMid, StringBuffer stringBufferFoot,
            TreeMap<String, String> importHashMap) {
        for (int i = 0; i < list.size(); i++) {
            TableObject tableObject = list.get(i);
            if (i == 0) {
                String tableNameCn = tableObject.getTableNameCn();
                // 取出中文表名中的前缀,A01_系统参数表.
                tableNameCn = StringUtils.substringAfter(tableNameCn, "_");
                tableNameCn = StringUtils.substringBeforeLast(tableNameCn, "表");

                stringBufferHead2.append("/**" + "\r\n");
                stringBufferHead2.append(" * 【" + tableNameCn + "】实体类" + "\r\n");
                stringBufferHead2.append(" * " + "\r\n");
                stringBufferHead2.append(" * @author " + CodeUtil.author + "\r\n");
                stringBufferHead2.append(" */" + "\r\n");
                stringBufferHead2.append("@Data" + "\r\n");
                stringBufferHead2.append("@TableName(\"" + tableName + "\")" + "\r\n");
                stringBufferHead2
                        .append("public class " + sysCodeTypeFirstUpper + " extends BaseEntity {" + "\r\n\r\n");

                stringBufferHead2.append("    " + "private static final long serialVersionUID = 1L;" + "\r\n\r\n");
            }

            // 字段名称,定义变量,方便引用,用CODE_TYPE_ID进行举例
            String codeTypeIdFirstUpper = CodeUtil.getTuoFengName(tableObject.getColumnNameEn(), true);
            String codeTypeIdFirstLower = CodeUtil.getTuoFengName(tableObject.getColumnNameEn(), false);

            if ("createBy".equals(codeTypeIdFirstLower) || "createDate".equals(codeTypeIdFirstLower)
                    || "createTime".equals(codeTypeIdFirstLower) || "updateBy".equals(codeTypeIdFirstLower)
                    || "updateDate".equals(codeTypeIdFirstLower) || "updateTime".equals(codeTypeIdFirstLower)) {
                continue;
            }

            stringBufferMid.append("    /**\r\n");
            stringBufferMid.append("     * " + tableObject.getColumnNameCn() + "\r\n");
            stringBufferMid.append("     */\r\n");

            if (CommonUtil.isExist("UUID主键", tableObject.getIsNull(), ",")) {
                stringBufferMid.append("    " + "@TableId(type = IdType.ASSIGN_UUID)" + "\r\n");
                importHashMap.put("import com.baomidou.mybatisplus.annotation.IdType;\r\n",
                        "import com.baomidou.mybatisplus.annotation.IdType;\r\n");
            } else if (CommonUtil.isExist("数据库生成主键,前台输入主键", tableObject.getIsNull(), ",")) {
                stringBufferMid.append("    " + "@TableId" + "\r\n");
            }
            if (CommonUtil.isExist("前台输入主键,不空", tableObject.getIsNull(), ",")) {
                stringBufferMid.append("    " + "@NotNull" + "\r\n");
            }
            // 处理数据类型
            if ("字符型".equals(tableObject.getDataType())) {
                processingDataTypeString(stringBufferMid, stringBufferFoot, tableObject, codeTypeIdFirstUpper,
                        codeTypeIdFirstLower);
            } else if ("大文本".equals(tableObject.getDataType())) {
                processingDataTypeClob(stringBufferMid, stringBufferFoot, tableObject, codeTypeIdFirstUpper,
                        codeTypeIdFirstLower);
            } else if ("整数型".equals(tableObject.getDataType())) {
                processingDataTypeInteger(stringBufferMid, stringBufferFoot, importHashMap, tableObject,
                        codeTypeIdFirstUpper, codeTypeIdFirstLower);
            } else if ("浮点型".equals(tableObject.getDataType())) {
                processingDataTypeFloat(stringBufferMid, stringBufferFoot, importHashMap, tableObject,
                        codeTypeIdFirstUpper, codeTypeIdFirstLower);
            } else if ("日期型".equals(tableObject.getDataType())) {
                processingDataTypeDate(stringBufferMid, stringBufferFoot, importHashMap, tableObject,
                        codeTypeIdFirstUpper, codeTypeIdFirstLower);
            } else if ("时间型".equals(tableObject.getDataType())) {
                processingDataTypeDateTime(stringBufferMid, stringBufferFoot, importHashMap, tableObject,
                        codeTypeIdFirstUpper, codeTypeIdFirstLower);
            }
        }
    }

    private static void processingDataTypeDateTime(StringBuffer stringBufferMid, StringBuffer stringBufferFoot,
            TreeMap<String, String> importHashMap, TableObject tableObject, String codeTypeIdFirstUpper,
            String codeTypeIdFirstLower) {
        stringBufferMid
                .append("    " + "@JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\", timezone = \"GMT+8\")" + "\r\n");
        stringBufferMid.append("    " + "@DateTimeFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")" + "\r\n");

        stringBufferMid.append("    " + "private Date " + codeTypeIdFirstLower + ";\r\n\r\n");

        // 处理get,set方法
        stringBufferFoot.append("    " + "public Date get" + codeTypeIdFirstUpper + "() {" + "\r\n");
        stringBufferFoot.append("        " + "return this." + codeTypeIdFirstLower + ";\r\n");
        stringBufferFoot.append("    " + "}" + "\r\n\r\n");
        stringBufferFoot.append(
                "    " + "public void set" + codeTypeIdFirstUpper + "(Date " + codeTypeIdFirstLower + ") {" + "\r\n");
        stringBufferFoot.append("        " + "this." + codeTypeIdFirstLower + " = " + codeTypeIdFirstLower + ";\r\n");
        stringBufferFoot.append("    " + "}" + "\r\n\r\n");

        importHashMap.put("import java.util.Date;\r\n", "import java.util.Date;\r\n");
        importHashMap.put("import com.fasterxml.jackson.annotation.JsonFormat;\r\n",
                "import com.fasterxml.jackson.annotation.JsonFormat;\r\n");
        importHashMap.put("import org.springframework.format.annotation.DateTimeFormat;\r\n",
                "org.springframework.format.annotation.DateTimeFormat;\r\n");
    }

    private static void processingDataTypeDate(StringBuffer stringBufferMid, StringBuffer stringBufferFoot,
            TreeMap<String, String> importHashMap, TableObject tableObject, String codeTypeIdFirstUpper,
            String codeTypeIdFirstLower) {
        stringBufferMid.append("    " + "@JsonFormat(pattern = \"yyyy-MM-dd\", timezone = \"GMT+8\")" + "\r\n");
        stringBufferMid.append("    " + "@DateTimeFormat(pattern = \"yyyy-MM-dd\")" + "\r\n");

        stringBufferMid.append("    " + "private Date " + codeTypeIdFirstLower + ";\r\n\r\n");

        // 处理get,set方法
        stringBufferFoot.append("    " + "public Date get" + codeTypeIdFirstUpper + "() {" + "\r\n");
        stringBufferFoot.append("        " + "return this." + codeTypeIdFirstLower + ";\r\n");
        stringBufferFoot.append("    " + "}" + "\r\n\r\n");
        stringBufferFoot.append(
                "    " + "public void set" + codeTypeIdFirstUpper + "(Date " + codeTypeIdFirstLower + ") {" + "\r\n");
        stringBufferFoot.append("        " + "this." + codeTypeIdFirstLower + " = " + codeTypeIdFirstLower + ";\r\n");
        stringBufferFoot.append("    " + "}" + "\r\n\r\n");

        importHashMap.put("import java.util.Date;\r\n", "import java.util.Date;\r\n");
        importHashMap.put("import com.fasterxml.jackson.annotation.JsonFormat;\r\n",
                "import com.fasterxml.jackson.annotation.JsonFormat;\r\n");
        importHashMap.put("import org.springframework.format.annotation.DateTimeFormat;\r\n",
                "org.springframework.format.annotation.DateTimeFormat;\r\n");
    }

    private static void processingDataTypeFloat(StringBuffer stringBufferMid, StringBuffer stringBufferFoot,
            TreeMap<String, String> importHashMap, TableObject tableObject, String codeTypeIdFirstUpper,
            String codeTypeIdFirstLower) {
        stringBufferMid.append("    " + "@DecimalMax(\""
                + getMax(tableObject.getDataLength(), tableObject.getDataPrecision()) + "\")" + "\r\n");
        importHashMap.put("import javax.validation.constraints.DecimalMax;\r\n",
                "import javax.validation.constraints.DecimalMax;\r\n");
        importHashMap.put("import java.math.BigDecimal;\r\n", "import java.math.BigDecimal;\r\n");

        stringBufferMid.append("    " + "private BigDecimal " + codeTypeIdFirstLower + ";\r\n\r\n");

        // 处理get,set方法
        stringBufferFoot.append("    " + "public BigDecimal get" + codeTypeIdFirstUpper + "() {" + "\r\n");
        stringBufferFoot.append("        " + "return this." + codeTypeIdFirstLower + ";\r\n");
        stringBufferFoot.append("    " + "}" + "\r\n\r\n");
        stringBufferFoot.append("    " + "public void set" + codeTypeIdFirstUpper + "(BigDecimal "
                + codeTypeIdFirstLower + ") {" + "\r\n");
        stringBufferFoot.append("        " + "this." + codeTypeIdFirstLower + " = " + codeTypeIdFirstLower + ";\r\n");
        stringBufferFoot.append("    " + "}" + "\r\n\r\n");
    }

    private static int LENGTH = 11;

    private static void processingDataTypeInteger(StringBuffer stringBufferMid, StringBuffer stringBufferFoot,
            TreeMap<String, String> importHashMap, TableObject tableObject, String codeTypeIdFirstUpper,
            String codeTypeIdFirstLower) {
        if (CommonUtil.isEmptyStr(tableObject.getDataLength())
                || Integer.valueOf(tableObject.getDataLength()) < LENGTH) {
            stringBufferMid.append("    " + "@Max(" + getMax(tableObject.getDataLength(), null) + ")" + "\r\n");
            importHashMap.put("import javax.validation.constraints.Max;\r\n",
                    "import javax.validation.constraints.Max;\r\n");

            stringBufferMid.append("    " + "private Integer " + codeTypeIdFirstLower + ";\r\n\r\n");

            // 处理get,set方法
            stringBufferFoot.append("    " + "public Integer get" + codeTypeIdFirstUpper + "() {" + "\r\n");
            stringBufferFoot.append("        " + "return this." + codeTypeIdFirstLower + ";\r\n");
            stringBufferFoot.append("    " + "}" + "\r\n\r\n");
            stringBufferFoot.append("    " + "public void set" + codeTypeIdFirstUpper + "(Integer "
                    + codeTypeIdFirstLower + ") {" + "\r\n");
            stringBufferFoot
                    .append("        " + "this." + codeTypeIdFirstLower + " = " + codeTypeIdFirstLower + ";\r\n");
            stringBufferFoot.append("    " + "}" + "\r\n\r\n");
        } else {
            stringBufferMid.append("    " + "private Long " + codeTypeIdFirstLower + ";\r\n\r\n");

            // 处理get,set方法
            stringBufferFoot.append("    " + "public Long get" + codeTypeIdFirstUpper + "() {" + "\r\n");
            stringBufferFoot.append("        " + "return this." + codeTypeIdFirstLower + ";\r\n");
            stringBufferFoot.append("    " + "}" + "\r\n\r\n");
            stringBufferFoot.append("    " + "public void set" + codeTypeIdFirstUpper + "(Long " + codeTypeIdFirstLower
                    + ") {" + "\r\n");
            stringBufferFoot
                    .append("        " + "this." + codeTypeIdFirstLower + " = " + codeTypeIdFirstLower + ";\r\n");
            stringBufferFoot.append("    " + "}" + "\r\n\r\n");
        }
    }

    private static void processingDataTypeString(StringBuffer stringBufferMid, StringBuffer stringBufferFoot,
            TableObject tableObject, String codeTypeIdFirstUpper, String codeTypeIdFirstLower) {
        stringBufferMid.append("    " + "@LengthForUtf8(max = " + tableObject.getDataLength() + ")" + "\r\n");
        stringBufferMid.append("    " + "private String " + codeTypeIdFirstLower + ";\r\n\r\n");

        // 处理get,set方法
        stringBufferFoot.append("    " + "public String get" + codeTypeIdFirstUpper + "() {" + "\r\n");
        stringBufferFoot.append("        " + "return this." + codeTypeIdFirstLower + ";\r\n");
        stringBufferFoot.append("    " + "}" + "\r\n\r\n");
        stringBufferFoot.append(
                "    " + "public void set" + codeTypeIdFirstUpper + "(String " + codeTypeIdFirstLower + ") {" + "\r\n");
        stringBufferFoot.append("        " + "this." + codeTypeIdFirstLower + " = " + codeTypeIdFirstLower + ";\r\n");
        stringBufferFoot.append("    " + "}" + "\r\n\r\n");
    }

    private static void processingDataTypeClob(StringBuffer stringBufferMid, StringBuffer stringBufferFoot,
            TableObject tableObject, String codeTypeIdFirstUpper, String codeTypeIdFirstLower) {
        stringBufferMid.append("    " + "private String " + codeTypeIdFirstLower + ";\r\n\r\n");

        // 处理get,set方法
        stringBufferFoot.append("    " + "public String get" + codeTypeIdFirstUpper + "() {" + "\r\n");
        stringBufferFoot.append("        " + "return this." + codeTypeIdFirstLower + ";\r\n");
        stringBufferFoot.append("    " + "}" + "\r\n\r\n");
        stringBufferFoot.append(
                "    " + "public void set" + codeTypeIdFirstUpper + "(String " + codeTypeIdFirstLower + ") {" + "\r\n");
        stringBufferFoot.append("        " + "this." + codeTypeIdFirstLower + " = " + codeTypeIdFirstLower + ";\r\n");
        stringBufferFoot.append("    " + "}" + "\r\n\r\n");
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
