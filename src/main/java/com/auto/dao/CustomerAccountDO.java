package com.auto.dao;
import java.util.*;
/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020-12-19
 */
public class CustomerAccountDO {
   private String plaformName;
   private String customerNickname;
   private String platformUuid;
   private Long customerId;
   private String customerAccountType;
   public String getPlaformName() {
      return plaformName;
   }
   public void setPlaformName(String plaformName) {
      this.plaformName = plaformName;
   }
   public String getCustomerNickname() {
      return customerNickname;
   }
   public void setCustomerNickname(String customerNickname) {
      this.customerNickname = customerNickname;
   }
   public String getPlatformUuid() {
      return platformUuid;
   }
   public void setPlatformUuid(String platformUuid) {
      this.platformUuid = platformUuid;
   }
   public Long getCustomerId() {
      return customerId;
   }
   public void setCustomerId(Long customerId) {
      this.customerId = customerId;
   }
   public String getCustomerAccountType() {
      return customerAccountType;
   }
   public void setCustomerAccountType(String customerAccountType) {
      this.customerAccountType = customerAccountType;
   }
   
}
