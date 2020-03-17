package com.zjmzxfzhl.modules.flowable.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessInstanceDetailResponse extends HistoricProcessInstanceResponse {
	private boolean suspended;
	private String deleteReason;
	private String startUserName;

}
