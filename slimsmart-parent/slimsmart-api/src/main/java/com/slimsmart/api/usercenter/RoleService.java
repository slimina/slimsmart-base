package com.slimsmart.api.usercenter;

import java.util.List;

import com.shq.common.service.BaseService;
import com.slimsmart.model.usercenter.Role;

/**
 * <b>角色管理接口</b><br/>
 * <p>提供角色管理接口：包括角色新增、删除、修改、及绑定权限等</p>
 * @author zhutianwei
 */
public interface RoleService extends BaseService<Role> {

	/**
	 * 查询当前角色是否绑定了用户
	 * @param roleId 角色Id
	 * @return true 存在 false 不存在
	 */
	boolean isExistsBackUser(String roleId);
	
	/**
	 * 根据角色查询绑定的权限Id集合
	 * @param roleId 角色Id
	 * @return 绑定的权限Id集合
	 */
	List<String> getPermIdListByRoleId(String roleId);
	
	/**
	 * 角色绑定权限
	 * @param roleId 角色Id
	 * @param permIds 权限Id，多个以逗号分隔
	 */
	void bindRolePermIds(String roleId,String permIds);
	
	/**
	 * 根据用户Id获取角色信息
	 * @param userId 用户Id
	 * @return 角色信息
	 */
	Role getRoleByUserId(String userId);
}
