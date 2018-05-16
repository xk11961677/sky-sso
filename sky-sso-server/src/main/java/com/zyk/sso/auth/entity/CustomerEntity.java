package com.zyk.sso.auth.entity;

import com.zyk.sso.auth.common.entity.BasicEntity;
import com.zyk.sso.auth.service.CustomerService;
import com.zyk.sso.auth.common.util.SpringUtils;
import lombok.Data;

import java.util.Date;

/**
 * 描述：</b><br>
 * @author：系统生成
 * @version:1.0
 */
@Data
public class CustomerEntity extends BasicEntity {
	private static final long serialVersionUID = 1L;
	
	/**
	 *主键
	 */
	private Integer customerid;
	/**
	 *登录名
	 */
	private String loginname;
	/**
	 *登录密码
	 */
	private String loginpassword;
	/**
	 *用户名称
	 */
	private String name;
	/**
	 *用户手机号
	 */
	private String mobile;
	/**
	 *权限
	 */
	private Integer authority;
	/**
	 *照片
	 */
	private String photo;

	private Date crtTime;

	private Date uptTime;
	/**
	 *
	 */
	private String attr1;
	/**
	 *
	 */
	private String attr2;
	/**
	 *
	 */
	private String attr3;
	/**
	 *
	 */
	private String attr4;
	/**
	 *
	 */
	private String attr5;
	
	/**
	 * 获取主键字段
	 */
	@Override
    public String primaryKey() {
    	if(customerid==null){
    		throw new IllegalArgumentException("主键为空!");
    	}
    	return customerid.toString();
    }
    
	/**
	 * 获取实体类名称
	 */
	@Override
    public String className() {
        return CustomerEntity.class.getName();
    }
	
	/**
	 * 获取service数据操作类型
	 */
	@Override
    public CustomerService service() {
        return (CustomerService)SpringUtils.getBean("customerServiceImpl");
    }
	
	/*user customize code start*/
	/**
	 * 登录时间
	 */
	private String logintime;

	private String creater;

	private String validateCode;

	private String searchName;

	private Date beginDate;

	private Date endDate;
	/*user customize code end*/
}

