package com.shq.common.util.collections;

import java.util.ArrayList;
import java.util.List;

import com.shq.common.model.BaseEntity;
import com.shq.common.util.string.StringUtil;

public class TreeUtil{
	 /**
	  * 
	  * 将List转换为树
	  * @param treeList
	  * @param parentId
	  * @return
	  */
	 public static List<Tree> convertTree(List<Tree> treeList,String parentId){
		 if(treeList==null || treeList.size() ==0 || StringUtil.isEmpty(parentId)){
			 return treeList;
		 }

		 List<String> ids = new ArrayList<String>();
		 List<Tree> resultList = new ArrayList<Tree>();
		 for(Tree tree : treeList){
			 if(parentId.equals(tree.getPid())){
				 resultList.add(tree);
				 ids.add(tree.getId());
				 findChildren(treeList,ids,tree);
			 }
		 }
		 for(Tree tree : treeList){
			 if(!ids.contains(tree.getId())){
				 resultList.add(tree);
			 }
		 }
		 return resultList;
	 }
	 
	 /**
	  * 
	  * 在集合中查找子元素
	  * @param treeList
	  * @param tree
	  */
	 private static void findChildren(final List<Tree> treeList, List<String> ids  ,final Tree tree){
		 for(Tree t : treeList){
			 if(tree.getId().equals(t.getPid())){
				 tree.addItemToChildren(t);
				 ids.add(t.getId());
				 findChildren(treeList,ids,t);
			 }
		 }
	 }
}

class Tree extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	//父Id
	private String pid;
	
	//子节点
	private List<Tree> children;
	private Boolean checked = false ;
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public List<Tree> getChildren() {
		return children;
	}
	
	@SuppressWarnings("unchecked")
	public void setChildren(List<?> children) {
		this.children = (List<Tree>)children;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
	/**
	 * 
	 * 增加子元素
	 * @param child
	 */
	 public void addItemToChildren(Tree child) {
	    	if(children == null) {
	    		children = new ArrayList<Tree>();
	    	} 
	    	if(child != null) {
	    		children.add(child);
	    	}
	 }
}
