package com.zjmzxfzhl.modules.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.base.BaseService;
import com.zjmzxfzhl.modules.sys.entity.SysFunc;

/**
 * 功能Service
 * 
 * @author 庄金明
 */
public interface SysFuncService extends BaseService<SysFunc> {
    /**
     * 分页查询功能
     * 
     * @param page
     * @param sysFunc
     * @return
     */
    IPage<SysFunc> list(IPage<SysFunc> page, SysFunc sysFunc);
}
