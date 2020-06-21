package com.zjmzxfzhl.modules.sys.common.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.zjmzxfzhl.modules.sys.service.SysCodeInfoService;
import com.zjmzxfzhl.modules.sys.service.SysConfigService;

import lombok.extern.slf4j.Slf4j;

/**
 * 在开发中可能会有这样的情景。需要在容器启动的时候执行一些内容。比如读取配置文件，数据库连接之类的。SpringBoot给我们提供了两个接口来帮助我们实现这种需求。
 * 
 * 这两个接口分别为CommandLineRunner和ApplicationRunner。他们的执行时机为容器启动完成的时候。这两个接口中有一个run方法，我们只需要实现这个方法即可。
 * 
 * @author 庄金明
 *
 */
@Component
@Slf4j
public class ZjmzxfzhlRunner implements ApplicationRunner {

    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private SysCodeInfoService sysCodeInfoService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        sysConfigService.loadSysConfigToRedis(null);
        sysCodeInfoService.loadSysCodeInfoToRedis(null);

        log.info("------【加载了下拉框的内存数据】---------");
    }

}