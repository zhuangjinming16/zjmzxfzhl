package com.zjmzxfzhl.modules.demo.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.aspect.annotation.DataPermission;
import com.zjmzxfzhl.common.base.BaseService;
import com.zjmzxfzhl.common.permission.provider.OrgDataPermissionProvider;
import com.zjmzxfzhl.modules.demo.entity.DemoZjmzxfzhl;
import com.zjmzxfzhl.modules.demo.mapper.DemoZjmzxfzhlMapper;

/**
 * 开发示例Service
 * 
 * @author 庄金明
 */
@Service
public class DemoZjmzxfzhlService extends BaseService<DemoZjmzxfzhlMapper, DemoZjmzxfzhl> {
	public IPage<DemoZjmzxfzhl> list(IPage<DemoZjmzxfzhl> page, DemoZjmzxfzhl demoZjmzxfzhl) {
		return page.setRecords(baseMapper.list(page, demoZjmzxfzhl));
	}

	// @DataPermission(tableNames = "T_DEMO_ZJMZXFZHL", aliasNames = "a", fieldName = "authFilterSql")
	/**
	 * @数据权限示例一
	 * @通过配置表 T_SYS_DATA_PERMISSION 实现简单数据权限
	 * @ 1.配置 数据源策略：1-用户录入
	 * @ 2.配置 角色数据权限 或者 人员数据权限
	 * @ 3.若未指定fieldName则默认注入authFilterSql属性中
	 * @param page
	 * @param demoZjmzxfzhl
	 * @return
	 */
	@DataPermission(tableNames = "T_DEMO_ZJMZXFZHL", aliasNames = "a")
	public IPage<DemoZjmzxfzhl> listDataPermission1(IPage<DemoZjmzxfzhl> page, DemoZjmzxfzhl demoZjmzxfzhl) {
		return page.setRecords(baseMapper.list(page, demoZjmzxfzhl));
	}

	// 以下写法是为规则提供器传递参数，参数格式为Json格式
	// @DataPermission(providers = OrgDataPermissionProvider.class, providerParams = "{\"alias\":\"o\",\"type\":\"1\"}")
	/**
	 * @数据权限示例二
	 * @通过实现AbstractDataPermissionProvider配置数据权限
	 * @param page
	 * @param demoZjmzxfzhl
	 * @return
	 */
	@DataPermission(providers = OrgDataPermissionProvider.class)
	public IPage<DemoZjmzxfzhl> listDataPermission2(IPage<DemoZjmzxfzhl> page, DemoZjmzxfzhl demoZjmzxfzhl) {
		return page.setRecords(baseMapper.list(page, demoZjmzxfzhl));
	}

	/**
	 * @数据权限示例三，示例一与示例二结合体，二则是且的关系
	 * @通过配置表T_SYS_DATA_PERMISSION + 实现AbstractDataPermissionProvider配置数据权限
	 * @param page
	 * @param demoZjmzxfzhl
	 * @return
	 */
	@DataPermission(tableNames = "T_DEMO_ZJMZXFZHL", aliasNames = "a", providers = OrgDataPermissionProvider.class)
	public IPage<DemoZjmzxfzhl> listDataPermission3(IPage<DemoZjmzxfzhl> page, DemoZjmzxfzhl demoZjmzxfzhl) {
		return page.setRecords(baseMapper.list(page, demoZjmzxfzhl));
	}

	/**
	 * @数据权限示例四
	 * @DataPermission是可以重复注解
	 * @配置多个DataPermission注入不同参数
	 * @1.注入第2个参数DemoZjmzxfzhl1中
	 * @2.注入第3个参数DemoZjmzxfzhl2中
	 * @param page
	 * @param demoZjmzxfzhl1
	 * @param demoZjmzxfzhl2
	 * @return
	 */
	@DataPermission(tableNames = "T_DEMO_ZJMZXFZHL", aliasNames = "a", index = 1)
	@DataPermission(providers = OrgDataPermissionProvider.class, index = 2, fieldName = "otherAuthFilterSql")
	public IPage<DemoZjmzxfzhl> listDataPermission4(IPage<DemoZjmzxfzhl> page, DemoZjmzxfzhl demoZjmzxfzhl1, DemoZjmzxfzhl demoZjmzxfzhl2) {
		IPage<DemoZjmzxfzhl> result = null;
		IPage<DemoZjmzxfzhl> result1 = page.setRecords(baseMapper.list1(page, demoZjmzxfzhl1));
		IPage<DemoZjmzxfzhl> result2 = page.setRecords(baseMapper.list2(page, demoZjmzxfzhl2));
		// 其他业务逻辑处理begin
		// 返回查询结果数据多的数据
		if (result1.getRecords().size() > result2.getRecords().size()) {
			result = result1;
		} else {
			result = result2;
		}
		// 其他业务逻辑处理end
		// return 实际业务处理结果
		return result;
	}

	/**
	 * @数据权限示例五
	 * @DataPermission是可以重复注解
	 * @配置多个DataPermission注入相同参数不同属性
	 * @param page
	 * @param demoZjmzxfzhl
	 * @return
	 */
	@DataPermission(tableNames = "T_DEMO_ZJMZXFZHL", aliasNames = "a", index = 1, fieldName = "authFilterSql")
	@DataPermission(providers = OrgDataPermissionProvider.class, index = 1, fieldName = "otherAuthFilterSql")
	public IPage<DemoZjmzxfzhl> listDataPermission5(IPage<DemoZjmzxfzhl> page, DemoZjmzxfzhl demoZjmzxfzhl) {
		IPage<DemoZjmzxfzhl> result = page.setRecords(baseMapper.list3(page, demoZjmzxfzhl));
		return result;
	}
}
