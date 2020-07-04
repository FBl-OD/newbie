package com.newbie.community.service.impl;

import com.newbie.community.service.ViewService;
import com.newbie.community.util.CommunityConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ViewServiceImpl implements ViewService, CommunityConst {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public long views(int blogId) {
        return redisTemplate.opsForValue().size(REDIS_PREFIX_VIEW+REDIS_SPLIT+blogId);
    }

    @Override
    public void init(int blogId) {
        redisTemplate.opsForValue().set(REDIS_PREFIX_VIEW+REDIS_SPLIT+blogId,0);
    }

    @Override
    public void increment(int blogId) {
        redisTemplate.opsForValue().increment(REDIS_PREFIX_VIEW+REDIS_SPLIT+blogId);

    }
}
