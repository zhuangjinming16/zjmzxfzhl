package com.zjmzxfzhl.modules.demo.controller;

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
import com.zjmzxfzhl.modules.demo.entity.DemoZjmzxfzhl;
import com.zjmzxfzhl.modules.demo.service.DemoZjmzxfzhlService;

/**
 * 开发示例Controller
 * 
 * @author 庄金明
 */
@RestController
@RequestMapping("/demo/zjmzxfzhl")
public class DemoZjmzxfzhlController extends BaseController {
	@Autowired
	private DemoZjmzxfzhlService demoZjmzxfzhlService;

	/**
	 * 自定义查询列表
	 * 
	 * @param demoZjmzxfzhl
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequiresPermissions("demo:zjmzxfzhl:list")
	@GetMapping(value = "/list")
	public R list(DemoZjmzxfzhl demoZjmzxfzhl, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		IPage<DemoZjmzxfzhl> pageList = demoZjmzxfzhlService.list(new Page<DemoZjmzxfzhl>(pageNo, pageSize), demoZjmzxfzhl);
		// IPage<DemoZjmzxfzhl> pageList = demoZjmzxfzhlService.listDataPermission1(new Page<DemoZjmzxfzhl>(pageNo, pageSize), demoZjmzxfzhl);
		// IPage<DemoZjmzxfzhl> pageList = demoZjmzxfzhlService.listDataPermission2(new Page<DemoZjmzxfzhl>(pageNo, pageSize), demoZjmzxfzhl);
		// IPage<DemoZjmzxfzhl> pageList = demoZjmzxfzhlService.listDataPermission3(new Page<DemoZjmzxfzhl>(pageNo, pageSize), demoZjmzxfzhl);

		// DemoZjmzxfzhl demoZjmzxfzhl2 = new DemoZjmzxfzhl();
		// BeanUtils.copyProperties(demoZjmzxfzhl, demoZjmzxfzhl2);
		// IPage<DemoZjmzxfzhl> pageList = demoZjmzxfzhlService.listDataPermission4(new Page<DemoZjmzxfzhl>(pageNo, pageSize), demoZjmzxfzhl, demoZjmzxfzhl2);

		// IPage<DemoZjmzxfzhl> pageList = demoZjmzxfzhlService.listDataPermission5(new Page<DemoZjmzxfzhl>(pageNo, pageSize), demoZjmzxfzhl);

		return R.ok(pageList);
	}

	/**
	 * 使用QueryWrapper查询列表
	 * 
	 * @param demoZjmzxfzhl
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequiresPermissions("demo:zjmzxfzhl:listByQw")
	@GetMapping(value = "/listByQw")
	public R listByQw(DemoZjmzxfzhl demoZjmzxfzhl, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		// 1.最简查询条件封装，输入参数不为空则默认全部eq匹配
		QueryWrapper<DemoZjmzxfzhl> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(demoZjmzxfzhl);

		// 2.自定义查询规则，默认按照主键升序排序
		// Map<String, FilterOperate> searchObjRule = new HashMap<>();
		// searchObjRule.put("columnName", FilterOperate.LIKE);
		// QueryWrapper<DemoZjmzxfzhl> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(demoZjmzxfzhl, searchObjRule);

		// 3.自定义查询规则，自定义排序规则
		// Map<String, FilterOperate> searchObjRule = new HashMap<>();
		// searchObjRule.put("columnName", FilterOperate.LIKE);
		// QueryWrapper<DemoZjmzxfzhl> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(demoZjmzxfzhl, searchObjRule, "columnName1|asc,columnName2|desc");

		IPage<DemoZjmzxfzhl> pageList = demoZjmzxfzhlService.page(new Page<DemoZjmzxfzhl>(pageNo, pageSize), queryWrapper);
		return R.ok(pageList);
	}

	@RequiresPermissions("demo:zjmzxfzhl:list")
	@GetMapping(value = "/queryById")
	public R queryById(@RequestParam(name = "id", required = true) String id) {
		DemoZjmzxfzhl demoZjmzxfzhl = demoZjmzxfzhlService.getById(id);
		return R.ok(demoZjmzxfzhl);
	}

	/**
	 * @功能：新增
	 * @param demoZjmzxfzhl
	 * @return
	 */
	@RequiresPermissions("demo:zjmzxfzhl:save")
	@PostMapping(value = "/save")
	public R save(@Valid @RequestBody DemoZjmzxfzhl demoZjmzxfzhl) {
		demoZjmzxfzhlService.save(demoZjmzxfzhl);
		return R.ok();
	}

	/**
	 * @功能：修改
	 * @param demoZjmzxfzhl
	 * @return
	 */
	@RequiresPermissions("demo:zjmzxfzhl:update")
	@PutMapping(value = "/update")
	public R update(@Valid @RequestBody DemoZjmzxfzhl demoZjmzxfzhl) {
		demoZjmzxfzhlService.updateById(demoZjmzxfzhl);
		return R.ok();
	}

	/**
	 * @功能：批量删除
	 * @param ids
	 * @return
	 */
	@RequiresPermissions("demo:zjmzxfzhl:delete")
	@DeleteMapping(value = "/delete")
	public R delete(@RequestParam(name = "ids", required = true) String ids) {
		if (ids == null || ids.trim().length() == 0) {
			return R.error("ids can't be empty");
		}
		String[] idsArr = ids.split(",");
		if (idsArr.length > 1) {
			demoZjmzxfzhlService.removeByIds(Arrays.asList(idsArr));
		} else {
			demoZjmzxfzhlService.removeById(idsArr[0]);
		}
		return R.ok();
	}
}
