package com.auto.modal.product;

import com.auto.dao.DefaultDatabaseConnect;
import com.auto.dao.DouyinGoodsDO;
import com.auto.dao.DouyinGoodsDao;
import com.auto.dao.ProductCategoryDO;
import com.auto.modal.excel.ReadExcelUtils;
import com.auto.modal.io.DiskIo;
import com.auto.modal.io.FileIo;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Classname ImportGoodsData
 * @Description 导入商品数据
 * @Date 2021/3/9 14:50
 * @Created by Administrator
 */
public class ImportGoodsData {
    private List<DouyinGoodsDO>  douYinGoodsDOList;

    public static void main(String[] args) throws Exception {
        ImportGoodsData obj=new ImportGoodsData();
        obj.init();
        String dir="E:\\data\\数据\\商品数据更新";
        List<String>  files= DiskIo.getFileListFromDir(dir);
        for(String path:files){
            obj.getDouYinGoods(path);
        }
    }
    public void init(){
        douYinGoodsDOList=new ArrayList<>();
    }
    public void  getDouYinGoods(String excelFilePath) throws IOException, SQLException {
        List<Map<String, String>> cells = ReadExcelUtils.readSXHash(excelFilePath);
        int begin=excelFilePath.indexOf("日榜_");
        int end=excelFilePath.indexOf(".xls");
        String goodsType=excelFilePath.substring(begin+3,end);
        begin=excelFilePath.indexOf("飞瓜数据_");
        end=excelFilePath.indexOf("抖音商品榜日榜");
        String date=excelFilePath.substring(begin+5,end);
        for (int j = 1; j < cells.size(); j++) {
            Map<String, String> map = cells.get(j);
            convert(goodsType,date,map);
        }
        batch();
    }
    public void convert(String goodsType,String date,Map<String, String> map) throws SQLException {

        DouyinGoodsDO goodsDO=new DouyinGoodsDO();
        goodsDO.setDouyinGoodsType(goodsType);
        goodsDO.setDouYinLiveId("0");
        goodsDO.setGoodsSaleTime(date+" 00:00:00");
        goodsDO.setDouYinEndSaleTime(date+" 23:59:59");
        goodsDO.setDouyinGoodsTitle(map.get("A"));
        String s=map.get("B");
        goodsDO.setDouyinGoodsPrice(Double.parseDouble(s));
        //https://haohuo.jinritemai.com/views/product/item2?id=3468423396591858543
        s=map.get("D");
        int i=s.indexOf("id=");
        int j=s.indexOf("&");
        if(j<0){
            j=s.length()-1;
        }
        if(i!=-1)
            s=s.substring(i+3,j);

        goodsDO.setDouyinGoodsId(s);
        s=map.get("E");
        if(s.equals("--")){
            s="0";
        }
        goodsDO.setDouyinGoodsSaleCount(Integer.parseInt(s));
        s=map.get("G");
        if(s.equals("--")){
            s="0";
        }
        goodsDO.setGoodsSeeCount(Integer.parseInt(s));

        douYinGoodsDOList.add(goodsDO);
    }
    public void batch() throws SQLException {
        DouyinGoodsDao douyinGoodsDao=new DouyinGoodsDao(DefaultDatabaseConnect.getConn());

        for(DouyinGoodsDO vo:douYinGoodsDOList){
            try {
                douyinGoodsDao.doInsert(vo);
            }
            catch (Exception ex){
                ex.printStackTrace();
                System.out.println(vo.getDouyinGoodsTitle());
            }

        }
        douYinGoodsDOList.clear();
    }
}
