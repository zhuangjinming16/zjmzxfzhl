package com.zjmzxfzhl.modules.sys.controller;

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
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 */
	// @RepeatRequest(lockIndexs = { 0 }, fieldNames = { "configId" })
	@RequiresPermissions("sys:config:list")
	@GetMapping(value = "/list")
	public R list(SysConfig sysConfig, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		IPage<SysConfig> pageList = sysConfigService.list(new Page<SysConfig>(pageNo, pageSize), sysConfig);
		return R.ok(pageList);
	}

	/**
	 * 使用QueryWrapper查询列表
	 * 
	 * @param sysConfig
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 */
	// @RepeatRequest(lockIndexs = { 0, 0 }, fieldNames = { "configId", "configName" })
	@RequiresPermissions("sys:config:listByQw")
	@GetMapping(value = "/listByQw")
	public R listByQw(SysConfig sysConfig, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		// 1.最简查询条件封装，输入参数不为空则默认全部eq匹配
		QueryWrapper<SysConfig> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysConfig);

		// 2.自定义查询规则，默认按照主键升序排序
		// Map<String, FilterOperate> searchObjRule = new HashMap<>();
		// searchObjRule.put("columnName", FilterOperate.LIKE);
		// QueryWrapper<SysConfig> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysConfig, searchObjRule);

		// 3.自定义查询规则，自定义排序规则
		// Map<String, FilterOperate> searchObjRule = new HashMap<>();
		// searchObjRule.put("columnName", FilterOperate.LIKE);
		// QueryWrapper<SysConfig> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysConfig, searchObjRule, "columnName1|asc,columnName2|desc");

		IPage<SysConfig> pageList = sysConfigService.page(new Page<SysConfig>(pageNo, pageSize), queryWrapper);
		return R.ok(pageList);
	}

	// @RepeatRequest(lockIndexs = 0)
	@RequiresPermissions("sys:config:list")
	@GetMapping(value = "/queryById")
	public R queryById(@RequestParam(name = "id", required = true) String id) {
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
	public R delete(@RequestParam(name = "ids", required = true) String ids) {
		sysConfigService.deleteSysConfig(ids);
		return R.ok();
	}
}
