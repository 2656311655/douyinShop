package com.auto.dao;

import java.util.*;

/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020-12-04
 */
public class UserPersonDO {
    private String realPhone;
    private String realName;
    private String realAddress;
    private Long userId;

    public String getRealPhone() {
        return realPhone;
    }

    public void setRealPhone(String realPhone) {
        this.realPhone = realPhone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRealAddress() {
        return realAddress;
    }

    public void setRealAddress(String realAddress) {
        this.realAddress = realAddress;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
