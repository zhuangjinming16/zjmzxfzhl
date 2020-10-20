package com.zjmzxfzhl.modules.demo.permission.provider;

import com.zjmzxfzhl.common.core.permission.provider.AbstractDataPermissionProvider;
import com.zjmzxfzhl.common.core.permission.wrapper.PermissionWrapper;

import lombok.Getter;
import lombok.Setter;

/**
 * 测试数据库后台返回provider
 *
 * @author
 */
@Getter
@Setter
public class DemoDataPermissionProvider extends AbstractDataPermissionProvider {

    /**
     * 数据库配置参数，特别注意，如果数据库传入的参数有作为查询条件，应使用SQLFilter.sqlInject(param)防止sql注入
     */
    private String alias;
    private String columnName;
    private String dbParam1;
    private int dbParam2;

    @Override
    public PermissionWrapper wrap(PermissionWrapper permissionWrapper) {
        // 测试请打开注释
        /// log.info(this.alias);
        /// log.info(this.columnName);
        /// log.info(this.dbParam1);
        /// log.info(String.valueOf(this.dbParam2));
        /// String alias = CommonUtil.isEmptyDefault(this.alias, "o");
        /// log.info(alias);
        /// String columnName = CommonUtil.isEmptyDefault(this.columnName, "columnName");
        /// log.info(columnName);
        return permissionWrapper;
    }
}
