package com.slimsmart.dao.usercenter;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.slimsmart.common.dao.MyBatisDao;
import com.slimsmart.model.usercenter.Perm;

/**
 * 资源权限dao
 * @author zhutianwei
 */
@Repository
public class PermDao extends MyBatisDao<Perm>{

	public boolean isExistsCode(Map<String,String> params) {
		long count = count("isExistsCode", params);
		return count > 0 ? true : false;
	}
	
	public void deleteRolePermByPermId(String permId){
		delete("deleteRolePermByPermId", permId);
	}
	
	public void deletePermByPid(String permId){
		delete("deletePermByPid", permId);
	}
	
	public List<Perm> getMenuListByRoleId(Map<String,Object> params){
		return findList("getMenuListByRoleId", params);
	}
	
	public List<Perm> getFuncListByRoleId(Map<String,Object> params){
		return findList("getFuncListByRoleId", params);
	}
}
