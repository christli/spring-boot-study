package com.christli.study.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class StudyAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyAdminApplication.class, args);
    }

}
