package com.auto.modal.express;

import com.auto.modal.excel.ReadExcelUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname ExpressCompare
 * @Description
 * @Date 2021/6/16 13:53
 * @Created by Administrator
 */
public class ExpressCompare {
    public static void main(String[] args) throws Exception {
        String excelFilePath="D:\\1.xlsx";
        List<HashMap<Integer, String>> cellList = ReadExcelUtils.readHash(excelFilePath);
        HashMap<String ,Integer> expressMap=new HashMap<>();
        HashMap<String ,Integer> expressMaps=new HashMap<>();
        for (int k = 1; k < cellList.size(); k++) {
            HashMap<Integer, String> map = cellList.get(k);
            String expressId=   map.get(0);
            expressId=expressId.replaceAll(" ","");
            expressMap.put(expressId,0);

            String express=   map.get(1);

            try {
                express=express.replaceAll(" ","");
            }
            catch (Exception ex){
                ex.printStackTrace();
            }

            if(!express.equals("")){
                if(expressMaps.containsKey(express)){
                    System.out.println(express);
                }
            }
                expressMaps.put(express,0);
        }
        int i=0;
        for (Map.Entry<String, Integer> entry : expressMaps.entrySet()) {

            String key=entry.getKey();
            if(key.equals("YT5515822130655")){
                System.out.println("");
            }
            if(!expressMap.containsKey(key)){
                System.out.println(key);
                i++;
            }
        }
        System.out.println(i);
    }
}
