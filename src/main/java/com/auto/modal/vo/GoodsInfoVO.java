package com.auto.modal.vo;

/**
 * @Classname GoodsInfo
 * @Description 商品信息。
 * @Date 2020/10/23 20:21
 * @Created by Administrator
 */
public class GoodsInfoVO {
    private String goodsName;
    private String goodsCode;
    private String goodsSku;
    private String goodsRemark;
    private String expressId;

    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public void setGoodsSku(String goodsSku) {
        this.goodsSku = goodsSku;
    }

    public String getGoodsSku() {
        return goodsSku;
    }

    public String getGoodsRemark() {
        return goodsRemark;
    }

    public void setGoodsRemark(String goodsRemark) {
        this.goodsRemark = goodsRemark;
    }
}
