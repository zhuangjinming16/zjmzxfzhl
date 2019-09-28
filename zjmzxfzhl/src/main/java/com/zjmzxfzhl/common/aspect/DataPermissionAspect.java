package com.zjmzxfzhl.common.aspect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjmzxfzhl.common.aspect.annotation.DataPermission;
import com.zjmzxfzhl.common.exception.SysException;
import com.zjmzxfzhl.common.permission.FilterGroup;
import com.zjmzxfzhl.common.permission.FilterOperate;
import com.zjmzxfzhl.common.permission.FilterRule;
import com.zjmzxfzhl.common.permission.SourceStrategy;
import com.zjmzxfzhl.common.permission.provider.AbstractDataPermissionProvider;
import com.zjmzxfzhl.common.util.ColumnUtils;
import com.zjmzxfzhl.common.util.CommonUtil;
import com.zjmzxfzhl.common.util.ShiroUtils;
import com.zjmzxfzhl.common.util.SpringContextUtils;
import com.zjmzxfzhl.common.xss.SQLFilter;
import com.zjmzxfzhl.modules.sys.common.SessionObject;
import com.zjmzxfzhl.modules.sys.entity.SysDataPermission;
import com.zjmzxfzhl.modules.sys.entity.SysRole;
import com.zjmzxfzhl.modules.sys.entity.SysUser;
import com.zjmzxfzhl.modules.sys.service.SysDataPermissionService;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据权限处理
 * 
 * @author 庄金明
 *
 */
@Aspect
@Component
@Slf4j
public class DataPermissionAspect {
	@Autowired
	private SysDataPermissionService sysDataPermissionService;

	@Pointcut("@annotation(com.zjmzxfzhl.common.aspect.annotation.DataPermission)||@annotation(com.zjmzxfzhl.common.aspect.annotation.DataPermission.DataPermissions)")
	public void pointCut() {
	}

