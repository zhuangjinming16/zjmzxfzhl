package com.zjmzxfzhl.common.xss;

import org.apache.commons.lang3.StringUtils;

import com.zjmzxfzhl.common.exception.SysException;

/**
 * SQL过滤
 * 
 * @author 庄金明
 */
public class SqlFilter {

	/**
	 * SQL注入过滤
	 * 
	 * @param str
	 *            待验证的字符串
	 */
	public static String sqlInject(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		// 去掉'|"|;|\字符
		str = StringUtils.replace(str, "'", "");
		str = StringUtils.replace(str, "\"", "");
		str = StringUtils.replace(str, ";", "");
		str = StringUtils.replace(str, "\\", "");

		// 转换成小写
		str = str.toLowerCase();

		// 非法字符
		String[] keywords = { "master", "truncate", "insert", "select", "delete", "update", "declare", "alter", "drop" };

		// 判断是否包含非法字符
		for (String keyword : keywords) {
			if (str.indexOf(keyword) != -1) {
				throw new SysException("SQL包含非法字符，请联系管理员");
			}
		}

		return str;
	}
}
