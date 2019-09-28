package com.zjmzxfzhl.modules.sys.controller;

import java.util.List;
import java.util.Map;

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
import com.zjmzxfzhl.modules.sys.entity.SysCodeInfo;
import com.zjmzxfzhl.modules.sys.service.SysCodeInfoService;

/**
 * 代码信息Controller
 * 
 * @author 庄金明
 */
@RestController
@RequestMapping("/sys/codeInfo")
public class SysCodeInfoController extends BaseController {
	@Autowired
	private SysCodeInfoService sysCodeInfoService;

	/**
	 * 自定义查询列表
	 * 
	 * @param sysCodeInfo
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequiresPermissions("sys:codeInfo:list")
	@GetMapping(value = "/list")
	public R list(SysCodeInfo sysCodeInfo, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		IPage<SysCodeInfo> pageList = sysCodeInfoService.list(new Page<SysCodeInfo>(pageNo, pageSize), sysCodeInfo);
		return R.ok(pageList);
	}

	/**
	 * 使用QueryWrapper查询列表
	 * 
	 * @param sysCodeInfo
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequiresPermissions("sys:codeInfo:listByQw")
	@GetMapping(value = "/listByQw")
	public R listByQw(SysCodeInfo sysCodeInfo, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		// 1.最简查询条件封装，输入参数不为空则默认全部eq匹配
		QueryWrapper<SysCodeInfo> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysCodeInfo);

		// 2.自定义查询规则，默认按照主键升序排序
		// Map<String, FilterOperate> searchObjRule = new HashMap<>();
		// searchObjRule.put("columnName", FilterOperate.LIKE);
		// QueryWrapper<SysCodeInfo> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysCodeInfo, searchObjRule);

		// 3.自定义查询规则，自定义排序规则
		// Map<String, FilterOperate> searchObjRule = new HashMap<>();
		// searchObjRule.put("columnName", FilterOperate.LIKE);
		// QueryWrapper<SysCodeInfo> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysCodeInfo, searchObjRule, "columnName1|asc,columnName2|desc");

		IPage<SysCodeInfo> pageList = sysCodeInfoService.page(new Page<SysCodeInfo>(pageNo, pageSize), queryWrapper);
		return R.ok(pageList);
	}

	@RequiresPermissions("sys:codeInfo:list")
	@GetMapping(value = "/queryById")
	public R queryById(@RequestParam(name = "id", required = true) String id) {
		SysCodeInfo sysCodeInfo = sysCodeInfoService.getById(id);
		return R.ok(sysCodeInfo);
	}

	/**
	 * @功能：新增
	 * @param sysCodeInfo
	 * @return
	 */
	@SysLogAuto(value = "新增代码信息")
	@RequiresPermissions("sys:codeInfo:save")
	@PostMapping(value = "/save")
	public R save(@Valid @RequestBody SysCodeInfo sysCodeInfo) {
		sysCodeInfoService.saveSysCodeInfo(sysCodeInfo);
		return R.ok();
	}

	/**
	 * @功能：修改
	 * @param sysCodeInfo
	 * @return
	 */
	@SysLogAuto(value = "修改代码信息")
	@RequiresPermissions("sys:codeInfo:update")
	@PutMapping(value = "/update")
	public R update(@Valid @RequestBody SysCodeInfo sysCodeInfo) {
		sysCodeInfoService.updateSysCodeInfo(sysCodeInfo);
		return R.ok();
	}

	/**
	 * @功能：批量删除
	 * @param ids
	 * @return
	 */
	@SysLogAuto(value = "删除代码信息")
	@RequiresPermissions("sys:codeInfo:delete")
	@DeleteMapping(value = "/delete")
	public R delete(@RequestParam(name = "ids", required = true) String ids) {
		sysCodeInfoService.deleteSysCodeInfo(ids);
		return R.ok();
	}

	/**
	 * 根据代码类型查询代码信息清单
	 * 
	 * @param codeTypeIds
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/getSysCodeInfos")
	public R getSysCodeInfos(String codeTypeIds) {
		Map<String, List<SysCodeInfo>> sysCodeInfosMap = sysCodeInfoService.getSysCodeInfosFromRedis(codeTypeIds);
		if (sysCodeInfosMap == null) {
			sysCodeInfosMap = sysCodeInfoService.getSysCodeInfosFromDb(codeTypeIds);
		}
		return R.ok(sysCodeInfosMap);
	}
}
