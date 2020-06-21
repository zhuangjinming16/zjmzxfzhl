package com.zjmzxfzhl.modules.sys.controller;

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
import com.zjmzxfzhl.common.core.Result;
import com.zjmzxfzhl.common.core.base.BaseController;
import com.zjmzxfzhl.common.log.annotation.Log;
import com.zjmzxfzhl.modules.sys.entity.SysPost;
import com.zjmzxfzhl.modules.sys.entity.SysPostUser;
import com.zjmzxfzhl.modules.sys.entity.SysUser;
import com.zjmzxfzhl.modules.sys.service.SysPostService;

/**
 * 岗位Controller
 * 
 * @author 庄金明
 */
@RestController
@RequestMapping("/sys/post")
public class SysPostController extends BaseController {
    @Autowired
    private SysPostService sysPostService;

    /**
     * 自定义查询列表
     * 
     * @param sysPost
     * @param current
     * @param size
     * @return
     */
    // @RequiresPermissions("sys:post:list")
    @GetMapping(value = "/list")
    public Result list(SysPost sysPost, @RequestParam Integer current, @RequestParam Integer size) {
        IPage<SysPost> pageList = sysPostService.list(new Page<SysPost>(current, size), sysPost);
        return Result.ok(pageList);
    }

    // @RequiresPermissions("sys:post:list")
    @GetMapping(value = "/queryById")
    public Result queryById(@RequestParam String id) {
        SysPost sysPost = sysPostService.getById(id);
        return Result.ok(sysPost);
    }

    /**
     * @功能：新增
     * @param sysPost
     * @return
     */
    // @RequiresPermissions("sys:post:save")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody SysPost sysPost) {
        sysPostService.save(sysPost);
        return Result.ok();
    }

    /**
     * @功能：修改
     * @param sysPost
     * @return
     */
    // @RequiresPermissions("sys:post:update")
    @PutMapping(value = "/update")
    public Result update(@Valid @RequestBody SysPost sysPost) {
        sysPostService.updateById(sysPost);
        return Result.ok();
    }

    /**
     * @功能：批量删除
     * @param ids
     * @return
     */
    // @RequiresPermissions("sys:post:delete")
    @DeleteMapping(value = "/delete")
    public Result delete(@RequestParam String ids) {
        if (ids == null || ids.trim().length() == 0) {
            return Result.error("ids can't be empty");
        }
        this.sysPostService.delete(ids);
        return Result.ok();
    }

    /**
     * 获取岗位用户
     * 
     * @param sysPostUser
     * @param current
     * @param size
     * @return
     */
    // @RequiresPermissions("sys:post:getPostUser")
    @GetMapping(value = "/getPostUser")
    public Result getPostUser(SysPostUser sysPostUser, @RequestParam Integer current, @RequestParam Integer size) {
        IPage<SysUser> pageList = this.sysPostService.getPostUser(new Page<SysUser>(current, size), sysPostUser);
        return Result.ok(pageList);
    }

    /**
     * 保存岗位用户
     * 
     * @param sysPostUser
     * @return
     */
    @Log(value = "保存岗位用户")
    // @RequiresPermissions("sys:post:savePostUsers")
    @PostMapping(value = "/savePostUsers")
    public Result savePostUsers(@RequestBody SysPostUser sysPostUser) {
        this.sysPostService.savePostUsers(sysPostUser.getPostId(), sysPostUser.getUserId());
        return Result.ok();
    }

    /**
     * 删除岗位用户
     * 
     * @param postId
     * @param userIds
     * @return
     */
    @Log(value = "删除岗位用户")
    // @RequiresPermissions("sys:post:deletePostUsers")
    @DeleteMapping(value = "/deletePostUsers")
    public Result deletePostUsers(String postId, String userIds) {
        this.sysPostService.deletePostUsers(postId, userIds);
        return Result.ok();
    }
}
