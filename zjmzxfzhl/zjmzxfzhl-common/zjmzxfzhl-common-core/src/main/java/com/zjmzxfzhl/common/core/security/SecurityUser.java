package com.zjmzxfzhl.common.core.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Map;

/**
 * 扩展用户信息
 *
 * @author 庄金明
 */
public class SecurityUser extends User {
    private static final long serialVersionUID = 1L;

    @Getter
    private String roleId;
    @Getter
    private String orgId;
    @Getter
    private String userRealName;
    @Getter
    private Map<String, Object> additionalInformation;
    /// @Getter
    /// private String clientId;

    public SecurityUser(String roleId, String orgId, String userRealName, Map<String, Object> additionalInformation,
                        String username, String password, boolean enabled, boolean accountNonExpired,
                        boolean credentialsNonExpired, boolean accountNonLocked, Collection<?
            extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.roleId = roleId;
        this.orgId = orgId;
        this.userRealName = userRealName;
        this.additionalInformation = additionalInformation;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return super.isAccountNonExpired();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return super.isAccountNonLocked();
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return super.isCredentialsNonExpired();
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }
}
