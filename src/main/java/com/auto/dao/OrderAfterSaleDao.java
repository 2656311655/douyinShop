package com.auto.dao;
import java.sql.*;
import com.auto.dao.OrderAfterSaleDO;
/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2021-01-03
 */
public class OrderAfterSaleDao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private String tableName = "order_after_sale";
    public OrderAfterSaleDao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;
    }
    public boolean doInsert(OrderAfterSaleDO vo) throws SQLException {
        String sql = "insert into order_after_sale(after_sale_id)values (?)";
        pStmt = conn.prepareStatement(sql);
        pStmt.setInt(1, vo.getAfterSaleId());
        return pStmt.execute();
    }
    public boolean doUpdate(OrderAfterSaleDO vo) throws SQLException {
        String sql = "update  order_after_sale SET  after_sale_id=?WHERE after_sale_id=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setInt(1, vo.getAfterSaleId());
        pStmt.setInt(2, vo.getAfterSaleId());
        return pStmt.executeUpdate() > 0;
    }
    public boolean doDelete(OrderAfterSaleDO vo) throws SQLException {
        String sql =String.format("DELETE FROM  order_after_sale WHERE after_sale_id=",vo.getAfterSaleId());
        pStmt = conn.prepareStatement(sql);
        return pStmt.execute();
    }   
}
