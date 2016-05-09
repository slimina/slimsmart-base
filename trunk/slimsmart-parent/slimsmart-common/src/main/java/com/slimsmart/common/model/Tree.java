package com.slimsmart.common.model;

import java.util.ArrayList;
import java.util.List;

import com.slimsmart.common.util.string.StringUtil;

/**
 * 树操作对象
 */
@SuppressWarnings("unchecked")
public class Tree extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	//父Id
	private String parentId;
		
	//显示的节点文本
	private String text;
	//该节点是否被选中。
	private boolean checked = false;
	//节点状态，'open' 或 'closed'
	private String state ="open";
	//子节点
	private  List<Tree> children;
	
	public String getText(){
		return text;
	};
	
	public void setText(String text) {
		this.text = text;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public <T extends Tree> List<T> getChildren() {
		return (List<T>) children;
	}
	
	public <T extends Tree> void setChildren(List<T> children) {
		this.children = (List<Tree>) children;
	}
	
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId == null ? null : parentId.trim();
	}
	public void updateTreeState(String state){
		if(this.getChildren() != null && this.getChildren().size() >0 ){
			this.setState(state);
			setChildrenHide(this.getChildren(),state);
		}
	}
	
	/**
	 * 
	 * 设置其子元素都隐藏
	 * @param childs
	 * @param isExpand
	 */
	private void setChildrenHide(final List<Tree> childs,String state){
		if(childs!=null && childs.size() > 0){
			for(Tree tree : childs){
				if("open".equals(state)){
					tree.setState("closed");;
				}else{
					tree.setState("open");;
				}
				setChildrenHide(tree.getChildren(),state);
			}
		}
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
	 /**
	  * 
	  * 将List转换为树
	  * @param treeList
	  * @param parentId
	  * @return
	  */
	 public static <T extends Tree> List<T> convertTree(List<T> treeList){
		 if(treeList==null || treeList.size() ==0){
			 return treeList;
		 }

		 List<String> ids = new ArrayList<String>();
		 List<T> resultList = new ArrayList<T>();
		 for(T tree : treeList){
			 if(StringUtil.isBlank(tree.getParentId())||"0".equals(tree.getParentId())){
				 resultList.add(tree);
				 ids.add(tree.getId());
				 findChildren(treeList,ids,tree);
			 }
		 }
		 for(T tree : treeList){
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
	 private static <T extends Tree> void findChildren(final List<T> treeList, List<String> ids  ,final Tree tree){
		 for(Tree t : treeList){
			 if(tree.getId().equals(t.getParentId())){
				 tree.addItemToChildren(t);
				 ids.add(t.getId());
				 findChildren(treeList,ids,t);
			 }
		 }
	 }
}
