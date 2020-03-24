package com.zjmzxfzhl.modules.sys.entity;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjmzxfzhl.common.base.BaseEntity;
import com.zjmzxfzhl.common.validator.constraints.LengthForUtf8;

import lombok.Getter;
import lombok.Setter;

/**
 * 【机构】实体类
 * 
 * @author 庄金明
 */
@Getter
@Setter
@TableName("T_SYS_ORG")
public class SysOrg extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@TableId
	@NotNull
	@LengthForUtf8(max = 32)
	private String orgId;

	@NotNull
	@LengthForUtf8(max = 100)
	private String orgName;

	@LengthForUtf8(max = 32)
	private String parentOrgId;

	@LengthForUtf8(max = 100)
	private String shortName;

	@LengthForUtf8(max = 4)
	private String orgType;

	@NotNull
	@LengthForUtf8(max = 2)
	private String orgLevel;

	@NotNull
	@LengthForUtf8(max = 200)
	private String orgLevelCode;

	@LengthForUtf8(max = 255)
	private String remark;

	@LengthForUtf8(max = 1)
	private String isLeaf;

	@TableField(exist = false)
	private String parentOrgName;

	@TableField(exist = false)
	private String authFilterSql;

}