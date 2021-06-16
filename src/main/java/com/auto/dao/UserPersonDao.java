package com.auto.dao;

import java.sql.*;

import com.auto.dao.UserPersonDO;

/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020-12-04
 */
public class UserPersonDao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private String tableName = "user_person";

    public UserPersonDao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;
    }

    public boolean doInsert(UserPersonDO vo) throws SQLException {
        String sql = "insert into user_person(real_address,real_phone,user_id,real_name)values (?,?,?,?)";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getRealAddress());
        pStmt.setString(2, vo.getRealPhone());
        pStmt.setLong(3, vo.getUserId());
        pStmt.setString(4, vo.getRealName());
        return pStmt.execute();
    }

    public boolean doUpdate(UserPersonDO vo) throws SQLException {
        String sql = "update  user_person SET  real_address=? , real_phone=? , user_id=? , real_name=?WHERE user_id=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getRealAddress());
        pStmt.setString(2, vo.getRealPhone());
        pStmt.setLong(3, vo.getUserId());
        pStmt.setString(4, vo.getRealName());
        pStmt.setLong(5, vo.getUserId());
        return pStmt.executeUpdate() > 0;
    }

    public boolean doDelete(UserPersonDO vo) throws SQLException {
        String sql = String.format("DELETE FROM  user_person WHERE user_id=", vo.getUserId());
        pStmt = conn.prepareStatement(sql);
        return pStmt.execute();
    }

    public String findRealAddress(UserPersonDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select real_address from user_person  where user_id='%s'", vo.getUserId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("real_address");
        return result;
    }

    public String findRealPhone(UserPersonDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select real_phone from user_person  where user_id='%s'", vo.getUserId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("real_phone");
        return result;
    }

    public String findRealName(UserPersonDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select real_name from user_person  where user_id='%s'", vo.getUserId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("real_name");
        return result;
    }
}
