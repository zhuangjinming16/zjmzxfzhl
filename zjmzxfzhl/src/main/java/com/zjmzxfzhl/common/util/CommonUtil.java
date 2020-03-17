package com.zjmzxfzhl.common.util;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import com.zjmzxfzhl.common.exception.BaseException;

public class CommonUtil {

	public static String trimToEmptyStr(String str) {
		return StringUtils.trimToEmpty(str);
	}

	public static boolean isEmptyObject(Object object) {
		return ObjectUtils.isEmpty(object);
	}

	public static void isEmptyObject(Object object, String message) {
		if (isEmptyObject(object)) {
			throw new BaseException(message);
		}
	}

	public static boolean isNotEmptyObject(Object object) {
		return !ObjectUtils.isEmpty(object);
	}

	public static boolean isEmptyStr(String str) {
		return StringUtils.isEmpty(str);
	}

	public static void isEmptyStr(String str, String message) {
		if (isEmptyStr(str)) {
			throw new BaseException(message);
		}
	}

	public static boolean isNotEmptyStr(String str) {
		return !StringUtils.isEmpty(str);
	}

	public static boolean isEmptyAfterTrim(String str) {
		return StringUtils.isEmpty(StringUtils.trimToEmpty(str));
	}

	public static void isEmptyAfterTrim(String str, String message) {
		if (isEmptyAfterTrim(str)) {
			throw new BaseException(message);
		}
	}

	public static boolean isNotEmptyAfterTrim(String str) {
		return !isEmptyAfterTrim(str);
	}

	public static <T> T isEmptyDefault(T source, T df) {
		return isNotEmptyObject(source) ? source : df;
	}

	/**
	 * 验证[某字符串]是否存在于逗号分隔的字符串中
	 * 
	 * @param str
	 *            【abc,123,www】
	 * @param substr
	 *            【123】
	 * @param sepatator
	 *            【,】
	 * @return
	 */
	public static boolean isExist(String str, String substr, String sepatator) {
		if (str == null || str.trim().equals(""))
			return false;
		if (substr == null || substr.trim().equals(""))
			return false;
		String[] strArr = str.split(sepatator);
		int size = strArr.length;
		for (int i = 0; i < size; i++) {
			if (strArr[i].equals(substr))
				return true;
		}
		return false;
	}

	/**
	 * 在数字面前补0; addPrefix(1, 4); 返回的是 0001
	 * 
	 * @param value
	 *            数字.
	 * @param num
	 *            返回几位数
	 * @return
	 */
	public static String addPrefix(Integer value, int num) {
		String ret = String.valueOf(value);
		for (int i = 0, len = num - ret.length(); i < len; i++) {
			ret = "0" + ret;
		}
		return ret;
	}

	/**
	 * 对String型的id号串,拆分成数组 【abc,123,www】将拆分成str数组
	 * 
	 * @param srcIds
	 * @param splitChar
	 * @return
	 */
	public static String[] getIds(String srcIds, String splitChar) {
		String[] ids = null;
		boolean spBegin = false; // 第一个字符是splitchar

		if (srcIds == null || srcIds.equals("")) {
			return null;
		}
		if (srcIds.indexOf(splitChar) == 0) {
			spBegin = true;
		}

		String[] strSplitIds = srcIds.trim().split(splitChar);
		int len = strSplitIds.length;
		if (spBegin) {
			if (len > 1) {
				ids = new String[len - 1];
				for (int i = 1; i < len; i++) {
					ids[i - 1] = strSplitIds[i];
				}
			} else {
				return null;
			}
		} else {
			ids = new String[len];
			for (int i = 0; i < len; i++) {
				ids[i] = strSplitIds[i];
			}
		}

		return ids;
	}

