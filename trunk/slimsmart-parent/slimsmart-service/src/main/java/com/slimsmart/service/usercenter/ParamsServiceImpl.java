package com.slimsmart.service.usercenter;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slimsmart.common.service.AbstractBaseService;
import com.slimsmart.common.util.JsonUtil;
import com.slimsmart.common.util.date.DateUtil;
import com.slimsmart.dao.usercenter.ParamsDao;
import com.slimsmart.api.usercenter.ParamsService;
import com.slimsmart.common.Constants;
import com.slimsmart.model.usercenter.Params;

@Service
public class ParamsServiceImpl extends AbstractBaseService<Params>implements ParamsService {

	@Autowired
	private ParamsDao paramsDao;

	@Override
	public boolean isExistsKey(String key) {
		return paramsDao.isExistsKey(key);
	}

	@Override
	public Params getParamsByKey(String key) {
		return paramsDao.getByKey(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <X> X getValueByKey(String key,X x) {
		Params params = getParamsByKey(key);
		if(params!=null){
			if(x instanceof Integer){
				return (X) Integer.valueOf(params.getValue());
			}else if(x instanceof Double){
				return (X) Double.valueOf(params.getValue());
			}else if(x instanceof Long){
				return (X) Long.valueOf(params.getValue());
			}else if(x instanceof String){
				return (X) params.getValue();
			}else if(x instanceof Date){
				if(params.getValue().length() == 10){
					return (X)DateUtil.formatStringToDate(params.getValue());
				}else{
					return (X)DateUtil.formatStringToSimpleDate(params.getValue());
				}
			}else if(x instanceof Boolean){
				if(Constants.YES.equals(params.getValue())){
					return (X) Boolean.valueOf(true);
				}else if(Constants.NO.equals(params.getValue())){
					return (X) Boolean.valueOf(false);
				}else{
					return (X) Boolean.valueOf(params.getValue());
				}
			}else if(x instanceof Map){
				return (X) JsonUtil.json2Map(params.getValue());
			}else{
				return (X) JsonUtil.renderObject(params.getValue(),x.getClass());
			}
		}
		return x;
	}

	@Override
	public void updateByKey(String key, String value) {
		paramsDao.updateByKey(key, value);
	}
}
