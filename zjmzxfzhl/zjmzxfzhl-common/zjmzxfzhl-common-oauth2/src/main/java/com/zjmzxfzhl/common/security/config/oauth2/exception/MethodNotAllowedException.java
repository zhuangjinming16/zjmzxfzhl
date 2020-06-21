package com.zjmzxfzhl.common.security.config.oauth2.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = ZjmzxfzhlAuth2ExceptionSerializer.class)
public class MethodNotAllowedException extends ZjmzxfzhlAuth2Exception {
    private static final long serialVersionUID = 1L;

    public MethodNotAllowedException(String msg, Throwable t) {
        super(msg);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "method_not_allowed";
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.METHOD_NOT_ALLOWED.value();
    }

}
