package com.auto.modal.douyin.dataConvert;

import com.auto.modal.excel.WriteExcelUtils;
import com.auto.modal.io.FileIo;
import com.auto.modal.vo.DouYinUserVO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Classname AnalysisData
 * @Description 解析数据
 * @Date 2021/4/17 20:46
 * @Created by Administrator
 */
public class AnalysisData {
    public static void main(String[] args) throws Exception {
        String xpath="E:\\data\\数据\\抖音\\抖音测试数据\\实时.txt";
        String s=FileIo.readTxtFile(xpath,"utf-8");
        List<DouYinUserVO> list= getUserIds(s);
        List<HashMap<Integer, String>> sList = new ArrayList<>();
        HashMap<Integer, String>  map=new HashMap<>();
        int i=0;
        map.put(i++,"用户唯一id");
        map.put(i++,"用户昵称");
        map.put(i++,"用户评论");
        map.put(i++,"进入时间");
        map.put(i++,"抖音号");
        sList.add(map);
        for(DouYinUserVO vo: list){
            i=0;
            map=new HashMap<>();
            map.put(i++,vo.getUser_id());
            map.put(i++,vo.getNickname());
            map.put(i++,vo.getContent());
            map.put(i++,vo.getFormat_time());
            sList.add(map);
        }
        String saveExcelFilePath = "E:\\data\\数据\\抖音\\抖音账号\\2021-04-18.xlsx";
        WriteExcelUtils obj = new WriteExcelUtils(saveExcelFilePath);
        obj.writeHashExcel(sList);

    }
    public static List<DouYinUserVO> getUserIds(String s){
        ArrayList<DouYinUserVO> userBeanList = new ArrayList<>();
        String[] ss = s.split("\n");
        String temp="\"list\":";
        System.out.println(temp.length());
        for (String s1:ss) {
            if(s1.length()<18)
                continue;
            temp=s1.substring(0,18);
            if(temp.equals("{\"data\":{\"cursor\":")){
                int i=s1.indexOf("\"list\":");
                int j=s1.indexOf("},\"errCode\":0}");
                String s2=s1.substring(i+7,j);


                //Json的解析类对象
                JsonParser parser = new JsonParser();
                //将JSON的String 转成一个JsonArray对象
                JsonArray jsonArray = parser.parse(s2).getAsJsonArray();

                Gson gson = new Gson();


                //加强for循环遍历JsonArray
                for (JsonElement user : jsonArray) {
                    //使用GSON，直接转成Bean对象
                    DouYinUserVO userBean = gson.fromJson(user, DouYinUserVO.class);
                    userBeanList.add(userBean);
                }

                System.out.println(s2);
            }
        }
        return userBeanList;
    }
}
