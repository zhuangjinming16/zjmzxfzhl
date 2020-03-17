package com.zjmzxfzhl.modules.flowable.common.exception;

import org.flowable.common.engine.api.FlowableException;

public class FlowableNoPermissionException extends FlowableException {

	private static final long serialVersionUID = 1L;

	public FlowableNoPermissionException(String message) {
		super(message);
	}

	public FlowableNoPermissionException(String message, Throwable cause) {
		super(message, cause);
	}
}
