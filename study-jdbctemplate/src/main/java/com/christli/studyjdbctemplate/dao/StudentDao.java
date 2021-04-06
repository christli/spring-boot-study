package com.christli.studyjdbctemplate.dao;

import com.christli.studyjdbctemplate.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class StudentDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int addStudent(){
        String sql = "insert into student(age,name,status) values(?,?,?)";
        int $result = jdbcTemplate.update(sql,new Object[]{10,"christli",0});
        return $result;
    }

    public Student getStudent(int status){
        String sql = "select * from student where status = ? limit 1";
        return jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<>(Student.class),new Object[]{status});
    }

    public List<Student> getStudentList(int status){
        String sql = "select * from student where status = ?";
        return  jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Student.class),new Object[]{status});
    }

    public int deleteStudent(int id){
        String sql = "delete from student where student_id = ?";
        return jdbcTemplate.update(sql,new Object[]{id});
    }

    public int updateStudent(int studentId,String name){
        String sql = "update student set name = ? where student_id = ?";
        return jdbcTemplate.update(sql,new Object[]{name,studentId});
    }

    public int batchAddStudent(){

        // 构造list集合
        List<Student> studentList = new ArrayList<>();
        Student student = new Student(21, 31, "christli", 1, new Date(), 1);
        Student student1 = new Student(22, 32, "christli2", 1, new Date(), 1);
        studentList.add(student);
        studentList.add(student1);
        String sql = "insert into student values(?,?,?,?,?,?)";

        int[] ints = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Student student = studentList.get(i);
                ps.setInt(1, student.getStudentId());
                ps.setInt(2, student.getAge());
                ps.setString(3, student.getName());
                ps.setInt(4, student.getSex());
                ps.setDate(5,new java.sql.Date(System.currentTimeMillis()));
                ps.setInt(6, student.getStatus());
            }

            @Override
            public int getBatchSize() {
                return studentList.size();
            }
        });
        return ints.length;
    }


}
