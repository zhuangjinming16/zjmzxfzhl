package com.zjmzxfzhl.modules.sys.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.base.BaseServiceImpl;
import com.zjmzxfzhl.modules.sys.entity.SysPostUser;
import com.zjmzxfzhl.modules.sys.mapper.SysPostUserMapper;
import com.zjmzxfzhl.modules.sys.service.SysPostUserService;

/**
 * 岗位和用户关系Service
 *
 * @author 庄金明
 */
@Service
public class SysPostUserServiceImpl extends BaseServiceImpl<SysPostUserMapper, SysPostUser> implements SysPostUserService {
    @Override
    public IPage<SysPostUser> list(IPage<SysPostUser> page, SysPostUser sysPostUser) {
        return page.setRecords(baseMapper.list(page, sysPostUser));
    }
}
