package com.shq.service.usercenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shq.api.usercenter.SystemService;
import com.shq.common.service.AbstractBaseService;
import com.shq.dao.usercenter.SystemDao;

/**
 * 系统管理接口实现
 * @author zhutianwei
 *
 */
@Service
public class SystemServiceImpl extends AbstractBaseService<com.shq.model.usercenter.System> implements SystemService {

	@Autowired
	private SystemDao systemDao;

	@Override
	public boolean isExistsCode(String code) {
		return systemDao.isExistsCode(code);
	}

	@Override
	public void delete(String id) {
		//删除角色绑定的权限
		systemDao.deleteRolePermBySystemId(id);
		//删除权限
		systemDao.deletePermBySystemId(id);
		//删除系统
		super.delete(id);
	}
}
