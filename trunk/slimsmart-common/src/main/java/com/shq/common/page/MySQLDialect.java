package com.shq.common.page;

public class MySQLDialect  extends Dialect {

	@Override
	public String getPageSQL(String sql, int offset, int limit) {
		sql = sql.trim();
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		pagingSelect.append(sql);
		pagingSelect.append(" limit ").append(offset).append(" , ").append(limit);
		return pagingSelect.toString();
	}
}
