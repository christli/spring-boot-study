package com.christli.study.ehcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StudyEhcacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyEhcacheApplication.class, args);
    }

}
