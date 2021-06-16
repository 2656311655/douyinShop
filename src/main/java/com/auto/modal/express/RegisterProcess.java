package com.auto.modal.express;

import com.auto.dao.DefaultDatabaseConnect;
import com.auto.dao.OrderDao;
import com.auto.modal.excel.ReadExcelUtils;
import com.auto.modal.excel.WriteExcelUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname AutoScreen
 * @Description 寻找快递丢件记录
 * @Date 2020/12/5 21:11
 * @Created by Administrator
 */
public class RegisterProcess {
    public static void main(String[] args) throws Exception {

        test1();
    }


    public static void test() throws Exception {
        //快递员丢件记录
        String path = "D:\\zili\\data\\douyinData\\抖音\\登记表\\圆通拦截记录-谢东东.xlsx";
        List<Map<String, String>> cells1 = ReadExcelUtils.readSHash(path);
        HashMap<String, Integer> expressMap = new HashMap<>();
        OrderDao orderDao = new OrderDao(DefaultDatabaseConnect.getConn());
        List<String> express1 = orderDao.findExpress();
        for (String s : express1) {
            expressMap.put(s, 1);
        }
        List<HashMap<Integer, String>> sList = new ArrayList<>();
        for (int j = 0; j < cells1.size(); j++) {
            Map<String, String> map = cells1.get(j);
            String express = map.get("A").trim();
            express = express.trim().toUpperCase();

            String s = map.get("E").trim();
            if (express.equals(" ")) {
                continue;
            }
            if (s.equals("已退回网点")) {
                if (expressMap.containsKey(express))
                    continue;
            }

            HashMap<Integer, String> map1 = new HashMap<>();
            map1.put(0, map.get("A").trim());
            map1.put(1, map.get("B").trim());
            map1.put(2, map.get("C").trim());
            map1.put(3, map.get("D").trim());
            map1.put(4, map.get("E").trim());
            map1.put(5, map.get("F").trim());
            map1.put(6, map.get("G").trim());
            map1.put(7, map.get("H").trim());
            map1.put(8, map.get("I").trim());
            map1.put(9, map.get("J").trim());
            map1.put(10, map.get("K").trim());
            sList.add(map1);
        }
        String saveExcelFilePath = "D:\\zili\\data\\douyinData\\抖音\\登记表\\圆通快递员丢件记录.xlsx";
        WriteExcelUtils obj = new WriteExcelUtils(saveExcelFilePath);
        obj.writeHashExcel(sList);
    }

    public static void test1() throws Exception {
        //快递员丢件记录
        String path = "D:\\zili\\data\\douyinData\\抖音\\登记表\\圆通拦截记录-谢东东.xlsx";
        List<Map<String, String>> cells1 = ReadExcelUtils.readSHash(path);
        HashMap<String, Integer> expressMap = new HashMap<>();
        OrderDao orderDao = new OrderDao(DefaultDatabaseConnect.getConn());
        List<String> express1 = orderDao.findExpress();
        for (String s : express1) {
            expressMap.put(s, 1);
        }
        List<HashMap<Integer, String>> sList = new ArrayList<>();
        for (int j = 1; j < cells1.size(); j++) {
            Map<String, String> map = cells1.get(j);
            String express = map.get("A").trim();
            express = express.trim().toUpperCase();
            if (expressMap.containsKey(express))
                    continue;
            HashMap<Integer, String> map1 = new HashMap<>();
            map1.put(0, map.get("A").trim());
            map1.put(1, map.get("B").trim());
            map1.put(2, map.get("C").trim());
            map1.put(3, "丢件");
            sList.add(map1);
        }
        String saveExcelFilePath = "D:\\zili\\data\\douyinData\\抖音\\登记表\\圆通快递员丢件记录.xlsx";
        WriteExcelUtils obj = new WriteExcelUtils(saveExcelFilePath);
        obj.writeHashExcel(sList);
    }

}
