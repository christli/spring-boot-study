# Spring-boot : MongoDB集成用法
- 代码例子用的mongo版本是 **4.4.4**
---
- `use xxx` 来切换数据库，默认用户在admin数据库 
- `db.auth("root","example")` 来认证数据库操作，返回1表示认证成功，
- `db.createCollection("people");` 创建表，这样数据库才能读取的到
- `mongo -uroot -pexample --authenticationDatabase test` 账号登录mongo，需要具体的数据库  
---
- 参考 [MongoDB命令大全](https://blog.csdn.net/qq_42039281/article/details/90265028)
- 参考 [Spring Boot(十一)：Spring Boot 中 MongoDB 的使用](http://www.ityouknow.com/springboot/2017/05/08/spring-boot-mongodb.html)