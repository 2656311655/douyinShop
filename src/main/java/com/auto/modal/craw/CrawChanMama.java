package com.auto.modal.craw;

import com.auto.dao.*;
import com.auto.modal.selenium.WebDriverUtils;
import org.openqa.selenium.*;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Classname ChanmamaWorker
 * @Description 爬取蝉妈妈信息
 * @Date 2021/3/3 9:12
 * @Created by Administrator
 */
public class CrawChanMama {
    private  List<DouyinAccountDO> douYinAccountDOS;
    private List<DouyinLiveDO> douYinLiveDOS;
    private List<DouyinGoodsDO> douYinGoodsDOS;
    private DouyinAccountDao douyinAccountDao;
    private DouyinGoodsDao douyinGoodsDao;
    private DouyinLiveDao douyinLiveDao;
    private HashMap<String,Integer> liveUrls;
    private WebDriverUtils webDriverUtils;
    private WebDriver driver;
    private JavascriptExecutor jse;
    public CrawChanMama() {

    }
    public static void main(String[] args) throws Exception {
        CrawChanMama obj =new CrawChanMama();
        obj.init();
        obj.run();

    }
    public void init() throws SQLException, InterruptedException {
        String driverPath="C:\\\\Program Files\\\\Google\\\\Chrome\\\\Application\\\\chromedriver.exe";
        String jsFilePath="D:\\zili\\work\\it\\project\\servenStars\\data\\stealth.min.js";
        this.webDriverUtils = new WebDriverUtils(driverPath,jsFilePath);
        douYinAccountDOS=new ArrayList<>();
        douYinLiveDOS=new ArrayList<>();
        douYinGoodsDOS=new ArrayList<>();
        liveUrls=new HashMap<>();
        douyinAccountDao=new DouyinAccountDao(DefaultDatabaseConnect.getConn());
        douyinGoodsDao=new DouyinGoodsDao(DefaultDatabaseConnect.getConn());
        douyinLiveDao=new DouyinLiveDao(DefaultDatabaseConnect.getConn());
        webDriverUtils.setPortDrivers("127.0.0.1:9223");
        driver = webDriverUtils.getDriver();
        jse = (JavascriptExecutor) driver;
        driver.get("https://www.chanmama.com/liveSearch?keyword=&search_type=1&follower_count=&gift_count=&volume=&is_take_product=0");
        Thread.sleep(2000);
    }
    public void run() throws InterruptedException, SQLException {
        //debug
       // String url="https://www.chanmama.com/liveDetail/6934499755221322510";
       // crawLiveGoods(url);
        //end;
        crawPotentialityAccount();
        batch();
    }
    public void batch() throws SQLException {
        for(DouyinLiveDO vo:douYinLiveDOS){
            douyinLiveDao.doInsert(vo);
        }
        for(DouyinAccountDO vo:douYinAccountDOS){
            try {
                douyinAccountDao.doInsert(vo);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }

        }
        for(DouyinGoodsDO vo:douYinGoodsDOS){
            try {
                douyinGoodsDao.doInsert(vo);
            }
            catch ( Exception ex){
                System.out.println(vo.getDouYinLiveId());
                System.out.println(vo.getDouyinGoodsTitle());
            }

        }
    }
    public boolean selector(){
        return false;
    }
    public  void   crawAccount(String url)throws InterruptedException{
        String xpath="//*[@id=\"Group3\"]";
        if(webDriverUtils.isJudgingElement(xpath)){
            driver.findElement(By.xpath(xpath)).click();
            Thread.sleep(1000*60*2);
        }
        DouyinAccountDO vo=new DouyinAccountDO();
        vo.setDouyinAccountUrl(url);
        url=String.format("https://www.chanmama.com/authorDetail/%s",url);
        driver.get(url);
        Thread.sleep(3000);
         xpath="//*[@id=\"seo-text\"]/div/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]";
        vo.setDouyinAccountName(driver.findElement(By.xpath(xpath)).getText());
        xpath="//*[@id=\"seo-text\"]/div/div[1]/div[1]/div[1]/div[2]/div[2]";
        vo.setDouyinId(driver.findElement(By.xpath(xpath)).getText());
        xpath="//*[@id=\"seo-text\"]/div/div[1]/div[2]/div[2]/div";
        if(webDriverUtils.isJudgingElement(xpath))
            vo.setDouyinAccountTitle(driver.findElement(By.xpath(xpath)).getText());
        else
            vo.setDouyinAccountTitle("");
        xpath="//*[@id=\"seo-text\"]/div/div[1]/div[3]/div/div[1]/div[1]/div[1]";
        String s=driver.findElement(By.xpath(xpath)).getText();
        s=s.replace("w","");
        double d= Double.parseDouble(s);
        Integer i=(int)(d * 10000) ;
        vo.setAccountFansCounts(i);
        xpath="//*[@id=\"app\"]/div[1]/div/div[1]/div[2]/div[3]/div[2]/div/div/div[4]/div[2]/div[1]/div[2]/div[1]/span";
        if(!webDriverUtils.isJudgingElement(xpath)){
            xpath="//*[@id=\"app\"]/div[1]/div/div[1]/div[2]/div[3]/div[2]/div/div/div[3]/div[2]/div[1]/div[2]/div[1]/span";
        }

        s=driver.findElement(By.xpath(xpath)).getText();
        s=s.replace(",","");
        vo.setLiveCounts(Integer.parseInt(s));
        douYinAccountDOS.add(vo);
    }

