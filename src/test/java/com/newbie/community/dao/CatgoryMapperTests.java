package com.newbie.community.dao;

import com.newbie.community.CommunityApplication;
import com.newbie.community.entity.Catgory;
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
public class CatgoryMapperTests {
    @Autowired
    CatgoryDao catgoryDao;
    @Test
    public void testInsert(){
        Catgory catgory = new Catgory();
        catgory.setUserId(1);
        catgory.setName("人工智能");
        catgory.setStatus(0);
        catgory.setCreateTime(new Date());
        catgoryDao.insert(catgory);
        System.out.println(catgory.getId());
    }
    @Test
    public void testSelectUserDefault(){
        System.out.println(catgoryDao.selectUserDefault(101,"default"));
    }
}