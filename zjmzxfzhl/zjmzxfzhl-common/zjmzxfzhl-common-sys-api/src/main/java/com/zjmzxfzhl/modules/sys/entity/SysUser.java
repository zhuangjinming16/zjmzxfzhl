package com.zjmzxfzhl.modules.sys.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zjmzxfzhl.common.core.base.BaseEntity;
import com.zjmzxfzhl.common.core.validator.constraints.LengthForUtf8;

import lombok.Data;

/**
 * 【用户】实体类
 *
 * @author 庄金明
 */
@Data
@TableName("T_SYS_USER")
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId
    @NotNull
    @LengthForUtf8(max = 32)
    @ExcelProperty("用户ID")
    private String userId;

    /**
     * 用户姓名
     */
    @NotNull
    @LengthForUtf8(max = 100)
    @ExcelProperty("用户姓名")
    private String userName;

    /**
     * 密码
     */
    @JsonIgnore
    @ExcelIgnore
    private String password;

    @JsonIgnore
    @ExcelIgnore
    private String salt;

    /**
     * 性别
     */
    @LengthForUtf8(max = 1)
    @ExcelProperty(value = "性别")
    private String sex;

    /**
     * 所属角色ID
     */
    @LengthForUtf8(max = 32)
    @ExcelProperty("角色ID")
    private String roleId;

    /**
     * 所属机构ID
     */
    @LengthForUtf8(max = 32)
    @ExcelProperty("所属机构")
    private String orgId;

    /**
     * 手机号码
     */
    @LengthForUtf8(max = 20)
    @ExcelProperty("手机号")
    private String mobile;

    /**
     * 身份证号码
     */
    @LengthForUtf8(max = 20)
    @ExcelProperty("身份证号")
    private String idCardNo;

    /**
     * 邮箱
     */
    @LengthForUtf8(max = 100)
    @ExcelProperty("email")
    private String email;

    /**
     * 用户状态
     */
    @LengthForUtf8(max = 1)
    @ExcelProperty("状态")
    private String status;

    /**
     * 排序号
     */
    @Max(999999)
    @ExcelProperty("排序号")
    private Integer sortNo;

    /**
     * 备注
     */
    @LengthForUtf8(max = 255)
    @ExcelProperty("备注")
    private String remark;

    @TableField(exist = false)
    @ExcelProperty("机构名称")
    private String orgName;
}