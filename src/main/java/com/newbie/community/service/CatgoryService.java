package com.newbie.community.service;

import com.newbie.community.entity.Catgory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CatgoryService {

    Catgory queryById(int id);

    List<Catgory> queryByUserId(int userId);

    Catgory queryUserDefault(int userId);

    int add(Catgory catgory);

    int updateStatus(@Param("id") int id, @Param("status") int status);

    default List<Catgory> queryAdminCatgories(){
        return queryByUserId(1);
    }

}
