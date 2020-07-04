package com.newbie.community.service.impl;

import com.newbie.community.dao.ResourceDao;
import com.newbie.community.entity.Resource;
import com.newbie.community.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl implements FileService {

    @Autowired
    private ResourceDao resourceDao;

    @Override
    public Resource queryById(int id) {
        return resourceDao.selectById(id);
    }

    @Override
    public List<Resource> queryAll(int userId, int offset, int limit) {
        return resourceDao.selectAll(userId,offset,limit);
    }

    @Override
    public int queryCount(int userId) {
        return resourceDao.selectCount(userId);
    }

    @Override
    public int add(Resource resource) {
        return resourceDao.insert(resource);
    }

    @Override
    public int updateStatus(int id, int status) {
        return resourceDao.updateStatus(id,status);
    }
}
