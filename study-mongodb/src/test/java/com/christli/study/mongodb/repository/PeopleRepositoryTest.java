package com.christli.study.mongodb.repository;

import com.christli.study.mongodb.entity.People;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PeopleRepositoryTest {

    @Autowired
    private PeopleRepository repository;

    @Test
    public void testSavePeople() throws Exception {
        People people = new People();
        people.setId(4l);
        people.setPeopleName("小明");
        people.setPassWord("fffooo123");
        people.setSex(1);
        repository.savePeople(people);
        System.out.println("save people " + people);
    }

    @Test
    public void findPeopleByPeopleName() {
        People people = repository.findPeopleByPeopleName("小明");
        System.out.println("people is " + people);
    }

    @Test
    public void updatePeople() {
        People people = new People();
        people.setId(2l);
        people.setPeopleName("天空");
        people.setPassWord("fffxxxx");
        repository.updatePeople(people);
        System.out.println("update people " + people);
    }

    @Test
    public void deletePeopleById() {
        long id = 3l;
        repository.deletePeopleById(id);
        System.out.println("delete people " + id);
    }
}