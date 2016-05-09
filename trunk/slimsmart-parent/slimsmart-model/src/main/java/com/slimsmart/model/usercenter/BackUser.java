package com.slimsmart.model.usercenter;

import com.slimsmart.common.model.BaseEntity;

/**
 * 后台用户实体
 * @author zhutianwei
 *
 */
public class BackUser extends BaseEntity{

	private static final long serialVersionUID = 1L;
	//用户登录名称
	private String loginName;
	//用户密码
    private String password;
    //状态：（0.正常，1.锁定，2.注销）
    private String status;
    //用户姓名
    private String name;
    //联系方式
    private String phone;
    //电子邮箱
    private String email;
    //备注
    private String remark;
    //用户绑定的角色ID
    private String roleId;
    //角色名称
    private String roleName;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}