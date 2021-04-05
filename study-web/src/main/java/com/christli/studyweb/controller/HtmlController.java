package com.christli.studyweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HtmlController {

    /**
     * 正常的页面 对应 /templates/index.html 页面
     * */
    @RequestMapping("/index")
    public String index(Model model){
        model.addAttribute("msg","这是一个index页面的正常消息");
        return "index";
    }

    /**
     * 抛出一个 RuntimeException 异常
     * */
    @RequestMapping("/index/err")
    public String err(){
        throw new RuntimeException("抛出一个 RuntimeException 异常");
    }

    /**
     * 抛出一个 RuntimeException 异常
     * */
    @RequestMapping("/index/mather")
    public String mather(Model model){

        int j=0;
        int i=0;
        i=100/j;

        return "index";
    }
}
