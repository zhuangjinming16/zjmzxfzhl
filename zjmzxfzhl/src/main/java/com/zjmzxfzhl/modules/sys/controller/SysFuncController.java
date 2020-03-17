package com.zjmzxfzhl.modules.sys.controller;

import java.util.Arrays;

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
	 * @param current
	 * @param size
	 * @return
	 */
	@RequiresPermissions("sys:func:list")
	@GetMapping(value = "/list")
	public R list(SysFunc sysFunc, @RequestParam Integer current, @RequestParam Integer size) {
		IPage<SysFunc> pageList = sysFuncService.list(new Page<SysFunc>(current, size), sysFunc);
		return R.ok(pageList);
	}

	@RequiresPermissions("sys:func:list")
	@GetMapping(value = "/queryById")
	public R queryById(@RequestParam String id) {
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
	public R delete(@RequestParam String ids) {
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
