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
import com.zjmzxfzhl.modules.sys.entity.SysConfig;
import com.zjmzxfzhl.modules.sys.service.SysConfigService;

/**
 * 系统参数Controller
 * 
 * @author 庄金明
 */
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends BaseController {
	@Autowired
	private SysConfigService sysConfigService;

	/**
	 * 自定义查询列表
	 * 
	 * @param sysConfig
	 * @param current
	 * @param size
	 * @return
	 */
	// @RepeatRequest(lockIndexs = { 0 }, fieldNames = { "configId" })
	@RequiresPermissions("sys:config:list")
	@GetMapping(value = "/list")
	public R list(SysConfig sysConfig, @RequestParam Integer current, @RequestParam Integer size) {
		IPage<SysConfig> pageList = sysConfigService.list(new Page<SysConfig>(current, size), sysConfig);
		return R.ok(pageList);
	}

	// @RepeatRequest(lockIndexs = 0)
	@RequiresPermissions("sys:config:list")
	@GetMapping(value = "/queryById")
	public R queryById(@RequestParam String id) {
		SysConfig sysConfig = sysConfigService.getById(id);
		return R.ok(sysConfig);
	}

	/**
	 * @功能：新增
	 * @param sysConfig
	 * @return
	 */
	@SysLogAuto(value = "新增系统参数")
	@RequiresPermissions("sys:config:save")
	@PostMapping(value = "/save")
	// public R save(@Validated({ AddGroup.class, UpdateGroup.class }) @RequestBody SysConfig sysConfig) {
	public R save(@Valid @RequestBody SysConfig sysConfig) {
		sysConfigService.saveSysConfig(sysConfig);
		return R.ok();
	}

	/**
	 * @功能：修改
	 * @param sysConfig
	 * @return
	 */
	// @RepeatRequest(lockIndexs = { 0 }, fieldNames = { "configId" })
	@SysLogAuto(value = "修改系统参数")
	@RequiresPermissions("sys:config:update")
	@PutMapping(value = "/update")
	public R update(@Valid @RequestBody SysConfig sysConfig) {
		sysConfigService.updateSysConfig(sysConfig);
		return R.ok();
	}

	/**
	 * @功能：批量删除
	 * @param ids
	 * @return
	 */
	@SysLogAuto(value = "删除系统参数")
	@RequiresPermissions("sys:config:delete")
	@DeleteMapping(value = "/delete")
	public R delete(@RequestParam String ids) {
		sysConfigService.deleteSysConfig(ids);
		return R.ok();
	}
}
