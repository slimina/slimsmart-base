package com.shq.service.usercenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shq.api.usercenter.PermService;
import com.shq.common.model.Tree;
import com.shq.common.service.AbstractBaseService;
import com.shq.common.util.collections.ListUtil;
import com.shq.common.util.collections.MapUtil;
import com.shq.dao.usercenter.PermDao;
import com.shq.model.usercenter.Perm;

/**
 * 权限管理接口实现
 * @author zhutianwei
 *
 */
@Service
public class PermServiceImpl extends AbstractBaseService<Perm> implements PermService {
	
	@Autowired
	private PermDao permDao;

	@Override
	public boolean isExistsCode(Map<String,String> params) {
		return permDao.isExistsCode(params);
	}

	@Override
	public void delete(String id) {
		deleteSub(id);
	}
	
	/**
	 * 递归删除下级节点
	 * @param parentId
	 */
	private void deleteSub(String parentId){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("parentId", parentId);
		List<Perm> list = permDao.findList(params);
		if(ListUtil.isNotEmpty(list)){
			for(Perm perm : list){
				deleteSub(perm.getId());
			}
		}
		//删除与角色关系
		permDao.deleteRolePermByPermId(parentId);
		super.delete(parentId);	
	}

	@Override
	public List<Perm> findMenuList(String systemId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("systemId", systemId);
		params.put("type", "0");
		List<Perm> list = permDao.findList(params);
		return Perm.convertTree(list);
	}

	@Override
	public List<Perm> findPermTree(String systemId, Map<String, Object> params) {
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("systemId", systemId);
		if(MapUtil.isEmpty(params)){
			List<Perm> list = permDao.findList(filter);
			return Perm.convertTree(list);
		}
		filter.putAll(params);
		return permDao.findList(filter);
	}

	@Override
	public List<Perm> getMenuListByRoleId(String roleId,String systemCode) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		params.put("systemCode", systemCode);
		List<Perm> list = permDao.getMenuListByRoleId(params);
		if(ListUtil.isNotEmpty(list)){
			list = Tree.convertTree(list);
		}
		return list;
	}

	@Override
	public List<Perm> getFuncListByRoleId(String roleId,String systemCode) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("roleId", roleId);
		params.put("systemCode", systemCode);
		return permDao.getFuncListByRoleId(params);
	}
}
