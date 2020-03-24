package com.zjmzxfzhl.modules.flowable.common;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.flowable.bpmn.model.ValuedDataObject;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.query.Query;
import org.flowable.common.engine.api.query.QueryProperty;
import org.flowable.engine.FormService;
import org.flowable.engine.HistoryService;
import org.flowable.engine.ManagementService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.zjmzxfzhl.common.Constants;
import com.zjmzxfzhl.common.util.ObjectUtils;
import com.zjmzxfzhl.common.util.SpringContextUtils;
import com.zjmzxfzhl.common.xss.SqlFilter;
import com.zjmzxfzhl.modules.flowable.common.FlowablePage.Direction;
import com.zjmzxfzhl.modules.flowable.common.FlowablePage.Order;
import com.zjmzxfzhl.modules.flowable.service.FlowableTaskService;
import com.zjmzxfzhl.modules.flowable.service.PermissionService;
import com.zjmzxfzhl.modules.flowable.wapper.IListWrapper;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@SuppressWarnings({ "rawtypes" })
public abstract class BaseFlowableController {
	@Autowired
	protected ResponseFactory responseFactory;
	@Autowired
	protected RepositoryService repositoryService;
	@Autowired
	protected ManagementService managementService;
	@Autowired
	protected RuntimeService runtimeService;
	@Autowired
	protected FormService formService;
	@Autowired
	protected HistoryService historyService;
	@Autowired
	protected PermissionService permissionService;
	@Autowired
	protected FlowableTaskService flowableTaskService;
	@Autowired
	protected TaskService taskService;

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	protected FlowablePage getFlowablePage(Map<String, String> requestParams) {
		int current = -1;
		if (ObjectUtils.isNotEmpty(requestParams.get(Constants.CURRENT))) {
			current = ObjectUtils.convertToInteger(requestParams.get(Constants.CURRENT), 1);
		}
		int size = 10;
		if (ObjectUtils.isNotEmpty(requestParams.containsKey(Constants.SIZE))) {
			size = ObjectUtils.convertToInteger(requestParams.get(Constants.SIZE), 10);
		}
		if (current < 0) {
			return null;
		}
		List<Order> orders = null;
		if (ObjectUtils.isNotEmpty(requestParams.get(Constants.ORDER_RULE))) {
			// orderRule=column1|asc,column2|desc
			orders = new ArrayList<>();
			String orderRule = requestParams.get(Constants.ORDER_RULE);
			// 处理排序
			if (orderRule != null && orderRule.length() > 0) {
				String[] orderColumnRules = orderRule.split(",");
				for (String orderColumnRule : orderColumnRules) {
					if (orderColumnRule.length() == 0) {
						continue;
					}
					String[] rule = orderColumnRule.split("\\|");
					String orderColumn = rule[0];
					orderColumn = SqlFilter.sqlInject(orderColumn);
					Order orderTmp = null;
					if (rule.length == 2 && "DESC".equals(rule[1].toUpperCase())) {
						orderTmp = new Order(orderColumn, Direction.DESC);
					} else {
						orderTmp = new Order(orderColumn, Direction.ASC);
					}
					orders.add(orderTmp);
				}
			}
		}
		if (orders == null) {
			return FlowablePage.of(current - 1, size);
		} else {
			return FlowablePage.of(current - 1, size, orders);
		}
	}

	protected FlowablePage pageList(Map<String, String> requestParams, Query query, Class<? extends IListWrapper> listWrapperClass,
			Map<String, QueryProperty> allowedSortProperties) {
		return pageList(getFlowablePage(requestParams), query, listWrapperClass, allowedSortProperties);
	}

	protected FlowablePage pageList(Map<String, String> requestParams, Query query, Class<? extends IListWrapper> listWrapperClass,
			Map<String, QueryProperty> allowedSortProperties, QueryProperty defaultDescSortProperty) {
		return pageList(getFlowablePage(requestParams), query, listWrapperClass, allowedSortProperties, defaultDescSortProperty);
	}

	protected FlowablePage pageList(FlowablePage flowablePage, Query query, Class<? extends IListWrapper> listWrapperClass,
			Map<String, QueryProperty> allowedSortProperties) {
		return pageList(flowablePage, query, listWrapperClass, allowedSortProperties, null);
	}

	protected FlowablePage pageList(FlowablePage flowablePage, Query query, Class<? extends IListWrapper> listWrapperClass,
			Map<String, QueryProperty> allowedSortProperties, QueryProperty defaultDescSortProperty) {
		List list = null;
		if (flowablePage == null) {
			list = query.list();
		} else {
			setQueryOrder(flowablePage.getOrders(), query, allowedSortProperties, defaultDescSortProperty);
			list = query.listPage((int) flowablePage.getOffset(), flowablePage.getSize());
		}
		if (listWrapperClass != null) {
			IListWrapper listWrapper = SpringContextUtils.getBean(listWrapperClass);
			list = listWrapper.execute(list);
		}

		flowablePage.setRecords(list);
		flowablePage.setTotal(query.count());
		return flowablePage;
	}

	protected List listWrapper(Class<? extends IListWrapper> listWrapperClass, List list) {
		IListWrapper listWrapper = SpringContextUtils.getBean(listWrapperClass);
		List result = listWrapper.execute(list);
		return result;
	}

	protected void setQueryOrder(List<Order> orders, Query query, Map<String, QueryProperty> properties, QueryProperty defaultDescSortProperty) {
		boolean orderByDefaultDescSortProperty = (orders == null || orders.size() == 0 || properties.isEmpty()) && defaultDescSortProperty != null;
		if (orderByDefaultDescSortProperty) {
			query.orderBy(defaultDescSortProperty).desc();
		} else {
			if (orders != null && orders.size() > 0) {
				for (Order order : orders) {
					QueryProperty qp = properties.get(order.getProperty());
					if (qp == null) {
						throw new FlowableIllegalArgumentException(
								"Value for param 'orders' is not valid, '" + order.getProperty() + "' is not a valid property");
					}
					query.orderBy(qp);
					if (order.getDirection() == Direction.ASC) {
						query.asc();
					} else {
						query.desc();
					}
				}
			}
		}
	}

	/**
	 * 只接收字符串
	 * 
	 * @param message
	 * @param arguments
	 * @return
	 */
	protected String messageFormat(String message, String... arguments) {
		return MessageFormat.format(message, (Object[]) arguments);
	}

	protected boolean isShowBusinessKey(String processDefinitionId) {
		List<ValuedDataObject> dataObjects = repositoryService.getBpmnModel(processDefinitionId).getMainProcess().getDataObjects();
		if (dataObjects != null && dataObjects.size() > 0) {
			for (ValuedDataObject valuedDataObject : dataObjects) {
				if ("showBusinessKey".equals(valuedDataObject.getId())) {
					if (valuedDataObject.getValue() instanceof String) {
						return Boolean.valueOf((String) valuedDataObject.getValue());
					} else if (valuedDataObject.getValue() instanceof Boolean) {
						return (Boolean) valuedDataObject.getValue();
					} else {
						return false;
					}
				}
			}
		}
		return false;
	}
}
