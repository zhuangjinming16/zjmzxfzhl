package com.zjmzxfzhl.modules.flowable.util;

import com.google.common.collect.Sets;
import com.zjmzxfzhl.common.core.security.SecurityUser;
import com.zjmzxfzhl.common.core.util.SecurityUtils;
import com.zjmzxfzhl.modules.flowable.common.model.ZjmzxfzhlUserTask;
import com.zjmzxfzhl.modules.flowable.constant.FlowableConstant;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.*;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.util.ProcessDefinitionUtil;
import org.flowable.idm.api.User;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
import org.springframework.beans.BeanUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 庄金明
 * @date 2020年3月24日
 */
public class FlowableUtils {

    public static User getFlowableUser() {
        SecurityUser securityUser = ((SecurityUser) SecurityUtils.getUserDetails());
        User currentUser = new UserEntityImpl();
        currentUser.setId(securityUser.getUsername());
        currentUser.setFirstName(securityUser.getUserRealName());
        currentUser.setLastName(securityUser.getUserRealName());
        currentUser.setDisplayName(securityUser.getUserRealName());
        return currentUser;
    }

    public static <T> Map<String, List<T>> groupListContentBy(List<T> source, Function<T, String> classifier) {
        return source.stream().collect(Collectors.groupingBy(classifier));
    }

    public static Map<String, FlowNode> getCanReachTo(FlowNode toFlowNode) {
        return getCanReachTo(toFlowNode, null);
    }

    public static Map<String, FlowNode> getCanReachTo(FlowNode toFlowNode, Map<String, FlowNode> canReachToNodes) {
        if (canReachToNodes == null) {
            canReachToNodes = new HashMap<>(16);
        }
        List<SequenceFlow> flows = toFlowNode.getIncomingFlows();
        if (flows != null && flows.size() > 0) {
            for (SequenceFlow sequenceFlow : flows) {
                FlowElement sourceFlowElement = sequenceFlow.getSourceFlowElement();
                if (sourceFlowElement instanceof FlowNode) {
                    canReachToNodes.put(sourceFlowElement.getId(), (FlowNode) sourceFlowElement);
                    if (sourceFlowElement instanceof SubProcess) {
                        for (Map.Entry<String, FlowElement> entry :
                                ((SubProcess) sourceFlowElement).getFlowElementMap().entrySet()) {
                            if (entry.getValue() instanceof FlowNode) {
                                FlowNode flowNodeV = (FlowNode) entry.getValue();
                                canReachToNodes.put(entry.getKey(), flowNodeV);
                            }
                        }
                    }
                    getCanReachTo((FlowNode) sourceFlowElement, canReachToNodes);
                }
            }
        }
        if (toFlowNode.getSubProcess() != null) {
            getCanReachTo(toFlowNode.getSubProcess(), canReachToNodes);
        }
        return canReachToNodes;
    }

    public static Map<String, FlowNode> getCanReachFrom(FlowNode fromFlowNode) {
        return getCanReachFrom(fromFlowNode, null);
    }

    public static Map<String, FlowNode> getCanReachFrom(FlowNode fromFlowNode,
                                                        Map<String, FlowNode> canReachFromNodes) {
        if (canReachFromNodes == null) {
            canReachFromNodes = new HashMap<>(16);
        }
        List<SequenceFlow> flows = fromFlowNode.getOutgoingFlows();
        if (flows != null && flows.size() > 0) {
            for (SequenceFlow sequenceFlow : flows) {
                FlowElement targetFlowElement = sequenceFlow.getTargetFlowElement();
                if (targetFlowElement instanceof FlowNode) {
                    canReachFromNodes.put(targetFlowElement.getId(), (FlowNode) targetFlowElement);
                    if (targetFlowElement instanceof SubProcess) {
                        for (Map.Entry<String, FlowElement> entry :
                                ((SubProcess) targetFlowElement).getFlowElementMap().entrySet()) {
                            if (entry.getValue() instanceof FlowNode) {
                                FlowNode flowNodeV = (FlowNode) entry.getValue();
                                canReachFromNodes.put(entry.getKey(), flowNodeV);
                            }
                        }
                    }
                    getCanReachFrom((FlowNode) targetFlowElement, canReachFromNodes);
                }
            }
        }
        if (fromFlowNode.getSubProcess() != null) {
            getCanReachFrom(fromFlowNode.getSubProcess(), canReachFromNodes);
        }
        return canReachFromNodes;
    }

