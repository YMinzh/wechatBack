package com.example.wechat.entity;

import java.util.Date;

public class Measure {
    private long id;
    private long send_id;
    private long target_id;
    private String content;
    private int status;
    private Date created_at;
    private Date updated_at;

    @Override
    public String toString() {
        return "Measure{" +
                "id=" + id +
                ", send_id=" + send_id +
                ", target_id=" + target_id +
                ", content='" + content + '\'' +
                ", status=" + status +
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

    public long getSend_id() {
        return send_id;
    }

    public void setSend_id(long send_id) {
        this.send_id = send_id;
    }

    public long getTarget_id() {
        return target_id;
    }

    public void setTarget_id(long target_id) {
        this.target_id = target_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
