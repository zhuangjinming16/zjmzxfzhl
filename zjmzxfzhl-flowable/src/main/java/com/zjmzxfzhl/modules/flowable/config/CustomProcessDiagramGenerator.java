package com.zjmzxfzhl.modules.flowable.config;

import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.*;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;

import java.awt.*;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 庄金明
 * @date 2020年3月23日
 */
public class CustomProcessDiagramGenerator extends DefaultProcessDiagramGenerator {

    public CustomProcessDiagramGenerator() {
        super(1.0);
    }

    public InputStream generateCustomDiagram(BpmnModel bpmnModel, String imageType,
                                             List<String> highLightedActivities, List<String> highLightedFlows,
                                             List<String> runningActivitiIdList, String activityFontName,
                                             String labelFontName, String annotationFontName,
                                             ClassLoader customClassLoader, double scaleFactor,
                                             boolean drawSequenceFlowNameWithNoLabelDi) {
        return generateCustomProcessDiagram(bpmnModel, imageType, highLightedActivities, highLightedFlows,
                runningActivitiIdList, activityFontName, labelFontName, annotationFontName, customClassLoader,
                scaleFactor, drawSequenceFlowNameWithNoLabelDi).generateImage(imageType);
    }

    protected CustomProcessDiagramCanvas generateCustomProcessDiagram(BpmnModel bpmnModel, String imageType,
                                                                      List<String> highLightedActivities,
                                                                      List<String> runningActivitiIdList,
                                                                      List<String> highLightedFlows,
                                                                      String activityFontName, String labelFontName,
                                                                      String annotationFontName,
                                                                      ClassLoader customClassLoader,
                                                                      double scaleFactor,
                                                                      boolean drawSequenceFlowNameWithNoLabelDi) {

        prepareBpmnModel(bpmnModel);
        CustomProcessDiagramCanvas processDiagramCanvas = initCustomProcessDiagramCanvas(bpmnModel, imageType,
                activityFontName, labelFontName, annotationFontName, customClassLoader);

        // Draw pool shape, if process is participant in collaboration
        for (Pool pool : bpmnModel.getPools()) {
            GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(pool.getId());
            processDiagramCanvas.drawPoolOrLane(pool.getName(), graphicInfo, scaleFactor);
        }

        // Draw lanes
        for (Process process : bpmnModel.getProcesses()) {
            for (Lane lane : process.getLanes()) {
                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(lane.getId());
                processDiagramCanvas.drawPoolOrLane(lane.getName(), graphicInfo, scaleFactor);
            }
        }

        // Draw activities and their sequence-flows
        for (Process process : bpmnModel.getProcesses()) {
            for (FlowNode flowNode : process.findFlowElementsOfType(FlowNode.class)) {
                if (!isPartOfCollapsedSubProcess(flowNode, bpmnModel)) {
                    drawCustomActivity(processDiagramCanvas, bpmnModel, flowNode, highLightedActivities,
                            runningActivitiIdList, highLightedFlows, scaleFactor, drawSequenceFlowNameWithNoLabelDi);
                }
            }
        }

        // Draw artifacts
        for (Process process : bpmnModel.getProcesses()) {

            for (Artifact artifact : process.getArtifacts()) {
                drawArtifact(processDiagramCanvas, bpmnModel, artifact);
            }

            List<SubProcess> subProcesses = process.findFlowElementsOfType(SubProcess.class, true);
            if (subProcesses != null) {
                for (SubProcess subProcess : subProcesses) {

                    GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(subProcess.getId());
                    if (graphicInfo != null && graphicInfo.getExpanded() != null && !graphicInfo.getExpanded()) {
                        continue;
                    }

                    if (!isPartOfCollapsedSubProcess(subProcess, bpmnModel)) {
                        for (Artifact subProcessArtifact : subProcess.getArtifacts()) {
                            drawArtifact(processDiagramCanvas, bpmnModel, subProcessArtifact);
                        }
                    }
                }
            }
        }

        return processDiagramCanvas;
    }

