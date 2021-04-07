package com.christli.studymybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.christli.studymybatis.mapper")
public class StudyMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyMybatisApplication.class, args);
    }

}
