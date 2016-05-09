package com.shq.service.usercenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shq.common.service.AbstractBaseService;
import com.shq.common.util.UUID;
import com.shq.common.util.string.StringUtil;
import com.shq.dao.usercenter.RoleDao;
import com.slimsmart.api.usercenter.RoleService;
import com.slimsmart.model.usercenter.Role;

/**
 * 角色接口实现
 * @author zhutianwei
 *
 */
@Service
public class RoleServiceImpl extends AbstractBaseService<Role> implements RoleService {
	
	@Autowired
	private RoleDao roleDao;

	@Override
	public boolean isExistsBackUser(String roleId) {
		return roleDao.isExistsBackUser(roleId);
	}

	@Override
	public List<String> getPermIdListByRoleId(String roleId) {
		return roleDao.getPermIdListByRoleId(roleId);
	}

	@Override
	public void bindRolePermIds(String roleId, String permIds) {
		if(StringUtil.isEmpty(permIds)){
			return;
		}
		String[] ids = permIds.split(",");
		Set<String> idSet = new HashSet<String>();
		for(String id : ids){
			idSet.add(id);
		}
		List<Map<String,String>> bindMapList = new ArrayList<Map<String,String>>();
		for(String id : idSet){
			Map<String,String> bindMap = new HashMap<String, String>();
			bindMap.put("id", UUID.getUUID());
			bindMap.put("roleId", roleId);
			bindMap.put("permId", id);
			bindMapList.add(bindMap);
		}
		//删除之前的权限绑定
		roleDao.deletePermIdsByRoleId(roleId);
		//添加绑定新的权限Id
		roleDao.bindRolePermId(bindMapList);
	}

	@Override
	public Role getRoleByUserId(String userId) {
		return roleDao.getRoleByUserId(userId);
	}
}
