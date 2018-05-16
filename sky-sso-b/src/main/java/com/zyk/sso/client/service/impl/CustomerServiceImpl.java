package com.zyk.sso.client.service.impl;

import java.util.List;

import com.zyk.sso.client.common.service.impl.BaseServiceImpl;
import com.zyk.sso.client.entity.CustomerEntity;
import com.zyk.sso.client.service.CustomerService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Repository;

/**
 * 描述：</b><br>
 * @author：系统生成
 * @version:1.0
 */
@Repository
@Slf4j
public class CustomerServiceImpl extends BaseServiceImpl implements CustomerService {

	
	@Override
	public CustomerEntity get(Integer customerid){
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setCustomerid(customerid);
		return (CustomerEntity)get(customerEntity);
	}
	
	 /*user customize code start*/

	 /*user customize code end*/
}
