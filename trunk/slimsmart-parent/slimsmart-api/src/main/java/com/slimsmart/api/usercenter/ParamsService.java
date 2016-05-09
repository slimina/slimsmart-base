package com.slimsmart.api.usercenter;

import com.slimsmart.common.service.BaseService;
import com.slimsmart.model.usercenter.Params;

public interface ParamsService extends BaseService<Params>{
	boolean isExistsKey(String key);
	Params getParamsByKey(String key);
	<X> X getValueByKey(String key,X x);
	void updateByKey(String key,String value);
}
