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
import com.zjmzxfzhl.modules.sys.entity.SysFunc;
import com.zjmzxfzhl.modules.sys.service.SysFuncService;

/**
 * 功能Controller
 * 
 * @author 庄金明
 */
@RestController
@RequestMapping("/sys/func")
public class SysFuncController extends BaseController {
	@Autowired
	private SysFuncService sysFuncService;

	/**
	 * 自定义查询列表
	 * 
	 * @param sysFunc
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequiresPermissions("sys:func:list")
	@GetMapping(value = "/list")
	public R list(SysFunc sysFunc, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		IPage<SysFunc> pageList = sysFuncService.list(new Page<SysFunc>(pageNo, pageSize), sysFunc);
		return R.ok(pageList);
	}

	/**
	 * 使用QueryWrapper查询列表
	 * 
	 * @param sysFunc
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequiresPermissions("sys:func:listByQw")
	@GetMapping(value = "/listByQw")
	public R listByQw(SysFunc sysFunc, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		// 1.最简查询条件封装，输入参数不为空则默认全部eq匹配
		QueryWrapper<SysFunc> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysFunc);

		// 2.自定义查询规则，默认按照主键升序排序
		// Map<String, FilterOperate> searchObjRule = new HashMap<>();
		// searchObjRule.put("columnName", FilterOperate.LIKE);
		// QueryWrapper<SysFunc> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysFunc, searchObjRule);

		// 3.自定义查询规则，自定义排序规则
		// Map<String, FilterOperate> searchObjRule = new HashMap<>();
		// searchObjRule.put("columnName", FilterOperate.LIKE);
		// QueryWrapper<SysFunc> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysFunc, searchObjRule, "columnName1|asc,columnName2|desc");

		IPage<SysFunc> pageList = sysFuncService.page(new Page<SysFunc>(pageNo, pageSize), queryWrapper);
		return R.ok(pageList);
	}

	@RequiresPermissions("sys:func:list")
	@GetMapping(value = "/queryById")
	public R queryById(@RequestParam(name = "id", required = true) String id) {
		SysFunc sysFunc = sysFuncService.getById(id);
		return R.ok(sysFunc);
	}

	/**
	 * @功能：新增
	 * @param sysFunc
	 * @return
	 */
	@SysLogAuto(value = "新增功能按钮")
	@RequiresPermissions("sys:func:save")
	@PostMapping(value = "/save")
	public R save(@Valid @RequestBody SysFunc sysFunc) {
		sysFuncService.save(sysFunc);
		return R.ok();
	}

	/**
	 * @功能：修改
	 * @param sysFunc
	 * @return
	 */
	@SysLogAuto(value = "修改功能按钮")
	@RequiresPermissions("sys:func:update")
	@PutMapping(value = "/update")
	public R update(@Valid @RequestBody SysFunc sysFunc) {
		sysFuncService.updateById(sysFunc);
		return R.ok();
	}

	/**
	 * @功能：批量删除
	 * @param ids
	 * @return
	 */
	@SysLogAuto(value = "删除功能按钮")
	@RequiresPermissions("sys:func:delete")
	@DeleteMapping(value = "/delete")
	public R delete(@RequestParam(name = "ids", required = true) String ids) {
		if (ids == null || ids.trim().length() == 0) {
			return R.error("ids can't be empty");
		}
		String[] idsArr = ids.split(",");
		if (idsArr.length > 1) {
			sysFuncService.removeByIds(Arrays.asList(idsArr));
		} else {
			sysFuncService.removeById(idsArr[0]);
		}
		return R.ok();
	}
}
