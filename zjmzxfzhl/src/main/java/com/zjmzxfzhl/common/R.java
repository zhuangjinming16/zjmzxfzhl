package com.zjmzxfzhl.common;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 统一结果返回类
 * 
 * @author 庄金明
 *
 */
@Data
@ApiModel(value = "返回说明")
public class R implements Serializable {
	private static final long serialVersionUID = 1L;

	private static Integer SC_INTERNAL_SERVER_ERROR = 500;
	private static Integer SC_OK = 200;

	@ApiModelProperty(value = "返回状态码；200:成功")
	private Integer code = 0;
	@ApiModelProperty(value = "返回信息")
	private String msg = "操作成功";

	@ApiModelProperty(value = "返回数据")
	private Object data;

	public R() {
	}

	public R(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public R(String msg) {
		this.code = SC_OK;
		this.msg = msg;
	}

	public R(Integer code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public static R error(String msg) {
		return error(SC_INTERNAL_SERVER_ERROR, msg);
	}

	public static R error(int code, String msg) {
		if (msg == null || msg.length() == 0) {
			msg = "交易执行失败";
		}
		R r = new R(code, msg);
		return r;
	}

	public static R error(int code, String msg, Object data) {
		if (msg == null || msg.length() == 0) {
			msg = "交易执行失败";
		}
		R r = new R(code, msg, data);
		return r;
	}

	public static R ok() {
		R r = new R();
		r.setCode(SC_OK);
		return r;
	}

	public static R ok(String msg) {
		R r = new R(msg);
		return r;
	}

	public static R ok(Object data) {
		R r = new R();
		r.setCode(SC_OK);
		r.setData(data);
		return r;
	}

	public static R ok(String msg, Object data) {
		R r = new R();
		r.setCode(SC_OK);
		if (msg != null && msg.length() != 0) {
			r.setMsg(msg);
		}
		r.setData(data);
		return r;
	}
}
