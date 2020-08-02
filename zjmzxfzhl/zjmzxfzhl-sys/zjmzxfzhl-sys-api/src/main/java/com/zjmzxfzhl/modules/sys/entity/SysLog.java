package com.zjmzxfzhl.modules.sys.entity;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjmzxfzhl.common.core.base.BaseEntity;
import com.zjmzxfzhl.common.core.validator.constraints.LengthForUtf8;

import lombok.Data;

/**
 * 【系统日志】实体类
 *
 * @author 庄金明
 */
@Data
@TableName("T_SYS_LOG")
public class SysLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * UUID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    @LengthForUtf8(max = 32)
    private String logId;

    /**
     * 日志类型
     */
    @NotNull
    @LengthForUtf8(max = 2)
    private String logType;

    /**
     * 日志内容
     */
    @NotNull
    @LengthForUtf8(max = 100)
    private String logContent;

    /**
     * 操作类型
     */
    @LengthForUtf8(max = 2)
    private String operateType;

    /**
     * 操作用户
     */
    @LengthForUtf8(max = 32)
    private String userId;

    /**
     * IP地址
     */
    @LengthForUtf8(max = 100)
    private String ip;

    /**
     * 请求方法
     */
    @LengthForUtf8(max = 255)
    private String method;

    /**
     * userAgent
     */
    @LengthForUtf8(max = 255)
    private String userAgent;

    /**
     * clientId
     */
    @LengthForUtf8(max = 255)
    private String clientId;

    /**
     * 请求路径
     */
    @LengthForUtf8(max = 255)
    private String requestUrl;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 请求类型
     */
    @LengthForUtf8(max = 10)
    private String requestType;

    /**
     * 操作结果
     */
    private String operateResult;

    /**
     * 耗时
     */
    private Long costTime;

}