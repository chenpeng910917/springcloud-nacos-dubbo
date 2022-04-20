package com.dubbo.provider.rpc;

import com.provider.rpc.HelloProviderRpc;
import com.provider.rpc.model.param.HelloProviderParam;
import com.provider.rpc.model.vo.HelloProviderVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author chenpeng
 */
@Slf4j
@DubboService(version = "1.0.0", group = "provider", interfaceClass = HelloProviderRpc.class, timeout = 3000)
public class HelloProviderRpcImpl implements HelloProviderRpc {

    @Override
    public HelloProviderVO helloProvider(HelloProviderParam param) {
        log.info("生产者入参param={}", param);
        HelloProviderVO helloProviderVO = new HelloProviderVO();
        helloProviderVO.setName(param.getName());
        helloProviderVO.setAge(param.getAge());
        return helloProviderVO;
    }
}
