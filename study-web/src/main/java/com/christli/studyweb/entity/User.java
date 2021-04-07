package com.christli.studyweb.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class User {
    private Integer userId;
    @NotNull(message = "年龄不能为空")
    @Max(message = "年龄不能超过120岁", value = 120)
    @Min(message = "年龄不能小于1岁", value = 1)
    private Integer age;
    private String userName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User() {
    }

    public User(Integer userId, Integer age, String userName) {
        this.userId = userId;
        this.age = age;
        this.userName = userName;
    }
}
