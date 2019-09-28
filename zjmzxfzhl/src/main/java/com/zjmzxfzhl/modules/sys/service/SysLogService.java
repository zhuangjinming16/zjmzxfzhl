package com.zjmzxfzhl.modules.sys.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.base.BaseService;
import com.zjmzxfzhl.modules.sys.entity.SysLog;
import com.zjmzxfzhl.modules.sys.mapper.SysLogMapper;

/**
 * 系统日志Service
 * 
 * @author 庄金明
 */
@Service
public class SysLogService extends BaseService<SysLogMapper, SysLog> {
	public IPage<SysLog> list(IPage<SysLog> page, SysLog sysLog) {
		return page.setRecords(baseMapper.list(page, sysLog));
	}
}
