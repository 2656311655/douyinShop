package com.auto.dao;
import java.util.*;
/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020-12-19
 */
public class ProductDO {
   private String productSku;
   private String productCatagory;
   private Long productId;
   private String productDescribe;
   private Date productSaleEndTime;
   private Date productSaleStartTime;
   public String getProductSku() {
      return productSku;
   }
   public void setProductSku(String productSku) {
      this.productSku = productSku;
   }
   public String getProductCatagory() {
      return productCatagory;
   }
   public void setProductCatagory(String productCatagory) {
      this.productCatagory = productCatagory;
   }
   public Long getProductId() {
      return productId;
   }
   public void setProductId(Long productId) {
      this.productId = productId;
   }
   public String getProductDescribe() {
      return productDescribe;
   }
   public void setProductDescribe(String productDescribe) {
      this.productDescribe = productDescribe;
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
