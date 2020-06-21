package com.zjmzxfzhl.modules.monitor.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.filechooser.FileSystemView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zjmzxfzhl.common.core.Result;
import com.zjmzxfzhl.modules.monitor.service.RedisService;
import com.zjmzxfzhl.modules.monitor.vo.RedisInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Slf4j
@RestController
@RequestMapping("/actuator")
public class MonitorController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/redis/info")
    public Result getRedisInfo() throws Exception {
        List<RedisInfo> infoList = this.redisService.getInfo();
        return Result.ok(infoList);
    }

    @GetMapping("/redis/keysSize")
    public Map<String, Object> getKeysSize() throws Exception {
        return redisService.getKeysSize();
    }

    @GetMapping("/redis/memoryInfo")
    public Map<String, Object> getMemoryInfo() throws Exception {
        return redisService.getMemoryInfo();
    }

    @GetMapping("/queryDiskInfo")
    public Result queryDiskInfo() {
        try {
            // 当前文件系统类
            FileSystemView fsv = FileSystemView.getFileSystemView();
            // 列出所有windows 磁盘
            File[] fs = File.listRoots();
            log.info("查询磁盘信息:" + fs.length + "个");
            List<Map<String, Object>> data = new ArrayList<>();
            for (int i = 0; i < fs.length; i++) {
                if (fs[i].getTotalSpace() == 0) {
                    continue;
                }
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("name", fsv.getSystemDisplayName(fs[i]));
                map.put("totalSpace", fs[i].getTotalSpace());
                map.put("freeSpace", fs[i].getFreeSpace());
                map.put("used", (fs[i].getTotalSpace() - fs[i].getFreeSpace()) * 100 / fs[i].getTotalSpace() + "%");
                data.add(map);
                log.info(map.toString());
            }
            return Result.ok(data);
        } catch (Exception e) {
            log.error("查询失败：", e);
            return Result.error("查询失败");
        }
    }
}
