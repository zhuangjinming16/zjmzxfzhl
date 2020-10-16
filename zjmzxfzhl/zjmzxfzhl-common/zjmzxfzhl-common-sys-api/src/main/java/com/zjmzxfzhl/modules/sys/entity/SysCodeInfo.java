package com.zjmzxfzhl.modules.sys.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjmzxfzhl.common.core.base.BaseEntity;
import com.zjmzxfzhl.common.core.validator.constraints.LengthForUtf8;

import lombok.Data;

/**
 * 【代码信息】实体类
 *
 * @author 庄金明
 */
@Data
@TableName("T_SYS_CODE_INFO")
public class SysCodeInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 代码信息ID
     */
    @TableId
    @NotNull
    @LengthForUtf8(max = 32)
    private String codeInfoId;

    /**
     * 代码类别ID
     */
    @NotNull
    @LengthForUtf8(max = 32)
    private String codeTypeId;

    /**
     * 下拉框值
     */
    @NotNull
    @LengthForUtf8(max = 100)
    private String value;

    /**
     * 下拉框内容
     */
    @NotNull
    @LengthForUtf8(max = 100)
    private String content;

    /**
     * 上级联动下拉框值
     */
    @LengthForUtf8(max = 100)
    private String parentValue;

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

    /**
     * 是否显示
     */
    @LengthForUtf8(max = 1)
    private String isOk;

}