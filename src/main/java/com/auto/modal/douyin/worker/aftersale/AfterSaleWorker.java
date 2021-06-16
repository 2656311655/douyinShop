package com.auto.modal.douyin.worker.aftersale;

import com.auto.modal.config.Config;
import com.auto.modal.selenium.WebDriverUtils;
import com.auto.modal.selenium.WebElementUtils;
import com.auto.modal.util.LoggerUtils;
import com.auto.modal.vo.SendExpressVO;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.krb5.internal.PAData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Classname AfterSaleWorker
 * @Description 售后处理
 * @Date 2021/5/31 15:36
 * @Created by Administrator
 */
public class AfterSaleWorker {
    private WebDriverUtils webDriverUtils;
    private JavascriptExecutor jse;
    private  Runtime runtime;
    private List<SendExpressVO> refundExpress ;
    private List<SendExpressVO> refuseRefundExpress ;

    AfterSaleWorker() throws Exception {

        init();
        run();
    }

    public void init() {
        runtime=Runtime.getRuntime();
        String driverPath = "C:\\\\Program Files\\\\Google\\\\Chrome\\\\Application\\\\chromedriver.exe";
        String jsFilePath = "D:\\zili\\work\\it\\project\\servenStars\\data\\stealth.min.js";
        webDriverUtils = new WebDriverUtils(driverPath, jsFilePath);
        webDriverUtils.setPortDrivers("127.0.0.1:9223");
        jse = (JavascriptExecutor) webDriverUtils.getDriver();
        refundExpress =new ArrayList<>();
        refuseRefundExpress =new ArrayList<>();

    }

