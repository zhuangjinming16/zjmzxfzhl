package com.zjmzxfzhl.modules.monitor.service;

import java.util.List;
import java.util.Map;

import com.zjmzxfzhl.modules.monitor.vo.RedisInfo;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
public interface RedisService {

    /**
     * 获取redis信息
     * 
     * @return
     */
    List<RedisInfo> getInfo();

    /**
     * 获取key数量
     * 
     * @return
     */
    Map<String, Object> getKeysSize();

    /**
     * 获取内存内容
     * 
     * @return
     */
    Map<String, Object> getMemoryInfo();
}
