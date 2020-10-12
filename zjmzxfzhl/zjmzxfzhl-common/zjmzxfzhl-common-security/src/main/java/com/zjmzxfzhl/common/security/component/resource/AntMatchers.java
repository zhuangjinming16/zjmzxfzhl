package com.zjmzxfzhl.common.security.component.resource;

import lombok.Data;

import java.util.List;

@Data
public class AntMatchers {
    private List<String> antPatterns;
    private List<String> hasAnyScopes;
}