	@SuppressWarnings("unchecked")
	@Around("pointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Class<?>[] paramTypes = method.getParameterTypes();

		DataPermission[] dataPermissions = method.getAnnotationsByType(DataPermission.class);

		SessionObject sessionObject = ShiroUtils.getSessionObject();
		if (sessionObject == null) {
			throw new SysException("登录超时，请重新登录");
		}
		SysUser sysUser = sessionObject.getSysUser();
		if (sysUser == null) {
			throw new SysException("登录超时，请重新登录");
		}
		SysRole role = sessionObject.getSysRole();
		if (role == null) {
			throw new SysException("登录超时，请重新登录");
		}
		List<SysRole> roles = sessionObject.getSysRoles();
		if (roles == null || roles.size() == 0) {
			throw new SysException("登录超时，请重新登录");
		}
		if ("admin".equals(sysUser.getUserId())) { // admin用户用户所有数据权限
			return joinPoint.proceed();
		}

		// 方法内的所有参数
		Object[] params = joinPoint.getArgs();
		if (params == null || params.length == 0) {
			throw new SysException("方法参数数量不能为空");
		}

		for (DataPermission dataPermission : dataPermissions) {
			String[] tableNames = dataPermission.tableNames();
			String[] aliasNames = dataPermission.aliasNames();
			Class<AbstractDataPermissionProvider>[] providers = (Class<AbstractDataPermissionProvider>[]) dataPermission.providers();
			String[] providerParams = dataPermission.providerParams();
			int index = dataPermission.index();
			if (params.length == 1) {
				index = 0;
			}
			if (index > params.length - 1) {
				throw new SysException("[index]错误");
			}
			Object param = params[index];
			if (param == null) {
				throw new SysException("第[" + index + "]参数为空");
			}

			String fieldName = dataPermission.fieldName();

			List<FilterGroup> groups = new ArrayList<>();
			for (int i = 0; i < providers.length; i++) {
				Class<AbstractDataPermissionProvider> providerClass = providers[i];
				AbstractDataPermissionProvider provider = SpringContextUtils.getBean(providerClass);
				// 注入参数值，若有特殊参数需要特殊处理，可以在实现类中重写改方法
				provider.setProviderParams(providerParams[i]);
				FilterGroup group = provider.filter(sessionObject);
				if (group != null) {
					groups.add(group);
				}
			}

			if (tableNames.length > 0) {
				Map<String, String> aliasNamesMap = new HashMap<>();
				for (int i = 0; i < tableNames.length; i++) {
					aliasNamesMap.put(tableNames[i], aliasNames[i]);
				}
				String userId = sysUser.getUserId();
				String roleIds = "";

				for (int i = 0; i < roles.size(); i++) {
					SysRole sysRole = roles.get(i);
					if (i == roles.size() - 1) {
						roleIds += sysRole.getRoleId();
					} else {
						roleIds += sysRole.getRoleId() + ",";
					}
				}

				QueryWrapper<SysDataPermission> queryWrapper = new QueryWrapper<>();
				queryWrapper.in("TABLE_NAME", (Object[]) tableNames).orderByAsc("TABLE_NAME");
				List<SysDataPermission> sysDataPermissions = this.sysDataPermissionService.list(queryWrapper);
				Map<String, List<SysDataPermission>> tableNameMap = new LinkedHashMap<>();
				for (SysDataPermission sysDataPermission : sysDataPermissions) {
					if (("1".equals(sysDataPermission.getEntityType()) && CommonUtil.isExist(roleIds, sysDataPermission.getEntityId(), ",")) || "2".equals(sysDataPermission.getEntityType()) && userId.equals(sysDataPermission.getEntityId())) {
						String key = sysDataPermission.getTableName().toUpperCase() + "-" + sysDataPermission.getColumnName().toUpperCase();
						List<SysDataPermission> tableNameList = null;
						if (tableNameMap.containsKey(key)) {
							tableNameList = tableNameMap.get(key);
						} else {
							tableNameList = new ArrayList<>();
						}
						tableNameList.add(sysDataPermission);
						tableNameMap.put(key, tableNameList);
					}
				}

				for (String key : tableNameMap.keySet()) {
					List<SysDataPermission> tableNameList = tableNameMap.get(key);
					if (tableNameList != null && tableNameList.size() == 1) {
						SysDataPermission sysDataPermission = tableNameList.get(0);
						if (SourceStrategy.TEXT.getKey().equals(sysDataPermission.getSourceStrategy())) {
							FilterGroup group = new FilterGroup();
							FilterOperate filterOperate = makeFilterOperate(sysDataPermission.getOperate());
							String realValue = makeRealSqlValue(filterOperate, sysDataPermission.getClassName(), sysDataPermission.getColumnName(), sysDataPermission.getValue());
							FilterRule rule = new FilterRule(aliasNamesMap.get(sysDataPermission.getTableName()), sysDataPermission.getColumnName(), filterOperate.getValue(), realValue);
							group.andRule(rule);
							groups.add(group);
						} else if (SourceStrategy.SYSTEM.getKey().equals(sysDataPermission.getSourceStrategy())) {
							Class<AbstractDataPermissionProvider> providerClass = (Class<AbstractDataPermissionProvider>) Class.forName(sysDataPermission.getSourceProvider());
							AbstractDataPermissionProvider provider = SpringContextUtils.getBean(providerClass);
							// 注入别名和查询属性名
							provider.setProviderParams("{\"alias\":\"" + aliasNamesMap.get(sysDataPermission.getTableName()) + "\",\"columnName\":\"" + sysDataPermission.getColumnName() + "\"}");
							// 注入数据库设置的参数值，以数据库参数为准，在最后注入
							provider.setProviderParams(sysDataPermission.getSourceProviderParams());
							FilterGroup group = provider.filter(sessionObject);
							groups.add(group);
						}
					} else {
						FilterGroup group = new FilterGroup();
						for (SysDataPermission sysDataPermission : tableNameList) {
							if (SourceStrategy.TEXT.getKey().equals(sysDataPermission.getSourceStrategy())) {
								FilterOperate filterOperate = makeFilterOperate(sysDataPermission.getOperate());
								String realValue = makeRealSqlValue(filterOperate, sysDataPermission.getClassName(), sysDataPermission.getColumnName(), sysDataPermission.getValue());
								FilterRule rule = new FilterRule(aliasNamesMap.get(sysDataPermission.getTableName()), sysDataPermission.getColumnName(), filterOperate.getValue(), realValue);
								FilterGroup innerGroup = new FilterGroup(rule);
								group.orGroup(innerGroup);
							} else if (SourceStrategy.SYSTEM.getKey().equals(sysDataPermission.getSourceStrategy())) {
								Class<AbstractDataPermissionProvider> providerClass = (Class<AbstractDataPermissionProvider>) Class.forName(sysDataPermission.getSourceProvider());
								AbstractDataPermissionProvider provider = SpringContextUtils.getBean(providerClass);
								// 注入别名和查询属性名
								provider.setProviderParams("{\"alias\":\"" + aliasNamesMap.get(sysDataPermission.getTableName()) + "\",\"columnName\":\"" + sysDataPermission.getColumnName() + "\"}");
								// 注入数据库设置的参数值，以数据库参数为准，在最后注入
								provider.setProviderParams(sysDataPermission.getSourceProviderParams());
								FilterGroup innerGroup = provider.filter(sessionObject);
								group.orGroup(innerGroup);
							}
						}
						groups.add(group);
					}
				}
			}

			FilterGroup parentGroup = new FilterGroup();
			parentGroup.andGroups(groups);
			String authFilterSql = parentGroup.formatSQL();
			log.info("数据权限sql:" + authFilterSql);

			if (paramTypes[index].getName().equals("java.util.Map")) {
				Map<String, Object> map = (Map<String, Object>) param;
				map.put(fieldName, authFilterSql);
			} else {
				PropertyUtils.setSimpleProperty(param, fieldName, authFilterSql);
			}
		}

		return joinPoint.proceed();
	}

	private String makeRealSqlValue(FilterOperate filterOperate, String className, String columnName, String value) {
		value = SQLFilter.sqlInject(value); // 防止从数据库配置中注入非法sql
		Class<?> clazz = null;
		Class<?> fieldClazz = null;
		try {
			clazz = Class.forName(className);
			String fieldName = ColumnUtils.camelName(columnName);
			fieldClazz = clazz.getDeclaredField(fieldName).getType();
		} catch (Exception e1) {
			return value;
		}

		String result = value;
		String type = fieldClazz.getName();
		switch (type) {
		case "java.lang.String":
			if (filterOperate == FilterOperate.IN || filterOperate == FilterOperate.BETWEEN) {
				result = CommonUtil.castStringBySeparator(value, ",");
			} else {
				result = "'" + value + "'";
			}
			break;
		case "java.util.Date":
			// TODO 日期格式，不同数据库处理方式不同
			if (filterOperate == FilterOperate.IN || filterOperate == FilterOperate.BETWEEN) {
				result = CommonUtil.castStringBySeparator(value, ",");
			} else {
				result = "'" + value + "'";
			}
			break;
		default:
			result = value;
			break;
		}
		return result;
	}

	private FilterOperate makeFilterOperate(String operate) {
		FilterOperate ruleOperate = FilterOperate.resolve(operate);
		if (ruleOperate == null) {
			ruleOperate = FilterOperate.EQ;
		}
		return ruleOperate;
	}

}
