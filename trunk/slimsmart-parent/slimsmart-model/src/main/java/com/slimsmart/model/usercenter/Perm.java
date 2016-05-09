package com.slimsmart.model.usercenter;

import com.slimsmart.common.model.Tree;

/**
 * 权限资源实体
 * @author zhutianwei
 *
 */
public class Perm extends Tree{
	
	private static final long serialVersionUID = 1L;
	//资源名称
	private String name;
	//资源权限编码，系统内唯一
    private String code;
    //类型：0 菜单 1按钮
    private String type;
    //状态：0 启用，1禁用
    private String status;
    //菜单索引，排列顺序
    private Integer orderNum;
    //系统ID
    private String systemId;
    //菜单图标地址
    private String iconUrl;
    //请求url地址
    private String pageUrl;
    //备注
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
        super.setText(name);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId == null ? null : systemId.trim();
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl == null ? null : iconUrl.trim();
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl == null ? null : pageUrl.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}