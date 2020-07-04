package com.newbie.community.service;

import com.newbie.community.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserService {

    User queryById(int id);

    User queryByName(String name);

    User queryByEmail(String email);

    int add(User user);

    int updateStatus(@Param("id") int id, @Param("status") int status);

}
