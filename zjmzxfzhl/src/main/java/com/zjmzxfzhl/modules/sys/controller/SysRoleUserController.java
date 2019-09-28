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
import com.zjmzxfzhl.common.base.BaseController;
import com.zjmzxfzhl.common.query.QueryWrapperGenerator;
import com.zjmzxfzhl.modules.sys.entity.SysRoleUser;
import com.zjmzxfzhl.modules.sys.service.SysRoleUserService;

/**
 * 角色和用户关系Controller
 * 
 * @author 庄金明
 */
@RestController
@RequestMapping("/sys/roleUser")
public class SysRoleUserController extends BaseController {
	@Autowired
	private SysRoleUserService sysRoleUserService;

	/**
	 * 自定义查询列表
	 * 
	 * @param sysRoleUser
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequiresPermissions("sys:roleUser:list")
	@GetMapping(value = "/list")
	public R list(SysRoleUser sysRoleUser, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		IPage<SysRoleUser> pageList = sysRoleUserService.list(new Page<SysRoleUser>(pageNo, pageSize), sysRoleUser);
		return R.ok(pageList);
	}

	/**
	 * 使用QueryWrapper查询列表
	 * 
	 * @param sysRoleUser
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequiresPermissions("sys:roleUser:listByQw")
	@GetMapping(value = "/listByQw")
	public R listByQw(SysRoleUser sysRoleUser, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		// 1.最简查询条件封装，输入参数不为空则默认全部eq匹配
		QueryWrapper<SysRoleUser> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysRoleUser);

		// 2.自定义查询规则，默认按照主键升序排序
		// Map<String, FilterOperate> searchObjRule = new HashMap<>();
		// searchObjRule.put("columnName", FilterOperate.LIKE);
		// QueryWrapper<SysRoleUser> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysRoleUser, searchObjRule);

		// 3.自定义查询规则，自定义排序规则
		// Map<String, FilterOperate> searchObjRule = new HashMap<>();
		// searchObjRule.put("columnName", FilterOperate.LIKE);
		// QueryWrapper<SysRoleUser> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysRoleUser, searchObjRule, "columnName1|asc,columnName2|desc");

		IPage<SysRoleUser> pageList = sysRoleUserService.page(new Page<SysRoleUser>(pageNo, pageSize), queryWrapper);
		return R.ok(pageList);
	}

	@RequiresPermissions("sys:roleUser:list")
	@GetMapping(value = "/queryById")
	public R queryById(@RequestParam(name = "id", required = true) String id) {
		SysRoleUser sysRoleUser = sysRoleUserService.getById(id);
		return R.ok(sysRoleUser);
	}

	/**
	 * @功能：新增
	 * @param sysRoleUser
	 * @return
	 */
	@RequiresPermissions("sys:roleUser:save")
	@PostMapping(value = "/save")
	public R save(@Valid @RequestBody SysRoleUser sysRoleUser) {
		sysRoleUserService.save(sysRoleUser);
		return R.ok();
	}

	/**
	 * @功能：修改
	 * @param sysRoleUser
	 * @return
	 */
	@RequiresPermissions("sys:roleUser:update")
	@PutMapping(value = "/update")
	public R update(@Valid @RequestBody SysRoleUser sysRoleUser) {
		sysRoleUserService.updateById(sysRoleUser);
		return R.ok();
	}

	/**
	 * @功能：批量删除
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("sys:roleUser:delete")
	@DeleteMapping(value = "/delete")
	public R delete(@RequestParam(name = "ids", required = true) String ids) {
		if (ids == null || ids.trim().length() == 0) {
			return R.error("ids can't be empty");
		}
		String[] idsArr = ids.split(",");
		if (idsArr.length > 1) {
			sysRoleUserService.removeByIds(Arrays.asList(idsArr));
		} else {
			sysRoleUserService.removeById(idsArr[0]);
		}
		return R.ok();
	}
}
