package com.zjmzxfzhl.common.core.permission.wrapper;

import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.Configuration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 庄金明
 */
public class PermissionWrapper extends AbstractPermissionWrapper<String, PermissionWrapper> {

    private static final long serialVersionUID = 1L;

    public PermissionWrapper() {
        this(null, null);
    }

    public PermissionWrapper(Configuration configuration, String additionalParameterName) {
        super.initNeed(configuration, additionalParameterName);
    }

    private PermissionWrapper(AtomicInteger paramNameSeq, Map<String, Object> paramNameValuePairs,
                              MergeSegments mergeSegments, Configuration configuration,
                              List<ParameterMapping> parameterMappings, String additionalParameterName) {
        this.paramNameSeq = paramNameSeq;
        this.paramNameValuePairs = paramNameValuePairs;
        this.expression = mergeSegments;
        this.configuration = configuration;
        this.parameterMappings = parameterMappings;
        this.additionalParameterName = additionalParameterName;
    }

    @Override
    protected PermissionWrapper instance() {
        return new PermissionWrapper(paramNameSeq, paramNameValuePairs, new MergeSegments(), configuration,
                parameterMappings, additionalParameterName);
    }
}
