package com.zjmzxfzhl.modules.flowable.vo;

import lombok.Data;

@Data
public class ListMyInvolvedSummaryVo {
    private String userId;
    private Boolean startByMe;
    private Boolean ccToMe;
    private Boolean unfinished;
    private Boolean finished;
}
