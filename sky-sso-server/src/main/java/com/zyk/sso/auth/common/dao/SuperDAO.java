package com.zyk.sso.auth.common.dao;

import com.zyk.sso.auth.common.entity.BasePageDTO;
import com.zyk.sso.auth.common.entity.BasicEntity;
import com.zyk.sso.auth.common.entity.Pagination;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;



/**
 * 描述: 通用数据库操作DAO接口类，该类实现了常用的数据库操作
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
public interface SuperDAO {

	/**
     * 描述: 自定义更新实体信息
     * @param statementName 自定义更新sql映射对应的ID
     * @param parameterObject 需要更新的实体信息
     * @return
     */
    Integer update(String statementName, Object parameterObject);
    
    /**
     * 描述: 系统默认更新实体信息
     * @param parameterObject 需要更新的实体信息
     * @return
     */
    Integer update(BasicEntity parameterObject);

    /**
     * 描述: 自定义删除满足条件的实体信息
     * @param statementName 自定义删除sql映射对应的ID
     * @param parameterObject 需要删除的实体条件信息
     * @return
     */
    Integer delete(String statementName, Object parameterObject);
    
    /**
     * 描述: 系统默认删除满足条件的实体信息
     * @param parameterObject 需要删除的实体条件信息
     * @return
     */
    Integer delete(BasicEntity parameterObject);

    /**
     * 描述: 自定义插入实体信息
     * @param statementName 自定义插入sql映射对应的ID
     * @param parameterObject 需要插入的实体信息
     * @return
     */
    Integer insert(String statementName, Object parameterObject);
    
    /**
     * 描述: 系统默认插入实体信息
     * @param parameterObject 需要插入的实体信息
     * @return
     */
    Integer insert(BasicEntity parameterObject);

    /**
     * 描述: 系统默认根据主键查询唯一记录
     * @param parameterObject 实体参数查询条件（需包含主键信息）
     * @return
     */
	BasicEntity get(BasicEntity parameterObject);
    
	/**
     * 描述: 系统默认根据根据条件返回一条记录。适用于有唯一性的列做为条件查询
     * @param parameterObject 实体参数查询条件
     * @return
     */
	BasicEntity getOne(BasicEntity parameterObject);
    /**
     * 描述: 自定义根据实体参数查询满足条件的记录
     * @param statementName 自定义查询ID
     * @param parameterObject 实体参数查询条件
     * @return
     */
	List<Object> getList(String statementName, Object parameterObject);
    
    /**
     * 描述: 系统默认根据实体参数查询满足条件的记录
     * @param parameterObject 实体参数查询条件
     * @return
     */
	List<BasicEntity> getList(BasicEntity parameterObject);

    /**
     * 描述: 自定义分页查询通用方法
     * @param statementName 自定义查询ID
     * @param baseParamDto 查询参数
     * @return
     */
	Pagination getPagenationList(String statementName, BasePageDTO baseParamDto);
    
	/**
	 * 描述: 系统默认分页查询方法
	 * @param baseParamDto 查询参数
	 * @return
	 */
	Pagination getPagenationList(BasePageDTO baseParamDto);

	/**
	 * 描述: 获取结果集为Map信息
	 * @param statementName 查询sql映射ID
	 * @param parameterObject 参数信息
	 * @param key 主键信息
	 * @return
	 */
	<T, V> Map<T, V> getMap(String statementName, T parameterObject, String key);


    /**
     * 描述: 通用获取结果集首行记录信息
     * @param statementName sql映射ID
     * @param parameterObject 查询条件参数信息
     * @return
     */
	<T> T getObject(String statementName, T parameterObject);
    
    /**
     * 描述: 自定义根据序列号名称获取对应的下一序列号值方法
     * @param tableName 表名称
     * @return
     */
    Long getNextIdVal(String tableName);
    
    /**
     * 描述: 系统默认更新实体信息(可以设置字段为空)
     * @param parameterObject 需要更新的实体信息
     * @return
     */
    Integer updateBak(BasicEntity parameterObject);
    
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
     * 
     * 描述: 自定义查询（可以指定查询数量）
     * @param statementName 自定义查询ID
     * @param parameterObject 自定义查询参数
     * @param rowBounds 查询数量设置
     * @return
     */
    List<? extends BasicEntity> getList(String statementName, Object parameterObject, RowBounds rowBounds);
    /**
	 * 描述: 系统默认根据实体参数查询满足条件的记录数量
	 * 
	 * @param parameterObject
	 *            实体参数查询条件
	 * @return
	 */
	Integer getCount(BasicEntity parameterObject);

}
