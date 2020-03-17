package com.zjmzxfzhl.modules.sys.entity;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjmzxfzhl.common.base.BaseEntity;
import com.zjmzxfzhl.common.validator.constraints.LengthForUTF8;

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
	@LengthForUTF8(max = 32)
	private String logId;// UUID

	@LengthForUTF8(max = 2)
	private String logType;// 日志类型

	@LengthForUTF8(max = 100)
	private String logContent;// 日志内容

	@LengthForUTF8(max = 2)
	private String operateType;// 操作类型

	@LengthForUTF8(max = 32)
	private String userId;// 操作用户

	@LengthForUTF8(max = 100)
	private String userName;// 操作用户姓名

	@LengthForUTF8(max = 100)
	private String ip;// IP地址

	@LengthForUTF8(max = 255)
	private String method;// 请求方法

	@LengthForUTF8(max = 255)
	private String requestUrl;// 请求路径

	private String requestParam;// 请求参数

	@LengthForUTF8(max = 10)
	private String requestType;// 请求类型

	private String operateResult;// 操作结果

	private Long costTime;// 耗时

}