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
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequiresPermissions("sys:codeType:list")
	@GetMapping(value = "/list")
	public R list(SysCodeType sysCodeType, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		IPage<SysCodeType> pageList = sysCodeTypeService.list(new Page<SysCodeType>(pageNo, pageSize), sysCodeType);
		return R.ok(pageList);
	}

	/**
	 * 使用QueryWrapper查询列表
	 * 
	 * @param sysCodeType
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequiresPermissions("sys:codeType:listByQw")
	@GetMapping(value = "/listByQw")
	public R listByQw(SysCodeType sysCodeType, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		// 1.最简查询条件封装，输入参数不为空则默认全部eq匹配
		QueryWrapper<SysCodeType> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysCodeType);

		// 2.自定义查询规则，默认按照主键升序排序
		// Map<String, FilterOperate> searchObjRule = new HashMap<>();
		// searchObjRule.put("columnName", FilterOperate.LIKE);
		// QueryWrapper<SysCodeType> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysCodeType, searchObjRule);

		// 3.自定义查询规则，自定义排序规则
		// Map<String, FilterOperate> searchObjRule = new HashMap<>();
		// searchObjRule.put("columnName", FilterOperate.LIKE);
		// QueryWrapper<SysCodeType> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysCodeType, searchObjRule, "columnName1|asc,columnName2|desc");

		IPage<SysCodeType> pageList = sysCodeTypeService.page(new Page<SysCodeType>(pageNo, pageSize), queryWrapper);
		return R.ok(pageList);
	}

	@RequiresPermissions("sys:codeType:list")
	@GetMapping(value = "/queryById")
	public R queryById(@RequestParam(name = "id", required = true) String id) {
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
	public R delete(@RequestParam(name = "ids", required = true) String ids) {
		sysCodeTypeService.deleteSysCodeType(ids);
		return R.ok();
	}
}
