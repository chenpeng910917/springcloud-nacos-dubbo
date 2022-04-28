package com.springcloud.mysql.model.vo;

import java.io.Serializable;
import lombok.Data;

/**
 * (User)实体类
 *
 * @author chenpeng
 * @since 2022-04-28 16:20:58
 */
@Data
public class UserVO implements Serializable {
    private static final long serialVersionUID = 959546118112249143L;
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
