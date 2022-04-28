package com.springcloud.mysql.common;

public enum ServiceResponseEnum {
    SUCESS(1, "服务接口调用成功"),
    ;

    ServiceResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    protected int code;
    protected String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
