package com.zjmzxfzhl.modules.sys.controller;

import javax.validation.Valid;

import org.quartz.SchedulerException;
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
import com.zjmzxfzhl.modules.sys.entity.SysJob;
import com.zjmzxfzhl.modules.sys.service.SysJobService;

/**
 * 定时任务Controller
 * 
 * @author 庄金明
 */
@RestController
@RequestMapping("/sys/job")
public class SysJobController extends BaseController {
    @Autowired
    private SysJobService sysJobService;

    /**
     * 自定义查询列表
     * 
     * @param sysJob
     * @param current
     * @param size
     * @return
     */
    // @RequiresPermissions("sys:job:list")
    @GetMapping(value = "/list")
    public Result list(SysJob sysJob, @RequestParam Integer current, @RequestParam Integer size) {
        IPage<SysJob> pageList = sysJobService.list(new Page<SysJob>(current, size), sysJob);
        return Result.ok(pageList);
    }

    // @RequiresPermissions("sys:job:list")
    @GetMapping(value = "/queryById")
    public Result queryById(@RequestParam String id) {
        SysJob sysJob = sysJobService.getById(id);
        return Result.ok(sysJob);
    }

    /**
     * @功能：新增
     * @param sysJob
     * @return
     * @throws JobException
     * @throws SchedulerException
     */
    // @RequiresPermissions("sys:job:save")
    @PostMapping(value = "/save")
    public Result save(@Valid @RequestBody SysJob sysJob) throws SchedulerException {
        sysJobService.saveJob(sysJob);
        return Result.ok();
    }

    /**
     * @功能：修改
     * @param sysJob
     * @return
     */
    // @RequiresPermissions("sys:job:update")
    @PutMapping(value = "/update")
    public Result update(@Valid @RequestBody SysJob sysJob) throws SchedulerException {
        sysJobService.updateJob(sysJob);
        return Result.ok();
    }

    /**
     * @功能：批量删除
     * @param ids
     * @return
     * @throws SchedulerException
     */
    // @RequiresPermissions("sys:job:delete")
    @DeleteMapping(value = "/delete")
    public Result delete(@RequestParam String ids) throws SchedulerException {
        sysJobService.delete(ids);
        return Result.ok();
    }

    // @RequiresPermissions("sys:job:changeStatus")
    @PutMapping("/changeStatus")
    public Result changeStatus(@RequestBody SysJob sysJob) throws SchedulerException {
        sysJobService.changeStatus(sysJob.getJobId());
        return Result.ok();
    }

    // @RequiresPermissions("sys:job:run")
    @PutMapping("/run")
    public Result run(@RequestBody SysJob sysJob) throws SchedulerException {
        sysJobService.run(sysJob.getJobId());
        return Result.ok();
    }
}
