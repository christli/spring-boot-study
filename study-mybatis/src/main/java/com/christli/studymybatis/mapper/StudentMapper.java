package com.christli.studymybatis.mapper;

import com.christli.studymybatis.entity.Student;
import org.apache.ibatis.annotations.*;

@Mapper
public interface StudentMapper {

    @Select("select * from student where student_id = #{studentId}")
    Student findById(@Param("studentId") Integer studentId);

    @Insert("insert into student(age,name) values(#{age},#{name})")
    int addStudent(@Param("name") String name,@Param("age") Integer age);

    @Update("update student set name = #{name} where student_id = #{studentId}")
    int updateStudent(@Param("studentId") Integer studentId,@Param("name") String name);

    @Delete("delete from student where student_id = #{studentId}")
    int deleteStudent(@Param("studentId") Integer studentId);
}

