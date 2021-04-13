# 集成Rabbitmq

> - 队列服务一般有三个概念：生产者，队列，消费者
>- rabbitmq在生产者和队列之间，加入了交换器（Exchange）

> - 所以rabbitmq包括两部分，交换机和队列

- 四个重要概念：虚拟主机、交换机、队列、绑定
- 一个虚拟主机持有一组交换机、队列和绑定
- 交换机用于转发消息，但是它不会做存储，没有队列绑定就直接丢弃信息
- 路由键：交换机转发消息到队列，就是根据路由键来确定是哪个队列
- 交换机可以绑定多个队列

> - 交换机有四种类型：Direct，topic，Headers，Fanout
>1. Direct:默认模式，根据key全文匹配绑定队列
>2. topic：根据通配符，字符串，用句号`.`分隔，信号`*`指定位置的一个单词，井号`#`表示一个或多个单词
>3. Headers:headers属性，一组键值对
>4. Fanout：广播模式，把消息发给绑定的全部队列

- Spring Boot 提供了`spring-boot-starter-amqp`项目对消息各种支持

> - 支持传对象
>- 先建好queue，然后按需求建exchange，默认是direct，就不用建exchange
>- 建好之后，BindingBuilder，把exchange和queue绑定在一起，topic用with函数来确定routingKey
>- topic类型的exchange，如果消息指定的routingKey匹配到多个队列，则对多个队列都转发消息
---

- 参考 [rabbitMQ的用法](http://www.ityouknow.com/springboot/2016/11/30/spring-boot-rabbitMQ.html)
