package com.auto.modal.product;

import com.auto.dao.DefaultDatabaseConnect;
import com.auto.dao.DouyinGoodsDO;
import com.auto.dao.DouyinGoodsDao;
import com.auto.modal.excel.WriteExcelUtils;
import com.auto.modal.vo.GoodsSaleVO;
import org.apache.poi.ss.formula.functions.T;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Classname GoodAnalysis
 * @Description 商品分析
 * @Date 2021/3/10 17:36
 * @Created by Administrator
 */
public class GoodAnalysis {
    private DouyinGoodsDao goodsDao;

    public static void main(String[] args) throws Exception {
        GoodAnalysis obj=new GoodAnalysis();
        obj.goodsDao=new DouyinGoodsDao(DefaultDatabaseConnect.getConn());
        String keyword="T恤";
        List<String > keyWords=new ArrayList<>();
        GoodsFilterKeys goodsFilterKeys=new GoodsFilterKeys();

        for(Map.Entry<String, String> entry:goodsFilterKeys.getFirstLevelMaps().entrySet()){
            //keyWords.add(entry.getValue());
        }
        keyWords.add("T恤");
        keyWords.add("防晒衣");
        keyWords.add("卫衣");
        keyWords.add("牛仔裤");
        keyWords.add("休闲裤");
        keyWords.add("羽绒服");
        keyWords.add("风衣");
        keyWords.add("冲锋衣");
        String startSaleTime="2021-02-06";
        Date date=new Date();
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,0);
        String endSaleTime=sf.format(calendar.getTime());
        obj.generateGoodsSaleExcel(keyWords,startSaleTime,endSaleTime);
    }

    public void  generateGoodsSaleExcel(List<String > keyWords,String startSaleTime,String endSaleTime) throws Exception {

        List<List<GoodsSaleVO> > goods=new ArrayList<>();

        int i=0;
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        Date endSale1=sf.parse(endSaleTime);
        Calendar calendar=Calendar.getInstance();
        for(String keyword:keyWords) {
            Date starSale=sf.parse(startSaleTime);
            calendar.setTime(starSale);
            calendar.add(Calendar.DATE,1);
            Date  endSale=calendar.getTime();
            List<GoodsSaleVO> goodsSaleVOS=new ArrayList<>();
            while (endSale.getTime() <= endSale1.getTime()) {
                String startTime = sf.format(starSale) + " 00:00:00";
                String endTime = sf.format(endSale) + " 00:00:00";
                goodsSaleVOS.add(getGoodsSaleVO(keyword, startTime, endTime));
                calendar.setTime(endSale);
                starSale = endSale;
                calendar.add(Calendar.DATE, 1);
                endSale = calendar.getTime();
            }
            goods.add(goodsSaleVOS);

        }

        String s="抖音服装类目销量表.xlsx";
        List<HashMap<Integer, String>> cells =convert(goods,keyWords);
        String saveExcelFilePath="E:\\data\\数据\\商品单类销量\\"+s;
        WriteExcelUtils writeExcelUtils = new WriteExcelUtils(saveExcelFilePath);
        writeExcelUtils.writeHashExcel(cells);
    }
    public List<HashMap<Integer, String>> convert(List<List<GoodsSaleVO> > goods,List<String > keyWords){
        HashMap<Integer,String> map=new HashMap<>();
        List<HashMap<Integer, String>> cells = new ArrayList<>();
        int i=0;
        map.put(i++,"销售日期");
        for(String key:keyWords){
            String s=String.format("%s类目销售数量",key);
            map.put(i++,s);
            s=String.format("%s类目平均客单价",key);
            map.put(i++,s);
        }
        cells.add(map);
        i=0;
        int len=goods.get(0).size();
        for(int j=0;j<len;j++){
            map=new HashMap<>();
            i=0;
            GoodsSaleVO vo=goods.get(i).get(j);
            map.put(i++,vo.getGoodsSaleTime());
            for(int k=0;k<goods.size();k++){
                vo=goods.get(k).get(j);
                map.put(i++,String.valueOf(vo.getGoodsSaleCount()));
                map.put(i++,String.valueOf(vo.getGoodsAverageSale()));
            }


            cells.add(map);

        }
        return cells;

    }
    public GoodsSaleVO getGoodsSaleVO(String keyword,String startSaleTime,String endSaleTime) throws SQLException {
        return  goodsDao.findGoodsSale(keyword,startSaleTime,endSaleTime);
    }


}
