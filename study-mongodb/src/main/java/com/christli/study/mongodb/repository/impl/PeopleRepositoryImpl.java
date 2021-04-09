package com.christli.study.mongodb.repository.impl;

import com.christli.study.mongodb.entity.People;
import com.christli.study.mongodb.repository.PeopleRepository;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class PeopleRepositoryImpl implements PeopleRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 创建对象
     * @param people
     */
    @Override
    public void savePeople(People people) {
        mongoTemplate.save(people);
    }

    /**
     * 根据用户名查询对象
     * @param peopleName
     * @return
     */
    @Override
    public People findPeopleByPeopleName(String peopleName) {
        Query query=new Query(Criteria.where("peopleName").is(peopleName));
        People people =  mongoTemplate.findOne(query , People.class);
        return people;
    }

    /**
     * 更新对象
     * @param people
     */
    @Override
    public long updatePeople(People people) {
        Query query=new Query(Criteria.where("id").is(people.getId()));
        Update update= new Update().set("peopleName", people.getPeopleName()).set("passWord", people.getPassWord());
        //更新查询返回结果集的第一条
        UpdateResult result =mongoTemplate.updateFirst(query,update,People.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,PeopleEntity.class);
        if(result!=null)
            return result.getMatchedCount();
        else
            return 0;
    }

    /**
     * 删除对象
     * @param id
     */
    @Override
    public void deletePeopleById(Long id) {
        Query query=new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query,People.class);
    }
}
