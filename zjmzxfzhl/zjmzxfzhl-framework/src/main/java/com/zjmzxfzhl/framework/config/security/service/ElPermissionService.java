package com.zjmzxfzhl.framework.config.security.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.zjmzxfzhl.framework.config.security.util.SecurityUtils;

/**
 * @author 庄金明
 *
 */
@Service(value = "elp")
public class ElPermissionService {

    public Boolean single(String permission) {
        List<String> elPermissions = SecurityUtils.getSessionObject().getPermissions();
        return elPermissions.contains(permission);
    }

    public Boolean or(String permissions) {
        List<String> elPermissions = SecurityUtils.getSessionObject().getPermissions();
        return Arrays.stream(permissions.split(",")).anyMatch(elPermissions::contains);
    }

    public Boolean and(String permissions) {
        List<String> elPermissions = SecurityUtils.getSessionObject().getPermissions();
        return Arrays.stream(permissions.split(",")).allMatch(elPermissions::contains);
    }
}
