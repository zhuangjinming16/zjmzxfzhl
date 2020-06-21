package com.zjmzxfzhl.modules.app.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.base.BaseServiceImpl;
import com.zjmzxfzhl.modules.app.entity.AppUser;
import com.zjmzxfzhl.modules.app.mapper.AppUserMapper;
import com.zjmzxfzhl.modules.app.service.AppUserService;

/**
 * 用户Service
 * 
 * @author 庄金明
 */
@Service
public class AppUserServiceImpl extends BaseServiceImpl<AppUserMapper, AppUser> implements AppUserService {
    @Override
    public IPage<AppUser> list(IPage<AppUser> page, AppUser appUser) {
        return page.setRecords(baseMapper.list(page, appUser));
    }
}