    public static Map<String, Set<String>> getSpecialGatewayElements(FlowElementsContainer container) {
        return getSpecialGatewayElements(container, null);
    }

    public static Map<String, Set<String>> getSpecialGatewayElements(FlowElementsContainer container, Map<String,
            Set<String>> specialGatewayElements) {
        if (specialGatewayElements == null) {
            specialGatewayElements = new HashMap<>(16);
        }
        Collection<FlowElement> flowelements = container.getFlowElements();
        for (FlowElement flowElement : flowelements) {
            boolean isBeginSpecialGateway =
                    flowElement.getId().endsWith(FlowableConstant.SPECIAL_GATEWAY_BEGIN_SUFFIX) && (flowElement instanceof ParallelGateway || flowElement instanceof InclusiveGateway || flowElement instanceof ComplexGateway);
            if (isBeginSpecialGateway) {
                String gatewayBeginRealId = flowElement.getId();
                String gatewayId = gatewayBeginRealId.substring(0, gatewayBeginRealId.length() - 6);
                Set<String> gatewayIdContainFlowelements = specialGatewayElements.computeIfAbsent(gatewayId,
                        k -> new HashSet<>());
                findElementsBetweenSpecialGateway(flowElement,
                        gatewayId + FlowableConstant.SPECIAL_GATEWAY_END_SUFFIX, gatewayIdContainFlowelements);
            } else if (flowElement instanceof SubProcess) {
                getSpecialGatewayElements((SubProcess) flowElement, specialGatewayElements);
            }
        }

        // 外层到里层排序
        Map<String, Set<String>> specialGatewayNodesSort = new LinkedHashMap<>();
        specialGatewayElements.entrySet().stream().sorted((o1, o2) -> o2.getValue().size() - o1.getValue().size()).forEach(entry -> specialGatewayNodesSort.put(entry.getKey(), entry.getValue()));

        return specialGatewayNodesSort;
    }

    public static void findElementsBetweenSpecialGateway(FlowElement specialGatewayBegin, String specialGatewayEndId,
                                                         Set<String> elements) {
        elements.add(specialGatewayBegin.getId());
        List<SequenceFlow> sequenceFlows = ((FlowNode) specialGatewayBegin).getOutgoingFlows();
        if (sequenceFlows != null && sequenceFlows.size() > 0) {
            for (SequenceFlow sequenceFlow : sequenceFlows) {
                FlowElement targetFlowElement = sequenceFlow.getTargetFlowElement();
                String targetFlowElementId = targetFlowElement.getId();
                elements.add(specialGatewayEndId);
                if (targetFlowElementId.equals(specialGatewayEndId)) {
                    continue;
                } else {
                    findElementsBetweenSpecialGateway(targetFlowElement, specialGatewayEndId, elements);
                }
            }
        }
    }

