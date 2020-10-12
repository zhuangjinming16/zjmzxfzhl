package com.zjmzxfzhl.common.security.component.resource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix="zjmzxfzhl.resource.config")
public class ZjmzxfzhlResourceProperties {
    private List<AntMatchers> antMatchers =new ArrayList<>();
    private List<String> addFilterBeforeUsernamePasswordAuthenticationFilter;
    private List<String> ignoreUrls = new ArrayList<>();
}
