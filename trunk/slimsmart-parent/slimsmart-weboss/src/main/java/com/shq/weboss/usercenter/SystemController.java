package com.shq.weboss.usercenter;

import java.util.HashMap;
import java.util.List;

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
import com.slimsmart.api.usercenter.SystemService;

/**
 * 系统管理
 * @author zhutianwei
 *
 */
@Controller
@RequestMapping("/usercenter/system/*")
public class SystemController  extends BaseController<com.shq.model.usercenter.System>{

	@Autowired
	private SystemService systemService;
	
	@Override
	public String index() {
		return getNameSpace()+"system-list";
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Page<com.shq.model.usercenter.System> list(System system, Integer page, Integer rows, HttpServletRequest request) throws Exception {
		Page<com.shq.model.usercenter.System> pageItem = new Page<com.shq.model.usercenter.System>(page, rows);
		if (system == null) {
			filterMap = new HashMap<String, Object>();
		} else {
			filterMap = ReflectionUtil.convertBean(system);
		}
		return systemService.findPage(pageItem, filterMap);
	}
	
	@RequestMapping("save")
	@ResponseBody
	public ResponseMsg save(com.shq.model.usercenter.System system,HttpServletRequest request) throws Exception{
		ResponseMsg response = new ResponseMsg();
		try{
			if(systemService.isExistsCode(system.getCode())){
				response.setCode(ServiceCode.FAULT.getCode()).setMessage("编码已经存在");
			}else{
				system = systemService.save(system);
				response.setCode(ServiceCode.SUCCESS.getCode()).setMessage(ServiceCode.SUCCESS.getMessage()).setData(system);
			}
		}catch (Exception e) {
			logger.error("save error : {}",e);
			response.setCode(ServiceCode.FAULT.getCode()).setMessage(ServiceCode.FAULT.getMessage());
		}
		return response; 
	}
	
	@RequestMapping("update")
	@ResponseBody
	public ResponseMsg update(com.shq.model.usercenter.System system,HttpServletRequest request) throws Exception{
		ResponseMsg response = new ResponseMsg();
		try{
			systemService.update(system);
			response.setCode(ServiceCode.SUCCESS.getCode()).setMessage(ServiceCode.SUCCESS.getMessage());
		}catch (Exception e) {
			logger.error("update error : {}",e);
			response.setCode(ServiceCode.FAULT.getCode()).setMessage(ServiceCode.FAULT.getMessage());
		}
		return response;
	}
	
	@RequestMapping("detail")
	@ResponseBody
	public com.shq.model.usercenter.System detail(String id, HttpServletRequest request) throws Exception {
		return systemService.get(id);
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public ResponseMsg delete(String id, HttpServletRequest request) throws Exception {
		ResponseMsg response = new ResponseMsg();
		try {
			systemService.delete(id);
			response.setCode(ServiceCode.SUCCESS.getCode()).setMessage(ServiceCode.SUCCESS.getMessage());
		}catch (Exception e) {
			logger.error("delete error : {}",e);
			response.setCode(ServiceCode.FAULT.getCode()).setMessage(ServiceCode.FAULT.getMessage());
		}
		return response;
	}
	
	@RequestMapping("findList")
	@ResponseBody
	public List<com.shq.model.usercenter.System> findList(HttpServletRequest request){
		return systemService.findList(new HashMap<String, Object>());
	}

	@Override
	protected String getNameSpace() {
		return "/pages/usercenter/system/";
	}
}