    /**
     * Verifies if the element with the given source identifier can reach the element with the target identifier through
     * following sequence flow.
     */
    public static boolean isReachable(String processDefinitionId, String sourceElementId, String targetElementId) {
        // Fetch source and target elements
        Process process = ProcessDefinitionUtil.getProcess(processDefinitionId);
        FlowElement sourceFlowElement = process.getFlowElement(sourceElementId, true);
        FlowNode sourceElement = null;
        if (sourceFlowElement instanceof FlowNode) {
            sourceElement = (FlowNode) sourceFlowElement;
        } else if (sourceFlowElement instanceof SequenceFlow) {
            sourceElement = (FlowNode) ((SequenceFlow) sourceFlowElement).getTargetFlowElement();
        }
        FlowElement targetFlowElement = process.getFlowElement(targetElementId, true);
        FlowNode targetElement = null;
        if (targetFlowElement instanceof FlowNode) {
            targetElement = (FlowNode) targetFlowElement;
        } else if (targetFlowElement instanceof SequenceFlow) {
            targetElement = (FlowNode) ((SequenceFlow) targetFlowElement).getTargetFlowElement();
        }
        if (sourceElement == null) {
            throw new FlowableException("Invalid sourceElementId '" + sourceElementId + "': no element found for " +
                    "this" + " id n process definition '" + processDefinitionId + "'");
        }
        if (targetElement == null) {
            throw new FlowableException("Invalid targetElementId '" + targetElementId + "': no element found for " +
                    "this" + " id n process definition '" + processDefinitionId + "'");
        }
        Set<String> visitedElements = new HashSet<>();
        return isReachable(process, sourceElement, targetElement, visitedElements);
    }

    public static boolean isReachable(Process process, FlowNode sourceElement, FlowNode targetElement) {
        return isReachable(process, sourceElement, targetElement, Sets.newHashSet());
    }

