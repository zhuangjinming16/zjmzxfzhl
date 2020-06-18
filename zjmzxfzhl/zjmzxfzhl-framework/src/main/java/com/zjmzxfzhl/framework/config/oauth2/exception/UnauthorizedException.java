package com.zjmzxfzhl.framework.config.oauth2.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = ZjmzxfzhlAuth2ExceptionSerializer.class)
public class UnauthorizedException extends ZjmzxfzhlAuth2Exception {
    private static final long serialVersionUID = 1L;

    public UnauthorizedException(String msg, Throwable t) {
        super(msg);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "unauthorized";
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.UNAUTHORIZED.value();
    }

}
