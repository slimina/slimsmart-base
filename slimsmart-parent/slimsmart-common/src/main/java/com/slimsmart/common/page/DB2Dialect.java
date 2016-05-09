package com.slimsmart.common.page;

public class DB2Dialect  extends Dialect {

	@Override
	public String getPageSQL(String sql, int offset, int limit) {
		sql = sql.trim();
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		pagingSelect.append("select * from (select row_.*,rownumber() over() as _rowid from (");
		pagingSelect.append(sql);
		pagingSelect.append(" ) as row_ ) _tmp where  _tmp._rowid >= ").append(offset);
		pagingSelect.append(" and ").append(" _tmp._rowid < ").append(offset+limit);
		return pagingSelect.toString();
	}
}
