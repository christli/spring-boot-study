package com.christli.study.transaction.user.controller;

import com.christli.study.transaction.user.entity.vo.User;
import com.christli.study.transaction.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/test1")
    public boolean test1(@RequestBody User user) {
        System.out.println("请求参数:" + user);
        try {
            userService.test1(user);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("最后查询的数据:" + userService.getBaseMapper().selectById(user.getId()));
        return true;
    }

    @PostMapping("/test2")
    public boolean test2(@RequestBody User user) {
        System.out.println("请求参数:" + user);
        userService.test2(user);
        System.out.println("最后查询的数据:" + userService.getBaseMapper().selectById(user.getId()));
        return true;
    }


    @PostMapping("/test3")
    public boolean test3(@RequestBody User user) {
        System.out.println("请求参数:" + user);
        userService.test3(user);
        System.out.println("最后查询的数据:" + userService.getBaseMapper().selectById(user.getId()));
        return true;
    }

    @PostMapping("/test4")
    public boolean test4(@RequestBody User user) {
        System.out.println("请求参数:" + user);
        userService.test4(user);
        System.out.println("最后查询的数据:" + userService.getBaseMapper().selectById(user.getId()));
        return true;
    }


}
