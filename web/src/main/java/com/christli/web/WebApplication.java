package com.christli.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.christli.helloworld.MyService;

@SpringBootApplication(scanBasePackages="com.christli")
@RestController
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @Autowired
    MyService myService;
    @RequestMapping("/1")
    public String getTest() {
        return myService.message();
    }

}
