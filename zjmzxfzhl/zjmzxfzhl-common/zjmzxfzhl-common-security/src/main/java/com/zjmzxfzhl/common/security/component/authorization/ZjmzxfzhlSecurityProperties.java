package com.zjmzxfzhl.common.security.component.authorization;

import com.zjmzxfzhl.common.security.component.resource.AuthorizeRequest;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ZjmzxfzhlSecurityProperties {
    private List<AuthorizeRequest> authorizeRequests = new ArrayList<>();
}
