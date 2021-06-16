package com.auto.modal.douyin.sendAdvice;


import com.auto.modal.vo.SendAdviceVO;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname GoodsRemark 备注修改商品SKU。
 * @Description TODO
 * @Date 2020/9/5 17:29
 * @Created by Administrator
 */
public class GoodsRemark {
    public Set<String> clothesColorList; //服装颜色表；
    public Set<String> clothesSizeList; //服装尺码表;
    public Set<String> clothesSkuList; //服装型号表;
    public static void main(String[] args) throws Exception {
    }

    public GoodsRemark() {
        this.clothesColorList = new HashSet<>();
        this.clothesSizeList = new HashSet<>();
        this.clothesSkuList = new HashSet<>();
        addClothesColorList(clothesColorList);
        addClothesSizeList(clothesSizeList);
        addClothesSkuList(clothesSkuList);
    }

    public static Matcher regex(String content, String regex, int flags) {
        Pattern pattern = Pattern.compile(regex, flags);
        Matcher m = pattern.matcher(content);
        //boolean f= m.matches(); //精准匹配
        return m;
    }
    public void reviseSku(String goodsRemark, SendAdviceVO vo) {
        String s1;
        s1 = "";
        s1 = getMaxSubString(goodsRemark, clothesColorList.toString());
        System.out.println(s1);
        if (!s1.equals("")) {
            if (clothesColorList.contains(s1)) {
                vo.setGoodsColor(s1);
            }
        }

        s1 = "";
        s1 = getMaxSubString(goodsRemark, clothesSizeList.toString());
        if (!s1.equals("")) {
            if (clothesSizeList.contains(s1)) {
                vo.setGoodsSize(s1);
            }
        }


    }

    public static String getMaxSubString(String str1, String str2) {
        String maxStr = "";

        String str = (str1.length() > str2.length()) ? str1 : str2;
        String key = str.equals(str1) ? str2 : str1;
        //System.out.println(str);
        for (int i = 0; i < key.length(); i++) {
            for (int j = key.length(); j > i && j > 0; j--) {
                String temp = key.substring(i, j);
                if (str.indexOf(temp) != -1 && maxStr.length() < temp.length()) {

                    maxStr = temp;

                }
            }
        }
        return maxStr.trim();
    }
    public static void addClothesColorList(Set<String> clothesColorList) {
        for (String s : ClotherInfoTable.clothesColorList) {
            clothesColorList.add(s);
        }
    }

    public static void addClothesSizeList(Set<String> clothesSizeList) {
        for (String s : ClotherInfoTable.clothesSizeList) {
            clothesSizeList.add(s);
        }
    }

    public static void addClothesSkuList(Set<String> clothesSkuList) {
        for (String s : ClotherInfoTable.clothesSku) {
            clothesSkuList.add(s);
        }
    }

}
