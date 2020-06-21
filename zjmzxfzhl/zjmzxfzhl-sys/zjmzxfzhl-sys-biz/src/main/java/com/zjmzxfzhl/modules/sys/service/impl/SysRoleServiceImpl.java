package com.zjmzxfzhl.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjmzxfzhl.common.core.base.BaseServiceImpl;
import com.zjmzxfzhl.common.core.exception.AppException;
import com.zjmzxfzhl.common.core.exception.SysException;
import com.zjmzxfzhl.common.core.util.CommonUtil;
import com.zjmzxfzhl.modules.sys.entity.SysFunc;
import com.zjmzxfzhl.modules.sys.entity.SysMenu;
import com.zjmzxfzhl.modules.sys.entity.SysRole;
import com.zjmzxfzhl.modules.sys.entity.SysRolePermission;
import com.zjmzxfzhl.modules.sys.entity.SysRoleUser;
import com.zjmzxfzhl.modules.sys.entity.SysUser;
import com.zjmzxfzhl.modules.sys.entity.vo.ElTree;
import com.zjmzxfzhl.modules.sys.mapper.SysFuncMapper;
import com.zjmzxfzhl.modules.sys.mapper.SysMenuMapper;
import com.zjmzxfzhl.modules.sys.mapper.SysRoleMapper;
import com.zjmzxfzhl.modules.sys.service.SysFuncService;
import com.zjmzxfzhl.modules.sys.service.SysMenuService;
import com.zjmzxfzhl.modules.sys.service.SysRolePermissionService;
import com.zjmzxfzhl.modules.sys.service.SysRoleService;
import com.zjmzxfzhl.modules.sys.service.SysRoleUserService;

