// package com.zjmzxfzhl.modules.app.aspect;
//
// import org.aspectj.lang.ProceedingJoinPoint;
// import org.aspectj.lang.annotation.Around;
// import org.aspectj.lang.annotation.Pointcut;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.context.request.RequestAttributes;
// import org.springframework.web.context.request.RequestContextHolder;
//
// import com.zjmzxfzhl.common.core.app.base.AppConstants;
// import com.zjmzxfzhl.common.core.app.base.AppSessionObject;
// import com.zjmzxfzhl.common.core.aspect.components.RepeatRequestComponent;
//
/// **
// * 登录用户请求防重发处理,Order=0优先于RedissonLockAspect
// *
// * @author 庄金明
// * @date 2020年3月24日
// */
//// @Aspect
//// @Component
//// @Order(0)
// public class AppRepeatRequestAspect {
// @Autowired
// private RepeatRequestComponent repeatRequestComponent;
//
// /**
// * APP端请求切面
// */
// @Pointcut("execution(* com.*..*.app.controller.*.*(..))")
// private void controllerAspectForApp() {
// }
//
// @Around("controllerAspectForApp()")
// public Object controllerAspectForAppAround(ProceedingJoinPoint joinPoint) throws Throwable {
// AppSessionObject appSessionObject = (AppSessionObject) RequestContextHolder.getRequestAttributes()
// .getAttribute(AppConstants.APP_SESSION_OBJECT, RequestAttributes.SCOPE_REQUEST);
// String userId = "";
// if (appSessionObject != null && appSessionObject.getUserId() != null
// && appSessionObject.getUserId().length() != 0) {
// userId = appSessionObject.getUserId();
// }
// return repeatRequestComponent.exec(joinPoint, userId);
// }
// }
