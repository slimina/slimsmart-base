package com.slimsmart.dao.usercenter;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.slimsmart.common.dao.MyBatisDao;
import com.slimsmart.common.util.UUID;
import com.slimsmart.model.usercenter.BackUser;

/**
 * 后台用户数据库dao
 * @author zhutianwei
 *
 */
@Repository
public class BackUserDao extends MyBatisDao<BackUser>{
	
	/**
	 * 添加与角色绑定关系
	 * @param backUser
	 */
	public void insertBindRole(String userId,String roleId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", UUID.getUUID());
		params.put("userId", userId);
		params.put("roleId", roleId);
		insert("insertBindRole", params);
	}
	
	/**
	 * 更新角色绑定关系
	 * @param backUser
	 */
	public void updateBindRole(String userId,String roleId){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("roleId", roleId);
		update("updateBindRole", params);
	}
	
	/**
	 * 删除用户下的角色绑定关系
	 * @param userId
	 */
	public void deleteBindRole(String userId){
		delete("deleteBindRole", userId);
	}
	
	/**
	 * 获取用户绑定角色Id
	 * @param userId
	 * @return
	 */
	public String getBindRoleId(String userId){
		return get("getBindRoleId", userId);
	}
	
	public boolean isExistsLoginName(String loginName){
		long count = count("isExistsLoginName", loginName);
		return count > 0 ? true : false;
	}
	
	public BackUser getBackUserByLoginName(String loginName){
		return get("getBackUserByLoginName", loginName);
	}
}
