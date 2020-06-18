package com.zjmzxfzhl.framework.config.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Service;

import com.zjmzxfzhl.common.util.SpringContextUtils;
import com.zjmzxfzhl.framework.config.oauth2.service.RedisClientDetailsService;
import com.zjmzxfzhl.framework.config.security.util.SecurityUtils;

/**
 * 用户验证处理
 *
 * @author 庄金明
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userDetailsServiceBeanId = "sysUserDetailsService";
        Authentication authentication = SecurityUtils.getAuthentication();
        if (authentication != null) {
            String clientId = authentication.getName();
            RedisClientDetailsService redisClientDetailsService = SpringContextUtils
                    .getBean("redisClientDetailsService");
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
