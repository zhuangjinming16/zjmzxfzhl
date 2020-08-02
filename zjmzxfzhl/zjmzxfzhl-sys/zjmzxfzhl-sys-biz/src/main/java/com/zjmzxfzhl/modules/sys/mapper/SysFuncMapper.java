package com.zjmzxfzhl.modules.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.modules.sys.entity.SysFunc;

/**
 * 功能Mapper
 *
 * @author 庄金明
 */
public interface SysFuncMapper extends BaseMapper<SysFunc> {
    /**
     * 查询功能列表
     *
     * @param page
     * @param entity
     * @return
     */
    public List<SysFunc> list(IPage<SysFunc> page, @Param("entity") SysFunc entity);
}
