package com.zjmzxfzhl.common.permission.provider;

import org.springframework.stereotype.Component;

import com.zjmzxfzhl.common.permission.FilterGroup;
import com.zjmzxfzhl.common.permission.FilterOperate;
import com.zjmzxfzhl.common.permission.FilterRule;
import com.zjmzxfzhl.common.util.CommonUtil;
import com.zjmzxfzhl.modules.sys.common.SessionObject;
import com.zjmzxfzhl.modules.sys.entity.SysOrg;

import lombok.Getter;
import lombok.Setter;

/**
 * 机构权限
 * 
 * @author 庄金明
 *
 */
@Getter
@Setter
@Component
public class OrgDataPermissionProvider extends AbstractDataPermissionProvider {
	public static final String TYPE_1 = "1";
	public static final String TYPE_2 = "2";
	public static final String TYPE_3 = "3";
	/**
	 * 别名
	 */
	private String alias;

	/**
	 * 1-机构权限，查询自己及下辖机构的数据
	 * 
	 * 2-只查询当前机构
	 * 
	 * 3-只查询下辖机构不包括自己
	 * 
	 * others-如有需要可添加用户分管机构表等其他场景
	 * 
	 */
	private String type;

	@Override
	public FilterGroup filter(SessionObject sessionObject) {
		// 别名，默认 o
		String alias = CommonUtil.isEmptyDefault(this.alias, "o");
		// 机构数据权限类型，默认1
		String type = CommonUtil.isEmptyDefault(this.type, "1");
		SysOrg sysOrg = sessionObject.getSysOrg();
		FilterGroup result = null;
		// 机构权限，查询自己及下辖机构的数据
		if (TYPE_1.equals(type)) {
			FilterRule rule = new FilterRule(alias, "ORG_LEVEL_CODE", FilterOperate.LIKE.getValue(),
					CommonUtil.addLikeRight(sysOrg.getOrgLevelCode()));
			result = new FilterGroup(rule);
		}
		// 只查询当前机构
		else if (TYPE_2.equals(type)) {
			FilterRule rule = new FilterRule(alias, "ORG_ID", FilterOperate.EQ.getValue(), sysOrg.getOrgId());
			result = new FilterGroup(rule);
		}
		// 查询下辖机构不包括自己
		else if (TYPE_3.equals(type)) {
			FilterRule rule = new FilterRule(alias, "ORG_LEVEL_CODE", FilterOperate.LIKE.getValue(),
					CommonUtil.addLikeRight(sysOrg.getOrgLevelCode()));
			FilterRule rule2 = new FilterRule(alias, "ORG_ID", FilterOperate.NE.getValue(), sysOrg.getOrgId());
			result = new FilterGroup(rule);
			result.andRule(rule2);
		}
		return result;
	}
}
