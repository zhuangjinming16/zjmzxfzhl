package com.zjmzxfzhl.modules.sys.controller;

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
import com.zjmzxfzhl.modules.sys.entity.SysCodeType;
import com.zjmzxfzhl.modules.sys.service.SysCodeTypeService;

/**
 * 代码类别Controller
 * 
 * @author 庄金明
 */
@RestController
@RequestMapping("/sys/codeType")
public class SysCodeTypeController extends BaseController {
	@Autowired
	private SysCodeTypeService sysCodeTypeService;

	/**
	 * 自定义查询列表
	 * 
	 * @param sysCodeType
	 * @param current
	 * @param size
	 * @return
	 */
	@RequiresPermissions("sys:codeType:list")
	@GetMapping(value = "/list")
	public R list(SysCodeType sysCodeType, @RequestParam Integer current, @RequestParam Integer size) {
		IPage<SysCodeType> pageList = sysCodeTypeService.list(new Page<SysCodeType>(current, size), sysCodeType);
		return R.ok(pageList);
	}

	@RequiresPermissions("sys:codeType:list")
	@GetMapping(value = "/queryById")
	public R queryById(@RequestParam String id) {
		SysCodeType sysCodeType = sysCodeTypeService.getById(id);
		return R.ok(sysCodeType);
	}

	/**
	 * @功能：新增
	 * @param sysCodeType
	 * @return
	 */
	@SysLogAuto(value = "新增代码类别")
	@RequiresPermissions("sys:codeType:save")
	@PostMapping(value = "/save")
	public R save(@Valid @RequestBody SysCodeType sysCodeType) {
		sysCodeTypeService.save(sysCodeType);
		return R.ok();
	}

	/**
	 * @功能：修改
	 * @param sysCodeType
	 * @return
	 */
	@SysLogAuto(value = "修改代码类别")
	@RequiresPermissions("sys:codeType:update")
	@PutMapping(value = "/update")
	public R update(@Valid @RequestBody SysCodeType sysCodeType) {
		sysCodeTypeService.updateById(sysCodeType);
		return R.ok();
	}

	/**
	 * @功能：批量删除
	 * @param ids
	 * @return
	 */
	@SysLogAuto(value = "删除代码类别")
	@RequiresPermissions("sys:codeType:delete")
	@DeleteMapping(value = "/delete")
	public R delete(@RequestParam String ids) {
		sysCodeTypeService.deleteSysCodeType(ids);
		return R.ok();
	}
}
