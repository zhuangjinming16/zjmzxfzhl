package com.zjmzxfzhl.common.security.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.zjmzxfzhl.common.security.util.SecurityUtils;

/**
 * @author 庄金明
 *
 */
@Service(value = "elp")
public class ElPermissionService {

    public Boolean single(String permission) {
        Collection<? extends GrantedAuthority> authorities = SecurityUtils.getUserDetails().getAuthorities();
        return authorities.stream().anyMatch(authority -> permission.equals(authority.getAuthority()));
    }

    public Boolean or(String permissions) {
        Set<String> elPermissions = SecurityUtils.getUserDetails().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        return Arrays.stream(permissions.split(",")).anyMatch(elPermissions::contains);
    }

    public Boolean and(String permissions) {
        Set<String> elPermissions = SecurityUtils.getUserDetails().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        return Arrays.stream(permissions.split(",")).allMatch(elPermissions::contains);
    }
}
