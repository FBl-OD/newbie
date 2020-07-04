package com.newbie.community.dao;

import com.newbie.community.entity.Catgory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CatgoryDao {

    Catgory selectById(int id);

    List<Catgory> selectByUserId(int userId);

    @Deprecated
    Catgory selectUserDefault(@Param("userId") int userId, @Param("name") String name);

    int insert(Catgory catgory);

    int updateStatus(@Param("id") int id, @Param("status") int status);
}
