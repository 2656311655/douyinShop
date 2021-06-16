package com.auto.config;

/**
 * @Classname AutoCrawConfig
 * @Description TODO
 * @Date 2021/2/17 11:07
 * @Created by Administrator
 */
public class AutoCrawConfig {
    private String chromeDrivePath; //
    private String jsFilePath; //隐藏爬虫特征js文本。

    public String getJsFilePath() {
        return jsFilePath;
    }

    public void setJsFilePath(String jsFilePath) {
        this.jsFilePath = jsFilePath;
    }

    public String getChromeDrivePath() {
        return chromeDrivePath;
    }

    public void setChromeDrivePath(String chromeDrivePath) {
        this.chromeDrivePath = chromeDrivePath;
    }

    //public static String chromeDrivePath = "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe";
}
