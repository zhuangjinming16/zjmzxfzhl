package com.zjmzxfzhl.modules.flowable.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.flowable.identitylink.api.history.HistoricIdentityLink;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zjmzxfzhl.common.R;
import com.zjmzxfzhl.common.aspect.annotation.SysLogAuto;
import com.zjmzxfzhl.modules.flowable.common.BaseFlowableController;
import com.zjmzxfzhl.modules.flowable.service.FlowableTaskService;
import com.zjmzxfzhl.modules.flowable.vo.IdentityRequest;

@RestController
@RequestMapping("/flowable/taskIdentityLink")
public class TaskIdentityLinkController extends BaseFlowableController {
	@Autowired
	protected FlowableTaskService flowableTaskService;

	@RequiresPermissions("flowable:taskIdentityLink:list")
	@GetMapping(value = "/list")
	public R list(@RequestParam String taskId) {
		HistoricTaskInstance task = flowableTaskService.getHistoricTaskInstanceNotNull(taskId);
		List<HistoricIdentityLink> historicIdentityLinks = historyService.getHistoricIdentityLinksForTask(task.getId());
		return R.ok(responseFactory.createTaskIdentityResponseList(historicIdentityLinks));
	}

	@SysLogAuto(value = "新增任务授权")
	@RequiresPermissions("flowable:taskIdentityLink:save")
	@PostMapping(value = "/save")
	public R save(@RequestBody IdentityRequest taskIdentityRequest) {
		flowableTaskService.saveTaskIdentityLink(taskIdentityRequest);
		return R.ok();
	}

	@SysLogAuto(value = "删除任务授权")
	@RequiresPermissions("flowable:taskIdentityLink:delete")
	@DeleteMapping(value = "/delete")
	public R deleteIdentityLink(@RequestParam String taskId, @RequestParam String identityId, @RequestParam String identityType) {
		flowableTaskService.deleteTaskIdentityLink(taskId, identityId, identityType);
		return R.ok();
	}
}
