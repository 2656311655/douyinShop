package com.auto.modal.douyin.worker.order;

import com.auto.modal.excel.ReadExcelUtils;
import com.auto.modal.selenium.WebDriverUtils;
import org.openqa.selenium.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname AutoRemarks
 * @Description 自动备注项链
 * @Date 2021/5/21 14:52
 * @Created by Administrator
 */
public class AutoRemarks {
    public static void main(String[] args) throws Exception {
        Map<String ,Integer> phoneMaps=new HashMap<>();
        // 获取手机
        String filepath=System.getProperty("user.dir");
        System.out.println(filepath); String excelFilePath = filepath+"\\back.xlsx";
        List<HashMap<Integer, String>> cellList = ReadExcelUtils.readHash(excelFilePath);
        for (int k = 1; k < cellList.size(); k++) {
            HashMap<Integer, String> map = cellList.get(k);
            String phone=   map.get(47);
            phoneMaps.put(phone,0);
        }

        //获取拍多件人物信息结构
        for (int k = 1; k < cellList.size(); k++) {
            HashMap<Integer, String> map = cellList.get(k);
            String goodsName=   map.get(30);
            String orderTime=map.get(10);
            Double price=0.0;
            try {
                price=Double.parseDouble(map.get(36));
            }
            catch (Exception ex){
                ex.printStackTrace();
            }

            String phone=map.get(47);
            if(goodsName.indexOf("帽子")>=0||price<30){
                continue;
            }
            else{
                Integer count=phoneMaps.get(phone)+1;
                phoneMaps.put(phone,count);
            }
        }

        //获取项链总数
        int sum=0;
        for (Map.Entry<String, Integer> entry : phoneMaps.entrySet()) {
            String key=entry.getKey();
            Integer value=entry.getValue();
            int j=value/2;
            sum=sum+j;
        }

        System.out.println(sum);
        //过滤已备注的电话号码
        for (int k = 1; k < cellList.size(); k++){
            HashMap<Integer, String> map = cellList.get(k);
            String phone=map.get(47);
            String remarks =map.get(12);
            if(remarks.indexOf("项链")>=0){
                phoneMaps.put(phone,0);
            }
        }
        int i=0;
        for (Map.Entry<String, Integer> entry : phoneMaps.entrySet()) {
            String key=entry.getKey();
            Integer value=entry.getValue();
            if(value<2){
                continue;

            }
            else{
                i++;
            }
        }
        autoRemarks(phoneMaps);
        System.out.println("已完成");
    }
    public static  void autoRemarks(Map<String, Integer> map) throws InterruptedException {
        HashMap<String,Integer> map1=new HashMap<>();
        String driverPath="C:\\\\Program Files\\\\Google\\\\Chrome\\\\Application\\\\chromedriver.exe";
        String filepath=System.getProperty("user.dir");
       // System.out.println(filepath);
        String jsFilePath=filepath+"\\stealth.min.js";
        WebDriverUtils webDriverUtils = new WebDriverUtils(driverPath,jsFilePath);
        webDriverUtils.setPortDrivers("127.0.0.1:9223");
        WebDriver driver = webDriverUtils.getDriver();

        JavascriptExecutor  jse = (JavascriptExecutor) driver;
        driver.get("https://fxg.jinritemai.com/ffa/morder/order/list");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key=entry.getKey();
            Integer value=entry.getValue();
            if(value<2){
                continue;
            }
            else{
                try {
                    re(key,webDriverUtils);
                }
                catch (Exception ex){

                    System.out.println(key);

                }

            }

        }
    }
    public static void re(String key, WebDriverUtils webDriverUtils) throws InterruptedException {
        JavascriptExecutor  jse = (JavascriptExecutor) webDriverUtils.getDriver();
        String xpath="//*[@class=\"modal-close-icon\"]";
        if(webDriverUtils.isJudgingElement(xpath)){
            WebElement element2=webDriverUtils.getDriver().findElement(By.xpath(xpath));
            jse.executeScript("arguments[0].click();" ,element2);
            Thread.sleep(1000);
        }
        xpath="//*[@placeholder=\"请输入收货人姓名或手机\"]";
        Thread.sleep(1000);
        WebElement element =webDriverUtils.getDriver().findElement(By.xpath(xpath));
        jse.executeScript("arguments[0].click();" ,element);
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.chord(Keys.DELETE));
        Thread.sleep(1000);
        element.sendKeys(key);
        xpath="//*[@class=\"ant-btn ant-btn-primary\"]";
        WebElement element2 =webDriverUtils.getDriver().findElement(By.xpath(xpath));
        jse.executeScript("arguments[0].click();" ,element2);
        Thread.sleep(1000);
        xpath="//*[@class=\"index_tableRow__3HGIM mortise-rich-table-row\"]";
        List <WebElement> element1 =webDriverUtils.getDriver().findElements(By.xpath(xpath));
        setOrderInfo(element1,   webDriverUtils);

    }
    public static void setOrderInfo( List <WebElement> elements,  WebDriverUtils webDriverUtils) throws InterruptedException {
        int i=0;
        Thread.sleep(1000);
        for(WebElement element:elements){
            String  xpath=".//*[@style=\"width: 106px; text-align: left;\"]/div";
            // 获取订单状态
           String orderStatus= element.findElement(By.xpath(xpath)).getText();
           if(orderStatus.equals("备货中")||orderStatus.equals("已发货")) {
               //获取goodsName
               xpath = ".//*[@class=\"table_rows__2hcuX table_title__1XgBy table_rows-2__16_0R\"]";
               WebElement element1 = element.findElement(By.xpath(xpath));
               String goodsName = element1.getAttribute("title");
               if (goodsName.indexOf("帽子") >= 0) {
                   continue;
               }
               if (i == 0) {
                   i++;
                   xpath = ".//*[@class=\"index_title__1tP29\"]";
                   String temp="";
                   if (webDriverUtils.isElement(element, xpath)) {
                       WebElement element2= element.findElement(By.xpath(xpath));
                        temp=element2.getText();
                   }
                   //第一个订单 //判断是否已经存在备注
                   xpath = ".//*[@class=\"index_content__12yZZ\"]/div";
                   if (webDriverUtils.isElement(element, xpath)) {
                       Thread.sleep(1000);

                       element1 = element.findElement(By.xpath(xpath));
                       String goodsRemarks = element1.getText();
                       if(temp.equals("买家留言")){
                           remarks(element, webDriverUtils, "送项链-zl ", true);
                       }
                       else {
                           if (goodsRemarks.indexOf("项链") >= 0) {
                               continue;
                           } else {
                               remarks(element, webDriverUtils, "送项链-zl", true);

                           }
                       }
                   } else {
                       //备注信息
                       remarks(element, webDriverUtils, "送项链-zl ", true);
                       continue;

                   }
               } else {
                   i++;
                   xpath = ".//*[@class=\"index_content__12yZZ\"]/div";
                   if (webDriverUtils.isElement(element, xpath)) {
                       Thread.sleep(1000);
                       element1 = element.findElement(By.xpath(xpath));
                       String goodsRemarks = element1.getText();
                       if (goodsRemarks.indexOf("项链") >= 0) {
                           goodsRemarks = goodsRemarks.replace("项链", "");
                           goodsRemarks = goodsRemarks.replace("送", "");
                           goodsRemarks = goodsRemarks + "1";
                           remarks(element, webDriverUtils, goodsRemarks, false);
                       }
                   }


               }
           }
        }

    }

    public static  void remarks(WebElement element,WebDriverUtils webDriverUtils,String remarks,Boolean flag) throws InterruptedException {
       String  xpath=".//*[@class=\"index_rightWrapper__3J8VU\"]";
        WebElement element1 = element.findElement(By.xpath(xpath));
        Thread.sleep(1000);
        element1.click();
        Thread.sleep(1000);
        xpath="//*[@placeholder=\"请输入备注信息\"]";
        element1 = webDriverUtils.getDriver().findElement(By.xpath(xpath));
        try {
            if(flag)
                element1.sendKeys(remarks);
            else{
                element1.click();//鼠标定位到相应的元素
                element1.sendKeys(Keys.chord(Keys.CONTROL, "a"));
                element1.sendKeys(Keys.chord(Keys.DELETE));
                element1.sendKeys(remarks);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        Thread.sleep(1000);
        xpath="//*[@class=\"ant-btn ant-btn-primary\"]";
        webDriverUtils.getDriver().findElements(By.xpath(xpath)).get(1).click();
    }

}
