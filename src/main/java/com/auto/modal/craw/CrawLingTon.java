package com.auto.modal.craw;

import com.auto.dao.*;
import com.auto.modal.selenium.WebDriverUtils;
import com.auto.modal.selenium.WebKeyBoardUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Classname LingTonDataSynchronization
 * @Description 爬取灵通信息抓取
 * @Date 2021/1/28 15:58
 * @Created by Administrator
 */
public class CrawLingTon {
    private WebDriver driver;
    private WebDriverUtils webDriverUtils;
    private Set<ExpressPrintDO> expressPrintDOList;
    private ExpressPrintDao expressPrintDao;
    private ExpressLogisticsDao expressLogisticsDao;
    private Set<ExpressLogisticsDO>  expressLogisticsDOList;
    private Set<CustomerDO> customerDOList;
    private Set<OrderDO> orderDOList;
    public static void main(String[] args) throws Exception {
        CrawLingTon obj =new CrawLingTon();
        String driverPath="C:\\\\Program Files\\\\Google\\\\Chrome\\\\Application\\\\chromedriver.exe";
        String jsFilePath="D:\\zili\\work\\it\\project\\servenStars\\data\\stealth.min.js";
        obj.webDriverUtils = new WebDriverUtils(driverPath,jsFilePath);
        obj.webDriverUtils.setPortDrivers("127.0.0.1:9223");
        obj.expressPrintDao=new ExpressPrintDao(DefaultDatabaseConnect.getConn());
        obj.expressLogisticsDao=new ExpressLogisticsDao(DefaultDatabaseConnect.getConn());
        obj.init();
        obj.run();
    }
    public void init() throws InterruptedException {
        customerDOList=new HashSet<>();
        orderDOList=new HashSet<>();
        expressPrintDOList=new HashSet<>();
        expressLogisticsDOList=new HashSet<>();
        driver = webDriverUtils.getDriver();
        driver.get("https://fxg.jinritemai.com/login");
        System.out.println("开启抖音数据同步，请输入账户和验证码！！");
        System.out.println("正在打开灵通");
        String xpath="//*[@class=\"ant-btn useBtn--2hQXv ant-btn-primary\"]";
        WebElement e= driver.findElement(By.xpath(xpath));
        e.click();
        Thread.sleep(10000);
        System.out.println("开始自动打单,按任意键开始");
        webDriverUtils.switchToTab(1);

    }
    public void run() throws InterruptedException, SQLException {
        //灵通辅助功能
        String xpath = "//*[@id=\"辅助功能 auxiliary/\"]/div/div";
        driver.findElement(By.xpath(xpath)).click();
        Thread.sleep(2000);
        //expressLogisticsSynchronization();
        expressPrintSynchronization();
        System.out.println("爬取物流信息完成，正在更新记录");
        batch();
        System.out.println("本次更新完毕");

    }
    public void expressPrintSynchronization() throws InterruptedException {
        //获取打印记录
        String xpath = "//*[@class=\"ant-pagination ant-table-pagination\"]/li";
        List<WebElement>  webElements=driver.findElements(By.xpath(xpath));
        if(webElements.size()==0)
            return;

        int maxPage=Integer.parseInt(webElements.get(webElements.size()-3).getAttribute("title"));
        int i=0;
        while (i<maxPage){
            Thread.sleep(5000);
            xpath="//*[@class=\"ant-table-row ant-table-row-level-0\"]";
            List<WebElement> webElementList=driver.findElements(By.xpath(xpath));
            for(WebElement element:webElementList){
                expressPrintDOList.add(getExpressDO(element));
            }
            try {
                webElements.get(webElements.size()-2).click();
            }
            catch (Exception e){
                e.printStackTrace();
                continue;
            }
            i++;
        }
    }
    public void expressLogisticsSynchronization() throws InterruptedException {
        //滚动爬取物流记录
        WebKeyBoardUtils webKeyBoardUtils=new WebKeyBoardUtils(driver);

        String xpath = "//*[@class=\"ant-pagination pagination\"]/li";
        List<WebElement>  webElements=driver.findElements(By.xpath(xpath));
        if(webElements.size()==0)
            return;
        int maxPage=Integer.parseInt(webElements.get(webElements.size()-3).getAttribute("title"));
        xpath = "//*[@class=\"ant-pagination-total-text\"]";
        String  s1=driver.findElement(By.xpath(xpath)).getText();
        s1=s1.replace("共","");
        s1=s1.replace("条","");
        int count=500;
        int maxCount=Integer.parseInt(s1);
        int j=1;
        while (j<=maxPage) {
            int i = 0;
            if(j==maxPage){
                if(maxPage==1){
                    count=maxCount;
                }
                else
                    count=maxCount%((maxPage-1)*500);
            }
            while (i+2 < count) {
                    xpath = "//*[@class=\"ReactVirtualized__Table__row virtualized-table-row\"]";
                    webElements = webKeyBoardUtils.getDriver().findElements(By.xpath(xpath));
                    i=Integer.parseInt(webElements.get(webElements.size()-1).getAttribute("aria-rowindex"));
                    if(i>=count)
                        i=count-1;
                    else if(i>10)
                        i=i-10;

                    for (WebElement e : webElements) {
                        try {
                            getExpressLogistics(e);
                        } catch (Exception ex) {
                            break;
                        }
                    }
                try {
                    xpath = String.format("//*[@aria-rowindex=\"%d\"]", i);
                    WebElement element = webKeyBoardUtils.getDriver().findElement(By.xpath(xpath));

                    webKeyBoardUtils.mouseWheel(element, 1);
                    Thread.sleep(20);
                    element.click();
                    Thread.sleep(10);
                 }
                catch (Exception ex){
                    continue;
                }



            }
            xpath = "//*[@class=\"ant-pagination pagination\"]/li";
            try {
                List<WebElement> webElements1=webKeyBoardUtils.getDriver().findElements(By.xpath(xpath));
                webElements1.get(webElements1.size()-2).click();
                Thread.sleep(2000);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
            j++;
            if(j==6)
                System.out.println("");
            xpath = "//*[@class=\"ant-btn scroll-button\"]";
            try {
                List<WebElement> webElements1=webKeyBoardUtils.getDriver().findElements(By.xpath(xpath));
                webElements1.get(webElements1.size()-1).click();
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
            Thread.sleep(2000);

        }

    }
    public void getExpressLogistics(WebElement element){
        OrderDO orderDO=new OrderDO();
        CustomerDO customerDO=new CustomerDO();
        ExpressLogisticsDO expressLogisticsDO=new ExpressLogisticsDO();
        String xpath="./*[@class=\"ReactVirtualized__Table__rowColumn\"]";
        List<WebElement> webElements= element.findElements(By.xpath(xpath));
        xpath="./div/div/div/span";
        String s=webElements.get(0).findElement(By.xpath(xpath)).getText();
        s=s.replace("订单：","");
        s=s.replace("A","");
        orderDO.setOrderId(Long.parseLong(s));
        xpath="./div/div/span";
        s=webElements.get(0).findElement(By.xpath(xpath)).getText();
        s=s.replace("打印：","");
        expressLogisticsDO.setExpressRecordTime(s);
        xpath="./div/div/div/span[1]/span";
        s=webElements.get(1).findElement(By.xpath(xpath)).getText();
        customerDO.setCustomerName(s);
        xpath="./div/div/div/span[2]/span";
        s=webElements.get(1).findElement(By.xpath(xpath)).getText();
        customerDO.setCustomerPhone(s);

        xpath="./div/div";
        s=webElements.get(2).findElement(By.xpath(xpath)).getAttribute("innerText");
        customerDO.setCustomerAddress(s);
        xpath="./div/div/span[2]";
        s=webElements.get(3).findElement(By.xpath(xpath)).getText();
        expressLogisticsDO.setExpressId(s);
        xpath="./div/div/p";
        try {
            if(!webDriverUtils.isElement(webElements.get(4),xpath)){
                xpath="./div/div/div/p";
            }
            s=webElements.get(4).findElements(By.xpath(xpath)).get(0).getAttribute("innerText");
        }
        catch (Exception ex){

            ex.printStackTrace();
        }

        if(s==null)
            s="";
        expressLogisticsDO.setExpressRecord(s);


        orderDOList.add(orderDO);
        customerDOList.add(customerDO);
        expressLogisticsDOList.add(expressLogisticsDO);
    }
    public ExpressPrintDO getExpressDO(WebElement webElement){
        ExpressPrintDO result=new ExpressPrintDO();
        String xpath=".//*[@clicktype=\"doubleClick\"]";
        List<WebElement> webElements= webElement.findElements(By.xpath(xpath));
        String s=webElements.get(0).getText();
        s=s.replace("A","");
        result.setOrderId(s);
        s=webElements.get(1).getText();
        result.setExpressId(s);
        xpath=".//td";
        webElements= webElement.findElements(By.xpath(xpath));
        s=webElements.get(0).getText();
        result.setExpressPrintTime(s);
        s=webElements.get(3).getText();
        result.setRecipientAddress(s);
        s=webElements.get(4).getText();
        result.setRecipientPhone(s);
        s=webElements.get(8).getText();
        result.setDouyinShop(s);
        result.setGoodsCode("");
        result.setGoodsName("");
        result.setGoodsSku("");
        result.setGoodsNumber("1");
        result.setFare("0");
        result.setWeight("0");
        result.setOrderStatus("");
        result.setExpressPlatform("灵通");
        result.setExpressRemark("");
        result.setSenderAddress("");
        result.setSender("");
        result.setSenderPhone("");
        result.setExpressStatus("");
        return result;
    }
    public void batch() throws SQLException {
        for(ExpressPrintDO vo:expressPrintDOList){
            try {
                expressPrintDao.doInsert(vo);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }

        }
        for(ExpressLogisticsDO vo:expressLogisticsDOList){
            try {
                expressLogisticsDao.doInsert(vo);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }



}
