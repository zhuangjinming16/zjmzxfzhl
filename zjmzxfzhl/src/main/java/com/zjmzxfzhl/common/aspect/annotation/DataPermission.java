package com.zjmzxfzhl.common.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zjmzxfzhl.common.aspect.annotation.DataPermission.DataPermissions;
import com.zjmzxfzhl.common.permission.provider.AbstractDataPermissionProvider;

/**
 * 数据权限注解
 * 
 * @author 庄金明
 * @date 2020年3月23日
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(DataPermissions.class)
public @interface DataPermission {
    /**
     * 需要做过滤的表清单，例如 "T_SYS_USER","T_SYS_ORG"
     * 
     * @return
     */
    String[] tableNames() default {};

    /**
     * 需要做过滤的表清单别名，例如 "user","org"，应与tableNames顺序对应
     * 
     * @return
     */
    String[] aliasNames() default {};

    /**
     * 已知的数据权限，则直接使用提供器数组即可
     * 
     * @return 提供器类名数组
     */
    Class<? extends AbstractDataPermissionProvider>[] providers() default {};

    String[] providerParams() default {};

    /**
     * 组拼好的sql字符串用于替换*Mapper.xml中配置的{{authFilterSql}}，默认值为 authFilterSql
     * 
     * @return
     */
    String fieldName() default "authFilterSql";

    /**
     * 可重复注解
     * 
     * 可注解在同一方法多次，用于注入另外的对象的不同数据权限sql字符串
     * 
     */
    @Target({ ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DataPermissions {
        DataPermission[] value();
    }
}
