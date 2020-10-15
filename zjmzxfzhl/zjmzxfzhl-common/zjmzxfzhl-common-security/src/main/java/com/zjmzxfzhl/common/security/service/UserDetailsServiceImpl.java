package com.zjmzxfzhl.common.security.service;

import com.zjmzxfzhl.common.core.Result;
import com.zjmzxfzhl.common.core.base.UserInfo;
import com.zjmzxfzhl.common.core.constant.SecurityConstants;
import com.zjmzxfzhl.common.core.security.SecurityUser;
import com.zjmzxfzhl.common.core.util.CommonUtil;
import com.zjmzxfzhl.common.core.util.SecurityUtils;
import com.zjmzxfzhl.common.core.util.SpringContextUtils;
import com.zjmzxfzhl.modules.sys.service.UserInfoService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户验证处理
 *
 * @author 庄金明
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    private JdbcClientDetailsService redisClientDetailsService;

    public UserDetailsServiceImpl(JdbcClientDetailsService redisClientDetailsService) {
        this.redisClientDetailsService = redisClientDetailsService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String beanId = "sysUserServiceImpl", clientId = null;
        Authentication authentication = SecurityUtils.getAuthentication();
        if (authentication != null) {
            clientId = authentication.getName();
        } else {
            HttpServletRequest request =
                    ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            if (SecurityConstants.OAUTH_AUTHORIZE_URL.equals(request.getServletPath())) {
                clientId = request.getParameter(SecurityConstants.CLIENT_ID);
                if (clientId == null || clientId.isEmpty()) {
                    throw new InvalidRequestException("Missing client ID");
                }
            }
        }
        if (CommonUtil.isNotEmptyStr(clientId)) {
            ClientDetails clientDetails = redisClientDetailsService.loadClientByClientId(clientId);
            if (clientDetails != null && clientDetails.getAdditionalInformation() != null) {
                Object remoteUserInfoServiceBeanId =
                        clientDetails.getAdditionalInformation().get(SecurityConstants.USER_INFO_SERVICE);
                if (CommonUtil.isNotEmptyObject(remoteUserInfoServiceBeanId)) {
                    beanId = remoteUserInfoServiceBeanId.toString();
                }
            }
        }
        UserInfoService userInfoService = SpringContextUtils.getBean(beanId);
        Result<UserInfo> result = userInfoService.info(username, SecurityConstants.INNER_TRUE);
        return getUserDetails(result);
    }

    /**
     * 构建userdetails
     *
     * @param result 用户信息
     * @return
     */
    private UserDetails getUserDetails(Result<UserInfo> result) {
        if (result == null || result.getData() == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        UserInfo info = result.getData();
        // 构造security用户
        return new SecurityUser(info.getRoleId(), info.getOrgId(), info.getUserName(),
                info.getAdditionalInformation(), info.getUserId(), info.getPassword(), true, true, true, true,
                info.getAuthorities() != null ? info.getAuthorities() : AuthorityUtils.NO_AUTHORITIES);
    }
}
