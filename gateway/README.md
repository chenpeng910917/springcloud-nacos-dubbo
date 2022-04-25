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
127.0.0.1:5001/hello?name=张三&age=22&token=fd  

模拟跨域请求 （在谷歌浏览器F12控制台中输入一下代码 回车执行 会报CORS跨域请求错误 加上配置后则无问题）  
get请求  
$.get('http://127.0.0.1:5001/hello?name=13&age=22&token=fd');  
post请求  
$.post('http://127.0.0.1:5001/hello','{ "name":"张三", "age": 22, "token":"adf"}');  