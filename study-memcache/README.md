### 集成memcached

> 方法和作用

- `set(key,value)` : 向key中添加值如果存就替换
- `add(key,value)   ` : 向key中添加值如果存在就不替换
- `replace(key,value)` :替换缓存key的值为value
- `delete(key)` : 删除缓存key的值

- 参考 [缓存应用 Memcached 入门教程](https://www.cnblogs.com/fishpro/p/spring-boot-study-memcached.html)