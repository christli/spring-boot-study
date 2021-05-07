# spring-boot-admin 多服务监控

> `SpringBootAdmin`不是Spring官方提供的模块，它包含了Client和Server两部分。

- server部分提供了用户管理界面，
- client即为被监控的服务。client需要注册到server端。

> `SpringBootAdmin`提供了很少的几个监控服务端点，
> 需要依赖`SpringBootActuator`丰富监控功能。