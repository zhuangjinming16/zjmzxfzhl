package com.zjmzxfzhl.modules.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.aspect.annotation.DataPermission;
import com.zjmzxfzhl.modules.demo.entity.DemoZjmzxfzhl;
import com.zjmzxfzhl.modules.sys.permission.provider.OrgDataPermissionProvider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    @DataPermission(providers = OrgDataPermissionProvider.class, fieldName = "authFilterSql01")
    @DataPermission(providers = OrgDataPermissionProvider.class, tableNames = "T_DEMO_ZJMZXFZHL", aliasNames = "a",
            fieldName = "authFilterSql02")
    public List<DemoZjmzxfzhl> list(IPage<DemoZjmzxfzhl> page, @Param("entity") DemoZjmzxfzhl entity);
}
