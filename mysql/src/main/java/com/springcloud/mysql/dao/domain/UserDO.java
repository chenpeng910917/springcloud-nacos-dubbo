package com.springcloud.mysql.dao.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * (User)实体类
 *
 * @author chenpeng
 * @since 2022-04-28 16:20:58
 */
@Data
public class UserDO implements Serializable {
    private static final long serialVersionUID = 112619685081024506L;
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
