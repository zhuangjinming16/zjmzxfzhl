package com.zjmzxfzhl.common.core.config.mybatis.permission;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 庄金明
 * @date
 */
@Data
public class CommonDataPermissionVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色Id
     */
    private String roleId;
    /**
     * 用户Id
     */
    private String userId;

    /**
     * 数据权限配置Id
     */
    private String dataPermissionId;
    /**
     * 方法Id
     */
    private String methodId;
    /**
     * 表清单
     */
    private String[] tableNames;

    /**
     * 实体类型
     */
    private String entityType;

    /**
     * 实体ID
     */
    private String entityId;

    /**
     * 业务表名
     */
    private String tableName;

    /**
     * 类名
     */
    private String className;

    /**
     * 字段名
     */
    private String columnName;

    /**
     * 数据源策略
     */
    private String sourceStrategy;

    /**
     * 查询表达式
     */
    private String operate;

    /**
     * 查询条件
     */
    private String value;

    /**
     * 系统数据源类
     */
    private String sourceProvider;

    /**
     * 系统数据源参数
     */
    private String sourceProviderParams;
}
