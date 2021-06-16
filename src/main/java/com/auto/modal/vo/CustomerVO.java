package com.auto.modal.vo;

/**
 * @Classname CustomerVO
 * @Description TODO
 * @Date 2020/12/2 11:25
 * @Created by Administrator
 */
public class CustomerVO {
    private String orderId;
    private String nickName;
    private String realName;
    private String userPhone;
    private String address;

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
