package com.auto.modal.vo;

/**
 * @Classname ReplyVO
 * @Description 飞鸽对话格式
 * @Date 2020/11/18 6:11
 * @Created by Administrator
 */
public class ReplyVO {
    private String userName;
    private String content;
    private String orderTime;
    private String phone;
    private String reply;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
