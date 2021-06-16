package com.auto.modal.vo;

/**
 * @Classname ProductQuality
 * @Description 产品满意度
 * @Date 2020/9/23 21:34
 * @Created by Administrator
 */
public class ProductQuality {
    private String goodsSku;
    private int saleSusCount;
    private int refundCount;
    private double rate;
    private double averageProfit;

    public double getAverageProfit() {
        return averageProfit;
    }

    public void setAverageProfit(double averageProfit) {
        this.averageProfit = averageProfit;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getRefundCount() {
        return refundCount;
    }

    public void setRefundCount(int refundCount) {
        this.refundCount = refundCount;
    }

    public String getGoodsSku() {
        return goodsSku;
    }

    public void setGoodsSku(String goodsSku) {
        this.goodsSku = goodsSku;
    }

    public int getSaleSusCount() {
        return saleSusCount;
    }

    public void setSaleSusCount(int saleSusCount) {
        this.saleSusCount = saleSusCount;
    }
}
