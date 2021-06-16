package com.auto.modal.config;

import com.auto.modal.util.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @Classname Config
 * @Description TODO
 * @Date 2020/11/25 18:31
 * @Created by Administrator
 */
public class Config {
    public static final Logger logger = LoggerFactory.getLogger("");
    public static String productExcelFilePath="D:\\zili\\data\\douyinData\\抖音\\供应商\\产品资料\\产品资料.xlsx";
    public static void main(String[] args) throws Exception {
        LoggerUtils.getLogger().warn("123213");
    }
}
