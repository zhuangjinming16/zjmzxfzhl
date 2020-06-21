package com.zjmzxfzhl.job.config.util;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.zjmzxfzhl.common.core.Constants;
import com.zjmzxfzhl.common.core.util.ExceptionUtil;
import com.zjmzxfzhl.common.core.util.SpringContextUtils;
import com.zjmzxfzhl.job.constant.ScheduleConstants;
import com.zjmzxfzhl.modules.sys.entity.SysJob;
import com.zjmzxfzhl.modules.sys.entity.SysJobLog;
import com.zjmzxfzhl.modules.sys.service.SysJobLogService;

/**
 * 抽象quartz调用
 *
 * @author 庄金明
 */
public abstract class AbstractQuartzJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(AbstractQuartzJob.class);

    /**
     * 线程本地变量
     */
    private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Exception ex = null;
        SysJob sysJob = new SysJob();
        try {
            BeanUtils.copyProperties(context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES), sysJob);
            before(context, sysJob);
            if (sysJob != null) {
                doExecute(context, sysJob);
            }
        } catch (Exception e) {
            ex = e;
            log.error("任务执行异常  - ：", e);
        } finally {
            after(context, sysJob, ex);
        }
    }

    /**
     * 执行前
     *
     * @param context
     *            工作执行上下文对象
     * @param sysJob
     *            系统计划任务
     */
    protected void before(JobExecutionContext context, SysJob sysJob) {
        threadLocal.set(new Date());
    }

    /**
     * 执行后
     *
     * @param context
     *            工作执行上下文对象
     * @param sysScheduleJob
     *            系统计划任务
     */
    protected void after(JobExecutionContext context, SysJob sysJob, Exception e) {
        Date startTime = threadLocal.get();
        threadLocal.remove();
        Date stopTime = new Date();
        final SysJobLog sysJobLog = new SysJobLog();
        sysJobLog.setJobName(sysJob.getJobName());
        sysJobLog.setJobGroup(sysJob.getJobGroup());
        sysJobLog.setInvokeTarget(sysJob.getInvokeTarget());
        sysJobLog.setStartTime(startTime);
        sysJobLog.setStopTime(stopTime);

        sysJobLog.setCreateBy(Constants.ADMIN);
        sysJobLog.setCreateDate(stopTime);
        sysJobLog.setCreateTime(stopTime);
        long runMs = sysJobLog.getStopTime().getTime() - sysJobLog.getStartTime().getTime();
        sysJobLog.setJobMessage(sysJobLog.getJobName() + " 总共耗时：" + runMs + "毫秒");
        if (e != null) {
            sysJobLog.setStatus("0");
            String errorMsg = ExceptionUtil.getExceptionMessage(e);
            sysJobLog.setExceptionInfo(errorMsg);
        } else {
            sysJobLog.setStatus("1");
        }

        // 写入数据库当中
        SpringContextUtils.getBean(SysJobLogService.class).save(sysJobLog);
    }

    /**
     * 执行方法，由子类重载
     *
     * @param context
     *            工作执行上下文对象
     * @param sysJob
     *            系统计划任务
     * @throws Exception
     *             执行过程中的异常
     */
    protected abstract void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception;
}
