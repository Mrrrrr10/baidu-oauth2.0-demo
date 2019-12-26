package com.nolookblog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mrrrrr10
 * @github https://github.com/Mrrrrr10
 * @blog https://nolookblog.com/
 * @description	OAuth 2.0 配置类
 */

@Configuration
public class OauthConfig {

	@Value("${baidu.oauth2.client-id}")
	public String clientId;

	@Value("${baidu.oauth2.scope}")
	public String scope;

	@Value("${baidu.oauth2.client-secret}")
	public String clientSecret;

	@Value("${baidu.oauth2.user-authorization-url}")
	public String authorizationUrl;

	@Value("${baidu.oauth2.access-token-url}")
	public String accessTokenUrl;

	@Value("${baidu.oauth2.resource.user-info-url}")
	public String userInfoUrl;
}
