package com.auto.dao;
import java.sql.*;
import com.auto.dao.ExpressLogisticsDO;
/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2021-01-15
 */
public class ExpressLogisticsDao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private String tableName = "express_logistics";
    public ExpressLogisticsDao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;
    }
    public boolean doInsert(ExpressLogisticsDO vo) throws SQLException {
        String sql = "insert into express_logistics(express_record,express_record_time,express_id)values (?,?,?)ON CONFLICT (express_id)  DO UPDATE  SET express_record = ? ";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getExpressRecord());
        pStmt.setString(2, vo.getExpressRecordTime());
        pStmt.setString(3, vo.getExpressId());
        pStmt.setString(4, vo.getExpressRecord());
        return pStmt.execute();
    }
    public boolean doUpdate(ExpressLogisticsDO vo) throws SQLException {
        String sql = "update  express_logistics SET  express_record=? , express_id=?WHERE express_id=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getExpressRecord());
        pStmt.setString(2, vo.getExpressId());
        pStmt.setString(3, vo.getExpressId());
        return pStmt.executeUpdate() > 0;
    }
    public boolean doDelete(ExpressLogisticsDO vo) throws SQLException {
        String sql =String.format("DELETE FROM  express_logistics WHERE express_id=",vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        return pStmt.execute();
    }
    public String findExpressRecord(ExpressLogisticsDO  vo) throws SQLException {
            String result = null;
            String sql =String.format("select express_record from express_logistics  where express_id='%s'",vo.getExpressId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("express_record"); 
            return result;
        }   
}
