package com.consumer.rpc;

import com.consumer.rpc.model.param.HelloConsumerParam;
import com.consumer.rpc.model.vo.HelloConsumerVO;

/**
 * @author chenpeng
 */

public interface HelloConsumerRpc {

    /**
     * 消费者提供接口
     *
     * @param param 入参
     * @return 结果
     */
    HelloConsumerVO helloConsumer(HelloConsumerParam param);
}
