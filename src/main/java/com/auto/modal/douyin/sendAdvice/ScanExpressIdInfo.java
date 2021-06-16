package com.auto.modal.douyin.sendAdvice;

import com.auto.dao.DatabaseConnection;
import com.auto.dao.ExpressPrintDO;
import com.auto.dao.ExpressPrintDao;
import com.auto.modal.excel.WriteExcelUtils;
import com.auto.modal.vo.GoodsInfoVO;
import com.auto.modal.vo.SendAdviceVO;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname ScanExpressIdInfo
 * @Description 扫描快递单内容
 * @Date 2020/10/23 20:19
 * @Created by Administrator
 */
public class ScanExpressIdInfo {
    private static String REGEX_CHINESE = "[\u4e00-\u9fa5]";
    public static void main(String[] args) throws Exception {
        String path = "D:\\zili\\data\\douyinData\\抖音\\供应商\\备货单\\扫单.txt";
        List<String> expressList = getExpressList(path);

        List<GoodsInfoVO> goodsInfoVOS = getGoodsInfoList(expressList, Config.goodsRemark);
        //HashMap<String,Integer>  goodsMaps=new HashMap<>();
        saveExcelFile(reviseRemark(goodsInfoVOS));
    }
    public static  void saveExcelFile(HashMap<String, Integer> map) throws Exception {
        ArrayList<SendAdviceVO> sendAdviceVOArrayList = new ArrayList<>();
        int sum=0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            String[] keys = key.split(" ");
            Integer value = entry.getValue();
            if (value < 0) {
                System.out.println("");
            }
            sum = sum + value;
            SendAdviceVO vo = new SendAdviceVO();
            try {
                vo.setGoodsCode(keys[0]);
                vo.setGoodsColor(keys[1]);
                vo.setGoodsSize(keys[2]);
            } catch (Exception e) {
                continue;
            }
            vo.setGoodsNumber(value);
            sendAdviceVOArrayList.add(vo);
        }
        System.out.println(sum);
        sum = 0;
        for (SendAdviceVO vo : sendAdviceVOArrayList) {
            sum = sum + vo.getGoodsNumber();
        }
        System.out.println(sum);
        List<HashMap<Integer, String>> addCellList = new ArrayList<>();
        addCellList = ExportSendAdviceExcelDou.exportSendAdviceExcel(sendAdviceVOArrayList);
        List<HashMap<Integer, String>> sList = ExportSendAdviceExcelDou.sortCellList(addCellList);
        String saveExcelFilePath = "D:\\zili\\data\\douyinData\\抖音\\供应商\\报货单\\扫单.xlsx";
        WriteExcelUtils obj = new WriteExcelUtils(saveExcelFilePath);
        obj.writeHashExcel(sList);
    }
    public static List<GoodsInfoVO> getGoodsInfoList(List<String> expressList, GoodsRemark goodsRemark) {
        List<GoodsInfoVO> goodsInfoVOList = new ArrayList<>();
        try {
            String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
            String className = "org.postgresql.Driver";
            String user = "postgres";
            String password = "332500asd";
            DatabaseConnection databaseConnection = new DatabaseConnection(url, className, user, password);
            ExpressPrintDao expressDao = new ExpressPrintDao(databaseConnection.getConn());
            for (String s : expressList) {
                ExpressPrintDO vo = new ExpressPrintDO();
                if(s.trim().equals("")){
                    continue;
                }
                vo.setExpressId(s);
                expressDao.find(vo);
                if(vo.getGoodsCode()==null){
                    System.out.println("");
                    continue;
                }
                GoodsInfoVO goodsInfoVO=getGoodsInfo(vo);

                goodsInfoVOList.add(goodsInfoVO);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return goodsInfoVOList;
    }
    public static GoodsInfoVO getGoodsInfo(ExpressPrintDO vo){
        String goodsCode="";
        String goodsColor="";
        String goodsSize="";
        GoodsInfoVO goodsInfoVO=new GoodsInfoVO();
        // goodsCode
        if(vo.getGoodsCode()==null)
            return goodsInfoVO;
        goodsCode = GoodsRemark.getMaxSubString(vo.getGoodsCode().trim().toUpperCase(), Config.goodsRemark.clothesSkuList.toString());
        if(!Config.goodsRemark.clothesSkuList.contains(goodsCode)) {
            if(vo.getGoodsName()==null){

            }
            else{
                if (vo.getGoodsName().equals("")) {
                    System.out.println("");

                } else {
                    goodsCode = GoodsRemark.getMaxSubString(vo.getGoodsName().toUpperCase(), Config.goodsRemark.clothesSkuList.toString());
                    if (goodsCode.equals("")) {

                    }
                }
            }
        }
        else{

            String s2=vo.getGoodsCode();
            s2=s2.replace(goodsCode,"");
            s2=s2.replaceAll(",","");
            goodsColor=GoodsRemark.getMaxSubString(s2,Config.goodsRemark.clothesColorList.toString());
            goodsColor=goodsColor.replaceAll(",","");
            s2=s2.replace(goodsColor,"");
            Pattern pat = Pattern.compile(REGEX_CHINESE);
            Matcher mat = pat.matcher(s2);
            s2=mat.replaceAll("");
            s2=s2.replace("：","").trim();
            s2=s2.replace(":","");
            String s1=Config.goodsRemark.clothesSizeList.toString();
            goodsSize=GoodsRemark.getMaxSubString(s2,s1);
        }
        //goodsSku
        if(!vo.getGoodsSku().equals(" ")){
            String s2=vo.getGoodsSku().toUpperCase().trim();
            s2=s2.replaceAll(",","");
            goodsColor=GoodsRemark.getMaxSubString(s2,Config.goodsRemark.clothesColorList.toString());
            s2=s2.replace(goodsColor,"");
            Pattern pat = Pattern.compile(REGEX_CHINESE);
            Matcher mat = pat.matcher(s2);
            s2=mat.replaceAll("");
            s2=s2.replace("：","").trim();
            s2=s2.replace(":","");
            String s1=Config.goodsRemark.clothesSizeList.toString();
            goodsSize=GoodsRemark.getMaxSubString(s2,s1);
        }
        //goodsCode
        goodsInfoVO.setExpressId(vo.getExpressId());
        goodsInfoVO.setGoodsName(vo.getGoodsName());
        goodsInfoVO.setGoodsCode(goodsCode);
        goodsInfoVO.setGoodsRemark(vo.getExpressRemark());
        goodsInfoVO.setGoodsSku(goodsColor+";"+goodsSize);
        return goodsInfoVO;
    }
    public static List<String> getExpressList(String path) {
        List<String> result = new ArrayList<>();
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            File file = new File(path);
            InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            BufferedReader bf = new BufferedReader(inputReader);
            // 按行读取字符串
            String str;

            while ((str = bf.readLine()) != null) {
                result.add(str);
            }
            bf.close();
            inputReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static HashMap<String, Integer> reviseRemark(List<GoodsInfoVO> goodsInfoVOS) {

        //备注

        List<SendAdviceVO> sendAdviceVOList = new ArrayList<>();
        HashMap<String, Integer> goodsMap = new HashMap<>();
        for (GoodsInfoVO goodsInfoVO : goodsInfoVOS) {
            String goodsCode=goodsInfoVO.getGoodsCode();
            String goodsColor="";
            String goodsSize="";
            try {

                String[] goodsSkus = goodsInfoVO.getGoodsSku().split(";");
                if (goodsSkus.length == 2) {
                    goodsColor = goodsSkus[0];
                    goodsSize = goodsSkus[1];
                } else {
                    System.out.println("");
                    continue;
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            String key = goodsCode + " " + goodsColor + " " + goodsSize;
            if (goodsMap.containsKey(key)) {
                Integer i = goodsMap.get(key);
                goodsMap.put(key, i + 1);
            } else {
                goodsMap.put(key, 1);
            }


        }
        System.out.println("11");
        int sum = 0;
        for (Map.Entry<String, Integer> entry : goodsMap.entrySet()) {
            sum = sum + entry.getValue();
        }
        System.out.println(sum);
        return goodsMap;
    }


}
