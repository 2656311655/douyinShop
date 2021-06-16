package com.auto.dao;

import java.util.Date;

/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2021-01-03
 */
public class OrderDO {
    private Double actualCheque;
    private Long senderId = 1L;
    private Long orderId;
   private Long recipentId=1L;
   private String orderStatus;
    private String customerRemarks;
    private String shopName;
    private String goodsCode;
   private String platformName="抖音";
   private Long mainOrderId;
   private String businessRemarks;
   private String expressId="";
   private Date orderSubmitTime;
   private Integer goodsNumber;
   private String afterSaleStatus;

   public void setAfterSaleStatus(String afterSaleStatus) {
      this.afterSaleStatus = afterSaleStatus;
   }

   public String getAfterSaleStatus() {
      return afterSaleStatus;
   }

   public void setGoodsNumber(Integer goodsNumber) {
      this.goodsNumber = goodsNumber;
   }

   public Integer getGoodsNumber() {
      return goodsNumber;
   }

   public Date getOrderSubmitTime() {
      return orderSubmitTime;
   }

   public void setOrderSubmitTime(Date orderSubmitTime) {
      this.orderSubmitTime = orderSubmitTime;
   }

   public Double getActualCheque() {
      return actualCheque;
   }
   public void setActualCheque(Double actualCheque) {
      this.actualCheque = actualCheque;
   }
   public Long getSenderId() {
      return senderId;
   }
   public void setSenderId(Long senderId) {
      this.senderId = senderId;
   }
   public Long getOrderId() {
      return orderId;
   }
   public void setOrderId(Long orderId) {
      this.orderId = orderId;
   }
   public Long getRecipentId() {
      return recipentId;
   }
   public void setRecipentId(Long recipentId) {
      this.recipentId = recipentId;
   }
   public String getOrderStatus() {
      return orderStatus;
   }
   public void setOrderStatus(String orderStatus) {
      this.orderStatus = orderStatus;
   }
   public String getCustomerRemarks() {
      return customerRemarks;
   }
   public void setCustomerRemarks(String customerRemarks) {
      this.customerRemarks = customerRemarks;
   }
   public String getShopName() {
      return shopName;
   }
   public void setShopName(String shopName) {
      this.shopName = shopName;
   }
   public String getGoodsCode() {
      return goodsCode;
   }
   public void setGoodsCode(String goodsCode) {
      this.goodsCode = goodsCode;
   }
   public String getPlatformName() {
      return platformName;
   }
   public void setPlatformName(String platformName) {
      this.platformName = platformName;
   }
   public Long getMainOrderId() {
      return mainOrderId;
   }
   public void setMainOrderId(Long mainOrderId) {
      this.mainOrderId = mainOrderId;
   }
   public String getBusinessRemarks() {
      return businessRemarks;
   }
   public void setBusinessRemarks(String businessRemarks) {
      this.businessRemarks = businessRemarks;
   }
   public String getExpressId() {
      return expressId;
   }
   public void setExpressId(String expressId) {
      this.expressId = expressId;
   }
   
}
