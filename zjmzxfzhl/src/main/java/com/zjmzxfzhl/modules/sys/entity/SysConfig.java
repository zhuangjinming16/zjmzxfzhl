package com.zjmzxfzhl.modules.sys.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjmzxfzhl.common.base.BaseEntity;
import com.zjmzxfzhl.common.validator.constraints.LengthForUTF8;

import lombok.Getter;
import lombok.Setter;

/**
 * 【系统参数】实体类
 * 
 * @author 庄金明
 */
@Getter
@Setter
@TableName("T_SYS_CONFIG")
public class SysConfig extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@TableId
	@NotNull
	@LengthForUTF8(max = 32)
	private String configId;// 系统参数ID

	@NotNull
	@LengthForUTF8(max = 100)
	private String configName;// 系统参数名称

	@NotNull
	@LengthForUTF8(max = 255)
	private String configValue;// 系统参数值

	@NotNull
	@Max(9999)
	private Integer sortNo;// 排序号

	@LengthForUTF8(max = 255)
	private String remark;// 备注

}