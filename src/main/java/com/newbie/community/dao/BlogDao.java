package com.newbie.community.dao;

import com.newbie.community.entity.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BlogDao {

    Blog selectById(int id);

    List<Blog> selectAll(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);

    int insert(Blog blog);

    int updateStatus(@Param("id") int id,@Param("status") int status);

}
