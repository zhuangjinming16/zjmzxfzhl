package com.zjmzxfzhl.modules.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.base.BaseService;
import com.zjmzxfzhl.modules.sys.entity.SysOrg;
import com.zjmzxfzhl.modules.sys.entity.vo.ElTree;

/**
 * 机构Service
 *
 * @author 庄金明
 */
public interface SysOrgService extends BaseService<SysOrg> {
    /**
     * 分页查询机构
     *
     * @param page
     * @param sysOrg
     * @return
     */
    IPage<SysOrg> list(IPage<SysOrg> page, SysOrg sysOrg);

    /**
     * 新增机构，自动计算机构级别、机构级次码、是否叶子
     *
     * @param sysOrg
     * @return
     */
    boolean saveSysOrg(SysOrg sysOrg);

    /**
     * 修改机构，自动计算机构级别、机构级次码、是否叶子
     *
     * @param sysOrg
     * @return
     */
    boolean updateSysOrg(SysOrg sysOrg);

    /**
     * 机构管理，机构树数据
     *
     * @return
     */
    List<ElTree> getTreeData();

    /**
     * 生成机构树
     *
     * @param orgList
     * @return
     */
    List<ElTree> makeOrgTree(List<SysOrg> orgList);
}
