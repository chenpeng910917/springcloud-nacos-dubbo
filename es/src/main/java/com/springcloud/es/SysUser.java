package com.springcloud.es;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;


/**
 * @author chenpeng
 */
@Data
@Document(indexName = "finance")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer payType;

    private Integer money;

}
