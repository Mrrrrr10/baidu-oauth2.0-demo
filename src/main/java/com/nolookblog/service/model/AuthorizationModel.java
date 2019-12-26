package com.nolookblog.service.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * @author Mrrrrr10
 * @github https://github.com/Mrrrrr10
 * @blog https://nolookblog.com/
 * @description 请求Access Token的返回信息
 */

@Data
public class AuthorizationModel {
	/**
	 * 要获取的Access Token（30天的有效期）
	 */
	private String access_token;

	/**
	 * 用于刷新Access Token 的 Refresh Token（10年的有效期）
	 */
	private String refresh_token;

	/**
	 * Access Token最终的访问范围
	 */
	private String scope;

	/**
	 * Access Token的有效期，以秒为单位（30天的有效期）
	 */
	private Long expires_in;

	/**
	 * 基于http调用Open API时所需要的Session Key，其有效期与Access Token一致
	 */
	private String session_key;

	/**
	 * 基于http调用Open API时计算参数签名用的签名密钥
	 */
	private String session_secret;

	/**
	 * 错误信息
	 */
	private String error;

	/**
	 * 错误描述
	 */
	private String error_description;

	/**
	 * Java Bean 转换成 Json字符串
	 *
	 * TODO: 不要在Java Bean的getXXX方法中调用JSON.toJSONString方法，否则会导致StackOverflowError
	 * TODO: 因为FastJson在序列化的时候，会根据一系列规则获取一个对象中的所有getter方法，然后依次执行。
	 * TODO: 解决方法
	 * 		1、方法名不以get开头
	 * 		2、使用@JSONField(serialize = false)修饰目标方法
	 * 		3、使用@JSONType修饰该Bean，并ignore掉方法对应的属性名（getXxx -> xxx）
	 *
	 * @return
	 */
	public String toJsonString(){
		return JSON.toJSONString(this);
	}
}
