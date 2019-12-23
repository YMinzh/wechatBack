package com.example.wechat.entity;

import java.util.Date;

public class Friend {
    private long id;
    private long user_id;
    private long friend_id;
    private Date created_at;
    private Date updated_at;

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", friend_id=" + friend_id +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(long friend_id) {
        this.friend_id = friend_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
