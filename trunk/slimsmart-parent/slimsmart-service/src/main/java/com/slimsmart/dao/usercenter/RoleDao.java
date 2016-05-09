package com.slimsmart.dao.usercenter;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.slimsmart.common.dao.MyBatisDao;
import com.slimsmart.model.usercenter.Role;

/**
 * 角色Dao
 * @author zhutianwei
 */
@Repository
public class RoleDao extends MyBatisDao<Role>{

	public boolean isExistsBackUser(String roleId){
		long count = count("isExistsBackUser", roleId);
		return count > 0 ? true : false;
	}
	
	public List<String> getPermIdListByRoleId(String roleId) {
		return findList("getPermIdListByRoleId", roleId);
	}
	
	public void bindRolePermId(List<Map<String,String>> mapList){
		batchInsert("bindRolePermId", mapList);
	}
	
	public void deletePermIdsByRoleId(String roleId){
		delete("deletePermIdsByRoleId", roleId);
	}
	
	public Role getRoleByUserId(String userId){
		return get("getRoleByUserId", userId);
	}
	
}
