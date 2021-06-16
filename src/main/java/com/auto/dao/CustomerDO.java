package com.auto.dao;
import java.util.*;
/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020-12-19
 */
public class CustomerDO {
   private String customerAddress;
   private String customerType;
   private String customerPhone;
   private String orderId;
   private Long customerId;
   private Long customerSessionId;
   private String customerTag;
   private String customerName;
   private String customerNickName;

   public String getCustomerNickName() {
      return customerNickName;
   }

   public void setCustomerNickName(String customerNickName) {
      this.customerNickName = customerNickName;
   }

   public String getCustomerAddress() {
      return customerAddress;
   }
   public void setCustomerAddress(String customerAddress) {
      this.customerAddress = customerAddress;
   }
   public String getCustomerType() {
      return customerType;
   }
   public void setCustomerType(String customerType) {
      this.customerType = customerType;
   }
   public String  getCustomerPhone() {
      return customerPhone;
   }
   public void setCustomerPhone(String  customerPhone) {
      this.customerPhone = customerPhone;
   }
   public String getOrderId() {
      return orderId;
   }
   public void setOrderId(String orderId) {
      this.orderId = orderId;
   }
   public Long getCustomerId() {
      return customerId;
   }
   public void setCustomerId(Long customerId) {
      this.customerId = customerId;
   }
   public Long getCustomerSessionId() {
      return customerSessionId;
   }
   public void setCustomerSessionId(Long customerSessionId) {
      this.customerSessionId = customerSessionId;
   }
   public String getCustomerTag() {
      return customerTag;
   }
   public void setCustomerTag(String customerTag) {
      this.customerTag = customerTag;
   }
   public String getCustomerName() {
      return customerName;
   }
   public void setCustomerName(String customerName) {
      this.customerName = customerName;
   }
   
}
