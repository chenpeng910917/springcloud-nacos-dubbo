package com.provider.rpc.model.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chenpeng
 */
@Data
public class HelloProviderParam implements Serializable {

    private String name;

    private Integer age;
}
