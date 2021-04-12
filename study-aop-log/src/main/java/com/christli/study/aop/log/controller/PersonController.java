package com.christli.study.aop.log.controller;

import com.christli.study.aop.log.annotation.Log;
import com.christli.study.aop.log.domain.Person;
import com.christli.study.aop.log.enums.BusinessType;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("person")
public class PersonController {
    @GetMapping("/{name}")
    @Log(title = "person", businessType = BusinessType.OTHER)
    public Person getPerson(@PathVariable("name") String name, @RequestParam int age) {
        return new Person(name, age);
    }

    @PostMapping("add")
    @Log(title = "person", businessType = BusinessType.INSERT)
    public int addPerson(@RequestBody Person person) {
        if (ObjectUtils.isEmpty(person)) {
            return -1;
        }
        return 1;
    }

    @PutMapping("update")
    @Log(title = "person", businessType = BusinessType.UPDATE)
    public int updatePerson(@RequestBody Person person) {
        if (ObjectUtils.isEmpty(person)) {
            return -1;
        }
        return 1;
    }

    @DeleteMapping("/{name}")
    @Log(title = "person", businessType = BusinessType.DELETE)
    public int deletePerson(@PathVariable(name = "name") String name) {
        if (!StringUtils.hasLength(name)) {
            return -1;
        }
        throw new RuntimeException("抛出一个异常");
//        return 1;
    }
}

