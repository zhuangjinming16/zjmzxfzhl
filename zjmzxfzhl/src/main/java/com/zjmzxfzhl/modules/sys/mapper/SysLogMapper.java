package com.zjmzxfzhl.modules.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.modules.sys.entity.SysLog;

/**
 * 系统日志Mapper
 * 
 * @author 庄金明
 */
public interface SysLogMapper extends BaseMapper<SysLog> {
    /**
     * 查询系统日志列表
     * 
     * @param page
     * @param entity
     * @return
     */
    public List<SysLog> list(IPage<SysLog> page, @Param("entity") SysLog entity);
}
