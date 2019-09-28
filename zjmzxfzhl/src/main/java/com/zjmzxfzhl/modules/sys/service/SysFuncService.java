package com.zjmzxfzhl.modules.sys.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.base.BaseService;
import com.zjmzxfzhl.modules.sys.entity.SysFunc;
import com.zjmzxfzhl.modules.sys.mapper.SysFuncMapper;

/**
 * 功能Service
 * 
 * @author 庄金明
 */
@Service
public class SysFuncService extends BaseService<SysFuncMapper, SysFunc> {
	public IPage<SysFunc> list(IPage<SysFunc> page, SysFunc sysFunc) {
		return page.setRecords(baseMapper.list(page, sysFunc));
	}
}
