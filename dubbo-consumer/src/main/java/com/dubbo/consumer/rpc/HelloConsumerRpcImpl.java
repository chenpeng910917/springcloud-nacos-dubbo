package com.dubbo.consumer.rpc;


import com.consumer.rpc.HelloConsumerRpc;
import com.consumer.rpc.model.param.HelloConsumerParam;
import com.consumer.rpc.model.vo.HelloConsumerVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author chenpeng
 * @date 2022/4/20
 */
@Slf4j
@DubboService(version = "1.0.0", group = "consumer", interfaceClass = HelloConsumerRpc.class, timeout = 3000)
public class HelloConsumerRpcImpl implements HelloConsumerRpc {

    @Override
    public HelloConsumerVO helloConsumer(HelloConsumerParam param) {
        log.info("消费者param={}", param);
        HelloConsumerVO helloConsumerVO = new HelloConsumerVO();
        helloConsumerVO.setName(param.getName());
        helloConsumerVO.setAge(param.getAge());
        return helloConsumerVO;
    }
}
