package com.zjmzxfzhl.modules.sys.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjmzxfzhl.common.core.Result;
import com.zjmzxfzhl.common.core.base.BaseController;
import com.zjmzxfzhl.common.core.exception.SysException;
import com.zjmzxfzhl.common.log.annotation.Log;
import com.zjmzxfzhl.common.security.userdetails.SecurityUser;
import com.zjmzxfzhl.common.security.util.SecurityUtils;
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

    /**
     * 自定义查询列表
     * 
     * @param sysUser
     * @param current
     * @param size
     * @return
     */
    // @RequiresPermissions("sys:user:list")
    @GetMapping(value = "/list")
    public Result list(SysUser sysUser, @RequestParam Integer current, @RequestParam Integer size) {
        IPage<SysUser> pageList = sysUserService.list(new Page<SysUser>(current, size), sysUser);
        // QueryWrapper<SysUser> queryWrapper = QueryWrapperGenerator.initQueryWrapperSimple(sysUser);
        // IPage<SysUser> pageList = sysUserService.getBaseMapper().selectPage(new Page<SysUser>(current, size),
        // queryWrapper);
        return Result.ok(pageList);
    }

    /**
     * 公共选人查询
     * 
     * @param sysUser
     * @param current
     * @param size
     * @return
     */
    @GetMapping(value = "/listSelectUser")
    public Result listSelectUser(SysUser sysUser, @RequestParam Integer current, @RequestParam Integer size) {
        IPage<SysUser> pageList = sysUserService.listSelectUser(new Page<SysUser>(current, size), sysUser);
        return Result.ok(pageList);
    }

    // @RequiresPermissions("sys:user:list")
    @GetMapping(value = "/queryById")
    public Result queryById(@RequestParam String id) {
        SysUser sysUser = sysUserService.getById(id);
        return Result.ok(sysUser);
    }

    /**
     * @功能：新增
     * @param sysUser
     * @return
     */
    @Log(value = "新增用户")
    // @RequiresPermissions("sys:user:save")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody SysUser sysUser) {
        sysUserService.saveSysUser(sysUser);
        return Result.ok();
    }

    /**
     * @功能：修改
     * @param sysUser
     * @return
     */
    @Log(value = "修改用户")
    // @RequiresPermissions("sys:user:update")
    @PutMapping(value = "/update")
    public Result update(@Valid @RequestBody SysUser sysUser) {
        sysUserService.updateSysUser(sysUser);
        return Result.ok();
    }

    /**
     * @功能：批量删除
     * @param ids
     * @return
     */
    @Log(value = "删除用户")
    // @RequiresPermissions("sys:user:delete")
    @DeleteMapping(value = "/delete")
    public Result delete(@RequestParam String ids) {
        sysUserService.delete(ids);
        return Result.ok();
    }

    @Log(value = "获取用户信息")
    @GetMapping(value = "/getUserInfo")
    public Result getUserInfo(@RequestParam(required = false) String roleId, HttpServletRequest request) {
        SecurityUser securityUser = null;
        if (roleId != null && !roleId.isEmpty()) {
            securityUser = sysUserService.saveGetUserInfo(null, roleId);
        } else {
            securityUser = (SecurityUser) SecurityUtils.getUserDetails();
        }

        // sessionObject = sysUserService.saveGetUserInfo("admin", roleId);
        return Result.ok(securityUser);
    }

    @PostMapping(value = "/updatePassword")
    public Result updatePassword(@RequestBody SysPasswordForm sysPasswordForm) {
        boolean success = sysUserService.updatePassword(sysPasswordForm);
        if (!success) {
            return Result.error("原密码错误");
        }
        return Result.ok();
    }

    @Log(value = "导出用户信息")
    // @RequiresPermissions("sys:user:export")
    @GetMapping(value = "/export")
    public void export(SysUser sysUser, HttpServletResponse response) throws IOException {
        try {
            IPage<SysUser> page = sysUserService.list(null, sysUser);
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=SysUserExport.xlsx");
            EasyExcel.write(response.getOutputStream(), SysUser.class).sheet("SysUser").doWrite(page.getRecords());
        } catch (Exception e) {
            throw new SysException("下载文件失败：" + e.getMessage());
        }
    }
}
