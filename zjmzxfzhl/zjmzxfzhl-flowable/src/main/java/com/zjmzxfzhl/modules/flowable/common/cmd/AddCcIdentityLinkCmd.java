package com.zjmzxfzhl.modules.flowable.common.cmd;

import com.zjmzxfzhl.modules.flowable.constant.FlowableConstant;
import com.zjmzxfzhl.modules.flowable.vo.CcToVo;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.impl.persistence.entity.CommentEntity;
import org.flowable.engine.impl.persistence.entity.CommentEntityManager;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityManager;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.impl.util.IdentityLinkUtil;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * @author 庄金明
 */
public class AddCcIdentityLinkCmd implements Command<Void>, Serializable {
    private static final long serialVersionUID = 1L;
    protected String processInstanceId;
    protected String taskId;
    protected String userId;
    protected CcToVo[] ccToVos;

    public AddCcIdentityLinkCmd(String processInstanceId, String taskId, String userId, CcToVo[] ccToVos) {
        validateParams(processInstanceId, taskId, userId, ccToVos);
        this.processInstanceId = processInstanceId;
        this.taskId = taskId;
        this.userId = userId;
        this.ccToVos = ccToVos;
    }

    protected void validateParams(String processInstanceId, String taskId, String userId, CcToVo[] ccTo) {
        if (processInstanceId == null) {
            throw new FlowableIllegalArgumentException("processInstanceId is null");
        }
        if (taskId == null) {
            throw new FlowableIllegalArgumentException("taskId is null");
        }
        if (userId == null) {
            throw new FlowableIllegalArgumentException("userId is null");
        }
        if (ccTo == null || ccTo.length == 0) {
            throw new FlowableIllegalArgumentException("ccTo is null or empty");
        }
    }

    @Override
    public Void execute(CommandContext commandContext) {
        ExecutionEntityManager executionEntityManager = CommandContextUtil.getExecutionEntityManager(commandContext);
        ExecutionEntity processInstance = executionEntityManager.findById(processInstanceId);
        if (processInstance == null) {
            throw new FlowableObjectNotFoundException("Cannot find process instance with id " + processInstanceId,
                    ExecutionEntity.class);
        }
        for (CcToVo ccTo : ccToVos) {
            IdentityLinkUtil.createProcessInstanceIdentityLink(processInstance, ccTo.getUserId(), null, FlowableConstant.CC);
        }
        this.createCcComment(commandContext);
        return null;

    }

    protected void createCcComment(CommandContext commandContext) {
        CommentEntityManager commentEntityManager = CommandContextUtil.getCommentEntityManager(commandContext);
        CommentEntity comment = (CommentEntity) commentEntityManager.create();
        comment.setProcessInstanceId(processInstanceId);
        comment.setUserId(userId);
        comment.setType(FlowableConstant.CC);
        comment.setTime(CommandContextUtil.getProcessEngineConfiguration(commandContext).getClock().getCurrentTime());
        comment.setTaskId(taskId);
        comment.setAction("AddCcTo");
        String ccToStr = StringUtils.arrayToCommaDelimitedString((Object [])ccToVos);
        comment.setMessage(ccToStr);
        comment.setFullMessage(ccToStr);
        commentEntityManager.insert(comment);
    }

}
