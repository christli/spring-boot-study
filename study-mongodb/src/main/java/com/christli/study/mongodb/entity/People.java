package com.christli.study.mongodb.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class People implements Serializable {
    private static final long serialVersionUID = -3258839839160856613L;
    private Long id;
    private String peopleName;
    private String passWord;
    private Integer sex;
}