	/**
	 * 中文字符串转为utf-8格式
	 * 
	 * @param s
	 * @return
	 */
	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 人民币金额转财务大写
	 * 
	 * @param money
	 * @return
	 */
	public static String toRMB(double money) {
		char[] s1 = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' };
		char[] s4 = { '分', '角', '元', '拾', '佰', '仟', '万', '拾', '佰', '仟', '亿', '拾', '佰', '仟', '万' };
		// 这两句是为后面转换做字典准备
		String str = String.valueOf(Math.round(money * 100 + 0.00001));
		// 这是把参数money小数点后移2位,然后取整,即按照"分"为单位取整,再转为字符串型
		String result = "";

		for (int i = 0; i < str.length(); i++) {
			int n = str.charAt(str.length() - 1 - i) - '0';
			result = s1[n] + "" + s4[i] + result;
		}
		// 这一段是按照转换出来的字符串按位取数字,再按照上面的字典将其逐位翻译成汉字
		// 如"2011"就翻译成"贰仟零佰壹拾壹元","98700432.10"就是"玖仟捌佰柒拾零万零仟肆佰叁拾贰元壹角零分"——注:这里的人民币大写汉字,貌似有错误,请查阅相关资料更正
		// 以上的翻译可以看出,和我们平时的认读有差别,于是以下就是在将那些应该纠正的地方手动剔除以下.上述两个例子最后得到的结果应该是"贰仟零壹拾壹元整","玖仟捌佰柒拾万零肆佰叁拾贰元壹角整"
		result = result.replaceAll("零仟", "零");
		result = result.replaceAll("零佰", "零");
		result = result.replaceAll("零拾", "零");
		result = result.replaceAll("零亿", "亿");
		result = result.replaceAll("零万", "万");
		result = result.replaceAll("零元", "元");
		result = result.replaceAll("零角", "零");
		result = result.replaceAll("零分", "零");

		result = result.replaceAll("零零", "零");
		result = result.replaceAll("零亿", "亿");
		result = result.replaceAll("零零", "零");
		result = result.replaceAll("零万", "万");
		result = result.replaceAll("零零", "零");
		result = result.replaceAll("零元", "元");
		result = result.replaceAll("亿万", "亿");

		result = result.replaceAll("零$", "");
		result = result.replaceAll("元$", "元整");
		result = result.replaceAll("角$", "角整");

		return result;
	}

	/**
	 * 根据指定的分隔符转换字符串
	 * 
	 * @param source
	 *            原字符串
	 * @param separator
	 *            分隔符
	 * @return 例子：'1,2,3,4' 转换为'1','2','3','4'
	 */
	public static String castStringBySeparator(String source, String separator) {
		source = source.replace(separator, "'" + separator + "'");
		source = "'" + source + "'";
		return source;
	}

	/**
	 * 手机号验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][0-9]{10}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	/**
	 * 电话号码验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isPhone(String str) {
		Pattern p1 = null, p2 = null;
		Matcher m = null;
		boolean b = false;
		p1 = Pattern.compile("^[0][1-9]{2,3}-?[0-9]{5,10}$"); // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
		if (str.startsWith("0")) {
			m = p1.matcher(str);
			b = m.matches();
		} else {
			m = p2.matcher(str);
			b = m.matches();
		}
		return b;
	}

	/**
	 * 获取32位唯一的uuid,不带横杠
	 * 
	 * @return
	 */
	public static String getUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 获取32位唯一的uuid,带横杠
	 * 
	 * @return
	 */
	public static String getUuid2() {
		return UUID.randomUUID().toString();
	}

	public static String addLike(String str) {
		return "'%" + str + "%'";
	}

	public static String addLikeLeft(String str) {
		return "'%" + str + "'";
	}

	public static String addLikeRight(String str) {
		return "'" + str + "%'";
	}

	/**
	 * 根据指定编码切割字符串
	 * 
	 * @param str
	 * @param num
	 * @param charsetName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String cutString(String str, int num, String charsetName) {
		try {
			int length = str.getBytes(charsetName).length;
			if (length > num) {
				str = str.substring(0, str.length() - 1);
				str = cutString(str, num, charsetName);
			}
			return str;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
