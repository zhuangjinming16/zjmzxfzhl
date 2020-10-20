package com.zjmzxfzhl.modules.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.base.BaseService;
import com.zjmzxfzhl.modules.sys.entity.SysJobLog;

/**
 * 定时任务执行日志Service
 *
 * @author 庄金明
 */
public interface SysJobLogService extends BaseService<SysJobLog> {
    /**
     * 分页查询定时任务执行日志
     *
     * @param page
     * @param sysJobLog
     * @return
     */
    IPage<SysJobLog> list(IPage<SysJobLog> page, SysJobLog sysJobLog);
}
