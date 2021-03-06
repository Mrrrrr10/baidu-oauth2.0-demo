package com.nolookblog.interceptor;

import com.nolookblog.common.OauthConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Mrrrrr10
 * @github https://github.com/Mrrrrr10
 * @blog https://nolookblog.com/
 * @description 定义一些页面需要做登录检查
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	/**
	 * 检查是否已经登录
	 *
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
							 Object handler) throws Exception {
		HttpSession session = request.getSession();

		// 获取session中存储的token
		String accessToken = (String) session.getAttribute(OauthConstants.SESSION_ACCESS_TOKEN);
		logger.info("[accessToken: {}]", accessToken);

		if (StringUtils.isNoneBlank(accessToken)) {
			// token存在
			return true;
		} else {
			// 如果token不存在, 则跳转等登录页面

			// 当前请求路径
			String currentUrl = request.getRequestURL().toString();
			logger.info("[url: {}]", request.getContextPath() + "/oauth2.0/login?callbackUrl=" + currentUrl);
			response.sendRedirect(request.getContextPath() + "/oauth2.0/login?callbackUrl=" + currentUrl);

			return false;
		}
	}
}
