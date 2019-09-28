package com.zjmzxfzhl.modules.sys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.modules.sys.entity.SysMenu;
import com.zjmzxfzhl.modules.sys.entity.SysRole;
import com.zjmzxfzhl.modules.sys.entity.SysUser;
import com.zjmzxfzhl.modules.sys.entity.vo.SysRolePermissionVO;

/**
 * 用户Mapper
 * 
 * @author 庄金明
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
	public List<SysUser> list(IPage<SysUser> page, @Param("entity") SysUser entity);

	public List<SysRole> getRolesByUserId(@Param("userId") String userId);

	public List<SysRolePermissionVO> getAdminPermissions();

	public List<SysRolePermissionVO> getRolePermissions(@Param("roleId") String roleId);

	public List<SysMenu> getRoleMenus(@Param("roleId") String roleId);
}
