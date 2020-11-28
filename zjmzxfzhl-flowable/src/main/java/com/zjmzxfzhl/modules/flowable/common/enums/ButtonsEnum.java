/*
 * Copyright (c) 2019- 2019 threefish(https://gitee.com/threefish https://github.com/threefish) All Rights Reserved.
 * 本项目完全开源，商用完全免费。但请勿侵犯作者合法权益，如申请软著等。
 * 最后修改时间：2019/10/07 18:26:07
 * 源 码 地 址：https://gitee.com/threefish/NutzFw
 */

package com.zjmzxfzhl.modules.flowable.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.EnumSet;
import java.util.HashMap;

/**
 * @author 庄金明
 *
 * @date 2020年11月21日
 */
@Getter
@AllArgsConstructor
public enum ButtonsEnum {

    COMPLETE("提交"),

    STOP("终止"),

    ASSIGN("转派"),

    DELEGATE("委派"),

    BACK("退回"),

    TAKE_BACK("撤回");

    static HashMap<String, ButtonsEnum> lookup = new HashMap<>();

    static {
        for (ButtonsEnum typeEnum : EnumSet.allOf(ButtonsEnum.class)) {
            lookup.put(typeEnum.toString(), typeEnum);
        }
    }

    String value;

    public static ButtonsEnum get(String value) {
        return lookup.get(value);
    }
}
