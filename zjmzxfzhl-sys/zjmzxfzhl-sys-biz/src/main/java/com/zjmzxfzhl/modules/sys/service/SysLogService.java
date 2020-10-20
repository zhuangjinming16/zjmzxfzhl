package com.zjmzxfzhl.modules.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.base.BaseService;
import com.zjmzxfzhl.modules.sys.entity.SysLog;

/**
 * 系统日志Service
 *
 * @author 庄金明
 */
public interface SysLogService extends BaseService<SysLog> {
    /**
     * 分页查询系统日志
     *
     * @param page
     * @param sysLog
     * @return
     */
    IPage<SysLog> list(IPage<SysLog> page, SysLog sysLog);
}
