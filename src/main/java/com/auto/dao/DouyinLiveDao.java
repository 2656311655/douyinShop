package com.auto.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2021-03-03
 */
public class DouyinLiveDao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private String tableName = "douyin_live";
    public DouyinLiveDao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;
    }
    //handle
    public List<String> findPetentialAccount(String startTime,String endTime) throws SQLException {
        List<String> temp = new ArrayList<>();
        List<String> result = new ArrayList<>();
        String sql =String.format("select douyin_account_url,sum(douyin_live_sales) AS sums\n" +
                "from douyin_live where douyin_live_time>'%s' and douyin_live_time<'%s' \n" +
                "GROUP BY (douyin_account_url)\n" +
                "ORDER BY  sums ",startTime,endTime);
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();

        while (set.next()){
            Double d=set.getDouble("sums");
            if(d<3000000&&d>=1000000){
                temp.add(set.getString("douyin_account_url"));
            }
        }
        int i=0;
        for(String s:temp){
            sql =String.format("select * from douyin_live where douyin_account_url='%s' ",s);
            pStmt = conn.prepareStatement(sql);
            set = pStmt.executeQuery();
            i=0;
            while (set.next()){
                if(set.getDouble("douyin_live_sales")<100000){
                    i++;
                }
            }
            if(i<=3){
                result.add(s);
            }
        }

        return result;
    }
    //end
    public boolean doInsert(DouyinLiveDO vo) throws SQLException {
        String sql = "insert into douyin_live(douyin_live_title,douyin_live_sales,douyin_live_sale_count,douyin_live_time,douyin_account_url,douyin_live_id)values (?,?,?,?,?,?)"+
        "ON CONFLICT (douyin_live_id) \n" +
                "DO UPDATE  SET douyin_live_title=? , douyin_live_sales=? , douyin_live_sale_count=? , douyin_live_time=? , douyin_account_url=? ";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getDouyinLiveTitle());
        pStmt.setFloat(2, vo.getDouyinLiveSales());
        pStmt.setInt(3, vo.getDouyinLiveSaleCount());
        pStmt.setString(4, vo.getDouyinLiveTime());
        pStmt.setString(5, vo.getDouyinAccountUrl());
        pStmt.setString(6, vo.getDouyinLiveId());
        pStmt.setString(7, vo.getDouyinLiveTitle());
        pStmt.setFloat(8, vo.getDouyinLiveSales());
        pStmt.setInt(9, vo.getDouyinLiveSaleCount());
        pStmt.setString(10, vo.getDouyinLiveTime());
        pStmt.setString(11, vo.getDouyinAccountUrl());
        return pStmt.execute();
    }
    public boolean doUpdate(DouyinLiveDO vo) throws SQLException {
        String sql = "update  douyin_live SET  douyin_live_title=? , douyin_live_sales=? , douyin_live_sale_count=? , douyin_live_time=? , douyin_account_url=? , douyin_live_id=?WHERE douyin_live_title=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getDouyinLiveTitle());
        pStmt.setFloat(2, vo.getDouyinLiveSales());
        pStmt.setInt(3, vo.getDouyinLiveSaleCount());
        pStmt.setString(4, vo.getDouyinLiveTime());
        pStmt.setString(5, vo.getDouyinAccountUrl());
        pStmt.setString(6, vo.getDouyinLiveId());
        pStmt.setString(7, vo.getDouyinLiveTitle());
        return pStmt.executeUpdate() > 0;
    }
    public boolean doDelete(DouyinLiveDO vo) throws SQLException {
        String sql =String.format("DELETE FROM  douyin_live WHERE douyin_live_title=",vo.getDouyinLiveTitle());
        pStmt = conn.prepareStatement(sql);
        return pStmt.execute();
    }
    public Float findDouyinLiveSales(DouyinLiveDO  vo) throws SQLException {
            Float result = null;
           String sql =String.format("select douyin_live_sales from douyin_live  where douyin_live_title='%s'",vo.getDouyinLiveTitle());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getFloat("douyin_live_sales"); 
            return result;
        }
    public Integer findDouyinLiveSaleCount(DouyinLiveDO  vo) throws SQLException {
            Integer result = null;
           String sql =String.format("select douyin_live_sale_count from douyin_live  where douyin_live_title='%s'",vo.getDouyinLiveTitle());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getInt("douyin_live_sale_count"); 
            return result;
        }
    public Date findDouyinLiveTime(DouyinLiveDO  vo) throws SQLException {
            Date result = null;
           String sql =String.format("select douyin_live_time from douyin_live  where douyin_live_title='%s'",vo.getDouyinLiveTitle());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getDate("douyin_live_time"); 
            return result;
        }
    public String findDouyinAccountUrl(DouyinLiveDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select douyin_account_url from douyin_live  where douyin_live_title='%s'",vo.getDouyinLiveTitle());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("douyin_account_url"); 
            return result;
        }
    public String findDouyinLiveId(DouyinLiveDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select douyin_live_id from douyin_live  where douyin_live_title='%s'",vo.getDouyinLiveTitle());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("douyin_live_id"); 
            return result;
        }   
}
