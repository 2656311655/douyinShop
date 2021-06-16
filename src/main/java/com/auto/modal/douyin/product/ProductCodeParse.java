package com.auto.modal.douyin.product;

import com.auto.dao.ProductCodeDO;
import com.auto.modal.config.Config;

import java.util.HashMap;

/**
 * @Classname SetProductCode
 * @Description 产品-编码解析器
 * @Date 2021/1/4 16:24
 * @Created by Administrator
 */
public class ProductCodeParse {

    public HashMap <String,Integer> clothesSizeList; //服装尺码表;

    public static void main(String[] args) throws Exception {
        ProductCodeParse productCodeParse=new ProductCodeParse(Config.productExcelFilePath);
        ProductCodeDO productCode=new ProductCodeDO();
        productCodeParse.generateProductCode(productCode);
    }
    public ProductCodeParse(String productFilePath){
        init(productFilePath);
    }
    public void init(String productFilePath){

    }
    public void generateProductCode(ProductCodeDO productCode){

    }
    public void parseProductCode(ProductCodeDO productCode){

    }




}
