package com.zjmzxfzhl.framework.config.security;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.zjmzxfzhl.common.Result;
import com.zjmzxfzhl.common.util.CommonUtil;
import com.zjmzxfzhl.common.util.JacksonUtil;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException {
        int code = 401;
        String msg = CommonUtil.format("请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI());
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(JacksonUtil.objToStr(Result.error(code, msg)));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
