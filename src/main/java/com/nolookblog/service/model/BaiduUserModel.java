package com.nolookblog.service.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;

/**
 * @author Mrrrrr10
 * @github https://github.com/Mrrrrr10
 * @blog https://nolookblog.com/
 * @description
 */

@Data
public class BaiduUserModel {

	/**
	 * 百度的userId
	 */
	private String userid;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 用户性别，0表示女性，1表示男性
	 */
	private Integer sex;

	/**
	 * 用户生日
	 */
	private String birthday;

	/**
	 * 用户描述
	 */
	private String userdetail;

	/**
	 * 是否绑定手机号
	 */
	private Integer is_bind_mobile;

	/**
	 * 是否已经实名认证
	 */
	private Integer is_realname;

	public String toJsonString(){
		return JSON.toJSONString(this);
	}
}
