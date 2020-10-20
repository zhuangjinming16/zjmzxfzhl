package com.zjmzxfzhl.modules.flowable.vo.query;

import lombok.Data;

@Data
public class ModelQueryVo extends BaseQueryVo{
    private String modelId;
    private String modelCategory;
    private String modelName;
    private String modelKey;
    private Integer modelVersion;
    private Boolean latestVersion = false;
    private Boolean deployed;
    private String deploymentId;
}
