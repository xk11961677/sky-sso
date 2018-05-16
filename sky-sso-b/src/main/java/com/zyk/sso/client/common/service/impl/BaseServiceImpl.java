package com.zyk.sso.client.common.service.impl;


import java.util.List;

import com.zyk.sso.client.common.entity.BasePageDTO;
import com.zyk.sso.client.common.entity.BasicEntity;
import com.zyk.sso.client.common.entity.Pagination;
import com.zyk.sso.client.common.exception.UserException;
import com.zyk.sso.client.common.dao.SuperDAO;
import com.zyk.sso.client.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 描述: 通用数据库操作service类，其他数据库操作service类均需继承该类
 * @version V1.0
 */
public class BaseServiceImpl implements BaseService {
	
	@Autowired
	public SuperDAO superDAO;
	
	/**
	 * 获取superDAO
	 * @return superDAO superDAO
	 */
	public SuperDAO getSuperDAO() {
		return superDAO;
	}
	
	@Override
	public Long getNextIdVal(String tableName) {
		return superDAO.getNextIdVal(tableName);
	}

    @Override
    public BasicEntity get(BasicEntity entity) {
        return superDAO.get(entity);
    }
    @Override
    public BasicEntity getOne(BasicEntity entity) {
    	return superDAO.getOne(entity);
    }
    @Override
    public List<? extends BasicEntity> getList(BasicEntity entity) {
        return superDAO.getList(entity);
    }
    
    @Override
	public Integer insert(BasicEntity entity){
		return this.superDAO.insert(entity);
	}

    @Override
    public Integer insertBak(BasicEntity entity){
        return this.superDAO.insert(entity);
    }
	
	@Override
	public Integer update(BasicEntity entity){
		return this.superDAO.update(entity);
	}
	
	@Override
	public Integer delete(BasicEntity entity){
		return this.superDAO.delete(entity);
	}
	
	@Override
	public Integer updateBak(BasicEntity entity){
		return this.superDAO.updateBak(entity);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Pagination getPagenationList(String statementName, BasePageDTO baseParamDTO) {
		return this.superDAO.getPagenationList(statementName, baseParamDTO);
	}

	@Override
	public Pagination getPagenationList(BasePageDTO baseParamDTO) {
		return this.superDAO.getPagenationList(baseParamDTO);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public List getList(String statementName,Object parameterObject) {
        return superDAO.getList(statementName,parameterObject);
    }
	
	@Override
	public int batchInsert(List<? extends BasicEntity> basicEntityList) {
		return superDAO.batchInsert(basicEntityList);
	}
	
	@Override
	public int batchUpdate(List<? extends BasicEntity> basicEntityList) {
		return superDAO.batchUpdate(basicEntityList);
	}

	@Override
	public Integer getCount(BasicEntity entity) {
		return superDAO.getCount(entity);
	}

	@Override
	public void insertOrUpdateOneByOne(List<? extends BasicEntity> insertOrUpdateEntityList) {
		
		if(insertOrUpdateEntityList!=null&&insertOrUpdateEntityList.size()>0){
			for(BasicEntity b:insertOrUpdateEntityList){
				int count=0;
				if(b.canInsert()){
					count=superDAO.insert(b);
				}
				if(b.canUpdate()){
					count=superDAO.update(b);
				}
				if(b.canDelete()){
					count=superDAO.delete(b);
				}
				if(count==0){
					throw new UserException();
				}
			}
		}
		
	}

}