    public void crawLiveGoods(String url) throws InterruptedException {
        String liveId=url.replace("https://www.chanmama.com/liveDetail/","");
        int maxPage=2;
        String xpath="";
        driver.get(url);
        Thread.sleep(5000);
        xpath="//*[@id=\"Group3\"]";
        if(webDriverUtils.isJudgingElement(xpath)){
            driver.findElement(By.xpath(xpath)).click();
            Thread.sleep(1000*60*2);
        }
        xpath="//*[@id=\"app\"]/div[1]/div[1]/div/div[2]/div[4]/div[3]/div/div/div[7]/div/div/div/div/table";
        WebElement element=driver.findElement(By.xpath(xpath));
        xpath="./thead/tr/th[3]/div/div/div";
        WebElement element1=element.findElement(By.xpath(xpath));
        //System.out.println(element1.getText());
        element1.click();
        Thread.sleep(1000);
        xpath="//*[@placeholder=\"请选择\"]";
        List<WebElement> webElements=driver.findElements(By.xpath(xpath));
        webElements.get(0).click();
        Thread.sleep(1000);
        xpath="//*[@class=\"el-scrollbar__view el-select-dropdown__list\"]/li[4]/span";
        List<WebElement> element2=driver.findElements(By.xpath(xpath));
        jse.executeScript("arguments[0].click();" ,element2.get(element2.size()-1));
        Thread.sleep(1000);
        xpath="//*[@id=\"app\"]/div[1]/div[1]/div/div[2]/div[4]/div[3]/div/div/div[6]/div[2]/span[2]";
        maxPage=Integer.parseInt(driver.findElement(By.xpath(xpath)).getText())/100+1;
        for(int i=1;i<=maxPage;i++){
            xpath="./tbody/tr";
            List<WebElement> elements=element.findElements(By.xpath(xpath));
            for(WebElement element3:elements){
                getLiveGoods(element3,liveId);
            }
            if(i==maxPage)
                break;
            xpath="//*[@class=\"el-icon el-icon-arrow-right\"]";
            List<WebElement>  elements1=driver.findElements(By.xpath(xpath));
            elements1.get(0).click();
            Thread.sleep(1000);
        }
    }
    public void crawPotentialityAccount() throws InterruptedException, SQLException {
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        Integer interval=11;
        Date now=new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(now);
        for(int i=1;i<interval;i++) {
            calendar.add(Calendar.DAY_OF_MONTH,-1);
            String s=sf.format(calendar.getTime());
            s=s.substring(2,s.length());
            String  xpath="//*[@placeholder=\"开始日期\"]";
            WebElement element=webDriverUtils.getDriver().findElement(By.xpath(xpath));
            for(int j=0;j<8;j++)
                element.sendKeys(Keys.BACK_SPACE);
            element.sendKeys(s);

            xpath="//*[@placeholder=\"结束日期\"]";
            element=webDriverUtils.getDriver().findElement(By.xpath(xpath));
            for(int j=0;j<8;j++)
                element.sendKeys(Keys.BACK_SPACE);
            element.sendKeys(s);
            element.sendKeys(Keys.ENTER);
            Thread.sleep(1000);
            xpath="//*[@id=\"e2e-live-search-table\"]/thead/tr/th[7]/div/div[1]";
            WebElement element1= webDriverUtils.getDriver().findElement(By.xpath(xpath));
            element1.click();
            Thread.sleep(1000);
            getAccountList();
        }
        batch();

    }
    public void craw() throws InterruptedException {

        String xpath="//*[@class=\"category-list flex align-items-center flex-flow-row-wrap\"]/div/div/span";
        List<WebElement> webElements=driver.findElements(By.xpath(xpath));
        //选择类目-男装
        jse.executeScript("arguments[0].click();" ,webElements.get(5));
        System.out.println(webElements.get(5).getText());
        Thread.sleep(5000);
        getAccountList();
        int i=0;

        for(Map.Entry<String, Integer> entry : liveUrls.entrySet()){
            String key=entry.getKey();
            try {
                crawLiveGoods(key);
            }
            catch (Exception ex){
                ex.printStackTrace();
                continue;
            }

            i++;
            if(i>25) {
                Thread.sleep(1000*60*2);
                i=0;
                System.out.println("暂停时间，防止触发爬取验证");
            }
        }
    }
    public void getAccountList() throws InterruptedException {
        int maxPage=10;
        String xpath="";
        for(int i=1;i<=maxPage;i++ ){
            xpath="//*[@id=\"e2e-live-search-table\"]/tbody/tr";
            List<WebElement> webElements=driver.findElements(By.xpath(xpath));
            for(WebElement element:webElements){
                getAccount(element);
            }
            xpath="//*[@class=\"el-icon el-icon-arrow-right\"]";
            WebElement element=driver.findElement(By.xpath(xpath));
            element.click();
            Thread.sleep(5000);
        }
    }
    public void getAccount(WebElement element){
        DouyinAccountDO douyinAccountDO=new DouyinAccountDO();
        DouyinLiveDO douyinLiveDO=new DouyinLiveDO();
        String xpath;
        String s;
        WebElement element1;
        xpath="./td[2]/div/div[2]/div[1]/a";
        element1 =element.findElement(By.xpath(xpath));
        s=element1.getAttribute("href");
        s=s.replace("https://www.chanmama.com/authorDetail/","");
        douyinAccountDO.setDouyinAccountUrl(s);
        douyinAccountDO.setDouyinAccountName(element1.getText());
        douyinLiveDO.setDouyinAccountUrl(douyinAccountDO.getDouyinAccountUrl());
        xpath="./td[1]/div/div[2]/div/a";
        element1 =element.findElement(By.xpath(xpath));
        s=element1.getAttribute("href");
        liveUrls.put(s,0);
        s=s.replace("https://www.chanmama.com/liveDetail/","");
        douyinLiveDO.setDouyinLiveId(s);
        douyinLiveDO.setDouyinLiveTitle(element1.getText());
        xpath="./td[3]";
        element1 =element.findElement(By.xpath(xpath));
        s="2021-"+element1.getAttribute("innerText");
        s=s.replace("\n","  ");
        douyinLiveDO.setDouyinLiveTime(s);

        xpath="./td[7]/div";
        element1=element.findElement(By.xpath(xpath));
        s=element1.getText();
        s=s.replace(",","");
        s=s.replace(".","");
        if(s.indexOf("w")>=0){
            s=s.replace("w","");
            s=s+"000.00";
        }
        try {
            Float salesSum=Float.parseFloat(s);
            douyinLiveDO.setDouyinLiveSales(salesSum);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        xpath="./td[8]/div";
        element1=element.findElement(By.xpath(xpath));
        s=element1.getText();
        s=s.replace(",","");
        s=s.replace(".","");
        if(s.indexOf("w")>=0){
            s=s.replace("w","");
            s=s+"000";

        }
        try {
            Integer saleCount=Integer.parseInt(s);
            douyinLiveDO.setDouyinLiveSaleCount(saleCount);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        douYinAccountDOS.add(douyinAccountDO);
        douYinLiveDOS.add(douyinLiveDO);


    }

    public void getLiveGoods(WebElement element,String liveId){
        DouyinGoodsDO goodsDO=new DouyinGoodsDO();
        goodsDO.setDouYinLiveId(liveId);
        String xpath="./td[1]/div/div[2]/div/a";
        WebElement element1=element.findElement(By.xpath(xpath));
        goodsDO.setDouyinGoodsTitle(element1.getText());
        goodsDO.setDouyinGoodsId(element1.getAttribute("href").replace("https://www.chanmama.com/promotionDetail/",""));

        xpath="./td[2]/div/div[1]";
        String s=element.findElement(By.xpath(xpath)).getText();
        s=s.replace(",","");
        goodsDO.setDouyinGoodsPrice(Double.parseDouble(s));
        xpath="./td[3]/div/div[1]/span";
        s=element.findElement(By.xpath(xpath)).getText();
        s=s.replace(",","");
        s=s.replace(".","");
        if(s.indexOf("w")>=0){
            s=s.replace("w","");
            s=s+"000";
        }
        goodsDO.setDouyinGoodsSaleCount(Integer.parseInt(s));
        xpath="./td[6]";
        s=element.findElement(By.xpath(xpath)).getText();
        s=s.replace(".","-");
        s="2021-"+ s;
        goodsDO.setDouYinSaleTime(s);
        goodsDO.setDouyinGoodsType("unknown");
        douYinGoodsDOS.add(goodsDO);
    }

}
