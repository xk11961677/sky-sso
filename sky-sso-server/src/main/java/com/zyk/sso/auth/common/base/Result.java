package com.zyk.sso.auth.common.base;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zyk.sso.auth.common.enumcode.FailureCodeEnum;
import com.zyk.sso.auth.common.enumcode.ResultCodeEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 通用返回结果类
 * @param <T>
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Setter
    @Getter
    private String resultCode;

    @Setter
    @Getter
    private String failureCode;

    @Setter
    @Getter
    private String failureMessage;

    @Setter
    @Getter
    private T data;

    @JsonIgnore
    public static <T> Result<T> getSuccessResult(T data) {
        Result result = new Result();
        result.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        result.setData(data);
        return result;
    }

    @JsonIgnore
    public static <T> Result<T> getSuccessResult(T data, Result result) {
        result.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        result.setData(data);
        return result;
    }

    @JsonIgnore
    public static Result getSuccessResult() {
        Result result = new Result();
        result.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        return result;
    }

    @JsonIgnore
    public static <T> Result<T> getFailureResult() {
        Result result = new Result();
        result.setResultCode(ResultCodeEnum.FAILURE.getCode());
        result.setFailureCode(FailureCodeEnum.SERVICE_EXCEPTION.getCode());
        result.setFailureMessage(FailureCodeEnum.SERVICE_EXCEPTION.getMsg());
        return result;
    }

    @JsonIgnore
    public static <T> Result<T> getFailureResult(String failureCode) {
        Result result = new Result();
        result.setResultCode(ResultCodeEnum.FAILURE.getCode());
        result.setFailureCode(failureCode);
        return result;
    }

    @JsonIgnore
    public static <T> Result<T> getFailureResult(String failureCode, String failureMessage) {
        Result result = new Result();
        result.setResultCode(ResultCodeEnum.FAILURE.getCode());
        result.setFailureCode(failureCode);
        result.setFailureMessage(failureMessage);
        return result;
    }

    public static <T> Result<T> getFailureResult(FailureCodeEnum failureCodeEnum) {
        Result result = new Result();
        result.setResultCode(ResultCodeEnum.FAILURE.getCode());
        result.setFailureCode(failureCodeEnum.getCode());
        result.setFailureMessage(failureCodeEnum.getMsg());
        return result;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return ResultCodeEnum.SUCCESS.getCode().equals(getResultCode());
    }

    public String toString() {
        return "Result [resultCode=" + this.resultCode + ", failureCode=" + this.failureCode + ", failureMessage=" + this.failureMessage + ", data=" + this.data + "]";
    }
}
