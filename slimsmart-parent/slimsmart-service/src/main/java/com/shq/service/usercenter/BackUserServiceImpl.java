package com.shq.service.usercenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shq.common.service.AbstractBaseService;
import com.shq.common.util.PasswordUtil;
import com.shq.dao.usercenter.BackUserDao;
import com.shq.model.usercenter.BackUser;
import com.shq.model.usercenter.support.UserStatus;
import com.slimsmart.api.usercenter.BackUserService;

/**
 * 后台用户实现
 * @author zhutianwei
 *
 */
@Service
public class BackUserServiceImpl  extends AbstractBaseService<BackUser> implements BackUserService {
	
	@Autowired
	private BackUserDao backUserDao;

	@Override
	public BackUser save(BackUser backUser) {
		//初始创建为正常状态
		backUser.setStatus(UserStatus.NORMAL.getKey());
		backUser.setPassword(PasswordUtil.getSaltMD5(backUser.getPassword()));
		//保存用户
		backUser = super.save(backUser);
		//保存用户与角色关系
		backUserDao.insertBindRole(backUser.getId(), backUser.getRoleId());
		return backUser;
	}

	@Override
	public void updatePassword(String id, String passwordMd5) {
		BackUser backUser = new BackUser();
		backUser.setId(id);
		backUser.setPassword(PasswordUtil.getSaltMD5(passwordMd5));
		super.update(backUser);
	}

	@Override
	public void delete(String id) {
		backUserDao.deleteBindRole(id);
		super.delete(id);
	}

	@Override
	public BackUser get(String id) {
		BackUser backUser = super.get(id);
		backUser.setRoleId(backUserDao.getBindRoleId(id));
		return backUser;
	}

	@Override
	public int update(BackUser entity) {
		backUserDao.updateBindRole(entity.getId(), entity.getRoleId());
		return super.update(entity);
	}

	@Override
	public boolean isExistsLoginName(String loginName) {
		return backUserDao.isExistsLoginName(loginName);
	}

	@Override
	public BackUser getBackUserByLoginName(String loginName) {
		return backUserDao.getBackUserByLoginName(loginName);
	}
}
