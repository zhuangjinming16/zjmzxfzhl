package com.zjmzxfzhl.modules.flowable.vo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;

/**
 * @author 庄金明
 * @date 2020年8月30日
 */
@Data
public class ModelRequest {
    private String id;
    private String key;
    private String name;
    private String category;
    private String description;
    private String tenantId;
    private String editor;
    private boolean newVersion;
    private boolean cascade;

    public String getMetaInfo() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode metaInfo = objectMapper.createObjectNode();
        metaInfo.put("name", name);
        metaInfo.put("description", description);
        return metaInfo.toString();
    }

}
