package com.newbie.community.dao;

import com.newbie.community.entity.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ResourceDao {

    Resource selectById(int id);

    List<Resource> selectAll(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);

    int selectCount(@Param("userId") int userId);

    int insert(Resource resource);

    int updateStatus(@Param("id") int id, @Param("status") int status);

}
