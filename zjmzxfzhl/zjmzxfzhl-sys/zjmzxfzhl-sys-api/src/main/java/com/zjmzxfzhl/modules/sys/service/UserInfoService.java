package com.zjmzxfzhl.modules.sys.service;

import com.zjmzxfzhl.common.core.Result;
import com.zjmzxfzhl.common.core.base.UserInfo;

public interface UserInfoService {
    Result<UserInfo> info(String userId, String inner);
}
