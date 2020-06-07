// package com.zjmzxfzhl.framework.config.security;
//
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
// import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
// import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
// import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
// import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
// import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
// import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
// import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
// import org.springframework.web.client.DefaultResponseErrorHandler;
// import org.springframework.web.client.RestTemplate;
//
/// ***
// * oauth2 服务配置**
// *
// * @author ruoyi
// */
// @Configuration
// @EnableResourceServer
// public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
//
// // @Autowired
// // private OAuth2ClientProperties oAuth2ClientProperties;
//
// @Bean
// public AuthIgnoreConfig authIgnoreConfig() {
// return new AuthIgnoreConfig();
// }
//
// @Bean
// public RestTemplate restTemplate() {
// RestTemplate restTemplate = new RestTemplate();
// restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
// return restTemplate;
// }
//
// @Bean
// public ResourceServerTokenServices tokenServices() {
// RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
// DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
// UserAuthenticationConverter userTokenConverter = new CommonUserConverter();
// accessTokenConverter.setUserTokenConverter(userTokenConverter);
// // remoteTokenServices.setCheckTokenEndpointUrl(resourceServerProperties.getTokenInfoUri());
// remoteTokenServices.setClientId("abc");
// remoteTokenServices.setClientSecret("def");
// remoteTokenServices.setRestTemplate(restTemplate());
// remoteTokenServices.setAccessTokenConverter(accessTokenConverter);
// return remoteTokenServices;
// }
//
// @Override
// public void configure(HttpSecurity http) throws Exception {
// http.csrf().disable();
// ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
// .authorizeRequests();
// // 不登录可以访问
// authIgnoreConfig().getUrls().forEach(url -> registry.antMatchers(url).permitAll());
// registry.anyRequest().authenticated();
// }
//
// @Override
// public void configure(ResourceServerSecurityConfigurer resources) {
// resources.tokenServices(tokenServices());
// }
// }
