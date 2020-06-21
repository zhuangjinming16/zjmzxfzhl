package com.zjmzxfzhl.modules.app.config.security.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zjmzxfzhl.common.core.util.SpringContextUtils;
import com.zjmzxfzhl.modules.app.entity.AppUser;
import com.zjmzxfzhl.modules.app.service.AppUserService;

/**
 * @author 庄金明
 * 
 */
@Service("appUserDetailsService")
public class AppUserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUserService appUserService = SpringContextUtils.getBean(AppUserService.class);
        AppUser appUser = appUserService.getById(username);
        return new User(appUser.getUserId(), appUser.getPassword(), AuthorityUtils.NO_AUTHORITIES);
    }
}
