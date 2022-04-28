package com.springcloud.mysql.common.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 业务异常枚举
 *
 * @author chenpeng
 */

public enum ExceptionCodeEnum {


    SUCCESS(5000, "成功"),
    ERROR_PARAM(5001, "参数错误"),
    ERROR(5002, "失败"),
    ;

    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String msg;

    ExceptionCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
