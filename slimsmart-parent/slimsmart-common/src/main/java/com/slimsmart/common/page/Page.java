package com.slimsmart.common.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
 /**
  * 与具体ORM实现无关的分页参数及查询结果封装.
  */
@SuppressWarnings("serial")
public class Page<T> implements Serializable {
	//-- 公共变量 --//
	public static final String ASC = "asc";
	public static final String DESC = "desc";

	// default page size
	public static final int SIZE = 20;
	
	//-- 分页参数 --//
	protected int pageNo = 1;
	protected int pageSize = SIZE;

	protected long total = 0;
	protected String orderBy = null;
	protected String order = null;
	protected boolean maybeHasMore = true;
	//--从1开始计数为第一条--//
	protected int startIndex;
	protected int endIndex;

	//-- 返回结果 --//
	protected List<T> rows = new ArrayList<T>();

	//-- 构造函数 --//
	public Page() {
	}

	public Page(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public Page(Integer pageNumber,Integer pageSize) {
		if (pageSize==null || pageSize < 1) {
			this.pageSize = 1;
		}else{
			this.pageSize = pageSize;
		}
		if (pageNumber==null || pageNumber < 1) {
			this.pageNo = 1;
		}else{
			this.pageNo = pageNumber;
		}
	}

	//-- 访问查询参数函数 --//
	/**
	 * 获得当前页的页号,序号从1开始,默认为1.
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
	 */
	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1) {
			this.pageNo = 1;
		}
	}
	/**
	 * 获得每页的记录数量,默认为1.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页的记录数量,低于1时自动调整为1.
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;

		if (pageSize < 1) {
			this.pageSize = 1;
		}
	}

	/**
	 * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从0开始.
	 */
	public int getFirst() {
		return ((pageNo - 1) * pageSize);
	}

	/**
	 * 获得排序字段,无默认值.多个排序字段时用','分隔.
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * 设置排序字段,多个排序字段时用','分隔.
	 */
	public void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * 获得排序方向.
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * 设置排序方式向.
	 * 
	 * @param order 可选值为desc或asc,多个排序字段时用','分隔.
	 */
	public void setOrder(final String order) {
		//检查order字符串的合法值
		String[] orders = StringUtils.split(StringUtils.lowerCase(order), ',');
		for (String orderStr : orders) {
			if (!StringUtils.equals(DESC, orderStr) && !StringUtils.equals(ASC, orderStr)) {
				throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
			}
		}

		this.order = StringUtils.lowerCase(order);
	}

	/**
	 * 是否已设置排序字段,无默认值.
	 */
	public boolean isOrderBySetted() {
		return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order));
	}

	//-- 访问查询结果函数 --//


	/**
	 * 根据pageSize与totalCount计算总页数, 默认值为-1.
	 */
	public long getTotalPages() {
		if (total < 0) {
			return -1;
		}

		long count = total / pageSize;
		if (total % pageSize > 0) {
			count++;
		}
		return count;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}

	/**
	 * 取得下页的页号, 序号从1开始.
	 * 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (isHasNext()) {
			return pageNo + 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * 取得上页的页号, 序号从1开始.
	 * 当前页为首页时返回首页序号.
	 */
	public int getPrePage() {
		if (isHasPre()) {
			return pageNo - 1;
		} else {
			return pageNo;
		}
	}

	
	public int getStartIndex() {
		return startIndex;
	}

	
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	
	public int getEndIndex() {
		return endIndex;
	}

	
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Page");
        sb.append("{pageNo=").append(pageNo);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", totalCount=").append(total);
        sb.append(", result=").append(rows);
        sb.append('}');
        return sb.toString();
    }
}