    protected static CustomProcessDiagramCanvas initCustomProcessDiagramCanvas(BpmnModel bpmnModel, String imageType,
                                                                               String activityFontName,
                                                                               String labelFontName,
                                                                               String annotationFontName,
                                                                               ClassLoader customClassLoader) {

        // We need to calculate maximum values to know how big the image will be in its entirety
        double minX = Double.MAX_VALUE;
        double maxX = 0;
        double minY = Double.MAX_VALUE;
        double maxY = 0;

        for (Pool pool : bpmnModel.getPools()) {
            GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(pool.getId());
            minX = graphicInfo.getX();
            maxX = graphicInfo.getX() + graphicInfo.getWidth();
            minY = graphicInfo.getY();
            maxY = graphicInfo.getY() + graphicInfo.getHeight();
        }

        List<FlowNode> flowNodes = gatherAllFlowNodes(bpmnModel);
        for (FlowNode flowNode : flowNodes) {

            GraphicInfo flowNodeGraphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());

            // width
            if (flowNodeGraphicInfo.getX() + flowNodeGraphicInfo.getWidth() > maxX) {
                maxX = flowNodeGraphicInfo.getX() + flowNodeGraphicInfo.getWidth();
            }
            if (flowNodeGraphicInfo.getX() < minX) {
                minX = flowNodeGraphicInfo.getX();
            }
            // height
            if (flowNodeGraphicInfo.getY() + flowNodeGraphicInfo.getHeight() > maxY) {
                maxY = flowNodeGraphicInfo.getY() + flowNodeGraphicInfo.getHeight();
            }
            if (flowNodeGraphicInfo.getY() < minY) {
                minY = flowNodeGraphicInfo.getY();
            }

            for (SequenceFlow sequenceFlow : flowNode.getOutgoingFlows()) {
                List<GraphicInfo> graphicInfoList = bpmnModel.getFlowLocationGraphicInfo(sequenceFlow.getId());
                if (graphicInfoList != null) {
                    for (GraphicInfo graphicInfo : graphicInfoList) {
                        // width
                        if (graphicInfo.getX() > maxX) {
                            maxX = graphicInfo.getX();
                        }
                        if (graphicInfo.getX() < minX) {
                            minX = graphicInfo.getX();
                        }
                        // height
                        if (graphicInfo.getY() > maxY) {
                            maxY = graphicInfo.getY();
                        }
                        if (graphicInfo.getY() < minY) {
                            minY = graphicInfo.getY();
                        }
                    }
                }
            }
        }

        List<Artifact> artifacts = gatherAllArtifacts(bpmnModel);
        for (Artifact artifact : artifacts) {

            GraphicInfo artifactGraphicInfo = bpmnModel.getGraphicInfo(artifact.getId());

            if (artifactGraphicInfo != null) {
                // width
                if (artifactGraphicInfo.getX() + artifactGraphicInfo.getWidth() > maxX) {
                    maxX = artifactGraphicInfo.getX() + artifactGraphicInfo.getWidth();
                }
                if (artifactGraphicInfo.getX() < minX) {
                    minX = artifactGraphicInfo.getX();
                }
                // height
                if (artifactGraphicInfo.getY() + artifactGraphicInfo.getHeight() > maxY) {
                    maxY = artifactGraphicInfo.getY() + artifactGraphicInfo.getHeight();
                }
                if (artifactGraphicInfo.getY() < minY) {
                    minY = artifactGraphicInfo.getY();
                }
            }

            List<GraphicInfo> graphicInfoList = bpmnModel.getFlowLocationGraphicInfo(artifact.getId());
            if (graphicInfoList != null) {
                for (GraphicInfo graphicInfo : graphicInfoList) {
                    // width
                    if (graphicInfo.getX() > maxX) {
                        maxX = graphicInfo.getX();
                    }
                    if (graphicInfo.getX() < minX) {
                        minX = graphicInfo.getX();
                    }
                    // height
                    if (graphicInfo.getY() > maxY) {
                        maxY = graphicInfo.getY();
                    }
                    if (graphicInfo.getY() < minY) {
                        minY = graphicInfo.getY();
                    }
                }
            }
        }

