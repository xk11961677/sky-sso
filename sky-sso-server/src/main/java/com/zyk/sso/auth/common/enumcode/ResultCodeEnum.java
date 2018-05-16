package com.zyk.sso.auth.common.enumcode;

/**
 * @author sky
 * @description
 * @create 2017-11-28 下午5:22
 **/

public enum ResultCodeEnum {
    SUCCESS("SUCC", "成功"),

    FAILURE("FAIL", "失败");

    private String msg;
    private String code;

    private ResultCodeEnum(String code, String msg) { this.msg = msg;
        this.code = code;
    }

    public String getMsg()
    {
        return this.msg;
    }

    public String getCode() {
        return this.code;
    }
}
