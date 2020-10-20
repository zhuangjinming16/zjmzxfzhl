package com.zjmzxfzhl.modules.flowable.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjmzxfzhl.common.core.Result;
import com.zjmzxfzhl.common.core.constant.Constants;
import com.zjmzxfzhl.common.core.util.ObjectUtils;
import com.zjmzxfzhl.common.log.annotation.Log;
import com.zjmzxfzhl.modules.flowable.common.BaseFlowableController;
import com.zjmzxfzhl.modules.flowable.common.FlowablePage;
import com.zjmzxfzhl.modules.flowable.common.ResponseFactory;
import com.zjmzxfzhl.modules.flowable.common.cmd.DeployModelCmd;
import com.zjmzxfzhl.modules.flowable.common.cmd.SaveModelEditorCmd;
import com.zjmzxfzhl.modules.flowable.vo.ModelRequest;
import com.zjmzxfzhl.modules.flowable.vo.ModelResponse;
import com.zjmzxfzhl.modules.flowable.vo.query.ModelQueryVo;
import com.zjmzxfzhl.modules.flowable.wapper.ModelListWrapper;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.api.query.QueryProperty;
import org.flowable.engine.impl.ModelQueryProperty;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ModelQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flowable/model")
public class ModelController extends BaseFlowableController {
    @Autowired
    protected ResponseFactory responseFactory;
    @Autowired
    protected ObjectMapper objectMapper;

    private static Map<String, QueryProperty> ALLOWED_SORT_PROPERTIES = new HashMap<>();

    static {
        ALLOWED_SORT_PROPERTIES.put("id", ModelQueryProperty.MODEL_ID);
        ALLOWED_SORT_PROPERTIES.put("category", ModelQueryProperty.MODEL_CATEGORY);
        ALLOWED_SORT_PROPERTIES.put("createTime", ModelQueryProperty.MODEL_CREATE_TIME);
        ALLOWED_SORT_PROPERTIES.put("key", ModelQueryProperty.MODEL_KEY);
        ALLOWED_SORT_PROPERTIES.put("lastUpdateTime", ModelQueryProperty.MODEL_LAST_UPDATE_TIME);
        ALLOWED_SORT_PROPERTIES.put("name", ModelQueryProperty.MODEL_NAME);
        ALLOWED_SORT_PROPERTIES.put("version", ModelQueryProperty.MODEL_VERSION);
        ALLOWED_SORT_PROPERTIES.put("tenantId", ModelQueryProperty.MODEL_TENANT_ID);
    }

    @GetMapping(value = "/list")
    public Result list(ModelQueryVo modelQueryVo) {
        ModelQuery modelQuery = repositoryService.createModelQuery();

        if (ObjectUtils.isNotEmpty(modelQueryVo.getModelId())) {
            modelQuery.modelId(modelQueryVo.getModelId());
        }
        if (ObjectUtils.isNotEmpty(modelQueryVo.getModelCategory())) {
            modelQuery.modelCategoryLike(ObjectUtils.convertToLike(modelQueryVo.getModelCategory()));
        }
        if (ObjectUtils.isNotEmpty(modelQueryVo.getModelName())) {
            modelQuery.modelNameLike(modelQueryVo.getModelName());
        }
        if (ObjectUtils.isNotEmpty(modelQueryVo.getModelKey())) {
            modelQuery.modelKey(modelQueryVo.getModelKey());
        }
        if (ObjectUtils.isNotEmpty(modelQueryVo.getModelVersion())) {
            modelQuery.modelVersion(modelQueryVo.getModelVersion());
        }
        if (modelQueryVo.getLatestVersion() != null) {
            if (modelQueryVo.getLatestVersion()) {
                modelQuery.latestVersion();
            }
        }
        if (ObjectUtils.isNotEmpty(modelQueryVo.getDeploymentId())) {
            modelQuery.deploymentId(modelQueryVo.getDeploymentId());
        }
        if (modelQueryVo.getDeployed() != null) {
            if (modelQueryVo.getDeployed()) {
                modelQuery.deployed();
            } else {
                modelQuery.notDeployed();
            }
        }
        if (ObjectUtils.isNotEmpty(modelQueryVo.getTenantId())) {
            modelQuery.modelTenantId(modelQueryVo.getTenantId());
        }

        FlowablePage page = this.pageList(modelQueryVo, modelQuery, ModelListWrapper.class, ALLOWED_SORT_PROPERTIES);
        return Result.ok(page);
    }

    @GetMapping(value = "/queryById")
    public Result queryById(@RequestParam String id) throws UnsupportedEncodingException {
        Model model = getModelById(id);
        ModelResponse modelResponse = responseFactory.createModelResponse(model);
        if (model.hasEditorSource()) {
            byte[] editorBytes = repositoryService.getModelEditorSource(model.getId());
            String editor = new String(editorBytes, "UTF-8");
            modelResponse.setEditor(editor);
        }
        return Result.ok(modelResponse);
    }

