package com.springcloud.mysql.common;

public abstract class AbstractServiceResponse<T> {

    public abstract int getCode();

    public abstract String getMsg();

    public abstract void setMsg(String msg);

    public abstract T getData();
}
