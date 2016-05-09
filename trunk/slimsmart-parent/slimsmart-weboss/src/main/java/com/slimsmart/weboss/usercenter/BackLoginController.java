package com.slimsmart.weboss.usercenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.slimsmart.common.exception.ServiceCode;
import com.slimsmart.common.util.PasswordUtil;
import com.slimsmart.common.util.collections.ListUtil;
import com.slimsmart.common.util.file.ResourcesUtil;
import com.slimsmart.common.util.http.ResponseMsg;
import com.slimsmart.common.util.string.StringUtil;
import com.slimsmart.model.usercenter.support.UserStatus;
import com.slimsmart.weboss.common.SessionData;
import com.slimsmart.weboss.common.SessionHolder;
import com.slimsmart.api.usercenter.BackUserService;
import com.slimsmart.api.usercenter.PermService;
import com.slimsmart.api.usercenter.RoleService;
import com.slimsmart.common.Constants;
import com.slimsmart.model.usercenter.BackUser;
import com.slimsmart.model.usercenter.Perm;
import com.slimsmart.model.usercenter.Role;

/**
 * 登录用户控制
 * @author zhutianwei
 *
 */
@Controller
public class BackLoginController {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BackUserService backUserService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermService permService;
	
	
	@RequestMapping("login")
	public String login(HttpServletRequest request) throws Exception{
		return "pages/login";
	}
	
	@RequestMapping("loginAuth")
	@ResponseBody
	public ResponseMsg loginAuth(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		//检查登录消息
		String loginName = request.getParameter("loginName");
		String password = request.getParameter("password");
		String kaptchaCode = request.getParameter("kaptchaCode");
		if(StringUtil.isBlank(loginName) || StringUtil.isBlank(password)  || StringUtil.isBlank(kaptchaCode) ){
			return new ResponseMsg().setCode(ServiceCode.FAULT.getCode()).setMessage("登录信息不能为空");
		}
		String sessionCode = (String)request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if(!kaptchaCode.equals(sessionCode)){
			logger.debug("kaptchaCode : {},sessionCode:{}",kaptchaCode,sessionCode);
			return new ResponseMsg().setCode(ServiceCode.FAULT.getCode()).setMessage("您输入的验证码错误");
		}
		BackUser backUser = backUserService.getBackUserByLoginName(loginName);
		logger.debug("backUser : {}",backUser);
		if(backUser==null){
			return new ResponseMsg().setCode(ServiceCode.FAULT.getCode()).setMessage("您输入的用户名或密码错误");
		}
		if(!UserStatus.NORMAL.getKey().equals(backUser.getStatus())){
			return new ResponseMsg().setCode(ServiceCode.FAULT.getCode()).setMessage("您的账号已被锁定或者注销，请联系管理员");
		}
		password = PasswordUtil.getMD5(password);
		password = PasswordUtil.getSaltMD5(password);
		if(!password.equals(backUser.getPassword())){
			return new ResponseMsg().setCode(ServiceCode.FAULT.getCode()).setMessage("您输入的用户名或密码错误");
		}
		SessionData sessionData = new SessionData();
		sessionData.setCreateDate(new Date());;
		sessionData.setUserId(backUser.getId());
		sessionData.setUserName(backUser.getName());
		sessionData.setLoginName(loginName);
		sessionData.setPhone(backUser.getPhone());
		sessionData.setEmail(backUser.getEmail());
		Role role = roleService.getRoleByUserId(backUser.getId());
		if(role!=null){
			sessionData.setRoleId(role.getId());
			sessionData.setRoleName(role.getName());
		}
		request.getSession().setAttribute(Constants.BOSS_SESSION_KEY, sessionData);
		logger.debug("backuser {} login success .",loginName);
		return new ResponseMsg().setCode(ServiceCode.SUCCESS.getCode()).setMessage("登录成功");
	}

	@RequestMapping("index")
	public String index(HttpServletRequest request) throws Exception{
		return "pages/index";
	}
	
	
	@RequestMapping("logout")
	public void logout(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.getSession().removeAttribute(Constants.BOSS_SESSION_KEY);
		SessionHolder.remove();
		response.sendRedirect(request.getContextPath() + "/login");
	}
	
	@RequestMapping("menuList")
	@ResponseBody
	public List<Perm> menuList(HttpServletRequest request) throws Exception{
		List<Perm> menuList = SessionHolder.get().getMenuList();
		if(ListUtil.isEmpty(menuList) && StringUtil.isNotBlank(SessionHolder.get().getRoleId())){
			String systemCode =ResourcesUtil.getString("boss.systemCode");
			menuList = permService.getMenuListByRoleId(SessionHolder.get().getRoleId(), systemCode);
			SessionHolder.get().setMenuList(menuList);
		}
		return ListUtil.isEmpty(menuList) ? new ArrayList<Perm>(): menuList;
	}
	
	
	@RequestMapping("funcList")
	@ResponseBody
	public List<Perm> funcList(HttpServletRequest request) throws Exception{
		List<Perm> funcList = SessionHolder.get().getFuncList();
		if(ListUtil.isEmpty(funcList) && StringUtil.isNotBlank(SessionHolder.get().getRoleId())){
			String systemCode =ResourcesUtil.getString("boss.systemCode");
			funcList = permService.getFuncListByRoleId(SessionHolder.get().getRoleId(), systemCode);
			SessionHolder.get().setMenuList(funcList);
		}
		return ListUtil.isEmpty(funcList) ? new ArrayList<Perm>(): funcList;
	}
}
