package com.zjmzxfzhl.common.permission.provider;

import com.zjmzxfzhl.common.permission.FilterGroup;
import com.zjmzxfzhl.modules.sys.common.SessionObject;

/**
 * 空的DataPermissionProvider，可以用于测试
 * 
 * @author 庄金明
 *
 */
public class NullDataPermissionProvider extends AbstractDataPermissionProvider {
    @Override
    public FilterGroup filter(SessionObject sessionObject) {
        return null;
    }
}
