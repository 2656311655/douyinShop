package com.auto.dao;

import com.auto.modal.vo.GoodsSaleVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2021-03-03
 */
public class DouyinGoodsDao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private String tableName = "douyin_goods";
    public DouyinGoodsDao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;
    }
    //handle
    public GoodsSaleVO findGoodsSale(String keyword,String startSaleTime,String endSaleTime) throws SQLException {
        GoodsSaleVO result=new GoodsSaleVO();
        result.setGoodsSaleTime(startSaleTime);
        result.setGoodsType(keyword);
        keyword="%"+keyword+'%';
        String sql =String.format("select  sum(douyin_goods_sale_count),sum(douyin_goods_sale_count*douyin_goods_price) AS saleSum  from douyin_goods  where douyin_start_sale_time>= '%s' and  douyin_end_sale_time< '%s'  and douyin_goods_type like '%s' and douyin_goods_price<500",startSaleTime,endSaleTime,keyword);
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        result.setGoodsSaleCount(set.getInt("sum"));
        Double d=set.getDouble("saleSum");

        result.setGoodsAverageSale(d/result.getGoodsSaleCount());
        return  result;
    }
    public List<DouyinGoodsDO> findAll(String startSaleTime) throws SQLException {
        List<DouyinGoodsDO> list=new ArrayList<>();
        String sql =String.format("select * from douyin_goods  where douyin_start_sale_time>= '%s' and douyin_goods_tag=0",startSaleTime);
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        while (set.next()){
            DouyinGoodsDO douyinGoodsDO=new DouyinGoodsDO();
            douyinGoodsDO.setDouyinGoodsType(set.getString("douyin_goods_type"));
            douyinGoodsDO.setDouyinGoodsTitle(set.getString("douyin_goods_title"));
            douyinGoodsDO.setDouyinGoodsId(set.getString("douyin_goods_url_id"));
            list.add(douyinGoodsDO);
        }
        return list;
    }
    //end
    public boolean doInsert(DouyinGoodsDO vo) throws SQLException {
        String sql = "insert into douyin_goods(douyin_goods_type,douyin_goods_sale_count,douyin_goods_url_id,douyin_goods_title,douyin_goods_price,douyin_start_sale_time,douyin_live_id,douyin_goods_see_count,douyin_end_sale_time,douyin_goods_tag)values (?,?,?,?,?,?,?,?,?,?)";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getDouyinGoodsType());
        pStmt.setInt(2, vo.getDouyinGoodsSaleCount());
        pStmt.setString(3, vo.getDouyinGoodsId());
        pStmt.setString(4, vo.getDouyinGoodsTitle());
        pStmt.setDouble(5, vo.getDouyinGoodsPrice());
        pStmt.setString(6,vo.getGoodsSaleTime());
        pStmt.setString(7,vo.getDouYinLiveId());
        pStmt.setInt(8,vo.getGoodsSeeCount());
        pStmt.setString(9,vo.getDouYinEndSaleTime());
        pStmt.setInt(10,0);
        return pStmt.execute();
    }
    public boolean doUpdate(DouyinGoodsDO vo) throws SQLException {
        String sql = "update  douyin_goods SET  douyin_goods_type=? , douyin_goods_tag=?WHERE douyin_goods_url_id=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getDouyinGoodsType());
        pStmt.setInt(2, vo.getGoodsTypeTag());
        pStmt.setString(3, vo.getDouyinGoodsId());
        return pStmt.executeUpdate() > 0;
    }
    public boolean doDelete(DouyinGoodsDO vo) throws SQLException {
        String sql =String.format("DELETE FROM  douyin_goods WHERE douyin_goods_id=",vo.getDouyinGoodsId());
        pStmt = conn.prepareStatement(sql);
        return pStmt.execute();
    }
    public String findDouyinGoodsType(DouyinGoodsDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select douyin_goods_type from douyin_goods  where douyin_goods_id='%s'",vo.getDouyinGoodsId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("douyin_goods_type"); 
            return result;
        }
    public Integer findDouyinGoodsSaleCount(DouyinGoodsDO  vo) throws SQLException {
            Integer result = null;
           String sql =String.format("select douyin_goods_sale_count from douyin_goods  where douyin_goods_id='%s'",vo.getDouyinGoodsId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getInt("douyin_goods_sale_count"); 
            return result;
        }
    public String findDouyinGoodsTitle(DouyinGoodsDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select douyin_goods_title from douyin_goods  where douyin_goods_id='%s'",vo.getDouyinGoodsId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("douyin_goods_title"); 
            return result;
        }
    public Double findDouyinGoodsPrice(DouyinGoodsDO  vo) throws SQLException {
            Double result = null;
           String sql =String.format("select douyin_goods_price from douyin_goods  where douyin_goods_id='%s'",vo.getDouyinGoodsId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getDouble("douyin_goods_price"); 
            return result;
        }   
}
