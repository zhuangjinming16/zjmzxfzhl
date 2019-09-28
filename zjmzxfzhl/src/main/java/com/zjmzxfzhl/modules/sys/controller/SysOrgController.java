package com.zjmzxfzhl.modules.sys.controller;

import java.util.Arrays;
import java.util.List;

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
import com.zjmzxfzhl.common.exception.SysException;
import com.zjmzxfzhl.common.query.QueryWrapperGenerator;
import com.zjmzxfzhl.common.util.CommonUtil;
import com.zjmzxfzhl.modules.sys.entity.SysOrg;
import com.zjmzxfzhl.modules.sys.entity.vo.ElTree;
import com.zjmzxfzhl.modules.sys.service.SysOrgService;

/**
 * 机构Controller
 * 
 * @author 庄金明
 */
@RestController
@RequestMapping("/sys/org")
public class SysOrgController extends BaseController {
	@Autowired
	private SysOrgService sysOrgService;

	/**
	 * 自定义查询列表
	 * 
	 * @param sysOrg
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequiresPermissions("sys:org:list")
	@GetMapping(value = "/list")
	public R list(SysOrg sysOrg, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		IPage<SysOrg> pageList = sysOrgService.list(new Page<SysOrg>(pageNo, pageSize), sysOrg);
		return R.ok(pageList);
	}

	/**
	 * 使用QueryWrapper查询列表
	 * 
	 * @param sysOrg
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequiresPermissions("sys:org:listByQw")
	@GetMapping(value = "/listByQw")
	public R listByQw(SysOrg sysOrg, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		// 1.最简查询条件封装，输入参数不为空则默认全部eq匹配
		QueryWrapper<SysOrg> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysOrg);

		// 2.自定义查询规则，默认按照主键升序排序
		// Map<String, FilterOperate> searchObjRule = new HashMap<>();
		// searchObjRule.put("columnName", FilterOperate.LIKE);
		// QueryWrapper<SysOrg> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysOrg, searchObjRule);

		// 3.自定义查询规则，自定义排序规则
		// Map<String, FilterOperate> searchObjRule = new HashMap<>();
		// searchObjRule.put("columnName", FilterOperate.LIKE);
		// QueryWrapper<SysOrg> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysOrg, searchObjRule, "columnName1|asc,columnName2|desc");

		IPage<SysOrg> pageList = sysOrgService.page(new Page<SysOrg>(pageNo, pageSize), queryWrapper);
		return R.ok(pageList);
	}

	@RequiresPermissions("sys:org:list")
	@GetMapping(value = "/queryById")
	public R queryById(@RequestParam(name = "id", required = true) String id) {
		SysOrg sysOrg = sysOrgService.getById(id);
		return R.ok(sysOrg);
	}

	/**
	 * @功能：新增
	 * @param sysOrg
	 * @return
	 */
	@SysLogAuto(value = "新增机构")
	@RequiresPermissions("sys:org:save")
	@PostMapping(value = "/save")
	public R save(@Valid @RequestBody SysOrg sysOrg) {
		sysOrgService.saveSysOrg(sysOrg);
		return R.ok(sysOrg);
	}

	/**
	 * @功能：修改
	 * @param sysOrg
	 * @return
	 */
	@SysLogAuto(value = "修改机构")
	@RequiresPermissions("sys:org:update")
	@PutMapping(value = "/update")
	public R update(@Valid @RequestBody SysOrg sysOrg) {
		sysOrgService.updateSysOrg(sysOrg);
		return R.ok(sysOrg);
	}

	/**
	 * @功能：批量删除
	 * @param ids
	 * @return
	 */
	@SysLogAuto(value = "删除机构")
	@RequiresPermissions("sys:org:delete")
	@DeleteMapping(value = "/delete")
	public R delete(@RequestParam(name = "ids", required = true) String ids) {
		if (ids == null || ids.trim().length() == 0) {
			return R.error("ids can't be empty");
		}
		String[] idsArr = ids.split(",");
		if (idsArr.length > 1) {
			sysOrgService.removeByIds(Arrays.asList(idsArr));
		} else {
			sysOrgService.removeById(idsArr[0]);
		}
		return R.ok();
	}

	/**
	 * 机构管理，机构树数据
	 * 
	 * @return
	 */
	@RequiresPermissions("sys:org:getTreeData")
	@GetMapping(value = "/getTreeData")
	public R getTreeData() {
		List<ElTree> treeData = sysOrgService.getTreeData();
		return R.ok(treeData);
	}

	/**
	 * 公共机构选择树
	 * 
	 * @return
	 */
	@GetMapping(value = "/getSelectTreeData")
	public R getSelectTreeData(String type) {
		type = CommonUtil.isEmptyDefault(type, "1"); // 默认type=1
		List<SysOrg> orgList = null;
		if ("1".equals(type)) {
			orgList = sysOrgService.listOrgDataPermission1(new SysOrg());
		} else if ("2".equals(type)) {
			orgList = sysOrgService.listOrgDataPermission2(new SysOrg());
		} else if ("3".equals(type)) {
			orgList = sysOrgService.listOrgDataPermission3(new SysOrg());
		} else {
			throw new SysException("参数错误[type=" + type + "]");
		}
		List<ElTree> treeData = sysOrgService.makeOrgTree(orgList);
		return R.ok(treeData);
	}
}
