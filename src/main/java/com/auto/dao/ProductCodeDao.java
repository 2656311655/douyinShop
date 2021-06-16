package com.auto.dao;
import java.sql.*;
import com.auto.dao.ProductCodeDO;
/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2021-01-03
 */
public class ProductCodeDao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private String tableName = "product_code";
    public ProductCodeDao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;
    }
    public boolean doInsert(ProductCodeDO vo) throws SQLException {
        String sql = "insert into product_code(trade,supplier,product_size,product_color,product_code,distributor,product_model)values (?,?,?,?,?,?,?)";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getTrade());
        pStmt.setString(2, vo.getSupplier());
        pStmt.setString(3, vo.getProductSize());
        pStmt.setString(4, vo.getProductColor());
        pStmt.setString(5, vo.getProductCode());
        pStmt.setString(6, vo.getDistributor());
        pStmt.setString(7, vo.getProductModel());
        return pStmt.execute();
    }
    public boolean doUpdate(ProductCodeDO vo) throws SQLException {
        String sql = "update  product_code SET  trade=? , supplier=? , product_size=? , product_color=? , product_code=? , distributor=? , product_model=?WHERE trade=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getTrade());
        pStmt.setString(2, vo.getSupplier());
        pStmt.setString(3, vo.getProductSize());
        pStmt.setString(4, vo.getProductColor());
        pStmt.setString(5, vo.getProductCode());
        pStmt.setString(6, vo.getDistributor());
        pStmt.setString(7, vo.getProductModel());
        pStmt.setString(8, vo.getTrade());
        return pStmt.executeUpdate() > 0;
    }
    public boolean doDelete(ProductCodeDO vo) throws SQLException {
        String sql =String.format("DELETE FROM  product_code WHERE trade=",vo.getTrade());
        pStmt = conn.prepareStatement(sql);
        return pStmt.execute();
    }
    public String findSupplier(ProductCodeDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select supplier from product_code  where trade='%s'",vo.getTrade());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("supplier"); 
            return result;
        }
    public String findProductSize(ProductCodeDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select product_size from product_code  where trade='%s'",vo.getTrade());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("product_size"); 
            return result;
        }
    public String findProductColor(ProductCodeDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select product_color from product_code  where trade='%s'",vo.getTrade());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("product_color"); 
            return result;
        }
    public String findProductCode(ProductCodeDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select product_code from product_code  where trade='%s'",vo.getTrade());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("product_code"); 
            return result;
        }
    public String findDistributor(ProductCodeDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select distributor from product_code  where trade='%s'",vo.getTrade());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("distributor"); 
            return result;
        }
    public String findProductModel(ProductCodeDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select product_model from product_code  where trade='%s'",vo.getTrade());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("product_model"); 
            return result;
        }   
}
