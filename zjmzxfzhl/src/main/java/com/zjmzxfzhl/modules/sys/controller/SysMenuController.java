package com.zjmzxfzhl.modules.sys.controller;

import java.util.List;

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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjmzxfzhl.common.R;
import com.zjmzxfzhl.common.aspect.annotation.SysLogAuto;
import com.zjmzxfzhl.common.base.BaseController;
import com.zjmzxfzhl.modules.sys.entity.SysMenu;
import com.zjmzxfzhl.modules.sys.entity.vo.ElTree;
import com.zjmzxfzhl.modules.sys.service.SysMenuService;

/**
 * 菜单Controller
 * 
 * @author 庄金明
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController {
	@Autowired
	private SysMenuService sysMenuService;

	/**
	 * 自定义查询列表
	 * 
	 * @param sysMenu
	 * @param current
	 * @param size
	 * @return
	 */
	@RequiresPermissions("sys:menu:list")
	@GetMapping(value = "/list")
	public R list(SysMenu sysMenu, @RequestParam Integer current, @RequestParam Integer size) {
		IPage<SysMenu> pageList = sysMenuService.list(new Page<SysMenu>(current, size), sysMenu);
		return R.ok(pageList);
	}

	@RequiresPermissions("sys:menu:list")
	@GetMapping(value = "/queryById")
	public R queryById(@RequestParam String id) {
		SysMenu sysMenu = sysMenuService.getById(id);
		return R.ok(sysMenu);
	}

	/**
	 * @功能：新增
	 * @param sysMenu
	 * @return
	 */
	@SysLogAuto(value = "新增功能菜单")
	@RequiresPermissions("sys:menu:save")
	@PostMapping(value = "/save")
	public R save(@Valid @RequestBody SysMenu sysMenu) {
		sysMenuService.saveSysMenu(sysMenu);
		return R.ok(sysMenu);
	}

	/**
	 * @功能：修改
	 * @param sysMenu
	 * @return
	 */
	@SysLogAuto(value = "修改功能菜单")
	@RequiresPermissions("sys:menu:update")
	@PutMapping(value = "/update")
	public R update(@Valid @RequestBody SysMenu sysMenu) {
		sysMenuService.updateSysMenu(sysMenu);
		return R.ok(sysMenu);
	}

	/**
	 * @功能：删除
	 * @param id
	 * @return
	 */
	@SysLogAuto(value = "删除功能菜单")
	@RequiresPermissions("sys:menu:delete")
	@DeleteMapping(value = "/delete")
	public R delete(@RequestParam String id) {
		sysMenuService.delete(id);
		return R.ok();
	}

	/**
	 * 菜单管理，菜单树数据
	 * 
	 * @return
	 */
	@RequiresPermissions("sys:menu:getTreeData")
	@GetMapping(value = "/getTreeData")
	public R getTreeData() {
		List<ElTree> treeData = sysMenuService.getTreeData();
		return R.ok(treeData);
	}
}
