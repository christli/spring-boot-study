package com.christli.study.redis;

import com.christli.study.redis.vo.Person;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisUtilTest {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void setKeyAndValue() {
        String key = "name";
        String value = "christli中文";
        System.out.println("访问set:key={" + key + "},value={" + value + "}");
        RedisUtil.set(this.redisTemplate, key, value);
    }

    @Test
    public void getKey() {
        String key = "name";
        System.out.println("访问set:key={" + key + "}");
        String result = (String) RedisUtil.get(this.redisTemplate, key);
        System.out.println("get result=" + result);
    }

    @Test
    public void setObj() {
        String id = "A001";
        String name = "christli中文";
        Integer age = 18;
        Person person = new Person();
        person.setId(id);
        person.setName(name);
        person.setAge(age);
        System.out.println("访问set：" + person.toString());
        RedisUtil.set(this.redisTemplate, "person:" + person.getId(), person);
        RedisUtil.hset(this.redisTemplate, "person", person.getId(), person);
    }

    @Test
    public void getObj() {
        String id = "A001";
        System.out.println("访问get:key id={" + id + "}");
        Person person = (Person) RedisUtil.get(this.redisTemplate, "person:" + id);
        String result = person.toString();
        System.out.println("get obj result=" + result);
        //hash
        Person person2 = (Person) RedisUtil.hget(this.redisTemplate, "person", id);
        String result2 = person2.toString();
        System.out.println("get obj2 result=" + result2);
    }

}