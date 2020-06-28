package com.newbie.community.entity;

import java.util.Date;

public class Blog {

    Integer id;

    Integer userId;

    String title;

    String content;

    Integer catgoryId;

    //状态：0-正常，1-删除
    Integer status;

    //创建时间
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        return "Blog{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", catgoryId=" + catgoryId +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}
