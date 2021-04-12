## 使用logback生成日志文件

### 日志级别

> - 日志级别从高到低为：TRACE < DEBUG < INFO < WARN < ERROR < ALL < OFF。
>- 如果日志设置为ERROR，那么低于ERROR级别的日志将不会输出。

- 如果我们想要设置某个包的日志级别，则在pom文件中加入下面这行配置：

```yaml
logging:
  level:
    # 包名
    com.christli: warn
```

- 如果想修改Spring Boot默认级别，则将包名改为root。

```yaml
logging:
  level:
    root: warn
```
>- 定义一个`logback-spring.xml`的文件来进行日志信息的配置
--- 
- 参考 [Spring Boot 使用logback生成日志文件](https://javatip.cn/archives/118)
