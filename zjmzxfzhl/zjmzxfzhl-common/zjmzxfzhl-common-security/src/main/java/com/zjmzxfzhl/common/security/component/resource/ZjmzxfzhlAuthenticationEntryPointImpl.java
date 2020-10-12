package com.zjmzxfzhl.common.security.component.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjmzxfzhl.common.core.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author 庄金明
 */
public class ZjmzxfzhlAuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = 1L;

    private ObjectMapper objectMapper;

    public ZjmzxfzhlAuthenticationEntryPointImpl(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        try {
            int code = HttpStatus.UNAUTHORIZED.value();
            String msg = String.format("请求访问：%s，认证失败，无法访问系统资源", request.getRequestURI());
            String data = null;
            if (e != null) {
                data = e.getMessage();
            }
            response.setStatus(code);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(objectMapper.writeValueAsString(Result.error(code, msg, data)));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
