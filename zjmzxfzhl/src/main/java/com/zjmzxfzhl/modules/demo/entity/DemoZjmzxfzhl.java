package com.zjmzxfzhl.modules.demo.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zjmzxfzhl.common.base.BaseEntity;
import com.zjmzxfzhl.common.validator.constraints.LengthForUTF8;

import lombok.Getter;
import lombok.Setter;

/**
 * 【开发示例】实体类
 * 
 * @author 庄金明
 */
@Getter
@Setter
@TableName("T_DEMO_ZJMZXFZHL")
public class DemoZjmzxfzhl extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@TableId
	@NotNull
	@LengthForUTF8(max = 32)
	private String zjmzxfzhlId;// ID

	@NotNull
	@LengthForUTF8(max = 32)
	private String zjmzxfzhlName;// 名称

	@LengthForUTF8(max = 1)
	private String zjmzxfzhlCodeInfo;// 代码信息

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date zjmzxfzhlDate;// 日期格式

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date zjmzxfzhlDatetime;// 时间格式

	@LengthForUTF8(max = 32)
	private String orgId;// 所属机构ID

	@LengthForUTF8(max = 3)
	private String zjmzxfzhlDbparam1;// 参数1

	@Max(999)
	private Integer zjmzxfzhlDbparam2;// 参数2

	@LengthForUTF8(max = 3)
	private String filterOperatorEq;// 等于

	@Max(999)
	private Integer filterOperatorNe;// 不等于

	@DecimalMax("99.99")
	private BigDecimal filterOperatorLt;// 小于

	@Max(999)
	private Integer filterOperatorLe;// 小于等于

	@Max(999)
	private Integer filterOperatorGt;// 大于

	@DecimalMax("99.99")
	private BigDecimal filterOperatorGe;// 大于等于

	@LengthForUTF8(max = 3)
	private String filterOperatorIn;// IN

	@Max(999)
	private Integer filterOperatorBetween;// BETWEEN

	@LengthForUTF8(max = 32)
	private String filterOperatorLike;// 模糊

	@LengthForUTF8(max = 32)
	private String filterOperatorLikeLeft;// 左模糊

	@LengthForUTF8(max = 32)
	private String filterOperatorLikeRight;// 右模糊

	@TableField(exist = false)
	private String authFilterSql; // 数据权限示例使用

	@TableField(exist = false)
	private String otherAuthFilterSql; // 数据权限示例使用

}