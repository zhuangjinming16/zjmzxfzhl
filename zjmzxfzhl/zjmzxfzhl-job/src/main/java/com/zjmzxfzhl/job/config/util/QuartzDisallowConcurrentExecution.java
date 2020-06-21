package com.zjmzxfzhl.job.config.util;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

import com.zjmzxfzhl.modules.sys.entity.SysJob;

/**
 * 定时任务处理（禁止并发执行）
 * 
 * @author 庄金明
 *
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
