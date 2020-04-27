package com.zjmzxfzhl.modules.monitor.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zjmzxfzhl.common.Result;
import com.zjmzxfzhl.common.base.BaseController;
import com.zjmzxfzhl.common.server.Server;

/**
 * 服务器监控
 * 
 * @author 庄金明
 */
@RestController
@RequestMapping("/monitor/server")
public class MonitorServerController extends BaseController {
    @RequiresPermissions("monitor:server:getServerInfo")
    @GetMapping(value = "/getServerInfo")
    public Result getServerInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return Result.ok(server);
    }
}
