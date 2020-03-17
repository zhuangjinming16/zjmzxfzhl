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
	@LengthForUTF8(max = 32)
	private String dataPermissionId;// 数据权限ID

	@NotNull
	@LengthForUTF8(max = 100)
	private String dataPermissionName;// 数据权限名称

	@LengthForUTF8(max = 100)
	private String methodId;// 方法Id(DataPermission.methodId)

	@LengthForUTF8(max = 1)
	private String entityType;// 实体类型(1-角色2-用户)

	@LengthForUTF8(max = 32)
	private String entityId;// 实体ID(用户ID或角色ID)

	@LengthForUTF8(max = 100)
	private String tableName;// 业务表名

	@LengthForUTF8(max = 255)
	private String className;// 类名

	@LengthForUTF8(max = 100)
	private String columnName;// 属性名

	@LengthForUTF8(max = 1)
	private String sourceStrategy;// 数据源策略(1-用户录入2-系统提供算法)

	@LengthForUTF8(max = 100)
	private String operate;// 查询表达式

	@LengthForUTF8(max = 255)
	private String value;// 查询条件

	@LengthForUTF8(max = 255)
	private String sourceProvider;// 系统提供的数据源class类

	@LengthForUTF8(max = 255)
	private String sourceProviderParams;// 系统提供的数据源class类参数注入JSON格式

}