package com.zjmzxfzhl.codegenerator.first;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

import com.zjmzxfzhl.codegenerator.util.CodeUtil;
import com.zjmzxfzhl.codegenerator.util.TableObject;

/**
 * @author 庄金明
 * @date 2020年3月25日
 */
public class CreateController {

    /**
     * 
     * @param createTableName
     *            需要创建的表名
     * @param templetPath
     *            模板路径
     */
    @SuppressWarnings("resource")
    public static void create(String createTableName, String templetPath) {
        // 所有Controller代码
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
        File actionFile = new File(CodeUtil.projectPath + "src\\main\\java\\com\\zjmzxfzhl\\codegenerator\\template\\"
                + templetPath + "\\Controller.txt");
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
        }

        // Controller的Controller类.
        try {
            String filePath = CodeUtil.projectPath + "src\\main\\java\\com\\zjmzxfzhl\\modules\\" + sys + "\\"
                    + "\\controller\\";
            File file = new File(filePath);
            file.mkdirs();
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new FileWriter(new File(filePath + aSys + aCodeType + "Controller.java")));

            // 根据表名替换:关键词
            String temp = stringBufferAll.toString();
            temp = StringUtils.replace(temp, "{_SYS}", sysUpper);
            temp = StringUtils.replace(temp, "{_Sys}", aSys);
            temp = StringUtils.replace(temp, "{_sys}", sys);
            // temp = StringUtils.replace(temp, "{_CODE_TYPE}", _CODE_TYPE);
            temp = StringUtils.replace(temp, "{_CodeType}", aCodeType);
            temp = StringUtils.replace(temp, "{_codeType}", codeType);

            temp = StringUtils.replace(temp, "{tableNameCn}", tableNameCn);

            // 替换开发人员姓名
            if (StringUtils.isEmpty(CodeUtil.author)) {
                System.out.println("代码生成失败，请修改开发人员姓名!");
                return;
            } else {
                // 替换--开发人员姓名
                temp = StringUtils.replace(temp, "{author}", CodeUtil.author);
            }

            bufferedWriter.write(temp);

            bufferedWriter.close();

            System.out.println("已经生成--Controller类!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CreateController.create(CodeUtil.createTableName, "first");
    }
}
