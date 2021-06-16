package com.auto.dao;
import java.util.*;
/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020-12-19
 */
public class ProductPriceDO {
   private String productSku;
   private Long productId;
   private String productPicing;
   private String productUnit;
   private Double productPrice;
   public String getProductSku() {
      return productSku;
   }
   public void setProductSku(String productSku) {
      this.productSku = productSku;
   }
   public Long getProductId() {
      return productId;
   }
   public void setProductId(Long productId) {
      this.productId = productId;
   }
   public String getProductPicing() {
      return productPicing;
   }
   public void setProductPicing(String productPicing) {
      this.productPicing = productPicing;
   }
   public String getProductUnit() {
      return productUnit;
   }
   public void setProductUnit(String productUnit) {
      this.productUnit = productUnit;
   }
   public Double getProductPrice() {
      return productPrice;
   }
   public void setProductPrice(Double productPrice) {
      this.productPrice = productPrice;
   }
   
}
