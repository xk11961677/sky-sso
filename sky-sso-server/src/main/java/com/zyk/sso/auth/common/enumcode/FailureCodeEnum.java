package com.zyk.sso.auth.common.enumcode;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @author sky
 **/
public enum FailureCodeEnum {

    SERVICE_EXCEPTION("SERVICE_EXCEPTION", "服务异常"),

    ERROR_10001("ERROR_10001", "参数错误"),

    ERROR_10002("ERROR_10002", "用户不存在"),

    ERROR_10003("ERROR_10003", "用户名或密码错误"),

    ERROR_10004("ERROR_10004", "请登录后在操作"),

    ERROR_10005("ERROR_10005", "旧密码错误")
    ;


    private String msg;

    private String code;

    FailureCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getCode() {
        return this.code;
    }

    public static FailureCodeEnum getByCode(String code) {
        Optional<FailureCodeEnum> optional = Arrays.stream(values()).filter(e -> Objects.equals(code, e.getCode())).findFirst();
        return optional.orElse(SERVICE_EXCEPTION);
    }

}