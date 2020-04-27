package com.zjmzxfzhl.modules.job;

import org.springframework.stereotype.Component;

import com.zjmzxfzhl.common.util.CommonUtil;

/**
 * 定时任务调度测试
 * 
 * @author 庄金明
 */
@Component("zjmzxfzhlJob")
public class ZjmzxfzhlJob {
    public void hasMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        System.out.println(CommonUtil.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void hasParam(String param) {
        System.out.println("执行有参方法：" + param);
    }

    public void hasNoParam() {
        System.out.println("执行无参方法");
    }
}
