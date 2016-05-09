package com.slimsmart.model.usercenter;

import com.shq.common.model.BaseEntity;

/**
 * 角色实体
 * @author zhutianwei
 *
 */
public class Role extends BaseEntity{

	private static final long serialVersionUID = 1L;
	//角色名称
	private String name;
	//备注信息
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}