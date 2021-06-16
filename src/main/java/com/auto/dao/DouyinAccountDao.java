package com.auto.dao;

import java.sql.*;

/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2021-03-03
 */
public class DouyinAccountDao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    public DouyinAccountDao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;
    }
    //handle
    public Integer findAccountLivesCount(DouyinAccountDO  vo) throws SQLException {
        Integer result = 0;
        String sql =String.format("select * from douyin_account  where douyin_account_url='%s'",vo.getDouyinAccountUrl());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if(set.getRow()==1) {
            result = set.getInt("account_live_count");
            vo.setDouyinId(set.getString("douyin_id"));
        }
        return result;
    }
    //end
    public boolean doInsert(DouyinAccountDO vo) throws SQLException {
        String sql = "insert into douyin_account(douyin_account_name,douyin_id,douyin_account_url,douyin_account_title,account_fans_count,account_live_count)values (?,?,?,?,?,?)";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getDouyinAccountName());
        pStmt.setString(2, vo.getDouyinId());
        pStmt.setString(3, vo.getDouyinAccountUrl());
        pStmt.setString(4, vo.getDouyinAccountTitle());
        pStmt.setInt(5, vo.getAccountFansCounts());
        pStmt.setInt(6, vo.getLiveCounts());
        return pStmt.execute();
    }
    public boolean doUpdate(DouyinAccountDO vo) throws SQLException {
        String sql = "update  douyin_account SET  douyin_account_name=? , douyin_id=? , douyin_account_url=? , douyin_account_title=?WHERE douyin_account_name=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getDouyinAccountName());
        pStmt.setString(2, vo.getDouyinId());
        pStmt.setString(3, vo.getDouyinAccountUrl());
        pStmt.setString(4, vo.getDouyinAccountTitle());
        pStmt.setString(5, vo.getDouyinAccountName());
        return pStmt.executeUpdate() > 0;
    }
    public boolean doDelete(DouyinAccountDO vo) throws SQLException {
        String sql =String.format("DELETE FROM  douyin_account WHERE douyin_account_name=",vo.getDouyinAccountName());
        pStmt = conn.prepareStatement(sql);
        return pStmt.execute();
    }
    public String findDouyinId(DouyinAccountDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select douyin_id from douyin_account  where douyin_account_name='%s'",vo.getDouyinAccountName());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("douyin_id"); 
            return result;
        }
    public String findDouyinAccountUrl(DouyinAccountDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select douyin_account_url from douyin_account  where douyin_account_name='%s'",vo.getDouyinAccountName());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("douyin_account_url"); 
            return result;
        }
    public String findDouyinAccountTitle(DouyinAccountDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select douyin_account_title from douyin_account  where douyin_account_name='%s'",vo.getDouyinAccountName());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("douyin_account_title"); 
            return result;
        }   
}
