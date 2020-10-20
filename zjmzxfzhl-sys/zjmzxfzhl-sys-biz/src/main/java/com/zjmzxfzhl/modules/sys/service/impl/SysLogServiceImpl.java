package com.zjmzxfzhl.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.Result;
import com.zjmzxfzhl.common.core.base.BaseServiceImpl;
import com.zjmzxfzhl.common.core.base.LogInfo;
import com.zjmzxfzhl.common.core.util.DateUtil;
import com.zjmzxfzhl.common.core.util.SecurityUtils;
import com.zjmzxfzhl.modules.sys.entity.SysLog;
import com.zjmzxfzhl.modules.sys.mapper.SysLogMapper;
import com.zjmzxfzhl.common.core.base.LogService;
import com.zjmzxfzhl.modules.sys.service.SysLogService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 系统日志Service
 *
 * @author 庄金明
 */
@Service
public class SysLogServiceImpl extends BaseServiceImpl<SysLogMapper, SysLog> implements SysLogService, LogService {
    @Override
    public IPage<SysLog> list(IPage<SysLog> page, SysLog sysLog) {
        return page.setRecords(baseMapper.list(page, sysLog));
    }

    @Override
    public Result save(LogInfo logInfo, String inner) {
        SysLog sysLog = new SysLog();
        BeanUtils.copyProperties(logInfo, sysLog);
        sysLog.setCreateTime(DateUtil.getNow());
        sysLog.setCreateTime(DateUtil.getNow());
        sysLog.setCreateBy(logInfo.getUserId());
        save(sysLog);
        return Result.ok();
    }
}
