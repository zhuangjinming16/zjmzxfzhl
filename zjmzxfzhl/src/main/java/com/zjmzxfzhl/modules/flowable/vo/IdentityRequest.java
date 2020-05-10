package com.zjmzxfzhl.modules.flowable.vo;

import lombok.Data;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Data
public class IdentityRequest {
    private String processDefinitionId;
    private String taskId;
    private String identityId;
    private String identityType;
}
