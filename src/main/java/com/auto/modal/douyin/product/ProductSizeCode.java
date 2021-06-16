package com.auto.modal.douyin.product;

import java.util.HashMap;

/**
 * @Classname ProductSizeCode
 * @Description TODO
 * @Date 2021/1/4 17:38
 * @Created by Administrator
 */
public class ProductSizeCode {
    public static void main(String[] args) throws Exception {

    }
    private HashMap<String,String> clothesSizeList; //服装颜色表；
    ProductSizeCode(){
        init();
    }


    public void init(){
        clothesSizeList=new HashMap<>();
        clothesSizeList.put("XS","01");
        clothesSizeList.put("S","02");
        clothesSizeList.put("M","03");
        clothesSizeList.put("L","04");
        clothesSizeList.put("XL","05");
        clothesSizeList.put("2XL","06");
        clothesSizeList.put("3XL","07");
        clothesSizeList.put("4XL","08");
        clothesSizeList.put("5XL","09");
        clothesSizeList.put("6XL","10");
        clothesSizeList.put("7XL","11");
    }
    public String getSizeCode(String size){
        return clothesSizeList.get(size);
    }
}
