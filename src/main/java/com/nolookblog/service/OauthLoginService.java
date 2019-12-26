package com.nolookblog.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mrrrrr10
 * @github https://github.com/Mrrrrr10
 * @blog https://nolookblog.com/
 * @description OAuth 2.0登录 服务接口
 */
public interface OauthLoginService {

    /**
     * OAuth 2.0 用户登录
     *
     * @param request
     * @return
     */
	String login(HttpServletRequest request);

}