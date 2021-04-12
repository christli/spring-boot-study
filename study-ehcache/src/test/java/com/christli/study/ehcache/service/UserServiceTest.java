package com.christli.study.ehcache.service;

import com.christli.study.ehcache.entity.UserDO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @AfterEach
    void after() {
        System.out.println("============删除缓存================");
        userService.delete(1);
    }


    @Test
    void userTest() {
        System.out.println("============以下第一次调用 ================");
        System.out.println(userService.list());
        System.out.println(userService.get(1));
        userService.save(new UserDO(1, "christli", "123456", 1));
        userService.update(new UserDO(1, "christli", "123456434", 1));

        System.out.println("============以下第二次调用 观察 list 和 get 方法 ================");
        System.out.println(userService.list());
        System.out.println(userService.get(1));
        userService.save(new UserDO(1, "christli", "123456", 1));
        userService.update(new UserDO(1, "christli", "123456434", 1));


        System.out.println("============以下第三次调用 先删除 观察 list 和 get 方法 ================");
        userService.delete(1);
        userService.list();
        userService.get(1);
        userService.save(new UserDO(1, "christli", "123456", 1));
        userService.update(new UserDO(1, "christli", "123456434", 1));

    }
}