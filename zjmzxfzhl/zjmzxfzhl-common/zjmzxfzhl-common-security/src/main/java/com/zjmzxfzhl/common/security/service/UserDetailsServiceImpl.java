package com.zjmzxfzhl.common.security.service;

import com.zjmzxfzhl.common.core.util.SecurityUtils;
import com.zjmzxfzhl.common.core.util.SpringContextUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户验证处理
 *
 * @author 庄金明
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * 默认code Url
     */
    public static String OAUTH_AUTHORIZE_URL = "/oauth/authorize";

    @Resource
    private JdbcClientDetailsService redisClientDetailsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userDetailsServiceBeanId = "sysUserDetailsService";
        String clientId = null;
        Authentication authentication = SecurityUtils.getAuthentication();
        if (authentication == null) {
            HttpServletRequest request =
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            if (OAUTH_AUTHORIZE_URL.equals(request.getServletPath())) {
                clientId = request.getParameter("client_id");
                if (clientId == null || clientId.isEmpty()) {
                    throw new InvalidRequestException("Missing client ID");
                }
            }
        } else {
            clientId = authentication.getName();
        }
        if (clientId != null && !clientId.isEmpty() && redisClientDetailsService != null) {
            ClientDetails clientDetails = redisClientDetailsService.loadClientByClientId(clientId);
            if (clientDetails != null && clientDetails.getAdditionalInformation() != null && clientDetails.getAdditionalInformation().get("userDetailsService") != null && !"".equals(clientDetails.getAdditionalInformation().get("userDetailsService"))) {
                userDetailsServiceBeanId =
                        clientDetails.getAdditionalInformation().get("userDetailsService").toString();
            }
        }
        UserDetailsService realUserDetailsService = SpringContextUtils.getBean(userDetailsServiceBeanId);
        return realUserDetailsService.loadUserByUsername(username);
    }
}
