package com.auto.modal.product;

import com.auto.dao.DefaultDatabaseConnect;
import com.auto.dao.DouyinGoodsDO;
import com.auto.dao.DouyinGoodsDao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Classname GoodsFilter
 * @Description 通过标题关键字对商品进行有效区分和过滤 -服装行业
 * @Date 2021/3/5 12:05
 * @Created by Administrator
 */
public class GoodsFilter {
    private  GoodsFilterKeys goodsFilterKeys;
    public static void main(String[] args) throws Exception {
        GoodsFilter obj=new GoodsFilter();
        obj.goodsFilterKeys=new GoodsFilterKeys();
        DouyinGoodsDao douyinGoodsDao =new DouyinGoodsDao(DefaultDatabaseConnect.getConn());
        List<DouyinGoodsDO> goodsDOS=douyinGoodsDao.findAll("2021-02-07");
        List<DouyinGoodsDO> tempDos=new ArrayList<>();
        for(DouyinGoodsDO vo:goodsDOS){
            String s1=obj.firstClassFilter(vo.getDouyinGoodsTitle());

            if(!s1.equals("")) {
                s1=s1 + "_" + vo.getDouyinGoodsType();
                vo.setDouyinGoodsType(s1);
                vo.setGoodsTypeTag(1);
                tempDos.add(vo);
            }
            else
                System.out.println("id "+vo.getDouyinGoodsId()+ " "+  vo.getDouyinGoodsTitle()  );
        }
        obj.batch(tempDos);
    }
    public   String  firstClassFilter(String goodsTitle){
        //
        for(Map.Entry<String,String> entry: this.goodsFilterKeys.getFirstLevelMaps().entrySet()){
            String key=entry.getKey();
            String value=entry.getValue();
            if(goodsTitle.indexOf(key)>=0){
                return value;
            }
        }

        return "";
    }

    public void batch(List<DouyinGoodsDO> goodsDOS) throws SQLException {
        DouyinGoodsDao douyinGoodsDao=new DouyinGoodsDao(DefaultDatabaseConnect.getConn());

        for(DouyinGoodsDO vo:goodsDOS){
            try {
                douyinGoodsDao.doUpdate(vo);
            }
            catch (Exception ex){
                ex.printStackTrace();
                System.out.println(vo.getDouyinGoodsTitle());
            }

        }
    }

}
