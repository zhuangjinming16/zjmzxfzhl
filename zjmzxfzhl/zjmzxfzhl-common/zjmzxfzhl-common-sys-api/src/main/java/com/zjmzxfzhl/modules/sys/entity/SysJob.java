package com.zjmzxfzhl.modules.sys.entity;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjmzxfzhl.common.core.base.BaseEntity;
import com.zjmzxfzhl.common.core.validator.constraints.LengthForUtf8;

import lombok.Data;

/**
 * 【定时任务】实体类
 *
 * @author 庄金明
 */
@Data
@TableName("T_SYS_JOB")
public class SysJob extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    @LengthForUtf8(max = 32)
    private String jobId;

    /**
     * 任务名称
     */
    @NotNull
    @LengthForUtf8(max = 64)
    private String jobName;

    /**
     * 任务组名
     */
    @NotNull
    @LengthForUtf8(max = 64)
    private String jobGroup;

    /**
     * 调用目标字符串
     */
    @NotNull
    @LengthForUtf8(max = 500)
    private String invokeTarget;

    /**
     * cron执行表达式
     */
    @LengthForUtf8(max = 255)
    private String cronExpression;

    /**
     * 计划执行错误策略
     */
    @LengthForUtf8(max = 20)
    private String misfirePolicy;

    /**
     * 是否并发执行
     */
    @LengthForUtf8(max = 1)
    private String concurrent;

    /**
     * 是否正常状态
     */
    @LengthForUtf8(max = 1)
    private String status;

    /**
     * 备注
     */
    @LengthForUtf8(max = 500)
    private String remark;

}