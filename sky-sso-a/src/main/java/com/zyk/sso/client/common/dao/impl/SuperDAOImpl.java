package com.zyk.sso.client.common.dao.impl;


import com.zyk.sso.client.common.entity.BasePageDTO;
import com.zyk.sso.client.common.entity.BasicEntity;
import com.zyk.sso.client.common.entity.Pagination;
import com.zyk.sso.client.common.exception.UserException;
import com.zyk.sso.client.common.dao.SuperDAO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;



@Repository
@SuppressWarnings({ "unchecked", "rawtypes" })
@Slf4j
public class SuperDAOImpl implements SuperDAO {

    private static final String pagenationStatementSuffix = "Mapper.getPagenationList";
    private static final String pagenationCountStatementSuffix = "-count";
    private static final String getListStatementSuffix = "Mapper.getList";
    private static final String selectStatementSuffix = "Mapper.findByPriKey";
    private static final String insertStatementSuffix = "Mapper.insert";
    private static final String updateStatementSuffix = "Mapper.update";
    private static final String deleteStatementSuffix = "Mapper.deleteByPriKey";
    private static final String getNextIdValStatement = "CommonEntity.getNextIdVal";
    private static final String batchUpdateStatementSuffix = "Mapper.batchUpdate";
    private static final String batchInsertStatementSuffix = "Mapper.batchInsert";

    /**
     * 批量sql单次执行的数量
     */
    private static final int batchExecuteOnceCount = 300;
    /**
	 * 运行环境的SessionTemplate
	 */
    @Autowired
	private SqlSessionTemplate zykSqlSessionTemplate;
    
    
    @Override
    public Integer  update(String statementName, Object parameterObject) {
    	return zykSqlSessionTemplate.update(statementName, parameterObject);
    }
    

    @Override
    public Integer update(BasicEntity parameterObject) {
    	String statementName = parameterObject.getClass().getSimpleName()+updateStatementSuffix;
        return this.update(statementName, parameterObject);
    }
    

    @Override
    public Integer delete(String statementName, Object parameterObject) {
        return zykSqlSessionTemplate.delete(statementName, parameterObject);
    }
    

    @Override
    public Integer delete(BasicEntity parameterObject) {
    	String statementName = parameterObject.getClass().getSimpleName()+deleteStatementSuffix;
        return this.delete(statementName,parameterObject);
    }
    

    @Override
    public Integer insert(String statementName, Object parameterObject) {
    	return zykSqlSessionTemplate.insert(statementName, parameterObject);
    }
    

    @Override
    public Integer insert(BasicEntity parameterObject) {
    	String statementName = parameterObject.getClass().getSimpleName()+insertStatementSuffix;
    	return zykSqlSessionTemplate.insert(statementName, parameterObject);
    }
    

    @Override
	public BasicEntity get(BasicEntity parameterObject) {
		String statementName = parameterObject.getClass().getSimpleName()+selectStatementSuffix;
		return zykSqlSessionTemplate.selectOne(statementName, parameterObject);
	}
	
    @Override
    public BasicEntity getOne(BasicEntity parameterObject) {
    	String statementName = parameterObject.getClass().getSimpleName()+getListStatementSuffix;
    	return zykSqlSessionTemplate.selectOne(statementName, parameterObject);
    }

	@Override
    public List<Object> getList(String statementName, Object parameterObject){
        return zykSqlSessionTemplate.selectList(statementName, parameterObject);
    }


    @Override
    public List<BasicEntity> getList(BasicEntity parameterObject) throws DataAccessException {
    	String statementName = parameterObject.getClass().getSimpleName()+getListStatementSuffix;
    	return zykSqlSessionTemplate.selectList(statementName,parameterObject);
    }
    

    @Override
	public Pagination getPagenationList(String statementName, BasePageDTO baseParamDTO) {
        if (baseParamDTO == null || !baseParamDTO.isPage()) {
            return null;
        }
        /**
         * 判断pageNum和pageSize
         */
        if (baseParamDTO.getPageNum() == null || baseParamDTO.getPageNum().intValue() < 1) {
            baseParamDTO.setPageNum(Pagination.DEFAULT_PAGE_NUM);
        }
        if (baseParamDTO.getPageSize() == null || baseParamDTO.getPageSize().intValue() < 1) {
            baseParamDTO.setPageSize(Pagination.DEFAULT_PAGE_SIZE);
        }

        // 计算记录起始值和结束值
//        baseParamDTO.setEndIdx(baseParamDTO.getPageSize() * baseParamDTO.getPageNum());
//        baseParamDTO.setStartIdx(baseParamDTO.getPageSize() * (baseParamDTO.getPageNum() - 1));//mysql 索引下标从0开始

        Integer totalCount = (Integer) zykSqlSessionTemplate.selectOne(statementName + pagenationCountStatementSuffix , baseParamDTO);

        List resultList = zykSqlSessionTemplate.selectList(statementName, baseParamDTO);

        return new Pagination(baseParamDTO.getPageSize(), baseParamDTO.getPageNum(), totalCount, resultList);
    }
    

    @Override
    public Pagination getPagenationList(BasePageDTO baseParamDTO) {
    	String statementName = baseParamDTO.getClass().getSimpleName()+pagenationStatementSuffix;
    	return this.getPagenationList(statementName, baseParamDTO);
    }


