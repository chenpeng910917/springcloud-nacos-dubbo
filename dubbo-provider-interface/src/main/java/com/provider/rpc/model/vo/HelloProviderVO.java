package com.provider.rpc.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chenpeng
 */
@Data
public class HelloProviderVO implements Serializable {

    private String name;

    private Integer age;

}
