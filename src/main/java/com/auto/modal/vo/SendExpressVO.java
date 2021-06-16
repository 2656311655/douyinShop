package com.auto.modal.vo;

/**
 * @Classname AfterSaleExpressVO
 * @Description  发货物流单号
 * @Date 2021/5/31 17:57
 * @Created by Administrator
 */
public class SendExpressVO {
     private  String expressId;
     private String expressContent;
     private String expressScreenshotFilePath;
     private String expressType;
     private  String afterSaleId;
     private String orderId;
     private String afterSaleReason;
     private String afterSaleRemarks;

    public void setExpressType(String expressType) {
        this.expressType = expressType;
    }

    public String getExpressType() {
        return expressType;
    }

    public void setAfterSaleRemarks(String afterSaleRemarks) {
        this.afterSaleRemarks = afterSaleRemarks;
    }

    public String getAfterSaleRemarks() {
        return afterSaleRemarks;
    }

    public String getAfterSaleReason() {
        return afterSaleReason;
    }

    public void setAfterSaleReason(String afterSaleReason) {
        this.afterSaleReason = afterSaleReason;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    public String getAfterSaleId() {
        return afterSaleId;
    }

    public void setAfterSaleId(String afterSaleId) {
        this.afterSaleId = afterSaleId;
    }

    public String getExpressContent() {
        return expressContent;
    }

    public String getExpressScreenshotFilePath() {
        return expressScreenshotFilePath;
    }

    public void setExpressContent(String expressContent) {
        this.expressContent = expressContent;
    }

    public void setExpressScreenshotFilePath(String expressScreenshotFilePath) {
        this.expressScreenshotFilePath = expressScreenshotFilePath;
    }
}
