package com.slimsmart.service.usercenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slimsmart.common.service.AbstractBaseService;
import com.slimsmart.dao.usercenter.SystemDao;
import com.slimsmart.api.usercenter.SystemService;

/**
 * 系统管理接口实现
 * @author zhutianwei
 *
 */
@Service
public class SystemServiceImpl extends AbstractBaseService<com.slimsmart.model.usercenter.System> implements SystemService {

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
