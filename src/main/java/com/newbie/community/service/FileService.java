package com.newbie.community.service;

import com.newbie.community.entity.Resource;

import java.util.List;

public interface FileService {

    Resource queryById(int id);

    List<Resource> queryAll(int userId, int offset, int limit);

    int queryCount(int userId);

    int add(Resource resource);

    int updateStatus(int id, int status);

}
