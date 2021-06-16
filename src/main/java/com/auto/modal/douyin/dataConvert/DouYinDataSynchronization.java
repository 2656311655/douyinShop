package com.auto.modal.douyin.dataConvert;

import com.auto.modal.io.DiskIo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Classname DouYinDataSynchronization
 * @Description 抖音数据同步
 * @Date 2020/12/5 7:34
 * @Created by Administrator
 */
public class DouYinDataSynchronization {
    public static List<String> orderPaths = new ArrayList();
    public static List<String> afterSalePaths = new ArrayList();
    public static List<String> expressPrintPaths = new ArrayList();
    public static List<String> stockPaths=new ArrayList<>();
    public static void main(String[] args) throws Exception {
        DouYinDataSynchronization obj =new DouYinDataSynchronization();
        obj.init();
        //obj.orderSynchronization(); //订单表新增和状态更新
        obj.expressSynchronization();
        obj.customerSynchronization();
    }
    public void expressSynchronization(){
        //快递表同步
        for (String s : expressPrintPaths) {
            ExpressSynchronization.expressInfoUpdate(s);
        }
    }
    public void customerSynchronization() throws IOException {
        for(String s:stockPaths){
            OrderSynchronization.updateNewStockCustomer(s);
        }
    }


    public void  orderSynchronization() throws IOException {
        //订单表同步
        for (String s : orderPaths) {
            OrderSynchronization.orderSynchronization(s);
        }
        //更新订单状态
        String s1="D:\\zili\\data\\douyinData\\抖音\\数据同步表\\2020-12-31\\抖嘉订单状态.xlsx";
       // OrderSynchronization.orderStatusSynchronization(s1);
        //更新状态售后状态
        for (String s : afterSalePaths) {
            OrderSynchronization.updateAfterSale(s);
        }
    }
    public  void init() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String sNow = sf.format(new Date());
        //sNow="2020-12-16";
        String everyDayDir = "D:\\zili\\data\\douyinData\\抖音\\数据同步表\\" + sNow;
        orderPaths.add(everyDayDir + File.separator + "抖嘉订单.xlsx");
        orderPaths.add(everyDayDir + File.separator + "逐日订单.xlsx");
        orderPaths.add(everyDayDir + File.separator + "风生订单.xlsx");
        afterSalePaths.add(everyDayDir + File.separator + "抖嘉售后单.xlsx");
        afterSalePaths.add(everyDayDir + File.separator + "逐日售后单.xlsx");
        afterSalePaths.add(everyDayDir + File.separator + "风生售后单.xlsx");
        expressPrintPaths.add(everyDayDir + File.separator + "灵通物流单.xlsx");
        afterSalePaths.add(everyDayDir + File.separator + "test.xlsx");
        String stockDir="D:\\zili\\data\\douyinData\\抖音\\供应商\\备货单";
        stockPaths=DiskIo.getFileListFromDir(stockDir);
    }
}
