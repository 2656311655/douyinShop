package com.auto.modal.vo;

/**
 * @Classname ReplayVO
 * @Description TODO
 * @Date 2020/12/23 23:07
 * @Created by Administrator
 */
public class CustomerReplyVO {
   private String replyContent;
   private String order;
   private String orderRemark;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }


    public String getOrderRemark() {
        return orderRemark;
    }
    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }
    public String getReplyContent() {
        return replyContent;
    }
    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }


}
