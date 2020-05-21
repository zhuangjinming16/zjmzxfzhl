package com.zjmzxfzhl.framework.job;

import org.quartz.JobExecutionContext;

import com.zjmzxfzhl.common.util.SpringContextUtils;
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
        JobInvokeUtil jobInvokeUtil = SpringContextUtils.getBean(JobInvokeUtil.class);
        jobInvokeUtil.invokeMethod(sysJob);
    }
}
