package com.newbie.community.service;

public interface LikeService {

    void like(int userId,int targetType,int targetId);

    void unlike(int userId,int targetType,int targetId);

    boolean isLike(int userId,int targetType,int targetId);

    long numOfLike(int targetType,int targetId);

}
