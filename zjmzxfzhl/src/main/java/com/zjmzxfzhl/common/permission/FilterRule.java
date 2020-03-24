package com.zjmzxfzhl.common.permission;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 条件过滤规则
 * 
 * @author 庄金明
 * @date 2020年3月23日
 */
@Getter
@Setter
public class FilterRule implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 查询sql别名
	 */
	private String alias;
	/**
	 * 查询sql属性名
	 */
	private String field;
	/**
	 * 查询表达式
	 */
	private String operate;
	/**
	 * 值
	 */
	private String value;

	public FilterRule() {
	}

	public FilterRule(String alias, String field, String operate, String value) {
		this.alias = alias;
		this.field = field;
		this.operate = operate;
		this.value = value;
	}

	public FilterRule(String alias, String field, String value) {
		this(alias, field, FilterOperate.EQ.getValue(), value);
	}

	public FilterRule(String field, String value) {
		this(null, field, value);
	}
}
