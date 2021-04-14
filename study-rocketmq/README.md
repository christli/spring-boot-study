# 集成Rocketmq

- 安装参考 [docker-rocketmq](https://github.com/foxiswho/docker-rocketmq)
- 代码参考 [RocketMQ集成和场景案例分析](https://blog.csdn.net/hemin1003/article/details/90405506)

---

### 需求背景

> RocketMq是一个由阿里巴巴开源的消息中间件，在设计上借鉴了Kafka，其2012年开源，2017年成为Apache顶级项目。

> 一般MQ适用场景如下：
>1. 流量削峰，提升系统高并发处理能力，比如秒杀场景
>2. 异步处理，应用解耦，提高系统吞吐量

> RocketMQ基础组件：
>1. Producer是消息生产者
>2. Consumer是消息消费者
>3. Topic是消息传递中间者，其中存放的是消息逻辑地址

### application.yml文件

````yaml
spring:
  # mq service
  rocketmq:
    name-server: localhost:9876
    producer:
      group: my-mq-group
      send-message-timeout: 86400
      compress-message-body-threshold: 4096
      max-message-size: 4194304
      retry-times-when-send-async-failed: 0
      retry-next-server: true
      retry-times-when-send-failed: 2
````

### RocketMQ的最佳实践中推荐

> - 一个应用尽可能用一个Topic，消息子类型用tags来标识，tags可以由应用自由设置。
>- 在使用rocketMQTemplate发送消息时，通过设置发送方法的destination参数来设置消息的目的地，
>- destination的格式为topicName:tagName，:前面表示topic的名称，后面表示tags名称。

