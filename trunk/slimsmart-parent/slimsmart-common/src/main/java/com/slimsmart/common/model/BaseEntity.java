package com.shq.common.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shq.common.util.date.DateTimeSerializer;

public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	// 系统主键ID
	protected String id;
	//记录创建时间
	protected Date createDate;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	@JsonSerialize(using = DateTimeSerializer.class)
	public Date getCreateDate() {
		return createDate;
	}

	@JsonSerialize(using = DateTimeSerializer.class)
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Long getCurrentTime() {
		return System.currentTimeMillis();
	}
	
	@SuppressWarnings("unused")
	@Deprecated
	private Long currentTime;
	
	@Deprecated
	public void setCurrentTime(Long currentTime){
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	protected ToStringBuilder toStringBuilder() {
		return new ToStringBuilder(ToStringStyle.SIMPLE_STYLE);
	}

	protected HashCodeBuilder hashCodeBuilder() {
		return new HashCodeBuilder();
	}
	
	protected boolean isEquals(Object src, Object obj) {
		boolean isEqual = true;
		if (src == obj) {
			return isEqual;
		}
		if ((obj == null) || (src == null)) {
			isEqual = false;
		}
		if (src.getClass() != obj.getClass()) {
			isEqual = false;
		}
		return isEqual;
	}
}
