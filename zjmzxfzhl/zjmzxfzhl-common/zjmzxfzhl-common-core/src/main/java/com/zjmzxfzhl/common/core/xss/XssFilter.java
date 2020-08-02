package com.zjmzxfzhl.common.core.xss;

import com.zjmzxfzhl.common.core.util.DateUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
public class XssFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
        try {
            chain.doFilter(xssRequest, response);
        } finally {
            // 请求结束后，清空当前线程时间，防止下次请求继续拿到的是之前请求的时间
            DateUtil.clearNow();
        }
    }
}