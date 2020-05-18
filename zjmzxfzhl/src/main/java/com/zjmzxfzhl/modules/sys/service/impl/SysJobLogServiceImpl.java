package com.zjmzxfzhl.modules.sys.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.base.BaseServiceImpl;
import com.zjmzxfzhl.modules.sys.entity.SysJobLog;
import com.zjmzxfzhl.modules.sys.mapper.SysJobLogMapper;
import com.zjmzxfzhl.modules.sys.service.SysJobLogService;

/**
 * 定时任务执行日志Service
 * 
 * @author 庄金明
 */
@Service
public class SysJobLogServiceImpl extends BaseServiceImpl<SysJobLogMapper, SysJobLog> implements SysJobLogService {
    @Override
    public IPage<SysJobLog> list(IPage<SysJobLog> page, SysJobLog sysJobLog) {
        return page.setRecords(baseMapper.list(page, sysJobLog));
    }
}
