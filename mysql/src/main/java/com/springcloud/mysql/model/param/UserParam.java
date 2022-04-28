package com.springcloud.mysql.model.param;

import java.io.Serializable;
import lombok.Data;

/**
 * (User)实体类
 *
 * @author chenpeng
 * @since 2022-04-28 16:20:52
 */
@Data
public class UserParam implements Serializable {
    private static final long serialVersionUID = -20797073553768419L;
    /**
    * 主键id
    */
    private Long id;
    /**
    * 姓名
    */
    private String name;
    /**
    * 年龄
    */
    private Integer age;

}
