package com.newbie.community.dao;

import com.newbie.community.CommunityApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class ResourceMapperTests {

    @Autowired
    private ResourceDao resourceDao;

    @Test
    public void testSelectCount(){
        System.out.println(resourceDao.selectCount(0));
    }
}
