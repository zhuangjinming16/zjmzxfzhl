package com.zjmzxfzhl.modules.flowable.wapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zjmzxfzhl.modules.flowable.common.ResponseFactory;

/**
 * @author 庄金明
 * @date 2020年3月22日
 */
@Component
public class ProcInsListWrapper implements IListWrapper {

    @Autowired
    private ResponseFactory responseFactory;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public List execute(List list) {
        return responseFactory.createHistoricProcessInstanceResponseList(list);
    }
}
