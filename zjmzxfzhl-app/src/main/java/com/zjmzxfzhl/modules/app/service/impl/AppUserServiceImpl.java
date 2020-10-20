package com.zjmzxfzhl.modules.app.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjmzxfzhl.common.core.Result;
import com.zjmzxfzhl.common.core.base.BaseServiceImpl;
import com.zjmzxfzhl.common.core.base.UserInfo;
import com.zjmzxfzhl.modules.app.entity.AppUser;
import com.zjmzxfzhl.modules.app.mapper.AppUserMapper;
import com.zjmzxfzhl.modules.app.service.AppUserService;
import com.zjmzxfzhl.common.core.base.UserInfoService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

/**
 * 用户Service
 *
 * @author 庄金明
 */
@Service
public class AppUserServiceImpl extends BaseServiceImpl<AppUserMapper, AppUser> implements AppUserService,
        UserInfoService {
    @Override
    public IPage<AppUser> list(IPage<AppUser> page, AppUser appUser) {
        return page.setRecords(baseMapper.list(page, appUser));
    }

    @Override
    public Result<UserInfo> info(String userId, String inner) {
        AppUser appUser = getById(userId);
        return Result.ok(new UserInfo(appUser.getUserId(), appUser.getUserName(), appUser.getPassword(), null, null,
                null, AuthorityUtils.NO_AUTHORITIES));
    }
}
