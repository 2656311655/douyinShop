package com.auto.modal.selenium;



import com.auto.modal.io.FileIo;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Classname WebDriverUtils
 * @Description 封装浏览器驱动工具类的一些常用函数
 * @Date 2020/12/19 16:13
 * @Created by Administrator
 */
public class WebDriverUtils {
    //test
    private  String driverPath; //浏览器驱动文件路径
    private String jsFilePath;//隐藏爬虫特征js文本。
    private WebDriver driver;//浏览器驱动实例
    public WebDriverUtils(String drivePath, String jsFilePath){
       this.driverPath= drivePath;
       this.jsFilePath= jsFilePath;
    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        driver.close();
        driver.quit();

    }

    public void loadWebDriver(WebDriver webDriver){
        this.driver=webDriver;
    }
    public WebDriver getDriver(){
        return this.driver;
    }
    public  void setDriver(String jsFilePath ) {
        System.setProperty("webdriver.chrome.driver", driverPath);
        ChromeOptions options = new ChromeOptions();
        ChromeDriver driver = new ChromeDriver(options);
        //jsFilePath = "D:\\zili\\work\\it\\project\\servenStars\\data\\stealth.min.js";
        String js = FileIo.readTxtFile(jsFilePath, "utf-8");
        Map map = new HashMap();
        // map.put("source","Object.defineProperties(navigator, {webdriver:{get:()=>undefined}})");
        map.put("source", js);
        driver.executeCdpCommand("Page.addScriptToEvaluateOnNewDocument", map);
        this.driver=driver;
    }
    public void setPortDrivers(String ip) {
        //通过端口代理浏览器,需执行//chrome.exe --remote-debugging-port=9222 --user-data-dir="C:\selenum\AutomationProfile"脚本
        System.setProperty("webdriver.chrome.driver", driverPath);
        ChromeOptions options = new ChromeOptions();
        //String port="127.0.0.1:9222";
        options.setExperimentalOption("debuggerAddress", ip);
        Map map = new HashMap();
        map.put("source","Object.defineProperties(navigator, {webdriver:{get:()=>undefined}})");
        map.put("source", jsFilePath);
        ChromeDriver driver = new ChromeDriver(options);
        driver.executeCdpCommand("Page.addScriptToEvaluateOnNewDocument", map);
        this.driver=driver;
    }
    //selenium
    public  void refresh() throws InterruptedException {
        driver.navigate().refresh();
        Thread.sleep(1000);
    }
    public  void switchToTab(int index) {
        Set<String> s1 = driver.getWindowHandles();
        int i = 0;
        for (String s : s1) {
            if (i == index) {
                driver.switchTo().window(s);
                break;
            }
            i++;
        }

    }
    public  boolean isElement(WebElement element, String xpath) {
        try {
            element.findElement(By.xpath(xpath));
            return true;
        } catch (Exception e) {
            //System.out.println("不存在此元素");
            return false;
        }
    }
    public  boolean isJudgingElement( String xpath) {
        try {
            driver.findElement(By.xpath(xpath));
            return true;
        } catch (Exception e) {
            //System.out.println("不存在此元素");
            return false;
        }
    }
    public  void openTab( String url) {
        //Open tab 2 using CTRL + t keys.
        driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
        //Open URL In 2nd tab.
        driver.get(url);

        //Call switchToTab() method to switch to 1st tab
        switchToTab(driver);
    }
    public  void switchToTab(WebDriver driver) {
        //Switching between tabs using CTRL + tab keys.
        driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "\t");
        //Switch to current selected tab's content.
        driver.switchTo().defaultContent();
    }


}
