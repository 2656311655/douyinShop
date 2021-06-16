package com.auto.dao;

import java.sql.*;

import com.auto.dao.UserInfoDO;

/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020-12-04
 */
public class UserInfoDao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private String tableName = "user_info";

    public UserInfoDao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;
    }

    public boolean doInsert(UserInfoDO vo) throws SQLException {
        String sql = "insert into user_info(nickName)values (?)";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getNickName());
        return pStmt.execute();
    }

    public boolean doUpdate(UserInfoDO vo) throws SQLException {
        String sql = "update  user_info SET  nickName=?WHERE nickName=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getNickName());
        pStmt.setString(2, vo.getNickName());
        return pStmt.executeUpdate() > 0;
    }

    public boolean doDelete(UserInfoDO vo) throws SQLException {
        String sql = String.format("DELETE FROM  user_info WHERE nickName=", vo.getNickName());
        pStmt = conn.prepareStatement(sql);
        return pStmt.execute();
    }
}
