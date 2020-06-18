package com.zjmzxfzhl.framework.config.oauth2.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * 鉴权成功监听器
 * 
 * @author 庄金明
 *
 */
@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        // Authentication aAuthentication = event.getAuthentication();
        // 这里的事件源除了登录事件（UsernamePasswordAuthenticationToken）
        // 还有可能是token验证事件源（OAuth2Authentication）
        // if (!event.getSource().getClass().getName()
        // .equals("org.springframework.security.authentication.UsernamePasswordAuthenticationToken")) {
        // return;
        // }
        // 登录日志逻辑。。。后续处理
    }
}
