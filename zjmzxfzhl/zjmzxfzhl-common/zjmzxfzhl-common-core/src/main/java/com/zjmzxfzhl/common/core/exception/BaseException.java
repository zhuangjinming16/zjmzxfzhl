package com.zjmzxfzhl.common.core.exception;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected String errCode;

    protected String errMsg;

    public BaseException() {
    }

    public BaseException(Throwable e) {
        super(e);
    }

    public BaseException(String errMsg) {
        super(errMsg);
        this.errMsg = errMsg;
    }

    public BaseException(String errMsg, Throwable e) {
        super(errMsg, e);
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
