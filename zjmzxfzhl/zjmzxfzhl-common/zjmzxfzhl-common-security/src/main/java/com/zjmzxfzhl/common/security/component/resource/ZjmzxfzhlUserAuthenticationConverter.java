package com.zjmzxfzhl.common.security.component.resource;

import com.zjmzxfzhl.common.core.security.SecurityUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 庄金明
 * @date
 */
public class ZjmzxfzhlUserAuthenticationConverter implements UserAuthenticationConverter {

    private static final String N_A = "N/A";

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(USERNAME, authentication.getName());
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        return response;
    }

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey(USERNAME)) {
            Collection<? extends GrantedAuthority> authorities = getAuthorities(map);

            /// String clientId = (String) map.get("clientId");
            String roleId = (String) map.get("roleId");
            String orgId = (String) map.get("orgId");
            String userRealName = (String) map.get("userRealName");
            String username = (String) map.get(USERNAME);
            Map<String, Object> additionalInformation = (Map<String, Object>) map.get("additionalInformation");
            SecurityUser securityUser = new SecurityUser(roleId, orgId, userRealName, additionalInformation, username
                    , N_A, true, true, true, true, authorities);
            return new UsernamePasswordAuthenticationToken(securityUser, N_A, authorities);
        }
        return null;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        Object authorities = map.get(AUTHORITIES);
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils.collectionToCommaDelimitedString((Collection<?>) authorities));
        }
        return AuthorityUtils.NO_AUTHORITIES;
    }

}
