package com.zjmzxfzhl.framework.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.zjmzxfzhl.common.Result;
import com.zjmzxfzhl.common.aspect.annotation.SysLogAuto;
import com.zjmzxfzhl.common.util.IpUtils;
import com.zjmzxfzhl.common.util.JacksonUtil;
import com.zjmzxfzhl.framework.shiro.util.ShiroUtils;
import com.zjmzxfzhl.modules.sys.entity.SysLog;
import com.zjmzxfzhl.modules.sys.entity.SysUser;
import com.zjmzxfzhl.modules.sys.entity.vo.SysLoginForm;
import com.zjmzxfzhl.modules.sys.service.SysLogService;
import com.zjmzxfzhl.modules.sys.service.SysUserService;

/**
 * 系统日志处理
 * 
 * @author 庄金明
 *
 */
@Aspect
@Component
public class SysLogAutoAspect {
    /**
     * 1-用户登录
     */
    private static final String LOG_TYPE_1 = "1";
    /**
     * 2-用户操作
     */
    private static final String LOG_TYPE_2 = "2";

    @Autowired
    private SysLogService sysLogService;
    @Autowired
    private SysUserService sysUserService;

    @Around("@annotation(sysLogAuto)")
    public Object around(ProceedingJoinPoint joinPoint, SysLogAuto sysLogAuto) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 请求的参数
        Object[] args = joinPoint.getArgs();
        SysLog sysLog = new SysLog();
        sysLog.setLogContent(sysLogAuto.value());
        sysLog.setLogType(sysLogAuto.logType());
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        // 获取request并设置IP地址
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        sysLog.setIp(IpUtils.getIpAddr(request));
        sysLog.setRequestUrl(request.getRequestURI());
        sysLog.setRequestType(request.getMethod());
        SysUser sysUser = null;
        // 1-用户登录
        if (LOG_TYPE_1.equals(sysLogAuto.logType())) {
            SysLoginForm aSysLoginForm = (SysLoginForm) args[0];
            sysUser = sysUserService.getById(aSysLoginForm.getUserId());
            if (sysUser == null) {
                sysUser = new SysUser();
                sysUser.setUserId(aSysLoginForm.getUserId());
            }
        } else if (LOG_TYPE_2.equals(sysLogAuto.logType())) {
            sysUser = ShiroUtils.getSysUser();
            sysLog.setRequestParam(paramsToJson(args));
        }
        if (sysUser != null) {
            sysLog.setUserId(sysUser.getUserId());
            sysLog.setUserName(sysUser.getUserName());
            sysLog.setCreateBy(sysUser.getUserId());
        }

        long beginTime = System.currentTimeMillis();
        Object result = null;
        Exception ex = null;
        try {
            // 执行方法
            result = joinPoint.proceed();
        } catch (Exception e) {
            ex = e;
        }
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        // 保存日志
        sysLog.setCostTime(time);

        String operateResult = "";
        if (result instanceof Result) {
            Result r = (Result) result;
            operateResult = r.getMsg();
        } else if (ex != null) {
            operateResult = ex.getMessage();
        }

        sysLog.setOperateResult(operateResult);
        // 保存系统日志
        sysLogService.save(sysLog);

        if (ex != null) {
            throw ex;
        }

        return result;
    }

    /**
     * 将参数转换成json格式字符串，多个参数以逗号隔开
     * 
     * @param args
     * @return
     */
    private String paramsToJson(Object[] args) {
        if (args == null || args.length == 0) {
            return "";
        }
        StringBuffer params = new StringBuffer();
        for (int i = 0; i < args.length; i++) {
            String param = null;
            if (!(args[i] instanceof HttpServletRequest || args[i] instanceof HttpServletResponse)) {
                // 序列化忽略null值，写入数据库长度可以缩短
                param = JacksonUtil.objToStr(args[i], Include.NON_NULL);
            }
            if (param == null) {
                param = "{}";
            }
            if (i == args.length - 1) {
                params.append(param);
            } else {
                params.append(param).append(",");
            }
        }
        return params.toString();
    }

}
