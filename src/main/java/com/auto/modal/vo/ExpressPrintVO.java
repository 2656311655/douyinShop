package com.auto.modal.vo;

/**
 * @Classname ExpressPrintVO
 * @Description TODO
 * @Date 2020/12/22 15:31
 * @Created by Administrator
 */
public class ExpressPrintVO {
    private String expressId;
    private String expressType;
    private String goodsCode;
    private String goodsColor;
    private String goodsSize;
    private CustomerVO customer;

    public CustomerVO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerVO customer) {
        this.customer = customer;
    }

    public String getExpressType() {
        return expressType;
    }
    public void setExpressType(String expressType) {
        this.expressType = expressType;
    }

    public void setGoodsSize(String goodsSize) {
        this.goodsSize = goodsSize;
    }

    public String getGoodsSize() {
        return goodsSize;
    }

    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsColor(String goodsColor) {
        this.goodsColor = goodsColor;
    }

    public String getGoodsColor() {
        return goodsColor;
    }
}
