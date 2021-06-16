package com.auto.dao;

/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2021-03-03
 */
public class DouyinAccountDO {
   private String douyinId;
   private String douyinAccountName;
   private String douyinAccountUrl;
   private String douyinAccountTitle;
   private Integer liveCounts;
   private Integer accountFansCounts;

   public Integer getLiveCounts() {
      return liveCounts;
   }

   public void setLiveCounts(Integer liveCounts) {
      this.liveCounts = liveCounts;
   }

   public Integer getAccountFansCounts() {
      return accountFansCounts;
   }

   public void setAccountFansCounts(Integer accountFansCounts) {
      this.accountFansCounts = accountFansCounts;
   }

   public String getDouyinId() {
      return douyinId;
   }
   public void setDouyinId(String douyinId) {
      this.douyinId = douyinId;
   }
   public String getDouyinAccountName() {
      return douyinAccountName;
   }
   public void setDouyinAccountName(String douyinAccountName) {
      this.douyinAccountName = douyinAccountName;
   }
   public String getDouyinAccountUrl() {
      return douyinAccountUrl;
   }
   public void setDouyinAccountUrl(String douyinAccountUrl) {
      this.douyinAccountUrl = douyinAccountUrl;
   }
   public String getDouyinAccountTitle() {
      return douyinAccountTitle;
   }
   public void setDouyinAccountTitle(String douyinAccountTitle) {
      this.douyinAccountTitle = douyinAccountTitle;
   }
   
}
