package com.christli.study.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudyLogbackApplication {

    protected static Logger logger = LoggerFactory.getLogger(StudyLogbackApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(StudyLogbackApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello() {
        logger.info("hello ," + "中文!!");
        return "Hello from study-logback";
    }


}
