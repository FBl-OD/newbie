package com.newbie.community.service.impl;

import com.newbie.community.dao.CatgoryDao;
import com.newbie.community.entity.Catgory;
import com.newbie.community.service.CatgoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatgoryServiceImpl implements CatgoryService {

    @Autowired
    private CatgoryDao catgoryDao;

    @Override
    public Catgory queryById(int id) {
        return catgoryDao.selectById(id);
    }

    @Override
    public List<Catgory> queryByUserId(int userId) {
        return catgoryDao.selectByUserId(userId);
    }

    @Deprecated
    @Override
    public Catgory queryUserDefault(int userId) {
        return catgoryDao.selectUserDefault(userId,"default");
    }

    @Override
    public int add(Catgory catgory) {
        return catgoryDao.insert(catgory);
    }

    @Override
    public int updateStatus(int id, int status) {
        return catgoryDao.updateStatus(id,status);
    }

}
