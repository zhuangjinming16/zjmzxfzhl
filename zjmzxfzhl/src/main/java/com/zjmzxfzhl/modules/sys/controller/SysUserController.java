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
import com.zjmzxfzhl.common.Constants;
import com.zjmzxfzhl.common.R;
import com.zjmzxfzhl.common.aspect.annotation.SysLogAuto;
import com.zjmzxfzhl.common.base.BaseController;
import com.zjmzxfzhl.common.query.QueryWrapperGenerator;
import com.zjmzxfzhl.common.util.DateUtil;
import com.zjmzxfzhl.common.util.IPUtils;
import com.zjmzxfzhl.common.util.JwtUtil;
import com.zjmzxfzhl.common.util.RedisUtil;
import com.zjmzxfzhl.common.util.ShiroUtils;
import com.zjmzxfzhl.modules.sys.common.SessionObject;
import com.zjmzxfzhl.modules.sys.entity.SysUser;
import com.zjmzxfzhl.modules.sys.entity.vo.SysPasswordForm;
import com.zjmzxfzhl.modules.sys.service.SysUserService;

/**
 * 用户Controller
 * 
 * @author 庄金明
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {
	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 自定义查询列表
	 * 
	 * @param sysUser
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequiresPermissions("sys:user:list")
	@GetMapping(value = "/list")
	public R list(SysUser sysUser, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		IPage<SysUser> pageList = sysUserService.list(new Page<SysUser>(pageNo, pageSize), sysUser);
		return R.ok(pageList);
	}

	/**
	 * 公共选人查询
	 * 
	 * @param sysUser
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@GetMapping(value = "/listSelectUser")
	public R listSelectUser(SysUser sysUser, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		IPage<SysUser> pageList = sysUserService.listSelectUser(new Page<SysUser>(pageNo, pageSize), sysUser);
		return R.ok(pageList);
	}

	/**
	 * 使用QueryWrapper查询列表
	 * 
	 * @param sysUser
	 * @param pageNo
	 * @param pageSize
	 * @param request
	 * @return
	 */
	@RequiresPermissions("sys:user:listByQw")
	@GetMapping(value = "/listByQw")
	public R listByQw(SysUser sysUser, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo, @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		// 1.最简查询条件封装，输入参数不为空则默认全部eq匹配
		QueryWrapper<SysUser> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysUser);

		// 2.自定义查询规则，默认按照主键升序排序
		// Map<String, FilterOperate> searchObjRule = new HashMap<>();
		// searchObjRule.put("columnName", FilterOperate.LIKE);
		// QueryWrapper<SysUser> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysUser, searchObjRule);

		// 3.自定义查询规则，自定义排序规则
		// Map<String, FilterOperate> searchObjRule = new HashMap<>();
		// searchObjRule.put("columnName", FilterOperate.LIKE);
		// QueryWrapper<SysUser> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysUser, searchObjRule, "columnName1|asc,columnName2|desc");

		IPage<SysUser> pageList = sysUserService.page(new Page<SysUser>(pageNo, pageSize), queryWrapper);
		return R.ok(pageList);
	}

	@RequiresPermissions("sys:user:list")
	@GetMapping(value = "/queryById")
	public R queryById(@RequestParam(name = "id", required = true) String id) {
		SysUser sysUser = sysUserService.getById(id);
		return R.ok(sysUser);
	}

	/**
	 * @功能：新增
	 * @param sysUser
	 * @return
	 */
	@SysLogAuto(value = "新增用户")
	@RequiresPermissions("sys:user:save")
	@PostMapping(value = "/save")
	public R save(@Valid @RequestBody SysUser sysUser) {
		sysUserService.saveSysUser(sysUser);
		return R.ok();
	}

	/**
	 * @功能：修改
	 * @param sysUser
	 * @return
	 */
	@SysLogAuto(value = "修改用户")
	@RequiresPermissions("sys:user:update")
	@PutMapping(value = "/update")
	public R update(@Valid @RequestBody SysUser sysUser) {
		sysUserService.updateSysUser(sysUser);
		return R.ok();
	}

	/**
	 * @功能：批量删除
	 * @param ids
	 * @return
	 */
	@SysLogAuto(value = "删除用户")
	@RequiresPermissions("sys:user:delete")
	@DeleteMapping(value = "/delete")
	public R delete(@RequestParam(name = "ids", required = true) String ids) {
		sysUserService.delete(ids);
		return R.ok();
	}

	@SysLogAuto(value = "获取用户信息")
	@GetMapping(value = "/getUserInfo")
	public R getUserInfo(@RequestParam(name = "roleId", defaultValue = "") String roleId, HttpServletRequest request) {
		SysUser sysUser = ShiroUtils.getSysUser();
		SessionObject sessionObject = sysUserService.saveGetUserInfo(sysUser, roleId);
		sessionObject.setLoginTime(DateUtil.getNow());
		sessionObject.setIpAddr(IPUtils.getIpAddr(request));
		sessionObject.setToken((String) redisUtil.get(Constants.PREFIX_USER_TOKEN + sysUser.getUserId()));
		redisUtil.set(Constants.PREFIX_USER_SESSION_OBJECT + sysUser.getUserId(), sessionObject, JwtUtil.EXPIRE_TIME);
		return R.ok(sessionObject);
	}

	// @SysLogAuto(value = "修改密码") //修改密码暂时不做日志登记
	@PostMapping(value = "/updatePassword")
	public R updatePassword(@RequestBody SysPasswordForm sysPasswordForm) {
		boolean success = sysUserService.updatePassword(sysPasswordForm);
		if (!success) {
			return R.error("原密码错误");
		}
		return R.ok();
	}
}
