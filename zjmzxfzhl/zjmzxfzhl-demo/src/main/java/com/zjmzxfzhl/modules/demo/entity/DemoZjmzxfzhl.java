package com.zjmzxfzhl.modules.demo.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zjmzxfzhl.common.base.BaseEntity;
import com.zjmzxfzhl.common.validator.constraints.LengthForUtf8;

import lombok.Data;

/**
 * 【开发示例】实体类
 * 
 * @author 庄金明
 */
@Data
@TableName("T_DEMO_ZJMZXFZHL")
public class DemoZjmzxfzhl extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId
    @NotNull
    @LengthForUtf8(max = 32)
    private String zjmzxfzhlId;

    @NotNull
    @LengthForUtf8(max = 32)
    private String zjmzxfzhlName;

    @LengthForUtf8(max = 1)
    private String zjmzxfzhlCodeInfo;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date zjmzxfzhlDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date zjmzxfzhlDatetime;

    @LengthForUtf8(max = 32)
    private String orgId;

    @LengthForUtf8(max = 3)
    private String zjmzxfzhlDbparam1;

    @Max(999)
    private Integer zjmzxfzhlDbparam2;

    @LengthForUtf8(max = 3)
    private String filterOperatorEq;

    @Max(999)
    private Integer filterOperatorNe;

    @DecimalMax("99.99")
    private BigDecimal filterOperatorLt;

    @Max(999)
    private Integer filterOperatorLe;

    @Max(999)
    private Integer filterOperatorGt;

    @DecimalMax("99.99")
    private BigDecimal filterOperatorGe;

    @LengthForUtf8(max = 3)
    private String filterOperatorIn;

    @Max(999)
    private Integer filterOperatorBetween;

    @LengthForUtf8(max = 32)
    private String filterOperatorLike;

    @LengthForUtf8(max = 32)
    private String filterOperatorLikeLeft;

    @LengthForUtf8(max = 32)
    private String filterOperatorLikeRight;
}