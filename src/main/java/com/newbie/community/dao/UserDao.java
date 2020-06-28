package com.newbie.community.dao;

import com.newbie.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserDao {

    User selectById(int id);

    User selectByName(String name);

    User selectByEmail(String email);

    int insert(User user);

    int updateStatus(@Param("id") int id, @Param("status") int status);
}
