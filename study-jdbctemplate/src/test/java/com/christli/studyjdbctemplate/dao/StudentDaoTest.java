package com.christli.studyjdbctemplate.dao;

import com.christli.studyjdbctemplate.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentDaoTest {

    @Autowired
    private StudentDao studentDao;

    @Test
    @Transactional
    public void addStudent() {
        studentDao.addStudent();
    }

    @Test
    public void getStudent() {
        Student student = studentDao.getStudent(0);
        System.out.println(student.toString());
    }

    @Test
    public void getStudentList() {
        List<Student> list = studentDao.getStudentList(0);
        System.out.println(list.toString());
    }

    @Test
    @Transactional
    public void deleteStudent() {
        studentDao.deleteStudent(29);
    }

    @Test
    @Transactional
    public void updateStudent() {
        studentDao.updateStudent(35,"christli35");
    }

    @Test
    @Transactional
    public void batchAddStudent() {
        studentDao.batchAddStudent();
    }
}