package com.auto.dao;
import java.util.*;
/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020-12-19
 */
public class ProductCostDO {
   private String productSku;
   private Double productCost;
   private Long productId;
   private String productCostType;
   private Date productSaleEndTime;
   private Date productSaleStartTime;
   public String getProductSku() {
      return productSku;
   }
   public void setProductSku(String productSku) {
      this.productSku = productSku;
   }
   public Double getProductCost() {
      return productCost;
   }
   public void setProductCost(Double productCost) {
      this.productCost = productCost;
   }
   public Long getProductId() {
      return productId;
   }
   public void setProductId(Long productId) {
      this.productId = productId;
   }
   public String getProductCostType() {
      return productCostType;
   }
   public void setProductCostType(String productCostType) {
      this.productCostType = productCostType;
   }
   public Date getProductSaleEndTime() {
      return productSaleEndTime;
   }
   public void setProductSaleEndTime(Date productSaleEndTime) {
      this.productSaleEndTime = productSaleEndTime;
   }
   public Date getProductSaleStartTime() {
      return productSaleStartTime;
   }
   public void setProductSaleStartTime(Date productSaleStartTime) {
      this.productSaleStartTime = productSaleStartTime;
   }
   
}
