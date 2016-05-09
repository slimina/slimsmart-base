package com.slimsmart.model.usercenter;

import com.shq.common.model.BaseEntity;

/**
 * 系统参数设置
 * @author slimina
 */
public class Params extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private String key;

    private String value;

    private String desc;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }
}