package com.auto.dao;
import java.util.*;
/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2021-01-15
 */
public class ExpressLogisticsDO {
   private String expressRecord;
   private String expressId;
   private String expressRecordTime;

   public String getExpressRecordTime() {
      return expressRecordTime;
   }

   public void setExpressRecordTime(String expressRecordTime) {
      this.expressRecordTime = expressRecordTime;
   }

   public String getExpressRecord() {
      return expressRecord;
   }
   public void setExpressRecord(String expressRecord) {
      this.expressRecord = expressRecord;
   }
   public String getExpressId() {
      return expressId;
   }
   public void setExpressId(String expressId) {
      this.expressId = expressId;
   }
   
}
