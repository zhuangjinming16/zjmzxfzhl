package com.zjmzxfzhl.modules.sys.entity.vo;

import lombok.Data;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Data
public class SysLoginForm {
    private String userId;
    private String password;
    private String uuid;
    private String captcha;
}