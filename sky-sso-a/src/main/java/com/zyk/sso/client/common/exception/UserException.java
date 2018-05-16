package com.zyk.sso.client.common.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserException extends RuntimeException{

	private static final long serialVersionUID = -2332975704789408379L;
	
	private final static Logger log= LoggerFactory.getLogger(UserException.class);
	
	private String errCode;

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public UserException() {
		super();
	}
	/**
	 * 构造异常信息
	 * @param message 错误信息
	 * @param cause 异常信息
	 * @param errCode 错误代码
	 */
	public UserException(String message, Throwable cause, String errCode) {
		super(message, cause);
		this.errCode = errCode;
		log.error(message, cause);
	}

	/**
	 * 构造异常信息
	 * @param message 错误信息
	 * @param errCode 错误代码
	 */
	public UserException(String message, String errCode) {
		super(message);
		this.errCode = errCode;
		log.error(message);
	}

	/**
	 * 构造异常信息
	 * @param message 错误信息
	 * @param cause 异常信息
	 */
	public UserException(String message, Throwable cause) {
		super(message, cause);
		log.error(message, cause);
	}

	/**
	 * 构造异常信息
	 * @param message 错误信息
	 */
	public UserException(String message) {
		super(message);
		log.error(message);
	}

	/**
	 *  构造异常信息
	 * @param cause 异常信息
	 */
	public UserException(Throwable cause) {
		super(cause);
		log.error(cause.getMessage(),cause);
	}

	/**
	 * 构造异常信息
	 * @param teslaError
	 */
	public UserException(UserError.Error teslaError) {
		super(teslaError.toString());
		this.setErrCode(teslaError.getCode());
		log.error(teslaError.toString());
	}

	/**
	 * 构造异常信息
	 * @param teslaError
	 * @param cause
	 */
	public UserException(UserError.Error teslaError,Throwable cause) {
		super(teslaError.toString());
		this.setErrCode(teslaError.getCode());
		log.error(teslaError.toString(), cause);
	}
	
	/**
	 * 获取异常的堆栈信息
	 * 
	 * @param t
	 * @return
	 */
	public static String getStackTrace(Throwable t)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		try
		{
			t.printStackTrace(pw);
			String s = sw.toString();
			if(s.length()>3500){
				return sw.toString().substring(0, 3500);
			}else{
				return sw.toString().substring(0, s.length());
			}
			
		}
		finally
		{
			pw.close();
		}
	}

}
