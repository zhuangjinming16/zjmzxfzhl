package com.zjmzxfzhl.modules.sys.entity.vo;

import java.io.Serializable;

import lombok.Data;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Data
public class SysRolePermissionVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String rolePermissionId;
    private String roleId;
    private String permissionType;
    private String menuOrFuncId;
    private String menuUrl;
    private String menuPermissions;
    private String funcMenuId;
    private String funcPermissions;
}
