package com.zjmzxfzhl.modules.flowable.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@Getter
@Setter
public class ProcessInstanceDetailResponse extends HistoricProcessInstanceResponse {
	private boolean suspended;
	private String deleteReason;
	private String startUserName;

}
