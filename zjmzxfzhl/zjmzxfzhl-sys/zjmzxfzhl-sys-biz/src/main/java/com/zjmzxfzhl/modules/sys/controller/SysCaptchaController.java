package com.zjmzxfzhl.modules.sys.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.code.kaptcha.Producer;
import com.zjmzxfzhl.common.core.Constants;
import com.zjmzxfzhl.common.core.util.RedisUtil;
import com.zjmzxfzhl.common.security.annotation.AnonymousAccess;

import io.swagger.annotations.Api;

/**
 * @author 庄金明
 * @date 2020年3月23日
 */
@RestController
@RequestMapping("/sys")
@Api(tags = "后台用户登录")
public class SysCaptchaController {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private Producer producer;

    @AnonymousAccess
    @GetMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response, String uuid) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        // 生成文字验证码
        String text = producer.createText();
        // 保存到 redis,60秒
        redisUtil.set(Constants.PREFIX_USER_CAPTCHA + uuid, text, 60);
        // 获取图片验证码
        BufferedImage image = producer.createImage(text);
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            ImageIO.write(image, "jpg", out);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
