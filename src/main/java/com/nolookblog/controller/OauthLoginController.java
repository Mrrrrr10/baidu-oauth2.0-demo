package com.nolookblog.controller;

import com.nolookblog.service.OauthLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mrrrrr10
 * @github https://github.com/Mrrrrr10
 * @blog https://nolookblog.com/
 * @description OAuth 2.0登录 控制器
 */

@Controller
public class OauthLoginController {

	@Autowired
	private OauthLoginService oauthLoginService;

	/**
	 * OAuth 2.0 用户登录
	 *
	 * @param request
	 * @return
	 */
	@GetMapping("/oauth2.0/login")
	public ModelAndView login(HttpServletRequest request) {
		String authorizationUrl = oauthLoginService.login(request);

		return new ModelAndView(authorizationUrl);
	}
}