        int nrOfLanes = 0;
        for (Process process : bpmnModel.getProcesses()) {
            for (Lane l : process.getLanes()) {

                nrOfLanes++;

                GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(l.getId());
                // // width
                if (graphicInfo.getX() + graphicInfo.getWidth() > maxX) {
                    maxX = graphicInfo.getX() + graphicInfo.getWidth();
                }
                if (graphicInfo.getX() < minX) {
                    minX = graphicInfo.getX();
                }
                // height
                if (graphicInfo.getY() + graphicInfo.getHeight() > maxY) {
                    maxY = graphicInfo.getY() + graphicInfo.getHeight();
                }
                if (graphicInfo.getY() < minY) {
                    minY = graphicInfo.getY();
                }
            }
        }

        // Special case, see https://activiti.atlassian.net/browse/ACT-1431
        if (flowNodes.isEmpty() && bpmnModel.getPools().isEmpty() && nrOfLanes == 0) {
            // Nothing to show
            minX = 0;
            minY = 0;
        }

        return new CustomProcessDiagramCanvas((int) maxX + 10, (int) maxY + 10, (int) minX, (int) minY, imageType,
                activityFontName, labelFontName, annotationFontName, customClassLoader);
    }

    protected void drawCustomActivity(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel,
                                      FlowNode flowNode, List<String> highLightedActivities,
                                      List<String> runningActivitiIdList, List<String> highLightedFlows,
                                      double scaleFactor, boolean drawSequenceFlowNameWithNoLabelDi) {

        drawInstruction(processDiagramCanvas, bpmnModel, flowNode, highLightedActivities, runningActivitiIdList,
                scaleFactor);

        // Outgoing transitions of activity
        for (SequenceFlow sequenceFlow : flowNode.getOutgoingFlows()) {
            boolean highLighted = (highLightedFlows.contains(sequenceFlow.getId()));
            String defaultFlow = null;
            if (flowNode instanceof Activity) {
                defaultFlow = ((Activity) flowNode).getDefaultFlow();
            } else if (flowNode instanceof Gateway) {
                defaultFlow = ((Gateway) flowNode).getDefaultFlow();
            }

            boolean isDefault = false;
            if (defaultFlow != null && defaultFlow.equalsIgnoreCase(sequenceFlow.getId())) {
                isDefault = true;
            }
            boolean drawConditionalIndicator =
                    sequenceFlow.getConditionExpression() != null && !(flowNode instanceof Gateway);

            String sourceRef = sequenceFlow.getSourceRef();
            String targetRef = sequenceFlow.getTargetRef();
            FlowElement sourceElement = bpmnModel.getFlowElement(sourceRef);
            FlowElement targetElement = bpmnModel.getFlowElement(targetRef);
            List<GraphicInfo> graphicInfoList = bpmnModel.getFlowLocationGraphicInfo(sequenceFlow.getId());
            if (graphicInfoList != null && graphicInfoList.size() > 0) {
                graphicInfoList = connectionPerfectionizer(processDiagramCanvas, bpmnModel, sourceElement,
                        targetElement, graphicInfoList);
                int[] xPoints = new int[graphicInfoList.size()];
                int[] yPoints = new int[graphicInfoList.size()];

                for (int i = 1; i < graphicInfoList.size(); i++) {
                    GraphicInfo graphicInfo = graphicInfoList.get(i);
                    GraphicInfo previousGraphicInfo = graphicInfoList.get(i - 1);

                    if (i == 1) {
                        xPoints[0] = (int) previousGraphicInfo.getX();
                        yPoints[0] = (int) previousGraphicInfo.getY();
                    }
                    xPoints[i] = (int) graphicInfo.getX();
                    yPoints[i] = (int) graphicInfo.getY();

                }

                processDiagramCanvas.drawSequenceflow(xPoints, yPoints, drawConditionalIndicator, isDefault,
                        highLighted, scaleFactor);

                // Draw sequenceflow label
                GraphicInfo labelGraphicInfo = bpmnModel.getLabelGraphicInfo(sequenceFlow.getId());
                if (labelGraphicInfo != null) {
                    processDiagramCanvas.drawLabel(sequenceFlow.getName(), labelGraphicInfo, false);
                } else {
                    if (drawSequenceFlowNameWithNoLabelDi) {
                        GraphicInfo lineCenter = getLineCenter(graphicInfoList);
                        processDiagramCanvas.drawLabel(sequenceFlow.getName(), lineCenter, false);
                    }

                }
            }
        }

        // Nested elements
        if (flowNode instanceof FlowElementsContainer) {
            for (FlowElement nestedFlowElement : ((FlowElementsContainer) flowNode).getFlowElements()) {
                if (nestedFlowElement instanceof FlowNode && !isPartOfCollapsedSubProcess(nestedFlowElement,
                        bpmnModel)) {
                    drawCustomActivity(processDiagramCanvas, bpmnModel, (FlowNode) nestedFlowElement,
                            highLightedActivities, highLightedFlows, runningActivitiIdList, scaleFactor,
                            drawSequenceFlowNameWithNoLabelDi);
                }
            }
        }
    }

    private void drawInstruction(CustomProcessDiagramCanvas processDiagramCanvas, BpmnModel bpmnModel,
                                 FlowNode flowNode, List<String> highLightedActivities,
                                 List<String> runningActivitiIdList, double scaleFactor) {
        ActivityDrawInstruction drawInstruction = activityDrawInstructions.get(flowNode.getClass());
        if (drawInstruction != null) {

            drawInstruction.draw(processDiagramCanvas, bpmnModel, flowNode);

            // Gather info on the multi instance marker
            boolean multiInstanceSequential = false;
            boolean multiInstanceParallel = false;
            boolean collapsed = false;
            if (flowNode instanceof Activity) {
                Activity activity = (Activity) flowNode;
                MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = activity.getLoopCharacteristics();
                if (multiInstanceLoopCharacteristics != null) {
                    multiInstanceSequential = multiInstanceLoopCharacteristics.isSequential();
                    multiInstanceParallel = !multiInstanceSequential;
                }
            }

            // Gather info on the collapsed marker
            GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
            if (flowNode instanceof SubProcess) {
                collapsed = graphicInfo.getExpanded() != null && !graphicInfo.getExpanded();
            } else if (flowNode instanceof CallActivity) {
                collapsed = true;
            }

            BigDecimal bigDecimal1 = BigDecimal.valueOf(1.0);
            BigDecimal scaleFactorBigDecimal = BigDecimal.valueOf(scaleFactor);

            if (bigDecimal1.equals(scaleFactorBigDecimal)) {
                // Actually draw the markers
                processDiagramCanvas.drawActivityMarkers((int) graphicInfo.getX(), (int) graphicInfo.getY(),
                        (int) graphicInfo.getWidth(), (int) graphicInfo.getHeight(), multiInstanceSequential,
                        multiInstanceParallel, collapsed);
            }

            // Draw highlighted activities
            // if (highLightedActivities.contains(flowNode.getId())) {
            // drawHighLight(processDiagramCanvas, bpmnModel.getGraphicInfo(flowNode.getId()));
            // }
            if (runningActivitiIdList.contains(flowNode.getId())) {
                processDiagramCanvas.drawCustomHighLight((int) graphicInfo.getX(), (int) graphicInfo.getY(),
                        (int) graphicInfo.getWidth(), (int) graphicInfo.getHeight(), Color.GREEN);
            } else if (highLightedActivities.contains(flowNode.getId())) {
                processDiagramCanvas.drawHighLight((int) graphicInfo.getX(), (int) graphicInfo.getY(),
                        (int) graphicInfo.getWidth(), (int) graphicInfo.getHeight());

            }
        }
    }
}