    protected Model getModelById(String modelId) {
        Model model = repositoryService.getModel(modelId);
        if (model == null) {
            throw new FlowableObjectNotFoundException("No model found with id " + modelId);
        }
        return model;
    }

    protected void checkModelKeyExists(String modelKey) {
        long countNum = repositoryService.createModelQuery().modelKey(modelKey).count();
        if (countNum > 0) {
            throw new FlowableObjectNotFoundException("ModelKey already exists with id " + modelKey);
        }
    }

    @Log(value = "新增流程模型")
    @PreAuthorize("@elp.single('flowable:model:save')")
    @PostMapping(value = "/save")
    @Transactional(rollbackFor = Exception.class)
    public Result save(@RequestBody ModelRequest modelRequest) {
        checkModelKeyExists(modelRequest.getKey());

        Model model = repositoryService.newModel();
        model.setKey(modelRequest.getKey());
        model.setName(modelRequest.getName());
        model.setVersion(1);
        model.setMetaInfo(modelRequest.getMetaInfo());
        model.setTenantId(modelRequest.getTenantId());
        model.setCategory(modelRequest.getCategory());
        repositoryService.saveModel(model);

        return Result.ok();
    }

    @Log(value = "修改流程模型")
    @PreAuthorize("@elp.single('flowable:model:update')")
    @PutMapping(value = "/update")
    @Transactional(rollbackFor = Exception.class)
    public Result update(@RequestBody ModelRequest modelRequest) {
        managementService.executeCommand(new SaveModelEditorCmd(modelRequest.getId(), modelRequest.getKey(),
                modelRequest.getName(), modelRequest.getCategory(), modelRequest.getDescription()));

        return Result.ok();
    }

    @Log(value = "删除流程模型")
    @PreAuthorize("@elp.single('flowable:model:delete')")
    @DeleteMapping(value = "/delete")
    @Transactional(rollbackFor = Exception.class)
    public Result delete(@RequestParam String ids, @RequestParam(required = false) boolean cascade) {
        if (ids == null || ids.trim().length() == 0) {
            return Result.error("ids can't be empty");
        }
        String[] idsArr = ids.split(",");
        for (String id : idsArr) {
            Model model = getModelById(id);
            List<Model> models = repositoryService.createModelQuery().modelKey(model.getKey()).list();
            for (Model deleteModel : models) {
                if (cascade && deleteModel.getDeploymentId() != null) {
                    repositoryService.deleteDeployment(deleteModel.getDeploymentId(), cascade);
                }
                repositoryService.deleteModel(deleteModel.getId());
            }
        }

        return Result.ok();
    }

    @Log(value = "保存流程设计")
    @PreAuthorize("@elp.single('flowable:model:saveModelEditor')")
    @PutMapping(value = "/saveModelEditor")
    @Transactional(rollbackFor = Exception.class)
    public Result saveModelEditor(@RequestBody ModelRequest modelRequest) {
        managementService.executeCommand(new SaveModelEditorCmd(modelRequest.getId(), modelRequest.getEditor()));
        return Result.ok();
    }

    @Log(value = "部署流程模型")
    @PreAuthorize("@elp.single('flowable:model:deploy')")
    @PostMapping(value = "/deploy")
    @Transactional(rollbackFor = Exception.class)
    public Result deployModel(@RequestBody ModelRequest modelRequest) {
        managementService.executeCommand(new DeployModelCmd(modelRequest.getId()));
        return Result.ok();
    }

    @Log(value = "部署流程模型")
    @PreAuthorize("@elp.single('flowable:model:import')")
    @PostMapping(value = "/import")
    @Transactional(rollbackFor = Exception.class)
    public Result doImport(@RequestParam(required = false) String tenantId, HttpServletRequest request) throws IOException {
        if (!(request instanceof MultipartHttpServletRequest)) {
            throw new IllegalArgumentException("request must instance of MultipartHttpServletRequest");
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        if (multipartRequest.getFileMap().size() == 0) {
            throw new IllegalArgumentException("request file is empty");
        }
        MultipartFile file = multipartRequest.getFileMap().values().iterator().next();
        String fileName = file.getOriginalFilename();
        boolean isFileNameInValid =
                ObjectUtils.isEmpty(fileName) || !(fileName.endsWith(".bpmn20.xml") || fileName.endsWith(".bpmn") || fileName.toLowerCase().endsWith(".bar") || fileName.toLowerCase().endsWith(".zip"));
        if (isFileNameInValid) {
            throw new IllegalArgumentException("Request file must end with .bpmn20.xml,.bpmn|,.bar,.zip");
        }

        this.managementService.executeCommand(new SaveModelEditorCmd(null, tenantId, new String(file.getBytes(),
                Constants.CHARSET_NAME_UTF8)));

        return Result.ok();
    }
}
