package com.zjmzxfzhl.common.core.xss;

import com.zjmzxfzhl.common.core.exception.SysException;

import lombok.extern.slf4j.Slf4j;

/**
 * SQL过滤
 * 
 * @author 庄金明
 */
@Slf4j
public class SqlFilter {
    final static String SQL_FILTER_STRINGS = "'|and |exec |insert |select |delete |update |case |drop |count |chr |mid |master |truncate |char |declare |;|or |+|,";

    /**
     * SQL注入过滤
     * 
     * @param str
     *            待验证的字符串
     */
    public static void sqlInject(String str) {
        if (str == null || str.length() == 0) {
            return;
        }
        // 转换成小写
        String strLower = str.toLowerCase();
        String[] sqlFilterStringsArr = SQL_FILTER_STRINGS.split("\\|");
        // 判断是否包含非法字符
        for (String keyword : sqlFilterStringsArr) {
            if (strLower.indexOf(keyword) != -1) {
                log.error("存在SQL注入关键字--->{}", keyword);
                log.error("值存在SQL注入风险!--->{}", str);
                throw new SysException("SQL包含非法字符，请联系管理员");
            }
        }
    }
}
