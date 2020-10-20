package com.zjmzxfzhl.modules.sys.controller;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.zjmzxfzhl.common.core.Result;
import com.zjmzxfzhl.common.core.base.BaseController;
import com.zjmzxfzhl.modules.sys.entity.SysPostUser;
import com.zjmzxfzhl.modules.sys.service.SysPostUserService;

/**
 * 岗位和用户关系Controller
 *
 * @author 庄金明
 */
@RestController
@RequestMapping("/sys/postUser")
public class SysPostUserController extends BaseController {
    @Autowired
    private SysPostUserService sysPostUserService;

    /**
     * 自定义查询列表
     *
     * @param sysPostUser
     * @param current
     * @param size
     * @return
     */
    @PreAuthorize("@elp.single('sys:postUser:list')")
    @GetMapping(value = "/list")
    public Result list(SysPostUser sysPostUser, @RequestParam Integer current, @RequestParam Integer size) {
        IPage<SysPostUser> pageList = sysPostUserService.list(new Page<SysPostUser>(current, size), sysPostUser);
        return Result.ok(pageList);
    }

    @PreAuthorize("@elp.single('sys:postUser:list')")
    @GetMapping(value = "/queryById")
    public Result queryById(@RequestParam String id) {
        SysPostUser sysPostUser = sysPostUserService.getById(id);
        return Result.ok(sysPostUser);
    }

    /**
     * @param sysPostUser
     * @return
     * @功能：新增
     */
    @PreAuthorize("@elp.single('sys:postUser:save')")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody SysPostUser sysPostUser) {
        sysPostUserService.save(sysPostUser);
        return Result.ok();
    }

    /**
     * @param sysPostUser
     * @return
     * @功能：修改
     */
    @PreAuthorize("@elp.single('sys:postUser:update')")
    @PutMapping(value = "/update")
    public Result update(@Valid @RequestBody SysPostUser sysPostUser) {
        sysPostUserService.updateById(sysPostUser);
        return Result.ok();
    }

    /**
     * @param ids
     * @return
     * @功能：批量删除
     */
    @PreAuthorize("@elp.single('sys:postUser:delete')")
    @DeleteMapping(value = "/delete")
    public Result delete(@RequestParam String ids) {
        if (ids == null || ids.trim().length() == 0) {
            return Result.error("ids can't be empty");
        }
        String[] idsArr = ids.split(",");
        if (idsArr.length > 1) {
            sysPostUserService.removeByIds(Arrays.asList(idsArr));
        } else {
            sysPostUserService.removeById(idsArr[0]);
        }
        return Result.ok();
    }
}
