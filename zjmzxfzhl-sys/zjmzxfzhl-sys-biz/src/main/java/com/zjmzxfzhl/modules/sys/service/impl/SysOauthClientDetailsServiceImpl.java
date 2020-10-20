package com.zjmzxfzhl.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.base.BaseServiceImpl;
import com.zjmzxfzhl.common.core.constant.CacheConstants;
import com.zjmzxfzhl.common.core.exception.SysException;
import com.zjmzxfzhl.common.core.util.PasswordUtil;
import com.zjmzxfzhl.modules.sys.entity.SysOauthClientDetails;
import com.zjmzxfzhl.modules.sys.mapper.SysOauthClientDetailsMapper;
import com.zjmzxfzhl.modules.sys.service.SysOauthClientDetailsService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * 应用客户端Service
 *
 * @author 庄金明
 */
@Service
public class SysOauthClientDetailsServiceImpl extends BaseServiceImpl<SysOauthClientDetailsMapper,
        SysOauthClientDetails> implements SysOauthClientDetailsService {
    @Override
    public IPage<SysOauthClientDetails> list(IPage<SysOauthClientDetails> page,
                                             SysOauthClientDetails sysOauthClientDetails) {
        return page.setRecords(baseMapper.list(page, sysOauthClientDetails));
    }

    /**
     * 通过ID删除客户端
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstants.OAUTH_CLIENT_DETAILS, allEntries = true)
    public boolean delete(String ids) {
        if (ids == null || ids.trim().length() == 0) {
            throw new SysException("ids can't be empty");
        }
        String[] idsArr = ids.split(",");
        if (idsArr.length > 1) {
            return removeByIds(Arrays.asList(idsArr));
        } else {
            return removeById(idsArr[0]);
        }
    }

    @Override
    public boolean saveSysOauthClientDetails(SysOauthClientDetails clientDetails) {
        String clientSecret = PasswordUtil.encryptPassword(clientDetails.getClientSecret());
        clientDetails.setClientSecret(clientSecret);
        return this.save(clientDetails);
    }

    /**
     * 根据客户端信息
     *
     * @param clientDetails
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = CacheConstants.OAUTH_CLIENT_DETAILS, key = "#clientDetails.clientId")
    public boolean updateSysOauthClientDetails(SysOauthClientDetails clientDetails) {
        SysOauthClientDetails aSysOauthClientDetails = this.getById(clientDetails.getClientId());
        if (!clientDetails.getClientSecret().equals(aSysOauthClientDetails.getClientSecret())) {
            String clientSecret = PasswordUtil.encryptPassword(clientDetails.getClientSecret());
            clientDetails.setClientSecret(clientSecret);
        }
        return this.updateById(clientDetails);
    }
}
