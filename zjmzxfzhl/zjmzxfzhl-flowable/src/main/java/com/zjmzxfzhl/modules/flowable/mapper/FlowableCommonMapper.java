package com.zjmzxfzhl.modules.flowable.mapper;

import com.zjmzxfzhl.modules.flowable.vo.ProcessDefinitionVo;
import com.zjmzxfzhl.modules.flowable.vo.query.ProcessInstanceQueryVo;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.flowable.idm.engine.impl.GroupQueryImpl;
import org.flowable.idm.engine.impl.UserQueryImpl;

import java.util.List;

/**
 * 流程公共查询Mapper，用于解耦系统sys模块
 *
 * @author 庄金明
 */
public interface FlowableCommonMapper {
    /**
     * 查询用户信息
     *
     * @param query
     * @return
     */
    List<User> selectUserByQueryCriteria(UserQueryImpl query);

    /**
     * 查询用户信息
     *
     * @param query
     * @return
     */
    long selectUserCountByQueryCriteria(UserQueryImpl query);

    /**
     * 根据Flowable GroupQueryImpl查询岗位列表
     *
     * @param query
     * @return
     */
    List<Group> selectGroupByQueryCriteria(GroupQueryImpl query);

    /**
     * 根据Flowable GroupQueryImpl查询岗位数量
     *
     * @param query
     * @return
     */
    long selectGroupCountByQueryCriteria(GroupQueryImpl query);

    /**
     * 查询我的流程汇总信息
     * @param processInstanceQueryVo
     * @return
     */
    List<ProcessDefinitionVo> listMyInvolvedSummary(ProcessInstanceQueryVo processInstanceQueryVo);
}
