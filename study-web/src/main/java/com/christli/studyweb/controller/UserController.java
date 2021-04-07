package com.christli.studyweb.controller;

import com.christli.studyweb.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @RequestMapping("/users")
    public ModelAndView getUserList(Model model) {
        List<User> userList = new ArrayList<User>();
        User user = new User(1,2,"zhangsan");
        userList.add(user);
        user = new User(2,8,"lisi");
        userList.add(user);
        user = new User(3,8,"zhaowu");
        userList.add(user);
        user = new User(4,8,"liuliu");
        userList.add(user);
        ModelAndView modelAndView = new ModelAndView("/userList");
        modelAndView.addObject("userList", userList);
        return modelAndView;
    }
}