    public static boolean isReachable(Process process, FlowNode sourceElement, FlowNode targetElement,
                                      Set<String> visitedElements) {
        // Special case: start events in an event subprocess might exist as an execution and are most likely be able to
        // reach the target
        // when the target is in the event subprocess, but should be ignored as they are not 'real' runtime executions
        // (but rather waiting for a
        // trigger)
        if (sourceElement instanceof StartEvent && isInEventSubprocess(sourceElement)) {
            return false;
        }
        // No outgoing seq flow: could be the end of eg . the process or an embedded subprocess
        if (sourceElement.getOutgoingFlows().size() == 0) {
            visitedElements.add(sourceElement.getId());
            FlowElementsContainer parentElement = process.findParent(sourceElement);
            if (parentElement instanceof SubProcess) {
                sourceElement = (SubProcess) parentElement;
                // by zjm begin
                // 子流程的结束节点，若目标节点在该子流程中，说明无法到达，返回false
                if (((SubProcess) sourceElement).getFlowElement(targetElement.getId()) != null) {
                    return false;
                }
                // by zjm end
            } else {
                return false;
            }
        }
        if (sourceElement.getId().equals(targetElement.getId())) {
            return true;
        }
        // To avoid infinite looping, we must capture every node we visit
        // and check before going further in the graph if we have already
        // visited the node.
        visitedElements.add(sourceElement.getId());
        // by zjm begin
        // 当前节点能够到达子流程，且目标节点在子流程中，说明可以到达，返回true
        if (sourceElement instanceof SubProcess && ((SubProcess) sourceElement).getFlowElement(targetElement.getId()) != null) {
            return true;
        }
        // by zjm end
        List<SequenceFlow> sequenceFlows = sourceElement.getOutgoingFlows();
        if (sequenceFlows != null && sequenceFlows.size() > 0) {
            for (SequenceFlow sequenceFlow : sequenceFlows) {
                String targetRef = sequenceFlow.getTargetRef();
                FlowNode sequenceFlowTarget = (FlowNode) process.getFlowElement(targetRef, true);
                if (sequenceFlowTarget != null && !visitedElements.contains(sequenceFlowTarget.getId())) {
                    boolean reachable = isReachable(process, sequenceFlowTarget, targetElement, visitedElements);
                    if (reachable) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    protected static boolean isInEventSubprocess(FlowNode flowNode) {
        FlowElementsContainer flowElementsContainer = flowNode.getParentContainer();
        while (flowElementsContainer != null) {
            if (flowElementsContainer instanceof EventSubProcess) {
                return true;
            }
            if (flowElementsContainer instanceof FlowElement) {
                flowElementsContainer = ((FlowElement) flowElementsContainer).getParentContainer();
            } else {
                flowElementsContainer = null;
            }
        }
        return false;
    }

    public static List<String> getParentProcessIds(FlowNode flowNode) {
        List<String> result = new ArrayList<>();
        FlowElementsContainer flowElementsContainer = flowNode.getParentContainer();
        while (flowElementsContainer != null) {
            if (flowElementsContainer instanceof SubProcess) {
                SubProcess flowElement = (SubProcess) flowElementsContainer;
                result.add(flowElement.getId());
                flowElementsContainer = flowElement.getParentContainer();
            } else if (flowElementsContainer instanceof Process) {
                Process flowElement = (Process) flowElementsContainer;
                result.add(flowElement.getId());
                flowElementsContainer = null;
            }
        }
        // 第一层Process为第0个
        Collections.reverse(result);
        return result;
    }

    /**
     * 查询不同层级
     *
     * @param sourceList
     * @param targetList
     * @return 返回不同的层级，如果其中一个层级较深，则返回层级小的+1，从第0层开始，请注意判断是否会出现下标越界异常；返回 -1 表示在同一层
     */
    public static Integer getDiffLevel(List<String> sourceList, List<String> targetList) {
        if (sourceList == null || sourceList.isEmpty() || targetList == null || targetList.isEmpty()) {
            throw new FlowableException("sourceList and targetList cannot be empty");
        }
        if (sourceList.size() == 1 && targetList.size() == 1) {
            // 都在第0层且不相等
            if (!sourceList.get(0).equals(targetList.get(0))) {
                return 0;
            } else {// 都在第0层且相等
                return -1;
            }
        }

        int minSize = sourceList.size() < targetList.size() ? sourceList.size() : targetList.size();
        Integer targetLevel = null;
        for (int i = 0; i < minSize; i++) {
            if (!sourceList.get(i).equals(targetList.get(i))) {
                targetLevel = i;
                break;
            }
        }
        if (targetLevel == null) {
            if (sourceList.size() == targetList.size()) {
                targetLevel = -1;
            } else {
                targetLevel = minSize;
            }
        }
        return targetLevel;
    }

    public static Set<String> getParentExecutionIdsByActivityId(List<ExecutionEntity> executions, String activityId) {
        List<ExecutionEntity> activityIdExecutions =
                executions.stream().filter(e -> activityId.equals(e.getActivityId())).collect(Collectors.toList());
        if (activityIdExecutions.isEmpty()) {
            throw new FlowableException("Active execution could not be found with activity id " + activityId);
        }
        // check for a multi instance root execution
        ExecutionEntity miExecution = null;
        boolean isInsideMultiInstance = false;
        for (ExecutionEntity possibleMiExecution : activityIdExecutions) {
            if (possibleMiExecution.isMultiInstanceRoot()) {
                miExecution = possibleMiExecution;
                isInsideMultiInstance = true;
                break;
            }
            if (isExecutionInsideMultiInstance(possibleMiExecution)) {
                isInsideMultiInstance = true;
            }
        }
        Set<String> parentExecutionIds = new HashSet<>();
        if (isInsideMultiInstance) {
            Stream<ExecutionEntity> executionEntitiesStream = activityIdExecutions.stream();
            if (miExecution != null) {
                executionEntitiesStream = executionEntitiesStream.filter(ExecutionEntity::isMultiInstanceRoot);
            }
            executionEntitiesStream.forEach(childExecution -> {
                parentExecutionIds.add(childExecution.getParentId());
            });
        } else {
            ExecutionEntity execution = activityIdExecutions.iterator().next();
            parentExecutionIds.add(execution.getParentId());
        }
        return parentExecutionIds;
    }

    public static boolean isExecutionInsideMultiInstance(ExecutionEntity execution) {
        return getFlowElementMultiInstanceParentId(execution.getCurrentFlowElement()).isPresent();
    }

    public static Optional<String> getFlowElementMultiInstanceParentId(FlowElement flowElement) {
        FlowElementsContainer parentContainer = flowElement.getParentContainer();
        while (parentContainer instanceof Activity) {
            if (isFlowElementMultiInstance((Activity) parentContainer)) {
                return Optional.of(((Activity) parentContainer).getId());
            }
            parentContainer = ((Activity) parentContainer).getParentContainer();
        }
        return Optional.empty();
    }

    public static boolean isFlowElementMultiInstance(FlowElement flowElement) {
        if (flowElement instanceof Activity) {
            return ((Activity) flowElement).getLoopCharacteristics() != null;
        }
        return false;
    }

    public static String getParentExecutionIdFromParentIds(ExecutionEntity execution, Set<String> parentExecutionIds) {
        ExecutionEntity taskParentExecution = execution.getParent();
        String realParentExecutionId = null;
        while (taskParentExecution != null) {
            if (parentExecutionIds.contains(taskParentExecution.getId())) {
                realParentExecutionId = taskParentExecution.getId();
                break;
            }
            taskParentExecution = taskParentExecution.getParent();
        }
        if (realParentExecutionId == null || realParentExecutionId.length() == 0) {
            throw new FlowableException("Parent execution could not be found with executionId id " + execution.getId());
        }
        return realParentExecutionId;
    }

    public static String[] getSourceAndTargetRealActivityId(FlowNode sourceFlowElement, FlowNode targetFlowElement) {
        // 实际应操作的当前节点ID
        String sourceRealActivityId = sourceFlowElement.getId();
        // 实际应操作的目标节点ID
        String targetRealActivityId = targetFlowElement.getId();
        List<String> sourceParentProcesss = FlowableUtils.getParentProcessIds(sourceFlowElement);
        List<String> targetParentProcesss = FlowableUtils.getParentProcessIds(targetFlowElement);
        int diffParentLevel = getDiffLevel(sourceParentProcesss, targetParentProcesss);
        if (diffParentLevel != -1) {
            sourceRealActivityId = sourceParentProcesss.size() == diffParentLevel ? sourceRealActivityId :
                    sourceParentProcesss.get(diffParentLevel);
            targetRealActivityId = targetParentProcesss.size() == diffParentLevel ? targetRealActivityId :
                    targetParentProcesss.get(diffParentLevel);
        }
        return new String[]{sourceRealActivityId, targetRealActivityId};
    }

    public static String getAttributeValue(BaseElement element, String namespace, String name) {
        return element.getAttributeValue(namespace, name);
    }

    public static String getFlowableAttributeValue(BaseElement element, String name) {
        return element.getAttributeValue(FlowableConstant.FLOWABLE_NAMESPACE, name);
    }

    public static List<ExtensionElement> getExtensionElements(BaseElement element, String name) {
        return element.getExtensionElements().get(name);
    }

    public static ZjmzxfzhlUserTask getZjmzxfzhlUserTask(UserTask userTask) {
        ZjmzxfzhlUserTask zjmzxfzhlUserTask = new ZjmzxfzhlUserTask();
        BeanUtils.copyProperties(userTask, zjmzxfzhlUserTask, FlowableConstant.BUTTONS);
        zjmzxfzhlUserTask.setButtons(getFlowableAttributeValue(userTask, FlowableConstant.BUTTONS));
        return zjmzxfzhlUserTask;
    }

    public static FlowElement getFlowElement(RepositoryService repositoryService, String processDefinitionId,
                                             String flowElementId, boolean searchRecursive) {
        Process process = repositoryService.getBpmnModel(processDefinitionId).getMainProcess();
        FlowElement flowElement = process.getFlowElement(flowElementId, searchRecursive);
        return flowElement;
    }

    public static FlowElement getFlowElement(RepositoryService repositoryService, String processDefinitionId,
                                             String flowElementId) {
        return getFlowElement(repositoryService, processDefinitionId, flowElementId, true);
    }
}
