package com.auto.modal.douyin.worker.aftersale;

import com.auto.modal.excel.ReadExcelUtils;
import com.auto.modal.selenium.WebDriverUtils;
import com.auto.modal.util.LoggerUtils;
import com.auto.modal.vo.SendExpressVO;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname AgreeAfterSale
 * @Description 自动退货
 * @Date 2021/6/14 0:17
 * @Created by Administrator
 */
public class AutoAfterSale {
    private WebDriverUtils webDriverUtils;
    private JavascriptExecutor jse;
    private  Runtime runtime;
    private List<SendExpressVO> refundExpress ;
    private String scanExpressExcelPath;
    private String afterSaleExcelPath;
    AutoAfterSale() throws InterruptedException, IOException {
        init();
        run();
    }
    public void  run() throws InterruptedException, IOException {
        //
        webDriverUtils.getDriver().get("https://fxg.jinritemai.com/ffa/morder/aftersale/list");
        Thread.sleep(1000 * 5);
        LoggerUtils.getLogger().warn("开启退货退款服务");
        autoAfterSaleProcess(); //(); //
    }
    public  void autoAfterSaleProcess() throws IOException, InterruptedException {
        //
        getScanExpress();
        for(SendExpressVO vo:refundExpress) {
            try {
                agreeRefundProcess(vo);
            } catch (Exception ex) {
                LoggerUtils.getLogger().info(vo.getExpressId()+" 退款失败");
            }
        }
        refundExpress.clear();
    }
    public void  agreeRefundProcess(SendExpressVO vo) throws InterruptedException {
        WebDriver webDriver=webDriverUtils.getDriver();
        String xpath="//*[@class=\"ant-btn ant-btn-dashed\"]";
        String url="https://fxg.jinritemai.com/ffa/morder/aftersale/detail?type=1&aftersale_id="+vo.getAfterSaleId();
        webDriver.get(url);
        Thread.sleep(5000);
        xpath="//*[@class=\"ant-btn ant-btn-dashed\"]";
        List<WebElement> webElementList= webDriver.findElements(By.xpath(xpath));
        if(webElementList.get(2).findElement(By.xpath(".//span")).getText().equals("已收到货，退款")){
            webElementList.get(2).click();
            Thread.sleep(2000);
            xpath="//*[@class=\"ant-btn ant-btn-primary\"]";
            List<WebElement> webElementList1= webDriver.findElements(By.xpath(xpath));
            if(webElementList1.get(0).findElement(By.xpath(".//span")).getText().equals("确 定")){
                webElementList1.get(0).click();
                Thread.sleep(1000);
                LoggerUtils.getLogger().info(vo.getExpressId()+" 退款成功");
            }
        }

    }
    public void getScanExpress() throws IOException {
        HashMap<String,Integer> expressMaps=new HashMap<>();
        List<HashMap<Integer, String>> cellList = ReadExcelUtils.readHash(scanExpressExcelPath);
        for (int k = 1; k < cellList.size(); k++) {
            HashMap<Integer, String> map = cellList.get(k);
            String expressId=   map.get(0);
            if(expressId.indexOf("1002172")>=0){
                System.out.println(expressId);
            }
            expressId=expressId.replaceAll(" ","");
            expressId=expressId.replaceAll("-1-1-","");
            expressId=expressId.toUpperCase();
            expressMaps.put(expressId,0);
        }
        cellList = ReadExcelUtils.readHash(afterSaleExcelPath);
        for (int k = 1; k < cellList.size(); k++) {
            HashMap<Integer, String> map = cellList.get(k);
            String afterSaleType=map.get(10);
            if(afterSaleType.equals("换货")){
                continue;
            }
            else{
                String expressId=map.get(20);
                if(map.get(20).indexOf("1002172")>=0){
                    System.out.println(afterSaleType);
                }
                expressId=expressId.replaceAll(" ","");
                expressId=expressId.toUpperCase();
                if(expressMaps.containsKey(expressId)){
                    SendExpressVO vo=new SendExpressVO();
                    vo.setAfterSaleId(map.get(0));
                    vo.setExpressId(expressId);
                    refundExpress.add(vo);
                }
            }
        }
    }
    public void init() {
        runtime=Runtime.getRuntime();
        String driverPath = "C:\\\\Program Files\\\\Google\\\\Chrome\\\\Application\\\\chromedriver.exe";
        String jsFilePath = "D:\\zili\\work\\it\\project\\servenStars\\data\\stealth.min.js";
        webDriverUtils = new WebDriverUtils(driverPath, jsFilePath);
        webDriverUtils.setPortDrivers("127.0.0.1:9223");
        jse = (JavascriptExecutor) webDriverUtils.getDriver();
        refundExpress =new ArrayList<>();
        scanExpressExcelPath="D:\\zili\\data\\douyinData\\抖音\\售后\\退货物流单号.xlsx";
        afterSaleExcelPath="D:\\zili\\data\\douyinData\\抖音\\售后\\抖音售后单.xlsx";

    }
}
