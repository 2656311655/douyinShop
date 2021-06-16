package com.auto.modal.vo;

/**
 * @Classname ExpressScanVO
 * @Description TODO
 * @Date 2020/12/12 11:23
 * @Created by Administrator
 */
public class ExpressScanVO {
    private String firstScanTime;
    private String scanPerson;
    private String expressId;
    private String expressType;
    private String expressStatus;
    private String expressPackage;
    private String expressRemarks;
    private String businessProcessTime;
    private String businessProcessPerson;

    public String getBusinessProcessPerson() {
        return businessProcessPerson;
    }

    public String getBusinessProcessTime() {
        return businessProcessTime;
    }

    public void setBusinessProcessTime(String businessProcessTime) {
        this.businessProcessTime = businessProcessTime;
    }

    public void setBusinessProcessPerson(String businessProcessPerson) {
        this.businessProcessPerson = businessProcessPerson;
    }

    public String getExpressRemarks() {
        return expressRemarks;
    }

    public void setExpressRemarks(String expressRemarks) {
        this.expressRemarks = expressRemarks;
    }

    public void setExpressType(String expressType) {
        this.expressType = expressType;
    }

    public String getExpressType() {
        return expressType;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    public String getExpressId() {
        return expressId;
    }

    public void setExpressStatus(String expressStatus) {
        this.expressStatus = expressStatus;
    }

    public String getExpressStatus() {
        return expressStatus;
    }

    public String getExpressPackage() {
        return expressPackage;
    }

    public String getFirstScanTime() {
        return firstScanTime;
    }

    public String getScanPerson() {
        return scanPerson;
    }

    public void setExpressPackage(String expressPackage) {
        this.expressPackage = expressPackage;
    }

    public void setFirstScanTime(String firstScanTime) {
        this.firstScanTime = firstScanTime;
    }

    public void setScanPerson(String scanPerson) {
        this.scanPerson = scanPerson;
    }
}
