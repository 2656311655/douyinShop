package com.auto.dao;

import java.util.*;

/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020-12-04
 */
public class ExpressProcessDO {
    private Date expressBusinessProcessTime;
    private String expressUnpackPerson;
    private String expressScanPerson;
    private String expressBusinessProcessPerson;
    private Date expressScanTime;
    private Date expressUnpackTime;
    private String expressId;
    private String expressBusinessStatus;


    public void setExpressBusinessStatus(String expressBusinessStatus) {
        this.expressBusinessStatus = expressBusinessStatus;
    }

    public String getExpressBusinessStatus() {
        return expressBusinessStatus;
    }

    public Date getExpressBusinessProcessTime() {
        return expressBusinessProcessTime;
    }

    public void setExpressBusinessProcessTime(Date expressBusinessProcessTime) {
        this.expressBusinessProcessTime = expressBusinessProcessTime;
    }

    public String getExpressUnpackPerson() {
        return expressUnpackPerson;
    }

    public void setExpressUnpackPerson(String expressUnpackPerson) {
        this.expressUnpackPerson = expressUnpackPerson;
    }

    public String getExpressScanPerson() {
        return expressScanPerson;
    }

    public void setExpressScanPerson(String expressScanPerson) {
        this.expressScanPerson = expressScanPerson;
    }

    public String getExpressBusinessProcessPerson() {
        return expressBusinessProcessPerson;
    }

    public void setExpressBusinessProcessPerson(String expressBusinessProcessPerson) {
        this.expressBusinessProcessPerson = expressBusinessProcessPerson;
    }

    public Date getExpressScanTime() {
        return expressScanTime;
    }

    public void setExpressScanTime(Date expressScanTime) {
        this.expressScanTime = expressScanTime;
    }

    public Date getExpressUnpackTime() {
        return expressUnpackTime;
    }

    public void setExpressUnpackTime(Date expressUnpackTime) {
        this.expressUnpackTime = expressUnpackTime;
    }

    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

}
