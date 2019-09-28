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
import com.zjmzxfzhl.modules.sys.entity.SysLog;
import com.zjmzxfzhl.modules.sys.service.SysLogService;

/**
 * 系统日志Controller
 * 
 * @author 庄金明
 */
@RestController
@RequestMapping("/sys/log")
public class SysLogController extends BaseController {
	@Autowired
	private SysLogService sysLogService;

	/**
	 * 自定义查询列表
	 * 
	 * @param sysLog
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequiresPermissions("sys:log:list")
	@GetMapping(value = "/list")
	public R list(SysLog sysLog, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		IPage<SysLog> pageList = sysLogService.list(new Page<SysLog>(pageNo, pageSize), sysLog);
		return R.ok(pageList);
	}

	/**
	 * 使用QueryWrapper查询列表
	 * 
	 * @param sysLog
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequiresPermissions("sys:log:listByQw")
	@GetMapping(value = "/listByQw")
	public R listByQw(SysLog sysLog, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		// 1.最简查询条件封装，输入参数不为空则默认全部eq匹配
		QueryWrapper<SysLog> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysLog);

		// 2.自定义查询规则，默认按照主键升序排序
		// Map<String, FilterOperate> searchObjRule = new HashMap<>();
		// searchObjRule.put("columnName", FilterOperate.LIKE);
		// QueryWrapper<SysLog> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysLog, searchObjRule);

		// 3.自定义查询规则，自定义排序规则
		// Map<String, FilterOperate> searchObjRule = new HashMap<>();
		// searchObjRule.put("columnName", FilterOperate.LIKE);
		// QueryWrapper<SysLog> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysLog, searchObjRule, "columnName1|asc,columnName2|desc");

		IPage<SysLog> pageList = sysLogService.page(new Page<SysLog>(pageNo, pageSize), queryWrapper);
		return R.ok(pageList);
	}

	@RequiresPermissions("sys:log:list")
	@GetMapping(value = "/queryById")
	public R queryById(@RequestParam(name = "id", required = true) String id) {
		SysLog sysLog = sysLogService.getById(id);
		return R.ok(sysLog);
	}

	/**
	 * @功能：新增
	 * @param sysLog
	 * @return
	 */
	@RequiresPermissions("sys:log:save")
	@PostMapping(value = "/save")
	public R save(@Valid @RequestBody SysLog sysLog) {
		sysLogService.save(sysLog);
		return R.ok();
	}

	/**
	 * @功能：修改
	 * @param sysLog
	 * @return
	 */
	@RequiresPermissions("sys:log:update")
	@PutMapping(value = "/update")
	public R update(@Valid @RequestBody SysLog sysLog) {
		sysLogService.updateById(sysLog);
		return R.ok();
	}

	/**
	 * @功能：批量删除
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("sys:log:delete")
	@DeleteMapping(value = "/delete")
	public R delete(@RequestParam(name = "ids", required = true) String ids) {
		if (ids == null || ids.trim().length() == 0) {
			return R.error("ids can't be empty");
		}
		String[] idsArr = ids.split(",");
		if (idsArr.length > 1) {
			sysLogService.removeByIds(Arrays.asList(idsArr));
		} else {
			sysLogService.removeById(idsArr[0]);
		}
		return R.ok();
	}
}
