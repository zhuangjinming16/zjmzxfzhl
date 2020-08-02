package com.zjmzxfzhl.common.core.config.mybatis.permission.mapper;

import com.zjmzxfzhl.common.core.config.mybatis.permission.CommonDataPermissionVO;

import java.util.List;

/**
 * @author 庄金明
 */
public interface CommonDataPermissionMapper {
    /**
     * 查询数据权限信息
     *
     * @param commonDataPermissionVO
     * @return
     */
    List<CommonDataPermissionVO> selectDataPermissions(CommonDataPermissionVO commonDataPermissionVO);
}
