package com.nolookblog.config;

import com.nolookblog.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author Mrrrrr10
 * @github https://github.com/Mrrrrr10
 * @blog https://nolookblog.com/
 * @description Web相关配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	/**
	 * 拦截器配置
	 *
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor())
				// 表示对所有请求已/user开头的请求进行拦截
				.addPathPatterns("/user/**")
				// .excludePathPatterns("/templates/**")
		;
	}

	/**
	 * 视图跳转控制器(页面跳转)
	 *
	 * @param registry
	 * @tips:
	 * 		如果需要访问一个页面，必须要写Controller类，然后再写一个方法跳转到页面，感觉好麻烦，其实重写WebMvcConfigurer中的addViewControllers方法即可达到效果了
	 *		直接访问http://localhost:8080/toLogin就跳转到login.jsp页面了
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// registry.addViewController("/toLogin").setViewName("login");
		registry.addViewController("/user/index").setViewName("userIndex");
	}

	/**
	 * 静态资源处理, 配置类优先于application.yaml
	 *
	 * @param registry
	 * @tips: 自定义静态资源映射目录, 访问: http://localhost:8080/my/index.html
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// addResourceHandler添加映射路径
		registry.addResourceHandler("/**")
				// addResourceLocations指定路径
				.addResourceLocations("classpath:/templates/");
	}
}
