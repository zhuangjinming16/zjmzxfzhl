package com.zjmzxfzhl.modules.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.aspect.annotation.DataPermission;
import com.zjmzxfzhl.modules.demo.entity.DemoZjmzxfzhl;

/**
 * 开发示例Mapper
 * 
 * @author 庄金明
 */
public interface DemoZjmzxfzhlMapper extends BaseMapper<DemoZjmzxfzhl> {
    /**
     * 查询开发示例列表
     * 
     * @param page
     * @param entity
     * @return
     */
    @DataPermission(tableNames = "T_DEMO_ZJMZXFZHL", aliasNames = "a")
    public List<DemoZjmzxfzhl> list(IPage<DemoZjmzxfzhl> page, @Param("entity") DemoZjmzxfzhl entity);
}
