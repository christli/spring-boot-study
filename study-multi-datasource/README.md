# Spring Boot 多数据源切换
- 参考 [MybatisPlus框架动态数据源用法（支持多个DB，动态切换数据源）](https://hemin.blog.csdn.net/article/details/105145250)
---  
### 使用mybatis-plus有以下优势
- 数据源分组，适用于多种场景 纯粹多库 读写分离 一主多从 混合模式。
- 简单集成Druid数据源监控多数据源，简单集成Mybatis-Plus简化单表，简单集成P6sy格式化sql，简单集成Jndi数据源。
- 简化Druid和HikariCp配置，提供全局参数配置。
- 提供自定义数据源来源(默认使用yml或properties配置)。
- 项目启动后能动态增减数据源。
- 多层数据源嵌套切换。（一个业务ServiceA调用ServiceB，ServiceB调用ServiceC，每个Service都是不同的数据源）
---
### 使用`@DS`注解
- 使用 `@DS` 切换数据源
- `@DS`可以注解在方法上和类上，同时存在方法注解优先于类上注解。
- 注解在service实现或mapper接口方法上，但强烈不建议同时在service和mapper注解。