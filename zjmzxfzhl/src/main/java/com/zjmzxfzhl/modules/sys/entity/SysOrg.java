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

	/**
	 * 机构ID
	 */
	@TableId
	@NotNull
	@LengthForUtf8(max = 32)
	private String orgId;

	/**
	 * 机构名称
	 */
	@NotNull
	@LengthForUtf8(max = 100)
	private String orgName;

	/**
	 * 上级机构ID
	 */
	@LengthForUtf8(max = 32)
	private String parentOrgId;

	/**
	 * 机构简称
	 */
	@LengthForUtf8(max = 100)
	private String shortName;

	/**
	 * 机构类型
	 */
	@LengthForUtf8(max = 4)
	private String orgType;

	/**
	 * 机构级次
	 */
	@NotNull
	@LengthForUtf8(max = 2)
	private String orgLevel;

	/**
	 * 组织机构路径
	 */
	@NotNull
	@LengthForUtf8(max = 200)
	private String orgLevelCode;

	/**
	 * 备注
	 */
	@LengthForUtf8(max = 255)
	private String remark;

	/**
	 * 是否叶子节点
	 */
	@LengthForUtf8(max = 1)
	private String isLeaf;

	@TableField(exist = false)
	private String parentOrgName;

	@TableField(exist = false)
	private String authFilterSql;

}