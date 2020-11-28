package com.zjmzxfzhl.modules.flowable.common;

import com.zjmzxfzhl.common.core.util.ObjectUtils;
import com.zjmzxfzhl.common.core.util.SpringContextUtils;
import com.zjmzxfzhl.common.core.xss.SqlFilter;
import com.zjmzxfzhl.modules.flowable.common.FlowablePage.Direction;
import com.zjmzxfzhl.modules.flowable.common.FlowablePage.Order;
import com.zjmzxfzhl.modules.flowable.service.FlowableTaskService;
import com.zjmzxfzhl.modules.flowable.service.PermissionService;
import com.zjmzxfzhl.modules.flowable.vo.query.BaseQueryVo;
import com.zjmzxfzhl.modules.flowable.wapper.IListWrapper;
import org.flowable.bpmn.model.ExtensionElement;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.ValuedDataObject;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.common.engine.api.query.Query;
import org.flowable.common.engine.api.query.QueryProperty;
import org.flowable.engine.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
@SuppressWarnings({"rawtypes"})
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

    protected FlowablePage getFlowablePage(BaseQueryVo baseQueryVo) {
        int current = baseQueryVo.getCurrent() ;
        int size = baseQueryVo.getSize();
        if (current < 0) {
            return null;
        }
        List<Order> orders = null;
        if (ObjectUtils.isNotEmpty(baseQueryVo.getOrderRule())) {
            // orderRule=column1|asc,column2|desc
            orders = new ArrayList<>();
            String orderRule = baseQueryVo.getOrderRule();
            // 处理排序
            if (orderRule != null && orderRule.length() > 0) {
                String[] orderColumnRules = orderRule.split(",");
                for (String orderColumnRule : orderColumnRules) {
                    if (orderColumnRule.length() == 0) {
                        continue;
                    }
                    String[] rule = orderColumnRule.split("\\|");
                    String orderColumn = rule[0];
                    SqlFilter.sqlInject(orderColumn);
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

    protected FlowablePage pageList(BaseQueryVo baseQueryVo, Query query,
                                    Class<? extends IListWrapper> listWrapperClass,
                                    Map<String, QueryProperty> allowedSortProperties) {
        return pageList(getFlowablePage(baseQueryVo), query, listWrapperClass, allowedSortProperties);
    }

    protected FlowablePage pageList(BaseQueryVo baseQueryVo, Query query,
                                    Class<? extends IListWrapper> listWrapperClass,
                                    Map<String, QueryProperty> allowedSortProperties,
                                    QueryProperty defaultDescSortProperty) {
        return pageList(getFlowablePage(baseQueryVo), query, listWrapperClass, allowedSortProperties,
                defaultDescSortProperty);
    }

    protected FlowablePage pageList(FlowablePage flowablePage, Query query,
                                    Class<? extends IListWrapper> listWrapperClass,
                                    Map<String, QueryProperty> allowedSortProperties) {
        return pageList(flowablePage, query, listWrapperClass, allowedSortProperties, null);
    }

    protected FlowablePage pageList(FlowablePage flowablePage, Query query,
                                    Class<? extends IListWrapper> listWrapperClass,
                                    Map<String, QueryProperty> allowedSortProperties,
                                    QueryProperty defaultDescSortProperty) {
        List list = null;
        if (flowablePage == null) {
            list = query.list();
            flowablePage = FlowablePage.of(0, 0);
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

    protected void setQueryOrder(List<Order> orders, Query query, Map<String, QueryProperty> properties,
                                 QueryProperty defaultDescSortProperty) {
        boolean orderByDefaultDescSortProperty =
                (orders == null || orders.size() == 0 || properties.isEmpty()) && defaultDescSortProperty != null;
        if (orderByDefaultDescSortProperty) {
            query.orderBy(defaultDescSortProperty).desc();
        } else {
            if (orders != null && orders.size() > 0) {
                for (Order order : orders) {
                    QueryProperty qp = properties.get(order.getProperty());
                    if (qp == null) {
                        throw new FlowableIllegalArgumentException("Value for param 'orders' is not valid, '" + order.getProperty() + "' is not a valid property");
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
        Process process = repositoryService.getBpmnModel(processDefinitionId).getMainProcess();
        Map<String, List<ExtensionElement>> extensionElements = process.getExtensionElements();
        if (extensionElements != null && !extensionElements.isEmpty() && extensionElements.get("properties") != null && extensionElements.get("properties").size() > 0) {
            List<ExtensionElement> properties = extensionElements.get("properties");
            for (ExtensionElement extensionElement : properties) {
                List<ExtensionElement> property = extensionElement.getChildElements().get("property");
                if (property != null && property.size() > 0) {
                    for (ExtensionElement propertyElement : property) {
                        String name = propertyElement.getAttributeValue(null, "name");
                        String value = propertyElement.getAttributeValue(null, "value");
                        if ("showBusinessKey".equals(name)) {
                            if (Boolean.valueOf(value)) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                }
            }
        }

        List<ValuedDataObject> dataObjects =
                repositoryService.getBpmnModel(processDefinitionId).getMainProcess().getDataObjects();
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
