package com.zjmzxfzhl.modules.sys.service;

import java.util.List;

import org.flowable.idm.engine.impl.GroupQueryImpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjmzxfzhl.common.core.base.BaseService;
import com.zjmzxfzhl.modules.sys.entity.SysPost;
import com.zjmzxfzhl.modules.sys.entity.SysPostUser;
import com.zjmzxfzhl.modules.sys.entity.SysUser;

/**
 * 岗位Service
 *
 * @author 庄金明
 */
public interface SysPostService extends BaseService<SysPost> {

    /**
     * 分页查询岗位
     *
     * @param page
     * @param sysPost
     * @return
     */
    IPage<SysPost> list(IPage<SysPost> page, SysPost sysPost);

    /**
     * 查询岗位用户
     *
     * @param page
     * @param sysPostUser
     * @return
     */
    IPage<SysUser> getPostUser(Page<SysUser> page, SysPostUser sysPostUser);

    /**
     * 保存岗位用户
     *
     * @param postId
     * @param userIds
     */
    void savePostUsers(String postId, String userIds);

    /**
     * 删除岗位用户
     *
     * @param postId
     * @param userIds
     */
    void deletePostUsers(String postId, String userIds);

    /**
     * 删除岗位
     *
     * @param ids
     */
    void delete(String ids);

    /**
     * 根据Flowable GroupQueryImpl查询岗位列表
     *
     * @param query
     * @return
     */
    List<SysPost> getPostsByFlowableGroupQueryImpl(GroupQueryImpl query);
}
