package com.christli.study.jpa.repository;

import com.christli.study.jpa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * 根据性别名字模糊查询
     */
    List<User> findByUserNameLikeAndSex(String name, int sex);
}
