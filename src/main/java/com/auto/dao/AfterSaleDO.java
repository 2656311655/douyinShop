package com.auto.dao;
import java.util.*;
/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2021-01-03
 */
public class AfterSaleDO {
   private String afterSaleExpressId;
   private Long orderId;
   private String afterSaleStatus;
   private Long afterSaleId;
   private String afterSaleReason;
   private String afterSaleType;
   private Date afterSaleApplyTime;
   private String afterSaleRemarks;
   private Date finalAfterSaleProcessTime;

   public Date getFinalAfterSaleProcessTime() {
      return finalAfterSaleProcessTime;
   }

   public void setFinalAfterSaleProcessTime(Date finalAfterSaleProcessTime) {
      this.finalAfterSaleProcessTime = finalAfterSaleProcessTime;
   }

   public Date getAfterSaleApplyTime() {
      return afterSaleApplyTime;
   }

   public String getAfterSaleRemarks() {
      return afterSaleRemarks;
   }

   public void setAfterSaleRemarks(String afterSaleRemarks) {
      this.afterSaleRemarks = afterSaleRemarks;
   }

   public void setAfterSaleApplyTime(Date afterSaleApplyTime) {
      this.afterSaleApplyTime = afterSaleApplyTime;
   }

   public String getAfterSaleExpressId() {
      return afterSaleExpressId;
   }
   public void setAfterSaleExpressId(String afterSaleExpressId) {
      this.afterSaleExpressId = afterSaleExpressId;
   }
   public Long getOrderId() {
      return orderId;
   }
   public void setOrderId(Long orderId) {
      this.orderId = orderId;
   }
   public String getAfterSaleStatus() {
      return afterSaleStatus;
   }
   public void setAfterSaleStatus(String afterSaleStatus) {
      this.afterSaleStatus = afterSaleStatus;
   }
   public Long getAfterSaleId() {
      return afterSaleId;
   }
   public void setAfterSaleId(Long afterSaleId) {
      this.afterSaleId = afterSaleId;
   }
   public String getAfterSaleReason() {
      return afterSaleReason;
   }
   public void setAfterSaleReason(String afterSaleReason) {
      this.afterSaleReason = afterSaleReason;
   }
   public String getAfterSaleType() {
      return afterSaleType;
   }
   public void setAfterSaleType(String afterSaleType) {
      this.afterSaleType = afterSaleType;
   }
   
}
