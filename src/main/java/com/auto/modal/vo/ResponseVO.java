package com.auto.modal.vo;


/**
 * @Description: 请求返回的json结构体
 * @Param:
 * @Auther: zl
 * @Date: 2020/2/23 16:52
 */
public class ResponseVO<T> {
    public int retCode;
    public String retMsg;
    public T data;

    public ResponseVO(int code, String message, T data) {
        this.retCode = code;
        this.retMsg = message;
        this.data = data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }

    public void setRetMsg(String retMessage) {
        this.retMsg = retMessage;
    }

    public static void main(String[] args) {

    }
}
