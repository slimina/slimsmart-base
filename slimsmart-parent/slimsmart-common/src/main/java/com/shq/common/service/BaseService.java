package com.shq.common.service;

import java.util.List;
import java.util.Map;

import com.shq.common.model.BaseEntity;
import com.shq.common.page.Page;

public interface BaseService<T extends BaseEntity> {
	/**
	 * 根据Id删除实体
	 * 
	 * @param id
	 */
	void delete(String id);
	/**
	 * 批量删除
	 * @param idEntitys
	 */
	void batchDelete(List<T> idEntitys);

	/**
	 * 根据Id获取实体
	 * 
	 * @param id
	 */
	T get(String id);

	/**
	 * 更新实体
	 *
     * @param ctfoContext
     */
	int update(T entity);

	/**
	 * 新增实体
	 *
     * @param ctfoContext
     */
	void insert(T entity);
	/**
	 * 批量新增
	 * @param idEntitys
	 */
	void batchInsert(List<T> idEntitys);

	/**
	 * 查询实体列表分页
	 * 
	 */
	Page<T> findPage(Page<T> page, Map<String, Object> filter);
	
	/**
	 * 
	 * 根据条件查询实体集合
	 * @param ctfoContext
	 * @param filter
	 * @return
	 */
	List<T> findList(Map<String, Object> filter);

    /**
     * 保存并返回实体
     *
     * @param ctfoContext 上下文
     * @param entity      实体
     * @return 实体
     */
    T save(T entity);
}
