package com.auto.modal.vo;

/**
 * @Classname MessageVO
 * @Description TODO
 * @Date 2021/3/12 19:31
 * @Created by Administrator
 */
public class MessageVO {
    private String phone;
    private String message;
    private String deviceId;
    private String token;

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
