package com.zjmzxfzhl.common.permission.provider;

import org.springframework.stereotype.Component;

import com.zjmzxfzhl.common.permission.FilterGroup;
import com.zjmzxfzhl.modules.sys.common.SessionObject;

import lombok.Getter;
import lombok.Setter;

/**
 * 测试数据库后台返回provider
 * 
 * @author
 *
 */
@Getter
@Setter
@Component
public class TestSystemDataPermissionProvider extends AbstractDataPermissionProvider {

	// 数据库配置参数，特别注意，如果数据库传入的参数有作为查询条件，应使用SQLFilter.sqlInject(param)防止sql注入
	private String alias;// 别名
	private String columnName; // 属性名
	private String dbParam1;
	private int dbParam2;

	@Override
	public FilterGroup filter(SessionObject sessionObject) {
		// 测试请打开注释
		// System.out.println(this.alias);
		// System.out.println(this.columnName);
		// System.out.println(this.dbParam1);
		// System.out.println(this.dbParam2);
		// String alias = CommonUtil.isEmptyDefault(this.alias, "o");// 别名
		// String cloumnName = CommonUtil.isEmptyDefault(this.cloumnName, "columnName");// 属性名
		return null;
	}
}
