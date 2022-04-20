package com.consumer.rpc.model.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chenpeng
 */
@Data
public class HelloConsumerParam implements Serializable {

    private String name;

    private Integer age;
}
