package com.zjmzxfzhl.modules.flowable.entity;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjmzxfzhl.common.base.BaseEntity;
import com.zjmzxfzhl.common.validator.constraints.LengthForUTF8;

import lombok.Getter;
import lombok.Setter;

/**
 * 【流程】实体类
 * 
 * @author 庄金明
 */
@Getter
@Setter
@TableName("T_FLOWABLE_FORM")
public class FlowableForm extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@TableId
	@NotNull
	@LengthForUTF8(max = 100)
	private String formKey;// 表单key

	@NotNull
	@LengthForUTF8(max = 100)
	private String formName;// 表单名称

	private String formJson; // 表单内容
}