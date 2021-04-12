package com.christli.study.ehcache.entity;

public class UserDO {
    private Integer userId;
    private Integer age;
    private String userName;
    private String passWord;

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

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }


    public UserDO(Integer userId, String userName, String passWord, Integer age) {
        this.userId = userId;
        this.userName = userName;
        this.passWord = passWord;
        this.age = age;
    }
}
