package com.zjmzxfzhl.modules.sys.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.Constants;
import com.zjmzxfzhl.common.aspect.annotation.DataPermission;
import com.zjmzxfzhl.common.base.BaseService;
import com.zjmzxfzhl.common.exception.SysException;
import com.zjmzxfzhl.common.permission.provider.NullDataPermissionProvider;
import com.zjmzxfzhl.common.permission.provider.OrgDataPermissionProvider;
import com.zjmzxfzhl.common.util.CommonUtil;
import com.zjmzxfzhl.common.util.PasswordUtil;
import com.zjmzxfzhl.common.util.RedisUtil;
import com.zjmzxfzhl.common.util.ShiroUtils;
import com.zjmzxfzhl.modules.sys.common.SessionObject;
import com.zjmzxfzhl.modules.sys.entity.SysMenu;
import com.zjmzxfzhl.modules.sys.entity.SysOrg;
import com.zjmzxfzhl.modules.sys.entity.SysRole;
import com.zjmzxfzhl.modules.sys.entity.SysRoleUser;
import com.zjmzxfzhl.modules.sys.entity.SysUser;
import com.zjmzxfzhl.modules.sys.entity.vo.Meta;
import com.zjmzxfzhl.modules.sys.entity.vo.Route;
import com.zjmzxfzhl.modules.sys.entity.vo.SysPasswordForm;
import com.zjmzxfzhl.modules.sys.entity.vo.SysRolePermissionVO;
import com.zjmzxfzhl.modules.sys.mapper.SysUserMapper;

/**
 * 用户Service
 * 
 * @author 庄金明
 */
@Service
public class SysUserService extends BaseService<SysUserMapper, SysUser> {
	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysRoleUserService sysRoleUserService;

	@Autowired
	private SysOrgService sysOrgService;

	@Autowired
	private RedisUtil redisUtil;

	@DataPermission(tableNames = { "T_SYS_USER", "T_SYS_ORG" }, aliasNames = { "a", "o" }, providers = {
			OrgDataPermissionProvider.class, NullDataPermissionProvider.class }, providerParams = { "{\"alias\":\"o\",\"type\":\"1\"}", "" })
	public IPage<SysUser> list(IPage<SysUser> page, SysUser sysUser) {
		return page.setRecords(baseMapper.list(page, sysUser));
	}

	/**
	 * 公共选人查询
	 * 
	 * @param page
	 * @param sysUser
	 * @return
	 */
	@DataPermission(providers = OrgDataPermissionProvider.class) // 默认别名为o,type=1
	public IPage<SysUser> listSelectUser(IPage<SysUser> page, SysUser sysUser) {
		return page.setRecords(baseMapper.list(page, sysUser));
	}

	public List<SysRole> getRoleByUserId(String userId) {
		return baseMapper.getRolesByUserId(userId);
	}

	/**
	 * 获取用户信息 ，
	 * 
	 * 若用户变更角色，则roleId不能为空,并且将变更后的roleId更新到T_SYS_USER表中
	 * 
	 * @param sysUser
	 * @return
	 */
	public SessionObject saveGetUserInfo(SysUser sysUser, String roleId) {
		SessionObject sessionObject = new SessionObject();
		sessionObject.setSysUser(sysUser);
		List<SysRole> sysRoles = getRoleByUserId(sysUser.getUserId());
		if (sysRoles == null || sysRoles.size() == 0) {
			if (!"admin".equals(sysUser.getUserId())) {
				throw new SysException("用户未配置角色权限，请联系管理员授权!");
			}
			SysRole sysRoleAdmin = sysRoleService.getById("admin");
			if (sysRoleAdmin == null) {
				throw new SysException("系统未配置admin角色，请联系管理员!");
			}
			sysRoles.add(sysRoleAdmin);
		}
		sessionObject.setSysRoles(sysRoles);
		if (CommonUtil.isEmptyStr(roleId)) {// 默认以T_SYS_USER表中的角色登录
			roleId = sysUser.getRoleId();
		}

		SysRole sysRoleUser = null;
		if (CommonUtil.isEmptyStr(roleId)) {
			roleId = sysRoles.get(0).getRoleId();
		} else {
			for (SysRole sysRole : sysRoles) {
				if (sysRole.getRoleId().equals(roleId)) {
					sysRoleUser = sysRole;
					break;
				}
			}
			if (sysRoleUser == null) {
				roleId = sysRoles.get(0).getRoleId();
				sysRoleUser = sysRoles.get(0);
			}
		}
		sessionObject.setSysRole(sysRoleUser);

		SysOrg sysOrg = this.sysOrgService.getById(sysUser.getOrgId());
		sessionObject.setSysOrg(sysOrg);

		sessionObject = loadFuncIdsAndPermissions(sysUser, roleId, sessionObject);
		List<Route> routes = this.loadRoutes(sysUser, roleId);
		sessionObject.setRoutes(routes);

		// 切换角色获取用户信息，需要更新用户表角色ID
		if (CommonUtil.isNotEmptyStr(roleId) && !roleId.equals(sysUser.getRoleId())) {
			SysUser updateRoleIdUser = new SysUser();
			updateRoleIdUser.setUserId(sysUser.getUserId());
			updateRoleIdUser.setRoleId(roleId);
			updateById(updateRoleIdUser);
		}
		return sessionObject;
	}

