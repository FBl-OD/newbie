package com.newbie.community.dao;

import com.newbie.community.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TagDao {

    Tag selectById(int id);

    List<Tag> selectByCatgoryId(int catgoryId);

    int insert(Tag tag);

    int updateStatus(@Param("id") int id, @Param("status") int status);
}
