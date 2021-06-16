package com.auto.dao;
import java.util.*;
/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2021-03-09
 */
public class ProductCategoryDO {
   private String productSecondCatagoryName;
   private Integer productCategoryId;
   private String productFirstCatagoryName;
   private String productThreeCatagoryName;
   public String getProductSecondCatagoryName() {
      return productSecondCatagoryName;
   }
   public void setProductSecondCatagoryName(String productSecondCatagoryName) {
      this.productSecondCatagoryName = productSecondCatagoryName;
   }
   public Integer getProductCategoryId() {
      return productCategoryId;
   }
   public void setProductCategoryId(Integer productCategoryId) {
      this.productCategoryId = productCategoryId;
   }
   public String getProductFirstCatagoryName() {
      return productFirstCatagoryName;
   }
   public void setProductFirstCatagoryName(String productFirstCatagoryName) {
      this.productFirstCatagoryName = productFirstCatagoryName;
   }
   public String getProductThreeCatagoryName() {
      return productThreeCatagoryName;
   }
   public void setProductThreeCatagoryName(String productThreeCatagoryName) {
      this.productThreeCatagoryName = productThreeCatagoryName;
   }
   
}
