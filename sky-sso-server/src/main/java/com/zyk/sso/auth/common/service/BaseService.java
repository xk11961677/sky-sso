package com.zyk.sso.auth.common.service;


import com.zyk.sso.auth.common.entity.BasePageDTO;
import com.zyk.sso.auth.common.entity.BasicEntity;
import com.zyk.sso.auth.common.entity.Pagination;

import java.util.List;


/**
 * 描述: 通用数据库操作service类，其他数据库操作service类均需继承该类
 * @version V1.0
 */
public interface BaseService {
	
	/**
     * 描述: 自定义根据序列号名称获取对应的下一序列号值方法
     * @param tableName 表名称
     * @return
     */
	Long getNextIdVal(String tableName);

	/**
	 * 
	 * 描述: 根据主键查询实体信息
	 * @param entity
	 * @return
	 */
    BasicEntity get(BasicEntity entity);
    /**
	 * 
	 * 描述:  系统默认根据根据条件返回一条记录。适用于有唯一性的列做为条件查询
	 * @param entity
	 * @return
	 */
    BasicEntity getOne(BasicEntity entity);
    /**
     * 
     * 描述:根据条件查询数量
     * @param entity
     * @return
     */
    Integer getCount(BasicEntity entity);
    /**
     * 
     * 描述:根据条件查询实体信息
     * @param entity
     * @return
     */
	List<? extends BasicEntity> getList(BasicEntity entity);
    
    /**
     * 描述: 插入实体信息
     * @param entity 需要插入的实体信息
     * @return
     */
	Integer insert(BasicEntity entity);

    /**
     * 描述: 插入实体信息
     * @param entity 需要插入的实体信息
     * @return
     */
    Integer insertBak(BasicEntity entity);
	
	/**
     * 描述: 更新实体信息
     * @param entity 需要更新的实体信息
     * @return
     */
	Integer update(BasicEntity entity);
	
	/**
     * 描述: 删除实体信息
     * @param entity 需要删除的实体信息
     * @return
     */
	Integer delete(BasicEntity entity);
	
	/**
     * 描述: 更新实体信息（可以设置字段为空）
     * @param entity 需要更新的实体信息
     * @return
     */
	Integer updateBak(BasicEntity entity);
	
	/**
	 * 
	 * 描述:自定义分页查询通用方法
	 * @param statementName sql查询ID
	 * @param baseParamDTO  分页查询条件
	 * @return
	 */
	@SuppressWarnings("rawtypes")
    Pagination getPagenationList(String statementName, BasePageDTO baseParamDTO);

	Pagination getPagenationList(BasePageDTO baseParamDTO);
	
	/**
	 * 
	 * 描述: 根据条件查询实体信息
	 * @param statementName sql查询ID
	 * @param parameterObject 查询条件
	 * @return
	 */
	List<Object> getList(String statementName, Object parameterObject);
	
	/**
     * 
     * 描述: 批量插入实体信息
     * @param basicEntityList 需要批量插入的实体信息
     * @return
     */
    int batchInsert(List<? extends BasicEntity> basicEntityList);
    
    /**
     * 
     * 描述: 批量更新实体信息
     * @param basicEntityList 需要批量更新的实体信息
     * @return
     */
    int batchUpdate(List<? extends BasicEntity> basicEntityList);

    /**
     * 按照_action_属性标记的分别一个一个的执行插入或更新。
     * @param insertOrUpdateEntityList
     */
    void insertOrUpdateOneByOne(List<? extends BasicEntity> insertOrUpdateEntityList);
}
