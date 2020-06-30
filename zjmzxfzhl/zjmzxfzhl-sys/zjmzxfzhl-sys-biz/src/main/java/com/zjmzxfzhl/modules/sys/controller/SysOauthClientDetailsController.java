package com.zjmzxfzhl.modules.sys.controller;

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
import com.zjmzxfzhl.modules.sys.entity.SysOauthClientDetails;
import com.zjmzxfzhl.modules.sys.service.SysOauthClientDetailsService;

/**
 * 应用客户端Controller
 * 
 * @author 庄金明
 */
@RestController
@RequestMapping("/sys/oauthClientDetails")
public class SysOauthClientDetailsController extends BaseController {
    @Autowired
    private SysOauthClientDetailsService sysOauthClientDetailsService;

    /**
     * 自定义查询列表
     * 
     * @param sysOauthClientDetails
     * @param current
     * @param size
     * @return
     */
    @PreAuthorize("@elp.single('sys:oauthClientDetails:list')")
    @GetMapping(value = "/list")
    public Result list(SysOauthClientDetails sysOauthClientDetails, @RequestParam Integer current,
            @RequestParam Integer size) {
        IPage<SysOauthClientDetails> pageList = sysOauthClientDetailsService
                .list(new Page<SysOauthClientDetails>(current, size), sysOauthClientDetails);
        return Result.ok(pageList);
    }

    @PreAuthorize("@elp.single('sys:oauthClientDetails:list')")
    @GetMapping(value = "/queryById")
    public Result queryById(@RequestParam String id) {
        SysOauthClientDetails sysOauthClientDetails = sysOauthClientDetailsService.getById(id);
        return Result.ok(sysOauthClientDetails);
    }

    /**
     * @功能：新增
     * @param sysOauthClientDetails
     * @return
     */
    @PreAuthorize("@elp.single('sys:oauthClientDetails:save')")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody SysOauthClientDetails sysOauthClientDetails) {
        sysOauthClientDetailsService.saveSysOauthClientDetails(sysOauthClientDetails);
        return Result.ok();
    }

    /**
     * @功能：修改
     * @param sysOauthClientDetails
     * @return
     */
    @PreAuthorize("@elp.single('sys:oauthClientDetails:update')")
    @PutMapping(value = "/update")
    public Result update(@Valid @RequestBody SysOauthClientDetails sysOauthClientDetails) {
        sysOauthClientDetailsService.updateSysOauthClientDetails(sysOauthClientDetails);
        return Result.ok();
    }

    /**
     * @功能：批量删除
     * @param ids
     * @return
     */
    @PreAuthorize("@elp.single('sys:oauthClientDetails:delete')")
    @DeleteMapping(value = "/delete")
    public Result delete(@RequestParam String ids) {
        sysOauthClientDetailsService.delete(ids);
        return Result.ok();
    }
}
