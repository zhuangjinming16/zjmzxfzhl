package com.zjmzxfzhl.common.security.component.resource;

import com.zjmzxfzhl.common.core.constant.SecurityConstants;
import lombok.Data;
import org.springframework.http.HttpMethod;

import java.util.List;

@Data
public class AuthorizeRequest {
    private String type = SecurityConstants.ANT_MATCHERS;
    private String httpMethod;
    private List<String> antPatterns;
    private String method = "authenticated";
    private String methodParams;
}
