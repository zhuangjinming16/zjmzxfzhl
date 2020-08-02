package com.zjmzxfzhl.common.core.base;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.zjmzxfzhl.common.core.jackson.AuthorityDeserializer;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @author 庄金明
 */
@Data
public class UserInfo implements Serializable {

    /**
     * 用户基本信息
     */
    private String userId;

    private String userName;

    private String password;

    private String orgId;

    private String roleId;

    private Map<String, Object> additionalInformation;

    /**
     * 权限标识集合
     */
    @JsonDeserialize(using = AuthorityDeserializer.class)
    private Collection<? extends GrantedAuthority> authorities;

    public UserInfo() {
    }

    public UserInfo(String userId, String userName, String password, String orgId, String roleId,
                    Map<String, Object> additionalInformation, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.orgId = orgId;
        this.roleId = roleId;
        this.additionalInformation = additionalInformation;
        this.authorities = authorities;
    }
}
