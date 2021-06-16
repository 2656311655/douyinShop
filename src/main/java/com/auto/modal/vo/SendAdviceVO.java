package com.auto.modal.vo;

/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020-08-26
 */
public class SendAdviceVO {
    private String goodsCode;
    private String goodsName;
    private String goodsColor;
    private String goodsSize;
    private Integer goodsNumber;

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public String getGoodsColor() {
        return goodsColor;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public String getGoodsSize() {
        return goodsSize;
    }

    public void setGoodsColor(String goodsColor) {
        this.goodsColor = goodsColor;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public void setGoodsSize(String goodsSize) {
        this.goodsSize = goodsSize;
    }
}
