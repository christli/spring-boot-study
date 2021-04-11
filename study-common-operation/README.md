### 读取配置文件的多种方式

1. 使用`@Value`注解读取
1. 使用`@ConfigurationProperties`读取
1. 使用`@PropertySource`读取特定的配置文件信息
    1. @PropertySource常用的三个属性，一个是value用于指定配置文件，另一个是encoding用于指定编码，最后一个是factory，用于指定解析工厂。
    2. 这里需要注意一下：@PropertySource默认只会加载properties格式的文件，也就是我们如果指定了yml类型的文件是不会生效的，这时候就需要我们重写解析工厂。

- 参考 [Spring Boot 读取配置文件的几种方式](https://javatip.cn/archives/72)

--- 

### 实现多文件上传

- Spring Boot默认上传的单个文件大小1MB，一次上传的总文件大小为10MB。
- `@Configuration` 配置类来设置上传文件大小限制
- 单个文件上传使用MultipartFile参数来接收文件
- 多文件使用MultipartFile[ ]数组来接收，然后遍历它，当成单文件来处理
- 将文件上传到服务器物理路径下，然后做个映射关系，让图片可以正常被访问
  ```java
    public class FileConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/static/**").addResourceLocations("file:"+"/Users/libinbin/data/java/static/");
        } 
    }
  ```
- 自定义静态资源路径: `spring.web.resources.static-locations=classpath:/templates/`
- 参考 [Spring Boot 多文件上传](https://javatip.cn/archives/74)

--- 

### 邮件发送

- 简单邮件
- 发送 html 格式邮件
- 发送带静态资源的邮件
- 邮件模板

- 参考 纯洁的微笑 [Spring Boot (十)：邮件服务](http://www.ityouknow.com/springboot/2017/05/06/spring-boot-mail.html)

---

### 操作Excel

- `HSSFWorkbook` 是操作 Excel2003 以前（包括2003）的版本，扩展名是.xls；
- `XSSFWorkbook` 是操作 Excel2007 后的版本，扩展名是.xlsx；
- `SXSSFWorkbook` 是操作 Excel2007 后的版本，扩展名是.xlsx；
- 参考 [Spring Boot 操作 Excel](https://www.cnblogs.com/fishpro/p/spring-boot-study-excel.html)

---

### 使用Dom4j XStream操作Xml

> Dom4j 专注于 Xml 操作的高性能库，Xstream 则专注于 对象之间的转换。

#### Dom4j

- Dom4j 为了支持 XPath、XML Schema、基于事件处理大文档或流文档。
- Dom4j 为提供构建文档表示的选项，为可通过 Dom4j-API 和标准底层 dom-API 并行访问功能。
- 为实现上述宏伟目标，Dom4j 使用接口和抽象基本类方法并大量使用 JDK 中 Collections 类。
- 所以 Dom4j 有丰富的 API,在灵活性上面 Dom4j 更占有优势，性能方面也无可挑剔。

#### XStream

- XStream 为基于注解不需要其它辅助类或映射文件 的OXMapping 技术，如果你用过 hibernate 或 mybatis 之类的 ORM 框架就不难理解这里的 OXM。
- XStream 可以将 JavaBean 序列化为 XML，或将 XML 反序列化为 JavaBean，使得XML序列化不再繁琐。
- XStream 也可以将 JavaBean 序列化成 Json 或反序列化，使用非常方便。
- 没有映射文件而且底层使用 xmlpull 推模型解析 XML，高性能、低内存占用，结合简洁明了的 API，上手基本是分分钟的事情。
- XStream 同时也可以定制转换类型策略并配有详细的错误诊断，能让你快速定位问题。

- 参考 [Spring Boot 使用 Dom4j XStream 操作 Xml](https://www.cnblogs.com/fishpro/p/spring-boot-study-xml.html)