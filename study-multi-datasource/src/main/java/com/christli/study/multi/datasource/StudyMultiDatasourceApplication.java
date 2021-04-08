package com.christli.study.multi.datasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.christli.study.multi.datasource.user.dao")
public class StudyMultiDatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyMultiDatasourceApplication.class, args);
    }

}
