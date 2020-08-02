package com.zjmzxfzhl.common.security.config.oauth2.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author 庄金明
 */
@JsonSerialize(using = ZjmzxfzhlAuth2ExceptionSerializer.class)
public class ForbiddenException extends ZjmzxfzhlAuth2Exception {
    private static final long serialVersionUID = 1L;

    public ForbiddenException(String msg, Throwable t) {
        super(msg);
    }

    @Override
    public String getOAuth2ErrorCode() {
        return "access_denied";
    }

    @Override
    public int getHttpErrorCode() {
        return HttpStatus.FORBIDDEN.value();
    }

}
