package com.zjmzxfzhl.modules.sys.entity;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjmzxfzhl.common.base.BaseEntity;
import com.zjmzxfzhl.common.validator.constraints.LengthForUTF8;

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
	@LengthForUTF8(max = 32)
	private String orgId;// 机构ID

	@NotNull
	@LengthForUTF8(max = 100)
	private String orgName;// 机构名称

	@LengthForUTF8(max = 32)
	private String parentOrgId;// 上级机构ID

	@LengthForUTF8(max = 100)
	private String shortName;// 机构简称

	@LengthForUTF8(max = 4)
	private String orgType;// 机构类型

	@NotNull
	@LengthForUTF8(max = 2)
	private String orgLevel;// 机构级次

	@NotNull
	@LengthForUTF8(max = 200)
	private String orgLevelCode;// 组织机构路径

	@LengthForUTF8(max = 255)
	private String remark;// 备注

	@LengthForUTF8(max = 1)
	private String isLeaf;// 是否叶子节点

	@TableField(exist = false)
	private String parentOrgName;

	@TableField(exist = false)
	private String authFilterSql;

}