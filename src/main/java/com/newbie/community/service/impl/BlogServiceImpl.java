package com.newbie.community.service.impl;

import com.newbie.community.dao.BlogDao;
import com.newbie.community.entity.Blog;
import com.newbie.community.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;

    @Override
    public Blog queryById(int id) {
        return blogDao.selectById(id);
    }

    @Override
    public List<Blog> queryAll(int userId, int offset, int limit) {
        return blogDao.selectAll(userId, offset, limit);
    }

    @Override
    public List<Blog> queryByTagId(int tagId, int offset, int limit) {
        return null;
    }

    @Override
    public List<Blog> queryByCatgoryId(int catgoryId, int offset, int limit) {
        return null;
    }

    @Override
    public int add(Blog blog) {
        return blogDao.insert(blog);
    }

    @Override
    public int updateStatus(int id, int status) {
        return blogDao.updateStatus(id, status);
    }
}