	/**
	 * 
	 * @param sysUser
	 * @param roleId
	 * @return
	 */
	public SessionObject loadFuncIdsAndPermissions(SysUser sysUser, String roleId, SessionObject data) {
		String userId = sysUser.getUserId();
		List<SysRolePermissionVO> authList = null;
		if ("admin".equals(userId)) {
			authList = baseMapper.getAdminPermissions();
		} else {
			authList = baseMapper.getRolePermissions(roleId);
		}
		List<String> funcs = new ArrayList<>();
		Map<String, String> authPathMap = new HashMap<String, String>();
		for (SysRolePermissionVO sysAuthOperVO : authList) {
			if ("1".equals(sysAuthOperVO.getPermissionType())) {
				if (CommonUtil.isNotEmptyStr(sysAuthOperVO.getMenuUrl())) {
					if (CommonUtil.isNotEmptyStr(sysAuthOperVO.getMenuPermissions())) {
						String[] menuPermissions = sysAuthOperVO.getMenuPermissions().split(",");// 以逗号分隔
						for (String permission : menuPermissions) {
							if (!authPathMap.containsKey(permission)) {
								authPathMap.put(permission, permission);
							}
						}
					}
				}
			} else if ("2".equals(sysAuthOperVO.getPermissionType())) {
				funcs.add(sysAuthOperVO.getMenuOrFuncId());
				if (CommonUtil.isNotEmptyStr(sysAuthOperVO.getFuncPermissions())) {
					String[] funcPermissions = sysAuthOperVO.getFuncPermissions().split(",");// 以逗号分隔
					for (String permission : funcPermissions) {
						if (!authPathMap.containsKey(permission)) {
							authPathMap.put(permission, permission);
						}
					}
				}
			}
		}
		List<String> permissions = new ArrayList<>();
		authPathMap.forEach((k, v) -> {
			permissions.add(v);
		});
		if (data == null) {
			data = new SessionObject();
		}
		data.setFuncIds(funcs);
		data.setPermissions(permissions);
		return data;
	}

	public List<Route> loadRoutes(SysUser sysUser, String roleId) {
		String userId = sysUser.getUserId();
		List<SysMenu> menuList = null;
		if ("admin".equals(userId)) {
			menuList = baseMapper.getRoleMenus("");
		} else {
			menuList = baseMapper.getRoleMenus(roleId);
		}
		Map<String, Route> routeMap = new LinkedHashMap<String, Route>();
		for (SysMenu menu : menuList) {
			if (!"1".equals(menu.getIsRoute())) {
				continue;
			}
			Route route = new Route();
			route.setRouteId(menu.getMenuId());
			route.setPath(menu.getMenuUrl());
			route.setComponent(menu.getComponent());
			route.setHidden("1".equals(menu.getHidden()));
			route.setRedirect(menu.getRedirect());
			if (CommonUtil.isNotEmptyStr(menu.getRouteName())) {
				route.setName(menu.getRouteName());
			} else {
				route.setName(urlToRouteName(menu.getMenuUrl()));
			}

			Meta meta = new Meta();
			meta.setTitle(menu.getMenuName());
			meta.setIcon(menu.getMenuIcon());
			meta.setIsCache("1".equals(menu.getIsCache()));
			meta.setAffix("1".equals(menu.getAffix()));
			route.setMeta(meta);

			routeMap.put(route.getRouteId(), route);
			if (CommonUtil.isNotEmptyStr(menu.getParentMenuId()) && routeMap.containsKey(menu.getParentMenuId())) {
				route.setRouteParentId(menu.getParentMenuId());
				Route parentRoute = routeMap.get(menu.getParentMenuId());
				parentRoute.addChildren(route);
			}
		}

		List<Route> result = new ArrayList<Route>();
		routeMap.forEach((k, v) -> {
			if (CommonUtil.isEmptyStr(v.getRouteParentId())) {
				result.add(v);
			}
		});

		return result;
	}

