package com.zjmzxfzhl.modules.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zjmzxfzhl.common.Result;
import com.zjmzxfzhl.common.base.BaseController;

/**
 * 开发示例Controller
 * 
 * @author 庄金明
 */
@RestController
@RequestMapping("/demo/helloworld")
public class DemoHelloworldController extends BaseController {
	@GetMapping(value = "/helloworld")
	public Result helloworld() {
		return Result.ok("Hello World");
	}
}
