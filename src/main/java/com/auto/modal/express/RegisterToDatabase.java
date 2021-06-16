package com.auto.modal.express;

import com.auto.dao.*;
import com.auto.modal.excel.ReadExcelUtils;

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Classname RegisterToDatabase
 * @Description 腾讯云登记表转数据库
 * @Date 2020/12/13 12:24
 * @Created by Administrator
 */
public class RegisterToDatabase {
    public static void main(String[] args) throws Exception {
        process();
        //process1();
    }

    public static void process()throws Exception{
        String path="D:\\zili\\data\\douyinData\\抖音\\登记表\\十月十号抖店.xlsx";
        List<Map<String, String>> cells = ReadExcelUtils.readSHash(path);
        ExpressProcessDao processDao = new ExpressProcessDao(DefaultDatabaseConnect.getConn());
        ExpressPackageDao packageDao=new ExpressPackageDao(DefaultDatabaseConnect.getConn());
        List<ExpressProcessDO> expressProcessDOList=converts(cells);
        for(ExpressProcessDO e : expressProcessDOList) {
            try {
                processDao.doInsert(e);
            }
            catch (SQLException e1){
                //System.out.println(e.getExpressId());
            }
        }
        List<ExpressPackageDO> expressPackageDOList=convert(cells);
        for(ExpressPackageDO e : expressPackageDOList) {
            try {
                //packageDao.doDelete(e);
                e.setOrderId("1");
                if(!e.getExpressId().trim().equals("")){
                    packageDao.doUpdate(e);
                }

            }
            catch (SQLException e1){
                System.out.println(e.getExpressId());
            }
        }
    }
    public static void process1()throws Exception {

        String path = "C:\\Users\\Administrator\\Desktop\\test.txt";
        ExpressProcessDao processDao = new ExpressProcessDao(DefaultDatabaseConnect.getConn());
        ExpressPackageDao packageDao=new ExpressPackageDao(DefaultDatabaseConnect.getConn());
        ArrayList<String> strings = new ArrayList<>();
        List<ExpressProcessDO> expressProcessDOList = new ArrayList<>();
        List<ExpressPackageDO> expressPackageDOList = new ArrayList<>();
        try {
            File file = new File(path);
            InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
            BufferedReader bf = new BufferedReader(inputReader);
            // 按行读取字符串
            String str;
            while ((str = bf.readLine()) != null) {
                strings.add(str);
                String[] strings1 = str.split(" ");
                ExpressProcessDO expressProcessDO = new ExpressProcessDO();
                ExpressPackageDO expressPackageDO = new ExpressPackageDO();
                expressPackageDO.setShopName(strings1[0]);
                expressPackageDO.setOrderId(strings1[1]);
                expressPackageDO.setAfterSaleId(strings1[2]);
                expressPackageDO.setExpressId(strings1[3]);
                expressPackageDO.setExpressType(strings1[4]);
                expressPackageDO.setExpressPackage(strings1[5]);
                expressPackageDO.setAfterSaleStatus(strings1[6]);
                expressPackageDO.setExpressStatus("已签收");
                expressProcessDO.setExpressId(strings1[3]);
                expressProcessDO.setExpressScanPerson("小星星");
                expressProcessDO.setExpressUnpackTime(new Date());
                expressProcessDO.setExpressUnpackPerson("小星星");
                expressProcessDO.setExpressScanTime(new Date());
                expressProcessDOList.add(expressProcessDO);
                expressPackageDOList.add(expressPackageDO);
            }
            bf.close();
            inputReader.close();
        } catch (IOException e) {

        }
        for(ExpressProcessDO e : expressProcessDOList) {
            try {
                processDao.doInsert(e);
            }
            catch (SQLException e1){
                System.out.println(e.getExpressId());
            }
        }

        for(ExpressPackageDO e : expressPackageDOList) {
            try {
                packageDao.doUpdate(e);
            }
            catch (SQLException e1){
                System.out.println(e.getExpressId());
            }
        }

    }
    public static void test() throws SQLException, IOException {
        String path="D:\\zili\\data\\douyinData\\抖音\\登记表\\十月十号抖店.xlsx";
        List<Map<String, String>> cells = ReadExcelUtils.readSHash(path);
        ExpressProcessDao processDao = new ExpressProcessDao(DefaultDatabaseConnect.getConn());
        ExpressPackageDao packageDao=new ExpressPackageDao(DefaultDatabaseConnect.getConn());
        List<ExpressProcessDO> expressProcessDOList=converts(cells);
        for(ExpressProcessDO e : expressProcessDOList) {
            try {
                processDao.doInsert(e);
            }
            catch (SQLException e1){
                System.out.println(e.getExpressId());
            }
        }
        List<ExpressPackageDO> expressPackageDOList=convert(cells);
        for(ExpressPackageDO e : expressPackageDOList) {
            try {
                packageDao.doUpdate(e);
            }
            catch (SQLException e1){
                System.out.println(e.getExpressId());
            }
        }
    }
    public static List<ExpressPackageDO> convert(List<Map<String, String>> cells) {
        List<ExpressPackageDO> expressPackageDOS=new ArrayList<>();

        for (int j = 1; j < cells.size(); j++) {
            Map<String, String> map = cells.get(j);
            if (map.size() != 13) {
                continue;
            }
            ExpressPackageDO expressPackageDO=new ExpressPackageDO();
            expressPackageDO.setOrderId("1");
            expressPackageDO.setExpressId(map.get("B").trim());
            expressPackageDO.setAfterSaleStatus("");
            expressPackageDO.setExpressStatus("");
            String s=map.get("H");
            if(s==null){
                expressPackageDO.setExpressType("未知件");
            }
            else if(s.indexOf("换")>=0) {
                expressPackageDO.setExpressType("换货件");
            }
            else if(s.indexOf("拦截")>=0){
                expressPackageDO.setExpressType("拦截件");
            }
            else{
                expressPackageDO.setExpressType("未知件");
            }
            expressPackageDO.setExpressStatus("已签收");
            expressPackageDO.setExpressPackage(map.get("F"));
            expressPackageDO.setShopName("");
            String s1=map.get("C");
            expressPackageDO.setExpressRemark(map.get("C")+map.get("D"));
            expressPackageDOS.add(expressPackageDO);
        }
        return expressPackageDOS;
    }
    public static List<ExpressProcessDO> converts(List<Map<String, String>> cells){
        List<ExpressProcessDO> expressProcessDOList=new ArrayList<>();
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        for (int j = 1; j < cells.size(); j++) {
            Map<String, String> map = cells.get(j);
            if (map.size() != 13) {
                System.out.println(map.get("B"));
                continue;
            }
            ExpressProcessDO expressProcessDO =new ExpressProcessDO();
            String scanTime="2020-12-29 00:00:00";
            try {
                if(map.get("B")==null)
                    continue;
                String s=map.get("B").trim().toUpperCase();
                s=s.replace("R02T","");
                s=s.replace("-1-1-","");
                Date date= sf.parse(scanTime);
                expressProcessDO.setExpressScanTime(date);
                expressProcessDO.setExpressUnpackTime(date);
                expressProcessDO.setExpressUnpackPerson("小星星");
                expressProcessDO.setExpressScanPerson("小星星");
                expressProcessDO.setExpressId(s);
            }
            catch (ParseException e){
                e.getErrorOffset();
                System.out.println(scanTime);
            }
            if(!expressProcessDO.getExpressId().trim().equals(""))
                expressProcessDOList.add(expressProcessDO);
        }
        return expressProcessDOList;
    }
    public static List<ExpressPackageDO> convert(String path ){
        List<ExpressPackageDO> expressPackageDOS=new ArrayList<>();

        return expressPackageDOS;
    }
    public static List<ExpressProcessDO> converts(String path ){
        List<ExpressProcessDO> expressProcessDOList=new ArrayList<>();
        return expressProcessDOList;
    }
}
