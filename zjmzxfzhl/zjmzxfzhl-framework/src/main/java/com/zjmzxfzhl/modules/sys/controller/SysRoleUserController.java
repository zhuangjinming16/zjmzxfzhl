package com.zjmzxfzhl.modules.sys.controller;

import java.util.Arrays;

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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjmzxfzhl.common.Result;
import com.zjmzxfzhl.common.base.BaseController;
import com.zjmzxfzhl.modules.sys.entity.SysRoleUser;
import com.zjmzxfzhl.modules.sys.service.SysRoleUserService;

/**
 * 角色和用户关系Controller
 * 
 * @author 庄金明
 */
@RestController
@RequestMapping("/sys/roleUser")
public class SysRoleUserController extends BaseController {
    @Autowired
    private SysRoleUserService sysRoleUserService;

    /**
     * 自定义查询列表
     * 
     * @param sysRoleUser
     * @param current
     * @param size
     * @return
     */
    // @RequiresPermissions("sys:roleUser:list")
    @GetMapping(value = "/list")
    public Result list(SysRoleUser sysRoleUser, @RequestParam Integer current, @RequestParam Integer size) {
        IPage<SysRoleUser> pageList = sysRoleUserService.list(new Page<SysRoleUser>(current, size), sysRoleUser);
        return Result.ok(pageList);
    }

    // @RequiresPermissions("sys:roleUser:list")
    @GetMapping(value = "/queryById")
    public Result queryById(@RequestParam String id) {
        SysRoleUser sysRoleUser = sysRoleUserService.getById(id);
        return Result.ok(sysRoleUser);
    }

    /**
     * @功能：新增
     * @param sysRoleUser
     * @return
     */
    // @RequiresPermissions("sys:roleUser:save")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody SysRoleUser sysRoleUser) {
        sysRoleUserService.save(sysRoleUser);
        return Result.ok();
    }

    /**
     * @功能：修改
     * @param sysRoleUser
     * @return
     */
    // @RequiresPermissions("sys:roleUser:update")
    @PutMapping(value = "/update")
    public Result update(@Valid @RequestBody SysRoleUser sysRoleUser) {
        sysRoleUserService.updateById(sysRoleUser);
        return Result.ok();
    }

    /**
     * @功能：批量删除
     * @param ids
     * @return
     */
    // @RequiresPermissions("sys:roleUser:delete")
    @DeleteMapping(value = "/delete")
    public Result delete(@RequestParam String ids) {
        if (ids == null || ids.trim().length() == 0) {
            return Result.error("ids can't be empty");
        }
        String[] idsArr = ids.split(",");
        if (idsArr.length > 1) {
            sysRoleUserService.removeByIds(Arrays.asList(idsArr));
        } else {
            sysRoleUserService.removeById(idsArr[0]);
        }
        return Result.ok();
    }
}
