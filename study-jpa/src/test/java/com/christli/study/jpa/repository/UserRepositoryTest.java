package com.christli.study.jpa.repository;

import com.christli.study.jpa.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void before() {
        User user = new User();
        user.setId(9000);
        user.setUserName("christli-01");
        user.setSex(1);
        user.setLastLoginTime(new Date());
        user.setPassword("passWord");
        userRepository.save(user);
        System.out.println("add user " + user.getUserName());
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteById(9000);
        System.out.println("delete user id:" + 9000);
    }

    @Test
    public void testFind() {
        Optional<User> optionalUser = userRepository.findById(9000);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            System.out.println("testFind user " + user.getUserName());
        }
    }

    @Test
    public void testFindAll() {
        List<User> list = userRepository.findAll();
        for (User user : list) {
            System.out.println("testFindAll user_name:" + user.getUserName());
        }
    }

    @Test
    public void testFindByUserNameLikeAndSex() {
        List<User> list = userRepository.findByUserNameLikeAndSex("christ%", 1);
        for (User user : list) {
            System.out.println("testFindByUserNameLikeAndSex user_name:" + user.getUserName());
        }
    }

    @Test
    public void testUpdate() {
        Optional<User> optionalUser = userRepository.findById(9000);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUserName("christli-001");
            userRepository.save(user);
            System.out.println("testUpdate user" + user.getUserName());
        }

    }

}