package com.auto.modal.selenium;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * @Classname WebKeyBorard
 * @Description 模拟鼠标键盘操作
 * @Date 2021/2/25 17:40
 * @Created by Administrator
 */
public class WebKeyBoardUtils {
    private WebDriver driver;
    private Actions actions;
    public WebKeyBoardUtils(WebDriver driver){
        this.driver=driver;
        actions=new Actions(driver);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        driver.close();
        driver.quit();
    }
    public WebDriver getDriver(){
        return driver;
    }
    //鼠标
    public boolean  moveMouse(int x,int y){
        actions.moveByOffset(200,300).perform();
        return true;
    }

    public void  mouseLeftClick(){

    }
    public void  mouseDoubleClick(){

    }
    public void  mouseRightClick(){

    }
    public void mouseWheel(WebElement element,int count){

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView();", element);
    }
    //键盘

}
