package com.newbie.community.dao;

import com.newbie.community.CommunityApplication;
import com.newbie.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class UserMapperTests {
    @Autowired
    UserDao userDao;

    @Test
    public void testInsert(){
        User user = new User();
        user.setName("test");
        user.setEmail("test@test.com");
        user.setCreateTime(new Date());
        user.setStatus(0);
        user.setType(0);
        user.setHeaderUrl("https://avatars2.githubusercontent.com/u/56433667?s=400&v=4");
        System.out.println(userDao.insert(user));
        System.out.println(user.getId());
    }

    @Test
    public void testSelectById(){
        System.out.println(userDao.selectById(101));
    }
}
