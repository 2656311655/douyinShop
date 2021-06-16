package com.auto.modal.vo;

/**
 * @Classname GoodsSaleVO
 * @Description 单类商品销售类
 * @Date 2021/3/11 15:41
 * @Created by Administrator
 */
public class GoodsSaleVO {
    private String goodsSaleTime;
    private Integer goodsSaleCount;
    private Double goodsAverageSale;
    private String  increaseRate;
    private String goodsType;

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public void setGoodsSaleTime(String goodsSaleTime) {
        this.goodsSaleTime = goodsSaleTime;
    }

    public String getGoodsSaleTime() {
        return goodsSaleTime;
    }

    public Double getGoodsAverageSale() {
        return goodsAverageSale;
    }

    public Integer getGoodsSaleCount() {
        return goodsSaleCount;
    }

    public String getIncreaseRate() {
        return increaseRate;
    }

    public void setGoodsAverageSale(Double goodsAverageSale) {
        this.goodsAverageSale = goodsAverageSale;
    }

    public void setGoodsSaleCount(Integer goodsSaleCount) {
        this.goodsSaleCount = goodsSaleCount;
    }

    public void setIncreaseRate(String increaseRate) {
        this.increaseRate = increaseRate;
    }
}
