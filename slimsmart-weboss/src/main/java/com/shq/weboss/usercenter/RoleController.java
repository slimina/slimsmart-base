package com.shq.weboss.usercenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shq.api.usercenter.PermService;
import com.shq.api.usercenter.RoleService;
import com.shq.common.exception.ServiceCode;
import com.shq.common.model.Tree;
import com.shq.common.page.Page;
import com.shq.common.util.ReflectionUtil;
import com.shq.common.util.collections.ListUtil;
import com.shq.common.util.http.ResponseMsg;
import com.shq.common.util.string.StringUtil;
import com.shq.common.web.BaseController;
import com.shq.model.usercenter.Perm;
import com.shq.model.usercenter.Role;

/**
 * 角色管理
 * 
 * @author zhutianwei
 */
@Controller
@RequestMapping("/usercenter/role/*")
public class RoleController extends BaseController<Role> {

	@Autowired
	private RoleService roleService;
	@Autowired
	private PermService permService;

	@Override
	public String index() {
		return getNameSpace() + "role-list";
	}

	@RequestMapping("findList")
	@ResponseBody
	public List<Role> findList(HttpServletRequest request) {
		return roleService.findList(new HashMap<String, Object>());
	}

	@RequestMapping("list")
	@ResponseBody
	public Page<Role> list(Role role, Integer page, Integer rows, HttpServletRequest request) throws Exception {
		Page<Role> pageItem = new Page<Role>(page, rows);
		if (role == null) {
			filterMap = new HashMap<String, Object>();
		} else {
			filterMap = ReflectionUtil.convertBean(role);
		}
		return roleService.findPage(pageItem, filterMap);
	}

	@RequestMapping("save")
	@ResponseBody
	public ResponseMsg save(Role role, HttpServletRequest request) throws Exception {
		ResponseMsg response = new ResponseMsg();
		try {
			role = roleService.save(role);
			response.setCode(ServiceCode.SUCCESS.getCode()).setMessage(ServiceCode.SUCCESS.getMessage()).setData(role);
		} catch (Exception e) {
			logger.error("save error : {}", e);
			response.setCode(ServiceCode.FAULT.getCode()).setMessage(ServiceCode.FAULT.getMessage());
		}
		return response;
	}

	@RequestMapping("update")
	@ResponseBody
	public ResponseMsg update(Role role, HttpServletRequest request) throws Exception {
		ResponseMsg response = new ResponseMsg();
		try {
			roleService.update(role);
			response.setCode(ServiceCode.SUCCESS.getCode()).setMessage(ServiceCode.SUCCESS.getMessage());
		} catch (Exception e) {
			logger.error("update error : {}", e);
			response.setCode(ServiceCode.FAULT.getCode()).setMessage(ServiceCode.FAULT.getMessage());
		}
		return response;
	}

	@RequestMapping("detail")
	@ResponseBody
	public Role detail(String id, HttpServletRequest request) throws Exception {
		return roleService.get(id);
	}

	@RequestMapping("delete")
	@ResponseBody
	public ResponseMsg delete(String id, HttpServletRequest request) throws Exception {
		ResponseMsg response = new ResponseMsg();
		try {
			if(roleService.isExistsBackUser(id)){
				response.setCode(ServiceCode.FAULT.getCode()).setMessage("当前角色绑定了用户不能删除");
			}else{
				roleService.delete(id);
				response.setCode(ServiceCode.SUCCESS.getCode()).setMessage(ServiceCode.SUCCESS.getMessage());
			}
		} catch (Exception e) {
			logger.error("delete error : {}", e);
			response.setCode(ServiceCode.FAULT.getCode()).setMessage(ServiceCode.FAULT.getMessage());
		}
		return response;
	}
	
	@RequestMapping("findPermTree")
	@ResponseBody
	public List<Perm> findPermTree(String systemId,String roleId, HttpServletRequest request) throws Exception {
		if(StringUtil.isEmpty(systemId)){
			return new ArrayList<Perm>();
		}
		List<Perm> permList = permService.findPermTree(systemId, null);
		List<String> permIds = roleService.getPermIdListByRoleId(roleId);
		if(ListUtil.isNotEmpty(permList) && ListUtil.isNotEmpty(permIds)){
			setPermChecked(permList, permIds);
		}
		return permList;
	}
	
	private <T extends Tree> void setPermChecked(final List<T> permList,final List<String> permIds){
		for(T perm : permList){
			if(permIds.contains(perm.getId())){
				perm.setChecked(true);
			}
			if(ListUtil.isNotEmpty(perm.getChildren())){
				setPermChecked(perm.getChildren(),permIds);
			}
		}
	}
	
	@RequestMapping("bind")
	@ResponseBody
	public ResponseMsg bindRolePermIds(String roleId,String permIds, HttpServletRequest request) throws Exception {
		ResponseMsg response = new ResponseMsg();
		if(StringUtil.isEmpty(permIds) || StringUtil.isEmpty(roleId)){
			return response.setCode(ServiceCode.FAULT.getCode()).setMessage("参数错误");
		}
		roleService.bindRolePermIds(roleId, permIds);
		return response.setCode(ServiceCode.SUCCESS.getCode()).setMessage("绑定成功");
	}

	@Override
	protected String getNameSpace() {
		return "/pages/usercenter/role/";
	}
}