/**
 * 角色Service
 * 
 * @author 庄金明
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysFuncService sysFuncService;

    @Autowired
    private SysRolePermissionService sysRolePermissionService;
    @Autowired
    private SysRoleUserService sysRoleUserService;

    @Override
    public IPage<SysRole> list(IPage<SysRole> page, SysRole sysRole) {
        return page.setRecords(baseMapper.list(page, sysRole));
    }

    /**
     * 查询角色权限
     * 
     * @param sysUser
     * @param roleId
     * @return
     */
    @Override
    public Map<String, Object> getRolePermissions(String roleId) {
        List<SysMenu> menus = ((SysMenuMapper) sysMenuService.getBaseMapper()).list(null, new SysMenu());
        List<SysFunc> funcs = ((SysFuncMapper) sysFuncService.getBaseMapper()).list(null, new SysFunc());

        Map<String, List<ElTree>> funcMap = new LinkedHashMap<String, List<ElTree>>();
        for (SysFunc sysFunc : funcs) {
            ElTree elTree = new ElTree();
            elTree.setId(sysFunc.getFuncId());
            elTree.setLabel(sysFunc.getFuncName());
            elTree.setIsLeaf(true);
            SysRolePermission rolePermission = new SysRolePermission("2", sysFunc.getFuncId(), sysFunc.getFuncName());
            elTree.setData(rolePermission);
            List<ElTree> funcsGroup = null;
            if (funcMap.containsKey(sysFunc.getMenuId())) {
                funcsGroup = funcMap.get(sysFunc.getMenuId());
            } else {
                funcsGroup = new ArrayList<>();
            }
            funcsGroup.add(elTree);
            funcMap.put(sysFunc.getMenuId(), funcsGroup);
        }

        Map<String, ElTree> menuMap = new LinkedHashMap<String, ElTree>();
        for (SysMenu sysMenu : menus) {
            ElTree elTree = new ElTree();
            elTree.setId(sysMenu.getMenuId());
            elTree.setLabel(sysMenu.getMenuName());

            SysRolePermission rolePermission = new SysRolePermission("1", sysMenu.getMenuId(), sysMenu.getMenuName());
            elTree.setData(rolePermission);

            if (funcMap.containsKey(sysMenu.getMenuId())) {
                elTree.setIsLeaf(false);
                elTree.addChildrens(funcMap.get(sysMenu.getMenuId()));
            }
            menuMap.put(sysMenu.getMenuId(), elTree);
            if (CommonUtil.isNotEmptyStr(sysMenu.getParentMenuId()) && menuMap.containsKey(sysMenu.getParentMenuId())) {
                elTree.setParentId(sysMenu.getParentMenuId());
                ElTree parentElTree = menuMap.get(sysMenu.getParentMenuId());
                parentElTree.addChildren(elTree);
            }
        }

        List<ElTree> permissionTree = new ArrayList<ElTree>();
        menuMap.forEach((k, v) -> {
            if (CommonUtil.isEmptyStr(v.getParentId())) {
                permissionTree.add(v);
            }
        });

        Map<String, Object> result = new HashMap<>(2);
        result.put("permissionTree", permissionTree);
        List<String> permissions = baseMapper.listMenuOrFuncIdsByRoleId(roleId);
        result.put("permissions", permissions);
        return result;
    }

    /**
     * 保存角色权限
     * 
     * @param roleId
     * @param menuOrFuncIds
     * @param permissionTypes
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRolePermissions(String roleId, String menuOrFuncIds, String permissionTypes) {
        String[] menuOrFuncIdArray = null;
        if (CommonUtil.isNotEmptyStr(menuOrFuncIds)) {
            menuOrFuncIdArray = menuOrFuncIds.split(",");
        }
        String[] permissionTypeArray = null;
        if (CommonUtil.isNotEmptyStr(permissionTypes)) {
            permissionTypeArray = permissionTypes.split(",");
        }

        if (menuOrFuncIdArray.length != permissionTypeArray.length) {
            throw new AppException("系统错误,参数长度不一致,请联系管理员!");
        }
        // 【1】先删除此角色的已有操作权限
        Map<String, Object> columnMap = new HashMap<>(1);
        columnMap.put("ROLE_ID", roleId);
        this.sysRolePermissionService.removeByMap(columnMap);
        // 【2】保存新的操作权限
        for (int i = 0; i < menuOrFuncIdArray.length; i++) {
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setRoleId(roleId);
            sysRolePermission.setPermissionType(permissionTypeArray[i]);
            sysRolePermission.setMenuOrFuncId(menuOrFuncIdArray[i]);
            this.sysRolePermissionService.save(sysRolePermission);
        }
    }

    /**
     * 查询角色用户
     * 
     * @param page
     * @param roleId
     * @return
     */
    @Override
    public IPage<SysUser> getRoleUser(Page<SysUser> page, SysRoleUser sysRoleUser) {
        return page.setRecords(baseMapper.getRoleUser(page, sysRoleUser));
    }

    /**
     * 保存角色用户
     * 
     * @param sysRoleUser
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRoleUsers(String roleId, String userIds) {
        String[] userIdArray = userIds.split(",");
        // 【1】先删除角色用户
        QueryWrapper<SysRoleUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ROLE_ID", roleId).in("USER_ID", (Object[]) userIdArray);
        this.sysRoleUserService.remove(queryWrapper);

        // 【2】保存角色用户
        for (int i = 0; i < userIdArray.length; i++) {
            SysRoleUser sysRoleUser = new SysRoleUser(roleId, userIdArray[i]);
            this.sysRoleUserService.save(sysRoleUser);
        }
    }

    /**
     * 删除角色用户
     * 
     * @param sysRoleUser
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoleUsers(String roleId, String userIds) {
        QueryWrapper<SysRoleUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ROLE_ID", roleId).in("USER_ID", (Object[]) userIds.split(","));
        this.sysRoleUserService.remove(queryWrapper);
    }

    /**
     * 删除角色
     * 
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String ids) {
        String[] idsArr = ids.split(",");

        for (int i = 0; i < idsArr.length; i++) {
            if ("admin".equals(idsArr[i])) {
                throw new SysException("不允许删除[admin]角色");
            }
        }

        if (idsArr.length > 1) {
            this.removeByIds(Arrays.asList(idsArr));
        } else {
            this.removeById(idsArr[0]);
        }
        this.sysRoleUserService.remove(new QueryWrapper<SysRoleUser>().in("ROLE_ID", (Object[]) idsArr));
        this.sysRolePermissionService.remove(new QueryWrapper<SysRolePermission>().in("ROLE_ID", (Object[]) idsArr));
    }
}
