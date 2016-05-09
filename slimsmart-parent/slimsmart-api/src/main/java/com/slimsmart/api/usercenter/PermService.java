package com.slimsmart.api.usercenter;

import java.util.List;
import java.util.Map;

import com.slimsmart.common.service.BaseService;
import com.slimsmart.model.usercenter.Perm;

/**
 * <b>资源权限管理接口</b><br/>
 * <p>提供资源权限管理接口：包括新增、删除、修改、及状态变更等</p>
 * @author zhutianwei
 */
public interface PermService extends BaseService<Perm> {

	/**
	 * 检查同一个系统编码不能相同
	 * @param params  参数
	 * @return
	 */
	boolean isExistsCode(Map<String,String> params);
	
	/**
	 * 查询某系统下的所有菜单
	 * @param systemId 系统Id
	 * @return
	 */
	List<Perm> findMenuList(String systemId);
	
	/**
	 * 查询权限树数据
	 * @param systemId 系统Id
	 * @param params  参数
	 * @return
	 */
	List<Perm> findPermTree(String systemId,Map<String,Object> params);
	
	/**
	 * 通过角色获取用户的菜单集合
	 * @param roleId 角色Id
	 * @param systemCode 系统编码
	 * @return 菜单集合
	 */
	List<Perm> getMenuListByRoleId(String roleId,String systemCode);
	
	/**
	 * 通过角色获取用户的功能按钮集合
	 * @param roleId 角色Id
	 * @param systemCode 系统编码
	 * @return 功能按钮集合
	 */
	List<Perm> getFuncListByRoleId(String roleId,String systemCode);
}
