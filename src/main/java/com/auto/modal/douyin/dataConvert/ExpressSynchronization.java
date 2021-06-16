package com.auto.modal.douyin.dataConvert;

import com.auto.dao.*;
import com.auto.modal.config.Config;
import com.auto.modal.io.FileIo;
import com.auto.modal.excel.ReadExcelUtils;
import com.auto.modal.vo.OrderExpressIdVO;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname DouYinExpressIdToDatabase
 * @Description 灵通打单转数据库
 * @Date 2020/10/18 13:23
 * @Created by Administrator
 */
public class ExpressSynchronization {
    public static void main(String[] args) throws Exception {
        ExpressSynchronization.expressInfoUpdate("D:\\zili\\data\\douyinData\\抖音\\数据同步表\\物流单\\灵通物流单-12月14号前.xlsx");
    }

    public static void expressInfoUpdate(String path) {
        try {
            ExpressPrintDao printDao = new ExpressPrintDao(DefaultDatabaseConnect.getConn());
            ExpressPackageDao packageDao=new ExpressPackageDao(DefaultDatabaseConnect.getConn());
            if (!FileIo.fileExists(path)) {
                System.out.println(path + "不存在");
            }
            else {
                List<Map<String, String>> cells = ReadExcelUtils.readSHash(path);
                List<ExpressPrintDO> expressPrintDOS = convert(cells);
                for (ExpressPrintDO vo : expressPrintDOS) {
                    try {
                        if(vo.getExpressId().equals("YT5127141882355")){
                            System.out.println("");
                        }
                        printDao.doInsert(vo);
                    } catch (Exception e) {
                        //e.printStackTrace();
                        Config.logger.warn(vo.getExpressId() + "同步失败");
                    }
                }
            }
            /*
             */
            DouyinOrderDao douyinOrderDao = new DouyinOrderDao(DefaultDatabaseConnect.getConn());
            List<ExpressPackageDO> expressPackageDOS  =douyinOrderDao.findAllExpress("2020-12-01");
            for(ExpressPackageDO vo:expressPackageDOS){
                if(vo.getExpressId().equals("YT5117170496765")){
                    System.out.println("");
                }
                try {
                    packageDao.doUpdate(vo);
                }
                catch (SQLException e1){
                    e1.printStackTrace();
                    Config.logger.warn(vo.getExpressId() + "更新失败");
                }


            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();

        }

    }
    public static void updateExpressPackage(ExpressPackageDO vo){
        try {
            DouyinOrderDao douyinOrderDao = new DouyinOrderDao(DefaultDatabaseConnect.getConn());
            //douyinOrderDao.fin
        }
        catch (SQLException e ){
            e.printStackTrace();
        }

    }
    public static List<ExpressPrintDO> convert(List<Map<String, String>> cells) {
        List<ExpressPrintDO> result = new ArrayList<>();
        String oldOrder="";
        String oGoodsName="";
        String oGoodsSku="";
        String oGoodsCode="";
        for (int j = 1; j < cells.size(); j++) {
            Map<String, String> map = cells.get(j);


            String orderId = map.get("B").replace("A", "");
            if(orderId.equals(" ")){
                orderId=orderId.replace(" ", "");
                ExpressPrintDO vo=result.get(result.size()-1);
                String s=map.get("Q");
                vo.setGoodsName(vo.getGoodsName()+";"+s);
                s=map.get("R");
                vo.setGoodsSku(vo.getGoodsSku()+";"+s);
                s=map.get("S");
                vo.setGoodsCode(vo.getGoodsCode()+";"+s);
                continue;
            }
            ExpressPrintDO vo = new ExpressPrintDO();
            vo.setExpressPrintTime(map.get("A"));
            orderId=orderId.replace(" ", "");
            vo.setOrderId(orderId);
            vo.setDouyinShop(map.get("C"));
            vo.setSender(map.get("D"));
            vo.setSenderAddress(map.get("E"));
            vo.setRecipient(map.get("F"));
            String phone = map.get("G");
            if (phone.equals(" ")) {
                phone = map.get("H");
            }
            vo.setRecipientPhone(phone);
            vo.setSenderAddress(map.get("I") + map.get("J") + map.get("K") + map.get("L"));
            String expressId=map.get("N").replaceAll(" ","");
            vo.setExpressId(expressId);
            vo.setWeight(map.get("O"));
            vo.setFare(map.get("P"));
            vo.setGoodsName(map.get("Q"));
            vo.setGoodsSku(map.get("R"));
            vo.setGoodsCode(map.get("S"));
            vo.setGoodsNumber(map.get("T"));
            vo.setGoodsSum(map.get("V"));
            vo.setExpressStatus(map.get("W"));
            vo.setExpressRemark(map.get("X"));
            vo.setExpressPrintTime(map.get("A"));
            vo.setExpressPrintTime(map.get("A"));
            vo.setExpressPlatform("灵通");
            vo.setOrderStatus("");
            oldOrder=orderId;
            result.add(vo);

        }
        return result;
    }

    public static Boolean convertTest() {
        try {
            String url = "jdbc:postgresql://127.0.0.1:5432/postgres?rewriteBatchedStatements=true";
            String className = "org.postgresql.Driver";
            String user = "postgres";
            String password = "332500asd";
            DatabaseConnection databaseConnection = new DatabaseConnection(url, className, user, password);
            DouyinOrderDao douyinOrderDao = new DouyinOrderDao(databaseConnection.getConn());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<ExpressDO> convertTes(List<HashMap<Integer, String>> cells) throws ParseException, IOException {
        String path = "D:\\zili\\data\\douyinData\\冠军贵宾\\数据\\抖音\\打印快递面单\\物流打印记录临时.xlsx";
        cells = ReadExcelUtils.readHash(path);
        try {
            String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
            String className = "org.postgresql.Driver";
            String user = "postgres";
            String password = "332500asd";
            DatabaseConnection databaseConnection = new DatabaseConnection(url, className, user, password);
            ExpressDao expressDao = new ExpressDao(databaseConnection.getConn());
            List<ExpressDO> expressDOList = convertTes(cells);
            for (ExpressDO vo : expressDOList) {
                expressDao.doInsert(vo);
            }
            List<ExpressDO> expressDO = new ArrayList<>();
            expressDao.findGoodsInfo(expressDO, "4308186469624");
            //System.out.println(expressDO.get(0).getGoodsCode());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<ExpressDO> expressDOList = new ArrayList<>();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

        int j = 1;
        while (j + 1 < cells.size()) {
            HashMap<Integer, String> map = cells.get(j);
            String orderId = map.get(1);
            String[] orderIds = orderId.split(",");
            List<OrderExpressIdVO> orderExpressIdVOS = new ArrayList<>();
            ExpressDO expressDO = new ExpressDO();
            int i = 0;
            expressDO.setExpressPrintTime(map.get(i++));
            expressDO.setOrderId(map.get(i++));
            expressDO.setDouYinShop(map.get(i++));
            expressDO.setSender(map.get(i++));
            expressDO.setSenderAddress(map.get(i++));
            expressDO.setRecipient(map.get(i++));
            expressDO.setRecipientPhone(map.get(i++));
            i++;
            String address = map.get(i++) + map.get(i++) + map.get(i++) + map.get(i++);
            expressDO.setRecipientAddress(address);
            i++;
            String expressId = map.get(i++).trim();
            expressId = expressId.replaceAll(" ", "");
            expressDO.setExpressId(expressId);
            expressDO.setWeight(map.get(i++));
            expressDO.setFare(map.get(i++));
            String s1 = map.get(21);
            if (s1 == null) {
                System.out.println("");
            }
            if (s1.equals(" ")) {
                s1 = "0f";
            }
            Float f1 = 0f;
            try {
                f1 = Float.parseFloat(s1);
            } catch (Exception e) {
                e.printStackTrace();
            }

            List<String> orderList = new ArrayList<>();

            if (orderIds.length == 1) {
                orderList.add(orderIds[0]);
                int k = j + 1;
                while (k < cells.size()) {

                    String tOrderId = cells.get(k++).get(1);
                    if (!tOrderId.equals(" ")) {
                        break;
                    } else {
                        String orderId0 = orderIds[0];
                        orderList.add(orderId0);
                    }
                }

            } else {
                for (String s : orderIds) {
                    orderList.add(s);
                }
            }

            for (String s : orderList) {
                OrderExpressIdVO orderExpressIdVO = new OrderExpressIdVO();
                orderExpressIdVO.setOrderId(s);
                i = 16;
                orderExpressIdVO.setGoodsName(map.get(i++));
                orderExpressIdVO.setGoodsSku(map.get(i++));
                orderExpressIdVO.setGoodsCode(map.get(i++));
                orderExpressIdVO.setGoodsCount(map.get(i++));
                String s2 = map.get(i++);
                Float f = 0f;
                if (!s2.equals(" ")) {
                    f = Float.parseFloat(s2);
                }
                f1 = f1 - f;
                orderExpressIdVO.setGoodsSum(f);
                i++;
                orderExpressIdVO.setExpressStatus(map.get(i++));
                orderExpressIdVO.setExpressRemark(map.get(i++));
                orderExpressIdVOS.add(orderExpressIdVO);
                try {
                    if (j + 1 < cells.size())
                        map = cells.get(++j);
                    else
                        break;


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            orderExpressIdVOS.get(0).setGoodsSum(f1);
            for (OrderExpressIdVO vo : orderExpressIdVOS) {
                ExpressDO nExpressDO = new ExpressDO();
                try {
                    //org.apache.commons.beanutils.BeanUtils.copyProperties(nExpressDO, expressDO);
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
                nExpressDO.setExpressId(expressId);
                nExpressDO.setOrderId(vo.getOrderId());
                nExpressDO.setGoodsName(vo.getGoodsName());
                nExpressDO.setGoodsSku(vo.getGoodsSku());
                nExpressDO.setGoodsCode(vo.getGoodsCode());
                nExpressDO.setGoodsNumber(vo.getGoodsCount());
                nExpressDO.setGoodsSum(String.valueOf(vo.getGoodsSum()));
                nExpressDO.setExpressStatus(vo.getExpressStatus());
                nExpressDO.setExpressRemark(vo.getExpressRemark());
                expressDOList.add(nExpressDO);
            }
        }
        return expressDOList;
    }
}