	/**
	 * 通过URL生成路由name（去掉URL前缀斜杠，替换内容中的斜杠‘/’为-）
	 * 
	 * 举例： URL = /sys/config RouteName = sysConfig
	 * 
	 * @return
	 */
	private String urlToRouteName(String url) {
		if (CommonUtil.isNotEmptyStr(url)) {
			if (url.startsWith("/")) {
				url = url.substring(1);
			}
			url = url.replace("/", "-");
			return url;
		} else {
			return null;
		}
	}

	/**
	 * 新增用户
	 * 
	 * @param sysUser
	 */
	public boolean saveSysUser(SysUser sysUser) {
		String salt = PasswordUtil.randomGen(8);
		String defaultPassword = (String) redisUtil.get(Constants.PREFIX_SYS_CONFIG + "defaultPassword", "1");
		String password = PasswordUtil.encrypt(defaultPassword, salt);// 默认密码
		sysUser.setSalt(salt);
		sysUser.setPassword(password);
		SysRoleUser sysRoleUser = new SysRoleUser(sysUser.getRoleId(), sysUser.getUserId());
		sysRoleUserService.saveOrUpdate(sysRoleUser);
		boolean result = this.save(sysUser);
		return result;
	}

	/**
	 * 修改用户
	 * 
	 * @param sysUser
	 */
	public boolean updateSysUser(SysUser sysUser) {
		SysUser sysUserDb = this.getById(sysUser.getUserId());
		if (!sysUserDb.getRoleId().equals(sysUser.getRoleId())) {
			SysRoleUser sysRoleUserDb = new SysRoleUser(sysUserDb.getRoleId(), sysUserDb.getUserId());
			this.sysRoleUserService.removeById(sysRoleUserDb.getRoleUserId());

			SysRoleUser sysRoleUser = new SysRoleUser(sysUser.getRoleId(), sysUser.getUserId());
			sysRoleUserService.saveOrUpdate(sysRoleUser);
		}
		sysUser.setPassword(null);// 密码设置为空，防止密码被恶意修改
		sysUser.setSalt(null);// 密码盐设置为空，防止密码被恶意修改
		boolean result = this.updateById(sysUser);
		return result;
	}

	/**
	 * 删除用户
	 * 
	 * @param ids
	 */
	public void delete(String ids) {
		if (ids == null || ids.trim().length() == 0) {
			throw new SysException("ids can't be empty");
		}
		String[] idsArr = ids.split(",");
		for (int i = 0; i < idsArr.length; i++) {
			if (idsArr[i].equals("admin")) {
				throw new SysException("不允许删除[admin]用户");
			}
		}
		if (idsArr.length > 1) {
			removeByIds(Arrays.asList(idsArr));
		} else {
			removeById(idsArr[0]);
		}
		QueryWrapper<SysRoleUser> queryWrapper = new QueryWrapper<>();
		sysRoleUserService.remove(queryWrapper.in("user_id", (Object[]) idsArr));
	}

	/**
	 * 修改密码
	 * 
	 * @param sysPasswordForm
	 */
	public boolean updatePassword(SysPasswordForm sysPasswordForm) {
		String userId = ShiroUtils.getUserId();
		SysUser sysUser = this.getById(userId);
		if (sysUser == null) {
			throw new SysException("用户不存在");
		}
		String oldPassword = PasswordUtil.encrypt(sysPasswordForm.getPassword(), sysUser.getSalt());
		String salt = PasswordUtil.randomGen(8);
		String newPassword = PasswordUtil.encrypt(sysPasswordForm.getNewPassword(), salt);
		sysUser.setPassword(newPassword);
		sysUser.setSalt(salt);
		return this.update(sysUser, new QueryWrapper<SysUser>().eq("user_id", userId).eq("password", oldPassword));
	}
}
