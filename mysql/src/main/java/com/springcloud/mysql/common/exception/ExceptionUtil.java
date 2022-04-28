package com.springcloud.mysql.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异常处理缩短日志
 *
 * @author chenpeng
 */
@Slf4j
public class ExceptionUtil {

    public static Logger logger = LoggerFactory.getLogger(ExceptionUtil.class);

    public static String getExceptionMsg(String errorMsg, Exception e) {
        String msg = "";
        try {
            if (e instanceof SpringException) {
                msg = StringUtils.join(errorMsg, " code=", ((SpringException) e).getErrorCode(), "msg=", ((SpringException) e).getErrorMsg());
            } else {
                msg = StringUtils.join(errorMsg, "msg=", e.getMessage());
            }
        } catch (Exception e1) {
            msg = errorMsg;
            log.error("getExceptionMsg");
        }
        return msg;
    }
}
