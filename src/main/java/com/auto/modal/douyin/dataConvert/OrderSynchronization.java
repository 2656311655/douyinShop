package com.auto.modal.douyin.dataConvert;

import com.auto.dao.DatabaseConnection;
import com.auto.dao.DefaultDatabaseConnect;
import com.auto.dao.DouyinOrderDO;
import com.auto.dao.DouyinOrderDao;
import com.auto.modal.io.FileIo;
import com.auto.modal.excel.ReadExcelUtils;
import com.auto.modal.vo.AfterSaleVO;
import com.auto.modal.vo.CustomerVO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Classname DouyinCsvToDatabase
 * @Description 将抖音订单excel文件转换为 DATABASE
 * @Date 2020/10/2 23:59
 * @Created by Administrator
 */
public class OrderSynchronization {
    public static void main(String[] args) throws Exception {
        //test();
        // updateAfterSale("");
        updateCustomerInfo("");
    }

    public static List<DouyinOrderDO> convert(List<Map<String, String>> cells) {
        List<DouyinOrderDO> orderDOList = new ArrayList<>();
        for (int j = 1; j < cells.size(); j++) {
            Map<String, String> map = cells.get(j);
            DouyinOrderDO douyinOrderDO = new DouyinOrderDO();
            String orderId = map.get("B").replace("'", "");
            douyinOrderDO.setOrderId(orderId);
            orderId = map.get("A").replace("'", "");
            douyinOrderDO.setMainOrderId(orderId);

            douyinOrderDO.setGoods(map.get("C"));
            douyinOrderDO.setGoodsSize(map.get("D"));
            douyinOrderDO.setGoodsId(map.get("F"));
            douyinOrderDO.setGoodsSku(map.get("G"));
            douyinOrderDO.setGoodsPrice(map.get("H"));
            douyinOrderDO.setNumber(map.get("E"));
            douyinOrderDO.setFare(map.get("I"));
            douyinOrderDO.setActualCheque(map.get("O"));
            douyinOrderDO.setPayWay(map.get("P"));
            douyinOrderDO.setUserNickname(map.get("Q"));
            douyinOrderDO.setRecipient(map.get("R"));
            douyinOrderDO.setRecipientPhone(map.get("S"));
            douyinOrderDO.setRecipientAddress(map.get("T"));
            douyinOrderDO.setProvince(map.get("U"));
            douyinOrderDO.setCity(map.get("V"));
            douyinOrderDO.setArea(map.get("W"));
            douyinOrderDO.setDetailedAddress(map.get("X"));
            douyinOrderDO.setUserRemarks(map.get("AE"));
            douyinOrderDO.setExpressCompany(map.get("AF"));
            douyinOrderDO.setExpressId(map.get("AG"));
            douyinOrderDO.setOrderSumbitTime(map.get("AH"));
            douyinOrderDO.setBusinessRemarks(map.get("AJ"));
            douyinOrderDO.setAchieveTime(map.get("AK"));
            douyinOrderDO.setApp(map.get("AL"));
            douyinOrderDO.setBusinessType(map.get("AM"));
            douyinOrderDO.setOrderType(map.get("AF"));
            douyinOrderDO.setDistributor("");
            douyinOrderDO.setSupplier("");
            douyinOrderDO.setOrderStatus(map.get("AN"));
            douyinOrderDO.setFinalLatestDeliveryTime(map.get("AO"));
            douyinOrderDO.setOderType(map.get("AK"));
            douyinOrderDO.setShopId(map.get("AT"));
            douyinOrderDO.setRefundSum(map.get("AV"));
            douyinOrderDO.setRefundApplySum(map.get("AW"));
            //
            douyinOrderDO.setPreferentSum(map.get("J"));
            douyinOrderDO.setPlaformCouponSum(map.get("K"));
            douyinOrderDO.setPlaformCouponName(map.get("L"));
            douyinOrderDO.setShopCouponSum(map.get("M"));
            douyinOrderDO.setShopCouponName(map.get("N"));
            douyinOrderDO.setIsAd(map.get("AQ"));
            douyinOrderDO.setAdId(map.get("AR"));
            douyinOrderDO.setAuthorId(map.get("AN"));
            orderDOList.add(douyinOrderDO);
        }
        return orderDOList;
    }

    public static List<AfterSaleVO> convertAfter(List<Map<String, String>> cells) {
        List<AfterSaleVO> afterSaleVOArrayList = new ArrayList<>();
        for (int j = 1; j < cells.size(); j++) {
            Map<String, String> map = cells.get(j);
            AfterSaleVO afterSaleVO = new AfterSaleVO();
            String expressId = map.get("Q");
            if (expressId == null || expressId.equals("") || expressId.equals(" ")) {
                //continue;
            }
            afterSaleVO.setAfterSaleId(map.get("A"));
            afterSaleVO.setOrderId(map.get("B"));
            String s=map.get("I");
            if(s.equals("未发货仅退款")){
                continue;
            }
            afterSaleVO.setAfterSaleType(s);

            afterSaleVO.setAfterSaleStatus(map.get("L"));
            afterSaleVO.setAfterSaleExpressId(map.get("Q").toUpperCase().trim());
            afterSaleVOArrayList.add(afterSaleVO);
        }
        return afterSaleVOArrayList;
    }

