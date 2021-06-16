package com.auto.dao;
import java.sql.*;
import com.auto.dao.ProductPriceDO;
/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020-12-19
 */
public class ProductPriceDao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private String tableName = "product_price";
    public ProductPriceDao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;
    }
    public boolean doInsert(ProductPriceDO vo) throws SQLException {
        String sql = "insert into product_price(product_sku,product_id,product_picing,product_price,product_unit)values (?,?,?,?,?)";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getProductSku());
        pStmt.setLong(2, vo.getProductId());
        pStmt.setString(3, vo.getProductPicing());
        pStmt.setDouble(4, vo.getProductPrice());
        pStmt.setString(5, vo.getProductUnit());
        return pStmt.execute();
    }
    public boolean doUpdate(ProductPriceDO vo) throws SQLException {
        String sql = "update  product_price SET  product_sku=? , product_id=? , product_picing=? , product_price=? , product_unit=?WHERE product_id=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getProductSku());
        pStmt.setLong(2, vo.getProductId());
        pStmt.setString(3, vo.getProductPicing());
        pStmt.setDouble(4, vo.getProductPrice());
        pStmt.setString(5, vo.getProductUnit());
        pStmt.setLong(6, vo.getProductId());
        return pStmt.executeUpdate() > 0;
    }
    public boolean doDelete(ProductPriceDO vo) throws SQLException {
        String sql =String.format("DELETE FROM  product_price WHERE product_id=",vo.getProductId());
        pStmt = conn.prepareStatement(sql);
        return pStmt.execute();
    }
    public String findProductSku(ProductPriceDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select product_sku from product_price  where product_id='%s'",vo.getProductId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("product_sku"); 
            return result;
        }
    public String findProductPicing(ProductPriceDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select product_picing from product_price  where product_id='%s'",vo.getProductId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("product_picing"); 
            return result;
        }
    public Double findProductPrice(ProductPriceDO  vo) throws SQLException {
            Double result = null;
           String sql =String.format("select product_price from product_price  where product_id='%s'",vo.getProductId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getDouble("product_price"); 
            return result;
        }
    public String findProductUnit(ProductPriceDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select product_unit from product_price  where product_id='%s'",vo.getProductId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("product_unit"); 
            return result;
        }   
}
