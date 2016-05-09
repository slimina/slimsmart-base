package com.slimsmart.common.page;

public class PostgreDialect  extends Dialect {

	@Override
	public String getPageSQL(String sql, int offset, int limit) {
		sql = sql.trim();
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		pagingSelect.append("select * from ( ");
		pagingSelect.append(sql);
		pagingSelect.append(" ) row_  limit ").append(limit).append(" , offset ").append(offset);
		return pagingSelect.toString();
	}
}
