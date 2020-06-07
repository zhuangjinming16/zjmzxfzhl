package com.zjmzxfzhl.modules.sys.common;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zjmzxfzhl.modules.sys.entity.SysOrg;
import com.zjmzxfzhl.modules.sys.entity.SysRole;
import com.zjmzxfzhl.modules.sys.entity.SysUser;
import com.zjmzxfzhl.modules.sys.entity.vo.Route;

import lombok.Data;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Data
public class SessionObject implements UserDetails {
    private static final long serialVersionUID = 1L;
    public String token;
    private SysUser sysUser;
    private SysOrg sysOrg;
    private SysRole sysRole;
    private List<SysRole> sysRoles;
    private List<Route> routes;
    private List<String> funcIds;
    private List<String> permissions;
    private Date loginTime;
    private String ipAddr;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (permissions != null && permissions.size() > 0) {
            Collection<GrantedAuthority> authorities = AuthorityUtils
                    .createAuthorityList(permissions.toArray(new String[0]));
            return authorities;
        }
        return null;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return sysUser.getUserId();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
