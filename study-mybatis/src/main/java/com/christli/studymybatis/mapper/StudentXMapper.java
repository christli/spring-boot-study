package com.christli.studymybatis.mapper;

import com.christli.studymybatis.entity.Student;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StudentXMapper {

    Student findById(@Param("studentId") Integer studentId);

    int addStudent(Student student);

    int updateStudent(@Param("studentId") Integer studentId, @Param("name") String name);

    int deleteStudent(@Param("studentId") Integer studentId);
}
