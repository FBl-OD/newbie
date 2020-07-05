package com.newbie.community.service;

import com.newbie.community.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface UserService {

    User queryById(int id);

    User queryByName(String name);

    User queryByEmail(String email);

    int add(User user);

    int updateStatus(@Param("id") int id, @Param("status") int status);

    public Map<String, Object> sendConfirmCode(String email);

    public Map<String, Object> register(User user, String confirmCode);

    public Map<String, Object> login(String username, String password);

    public void logout(String username);

}
