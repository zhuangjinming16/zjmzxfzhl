package com.zjmzxfzhl.common.exception;

import java.util.List;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.zjmzxfzhl.common.R;

import lombok.extern.slf4j.Slf4j;

/**
 * 异常处理器
 * 
 * @author 庄金明
 * 
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(BaseException.class)
	public R handleBaseException(BaseException e) {
		log.error(e.getMessage(), e);
		return R.error(e.getMessage());
	}

	@ExceptionHandler(SysException.class)
	public R handleSysException(SysException e) {
		log.error(e.getMessage(), e);
		return R.error(e.getMessage());
	}

	@ExceptionHandler(AppException.class)
	public R handleAppException(AppException e) {
		log.error(e.getMessage(), e);
		return R.error(e.getMessage());
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public R handlerNoFoundException(NoHandlerFoundException e) {
		log.error(e.getMessage(), e);
		return R.error(404, "路径不存在，请检查路径是否正确");
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public R handleDuplicateKeyException(DuplicateKeyException e) {
		log.error(e.getMessage(), e);
		return R.error("数据库中已存在该记录");
	}

	@ExceptionHandler(AuthorizationException.class)
	public R handleAuthorizationException(AuthorizationException e) {
		log.error(e.getMessage(), e);
		return R.error("没有权限，请联系管理员授权");
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error(e.getMessage(), e);
		StringBuffer errorMsg = new StringBuffer();
		BindingResult bindingResult = e.getBindingResult();
		List<ObjectError> allErrors = bindingResult.getAllErrors();
		if (allErrors != null && allErrors.size() > 0) {
			for (ObjectError objectError : allErrors) {
				if (objectError instanceof FieldError) {
					FieldError fieldError = (FieldError) objectError;
					errorMsg.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append(";");
				} else {
					errorMsg.append(objectError.getDefaultMessage()).append(";");
				}
			}
			return R.error(errorMsg.toString());
		} else {
			return R.error(e.getMessage());
		}
	}

	@ExceptionHandler(Exception.class)
	public R handleException(Exception e) {
		log.error(e.getMessage(), e);
		return R.error(e.getMessage());
	}

}
