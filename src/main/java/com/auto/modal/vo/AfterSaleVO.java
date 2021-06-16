package com.auto.modal.vo;

/**
 * @Classname AfterSaleVO
 * @Description TODO
 * @Date 2020/10/29 9:50
 * @Created by Administrator
 */
public class AfterSaleVO {
    public String orderId;
    public String afterSaleType;
    public String afterSaleId;
    public String afterSaleExpressId;
    public String afterSaleStatus;

    public String getAfterSaleStatus() {
        return afterSaleStatus;
    }

    public void setAfterSaleStatus(String afterSaleStatus) {
        this.afterSaleStatus = afterSaleStatus;
    }

    public String getAfterSaleExpressId() {
        return afterSaleExpressId;
    }

    public void setAfterSaleExpressId(String afterSaleExpressId) {
        this.afterSaleExpressId = afterSaleExpressId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAfterSaleId() {
        return afterSaleId;
    }

    public void setAfterSaleId(String afterSaleId) {
        this.afterSaleId = afterSaleId;
    }

    public String getAfterSaleType() {
        return afterSaleType;
    }

    public void setAfterSaleType(String afterSaleType) {
        this.afterSaleType = afterSaleType;
    }
}
