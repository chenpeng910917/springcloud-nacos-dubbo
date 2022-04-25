# 工程简介

# 延伸阅读

在nacos配置中心 新创建data id = gateway-router group = DEFAULT_GROUP

```json
[
    {
    "filters": [],
    "id": "path1",
    "predicates":[
        {
            "name": "Path",
            "args": {
                "pattern": "/hello/**"
            }
        }
    ],
    "uri": "lb://dubbo-spring-cloud-provider"
}
]
```


请求地址  请求网关服务 网关服务转发到provider服务  把provider和consumer服务都启动起来
127.0.0.1:5001/hello?name=张三&age=22