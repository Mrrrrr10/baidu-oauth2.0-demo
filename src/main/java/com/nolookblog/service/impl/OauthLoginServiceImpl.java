package com.nolookblog.service.impl;

import com.nolookblog.common.OauthConstants;
import com.nolookblog.config.OauthConfig;
import com.nolookblog.service.OauthLoginService;
import com.nolookblog.service.model.AuthorizationModel;
import com.nolookblog.service.model.BaiduUserModel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.MessageFormat;

/**
 * @author Mrrrrr10
 * @github https://github.com/Mrrrrr10
 * @blog https://nolookblog.com/
 * @description OAuth 2.0登录 服务接口实现类
 */

@Service
public class OauthLoginServiceImpl implements OauthLoginService {

	@Autowired
	private OauthConfig oauthConfig;

	@Autowired
	private RestTemplate restTemplate;

	private Logger logger = LoggerFactory.getLogger(OauthLoginServiceImpl.class);

	/**
	 * OAuth 2.0 用户登录
	 *
	 * @param request
	 * @return
	 */
	@Override
	public String login(HttpServletRequest request) {
		// session
		HttpSession session = request.getSession();

		// 当前请求路径(http://localhost:8080/oauth2.0/login)
		String currentUrl = request.getRequestURL().toString();
		logger.info("[currentUrl: {}]", currentUrl);

		// 当前系统OAuth 2.0登录之前的URL
		String callbackUrl = request.getParameter("callbackUrl");
		if (StringUtils.isNoneBlank(callbackUrl)) {
			// 如果存在回调URL, 则将这个URL添加到session
			session.setAttribute("callbackUrl", callbackUrl);
			logger.info("[request param callbackUrl: {}]", callbackUrl);
		}

		// 当前系统请求认证服务器成功之后返回的Authorization Code
		String authorizationCode = request.getParameter("code");
		logger.info("[authorizationCode: {}]", authorizationCode);

		// 重定向请求
		String doRedirect = "redirect:";

		if (StringUtils.isBlank(authorizationCode)) {
			// authorizationCode为空, 则说明当前请求不是认证服务器的回调请求, 那么重定向URL到百度OAuth 2.0登录

			// 重定向请求百度OAuth 2.0登录
			doRedirect += MessageFormat.format(
					oauthConfig.authorizationUrl, oauthConfig.clientId, currentUrl);

		} else {
			// authorizationCode不为空, 通过authorizationCode获取accessToken

			// 1.请求accessTokenUrl, 获取accessToken
			ResponseEntity<AuthorizationModel> authorizationModelResponseEntity =
					restTemplate.getForEntity(oauthConfig.accessTokenUrl, AuthorizationModel.class,
							oauthConfig.clientId, oauthConfig.clientSecret,
							authorizationCode, currentUrl);

			AuthorizationModel authorizationModel = authorizationModelResponseEntity.getBody();

			if (authorizationModel != null) {
				// 获取accessToken
				String accessToken = authorizationModel.getAccess_token();

				if (StringUtils.isNoneBlank(accessToken)) {
					// 2.将accessToken存到session
					session.setAttribute(OauthConstants.SESSION_ACCESS_TOKEN, accessToken);

					// 3.请求userInfoUrl, 获取用户基础信息, 并将userId存到session
					ResponseEntity<BaiduUserModel> baiduUserModelResponseEntity =
							restTemplate.getForEntity(oauthConfig.userInfoUrl, BaiduUserModel.class,
									accessToken);

					BaiduUserModel baiduUserModel = baiduUserModelResponseEntity.getBody();

					if (baiduUserModel != null) {
						// 获取userId
						String userId = baiduUserModel.getUserid();

						if (StringUtils.isNoneBlank(userId)) {
							session.setAttribute(OauthConstants.SESSION_USER_ID, userId);
						}
					}
				}
			}

			// 4.从session中获取回调URL, 并返回
			callbackUrl = (String) session.getAttribute("callbackUrl");
			session.removeAttribute("callbackUrl");

			if (StringUtils.isNoneBlank(callbackUrl)) {
				// 回到请求前的url
				doRedirect += callbackUrl;
			} else {
				// 用户中心
				doRedirect += "/user/index";
			}
		}

		logger.info("[doRedirect: {}]", doRedirect);

		return doRedirect;
	}
}
