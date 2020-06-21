package com.zjmzxfzhl.common.security.service;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;

import com.zjmzxfzhl.common.core.util.SpringContextUtils;
import com.zjmzxfzhl.common.security.util.SecurityUtils;

/**
 * 用户验证处理
 *
 * @author 庄金明
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private JdbcClientDetailsService redisClientDetailsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userDetailsServiceBeanId = "sysUserDetailsService";
        Authentication authentication = SecurityUtils.getAuthentication();
        if (authentication != null && redisClientDetailsService != null) {
            String clientId = authentication.getName();
            ClientDetails clientDetails = redisClientDetailsService.loadClientByClientId(clientId);
            if (clientDetails != null && clientDetails.getAdditionalInformation() != null
                    && clientDetails.getAdditionalInformation().get("userDetailsService") != null
                    && !"".equals(clientDetails.getAdditionalInformation().get("userDetailsService"))) {
                userDetailsServiceBeanId = clientDetails.getAdditionalInformation().get("userDetailsService")
                        .toString();
            }
        }
        UserDetailsService realUserDetailsService = SpringContextUtils.getBean(userDetailsServiceBeanId);
        return realUserDetailsService.loadUserByUsername(username);
    }
}
