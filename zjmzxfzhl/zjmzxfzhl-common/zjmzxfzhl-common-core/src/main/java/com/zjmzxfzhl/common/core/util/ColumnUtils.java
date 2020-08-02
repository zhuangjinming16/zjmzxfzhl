package com.zjmzxfzhl.common.core.util;

import com.zjmzxfzhl.common.core.constant.Constants;

/**
 * @author 庄金明
 * @date 2020年3月23日
 */
public class ColumnUtils {
    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。 如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
     * 例如：hello_world->helloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains(Constants.UNDERSCORE)) {
            // 不含下划线，仅将首字母小写
            return name.substring(0, 1).toLowerCase() + name.substring(1).toLowerCase();
        }
        // 用下划线将原始字符串分割
        String[] camels = name.split(Constants.UNDERSCORE);
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。 如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
     * 例如：hello_world,test_id->helloWorld,testId
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelNames(String names) {
        if (names == null || names.length() == 0) {
            return null;
        }
        StringBuffer sf = new StringBuffer();
        String[] fs = names.split(",");
        for (String field : fs) {
            field = camelName(field);
            sf.append(field + ",");
        }
        String result = sf.toString();
        return result.substring(0, result.length() - 1);
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。(首字母写) 如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
     * 例如：hello_world->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelNameCapFirst(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains(Constants.UNDERSCORE)) {
            // 不含下划线，仅将首字母小写
            return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        }
        // 用下划线将原始字符串分割
        String[] camels = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 其他的驼峰片段，首字母大写
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /**
     * 将驼峰命名转化成下划线
     *
     * @param para
     * @return
     */
    public static String camelToUnderline(String para) {
        StringBuilder sb = new StringBuilder(para);
        int temp = 0;
        for (int i = 0; i < para.length(); i++) {
            if (Character.isUpperCase(para.charAt(i))) {
                sb.insert(i + temp, "_");
                temp += 1;
            }
        }
        return sb.toString().toLowerCase();
    }
}
