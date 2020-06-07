package com.zjmzxfzhl.framework.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.zjmzxfzhl.common.Constants;
import com.zjmzxfzhl.common.Result;
import com.zjmzxfzhl.common.util.JwtUtil;
import com.zjmzxfzhl.common.util.RedisUtil;
import com.zjmzxfzhl.framework.config.security.util.SecurityUtils;

@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 退出处理
     * 
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String token = SecurityUtils.resolveToken(request);
        if (token != null && !token.isEmpty()) {
            String userId = JwtUtil.getUserId(token);
            // 清空用户Token缓存
            redisUtil.del(Constants.PREFIX_USER_TOKEN + userId);
            // 清空用户sessionObject缓存
            redisUtil.del(Constants.PREFIX_USER_SESSION_OBJECT + userId);
        }
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(Result.ok("注销成功"));
    }
}
