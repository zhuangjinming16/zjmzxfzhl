package com.zjmzxfzhl.modules.flowable.vo.query;

import lombok.Data;

@Data
public class ProcessDefinitionQueryVo extends BaseQueryVo{
    private String processDefinitionCategory;
    private String processDefinitionName;
    private String processDefinitionKey;
    private String processDefinitionId;
    private Integer processDefinitionVersion;
    private Boolean suspended = false;
    private Boolean active = false;
    private Boolean latestVersion = false;
    private String startableByUser;
}