    public void run() throws Exception {
        switchShop("");
        //
        webDriverUtils.getDriver().get("https://fxg.jinritemai.com/ffa/morder/aftersale/list");
        Thread.sleep(1000 * 5);


      //  LoggerUtils.getLogger().warn("开启退货退款款服务");
     //   afterSaleProcess();
        LoggerUtils.getLogger().warn("开启已发货仅退款服务");
        afterSaleRefundProcess(); //


    }
    public  void  afterSale() throws Exception {
        //退货退款
        List<SendExpressVO> expressVOList=new ArrayList<>();
        String xpath = "//*[@class=\"ant-btn style_button__3sVI1\"]";
        List<WebElement> elements = webDriverUtils.getDriver().findElements(By.xpath(xpath));
        WebElement element = elements.get(2).findElement(By.xpath("span"));
        String s = element.getText();
        try {
            if (s.indexOf("退货待处理") >= 0) {
                Thread.sleep(2000);
                jse.executeScript("arguments[0].click();", elements.get(2));
                Thread.sleep(3000);
                xpath = "//*[@class=\"index_tableRow__3HGIM mortise-rich-table-row\"]";
                List<WebElement> elementList = webDriverUtils.getDriver().findElements(By.xpath(xpath));
                for (WebElement element1 : elementList) {
                    expressVOList.add(getExpressRecord(element1,false));
                }

            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        //
        xpath = "//*[@src=\"data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMzQiIGhlaWdodD0iMzQiIHZpZXdCb3g9IjAgMCAzNCAzNCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIj4KPHBhdGggZmlsbC1ydWxlPSJldmVub2RkIiBjbGlwLXJ1bGU9ImV2ZW5vZGQiIGQ9Ik0xNC44MzEgMjMuODUzNkMxNS4wOTYyIDIzLjg1MzYgMTUuMzUwNiAyMy45NTg5IDE1LjUzODEgMjQuMTQ2NEwxNi45ODI1IDI1LjU5MDhDMTcuMDYwNiAyNS42Njg5IDE3LjE4NzIgMjUuNjY4OSAxNy4yNjUzIDI1LjU5MDhMMTguNzA5NyAyNC4xNDY0QzE4Ljg5NzIgMjMuOTU4OSAxOS4xNTE2IDIzLjg1MzYgMTkuNDE2OCAyMy44NTM2SDI0LjYyNEMyNS40NTI1IDIzLjg1MzYgMjYuMTI0IDIzLjE4MiAyNi4xMjQgMjIuMzUzNlYxMi41QzI2LjEyNCAxMS42NzE2IDI1LjQ1MjUgMTEgMjQuNjI0IDExSDkuNjI0MDJDOC43OTU2IDExIDguMTI0MDIgMTEuNjcxNiA4LjEyNDAyIDEyLjVWMjIuMzUzNkM4LjEyNDAyIDIzLjE4MiA4Ljc5NTYgMjMuODUzNiA5LjYyNDAyIDIzLjg1MzZIMTQuODMxWk02LjYyNDAyIDEyLjVDNi42MjQwMiAxMC44NDMxIDcuOTY3MTcgOS41IDkuNjI0MDIgOS41SDI0LjYyNEMyNi4yODA5IDkuNSAyNy42MjQgMTAuODQzMSAyNy42MjQgMTIuNVYyMi4zNTM2QzI3LjYyNCAyNC4wMTA0IDI2LjI4MDkgMjUuMzUzNiAyNC42MjQgMjUuMzUzNkgxOS42MjM5TDE3LjQ3NzUgMjcuNUMxNy4yODIyIDI3LjY5NTMgMTYuOTY1NiAyNy42OTUzIDE2Ljc3MDMgMjcuNUwxNC42MjM5IDI1LjM1MzZIOS42MjQwMkM3Ljk2NzE3IDI1LjM1MzYgNi42MjQwMiAyNC4wMTA0IDYuNjI0MDIgMjIuMzUzNlYxMi41WiIgZmlsbD0id2hpdGUiLz4KPHBhdGggZD0iTTEzLjI1IDE3LjVDMTMuMjUgMTguMTkwNCAxMi42OTA0IDE4Ljc1IDEyIDE4Ljc1QzExLjMwOTYgMTguNzUgMTAuNzUgMTguMTkwNCAxMC43NSAxNy41QzEwLjc1IDE2LjgwOTYgMTEuMzA5NiAxNi4yNSAxMiAxNi4yNUMxMi42OTA0IDE2LjI1IDEzLjI1IDE2LjgwOTYgMTMuMjUgMTcuNVoiIGZpbGw9IndoaXRlIi8+CjxwYXRoIGQ9Ik0xOC4yNSAxNy41QzE4LjI1IDE4LjE5MDQgMTcuNjkwNCAxOC43NSAxNyAxOC43NUMxNi4zMDk2IDE4Ljc1IDE1Ljc1IDE4LjE5MDQgMTUuNzUgMTcuNUMxNS43NSAxNi44MDk2IDE2LjMwOTYgMTYuMjUgMTcgMTYuMjVDMTcuNjkwNCAxNi4yNSAxOC4yNSAxNi44MDk2IDE4LjI1IDE3LjVaIiBmaWxsPSJ3aGl0ZSIvPgo8cGF0aCBkPSJNMjIuMjUgMTguNzVDMjIuOTQwNCAxOC43NSAyMy41IDE4LjE5MDQgMjMuNSAxNy41QzIzLjUgMTYuODA5NiAyMi45NDA0IDE2LjI1IDIyLjI1IDE2LjI1QzIxLjU1OTYgMTYuMjUgMjEgMTYuODA5NiAyMSAxNy41QzIxIDE4LjE5MDQgMjEuNTU5NiAxOC43NSAyMi4yNSAxOC43NVoiIGZpbGw9IndoaXRlIi8+Cjwvc3ZnPgo=\"]";
        element=webDriverUtils.getDriver().findElement(By.xpath(xpath));
        element.click();
        Thread.sleep(5000);
        webDriverUtils.switchToTab(1);
        // 发送飞鸽信息
        for( SendExpressVO vo:expressVOList){
            sendMsg(vo);
        }
        Thread.sleep(1000*60*60);
        //
        webDriverUtils.switchToTab(0);
        for( SendExpressVO vo:expressVOList){
            refuseApply(vo);
        }


    }
    public void refuseApply(SendExpressVO vo){

    }
    public void  sendMsg(SendExpressVO vo) throws Exception {
        String xpath = "//*[@placeholder=\"搜用户/180天订单(Ctrl+F)\"]";
        WebElement input =webDriverUtils.getDriver().findElement(By.xpath(xpath));
        input.clear();
        input.sendKeys(vo.getOrderId());
        Thread.sleep(2000);
        //点击
        xpath = "//*[@class=\"_1nySwhe5uGiUuOw9uc3y76\"]";
        WebElement button = webDriverUtils.getDriver().findElement(By.xpath(xpath));
        button.click();
        Thread.sleep(2000);
        String s="";
        xpath="//*[@class=\"_22LxwN62A5nSlZXFT9XC7N\"]";
        String fileName = "D:\\zili\\work\\data\\order\\" + vo.getOrderId() + ".png";
        if(vo.getAfterSaleReason().equals("做工粗糙 / 有瑕疵 / 破损或污渍")){
            s="\n" +
                    "亲 您申请退货退款理由是做工粗糙 / 有瑕疵 / 破损或污渍 能否提供下产品质量问题的截图呢 我们这边好跟生产厂家沟通反馈 。\n" +
                    " 如果您觉得麻烦了的 话 退货退款退款原因选择其他我这边给您可以直接同意。 订单都是支持七天无理由退换货的 ，每个订单都是有运费险的 邮费这边我们都是出的 给你带来不便 望请见谅。";
            if(replay(s,webDriverUtils.getDriver())) {
                WebElement element = webDriverUtils.getDriver().findElement(By.xpath(xpath));
                xpath="//*[@class=\"_22LxwN62A5nSlZXFT9XC7N\"]";
                element=webDriverUtils.getDriver().findElement(By.xpath(xpath));
                FileUtils.copyFile(captureElement(element), new File(fileName));
            }
            else{
                LoggerUtils.getLogger().warn(vo.getOrderId()+ "无法回复");
            }
        }
        else if(vo.getAfterSaleReason().equals("效果与商品描述不符")||vo.getAfterSaleReason().equals("效果与商品描述不符")){
            s=" \n" +
                    " 亲 您申请退货退款理由是效果与商品描述不符 能否提供下商品与效果描述不符的相关资质呢 我们这边好跟品牌方沟通反馈 。\n" +
                    " 如果您觉得麻烦了的 话 退货退款退款原因选择其他我这边给你可以直接同意。 订单都是支持七天无理由退换货的 ，每个订单都是有运费险的 邮费这边我们都是出的 给你带来不便 望请见谅。";
            if(replay(s,webDriverUtils.getDriver())) {

                xpath = "//*[@class=\"_22LxwN62A5nSlZXFT9XC7N\"]";
                WebElement element = webDriverUtils.getDriver().findElement(By.xpath(xpath));

                FileUtils.copyFile(captureElement(element), new File(fileName));
            }
            else{
                LoggerUtils.getLogger().warn(vo.getOrderId()+ "无法回复");
            }
        }
        else if(vo.getAfterSaleReason().equals("商品材质 / 品牌 / 外观等描述不符")){
            s="\n" +
                    "您好 ，我是那度传奇小店售后客服 我看您申请的退货理由是商品材质 / 品牌 / 外观等描述不符 这个我们的所有的衣服都是100%纯棉材质，我们的品牌是BOYJIMMY  这是我们的质检报告和品牌授权，、";
            if(replay(s,webDriverUtils.getDriver())) {
                xpath = "//*[@class=\"sit-icon-icon sit-icon-icon-pictureDefault _3KYh10y6QiCySjR4627EIi\"]";
                WebElement element = webDriverUtils.getDriver().findElement(By.xpath(xpath));
                element.click();
                sendPic("boy授权书.png");
                Thread.sleep(1000);
                xpath = "//*[@class=\"_2QrPV7cavgdEghFuIZGnJX\"]";
                List<WebElement> elements = webDriverUtils.getDriver().findElements(By.xpath(xpath));
                elements.get(1).click();
                Thread.sleep(1000);

                xpath = "//*[@class=\"sit-icon-icon sit-icon-icon-pictureDefault _3KYh10y6QiCySjR4627EIi\"]";
                element = webDriverUtils.getDriver().findElement(By.xpath(xpath));
                element.click();
                sendPic("质检报告.png");
                Thread.sleep(1000);
                xpath = "//*[@class=\"_2QrPV7cavgdEghFuIZGnJX\"]";
                elements = webDriverUtils.getDriver().findElements(By.xpath(xpath));
                elements.get(1).click();
                Thread.sleep(1000);

                s = " \n" +
                        "如您还有疑问的 能否提供相关商品材质 / 品牌 / 外观等描述不符的相关资质呢 我这边好跟品牌方反馈沟通下 麻烦了";
                replay(s, webDriverUtils.getDriver());
                s = " 如果您觉得麻烦了的 话 退货退款退款原因选择其他我这边给你可以直接同意。 订单都是支持七天无理由退换货的 ，每个订单都是有运费险的 邮费这边我们都是出的 给你带来不便 望请见谅。";
                replay(s, webDriverUtils.getDriver());
                xpath = "//*[@class=\"_22LxwN62A5nSlZXFT9XC7N\"]";
                element = webDriverUtils.getDriver().findElement(By.xpath(xpath));

                FileUtils.copyFile(captureElement(element), new File(fileName));
            }
            else{
                LoggerUtils.getLogger().warn(vo.getOrderId()+ "无法回复");
            }
        }

    }
    public  void  sendPic(String pngPath) throws InterruptedException {
        String path="C:\\1.exe";
        try {
            Thread.sleep(1000);
            runtime.exec(path+" "+pngPath);

        }
        catch (Exception ex){
            ex.printStackTrace();
            return;
        }
        Thread.sleep(5000);
    }
    public boolean replay(String s,WebDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        String xpath = "//*[@class=\"_3eD_DJuU5XQ5a8zricZqJd\"]";
        if(!webDriverUtils.isJudgingElement(xpath)){
            return false;
        }
        WebElement replayInput = driver.findElement(By.xpath(xpath));
        replayInput.sendKeys(s);
        Thread.sleep(1000);
        replayInput.sendKeys(Keys.ENTER);
        return true;
    }
    public boolean closeChat(){
        String xpath = "//*[@alt=\"结束会话\"]";
        WebElement closeButton = webDriverUtils.getDriver().findElement(By.xpath(xpath));
        closeButton.click();
        return true;
    }
    public void afterSaleRefundProcess() throws Exception {
       // 已发货仅退款
       // setPageSize();
      //  noGoodsRefundProcess();
        String xpath = "//*[@class=\"ant-btn style_button__3sVI1\"]";
        List<WebElement> elements = webDriverUtils.getDriver().findElements(By.xpath(xpath));
        List<WebElement> element = elements.get(1).findElements(By.xpath("span"));
        String s = element.get(1).getText();
        s=s.replace(" ","");
        while (!s.equals("")) {
            Integer i= Integer.parseInt(s);
            if(i<50)
                break;
            Thread.sleep(1000 * 2);
            goodsRefundProcess();
            webDriverUtils.getDriver().get("https://fxg.jinritemai.com/ffa/morder/aftersale/list");
            Thread.sleep(1000 * 5);
            refuseRefundExpress.clear();
            refundExpress.clear();
            xpath = "//*[@class=\"ant-btn style_button__3sVI1\"]";
            elements = webDriverUtils.getDriver().findElements(By.xpath(xpath));
            element = elements.get(1).findElements(By.xpath("span"));
            s = element.get(1).getText();
        }
        webDriverUtils.getDriver().get("https://fxg.jinritemai.com/ffa/morder/aftersale/list");
        Thread.sleep(1000 * 5);
        refuseRefundExpress.clear();
        refundExpress.clear();
        // 退货退款
        xpath = "//*[@class=\"ant-btn style_button__3sVI1\"]";
        elements = webDriverUtils.getDriver().findElements(By.xpath(xpath));
        element = elements.get(2).findElements(By.xpath("span"));
        s = element.get(1).getText();
        s=s.replace(" ","");
        while (!s.equals("")) {
            Integer i= Integer.parseInt(s);
            if(i<1)
                break;
            Thread.sleep(1000 * 2);
            afterSale();
            webDriverUtils.getDriver().get("https://fxg.jinritemai.com/ffa/morder/aftersale/list");
            Thread.sleep(1000 * 5);
            xpath = "//*[@class=\"ant-btn style_button__3sVI1\"]";
            elements = webDriverUtils.getDriver().findElements(By.xpath(xpath));
            element = elements.get(1).findElements(By.xpath("span"));
            s = element.get(1).getText();
        }

    }

    public void goodsRefundProcess() throws InterruptedException {
        List<SendExpressVO> expressVOList=new ArrayList<>();
        //已发货仅退款
        try {
            String xpath = "//*[@class=\"ant-btn style_button__3sVI1\"]";
            List<WebElement> elements = webDriverUtils.getDriver().findElements(By.xpath(xpath));
            WebElement element = elements.get(1).findElement(By.xpath("span"));
            String s = element.getText();
            if (s.indexOf("已发货仅退款待处理") >= 0) {
                Thread.sleep(2000);
                jse.executeScript("arguments[0].click();", elements.get(1));
                Thread.sleep(3000);
                xpath = "//*[@class=\"index_tableRow__3HGIM mortise-rich-table-row\"]";
                List<WebElement> elementList = webDriverUtils.getDriver().findElements(By.xpath(xpath));
                for (WebElement element1 : elementList) {
                    expressVOList.add(getExpressRecord(element1,true));
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //设置物流类型
        for(SendExpressVO vo:expressVOList){
            try {
                setExpressType(vo);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }

        }
        //写入日志
        System.out.println("开始写入日志");
        loggerMsg();
        System.out.println("输入任意键执行已发货仅退款服务");
        Scanner in=new Scanner(System.in);
        String s=in.nextLine();
        executeGoodsRefund();
    }

    public  void  loggerMsg(){
        System.out.println("异常物流件");
        LoggerUtils.getLogger().warn("异常物流件");
        for(SendExpressVO  sendExpressVO :refundExpress){
            if(sendExpressVO.getExpressType()=="异常物流件"){
                LoggerUtils.getLogger().info(sendExpressVO.getExpressId());
                System.out.println(sendExpressVO.getExpressId());
            }
        }
        System.out.println("退回件");
        LoggerUtils.getLogger().info("退回件");
        for(SendExpressVO  sendExpressVO :refundExpress){
            if(sendExpressVO.getExpressType()=="退回件"){
                LoggerUtils.getLogger().info(sendExpressVO.getExpressId());
                System.out.println(sendExpressVO.getExpressId());
            }
        }
        System.out.println("中途件");
        LoggerUtils.getLogger().info("中途件");
        for(SendExpressVO  sendExpressVO :refundExpress){
            if(sendExpressVO.getExpressType()=="中途件"){
                LoggerUtils.getLogger().info(sendExpressVO.getExpressId());
                System.out.println(sendExpressVO.getExpressId());
            }
        }
        System.out.println("签收件");
        LoggerUtils.getLogger().info("签收件");
        for(SendExpressVO  sendExpressVO :refundExpress){
            if(sendExpressVO.getExpressType()=="签收件"){
                LoggerUtils.getLogger().info(sendExpressVO.getExpressId());
                String s="商品已发货或者已签收，不支持仅退款，卖家已通知拦截，买家不想要请拒签,2，改为申请退货退款，填写卖家发货单号，跟踪物流。请勿重复申请";
                System.out.println(sendExpressVO.getExpressId());
            }
        }
    }
    public void  executeGoodsRefund(){
        // 执行已发货仅退款程序
        System.out.println("异常物流件");
        for(SendExpressVO  sendExpressVO :refundExpress){
            if(sendExpressVO.getExpressType()=="异常物流件"){
                try {
                    refund(sendExpressVO);
                }
                catch (Exception ex){
                    System.out.println(sendExpressVO.getExpressId());
                }
            }
        }
        System.out.println("拒收或退回件");
        for(SendExpressVO  sendExpressVO :refundExpress){
            if(sendExpressVO.getExpressType()=="退回件"){

                try {
                    refund(sendExpressVO);
                }
                catch (Exception ex){
                    System.out.println(sendExpressVO.getExpressId());
                }
            }
        }
        System.out.println("中途件");
        for(SendExpressVO  sendExpressVO :refundExpress){
            if(sendExpressVO.getExpressType()=="中途件"){
                try {
                    refund(sendExpressVO);
                }
                catch (Exception ex){
                    System.out.println(sendExpressVO.getExpressId());
                }
            }
        }
        System.out.println("签收件");
        for(SendExpressVO  sendExpressVO :refundExpress){
            if(sendExpressVO.getExpressType()=="签收件"){
                String s="商品已发货或者已签收，不支持仅退款，卖家已通知拦截，买家不想要请拒签,2，改为申请退货退款，填写卖家发货单号，跟踪物流。请勿重复申请";
                try {
                    refuseRefund(sendExpressVO,s);
                }
                catch (Exception ex){
                    System.out.println(sendExpressVO.getExpressId());
                }
            }


        }
    }

    public void  setExpressType(SendExpressVO vo) throws InterruptedException {
        // 设置
        String content =vo.getExpressContent();
        System.out.println(content.length());
        if(content.length()<=55){
            vo.setExpressType("异常物流件");
        }

        else if(content.indexOf("拒收")>=0){
            vo.setExpressType("退回件");
        }
        else if(content.indexOf("已退回")>=0||content.indexOf("退回")>=0 ){
            vo.setExpressType("退回件");
            //refund(vo);


        }
        else {
            if(content.indexOf("签收")>=0){
                vo.setExpressType("签收件");
            }
            else{
                vo.setExpressType("中途件");
            }
        }
        refundExpress.add(vo);


    }
    public void refuseRefund(SendExpressVO vo,String s) throws InterruptedException{
        String content =vo.getExpressContent();
        WebDriver webDriver=webDriverUtils.getDriver();
        String xpath="//*[@class=\"ant-btn ant-btn-dashed\"]";
        //System.out.println(content.length());
        String url="https://fxg.jinritemai.com/ffa/morder/aftersale/detail?type=1&aftersale_id="+vo.getAfterSaleId();
        webDriver.get(url);
        Thread.sleep(2000);
        xpath="//*[@class=\"ant-descriptions-item-content\"]";
        List<WebElement> webElementList=webDriver.findElements(By.xpath(xpath));
        String s1=webElementList.get(1).getText();
        Thread.sleep(1000);
        xpath="//*[@class=\"ant-btn ant-btn-dashed\"]";
        webElementList= webDriver.findElements(By.xpath(xpath));
        if(webElementList.get(0).findElement(By.xpath(".//span")).getText().equals("拒绝")){
            webElementList.get(0).click();
            Thread.sleep(2000);

            xpath="//*[@id=\"comment\"]";
            WebElement element=webDriver.findElement(By.xpath(xpath));
            element.click();
            Thread.sleep(2000);

            xpath="//*[@class=\"ant-select-item-option-content\"]";
            List<WebElement> elements=webDriver.findElements(By.xpath(xpath));
            if(elements.size()==4)
                elements.get(1).click();
            else
                elements.get(0).click();
            Thread.sleep(1000);

            xpath="//*[@class=\"ant-input index_textarea__2w5z5\"]";
            element=webDriver.findElement(By.xpath(xpath));
            element.sendKeys(s);
            Thread.sleep(1000);
            xpath="//*[@class=\"style_inputElement__2b0H0\"]";
            element=webDriver.findElement(By.xpath(xpath));
            element.click();

            String path="C:\\1.exe";
            try {
                Thread.sleep(1000);
                runtime.exec(path+" "+ vo.getExpressId()+".png");

            }
            catch (Exception ex){
                ex.printStackTrace();
                return;
            }
            Thread.sleep(1000*2);

            Thread.sleep(1000*6);
            xpath="//*[@class=\"ant-btn ant-btn-primary\"]";
            List<WebElement> webElementList1= webDriver.findElements(By.xpath(xpath));
            if(webElementList1.get(0).findElement(By.xpath(".//span")).getText().equals("拒绝")){
                webElementList1.get(0).click();
                Thread.sleep(2000);
            }
            else{
                Thread.sleep(2000);
            }
        }
    }
    public void refund(SendExpressVO vo) throws InterruptedException {
        String content =vo.getExpressContent();
        WebDriver webDriver=webDriverUtils.getDriver();
        String xpath="//*[@class=\"ant-btn ant-btn-dashed\"]";
        String url="https://fxg.jinritemai.com/ffa/morder/aftersale/detail?type=1&aftersale_id="+vo.getAfterSaleId();
        webDriver.get(url);
        Thread.sleep(2000);
        xpath="//*[@class=\"ant-descriptions-item-content\"]";
        List<WebElement> webElementList=webDriver.findElements(By.xpath(xpath));
        String s1=webElementList.get(1).getText();
        Thread.sleep(1000);
        if(s1.equals("假冒品牌")||s1.equals("做工粗糙 / 有瑕疵 / 破损或污渍")||s1.equals("商品材质 / 品牌 / 外观等描述不符")||s1.equals("效果与商品描述不符")
                ||s1.equals("商品/赠品/配件/证书漏发")

        ){
            return;
        }
        Thread.sleep(1000);
        xpath="//*[@class=\"ant-btn ant-btn-dashed\"]";
        webElementList= webDriver.findElements(By.xpath(xpath));
        if(webElementList.get(1).findElement(By.xpath(".//span")).getText().equals("退款")){
            webElementList.get(1).click();
            Thread.sleep(2000);
            xpath="//*[@class=\"ant-btn ant-btn-primary\"]";
            List<WebElement> webElementList1= webDriver.findElements(By.xpath(xpath));
            if(webElementList1.get(0).findElement(By.xpath(".//span")).getText().equals("确 定")){
                webElementList1.get(0).click();
                Thread.sleep(1000);
            }
        }
    }
    public Boolean noGoodsRefundProcess() {
        //未发货仅退款
        try {
            //
            String xpath = "//*[@class=\"ant-btn style_button__3sVI1\"]";
            List<WebElement> elements = webDriverUtils.getDriver().findElements(By.xpath(xpath));

            WebElement element = elements.get(0).findElement(By.xpath("span"));

            System.out.println(element.getText());
            String s = element.getText();
            if (s.indexOf("未发货退款待处理") >= 0) {
                Thread.sleep(2000);
                jse.executeScript("arguments[0].click();", elements.get(0));
                Thread.sleep(3000);
                xpath = "//*[@class=\"index_tableHeaderCell__1QTVs\"]";
                List<WebElement> elementsList = webDriverUtils.getDriver().findElements(By.xpath(xpath));

                WebElement e = elementsList.get(0).findElement(By.xpath(".//label/span"));
                s = e.getAttribute("class");
                System.out.println(s);
                if (s.equals("ant-checkbox")) {
                    System.out.println("未选择");
                    xpath = ".//input";
                    WebElement e1 = e.findElement(By.xpath(xpath));
                    jse.executeScript("arguments[0].click();", e1);
                    Thread.sleep(1000);
                } else if (s.equals("ant-checkbox ant-checkbox-checked")) {
                    System.out.println("已全选");
                }
                xpath = "//*[@class=\"style_batch__TFSCg\"]/button/span";
                e = webDriverUtils.getDriver().findElement(By.xpath(xpath));
                System.out.println(e.getText());
                if (e.getText().equals("批量同意退款")) {
                    jse.executeScript("arguments[0].click();", e);
                    Thread.sleep(1000);
                    xpath = "//*[@class=\"ant-btn ant-btn-primary\"]/span";
                    List<WebElement> elementList = webDriverUtils.getDriver().findElements(By.xpath(xpath));
                    System.out.println(elementList.get(1).getText());
                    if (elementList.get(1).getText().equals("确 定")) {
                        elementList.get(1).click();
                        Thread.sleep(1000);
                        xpath = "//*[@class=\"ant-btn ant-btn-primary\"]/span";
                        if (webDriverUtils.isJudgingElement(xpath)) {
                            WebElement e1 = webDriverUtils.getDriver().findElement(By.xpath(xpath));
                            if (e1.getText().equals("我知道了")) {
                                e1.click();
                                return false;
                            }
                        } else {
                            return true;
                        }


                    } else if (e.getText().equals("我知道了")) {
                        e.click();
                        return false;
                    }
                } else {
                    System.out.println(e.getText());
                    return false;
                }
            } else {

                return false;
            }


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public void agreeRefund() {
        //退货同意
        WebDriver driver = webDriverUtils.getDriver();
        //String xpath="";
        Scanner sc = new Scanner(System.in);
        String xpath = "";
        try {
            Thread.sleep(15000);
            try {
                xpath = "//*[@class=\"index_tabbar__2Dlhj byted-tabs\"]/li[4]";
                WebElement e = driver.findElement(By.xpath(xpath));
                WebElement text = e.findElement(By.xpath("span"));
                System.out.println(text.getText());
                String s = text.getText();
                if (s.indexOf("退货待处理") >= 0) {
                    if (!s.equals("退货待处理")) {

                        Thread.sleep(1000);
                        xpath = "//*[@class=\"index_tableRow__3HGIM mortise-rich-table-row\"]";
                        List<WebElement> contents = driver.findElements(By.xpath(xpath));
                        //选择理由
                        for (int i = 0; i < contents.size(); i++) {
                            xpath = ".//div[2]/div[3]";
                            String s1 = contents.get(i).findElement(By.xpath(xpath)).getText();
                            System.out.println(s1);
                            if (s1.equals("其他") || s1.equals("多拍 / 错拍 / 不想要") || s1.equals("不喜欢 / 效果不好")) {
                                xpath = ".//div[1]/label/span/input";
                                WebElement e2 = contents.get(i).findElement(By.xpath(xpath));
                                e2.click();
                                Thread.sleep(1000);
                            }
                        }
                        //同意退货
                        xpath = "//*[@id=\"app-right-wrapper\"]/div[2]/div[1]/div[2]/div/div[2]/div[3]/div[3]/div/div[2]/button/span";
                        e = driver.findElement(By.xpath(xpath));
                        System.out.println(e.getText());
                        if (e.getText().equals("批量同意退货")) {
                            //点击退货
                            xpath = "//*[@class=\"style_batch__2prZP\"]/button";
                            e = driver.findElement(By.xpath(xpath));
                            JavascriptExecutor jse = (JavascriptExecutor) driver;
                            jse.executeScript("arguments[0].click();", e);
                            Thread.sleep(2000);

                            xpath = "//*[@class=\"ant-radio-wrapper ant-radio-wrapper-checked\"]";
                            if (!webDriverUtils.isJudgingElement(xpath)) {
                                xpath = "//*[@class=\"ant-radio-wrapper\"]/span/input";
                                List<WebElement> e1 = driver.findElements(By.xpath(xpath));
                                jse = (JavascriptExecutor) driver;
                                jse.executeScript("arguments[0].click();", e1.get(e1.size() - 1));
                                Thread.sleep(2000);
                            } else {
                                System.out.println();
                            }
                            xpath = "//*[@class=\"ant-checkbox-group custom-checkbox\"]/label/span[1]/input";
                            e = driver.findElement(By.xpath(xpath));
                            e.click();
                            Thread.sleep(2000);
                            xpath = "//*[@class=\"ant-btn index_btn__V8Qrf sp ant-btn-primary\"]/span";
                            e = driver.findElement(By.xpath(xpath));
                            if (e.getText().equals("提交")) {
                                e.click();
                                Thread.sleep(2000);
                                xpath = "//*[@class=\"ant-btn ant-btn-primary\"]/span";
                                if (webDriverUtils.isJudgingElement(xpath)) {
                                    WebElement e1 = driver.findElement(By.xpath(xpath));

                                    if (e1.getText().equals("我知道了")) {
                                        e1.click();
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            //
            xpath = "//*[@class=\"ant-btn ant-btn-primary\"]/span";
            if (webDriverUtils.isJudgingElement(xpath)) {
                WebElement e = driver.findElement(By.xpath(xpath));
                System.out.println(e.getText());
                if (e.getText().equals("确 定")) {
                    e.click();
                    Thread.sleep(1000);
                }
            }
            xpath = "//*[@class=\"ant-btn index_btn__V8Qrf sp ant-btn-default\"]/span";
            if (webDriverUtils.isJudgingElement(xpath)) {
                WebElement e = driver.findElement(By.xpath(xpath));
                System.out.println(e.getText());
                if (e.getText().equals("取消")) {
                    e.click();
                    Thread.sleep(1000);
                }
            }
            xpath = "//*[@class=\"ant-btn ant-btn-primary\"]/span";
            if (webDriverUtils.isJudgingElement(xpath)) {
                WebElement e1 = driver.findElement(By.xpath(xpath));
                if (e1.getText().equals("我知道了")) {
                    e1.click();
                    Thread.sleep(1000);
                }
            }

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public boolean setPageSize() throws InterruptedException {
        //设置页码100页
        WebDriver driver = webDriverUtils.getDriver();
        String xpath = "//*[@title=\"10 条/页\"]";
        WebElement e = null;
        if (webDriverUtils.isJudgingElement(xpath)) {
            e = driver.findElement(By.xpath(xpath));
            e.click();
            Thread.sleep(3000);
            xpath = "//*[@class=\"ant-select-item ant-select-item-option\"]/div";
            List<WebElement> e1 = driver.findElements(By.xpath(xpath));
            Thread.sleep(5000);
            System.out.println(e1.get(e1.size() - 1).getText());
            e1.get(e1.size() - 1).click();
            Thread.sleep(2000);
            return true;
        } else {
            return false;
        }
    }

    public void switchShop(String shopName) {

    }

    public SendExpressVO getExpressRecord(WebElement element,Boolean expressFlag) throws InterruptedException, SQLException {
        //订单号 与售后单号
        SendExpressVO result = new SendExpressVO();
        WebDriver driver = webDriverUtils.getDriver();
        String xpath = ".//*[@class=\"style_orderId__5FC3J\"]";
        List<WebElement> webElements1 = element.findElements(By.xpath(xpath));
        Thread.sleep(200);
        if (webElements1.size() == 2) {
            String orderId = webElements1.get(0).findElement(By.xpath(".//span")).getText();
            result.setOrderId(orderId);
            String afterSaleId = webElements1.get(1).findElement(By.xpath(".//span")).getText();
            result.setAfterSaleId(afterSaleId);
            xpath = ".//*[@style=\"width: 92px; text-align: left;\"]";
            WebElement element1=element.findElement(By.xpath(xpath));
            result.setAfterSaleReason(element1.getText());
        } else {
            return null;
        }
        if(expressFlag) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                xpath = ".//*[@class=\"style_logistics__B1Cmd\"]/a";
                WebElement webElement = element.findElement(By.xpath(xpath));
                Thread.sleep(200);
                //获取快递
                try {
                    jse.executeScript("arguments[0].click();", webElement);
                } catch (Exception ex) {
                    System.out.println(webElements1.get(1).getText());
                    ex.printStackTrace();
                }

                Thread.sleep(2000);
                xpath = "//*[@class=\"index_infoItem__1Ie54\"]/span";
                String expressId = driver.findElements(By.xpath(xpath)).get(1).getText().toUpperCase().trim();
                result.setExpressId(expressId);
                String expressRecord = "";
                Thread.sleep(2000);
                //截图
                String path = "D:\\zili\\work\\data\\expressScreen\\" + result.getExpressId() + ".png";
                File file = new File(path);
                if (!file.exists()) {
                    xpath = "//*[@class=\"drawer-content\"]";
                    WebElement elem = driver.findElement(By.xpath(xpath));
                    String fileName = "D:\\zili\\work\\data\\expressScreen\\" + result.getExpressId() + ".png";
                    try {
                        Thread.sleep(1000);
                        FileUtils.copyFile(captureElement(elem), new File(fileName));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                List<WebElement> webElements = new ArrayList<>();
                xpath = "//*[@class=\"ant-timeline index_traces__2t2Hq\"]/li";
                if (webDriverUtils.isJudgingElement(xpath)) {
                    List<WebElement> webElementList = driver.findElements(By.xpath(xpath));
                    for (WebElement element1 : webElementList) {
                        xpath = ".//*[@class=\"index_date__5LZNr\"]";
                        String s = element1.findElement(By.xpath(xpath)).getText();
                        xpath = ".//*[@class=\"index_itemDetail__2d0h5\"]";
                        s = s + element1.findElement(By.xpath(xpath)).getText();
                        expressRecord = expressRecord + s;
                    }
                }
                result.setExpressContent(expressRecord);

            xpath = "//*[@class=\"drawer-close-icon\"]";
            WebElement e = driver.findElement(By.xpath(xpath));
            e.click();
            Thread.sleep(1000);
        }
        return result;
    }
    //页面元素截图
    public static File captureElement(WebElement element) throws Exception {
        WrapsDriver wrapsDriver = (WrapsDriver) element;
        // 截图整个页面
        File screen = ((TakesScreenshot) wrapsDriver.getWrappedDriver()).getScreenshotAs(OutputType.FILE);
        BufferedImage img = ImageIO.read(screen);
        // 获得元素的高度和宽度
        int width = element.getSize().getWidth();
        int height = element.getSize().getHeight();
        // 创建一个矩形使用上面的高度，和宽度

        Point p = element.getLocation();

        Rectangle rect = new Rectangle(0 ,0,height,width);

        // 得到元素的坐标
        BufferedImage dest = img.getSubimage(p.getX(), 0, rect.width,rect.height);
        //存为png格式
        ImageIO.write(dest, "png", screen);
        return screen;
    }

}
