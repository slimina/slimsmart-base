package com.slimsmart.weboss.common;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.slimsmart.common.model.BaseEntity;
import com.slimsmart.model.usercenter.Perm;

/**
 *
 * session储存信息
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SessionData extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	//用户信息
	private String userId;
	private String loginName;
	private String userName;
	private String phone;
	private String email;
	
	//用户绑定的角色ID
    private String roleId;
    //角色名称
    private String roleName;
    
	//功能菜单
	private List<Perm> menuList = null;
	//功能按钮
	private List<Perm> funcList = null;
	
	public String getUserId() {
		return userId;
	}
	public SessionData setUserId(String userId) {
		this.userId = userId;
		return this;
	}
	public String getLoginName() {
		return loginName;
	}
	public SessionData setLoginName(String loginName) {
		this.loginName = loginName;
		return this;
	}
	public String getUserName() {
		return userName;
	}
	public SessionData setUserName(String userName) {
		this.userName = userName;
		return this;
	}
	public String getRoleId() {
		return roleId;
	}
	public SessionData setRoleId(String roleId) {
		this.roleId = roleId;
		return this;
	}
	public String getRoleName() {
		return roleName;
	}
	public SessionData setRoleName(String roleName) {
		this.roleName = roleName;
		return this;
	}
	public List<Perm> getMenuList() {
		return menuList;
	}
	public SessionData setMenuList(List<Perm> menuList) {
		this.menuList = menuList;
		return this;
	}
	public List<Perm> getFuncList() {
		return funcList;
	}
	public SessionData setFuncList(List<Perm> funcList) {
		this.funcList = funcList;
		return this;
	}
	public String getPhone() {
		return phone;
	}
	public SessionData setPhone(String phone) {
		this.phone = phone;
		return this;
	}
	public String getEmail() {
		return email;
	}
	public SessionData setEmail(String email) {
		this.email = email;
		return this;
	}
}
