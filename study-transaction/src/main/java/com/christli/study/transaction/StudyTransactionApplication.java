package com.christli.study.transaction;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.christli.study.transaction.user.dao")
public class StudyTransactionApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyTransactionApplication.class, args);
    }

}
