package com.zjmzxfzhl.modules.flowable.vo;

import java.util.Date;

import lombok.Data;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Data
public class ProcessDefinitionRequest {
    private String processDefinitionId;
    private boolean includeProcessInstances = false;
    private Date date;
}
