package com.nowcoder.model;

import java.util.Date;

/**
 * @Author: wangyang
 * @Description:
 * @Date: Cread in 15:03 2017/9/15
 * @Modified By:
 */
public class LoginTicket {
    private int id;
    private Date expired;
    private String ticket;
    private int status;//0有效，1无效
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
