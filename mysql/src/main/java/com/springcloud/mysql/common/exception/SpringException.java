package com.springcloud.mysql.common.exception;

/**
 * 业务异常类
 *
 * @author chenpeng
 */
public class SpringException extends RuntimeException {
    /**
     * 错误码
     */
    private final int errorCode;

    /**
     * 错误信息
     */
    private final String errorMsg;

    public SpringException(int errorCode, String errorMsg) {
        super("{\"errorCode\":" + errorCode + ",\"errorMsg\":\"" + errorMsg + "\"}");
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public SpringException(ExceptionCodeEnum exceptionCodeEnum) {
        this(exceptionCodeEnum.getCode(), exceptionCodeEnum.getMsg());
    }

    public SpringException(ExceptionCodeEnum exceptionCodeEnum, String errorMsg) {
        this(exceptionCodeEnum.getCode(), errorMsg);
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
