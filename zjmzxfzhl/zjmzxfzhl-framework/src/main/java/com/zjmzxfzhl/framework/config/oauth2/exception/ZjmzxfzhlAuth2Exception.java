package com.zjmzxfzhl.framework.config.oauth2.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;

@JsonSerialize(using = ZjmzxfzhlAuth2ExceptionSerializer.class)
public class ZjmzxfzhlAuth2Exception extends OAuth2Exception {
    private static final long serialVersionUID = 1L;
    @Getter
    private String errorCode;

    public ZjmzxfzhlAuth2Exception(String msg) {
        super(msg);
    }

    public ZjmzxfzhlAuth2Exception(String msg, String errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }
}
