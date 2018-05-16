package com.zyk.sso.auth.service.impl;

import java.util.List;

import com.zyk.sso.auth.entity.CustomerEntity;
import com.zyk.sso.auth.service.CustomerService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Repository;

import com.zyk.sso.auth.common.service.impl.BaseServiceImpl;

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
	

	 /*user customize code end*/
}
