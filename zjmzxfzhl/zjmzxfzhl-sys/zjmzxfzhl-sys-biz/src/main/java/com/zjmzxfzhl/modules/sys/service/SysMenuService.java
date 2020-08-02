package com.zjmzxfzhl.modules.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.base.BaseService;
import com.zjmzxfzhl.modules.sys.entity.SysMenu;
import com.zjmzxfzhl.modules.sys.entity.vo.ElTree;

/**
 * 菜单Service
 *
 * @author 庄金明
 */
public interface SysMenuService extends BaseService<SysMenu> {
    /**
     * 分页查询菜单
     *
     * @param page
     * @param sysMenu
     * @return
     */
    IPage<SysMenu> list(IPage<SysMenu> page, SysMenu sysMenu);

    /**
     * 新增菜单，自动计算是否叶子
     *
     * @param sysMenu
     * @return
     */
    boolean saveSysMenu(SysMenu sysMenu);

    /**
     * 修改菜单
     *
     * @param sysMenu
     * @return
     */
    boolean updateSysMenu(SysMenu sysMenu);

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    boolean delete(String id);

    /**
     * 菜单管理，菜单树数据
     *
     * @return
     */
    List<ElTree> getTreeData();
}
