package com.springcloud.mysql.common;

/**
 * @author chenpeng
 */
public class ServiceResponse<T> extends AbstractServiceResponse<T> {

    private int code;
    private long ts;
    private String msg;

    T data;

    private ServiceResponse() {}

    private static <S> ServiceResponse<S> buildResponse(int code, String msg, S data) {
        ServiceResponse<S> resp = new ServiceResponse<>();
        resp.setCode(code);
        resp.setMsg(msg);
        resp.setTs(System.currentTimeMillis()/1000);
        resp.setData(data);
        return resp;
    }

    public static ServiceResponse buildErrorResponse(int errCode, String errMsg) {
        return buildResponse(errCode, errMsg, null);
    }

    public static <S> ServiceResponse<S> buildErrorResponse(int errCode, String errMsg, S result) {
        return buildResponse(errCode, errMsg, result);
    }

    public static <S> ServiceResponse<S> buildSuccessResponse(S result) {
        return buildResponse(ServiceResponseEnum.SUCESS.code, ServiceResponseEnum.SUCESS.msg, result);
    }

    public static <S> ServiceResponse<S> buildSuccessResponse(String msg, S result) {
        return buildResponse(ServiceResponseEnum.SUCESS.code, msg, result);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
