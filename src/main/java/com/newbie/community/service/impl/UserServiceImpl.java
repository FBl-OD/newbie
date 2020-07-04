package com.newbie.community.service.impl;

import com.newbie.community.dao.UserDao;
import com.newbie.community.entity.User;
import com.newbie.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;


    @Override
    public User queryById(int id) {
        return userDao.selectById(id);
    }

    @Override
    public User queryByName(String name) {
        return userDao.selectByName(name);
    }

    @Override
    public User queryByEmail(String email) {
        return userDao.selectByEmail(email);
    }

    @Override
    public int add(User user) {
        return userDao.insert(user);
    }

    @Override
    public int updateStatus(int id, int status) {
        return userDao.updateStatus(id,status);
    }
}
