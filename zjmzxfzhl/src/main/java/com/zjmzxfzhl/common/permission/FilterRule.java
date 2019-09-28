package com.zjmzxfzhl.common.permission;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 条件过滤规则
 * 
 * @author 庄金明
 *
 */
@Getter
@Setter
public class FilterRule implements Serializable {
	private static final long serialVersionUID = 1L;
	private String alias;// 查询sql别名
	private String field;// 查询sql属性名
	private String operate;// 查询表达式
	private String value;// 值

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
