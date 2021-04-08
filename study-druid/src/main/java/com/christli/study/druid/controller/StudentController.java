package com.christli.study.druid.controller;

import com.christli.study.druid.entity.Student;
import com.christli.study.druid.mapper.StudentMapper;
import com.christli.study.druid.util.JsonResult;
import com.christli.study.druid.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentMapper studentMapper;

    @GetMapping(value = "/students/{id}")
    public JsonResult findById(@PathVariable("id") Integer id) {
        Student student = studentMapper.findById(id);
        return new JsonResult(ResultCode.SUCCESS, student);
    }

    @PutMapping(value = "/students/{id}")
    public JsonResult saveStudent(@PathVariable("id") Integer id,@RequestBody Student student) {
        int result = studentMapper.updateStudent(id, student.getName());
        return new JsonResult(ResultCode.SUCCESS, result);
    }
}
