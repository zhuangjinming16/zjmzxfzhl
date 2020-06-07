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
import com.zjmzxfzhl.modules.sys.entity.SysJobLog;
import com.zjmzxfzhl.modules.sys.service.SysJobLogService;

/**
 * 定时任务执行日志Controller
 * 
 * @author 庄金明
 */
@RestController
@RequestMapping("/sys/jobLog")
public class SysJobLogController extends BaseController {
    @Autowired
    private SysJobLogService sysJobLogService;

    /**
     * 自定义查询列表
     * 
     * @param sysJobLog
     * @param current
     * @param size
     * @return
     */
    // @RequiresPermissions("sys:jobLog:list")
    @GetMapping(value = "/list")
    public Result list(SysJobLog sysJobLog, @RequestParam Integer current, @RequestParam Integer size) {
        IPage<SysJobLog> pageList = sysJobLogService.list(new Page<SysJobLog>(current, size), sysJobLog);
        return Result.ok(pageList);
    }

    // @RequiresPermissions("sys:jobLog:list")
    @GetMapping(value = "/queryById")
    public Result queryById(@RequestParam String id) {
        SysJobLog sysJobLog = sysJobLogService.getById(id);
        return Result.ok(sysJobLog);
    }

    /**
     * @功能：新增
     * @param sysJobLog
     * @return
     */
    // @RequiresPermissions("sys:jobLog:save")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody SysJobLog sysJobLog) {
        sysJobLogService.save(sysJobLog);
        return Result.ok();
    }

    /**
     * @功能：修改
     * @param sysJobLog
     * @return
     */
    // @RequiresPermissions("sys:jobLog:update")
    @PutMapping(value = "/update")
    public Result update(@Valid @RequestBody SysJobLog sysJobLog) {
        sysJobLogService.updateById(sysJobLog);
        return Result.ok();
    }

    /**
     * @功能：批量删除
     * @param ids
     * @return
     */
    // @RequiresPermissions("sys:jobLog:delete")
    @DeleteMapping(value = "/delete")
    public Result delete(@RequestParam String ids) {
        if (ids == null || ids.trim().length() == 0) {
            return Result.error("ids can't be empty");
        }
        String[] idsArr = ids.split(",");
        if (idsArr.length > 1) {
            sysJobLogService.removeByIds(Arrays.asList(idsArr));
        } else {
            sysJobLogService.removeById(idsArr[0]);
        }
        return Result.ok();
    }
}
