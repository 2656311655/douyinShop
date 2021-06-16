package com.auto.modal.douyin.product;

import java.util.HashMap;

/**
 * @Classname ProductColorCode
 * @Description 获取产品颜色代码
 * @Date 2021/1/4 17:23
 * @Created by Administrator
 */
public class ProductColorCode {
    private HashMap<String,String> clothesColorList; //服装颜色表；
    public static void main(String[] args) throws Exception {

    }
    ProductColorCode(String[] colors){
        init(colors);
    }
    public void init(String[] colors){
        String a="AA";
        for(String color:colors){
            clothesColorList.put(color,a);
            a=createNext(a);
        }
    }
    public String getColorCode(String color){
        return clothesColorList.get(color);
    }
    public String createNext(String s){
        char [] tempChar = s.toCharArray();
        for(int i =tempChar.length-1;i>=1;i--){
            if (tempChar[i]<'z' ){
                tempChar[i]=(char)(tempChar[i]+1);
                break;
            }else {
                tempChar[i]='a';
                tempChar[i-1]=(char)(tempChar[i-1]+1);
                if (tempChar[i-1]<='z'){
                    i=0;
                }
            }
        }
        return String.valueOf(tempChar);
    }
}
