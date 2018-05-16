package com.zyk.sso.auth.service;

import com.zyk.sso.auth.common.service.BaseService;
import com.zyk.sso.auth.entity.CustomerEntity;

import java.util.List;

/**
 * 描述：</b><br>
 * @author：系统生成
 * @version:1.0
 */
public interface CustomerService extends BaseService {
	
	/**
     * 描述: 根据主键查询
	 * @param customerid 主键 
     * @return
     */
	public CustomerEntity get(Integer customerid);
	

	/*user customize code start*/

	/*user customize code end*/
}
