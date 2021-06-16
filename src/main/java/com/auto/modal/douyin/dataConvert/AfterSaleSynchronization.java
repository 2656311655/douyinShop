package com.auto.modal.douyin.dataConvert;

import com.auto.dao.*;
import com.auto.modal.excel.ReadExcelUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Classname DouYinAfterSaleToDatabase
 * @Description 抖音售后表转数据库
 * @Date 2020/11/9 20:28
 * @Created by Administrator
 */
public class AfterSaleSynchronization {
    public static void main(String[] args) throws Exception {

    }

    public static void AfterSaleUpdate(String path) throws IOException {
        List<Map<String, String>> cells = ReadExcelUtils.readSHash(path);
        try {
            DouyinAfterSaleDao douyinAfterSaleDao = new DouyinAfterSaleDao(DefaultDatabaseConnect.getConn());
            List<DouyinAfterSaleDO> afterSaleDOS = AfterSaleSynchronization.convertAfter(cells);
            for (DouyinAfterSaleDO vo : afterSaleDOS) {
                douyinAfterSaleDao.doInsert(vo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<DouyinAfterSaleDO> convertAfter(List<Map<String, String>> cells) {
        List<DouyinAfterSaleDO> afterSaleVOArrayList = new ArrayList<>();
        for (int j = 1; j < cells.size(); j++) {
            Map<String, String> map = cells.get(j);
            DouyinAfterSaleDO afterSaleVO = new DouyinAfterSaleDO();
            afterSaleVO.setAfterSaleId(map.get("A"));
            afterSaleVO.setOrderId(map.get("B"));
            afterSaleVO.setGoodsNumber(map.get("C"));
            afterSaleVO.setGoodsName(map.get("D"));
            afterSaleVO.setGoodsId(map.get("E"));
            afterSaleVO.setActualCheque(map.get("F"));
            afterSaleVO.setFare(map.get("G"));
            afterSaleVO.setGoodsStatus(map.get("H"));
            afterSaleVO.setAfterSaleType(map.get("I"));
            afterSaleVO.setRefundGoodsSum(map.get("J"));
            afterSaleVO.setRefundFareSum(map.get("K"));
            afterSaleVO.setAfterSaleStatus(map.get("L"));
            afterSaleVO.setAfterApplyTime(map.get("M"));
            afterSaleVO.setRefundWay(map.get("N"));
            afterSaleVO.setRefundVoucher(map.get("O"));
            afterSaleVO.setRefundReason(map.get("P"));
            afterSaleVO.setRefundExpressId(map.get("Q"));
            afterSaleVO.setRefundSendTime(map.get("R"));
            afterSaleVO.setRefundExpressCompany(map.get("S"));
            afterSaleVO.setBuyerName(map.get("T"));
            afterSaleVO.setAutoApplyFinalTime(map.get("U"));
            afterSaleVO.setAgreeAfterSaleApplyTime(map.get("V"));
            afterSaleVO.setRefundSusTime(map.get("W"));
            afterSaleVO.setAfterSaleCloseTime(map.get("X"));
            afterSaleVO.setBusinessRefundAddress(map.get("Y"));
            afterSaleVO.setBusinessRefundPhone(map.get("AA"));
            afterSaleVO.setHistoryRecord(map.get("AB"));
            afterSaleVOArrayList.add(afterSaleVO);
        }
        return afterSaleVOArrayList;
    }
}
