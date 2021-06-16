package com.auto.modal.product;

import com.auto.dao.*;
import com.auto.modal.excel.ReadExcelUtils;
import com.auto.modal.io.DiskIo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname ExprotData
 * @Description 导入商品分类数据
 * @Date 2021/3/9 12:02
 * @Created by Administrator
 */
public class ImportProductCategoryData {
    HashMap<String ,Integer> productCategoryMaps;
    List<String> productCategoryKeys;

    public static void main(String[] args) throws Exception {
        ImportProductCategoryData obj=new ImportProductCategoryData();
        obj.init();
        String dir="E:\\data\\产品资料库\\商品关键字";
        ArrayList<String> stockPaths= DiskIo.getFileListFromDir(dir);
        if(stockPaths==null){
            stockPaths=new ArrayList<>();
        }
        for(String s:stockPaths)
            obj.importProductCategory(s);
        obj.batch();

    }
    public void  init(){
        productCategoryMaps=new HashMap<>();
        productCategoryKeys=new ArrayList<>();
    }
    public List<ProductCategoryKeywordDO> getProductCategoryKeys(){
        List<ProductCategoryKeywordDO> result =new ArrayList<>();
        for(String s : productCategoryKeys){
            String[] s1 =s.split("\\$");
            if(s1.length!=2){
                System.out.println(s);
            }
            String key=s1[0];
            String value=s1[1];
            ProductCategoryKeywordDO vo=new ProductCategoryKeywordDO();
            vo.setProductTitleKeywordName(key);
            if(!productCategoryMaps.containsKey(value)){
               System.out.println(s);
            }
            Integer id=productCategoryMaps.get(value);
            vo.setProductCategoryId(id);
            result.add(vo);
        }
        return result;
    }
    public  List<ProductCategoryDO>  getProductCategoryMaps() {
        List<ProductCategoryDO> productCategoryDOS =new ArrayList<>();
        for(Map.Entry<String,Integer> entry: productCategoryMaps.entrySet()){
            String key=entry.getKey();
            Integer value=entry.getValue();
            ProductCategoryDO productCategoryDO =new ProductCategoryDO();
            productCategoryDO.setProductCategoryId(value);
            String [] keys=key.split("\\|");
            if(keys.length==1){
                productCategoryDO.setProductFirstCatagoryName(keys[0]);
                productCategoryDO.setProductSecondCatagoryName("");
                productCategoryDO.setProductThreeCatagoryName("");
            }
            else if(keys.length==2){
                productCategoryDO.setProductFirstCatagoryName(keys[0]);
                productCategoryDO.setProductSecondCatagoryName(keys[1]);
                productCategoryDO.setProductThreeCatagoryName("");
            }
            else if(keys.length==3){
                productCategoryDO.setProductFirstCatagoryName(keys[0]);
                productCategoryDO.setProductSecondCatagoryName(keys[1]);
                productCategoryDO.setProductThreeCatagoryName(keys[2]);

            }
            else if(keys.length==4){
                productCategoryDO.setProductFirstCatagoryName(keys[0]);
                productCategoryDO.setProductSecondCatagoryName(keys[1]);
                productCategoryDO.setProductThreeCatagoryName(keys[2]+keys[3]);
            }
            else{
               System.out.println("异常字段 "+key);
            }
            productCategoryDOS.add(productCategoryDO);
        }
        return productCategoryDOS;
    }
    public void importProductCategory(String excelFilePath) throws IOException {
        List<Map<String, String>> cells =ReadExcelUtils.readSHash(excelFilePath);

        for (int j = 1; j < cells.size(); j++) {
            Map<String, String> map = cells.get(j);
            convert(map);
        }

    }
    public void  convert(Map<String, String> map){
        String key=map.get("A");
        String value="";
        if(map.size()==2){
            value=map.get("B");


        }
        else if(map.size()==3)
            value=map.get("B")+"|"+map.get("C");
        else if(map.size()==4){
            value=map.get("B")+"|"+map.get("C")+"|"+map.get("D");
        }
        else{
            System.out.println("异常字段"+key);
        }
        if(!productCategoryMaps.containsKey(value))
            productCategoryMaps.put(value,productCategoryMaps.size()+1);
        productCategoryKeys.add(key+'$'+value);

    }
    public  void  batch() throws SQLException {
       ProductCategoryDao productCategoryDao=new ProductCategoryDao(DefaultDatabaseConnect.getConn());
       ProductCategoryKeywordDao productCategoryKeywordDao=new ProductCategoryKeywordDao(DefaultDatabaseConnect.getConn());
       List<ProductCategoryDO>  productCategoryDOS=getProductCategoryMaps();
       List<ProductCategoryKeywordDO> productCategoryKeys=getProductCategoryKeys();
       for(ProductCategoryDO vo:productCategoryDOS){
           try {
               productCategoryDao.doInsert(vo);
           }
           catch (Exception ex){
               ex.printStackTrace();
               System.out.println(vo.getProductFirstCatagoryName());
           }

       }
        for(ProductCategoryKeywordDO vo:productCategoryKeys){
            try {
                productCategoryKeywordDao.doInsert(vo);
            }
            catch (Exception ex){
                ex.printStackTrace();
                System.out.println(vo.getProductTitleKeywordName());
            }

        }
    }
}
