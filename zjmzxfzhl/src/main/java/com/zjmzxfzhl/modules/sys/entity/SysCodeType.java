package com.zjmzxfzhl.modules.sys.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjmzxfzhl.common.base.BaseEntity;
import com.zjmzxfzhl.common.validator.constraints.LengthForUtf8;

import lombok.Data;

/**
 * 【代码类别】实体类
 * 
 * @author 庄金明
 */
@Data
@TableName("T_SYS_CODE_TYPE")
public class SysCodeType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 代码类别ID
     */
    @TableId
    @NotNull
    @LengthForUtf8(max = 32)
    private String codeTypeId;

    /**
     * 代码类别名称
     */
    @NotNull
    @LengthForUtf8(max = 100)
    private String codeTypeName;

    /**
     * 排序号
     */
    @NotNull
    @Max(9999)
    private Integer sortNo;

    /**
     * 备注
     */
    @LengthForUtf8(max = 255)
    private String remark;

}