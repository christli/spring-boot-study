package com.christli.studyweb.controller;

import com.christli.studyweb.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * RESTful API 风格示例 对资源 user 进行操作
 * 本示例没有使用数据库，也没有使用 service 类来辅助完成，所有操作在本类中完成
 */
@RestController
@RequestMapping("/api/user")
public class ApiController {
    /**
     * 模拟一组数据
     */
    private List<User> getData() {
        List<User> list = new ArrayList<>();

        User user = new User();
        user.setUserId(1);
        user.setUserName("admin");
        list.add(user);

        user = new User();
        user.setUserId(2);
        user.setUserName("heike");
        list.add(user);

        user = new User();
        user.setUserId(3);
        user.setUserName("tom");
        list.add(user);

        user = new User();
        user.setUserId(4);
        user.setUserName("mac");
        list.add(user);

        return list;
    }


    /**
     * SELECT 查询操作，返回一个JSON数组
     * 具有幂等性
     */
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public Object getUsers() {
        List<User> list = new ArrayList<>();

        list = getData();

        return list;
    }

    /**
     * SELECT 查询操作，返回一个新建的JSON对象
     * 具有幂等性
     */
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Object getUser(@PathVariable("id") String id) {

        if (null == id) {
            return null;
        }

        List<User> list = getData();
        User userDao = null;
        for (User user : list) {
            if (id.equals(user.getUserId().toString())) {
                userDao = user;
                break;
            }
        }

        return userDao;
    }

    /**
     * 新增一个用户对象
     * 非幂等
     */
    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public Object addUser(@Valid @RequestBody User user) {

        List<User> list = getData();
        list.add(user);//模拟向列表中增加数据
        return user;
    }

    /**
     * 编辑一个用户对象
     * 幂等性
     */
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Object editUser(@PathVariable("id") String id, @RequestBody User user) {
        List<User> list = getData();
        for (User user1 : list) {
            if (id.equals(user1.getUserId().toString())) {
                user1 = user;
                break;
            }
        }

        return user;
    }

    /**
     * 删除一个用户对象
     * 幂等性
     * 返回 HttpStatus.NO_CONTENT 表示无返回内容
     */
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") String id) {
        List<User> list = getData();
        User userDao = null;
        for (User user : list) {
            if (id.equals(user.getUserId().toString())) {
                //删除用户
                userDao = user;
                break;
            }
        }
    }
}
