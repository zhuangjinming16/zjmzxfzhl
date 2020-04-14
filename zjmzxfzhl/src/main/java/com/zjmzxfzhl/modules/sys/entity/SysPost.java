package com.zjmzxfzhl.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zjmzxfzhl.common.base.BaseEntity;
import com.zjmzxfzhl.common.validator.constraints.LengthForUtf8;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 【岗位】实体类
 * 
 * @author 庄金明
 */
@Getter
@Setter
@TableName("T_SYS_POST")
public class SysPost extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 岗位ID
	 */
	@TableId
	@NotNull
	@LengthForUtf8(max = 32)
	private String postId;

	/**
	 * 岗位名称
	 */
	@NotNull
	@LengthForUtf8(max = 255)
	private String postName;

	/**
	 * 状态
	 */
	@LengthForUtf8(max = 1)
	private String postStatus;

	/**
	 * 排序号
	 */
	@Max(9999)
	private Integer sortNo;

	/**
	 * 备注
	 */
	@LengthForUtf8(max = 255)
	private String remark;

}