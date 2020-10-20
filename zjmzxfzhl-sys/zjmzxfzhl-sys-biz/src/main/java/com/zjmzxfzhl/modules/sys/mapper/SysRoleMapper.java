package com.zjmzxfzhl.modules.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.aspect.annotation.DataPermission;
import com.zjmzxfzhl.modules.sys.entity.SysRole;
import com.zjmzxfzhl.modules.sys.entity.SysRoleUser;
import com.zjmzxfzhl.modules.sys.entity.SysUser;
import com.zjmzxfzhl.modules.sys.permission.provider.OrgDataPermissionProvider;

/**
 * 角色Mapper
 *
 * @author 庄金明
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 查询角色列表
     *
     * @param page
     * @param entity
     * @return
     */
    public List<SysRole> list(IPage<SysRole> page, @Param("entity") SysRole entity);

    /**
     * 根据角色I点查询菜单按钮列表
     *
     * @param roleId
     * @return
     */
    public List<String> listMenuOrFuncIdsByRoleId(String roleId);

    /**
     * 查询角色用户列表
     *
     * @param page
     * @param entity
     * @return
     */
    @DataPermission(providers = OrgDataPermissionProvider.class)
    public List<SysUser> getRoleUser(IPage<SysUser> page, @Param("entity") SysRoleUser entity);
}
