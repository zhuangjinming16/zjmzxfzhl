package com.zjmzxfzhl.common.permission;

import java.util.HashMap;
import java.util.Map;

import org.springframework.lang.Nullable;

import com.zjmzxfzhl.common.util.CommonUtil;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 条件过滤表达式，支持 and和or
 * 
 * @author 庄金明
 *
 */
@Getter
@RequiredArgsConstructor
public enum FilterOperate {
	// 表达式
	AND("and", "and", "并且"), OR("or", "or", "或者"), EQ("=", "=", "等于"), NE("!=", "!=", "不等于"), LT("<", "<", "小于"), LE("<=", "<=", "小于等于"), GT(">", ">",
			"大于"), GE(">=", ">=", "大于等于"), IN("in", "in", "包含"), BETWEEN("between", "between",
					"在X区间"), LIKE("like", "like", "全模糊"), LIKE_LEFT("like_left", "like", "左模糊"), LIKE_RIGHT("like_right", "like", "右模糊");
	private final String value;

	private final String dbValue;

	private final String message;

	private static final Map<String, FilterOperate> MAPPINGS = new HashMap<>(16);

	static {
		for (FilterOperate operate : values()) {
			MAPPINGS.put(operate.getValue(), operate);
		}
	}

	/**
	 * 根据value获取FilterOperate
	 * 
	 * @param value
	 *            value
	 * @return FilterOperate
	 */
	@Nullable
	public static FilterOperate resolve(@Nullable String value) {
		return (value != null ? MAPPINGS.get(value.toLowerCase()) : null);
	}

	/**
	 * 根据value获取FilterOperate
	 * 
	 * @param value
	 *            value
	 * @param defaultOperate
	 *            defaultOperate
	 * @return FilterOperate
	 */
	@Nullable
	public static FilterOperate resolveOrDefault(@Nullable String value, FilterOperate defaultOperate) {
		if (value == null) {
			return defaultOperate;
		}
		return MAPPINGS.getOrDefault(value.toLowerCase(), defaultOperate);
	}

	/**
	 * 判断是否包含指定的value值
	 * 
	 * @param value
	 *            value
	 * @return true or false
	 */
	public static Boolean containsValue(String value) {
		if (CommonUtil.isEmptyStr(value)) {
			return false;
		}
		return MAPPINGS.keySet().contains(value);
	}
}
