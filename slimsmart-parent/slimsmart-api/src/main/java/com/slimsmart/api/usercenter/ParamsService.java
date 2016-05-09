package com.slimsmart.api.usercenter;

import com.shq.common.service.BaseService;
import com.shq.model.usercenter.Params;

public interface ParamsService extends BaseService<Params>{
	boolean isExistsKey(String key);
	Params getParamsByKey(String key);
	<X> X getValueByKey(String key,X x);
	void updateByKey(String key,String value);
}
