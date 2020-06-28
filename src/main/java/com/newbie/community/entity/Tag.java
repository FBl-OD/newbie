package com.newbie.community.entity;

import java.util.Date;

public class Tag {

    Integer id;

    String name;

    Integer catgoryId;

    //状态：0-正常，1-删除
    Integer status;

    Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public Integer getCatgoryId() {
        return catgoryId;
    }

    public void setCatgoryId(Integer catgoryId) {
        this.catgoryId = catgoryId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", catgoryId=" + catgoryId +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
