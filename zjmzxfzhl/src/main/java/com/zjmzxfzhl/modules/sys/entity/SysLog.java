package com.zjmzxfzhl.modules.sys.entity;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjmzxfzhl.common.base.BaseEntity;
import com.zjmzxfzhl.common.validator.constraints.LengthForUtf8;

import lombok.Getter;
import lombok.Setter;

/**
 * 【系统日志】实体类
 * 
 * @author 庄金明
 */
@Getter
@Setter
@TableName("T_SYS_LOG")
public class SysLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.ASSIGN_UUID)
	@NotNull
	@LengthForUtf8(max = 32)
	private String logId;

	@LengthForUtf8(max = 2)
	private String logType;

	@LengthForUtf8(max = 100)
	private String logContent;

	@LengthForUtf8(max = 2)
	private String operateType;

	@LengthForUtf8(max = 32)
	private String userId;

	@LengthForUtf8(max = 100)
	private String userName;

	@LengthForUtf8(max = 100)
	private String ip;

	@LengthForUtf8(max = 255)
	private String method;

	@LengthForUtf8(max = 255)
	private String requestUrl;

	private String requestParam;

	@LengthForUtf8(max = 10)
	private String requestType;

	private String operateResult;

	private Long costTime;

}