package com.zjmzxfzhl.modules.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.base.BaseService;
import com.zjmzxfzhl.modules.sys.entity.SysRole;
import com.zjmzxfzhl.modules.sys.entity.SysUser;
import com.zjmzxfzhl.modules.sys.entity.vo.Route;
import com.zjmzxfzhl.modules.sys.entity.vo.SysPasswordForm;
import com.zjmzxfzhl.modules.sys.entity.vo.SysUserInfo;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * 用户Service
 *
 * @author 庄金明
 */
public interface SysUserService extends BaseService<SysUser> {
    /**
     * 分页查询用户
     *
     * @param page
     * @param sysUser
     * @return
     */
    IPage<SysUser> list(IPage<SysUser> page, SysUser sysUser);

    /**
     * 公共选人查询
     *
     * @param page
     * @param sysUser
     * @return
     */
    IPage<SysUser> listSelectUser(IPage<SysUser> page, SysUser sysUser);

    /**
     * 根据用户id查询角色
     *
     * @param userId
     * @return
     */
    List<SysRole> getRoleByUserId(String userId);

    /**
     * 获取用户信息 ，若用户变更角色，则roleId不能为空,并且将变更后的roleId更新到T_SYS_USER表中
     *
     * @param userId
     * @param roleId
     * @return
     */
    SysUserInfo saveGetUserInfo(String userId, String roleId);

    /**
     * 加载用户按钮权限
     *
     * @param sysUser
     * @param roleId
     * @return
     */
    Collection<? extends GrantedAuthority> loadPermissions(SysUser sysUser, String roleId);

    /**
     * 加载路由信息
     *
     * @param sysUser
     * @param roleId
     * @return
     */
    List<Route> loadRoutes(SysUser sysUser, String roleId);

    /**
     * 新增用户
     *
     * @param sysUser
     * @return
     */
    boolean saveSysUser(SysUser sysUser);

    /**
     * 修改用户
     *
     * @param sysUser
     * @return
     */
    boolean updateSysUser(SysUser sysUser);

    /**
     * 删除用户
     *
     * @param ids
     */
    void delete(String ids);

    /**
     * 修改密码
     *
     * @param sysPasswordForm
     * @return
     */
    boolean updatePassword(SysPasswordForm sysPasswordForm);
}
