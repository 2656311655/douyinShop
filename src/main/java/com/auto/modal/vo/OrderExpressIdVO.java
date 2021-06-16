package com.auto.modal.vo;

/**
 * @Classname OrderExpressId
 * @Description TODO
 * @Date 2020/10/23 17:17
 * @Created by Administrator
 */
public class OrderExpressIdVO {
    private String orderId;
    private String goodsName;
    private String goodsSku;
    private String goodsCode;
    private String goodsCount;
    private Float goodsSum;
    private String expressId;
    private String expressStatus;
    private String expressRemark;

    public String getExpressRemark() {
        return expressRemark;
    }

    public void setExpressRemark(String expressRemark) {
        this.expressRemark = expressRemark;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    public String getExpressId() {
        return expressId;
    }

    public String getGoodsSku() {
        return goodsSku;
    }

    public void setGoodsSku(String goodsSku) {
        this.goodsSku = goodsSku;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public String getExpressStatus() {
        return expressStatus;
    }

    public String getGoodsCount() {
        return goodsCount;
    }

    public Float getGoodsSum() {
        return goodsSum;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setExpressStatus(String expressStatus) {
        this.expressStatus = expressStatus;
    }

    public void setGoodsCount(String goodsCount) {
        this.goodsCount = goodsCount;
    }

    public void setGoodsSum(Float goodsSum) {
        this.goodsSum = goodsSum;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
