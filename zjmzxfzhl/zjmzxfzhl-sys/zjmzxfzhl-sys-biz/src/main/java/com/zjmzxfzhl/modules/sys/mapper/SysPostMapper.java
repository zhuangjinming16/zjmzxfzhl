package com.zjmzxfzhl.modules.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.flowable.idm.engine.impl.GroupQueryImpl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.aspect.annotation.DataPermission;
import com.zjmzxfzhl.modules.sys.entity.SysPost;
import com.zjmzxfzhl.modules.sys.entity.SysPostUser;
import com.zjmzxfzhl.modules.sys.entity.SysUser;
import com.zjmzxfzhl.modules.sys.permission.provider.OrgDataPermissionProvider;

/**
 * 岗位Mapper
 *
 * @author 庄金明
 */
public interface SysPostMapper extends BaseMapper<SysPost> {
    /**
     * 查询岗位列表
     *
     * @param page
     * @param entity
     * @return
     */
    public List<SysPost> list(IPage<SysPost> page, @Param("entity") SysPost entity);

    /**
     * 查询岗位用户列表
     *
     * @param page
     * @param entity
     * @return
     */
    @DataPermission(providers = OrgDataPermissionProvider.class)
    public List<SysUser> getPostUser(IPage<SysUser> page, @Param("entity") SysPostUser entity);

    /**
     * 根据Flowable GroupQueryImpl查询岗位列表
     *
     * @param query
     * @return
     */
    public List<SysPost> getPostsByFlowableGroupQueryImpl(GroupQueryImpl query);
}
