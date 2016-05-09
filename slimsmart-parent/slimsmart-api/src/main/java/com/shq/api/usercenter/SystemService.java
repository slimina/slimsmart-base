package com.shq.api.usercenter;

import com.shq.common.service.BaseService;

/**
 * <b>系统管理接口</b><br/>
 * <p>提供系统管理接口：包括系统新增、删除、修改、及状态变更等</p>
 * @author zhutianwei
 */
public interface SystemService extends BaseService<com.shq.model.usercenter.System> {

	/***
	 * 检查系统编码是否存在  
	 * @param code 系统编码
	 * @return
	 */
	boolean isExistsCode(String code);
}
