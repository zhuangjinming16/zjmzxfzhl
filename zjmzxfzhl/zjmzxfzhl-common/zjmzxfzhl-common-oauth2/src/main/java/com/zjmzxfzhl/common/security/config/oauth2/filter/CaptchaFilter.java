package com.zjmzxfzhl.common.security.config.oauth2.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zjmzxfzhl.common.core.Constants;
import com.zjmzxfzhl.common.core.exception.BaseException;
import com.zjmzxfzhl.common.core.util.CommonUtil;
import com.zjmzxfzhl.common.core.util.RedisUtil;
import com.zjmzxfzhl.common.security.config.oauth2.constant.Oauth2Constants;
import com.zjmzxfzhl.common.security.util.SecurityUtils;

@Component
public class CaptchaFilter extends OncePerRequestFilter {

    @Value("${captchaFilter.clients}")
    private List<String> clients;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = SecurityUtils.getAuthentication();
        String clientId = authentication != null ? authentication.getName() : null;
        // 只处理登录请求和需要验证码验证的client_id
        Boolean captchaVerifiable = clientId != null && clients.contains(clientId)
                && StringUtils.containsIgnoreCase(request.getServletPath(), Oauth2Constants.OAUTH_TOKEN_URL);
        if (captchaVerifiable) {
            validateCaptcha(request);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 验证验证码合法性
     *
     * @param request
     * @return
     */
    private void validateCaptcha(HttpServletRequest request) {
        String captcha = request.getParameter("captcha");
        String uuid = request.getParameter("uuid");
        CommonUtil.isEmptyStr(uuid, "验证码uuid不能为空");
        CommonUtil.isEmptyStr(captcha, "验证码不能为空");

        String cacheCaptcha = (String) redisUtil.get(Constants.PREFIX_USER_CAPTCHA + uuid);
        if (!captcha.equals(cacheCaptcha)) {
            throw new BaseException("验证码错误或已失效");
        }
        // 验证码验证通过后，应立即删除缓存，防止恶意暴力破解密码
        redisUtil.del(Constants.PREFIX_USER_CAPTCHA + uuid);
    }
}