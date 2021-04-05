package com.christli.studyweb.controller;

import com.christli.studyweb.dao.UserDao;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


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
    private List<UserDao> getData() {
        List<UserDao> list = new ArrayList<>();

        UserDao userDao = new UserDao();
        userDao.setUserId(1);
        userDao.setUserName("admin");
        list.add(userDao);

        userDao = new UserDao();
        userDao.setUserId(2);
        userDao.setUserName("heike");
        list.add(userDao);

        userDao = new UserDao();
        userDao.setUserId(3);
        userDao.setUserName("tom");
        list.add(userDao);

        userDao = new UserDao();
        userDao.setUserId(4);
        userDao.setUserName("mac");
        list.add(userDao);

        return list;
    }


    /**
     * SELECT 查询操作，返回一个JSON数组
     * 具有幂等性
     */
    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public Object getUsers() {
        List<UserDao> list = new ArrayList<>();

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

        List<UserDao> list = getData();
        UserDao userDao = null;
        for (UserDao user : list) {
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
    public Object addUser(@RequestBody UserDao user) {

        List<UserDao> list = getData();
        list.add(user);//模拟向列表中增加数据
        return user;
    }

    /**
     * 编辑一个用户对象
     * 幂等性
     */
    @PutMapping("/users/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Object editUser(@PathVariable("id") String id, @RequestBody UserDao user) {
        List<UserDao> list = getData();
        for (UserDao userDao1 : list) {
            if (id.equals(userDao1.getUserId().toString())) {
                userDao1 = user;
                break;
            }
        }

        return user;
    }

    /**
     * 删除一个用户对象
     * 幂等性
     * 返回 HttpStatus.NO_CONTENT 表示无返回内容
     * */
    @DeleteMapping("/users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") String id){
        List<UserDao> list= getData();
        UserDao userDao=null;
        for (UserDao user:list) {
            if(id.equals(user.getUserId().toString())){
                //删除用户
                userDao=user;
                break;
            }
        }
    }
}
