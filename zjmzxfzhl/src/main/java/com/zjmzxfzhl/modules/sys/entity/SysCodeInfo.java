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
 * 【代码信息】实体类
 * 
 * @author 庄金明
 */
@Getter
@Setter
@TableName("T_SYS_CODE_INFO")
public class SysCodeInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@TableId
	@NotNull
	@LengthForUTF8(max = 32)
	private String codeInfoId;// 代码信息ID

	@NotNull
	@LengthForUTF8(max = 32)
	private String codeTypeId;// 代码类别ID

	@NotNull
	@LengthForUTF8(max = 100)
	private String value;// 下拉框值

	@NotNull
	@LengthForUTF8(max = 100)
	private String content;// 下拉框内容

	@LengthForUTF8(max = 100)
	private String parentValue;// 上级联动下拉框值

	@NotNull
	@Max(9999)
	private Integer sortNo;// 排序号

	@LengthForUTF8(max = 255)
	private String remark;// 备注

	@LengthForUTF8(max = 1)
	private String isOk;// 是否显示

}