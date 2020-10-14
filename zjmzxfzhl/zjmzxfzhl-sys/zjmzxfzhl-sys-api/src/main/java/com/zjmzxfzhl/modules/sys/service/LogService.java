package com.zjmzxfzhl.modules.sys.service;

import com.zjmzxfzhl.common.core.Result;
import com.zjmzxfzhl.modules.sys.entity.SysLog;

public interface LogService {
    Result save(SysLog sysLog, String inner);
}
