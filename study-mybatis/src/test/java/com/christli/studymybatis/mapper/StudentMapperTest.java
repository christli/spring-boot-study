package com.christli.studymybatis.mapper;

import com.christli.studymybatis.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentMapperTest {

    @Autowired
    private StudentMapper mapper;


    @Test
    void findById() {
        Student student = mapper.findById(61);
        System.out.println(student);
    }

    @Test
    @Transactional
    void addStudent() {
        int result = mapper.addStudent("christli", 12);
        System.out.println(result);
    }

    @Test
    @Transactional
    void updateStudent() {
        int result = mapper.updateStudent(30, "christ");
        System.out.println(result);
    }

    @Test
    @Transactional
    void deleteStudent() {
        int result = mapper.deleteStudent(30);
        System.out.println(result);
    }
}