package com.zjmzxfzhl.modules.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.base.BaseService;
import com.zjmzxfzhl.modules.sys.entity.SysOauthClientDetails;

/**
 * 应用客户端Service
 *
 * @author 庄金明
 */
public interface SysOauthClientDetailsService extends BaseService<SysOauthClientDetails> {
    /**
     * 分页查询应用客户端
     *
     * @param page
     * @param sysOauthClientDetails
     * @return
     */
    IPage<SysOauthClientDetails> list(IPage<SysOauthClientDetails> page, SysOauthClientDetails sysOauthClientDetails);

    /**
     * 删除客户端信息
     *
     * @param id
     * @return
     */
    boolean delete(String ids);

    /**
     * 新增客户端信息
     *
     * @param sysOauthClientDetails
     * @return
     */
    boolean saveSysOauthClientDetails(SysOauthClientDetails sysOauthClientDetails);

    /**
     * 更新客户端信息
     *
     * @param sysOauthClientDetails
     * @return
     */
    boolean updateSysOauthClientDetails(SysOauthClientDetails sysOauthClientDetails);
}
