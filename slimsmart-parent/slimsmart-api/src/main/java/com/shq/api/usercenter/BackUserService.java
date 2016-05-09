package com.shq.api.usercenter;

import com.shq.common.service.BaseService;
import com.shq.model.usercenter.BackUser;

/**
 * <b>后台用户接口</b><br/>
 * <p>提供后台用户管理接口：包括用户注册、登录、密码等</p>
 * @author zhutianwei
 */
public interface BackUserService extends BaseService<BackUser>{
	
	/**
	 * 更新密码，用于修改密码和密码重置
	 * @param id 用户Id
	 * @param passwordMd5  密码明文的一次MD5
	 */
	void updatePassword(String id ,String passwordMd5);
	
	/**
	 * 查询登录名称是否存在
	 * @param loginName
	 * @return true 存在 false 不存在
	 */
	boolean isExistsLoginName(String loginName);
	
	/**
	 * 根据登录名称获取用户信息
	 * @param loginName 用户登录名称
	 * @return
	 */
	BackUser getBackUserByLoginName(String loginName);
}
