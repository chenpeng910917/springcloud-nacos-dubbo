package com.consumer.rpc.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author chenpeng
 */
@Data
public class HelloConsumerVO implements Serializable {

    private String name;

    private Integer age;

}
