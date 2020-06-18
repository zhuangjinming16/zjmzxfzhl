package com.zjmzxfzhl.framework.config.oauth2.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = ZjmzxfzhlAuth2ExceptionSerializer.class)
public class InvalidException extends ZjmzxfzhlAuth2Exception {
    private static final long serialVersionUID = 1L;

    public InvalidException(String msg, Throwable t) {
        super(msg);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "invalid_exception";
    }

    @Override
    public int getHttpErrorCode() {
        return 426;
    }

}
