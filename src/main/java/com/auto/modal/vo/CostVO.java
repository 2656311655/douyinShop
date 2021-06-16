package com.auto.modal.vo;

import java.util.Date;

/**
 * @Classname Cost
 * @Description TODO
 * @Date 2020/9/23 6:49
 * @Created by Administrator
 */
public class CostVO {
    private String productSku;
    private Date saleStartTime;
    private Date saleEndTime;

    public Date getSaleEndTime() {
        return saleEndTime;
    }

    public Date getSaleStartTime() {
        return saleStartTime;
    }

    public void setSaleEndTime(Date saleEndTime) {
        this.saleEndTime = saleEndTime;
    }

    public void setSaleStartTime(Date saleStartTime) {
        this.saleStartTime = saleStartTime;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }
}
