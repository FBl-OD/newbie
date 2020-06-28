package com.newbie.community.entity;

import java.util.Date;

public class Catgory {

    Integer id;

    Integer userId;

    String name;

    //状态：0-正常，1-删除
    Integer status;

    Date creatTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }

    @Override
    public String toString() {
        return "Catgory{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", creatTime=" + creatTime +
                '}';
    }
}
