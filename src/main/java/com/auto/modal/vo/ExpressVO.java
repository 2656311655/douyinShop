package com.auto.modal.vo;

/**
 * @Classname ExpressVO
 * @Description 快递包裹信息
 * @Date 2020/10/11 16:24
 * @Created by Administrator
 */
public class ExpressVO {
    private String shopName;
    private String afterSaleExpressId;
    private String goodsSku;
    private String goodsSize;
    private String afterSaleStatus;
    private String afterSaleId;
    private String orderId;
    private String expressType;
    private String senderInfo;
    private String recipientInfo;

    public String getGoodsSize() {
        return goodsSize;
    }

    public void setGoodsSize(String goodsSize) {
        this.goodsSize = goodsSize;
    }

    public String getRecipientInfo() {
        return recipientInfo;
    }

    public void setRecipientInfo(String recipientInfo) {
        this.recipientInfo = recipientInfo;
    }

    public String getSenderInfo() {
        return senderInfo;
    }

    public void setSenderInfo(String senderInfo) {
        this.senderInfo = senderInfo;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getExpressType() {
        return expressType;
    }

    public void setExpressType(String expressType) {
        this.expressType = expressType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setAfterSaleStatus(String afterSaleStatus) {
        this.afterSaleStatus = afterSaleStatus;
    }

    public String getAfterSaleStatus() {
        return afterSaleStatus;
    }

    public void setAfterSaleExpressId(String afterSaleExpressId) {
        this.afterSaleExpressId = afterSaleExpressId;
    }

    public String getAfterSaleExpressId() {
        return afterSaleExpressId;
    }



    public void setAfterSaleId(String afterSaleId) {
        this.afterSaleId = afterSaleId;
    }

    public String getAfterSaleId() {
        return afterSaleId;
    }


    public void setGoodsSku(String goodsSku) {
        this.goodsSku = goodsSku;
    }

    public String getGoodsSku() {
        return goodsSku;
    }


}
