package com.zjmzxfzhl.common.exception;

public class SysException extends BaseException {

	private static final long serialVersionUID = 1L;

	public SysException() {
	}

	public SysException(Throwable cause) {
		super(cause);
	}

	public SysException(String errMsg) {
		super(errMsg);
	}

	public SysException(String errMsg, Throwable cause) {
		super(errMsg, cause);
	}

	public SysException(String errCode, String errMsg, Throwable cause) {
		super(errMsg, cause);
		this.errCode = errCode;
	}
}
