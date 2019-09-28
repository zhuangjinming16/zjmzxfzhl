package com.zjmzxfzhl.modules.sys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.zjmzxfzhl.common.query.QueryWrapperGenerator;
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
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequiresPermissions("sys:menu:list")
	@GetMapping(value = "/list")
	public R list(SysMenu sysMenu, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		IPage<SysMenu> pageList = sysMenuService.list(new Page<SysMenu>(pageNo, pageSize), sysMenu);
		return R.ok(pageList);
	}

	/**
	 * 使用QueryWrapper查询列表
	 * 
	 * @param sysMenu
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequiresPermissions("sys:menu:listByQw")
	@GetMapping(value = "/listByQw")
	public R listByQw(SysMenu sysMenu, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		// 1.最简查询条件封装，输入参数不为空则默认全部eq匹配
		QueryWrapper<SysMenu> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysMenu);

		// 2.自定义查询规则，默认按照主键升序排序
		// Map<String, FilterOperate> searchObjRule = new HashMap<>();
		// searchObjRule.put("columnName", FilterOperate.LIKE);
		// QueryWrapper<SysMenu> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysMenu, searchObjRule);

		// 3.自定义查询规则，自定义排序规则
		// Map<String, FilterOperate> searchObjRule = new HashMap<>();
		// searchObjRule.put("columnName", FilterOperate.LIKE);
		// QueryWrapper<SysMenu> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysMenu, searchObjRule, "columnName1|asc,columnName2|desc");

		IPage<SysMenu> pageList = sysMenuService.page(new Page<SysMenu>(pageNo, pageSize), queryWrapper);
		return R.ok(pageList);
	}

	@RequiresPermissions("sys:menu:list")
	@GetMapping(value = "/queryById")
	public R queryById(@RequestParam(name = "id", required = true) String id) {
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
	public R delete(@RequestParam(name = "id", required = true) String id) {
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
