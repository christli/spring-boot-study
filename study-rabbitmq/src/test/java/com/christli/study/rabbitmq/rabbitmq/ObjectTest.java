package com.christli.study.rabbitmq.rabbitmq;

import com.christli.study.rabbitmq.model.User;
import com.christli.study.rabbitmq.object.ObjectSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ObjectTest {

    @Autowired
    private ObjectSender sender;

    @Test
    public void sendObject() throws Exception {
        User user=new User();
        user.setUserName("neo");
        user.setPassWord("123456");
        sender.send(user);
    }

}
