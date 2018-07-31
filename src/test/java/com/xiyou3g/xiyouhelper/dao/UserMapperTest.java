package com.xiyou3g.xiyouhelper.dao;

import com.xiyou3g.xiyouhelper.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * mengchen
 * 18-7-18 下午9:30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

   /* @Autowired
    private UserMapper userMapper;

    @Test
    public void whenInsertUser() {
        User user = new User();
        user.setUserName("pipiap");
        user.setPassword("123456");
        userMapper.insert(user);
    }*/
//
//    @Test
//    public void whenTestBranch() {
//        System.out.println("dev branch ok!!");
//    }

    @Test
    public void testUpdatePassword() {
//        userMapper.updatePassword("04161031", "1293141942qwer", "aaaaaa");
    }

}
