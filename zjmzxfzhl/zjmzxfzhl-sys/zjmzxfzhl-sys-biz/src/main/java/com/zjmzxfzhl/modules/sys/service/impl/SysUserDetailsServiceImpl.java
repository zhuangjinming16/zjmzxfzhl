package com.zjmzxfzhl.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zjmzxfzhl.common.security.userdetails.SecurityUser;
import com.zjmzxfzhl.modules.sys.service.SysUserService;

/**
 * 用户验证处理
 *
 * @author 庄金明
 */
@Service("sysUserDetailsService")
public class SysUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUser securityUser = sysUserService.saveGetUserInfo(username, null);
        return securityUser;
    }
}
