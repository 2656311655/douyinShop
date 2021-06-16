package com.auto.modal.douyin.dataConvert;

import com.auto.dao.DefaultDatabaseConnect;
import com.auto.dao.handle.ExpressHDao;

import java.sql.SQLException;

/**
 * @Classname ClearnExpressPackage
 * @Description 清理去重数据库 数据库数据清洗。
 * @Date 2020/12/15 8:27
 * @Created by Administrator
 */
public class CleanDataBase {
    public static void main(String[] args) throws Exception {
        dataClean();
    }
    public static void  dataClean() throws SQLException {
        ExpressHDao expressHDao=new ExpressHDao(DefaultDatabaseConnect.getConn());
        //expressHDao.duplicateRemoval();
        expressHDao.reviseExpressId();
    }

}
