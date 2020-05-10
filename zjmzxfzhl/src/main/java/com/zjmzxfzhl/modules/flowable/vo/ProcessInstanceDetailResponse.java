package com.zjmzxfzhl.modules.flowable.vo;

import lombok.Data;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Data
public class ProcessInstanceDetailResponse extends HistoricProcessInstanceResponse {
    private boolean suspended;
    private String deleteReason;
    private String startUserName;

}
