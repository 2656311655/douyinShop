package com.auto.modal.craw;

import com.auto.modal.selenium.WebDriverUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * @Classname Start
 * @Description TODO
 * @Date 2021/4/15 17:49
 * @Created by Administrator
 */
public class Start {
    public static void main(String[] args) throws Exception {
        String driverPath="C:\\\\Program Files\\\\Google\\\\Chrome\\\\Application\\\\chromedriver.exe";
        String jsFilePath="D:\\zili\\work\\it\\project\\servenStars\\data\\stealth.min.js";
        WebDriverUtils webDriverUtils=new WebDriverUtils(driverPath,jsFilePath);
        webDriverUtils.setPortDrivers("127.0.0.1:9223");
        startOrderUpdate(webDriverUtils);
    }
    public static void startOrderUpdate(WebDriverUtils webDriverUtils) throws NoSuchMethodException, InterruptedException, ParseException, InstantiationException, SQLException, IllegalAccessException, InvocationTargetException {
        CrawDouYinOrder crawDouYinOrder=new CrawDouYinOrder(webDriverUtils,"那度传奇服饰");
        crawDouYinOrder.orderSynchronization();
    }
}
