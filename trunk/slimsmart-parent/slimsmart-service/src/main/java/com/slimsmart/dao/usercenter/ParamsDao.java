package com.slimsmart.dao.usercenter;

import org.springframework.stereotype.Repository;

import com.slimsmart.common.dao.MyBatisDao;
import com.slimsmart.model.usercenter.Params;

@Repository
public class ParamsDao extends MyBatisDao<Params>{
	
	public boolean isExistsKey(String key){
		long count = count("isExistsKey", key);
		return count > 0 ? true : false;
	}
	
	public Params getByKey(String key){
		return get("getByKey", key);
	}
	
	public void updateByKey(String key,String value){
		Params params = new Params();
		params.setKey(key);
		params.setValue(value);
		update("updateByKey", params);
	}
}
