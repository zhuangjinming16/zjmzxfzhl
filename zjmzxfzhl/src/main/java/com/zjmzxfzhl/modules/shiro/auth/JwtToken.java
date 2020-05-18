package com.zjmzxfzhl.modules.shiro.auth;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author 庄金明
 * @date 2020年3月22日
 */
public class JwtToken implements AuthenticationToken {

    private static final long serialVersionUID = 1L;
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
