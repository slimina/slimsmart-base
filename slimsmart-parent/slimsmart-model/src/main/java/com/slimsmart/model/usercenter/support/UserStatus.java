package com.shq.model.usercenter.support;

/**
 * 用户状态枚举类
 */
public enum UserStatus {
	
	NORMAL("0","正常"),
	LOCK("1","锁定"),
	LOGOUT("2","注销");
	
	private String key;
	private String value;
	
	public String getValue() {
		return value;
	}

	public String getKey() {
		return key;
	}

	private UserStatus(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * 通过index获取enmu对象
	 * 
	 * @param oridal
	 * @return
	 */
	public static UserStatus get(int oridal) {
		for (UserStatus dot : UserStatus.values()) {
			if (oridal == dot.ordinal()) {
				return dot;
			}
		}
		return null;
	}

	/**
	 * 通过值获取enmu对象
	 * 
	 * @return
	 */
	public static UserStatus getByKey(String key) {
		for (UserStatus dot : UserStatus.values()) {
			if (dot.getKey().equals(key)) {
				return dot;
			}
		}
		return null;
	}
}
