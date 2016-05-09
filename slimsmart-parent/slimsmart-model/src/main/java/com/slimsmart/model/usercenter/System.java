package com.slimsmart.model.usercenter;

import com.slimsmart.common.model.BaseEntity;

/**
 * 系统管理实体
 * @author zhutianwei
 */
public class System extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	//系统名称
	private String name;
	//状态：0 正常 1 维护中 2 关闭
    private String status;
    //系统编码，唯一索引
    private String code;
    //访问地址:，如：http://www.dytj.com
    private String url;
    //备注
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}