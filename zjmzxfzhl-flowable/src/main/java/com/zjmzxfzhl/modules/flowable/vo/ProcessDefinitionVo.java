package com.zjmzxfzhl.modules.flowable.vo;

import lombok.Data;

@Data
public class ProcessDefinitionVo {
    private String category;
    private String categoryName;
    private String processDefinitionKey;
    private String processDefinitionName;
    private Long count;
}