	@Override
    public <T, V> Map<T, V> getMap(String statementName, T parameterObject, String key) {
        return zykSqlSessionTemplate.selectMap(statementName, parameterObject, key);
    }


	@Override
	public <T> T getObject(String statementName, T parameterObject) {
        return (T) zykSqlSessionTemplate.selectOne(statementName, parameterObject);
    }

	
	@Override
	public Long getNextIdVal(String tableName) {
		return zykSqlSessionTemplate.selectOne(getNextIdValStatement, tableName);
	}
	

    @Override
    public Integer updateBak(BasicEntity parameterObject) {
    	String statementName = parameterObject.getClass().getSimpleName()+updateStatementSuffix+"Bak";
        return this.update(statementName, parameterObject);
    }


	@Override
	public int batchInsert(List<? extends BasicEntity> basicEntityList) {
		//每次I/O提交数量
		int singleNum = batchExecuteOnceCount;
		//sql执行影响数据行数
        int affectedRows = 0;
        try {
        	 if(basicEntityList != null && basicEntityList.size()>0){
            	 String statementName = basicEntityList.get(0).getClass().getSimpleName();
            	 statementName = statementName + batchInsertStatementSuffix;
            	 int rowSize = basicEntityList.size();//数据总量
 				 int fromIndex = 0;//起始序号
 				 int endIndex = 0;//结束序号
             	 log.debug("批量执行插入【"+statementName+"】开始："+System.currentTimeMillis());
                 for (int i = 0; i==0 || i < Math.ceil(Double.valueOf(Integer.valueOf(rowSize / singleNum))); i++) {
                     endIndex = (i + 1) * singleNum;//默认结束序号滚动一页
                 	 if(endIndex >= rowSize){//如结束序号大于等于数据总量时，把数据总量赋值给结束序列
                 		endIndex = rowSize;
                 	 }
                 	 log.debug("批量执行插入【"+statementName+"】第【"+(i+1)+"】次共【"+(endIndex-fromIndex)+"】条记录开始："+System.currentTimeMillis());
                     affectedRows += zykSqlSessionTemplate.insert(statementName, basicEntityList.subList(fromIndex, endIndex));
                     log.debug("批量执行插入【"+statementName+"】第【"+(i+1)+"】次共【"+(endIndex-fromIndex)+"】条记录结束："+System.currentTimeMillis());
                     fromIndex = endIndex;
                 }
                 log.debug("批量执行插入【"+statementName+"】结束："+System.currentTimeMillis());
            }
		} catch (Exception e) {
			log.error("系统批量插入数据出错：",e);
            throw new UserException();
		}
        return affectedRows;
	}

	@Override
	public int batchUpdate(List<? extends BasicEntity> basicEntityList) {
		//每次I/O提交数量
		int singleNum = batchExecuteOnceCount;
		//sql执行影响数据行数
        int affectedRows = 0;
        try {
        	if(basicEntityList != null && basicEntityList.size()>0){
				String statementName = basicEntityList.get(0).getClass().getSimpleName();
				statementName = statementName + batchUpdateStatementSuffix;
				int rowSize = basicEntityList.size();//数据总量
				int fromIndex = 0;//起始序号
				int endIndex = 0;//结束序号
            	log.debug("批量执行更新【"+statementName+"】开始："+System.currentTimeMillis());
                for (int i = 0; i==0 || i < Math.ceil(Double.valueOf(Integer.valueOf(rowSize / singleNum))); i++) {
                	endIndex = (i + 1) * singleNum;//默认结束序号滚动一页
                	if(endIndex >= rowSize){//如结束序号大于等于数据总量时，把数据总量赋值给结束序列
                		endIndex = rowSize;
                	}
                	log.debug("批量执行更新【"+statementName+"】第【"+(i+1)+"】次共【"+(endIndex-fromIndex)+"】条记录开始："+System.currentTimeMillis());
     //               affectedRows += zykSqlSessionTemplate.update(statementName, basicEntityList.subList(fromIndex, endIndex));
                	for(BasicEntity basic:basicEntityList.subList(fromIndex, endIndex)){
						affectedRows +=update(basic);
					}
                    log.debug("批量执行更新【"+statementName+"】第【"+(i+1)+"】次共【"+(endIndex-fromIndex)+"】条记录结束："+System.currentTimeMillis());
                    fromIndex = endIndex;
                }
                log.debug("批量执行更新【"+statementName+"】结束："+System.currentTimeMillis());
           }
		} catch (Exception e) {
			log.error("系统批量更新数据出错：",e);
			throw new UserException();
		} 
        return affectedRows;
	}


	@Override
	public List<? extends BasicEntity> getList(String statementName, Object parameterObject, RowBounds rowBounds) {
      return zykSqlSessionTemplate.selectList(statementName, parameterObject,rowBounds);
	}
	@Override
	public Integer getCount(BasicEntity parameterObject) {
		String statementName = parameterObject.getClass().getSimpleName() + pagenationStatementSuffix;
		Integer totalCount = (Integer) zykSqlSessionTemplate.selectOne(statementName + pagenationCountStatementSuffix, parameterObject);
		return totalCount;
	}

}
