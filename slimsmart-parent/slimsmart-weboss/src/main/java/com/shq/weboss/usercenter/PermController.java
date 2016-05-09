package com.shq.weboss.usercenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shq.common.exception.ServiceCode;
import com.shq.common.util.http.ResponseMsg;
import com.shq.common.util.string.StringUtil;
import com.shq.common.web.BaseController;
import com.slimsmart.api.usercenter.PermService;
import com.slimsmart.model.usercenter.Perm;

/**
 * 权限管理
 * 
 * @author zhutianwei
 * 
 */
@Controller
@RequestMapping("/usercenter/perm/*")
public class PermController extends BaseController<Perm> {

	@Autowired
	private PermService permService;

	@Override
	public String index() {
		return getNameSpace() + "perm-list";
	}

	@RequestMapping("save")
	@ResponseBody
	public ResponseMsg save(Perm perm,HttpServletRequest request) throws Exception{
		ResponseMsg response = new ResponseMsg();
		try{
			Map<String,String> params = new HashMap<String,String>();
			params.put("systemId", perm.getSystemId());
			params.put("code", perm.getCode());
			if(permService.isExistsCode(params)){
				response.setCode(ServiceCode.FAULT.getCode()).setMessage("编码已经存在");
			}else{
				perm.setStatus("0");
				perm = permService.save(perm);
				response.setCode(ServiceCode.SUCCESS.getCode()).setMessage(ServiceCode.SUCCESS.getMessage()).setData(perm);
			}
		}catch (Exception e) {
			logger.error("save error : {}",e);
			response.setCode(ServiceCode.FAULT.getCode()).setMessage(ServiceCode.FAULT.getMessage());
		}
		return response; 
	}
	
	@RequestMapping("update")
	@ResponseBody
	public ResponseMsg update(Perm perm,HttpServletRequest request) throws Exception{
		ResponseMsg response = new ResponseMsg();
		try{
			Map<String,String> params = new HashMap<String,String>();
			params.put("systemId", perm.getSystemId());
			params.put("code", perm.getCode());
			params.put("permId", perm.getId());
			if(permService.isExistsCode(params)){
				response.setCode(ServiceCode.FAULT.getCode()).setMessage("编码已经存在");
			}else{
				permService.update(perm);
				response.setCode(ServiceCode.SUCCESS.getCode()).setMessage(ServiceCode.SUCCESS.getMessage());
			}
		}catch (Exception e) {
			logger.error("update error : {}",e);
			response.setCode(ServiceCode.FAULT.getCode()).setMessage(ServiceCode.FAULT.getMessage());
		}
		return response;
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public ResponseMsg delete(String id, HttpServletRequest request) throws Exception {
		ResponseMsg response = new ResponseMsg();
		try {
			permService.delete(id);
			response.setCode(ServiceCode.SUCCESS.getCode()).setMessage(ServiceCode.SUCCESS.getMessage());
		}catch (Exception e) {
			logger.error("delete error : {}",e);
			response.setCode(ServiceCode.FAULT.getCode()).setMessage(ServiceCode.FAULT.getMessage());
		}
		return response;
	}
	
	@RequestMapping("detail")
	@ResponseBody
	public Perm detail(String id, HttpServletRequest request) throws Exception {
		return permService.get(id);
	}
	
	@RequestMapping("findPermTree")
	@ResponseBody
	public List<Perm> findPermTree(Perm perm, HttpServletRequest request) throws Exception {
		if(StringUtil.isEmpty(perm.getSystemId())){
			return new ArrayList<Perm>();
		}
		if(perm!=null){
			filterMap = new HashMap<String, Object>();
			if(StringUtil.isNotBlank(perm.getName())){
				filterMap.put("name", perm.getName());
			}
			if(StringUtil.isNotBlank(perm.getStatus())){
				filterMap.put("status", perm.getStatus());
			}
		}
		return permService.findPermTree(perm.getSystemId(), filterMap);
	}
	
	@RequestMapping("menuList")
	@ResponseBody
	public List<Perm> menuList(Perm perm, HttpServletRequest request)throws Exception {
		if(StringUtil.isEmpty(perm.getSystemId())){
			return new ArrayList<Perm>();
		}
		return permService.findMenuList(perm.getSystemId());
	}

	@Override
	protected String getNameSpace() {
		return "/pages/usercenter/perm/";
	}
}
