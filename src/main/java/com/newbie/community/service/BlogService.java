package com.newbie.community.service;

import com.newbie.community.entity.Blog;

import java.util.List;

public interface BlogService {

    Blog queryById(int id);

    List<Blog> queryAll(int userId, int offset, int limit);

    List<Blog> queryByTagId(int tagId, int offset, int limit);

    List<Blog> queryByCatgoryId(int catgoryId, int offset, int limit);

    int queryCount(int userId);

    int queryCountOfSpecifiedCategory(int categoryId);

    int add(Blog blog);

    int updateStatus(int id, int status);

    int updateBlogById(int id, Blog blog);


}
