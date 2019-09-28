package com.zjmzxfzhl.modules.sys.controller;

import java.util.Arrays;

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
import com.zjmzxfzhl.modules.sys.entity.SysDataPermission;
import com.zjmzxfzhl.modules.sys.service.SysDataPermissionService;

/**
 * 数据权限Controller
 * 
 * @author 庄金明
 */
@RestController
@RequestMapping("/sys/dataPermission")
public class SysDataPermissionController extends BaseController {
	@Autowired
	private SysDataPermissionService sysDataPermissionService;

	/**
	 * 自定义查询列表
	 * 
	 * @param sysDataPermission
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequiresPermissions("sys:dataPermission:list")
	@GetMapping(value = "/list")
	public R list(SysDataPermission sysDataPermission, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		IPage<SysDataPermission> pageList = sysDataPermissionService.list(new Page<SysDataPermission>(pageNo, pageSize), sysDataPermission);
		return R.ok(pageList);
	}

	/**
	 * 使用QueryWrapper查询列表
	 * 
	 * @param sysDataPermission
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequiresPermissions("sys:dataPermission:listByQw")
	@GetMapping(value = "/listByQw")
	public R listByQw(SysDataPermission sysDataPermission, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		// 1.最简查询条件封装，输入参数不为空则默认全部eq匹配
		QueryWrapper<SysDataPermission> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysDataPermission);

		// 2.自定义查询规则，默认按照主键升序排序
		// Map<String, FilterOperate> searchObjRule = new HashMap<>();
		// searchObjRule.put("columnName", FilterOperate.LIKE);
		// QueryWrapper<SysDataPermission> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysDataPermission, searchObjRule);

		// 3.自定义查询规则，自定义排序规则
		// Map<String, FilterOperate> searchObjRule = new HashMap<>();
		// searchObjRule.put("columnName", FilterOperate.LIKE);
		// QueryWrapper<SysDataPermission> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysDataPermission, searchObjRule, "columnName1|asc,columnName2|desc");

		IPage<SysDataPermission> pageList = sysDataPermissionService.page(new Page<SysDataPermission>(pageNo, pageSize), queryWrapper);
		return R.ok(pageList);
	}

	@RequiresPermissions("sys:dataPermission:list")
	@GetMapping(value = "/queryById")
	public R queryById(@RequestParam(name = "id", required = true) String id) {
		SysDataPermission sysDataPermission = sysDataPermissionService.getById(id);
		return R.ok(sysDataPermission);
	}

	/**
	 * @功能：新增
	 * @param sysDataPermission
	 * @return
	 */
	@SysLogAuto(value = "新增数据权限")
	@RequiresPermissions("sys:dataPermission:save")
	@PostMapping(value = "/save")
	public R save(@Valid @RequestBody SysDataPermission sysDataPermission) {
		sysDataPermissionService.save(sysDataPermission);
		return R.ok();
	}

	/**
	 * @功能：修改
	 * @param sysDataPermission
	 * @return
	 */
	@SysLogAuto(value = "修改数据权限")
	@RequiresPermissions("sys:dataPermission:update")
	@PutMapping(value = "/update")
	public R update(@Valid @RequestBody SysDataPermission sysDataPermission) {
		sysDataPermissionService.updateById(sysDataPermission);
		return R.ok();
	}

	/**
	 * @功能：批量删除
	 * @param ids
	 * @return
	 */
	@SysLogAuto(value = "删除数据权限")
	@RequiresPermissions("sys:dataPermission:delete")
	@DeleteMapping(value = "/delete")
	public R delete(@RequestParam(name = "ids", required = true) String ids) {
		if (ids == null || ids.trim().length() == 0) {
			return R.error("ids can't be empty");
		}
		String[] idsArr = ids.split(",");
		if (idsArr.length > 1) {
			sysDataPermissionService.removeByIds(Arrays.asList(idsArr));
		} else {
			sysDataPermissionService.removeById(idsArr[0]);
		}
		return R.ok();
	}
}
