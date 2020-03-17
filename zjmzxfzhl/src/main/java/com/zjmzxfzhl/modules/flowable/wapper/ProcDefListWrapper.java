package com.zjmzxfzhl.modules.flowable.wapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zjmzxfzhl.modules.flowable.common.ResponseFactory;

@Component
public class ProcDefListWrapper implements IListWrapper {

	@Autowired
	private ResponseFactory responseFactory;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List execute(List list) {
		return responseFactory.createProcessDefinitionResponseList(list);
	}
}
