package com.zjmzxfzhl.common.core;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 庄金明
 * @date 2020年3月23日
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private static Integer SC_INTERNAL_SERVER_ERROR = 500;
    private static Integer SC_OK = 200;

    private Integer code = 0;
    private String msg = "操作成功";

    private T data;

    public Result() {
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(String msg) {
        this.code = SC_OK;
        this.msg = msg;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result error(String msg) {
        return error(SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static Result error(int code, String msg) {
        if (msg == null || msg.length() == 0) {
            msg = "交易执行失败";
        }
        Result r = new Result(code, msg);
        return r;
    }

    public static <T> Result error(int code, String msg, T data) {
        if (msg == null || msg.length() == 0) {
            msg = "交易执行失败";
        }
        Result r = new Result(code, msg, data);
        return r;
    }

    public static Result ok() {
        Result r = new Result();
        r.setCode(SC_OK);
        return r;
    }

    public static Result ok(String msg) {
        Result r = new Result(msg);
        return r;
    }

    public static <T> Result<T> ok(T data) {
        Result r = new Result();
        r.setCode(SC_OK);
        r.setData(data);
        return r;
    }

    public static <T> Result ok(String msg, T data) {
        Result r = new Result();
        r.setCode(SC_OK);
        if (msg != null && msg.length() != 0) {
            r.setMsg(msg);
        }
        r.setData(data);
        return r;
    }
}
