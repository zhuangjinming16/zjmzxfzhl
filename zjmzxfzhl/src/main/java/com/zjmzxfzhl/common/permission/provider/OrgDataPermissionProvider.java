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
	private String alias;// 别名

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
	private String type;// 机构数据权限类型

	@Override
	public FilterGroup filter(SessionObject sessionObject) {
		String alias = CommonUtil.isEmptyDefault(this.alias, "o");// 别名，默认 o
		String type = CommonUtil.isEmptyDefault(this.type, "1");// 机构数据权限类型，默认1
		SysOrg sysOrg = sessionObject.getSysOrg();
		FilterGroup result = null;
		if ("1".equals(type)) { // 机构权限，查询自己及下辖机构的数据
			FilterRule rule = new FilterRule(alias, "ORG_LEVEL_CODE", FilterOperate.LIKE.getValue(), CommonUtil.addLikeRight(sysOrg.getOrgLevelCode()));
			result = new FilterGroup(rule);
		} else if ("2".equals(type)) { // 只查询当前机构
			FilterRule rule = new FilterRule(alias, "ORG_ID", FilterOperate.EQ.getValue(), sysOrg.getOrgId());
			result = new FilterGroup(rule);
		} else if ("3".equals(type)) { // 查询下辖机构不包括自己
			FilterRule rule = new FilterRule(alias, "ORG_LEVEL_CODE", FilterOperate.LIKE.getValue(), CommonUtil.addLikeRight(sysOrg.getOrgLevelCode()));
			FilterRule rule2 = new FilterRule(alias, "ORG_ID", FilterOperate.NE.getValue(), sysOrg.getOrgId());
			result = new FilterGroup(rule);
			result.andRule(rule2);
		}
		return result;
	}
}
