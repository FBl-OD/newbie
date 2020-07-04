package com.newbie.community.service;

public interface ViewService {

    void increment(int blogId);

    void init(int blogId);

    long views(int blogId);

}
