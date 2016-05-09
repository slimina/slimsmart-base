package com.shq.common.page;

public abstract class Dialect {
	public static enum Type {
		MYSQL, ORACLE,POSTGRE,DB2
	}
	public abstract String getPageSQL(String sql, int skipResults,int maxResults);
}