    public static List<CustomerVO> convertCustomer(List<Map<String, String>> cells) {
        List<CustomerVO> customerVOList = new ArrayList<>();
        for (int j = 1; j < cells.size(); j++) {
            Map<String, String> map = cells.get(j);
            String orderIds = map.get("B");
            if (orderIds.equals(" ")) {
                continue;
            }
            orderIds = orderIds.replace(" ", "");
            if (orderIds.charAt(orderIds.length() - 1) != 'A') {
                System.out.println(orderIds);
            }
            orderIds = orderIds.replace("A", "");

            String realUserName = map.get("F");
            String realPhone = map.get("G");
            if (realPhone.equals(" ")) {
                realPhone = map.get("H");
            }
            String realAddress = map.get("I") + map.get("J") + map.get("K") + map.get("L");
            CustomerVO customerVO = new CustomerVO();
            customerVO.setOrderId(orderIds);
            customerVO.setRealName(realUserName);
            customerVO.setUserPhone(realPhone);
            customerVO.setAddress(realAddress);
            customerVOList.add(customerVO);
        }
        return customerVOList;
    }

    public static void orderSynchronization(String path) throws IOException {
        //插入或更新订单表
        if (!FileIo.fileExists(path)) {
            System.out.println(path + "不存在");
            return;
        }
        List<Map<String, String>> cells = ReadExcelUtils.readSHash(path);
        try {
            DouyinOrderDao douyinOrderDao = new DouyinOrderDao(DefaultDatabaseConnect.getConn());
            List<DouyinOrderDO> orderDOList = OrderSynchronization.convert(cells);
            //douyinOrderDao.doBatchInsert(orderDOList);
            for (DouyinOrderDO vo : orderDOList) {
                try {
                    if(vo.getOrderId().equals("4743869757290423456")){
                        System.out.println("");
                    }
                    douyinOrderDao.doInsert(vo);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(vo.getOrderId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void orderStatusSynchronization(String path)throws IOException{
        //更新订单状态
        if (!FileIo.fileExists(path)) {
            System.out.println(path + "不存在");
            return;
        }
        List<Map<String, String>> cells = ReadExcelUtils.readSHash(path);
        try {
            DouyinOrderDao douyinOrderDao = new DouyinOrderDao(DefaultDatabaseConnect.getConn());
            List<DouyinOrderDO> orderDOList = OrderSynchronization.convertOrderStatus(cells);
            //douyinOrderDao.doBatchInsert(orderDOList);
            for (DouyinOrderDO vo : orderDOList) {
                try {
                    if(vo.getOrderId().equals("4743869757290423456")){
                        System.out.println("");
                    }
                    douyinOrderDao.updateOrderStatus(vo);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(vo.getOrderId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<DouyinOrderDO> convertOrderStatus(List<Map<String, String>> cells){
        List<DouyinOrderDO> douyinOrderDOList=new ArrayList<>();
        for (int j = 1; j < cells.size(); j++) {
            Map<String, String> map = cells.get(j);
            DouyinOrderDO douyinOrderDO = new DouyinOrderDO();
            String orderId = map.get("A").replace("'", "");
            douyinOrderDO.setOrderId(orderId);
            douyinOrderDO.setOrderStatus(map.get("B"));
            douyinOrderDO.setBusinessRemarks(map.get("C"));
            douyinOrderDOList.add(douyinOrderDO);
        }
        return  douyinOrderDOList;
    }
    public static void updateOrderMainId(String path) throws  IOException{
        if (!FileIo.fileExists(path)) {
            System.out.println(path + "不存在");
            return;
        }
        List<Map<String, String>> cells = ReadExcelUtils.readSHash(path);
        try {
            DouyinOrderDao douyinOrderDao = new DouyinOrderDao(DefaultDatabaseConnect.getConn());
            List<DouyinOrderDO> orderDOList = OrderSynchronization.convert(cells);
            //douyinOrderDao.doBatchInsert(orderDOList);
            for (DouyinOrderDO vo : orderDOList) {
                try {
                    if(vo.getOrderId().equals("4743869757290423456")){
                        System.out.println("");
                    }
                    douyinOrderDao.updateMainOrderId(vo.getOrderId(),vo.getMainOrderId());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(vo.getOrderId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateOrderStatus(String path) throws IOException {
        if (!FileIo.fileExists(path)) {
            System.out.println(path + "不存在");
            return;
        }
        List<Map<String, String>> cells = ReadExcelUtils.readSHash(path);
        try {
            DouyinOrderDao douyinOrderDao = new DouyinOrderDao(DefaultDatabaseConnect.getConn());
            List<DouyinOrderDO> orderDOList = OrderSynchronization.convertOrderStatus(cells);
            //douyinOrderDao.doBatchInsert(orderDOList);
            for (DouyinOrderDO vo : orderDOList) {
                try {
                    if(vo.getOrderId().equals("4743869757290423456")){
                        System.out.println("");
                    }
                    douyinOrderDao.updateMainOrderId(vo.getOrderId(),vo.getMainOrderId());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(vo.getOrderId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void updateAfterSale(String path) throws IOException {
        if (!FileIo.fileExists(path)) {
            System.out.println(path + "不存在");
            return;
        }
        //更新订单表售后状态
        List<Map<String, String>> cells = ReadExcelUtils.readSHash(path);
        try {
            DouyinOrderDao douyinOrderDao = new DouyinOrderDao(DefaultDatabaseConnect.getConn());
            List<AfterSaleVO> afterSaleDOList = OrderSynchronization.convertAfter(cells);
            //douyinOrderDao.batchUpdateAfterSale(afterSaleDOList);
            for (AfterSaleVO vo : afterSaleDOList) {
                if(vo.getAfterSaleExpressId()==null)
                    continue;
                else{
                    if (vo.getAfterSaleExpressId().equals("YT5158335761773")) {
                        System.out.println("");
                    }
                    if(!douyinOrderDao.updateAfterSale(vo)){
                        System.out.println(vo.getAfterSaleExpressId()+"插入失败");
                    }
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateCustomerInfo(String path) throws IOException {
        if(!FileIo.fileExists(path))
            return;
        List<Map<String, String>> cells = ReadExcelUtils.readSHash(path);
        try {
            String url = "jdbc:postgresql://127.0.0.1:5432/postgres?rewriteBatchedStatements=true";
            String className = "org.postgresql.Driver";
            String user = "postgres";
            String password = "332500asd";
            DatabaseConnection databaseConnection = new DatabaseConnection(url, className, user, password);
            DouyinOrderDao douyinOrderDao = new DouyinOrderDao(databaseConnection.getConn());
            List<CustomerVO> customerVOList = OrderSynchronization.convertCustomer(cells);
            //douyinOrderDao.batchUpdateAfterSale(afterSaleDOList);
            for (CustomerVO vo : customerVOList) {
                try {
                    douyinOrderDao.updateCustomerInfo(vo);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 通过备货表更新
    public static void updateOldStockCustomer(String path)throws  IOException{
        if(!FileIo.fileExists(path))
            return;
        try {
            List<Map<String, String>> cells = ReadExcelUtils.readSHash(path);
            List<CustomerVO> customerVOList=convertCustomers(cells);
            updateOrderCustomer(customerVOList);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(path);


    }
    public static List<CustomerVO> convertCustomers(List<Map<String, String>> cells){
        List<CustomerVO> customerVOList = new ArrayList<>();
        for (int j = 1; j < cells.size(); j++) {
            Map<String, String> map = cells.get(j);
            String orderIds = map.get("A");
            if (orderIds.equals(" ")) {
                continue;
            }
            orderIds = orderIds.replace("'", "");
            if (orderIds.charAt(orderIds.length() - 1) != 'A') {
                //System.out.println(orderIds);
            }
            CustomerVO customerVO=new CustomerVO();
            customerVO.setOrderId(orderIds);
            customerVO.setRealName(map.get("Q"));
            customerVO.setUserPhone(map.get("R"));
            String address=map.get("S")+map.get("T")+map.get("U")+map.get("V")+map.get("W");
            customerVO.setAddress(address);
            customerVOList.add(customerVO);
        }
        return customerVOList;
    }
    public static void updateNewStockCustomer(String path)throws  IOException{
        if(!FileIo.fileExists(path))
            return;
        List<Map<String, String>> cells = ReadExcelUtils.readSHash(path);
        List<CustomerVO> customerVOList=convertCustomerss(cells);
        updateOrderCustomer(customerVOList);
    }
    public static List<CustomerVO> convertCustomerss(List<Map<String, String>> cells){
        List<CustomerVO> customerVOList = new ArrayList<>();
        for (int j = 1; j < cells.size(); j++) {
            Map<String, String> map = cells.get(j);
            String orderIds = map.get("A");
            if (orderIds.equals(" ")) {
                continue;
            }
            orderIds = orderIds.replace("'", "");
            if (orderIds.charAt(orderIds.length() - 1) != 'A') {
                System.out.println(orderIds);
            }
            CustomerVO customerVO=new CustomerVO();
            customerVO.setOrderId(orderIds);
            customerVO.setRealName(map.get("R"));
            customerVO.setUserPhone(map.get("S"));
            String address=map.get("T")+map.get("U")+map.get("V")+map.get("W")+map.get("X");
            customerVO.setAddress(address);
            customerVOList.add(customerVO);
        }
        return customerVOList;
    }
    public static void  updateOrderCustomer(List<CustomerVO> customerVOList){
        try {
            DouyinOrderDao douyinOrderDao = new DouyinOrderDao(DefaultDatabaseConnect.getConn());
            for (CustomerVO vo : customerVOList) {
                try {
                    douyinOrderDao.updateCustomerInfo(vo);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
