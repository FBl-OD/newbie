package com.newbie.community.service.impl;

import com.newbie.community.service.LikeService;
import com.newbie.community.util.CommunityConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImp implements LikeService , CommunityConst {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void like(int userId, int targetType, int targetId) {
        redisTemplate.opsForSet().add(REDIS_PREFIX_LIKE+REDIS_SPLIT+targetType+REDIS_SPLIT+targetId,userId);
    }

    @Override
    public void unlike(int userId, int targetType, int targetId) {
        redisTemplate.opsForSet().remove(REDIS_PREFIX_LIKE+REDIS_SPLIT+targetType+REDIS_SPLIT+targetId,userId);
    }

    @Override
    public boolean isLike(int userId, int targetType, int targetId) {
        return redisTemplate.opsForSet().isMember(REDIS_PREFIX_LIKE+REDIS_SPLIT+targetType+REDIS_SPLIT+targetId,userId);
    }

    @Override
    public long numOfLike(int targetType, int targetId) {
        return redisTemplate.opsForSet().size(REDIS_PREFIX_LIKE+REDIS_SPLIT+targetType+REDIS_SPLIT+targetId);
    }
}
