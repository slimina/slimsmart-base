package com.shq.weboss.usercenter;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shq.common.exception.ServiceCode;
import com.shq.common.page.Page;
import com.shq.common.util.ReflectionUtil;
import com.shq.common.util.http.ResponseMsg;
import com.shq.common.web.BaseController;
import com.shq.model.usercenter.Params;
import com.slimsmart.api.usercenter.ParamsService;

@Controller
@RequestMapping("/usercenter/params/*")
public class ParamsController extends BaseController<Params>{

	@Autowired
	private ParamsService paramsService;
	@Override
	protected String getNameSpace() {
		return "/pages/usercenter/params/";
	}
	
	@Override
	public String index() {
		return getNameSpace()+"params-list";
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Page<Params> list(Params params, Integer page, Integer rows, HttpServletRequest request) throws Exception {
		Page<Params> pageItem = new Page<Params>(page, rows);
		if (params == null) {
			filterMap = new HashMap<String, Object>();
		} else {
			filterMap = ReflectionUtil.convertBean(params);
		}
		return paramsService.findPage(pageItem, filterMap);
	}
	
	@RequestMapping("save")
	@ResponseBody
	public ResponseMsg save(Params params,HttpServletRequest request) throws Exception{
		ResponseMsg response = new ResponseMsg();
		try{
			if(paramsService.isExistsKey(params.getKey())){
				return response.setCode(ServiceCode.FAULT.getCode()).setMessage("参数编码已经存在");
			}
			params = paramsService.save(params);
			response.setCode(ServiceCode.SUCCESS.getCode()).setMessage(ServiceCode.SUCCESS.getMessage()).setData(params);
		}catch (Exception e) {
			logger.error("save error : {}",e);
			response.setCode(ServiceCode.FAULT.getCode()).setMessage(ServiceCode.FAULT.getMessage());
		}
		return response; 
	}
	
	@RequestMapping("update")
	@ResponseBody
	public ResponseMsg update(Params params,HttpServletRequest request) throws Exception{
		ResponseMsg response = new ResponseMsg();
		try{
			paramsService.update(params);
			response.setCode(ServiceCode.SUCCESS.getCode()).setMessage(ServiceCode.SUCCESS.getMessage());
		}catch (Exception e) {
			logger.error("update error : {}",e);
			response.setCode(ServiceCode.FAULT.getCode()).setMessage(ServiceCode.FAULT.getMessage());
		}
		return response;
	}
	
	@RequestMapping("detail")
	@ResponseBody
	public Params detail(String id, HttpServletRequest request) throws Exception {
		return paramsService.get(id);
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public ResponseMsg delete(String id, HttpServletRequest request) throws Exception {
		ResponseMsg response = new ResponseMsg();
		try {
			paramsService.delete(id);
			response.setCode(ServiceCode.SUCCESS.getCode()).setMessage(ServiceCode.SUCCESS.getMessage());
		}catch (Exception e) {
			logger.error("delete error : {}",e);
			response.setCode(ServiceCode.FAULT.getCode()).setMessage(ServiceCode.FAULT.getMessage());
		}
		return response;
	}
}
