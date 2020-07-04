package com.newbie.community.entity;

import java.util.Date;

public class Resource {

    Integer id;

    Integer userId;

    String title;

    Integer status;

    Date createTime;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        return "File{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
