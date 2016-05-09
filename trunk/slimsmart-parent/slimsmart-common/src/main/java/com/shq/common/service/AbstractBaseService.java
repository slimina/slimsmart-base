package com.shq.common.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.shq.common.annotation.Log;
import com.shq.common.dao.MyBatisDao;
import com.shq.common.model.BaseEntity;
import com.shq.common.page.Page;
import com.shq.common.util.ApplicationContextProvider;
import com.shq.common.util.ReflectionUtil;
import com.shq.common.util.string.StringUtil;

public abstract class AbstractBaseService<T extends BaseEntity> implements BaseService<T> {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	protected Class<T> entityClass;

	@Autowired
	protected ApplicationContextProvider contextProvider;

	public AbstractBaseService() {
		super();
		this.entityClass = ReflectionUtil.getSuperClassGenricType(getClass());
	}

	@Log("根据ID删除")
	@Override
	public void delete(String id) {
		getDao().delete(id);
	}

	@Log("根据ID查询")
	@Override
	public T get(String id) {
		return getDao().get(id);
	}

	@Log("修改")
	@Override
	public int update(T entity) {
		return getDao().update(entity);
	}

	@Log("添加")
	@Override
	public void insert(T entity) {
		getDao().insert(entity);
	}

	@Log("添加")
	@Override
	public T save(T entity) {
		return getDao().insert(entity);
	}
	
	@Log("批量删除")
	@Override
	public void batchDelete(List<T> idEntitys) {
		getDao().batchDelete(idEntitys);
	}

	@Log("批量添加")
	@Override
	public void batchInsert(List<T> idEntitys) {
		getDao().batchInsert(idEntitys);
	}

	@Log("分页查询")
	@Override
	public Page<T> findPage(Page<T> page, Map<String, Object> filter) {
		return getDao().findPage(page, filter);
	}

	@Log("查询集合")
	@Override
	public List<T> findList(Map<String, Object> filter) {
		return getDao().findList(filter);
	}

	@SuppressWarnings("unchecked")
	protected MyBatisDao<T> getDao() {
		String entityClassName = entityClass.getSimpleName();
		String daoName = StringUtil.lowercaseFirstLetter(entityClassName) + "Dao";
		logger.trace("getDao:{}", daoName);
		MyBatisDao<T> dao = (MyBatisDao<T>) ApplicationContextProvider.getBean(daoName);
		return dao;
	}
}