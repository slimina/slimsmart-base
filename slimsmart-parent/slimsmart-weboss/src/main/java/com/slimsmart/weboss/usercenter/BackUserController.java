package com.shq.weboss.usercenter;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shq.common.exception.ServiceCode;
import com.shq.common.page.Page;
import com.shq.common.util.PasswordUtil;
import com.shq.common.util.ReflectionUtil;
import com.shq.common.util.http.ResponseMsg;
import com.shq.common.util.string.StringUtil;
import com.shq.common.web.BaseController;
import com.slimsmart.api.usercenter.BackUserService;
import com.slimsmart.model.usercenter.BackUser;

/**
 * 后台用户
 * @author zhutianwei
 *
 */
@Controller
@RequestMapping("/usercenter/backUser/*")
public class BackUserController extends BaseController<BackUser>{

	@Autowired
	private BackUserService backUserService;
	
	@Override
	public String index() {
		return getNameSpace()+"backuser-list";
	}
	
	@RequestMapping("save")
	@ResponseBody
	public ResponseMsg save(BackUser backUser,HttpServletRequest request) throws Exception{
		ResponseMsg response = new ResponseMsg();
		try{
			if(backUserService.isExistsLoginName(backUser.getLoginName())){
				response.setCode(ServiceCode.FAULT.getCode()).setMessage("登录用户名已经存在");
			}else{
				backUser.setPassword(PasswordUtil.getMD5(backUser.getPassword()));
				backUser = backUserService.save(backUser);
				response.setCode(ServiceCode.SUCCESS.getCode()).setMessage(ServiceCode.SUCCESS.getMessage()).setData(backUser);
			}
		}catch (Exception e) {
			logger.error("save error : {}",e);
			response.setCode(ServiceCode.FAULT.getCode()).setMessage(ServiceCode.FAULT.getMessage());
		}
		return response; 
	}
	
	@RequestMapping("update")
	@ResponseBody
	public ResponseMsg update(BackUser backUser,HttpServletRequest request) throws Exception{
		ResponseMsg response = new ResponseMsg();
		try{
			backUserService.update(backUser);
			response.setCode(ServiceCode.SUCCESS.getCode()).setMessage(ServiceCode.SUCCESS.getMessage());
		}catch (Exception e) {
			logger.error("update error : {}",e);
			response.setCode(ServiceCode.FAULT.getCode()).setMessage(ServiceCode.FAULT.getMessage());
		}
		return response;
	}
	
	@RequestMapping("detail")
	@ResponseBody
	public BackUser detail(String id, HttpServletRequest request) throws Exception {
		return backUserService.get(id);
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public ResponseMsg delete(String id, HttpServletRequest request) throws Exception {
		ResponseMsg response = new ResponseMsg();
		try {
			backUserService.delete(id);
			response.setCode(ServiceCode.SUCCESS.getCode()).setMessage(ServiceCode.SUCCESS.getMessage());
		}catch (Exception e) {
			logger.error("delete error : {}",e);
			response.setCode(ServiceCode.FAULT.getCode()).setMessage(ServiceCode.FAULT.getMessage());
		}
		return response;
	}
	
	@RequestMapping("resetPwd")
	@ResponseBody
	public ResponseMsg resetPwd(String id,HttpServletRequest request) throws Exception {
		ResponseMsg response = new ResponseMsg();
		try {
			String defaultPassword = null;
			if(StringUtil.isBlank(defaultPassword)){
				defaultPassword = "1a2b3c";
			}
			backUserService.updatePassword(id, PasswordUtil.getMD5(defaultPassword));
			response.setCode(ServiceCode.SUCCESS.getCode()).setMessage(ServiceCode.SUCCESS.getMessage()).setData(defaultPassword);
		}catch (Exception e) {
			logger.error("updatePwd error : {}",e);
			response.setCode(ServiceCode.FAULT.getCode()).setMessage(ServiceCode.FAULT.getMessage());
		}
		return response;
	}
	@RequestMapping("modifyPassword")
	@ResponseBody
	public ResponseMsg modifyPassword(BackUser backUser,String oldPassword,HttpServletRequest request) throws Exception {
		ResponseMsg response = new ResponseMsg();
		try {
			BackUser bUser = backUserService.get(backUser.getId());
			if(StringUtil.isBlank(oldPassword)){
				response.setCode(ServiceCode.FAULT.getCode()).setMessage("新旧密码不能为空");
				return response;
			}else{
				oldPassword=PasswordUtil.getSaltMD5(PasswordUtil.getMD5(oldPassword));
				if(!oldPassword.equals(bUser.getPassword())){
					response.setCode(ServiceCode.FAULT.getCode()).setMessage("旧密码不正确");
				}else {
					backUserService.updatePassword(backUser.getId(), PasswordUtil.getMD5(backUser.getPassword()));
					response.setCode(ServiceCode.SUCCESS.getCode()).setMessage("修改密码成功");
				}
			}
		}catch (Exception e) {
			logger.error("updatePwd error : {}",e);
			response.setCode(ServiceCode.FAULT.getCode()).setMessage(ServiceCode.FAULT.getMessage());
		}
		return response;
	}
	
	@RequestMapping("list")
	@ResponseBody
	public Page<BackUser> list(BackUser backUser, Integer page, Integer rows, HttpServletRequest request) throws Exception {
		Page<BackUser> pageItem = new Page<BackUser>(page, rows);
		if (backUser == null) {
			filterMap = new HashMap<String, Object>();
		} else {
			filterMap = ReflectionUtil.convertBean(backUser);
		}
		return backUserService.findPage(pageItem, filterMap);
	}

	@Override
	protected String getNameSpace() {
		return "/pages/usercenter/backuser/";
	}
}
