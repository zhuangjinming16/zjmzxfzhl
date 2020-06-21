package com.zjmzxfzhl.modules.sys.common;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zjmzxfzhl.common.security.userdetails.SecurityUser;
import com.zjmzxfzhl.modules.sys.entity.SysOrg;
import com.zjmzxfzhl.modules.sys.entity.SysRole;
import com.zjmzxfzhl.modules.sys.entity.SysUser;
import com.zjmzxfzhl.modules.sys.entity.vo.Route;

import lombok.Getter;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
public class SysSecurityUser extends SecurityUser {
    private static final long serialVersionUID = 1L;

    @Getter
    private SysUser sysUser;
    @Getter
    private SysOrg sysOrg;
    @Getter
    private SysRole sysRole;
    @Getter
    private List<SysRole> sysRoles;
    @Getter
    private List<Route> routes;
    @Getter
    private Date loginTime;
    @Getter
    private String ipAddr;

    public SysSecurityUser(SysUser sysUser, SysOrg sysOrg, SysRole sysRole, List<SysRole> sysRoles, List<Route> routes,
            Date loginTime, String ipAddr, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
            boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(sysRole.getRoleId(), sysUser.getUserId(), sysUser.getPassword(), enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities);
        this.sysUser = sysUser;
        this.sysOrg = sysOrg;
        this.sysRole = sysRole;
        this.sysRoles = sysRoles;
        this.routes = routes;
        this.loginTime = loginTime;
        this.ipAddr = ipAddr;
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
}
