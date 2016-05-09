package com.shq.common.dao;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.shq.common.model.BaseEntity;
import com.shq.common.page.Page;
import com.shq.common.util.ReflectionUtil;
import com.shq.common.util.UUID;

@SuppressWarnings({ "unchecked" })
public class MyBatisDao<T extends BaseEntity> extends SqlSessionDaoSupport {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected Class<T> entityClass;

	/**
	 * 用于Dao层子类使用的构造函数. 通过子类的泛型定义取得对象类型Class. eg. public class UserDao extends
	 * SimpleHibernateDao<User>
	 */
	public MyBatisDao() {
		this.entityClass = ReflectionUtil.getSuperClassGenricType(getClass());
	}

	/**
	 * 用于用于省略Dao层, 在Service层直接使用通用MyBatisDao的构造函数. 在构造函数中定义对象类型Class. eg.
	 * SimpleHibernateDao<User> userDao = new MyBatisDao<User>(sessionFactory,
	 * User.class);
	 */
	public MyBatisDao(final Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * 通用插入方法 statementName = findPage
	 * 
	 * @param idEntity
	 */
	public T insert(final T idEntity) {
		return insert("insert", idEntity);
	}

	/**
	 * 批量插入实体
	 * 
	 * @param idEntitys
	 * @return
	 */
	public <X> List<X> batchInsert(final List<X> idEntitys) {
		return batchInsert("insert", idEntitys);
	}

	/**
	 * 根据ID删除单表数据 statementName = delete
	 * 
	 * @param id
	 */
	public void delete(final String id) {
		delete("delete", id);
	}

	/**
	 * 批量删除
	 * 
	 * @param idEntitys
	 */
	public <X> void batchDelete(final List<X> idEntitys) {
		batchDelete("delete", idEntitys);
	}

	/**
	 * 单表查询方法，返回对象实体（包含全部属性的值），要求mapper实现语句时select出所有的字段 statementName = get
	 * 
	 * @param id
	 *            主键
	 * @return
	 */
	public T get(final String id) {
		return get("get", id);
	}

	/**
	 * 保存修改的对象. 返回0表示更新失败。如果这个值已经被人修改过，会抛出ConcurrentModificationException
	 * statementName = update
	 */
	public int update(final T idEntity) throws ConcurrentModificationException {
		return update("update", idEntity);
	}

	/**
	 * 公共分页方法，需要mapper提供count实现，findPage实现 statementName = findPage
	 * 
	 * @param page
	 * @param filter
	 * @return
	 */
	public Page<T> findPage(final Page<T> page, final Map<String, Object> filter) {
		return findPage(page, "count","findPage", filter);
	}

	/**
	 * 根据条件查询实体集合 statementName = findList
	 * 
	 * @param ctfoContex
	 * @param filter
	 * @return
	 */
	public List<T> findList(final Map<String, Object> filter) {
		return findList("findList", filter);
	}

	/**
	 * 添加实体
	 * 
	 * @param statementName
	 * @param entity
	 */
	protected T insert(String statementName, final T entity) {

		Assert.notNull(statementName, "statementName不能为空");
		Assert.notNull(entity, "entity不能为空");

		if (StringUtils.isEmpty(entity.getId())) {
			entity.setId(UUID.getUUID());
		}
		if (entity.getCreateDate()==null) {
			entity.setCreateDate(new Date());
		}
		getSqlSession().insert(getStatementName(statementName), entity);
		return entity;
	}
	
	/**
	 * 添加实体
	 * 
	 * @param statementName
	 * @param entity
	 */
	protected void insert(String statementName, Object obj) {
		Assert.notNull(statementName, "statementName不能为空");
		Assert.notNull(obj, "params不能为空");
		if(obj instanceof BaseEntity){
			BaseEntity entity = (BaseEntity)obj;
			if (StringUtils.isEmpty(entity.getId())) {
				entity.setId(UUID.getUUID());
			}
			if (entity.getCreateDate()==null) {
				entity.setCreateDate(new Date());
			}
			getSqlSession().insert(getStatementName(statementName), entity);
		}else{
			getSqlSession().insert(getStatementName(statementName), obj);
		}
	}

	/**
	 * 批量插入实体
	 * 
	 * @param statementName
	 * @param idEntitys
	 * @return
	 */
	protected <X> List<X> batchInsert(String statementName, final List<X> idEntitys) {
		Assert.notNull(statementName, "statementName不能为空");
		Assert.notNull(idEntitys, "entity不能为空");

		getSqlSession().getConfiguration().setDefaultExecutorType(ExecutorType.BATCH);
		for (X x : idEntitys) {
			if(x instanceof BaseEntity){
				BaseEntity entity = (BaseEntity)x;
				if (StringUtils.isEmpty(entity.getId())) {
					entity.setId(UUID.getUUID());
				}
				if (entity.getCreateDate()==null) {
					entity.setCreateDate(new Date());
				}
				getSqlSession().insert(getStatementName(statementName), entity);
			}else{
				getSqlSession().insert(getStatementName(statementName), x);
			}
		}
		return idEntitys;
	}

	/**
	 * 批量删除
	 * 
	 * @param statementName
	 * @param idEntitys
	 */
	public <X> void batchDelete(String statementName, final List<X> idEntitys) {
		Assert.notNull(statementName, "statementName不能为空");
		Assert.notNull(idEntitys, "idEntitys不能为空");
		getSqlSession().getConfiguration().setDefaultExecutorType(ExecutorType.BATCH);
		for (X x : idEntitys) {
			if(x instanceof BaseEntity){
				getSqlSession().delete(getStatementName(statementName), ((BaseEntity)x).getId());
			}else{
				getSqlSession().delete(getStatementName(statementName), x);
			}
		}
	}

	/**
	 * 根据Id删除实体
	 * 
	 * @param statementName
	 */
	protected void delete(String statementName, final Object obj) {
		Assert.notNull(statementName, "statementName不能为空");
		Assert.notNull(obj, "obj不能为空");
		getSqlSession().delete(getStatementName(statementName), obj);
	}

	/**
	 * 更新实体
	 * 
	 * @param statementName
	 * @param obj
	 *            可以为string int long IdEntity Map eg.
	 * @return
	 * @throws ConcurrentModificationException
	 */
	protected int update(String statementName, final Object obj) throws ConcurrentModificationException {

		Assert.notNull(statementName, "statementName不能为空");
		Assert.notNull(obj, "obj不能为空");
		int count = getSqlSession().update(getStatementName(statementName), obj);
		logger.debug("updated: {}", count);
		return count;
	}

	/**
	 * 根据Id获取实体对象
	 * 
	 * @param statementName
	 * @return
	 */
	protected <X> X get(String statementName, final Object obj) {
		Assert.notNull(statementName, "statementName不能为空");
		Assert.notNull(obj, "obj不能为空");
		return (X) getSqlSession().selectOne(getStatementName(statementName), obj);
	}

	/**
	 * 根据条件获取查询总数目
	 * 
	 * @param statementName
	 * @param obj
	 *            可以为string int long IdEntity map eg.
	 * @return
	 */
	public long count(String statementName, Object obj) {
		Assert.notNull(statementName, "statementName不能为空");
		if (null == obj) {
			obj = new HashMap<String,Object>();
		}
		Object rel = getSqlSession().selectOne(getStatementName(statementName), obj);
		long result = ((Number)rel).longValue();
		return result;
	}
	
	/**
	 * 根据条件查询实体集合
	 * 
	 * @param statementName
	 * @param obj
	 *            可以为string int long IdEntity Map eg.
	 * @return
	 */
	public <X> List<X> findList(String statementName, Object obj) {
		Assert.notNull(statementName, "statementName不能为空");
		if (null == obj) {
			obj = new HashMap<String,Object>();
		}
		List<X> result = getSqlSession().selectList(getStatementName(statementName), obj);
		return result;
	}

	/**
	 * 公共分页
	 * 
	 * @param page
	 * @param statementName
	 * @param filter
	 * @return
	 */
	protected <X> List<X> findRange(String statementName,Integer startIndex,Integer length,Object obj) {
		Assert.notNull(statementName, "statementName不能为空");
		if(startIndex==null || startIndex < 0 ){
			startIndex = 0;
		}
		if(length==null || length < 1 ){
			startIndex = Page.SIZE;
		}
		return getSqlSession().selectList(getStatementName(statementName), toParameterMap(obj), getRowBounds(startIndex, length));
	}
	
	/**
	 * 公共分页
	 * 
	 * @param page
	 * @param statementName
	 * @param filter
	 * @return
	 */
	protected <X> Page<X> findPage(final Page<X> page, String countStatementName,String listStatementName, Object obj) {
		Assert.notNull(page, "page不能为空");
		Assert.notNull(countStatementName, "countStatementName不能为空");
		Assert.notNull(listStatementName, "listStatementName不能为空");
		page.setTotal(count(countStatementName, obj));
		RowBounds rowBounds = getRowBounds(page.getFirst(),page.getPageSize());
		List<X> result = getSqlSession().selectList(getStatementName(listStatementName), toParameterMap(obj), rowBounds);
		postProcess(page, result);
		return page;
	}


	private <X> void postProcess(final Page<X> page, List<X> result) {
		if (result == null) {
			result = new ArrayList<X>();
		}
		page.setRows(result);
	}

	private RowBounds getRowBounds(int startIndex,int length) {
		return new RowBounds(startIndex, length);
	}

	private String getStatementName(String statementName) {
		return entityClass.getName() + "." + statementName;
	}

	private Map<?, ?> toParameterMap(Object parameter) {
		if (parameter == null) {
			return new HashMap<Object, Object>();
		}
		if (parameter instanceof Map) {
			return (Map<?, ?>) parameter;
		} else {
			try {
				return PropertyUtils.describe(parameter);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return null;
			}
		}
	}

	@Autowired
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }
}
