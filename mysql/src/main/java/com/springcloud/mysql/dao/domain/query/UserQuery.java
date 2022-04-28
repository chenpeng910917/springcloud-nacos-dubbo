package com.springcloud.mysql.dao.domain.query;

import java.io.Serializable;
import lombok.Data;

/**
 * (User)实体类
 *
 * @author chenpeng
 * @since 2022-04-28 16:20:55
 */
@Data
public class UserQuery implements Serializable {
    private static final long serialVersionUID = -71727916461249042L;
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
