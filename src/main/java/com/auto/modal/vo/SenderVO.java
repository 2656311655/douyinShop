package com.auto.modal.vo;

/**
 * @Classname SenderVO
 * @Description TODO
 * @Date 2021/1/31 16:54
 * @Created by Administrator
 */
public class SenderVO {
    private String expressId;
    private String expressPrintTime;
    private String orderId;
    private String goodsCode;
    private String goodsNumber;
    private String recipient;
    private String recipientPhone;
    private String recipientAddress;
    private String fare;
    private String goodsCost;
    private String totalCost;

    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    public String getExpressId() {
        return expressId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setGoodsCost(String goodsCost) {
        this.goodsCost = goodsCost;
    }

    public String getExpressPrintTime() {
        return expressPrintTime;
    }

    public String getFare() {
        return fare;
    }

    public String getGoodsCost() {
        return goodsCost;
    }

    public String getGoodsNumber() {
        return goodsNumber;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setExpressPrintTime(String expressPrintTime) {
        this.expressPrintTime = expressPrintTime;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }
}
