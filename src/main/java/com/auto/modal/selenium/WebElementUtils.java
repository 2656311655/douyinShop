package com.auto.modal.selenium;

import org.openqa.selenium.WebDriver;

/**
 * @Classname WebElement
 * @Description 封装浏览器驱动元素类的一些常用函数
 * @Date 2021/2/25 17:39
 * @Created by Administrator
 */
public class WebElementUtils {
    private WebDriver driver;
    WebElementUtils(WebDriver driver){
        this.driver=driver;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        driver.close();
    }
}
