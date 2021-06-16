package com.auto.modal.util;

import org.apache.log4j.Logger;

/**
 * @Classname LoggerUtils
 * @Description TODO
 * @Date 2021/6/9 17:31
 * @Created by Administrator
 */
public class LoggerUtils {
    private  static Logger logger;

    public static Logger getLogger(){
        if (null == logger){
            //Java8 废弃了Reflection.getCallerClass()
            logger = Logger.getLogger(LoggerUtils.class);
        }
        return logger;
    }
}
