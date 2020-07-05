package com.newbie.community.service;


import com.newbie.community.CommunityApplication;
import com.newbie.community.util.CommunityConst;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class LikeServiceTests implements CommunityConst {
    @Autowired
    private LikeService likeService;

    @Test
    public void testLike(){
        for(int i=0;i<10;i++) {
            likeService.like(i, BLOG_TYPE, 174);
        }
    }
}
