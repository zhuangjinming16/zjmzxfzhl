package com.zjmzxfzhl.common.security.service;

import com.zjmzxfzhl.common.core.constant.CacheConstants;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

/**
 * @author 庄金明
 */
public class RedisClientDetailsService extends JdbcClientDetailsService {

    /**
     * 扩展 默认的 ClientDetailsService, 增加逻辑删除判断(status = 1)
     */
    private static final String SELECT_CLIENT_DETAILS_SQL = "select client_id, client_secret, resource_ids, scope, " +
            "authorized_grant_types, " + "web_server_redirect_uri, authorities, access_token_validity, " +
            "refresh_token_validity, additional_information, autoapprove " + "from t_sys_oauth_client_details where " +
            "client_id = ? and `status` = 1 ";

    private static final String SELECT_FIND_STATEMENT = "select client_id, client_secret,resource_ids, scope, " +
            "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, " +
            "refresh_token_validity, additional_information, autoapprove from t_sys_oauth_client_details where " +
            "`status` = 1 order by client_id ";

    private RedisTemplate<String, Object> redisTemplate;

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisClientDetailsService(DataSource dataSource) {
        super(dataSource);
        setSelectClientDetailsSql(SELECT_CLIENT_DETAILS_SQL);
        setFindClientDetailsSql(SELECT_FIND_STATEMENT);
    }

    /**
     * 重写原生方法支持redis缓存
     *
     * @param clientId
     * @return
     */
    @Override
    @SneakyThrows
    @Cacheable(value = CacheConstants.OAUTH_CLIENT_DETAILS, key = "#clientId", unless = "#result == null")
    public ClientDetails loadClientByClientId(String clientId) {
        return super.loadClientByClientId(clientId);
    }
}
