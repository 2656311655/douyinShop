package com.auto.modal.vo;

/**
 * @Classname ProductCostVO
 * @Description 产品成本结构
 * @Date 2020/10/4 22:45
 * @Created by Administrator
 */
public class ProductCostVO {
    private String goodsName;
    private Double goodsPrice;
    private Double goodsCost; //商品成本
    private Double goodsOtherCost;//商品其他成本
    private Double goodsFare; //邮费
    private Integer actualOrderSum;//实际订单数量
    private Integer unConfirmOrderSum;//未确认订单数量；
    private Integer dealOrderSum; //退货数量；

    public Double getGoodsFare() {
        return goodsFare;
    }

    public void setGoodsFare(Double goodsFare) {
        this.goodsFare = goodsFare;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public Double getGoodsOtherCost() {
        return goodsOtherCost;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }


    public Integer getActualOrderSum() {
        return actualOrderSum;
    }

    public Integer getDealOrderSum() {
        return dealOrderSum;
    }

    public Integer getUnConfirmOrderSum() {
        return unConfirmOrderSum;
    }

    public void setActualOrderSum(Integer actualOrderSum) {
        this.actualOrderSum = actualOrderSum;
    }

    public void setDealOrderSum(Integer dealOrderSum) {
        this.dealOrderSum = dealOrderSum;
    }

    public void setGoodsOtherCost(Double goodsOtherCost) {
        this.goodsOtherCost = goodsOtherCost;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }


    public void setUnConfirmOrderSum(Integer unConfirmOrderSum) {
        this.unConfirmOrderSum = unConfirmOrderSum;
    }

    public Double getGoodsCost() {
        return goodsCost;
    }

    public void setGoodsCost(Double goodsCost) {
        this.goodsCost = goodsCost;
    }
}
