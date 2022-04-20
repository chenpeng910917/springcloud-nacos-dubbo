package com.provider.rpc;

import com.provider.rpc.model.param.HelloProviderParam;
import com.provider.rpc.model.vo.HelloProviderVO;

/**
 * @author chenpeng
 */
public interface HelloProviderRpc {

    HelloProviderVO helloProvider(HelloProviderParam param);

}
