package com.zjmzxfzhl.job.config.util;

import org.quartz.JobExecutionContext;

import com.zjmzxfzhl.modules.sys.entity.SysJob;

/**
 * 定时任务处理（允许并发执行）
 * 
 * @author 庄金明
 *
 */
public class QuartzJobExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
