package com.auto.modal.douyin.sendAdvice;

import com.auto.dao.DefaultDatabaseConnect;
import com.auto.dao.handle.ExpressHDao;
import com.auto.modal.excel.WriteExcelUtils;
import com.auto.modal.vo.SenderVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname GenarateSenAdvice
 * @Description 生成小店发货订单  - 详细表 - 发货型号表 退货与拦截型号表
 * @Date 2021/1/31 15:38
 * @Created by Administrator
 */
public class GenerateSenAdvice {
    public static void main(String[] args) throws Exception {
        Map<String,Double> goodsCostMap=setProductList();
        Map<String,Integer> map =new HashMap<>();
        ExpressHDao expressHDao =new ExpressHDao(DefaultDatabaseConnect.getConn());
        List<SenderVO> senderVOList = expressHDao.findShopOrderId("大惠仓");
        List<SenderVO> senderVOLists=expressHDao.findShopOrder("大惠仓");
        //senderVOLists.clear();
        for(SenderVO vo:senderVOLists){
            map.put(vo.getOrderId(),1);
        }
        List<SenderVO> senderVOS=new ArrayList<>();
        for(SenderVO vo:senderVOList){
            if(!map.containsKey(vo.getOrderId())){
                vo.setFare("2.5");
                try {
                    Double d=goodsCostMap.get(vo.getGoodsCode());
                    d=d*Integer.valueOf(vo.getGoodsNumber());
                    Double total=d+2.5;
                    vo.setTotalCost(String.valueOf(total));
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }

                vo.setGoodsCost(String.valueOf(goodsCostMap.get(vo.getGoodsCode())));

                senderVOS.add(vo);
            }
        }
        List<HashMap<Integer, String>> cellList= new ArrayList<>();
        HashMap<Integer, String> map1=new HashMap<>();
        int i=0;
        map1.put(i++,"快递单号");
        map1.put(i++,"发货时间");
        map1.put(i++,"订单编号");
        map1.put(i++,"商品数量");
        map1.put(i++,"收件人");
        map1.put(i++,"收件人手机");
        map1.put(i++,"收件人地址");
        map1.put(i++,"商品型号");
        map1.put(i++,"拿货价格");
        map1.put(i++,"快递费用");
        map1.put(i++,"总成本");
        cellList.add(map1);
        for(SenderVO vo:senderVOS){
             i=0;
            map1=new HashMap<>();
            map1.put(i++,vo.getExpressId());
            map1.put(i++,vo.getExpressPrintTime());
            map1.put(i++,vo.getOrderId());
            map1.put(i++,vo.getGoodsNumber());
            map1.put(i++,vo.getRecipient());
            map1.put(i++,vo.getRecipientPhone());
            map1.put(i++,vo.getRecipientAddress());
            map1.put(i++,vo.getGoodsCode());
            map1.put(i++,vo.getGoodsCost());
            map1.put(i++,vo.getFare());
            map1.put(i++,vo.getTotalCost());
            cellList.add(map1);
        }
        String saveExcelFilePath = "D:\\zili\\data\\douyinData\\抖音\\财务数据\\大惠仓发货单.xlsx";
        WriteExcelUtils obj = new WriteExcelUtils(saveExcelFilePath);
        obj.writeHashExcel(cellList);

    }
    public static Map<String,Double> setProductList() {
        Map<String,Double>  map = new HashMap<>();
        //02/24 新增
        map.put("Y301", 45.00);
        map.put("Y302", 45.00);
        map.put("Y303", 45.00);
        map.put("Y305", 45.00);
        map.put("Y306", 53.00);
        map.put("Y307", 53.00);
        map.put("Y309", 40.00);
        map.put("Y310", 35.00);
        map.put("K520", 45.00);
        map.put("K521", 45.00);
        map.put("M668", 50.00);

        //10/28 新增
        map.put("GJ06", 38.00);

        map.put("GJ07", 43.00);

        map.put("GJ10", 50.00);
        map.put("GJ11", 45.00);
        map.put("GJK01", 35.00);
        map.put("GJK02", 35.00);
        map.put("GJK201", 30.00);
        map.put("GJ15", 18.00);



        map.put("GJK05", 55.00);

        map.put("GJK06", 50.00);

        map.put("2103", 50.00);


        //
        map.put("2110", 50.00);
        map.put("2113", 55.00);

        map.put("8813", 30.00);

        map.put("8980", 45.00);

        map.put("9810", 45.00);

        map.put("MH888", 50.00);

        map.put("GJ12", 45.00);

        map.put("GJ02", 33.00);

        map.put("GJ03", 38.00);
        return map;
    }
}
