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
 * 【数据权限】实体类
 * 
 * @author 庄金明
 */
@Getter
@Setter
@TableName("T_SYS_DATA_PERMISSION")
public class SysDataPermission extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.ASSIGN_UUID)
	@LengthForUtf8(max = 32)
	private String dataPermissionId;

	@NotNull
	@LengthForUtf8(max = 100)
	private String dataPermissionName;

	@LengthForUtf8(max = 100)
	private String methodId;

	@LengthForUtf8(max = 1)
	private String entityType;

	@LengthForUtf8(max = 32)
	private String entityId;

	@LengthForUtf8(max = 100)
	private String tableName;

	@LengthForUtf8(max = 255)
	private String className;

	@LengthForUtf8(max = 100)
	private String columnName;

	@LengthForUtf8(max = 1)
	private String sourceStrategy;

	@LengthForUtf8(max = 100)
	private String operate;

	@LengthForUtf8(max = 255)
	private String value;

	@LengthForUtf8(max = 255)
	private String sourceProvider;

	@LengthForUtf8(max = 255)
	private String sourceProviderParams;

}