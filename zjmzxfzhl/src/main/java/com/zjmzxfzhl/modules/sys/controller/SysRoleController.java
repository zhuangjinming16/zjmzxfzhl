package com.zjmzxfzhl.modules.sys.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjmzxfzhl.common.R;
import com.zjmzxfzhl.common.aspect.annotation.SysLogAuto;
import com.zjmzxfzhl.common.base.BaseController;
import com.zjmzxfzhl.common.util.ShiroUtils;
import com.zjmzxfzhl.modules.sys.entity.SysRole;
import com.zjmzxfzhl.modules.sys.entity.SysRolePermission;
import com.zjmzxfzhl.modules.sys.entity.SysRoleUser;
import com.zjmzxfzhl.modules.sys.entity.SysUser;
import com.zjmzxfzhl.modules.sys.service.SysRoleService;

/**
 * 角色Controller
 * 
 * @author 庄金明
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {
	@Autowired
	private SysRoleService sysRoleService;

	/**
	 * 自定义查询列表
	 * 
	 * @param sysRole
	 * @param current
	 * @param size
	 * @return
	 */
	@RequiresPermissions("sys:role:list")
	@GetMapping(value = "/list")
	public R list(SysRole sysRole, @RequestParam Integer current, @RequestParam Integer size) {
		IPage<SysRole> pageList = sysRoleService.list(new Page<SysRole>(current, size), sysRole);
		return R.ok(pageList);
	}

	@RequiresPermissions("sys:role:list")
	@GetMapping(value = "/queryById")
	public R queryById(@RequestParam String id) {
		SysRole sysRole = sysRoleService.getById(id);
		return R.ok(sysRole);
	}

	/**
	 * @功能：新增
	 * @param sysRole
	 * @return
	 */
	@SysLogAuto(value = "新增角色")
	@RequiresPermissions("sys:role:save")
	@PostMapping(value = "/save")
	public R save(@Valid @RequestBody SysRole sysRole) {
		sysRoleService.save(sysRole);
		return R.ok();
	}

	/**
	 * @功能：修改
	 * @param sysRole
	 * @return
	 */
	@SysLogAuto(value = "修改角色")
	@RequiresPermissions("sys:role:update")
	@PutMapping(value = "/update")
	public R update(@Valid @RequestBody SysRole sysRole) {
		sysRoleService.updateById(sysRole);
		return R.ok();
	}

	/**
	 * @功能：批量删除
	 * @param ids
	 * @return
	 */
	@SysLogAuto(value = "删除角色")
	@RequiresPermissions("sys:role:delete")
	@DeleteMapping(value = "/delete")
	public R delete(@RequestParam String ids) {
		if (ids == null || ids.trim().length() == 0) {
			return R.error("ids can't be empty");
		}
		this.sysRoleService.delete(ids);
		return R.ok();
	}

	/**
	 * 查询角色权限
	 * 
	 * @param roleId
	 * @return
	 */
	@RequiresPermissions("sys:role:getRolePermissions")
	@GetMapping(value = "/getRolePermissions")
	public R getRolePermissions(String roleId) {
		SysUser sysUser = ShiroUtils.getSysUser();
		Map<String, Object> data = this.sysRoleService.getRolePermissions(sysUser, roleId);
		return R.ok(data);
	}

	/**
	 * 保存角色权限
	 * 
	 * @param roleId
	 * @param menuOrFuncIds
	 *            菜单或者按钮ID
	 * @param permissionTypes
	 *            权限类型 1-菜单 2-按钮
	 * @return
	 */
	@SysLogAuto(value = "保存角色权限")
	@RequiresPermissions("sys:role:saveRolePermissions")
	@PostMapping(value = "/saveRolePermissions")
	public R saveRolePermissions(@RequestBody SysRolePermission sysRolePermission) {
		this.sysRoleService.saveRolePermissions(sysRolePermission.getRoleId(), sysRolePermission.getMenuOrFuncId(),
				sysRolePermission.getPermissionType());
		return R.ok();
	}

	/**
	 * 获取角色用户
	 * 
	 * @param roleId
	 * @return
	 */
	@RequiresPermissions("sys:role:getRoleUser")
	@GetMapping(value = "/getRoleUser")
	public R getRoleUser(SysRoleUser sysRoleUser, @RequestParam Integer current, @RequestParam Integer size) {
		IPage<SysUser> pageList = this.sysRoleService.getRoleUser(new Page<SysUser>(current, size), sysRoleUser);
		return R.ok(pageList);
	}

	/**
	 * 保存角色用户
	 * 
	 * @param sysRoleUser
	 * @return
	 */
	@SysLogAuto(value = "保存角色用户")
	@RequiresPermissions("sys:role:saveRoleUsers")
	@PostMapping(value = "/saveRoleUsers")
	public R saveRoleUsers(@RequestBody SysRoleUser sysRoleUser) {
		this.sysRoleService.saveRoleUsers(sysRoleUser.getRoleId(), sysRoleUser.getUserId());
		return R.ok();
	}

	/**
	 * 删除角色用户
	 * 
	 * @param sysRoleUser
	 * @return
	 */
	@SysLogAuto(value = "删除角色用户")
	@RequiresPermissions("sys:role:deleteRoleUsers")
	@DeleteMapping(value = "/deleteRoleUsers")
	public R deleteRoleUsers(String roleId, String userIds) {
		this.sysRoleService.deleteRoleUsers(roleId, userIds);
		return R.ok();
	}

	/**
	 * 查询所有角色
	 * 
	 * @return
	 */
	@RequiresPermissions("sys:role:listAll")
	@GetMapping(value = "/listAll")
	public R listAll() {
		QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
		List<SysRole> roles = sysRoleService.list(queryWrapper.orderByAsc("sort_no"));
		return R.ok(roles);
	}
}
