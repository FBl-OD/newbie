package com.newbie.community.dao;

import com.newbie.community.CommunityApplication;
import com.newbie.community.entity.Blog;
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
public class BlogMapperTests {
    @Autowired
    private BlogDao blogDao;

    @Test
    public void testInsert(){
        for(int i=0;i<25;i++) {
            Blog blog = new Blog();
            blog.setCatgoryId(101);
            blog.setTitle("测试标题"+(i+1));
            blog.setContent("这是测试文章"+(i+1)+"的内容，随便写了点。。。");
            blog.setCreateTime(new Date());
            blog.setStatus(0);
            blog.setUserId(101);
            blogDao.insert(blog);
            System.out.println(blog.getId());
        }
    }

    @Test
    public void testSelectCount(){
        System.out.println(blogDao.selectCount(0));
    }

    @Test
    public void testSelectById(){
        System.out.println(blogDao.selectById(101));
    }

    @Test
    public void testUpdateById(){
        Blog blog = new Blog();
        blog.setHtml("<h1>你好</h1>");
        blog.setUserId(101);
        blog.setCreateTime(new Date());
        blog.setCatgoryId(106);
        blog.setContent("#你好");
        blog.setTitle("低级将");
        blog.setStatus(0);
        System.out.println(blogDao.updateById(171, blog));

    }
}
