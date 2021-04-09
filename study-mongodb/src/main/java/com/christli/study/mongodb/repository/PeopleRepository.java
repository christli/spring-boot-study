package com.christli.study.mongodb.repository;

import com.christli.study.mongodb.entity.People;

public interface PeopleRepository {
    public void savePeople(People people);

    public People findPeopleByPeopleName(String peopleName);

    public long updatePeople(People people);

    public void deletePeopleById(Long id);
}
