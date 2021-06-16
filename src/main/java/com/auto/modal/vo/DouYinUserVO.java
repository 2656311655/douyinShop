package com.auto.modal.vo;

/**
 * @Classname DouYinUserVO
 * @Description TODO
 * @Date 2021/4/18 17:00
 * @Created by Administrator
 */
public class DouYinUserVO {
    private String author_id;
    private String msg_id;
    private String user_id;
    private String avatar;
    private String content;
    private String polling_create_time;
    private String nickname;
    private String barrage_type;
    private String return_customer;
    private String format_time;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getBarrage_type() {
        return barrage_type;
    }

    public String getFormat_time() {
        return format_time;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPolling_create_time() {
        return polling_create_time;
    }

    public String getReturn_customer() {
        return return_customer;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setBarrage_type(String barrage_type) {
        this.barrage_type = barrage_type;
    }

    public void setFormat_time(String format_time) {
        this.format_time = format_time;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPolling_create_time(String polling_create_time) {
        this.polling_create_time = polling_create_time;
    }

    public void setReturn_customer(String return_customer) {
        this.return_customer = return_customer;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

}
