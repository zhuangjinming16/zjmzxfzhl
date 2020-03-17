package com.zjmzxfzhl.modules.flowable.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.flowable.job.api.Job;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zjmzxfzhl.common.R;
import com.zjmzxfzhl.common.aspect.annotation.SysLogAuto;
import com.zjmzxfzhl.modules.flowable.common.BaseFlowableController;

@RestController
@RequestMapping("/flowable/processDefinitionJob")
public class ProcessDefinitionJobController extends BaseFlowableController {

	@RequiresPermissions("flowable:processDefinitionJob:list")
	@GetMapping(value = "/list")
	public List<Job> list(@RequestParam String processDefinitionId) {
		return managementService.createTimerJobQuery().processDefinitionId(processDefinitionId).list();
	}

	@SysLogAuto(value = "新增流程定义定时任务")
	@RequiresPermissions("flowable:processDefinitionJob:delete")
	@DeleteMapping(value = "/delete")
	@Transactional
	public R deleteJob(@RequestParam String jobId) {
		managementService.deleteTimerJob(jobId);
		return R.ok();
	}
}
