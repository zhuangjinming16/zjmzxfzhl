package com.zjmzxfzhl.modules.sys.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.base.BaseServiceImpl;
import com.zjmzxfzhl.modules.sys.entity.SysFunc;
import com.zjmzxfzhl.modules.sys.mapper.SysFuncMapper;
import com.zjmzxfzhl.modules.sys.service.SysFuncService;

/**
 * 功能Service
 * 
 * @author 庄金明
 */
@Service
public class SysFuncServiceImpl extends BaseServiceImpl<SysFuncMapper, SysFunc> implements SysFuncService {
    @Override
    public IPage<SysFunc> list(IPage<SysFunc> page, SysFunc sysFunc) {
        return page.setRecords(baseMapper.list(page, sysFunc));
    }
}
