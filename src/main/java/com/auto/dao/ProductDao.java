package com.auto.dao;
import java.sql.*;
import com.auto.dao.ProductDO;
/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020-12-19
 */
public class ProductDao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private String tableName = "product";
    public ProductDao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;
    }
    public boolean doInsert(ProductDO vo) throws SQLException {
        String sql = "insert into product(product_sku,product_describe,product_id,product_sale_end_time,product_sale_start_time,product_catagory)values (?,?,?,?,?,?)";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getProductSku());
        pStmt.setString(2, vo.getProductDescribe());
        pStmt.setLong(3, vo.getProductId());
        Timestamp ts = null;
        ts.setTime(vo.getProductSaleEndTime().getTime());
        pStmt.setTimestamp(4, ts);

        ts.setTime(vo.getProductSaleStartTime().getTime());
        pStmt.setTimestamp(5, ts);
        pStmt.setString(6, vo.getProductCatagory());
        return pStmt.execute();
    }
    public boolean doUpdate(ProductDO vo) throws SQLException {
        String sql = "update  product SET  product_sku=? , product_describe=? , product_id=? , product_sale_end_time=? , product_sale_start_time=? , product_catagory=?WHERE product_id=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getProductSku());
        pStmt.setString(2, vo.getProductDescribe());
        pStmt.setLong(3, vo.getProductId());
        Timestamp ts = null;
        ts.setTime(vo.getProductSaleEndTime().getTime());
        pStmt.setTimestamp(4, ts);
        ts.setTime(vo.getProductSaleStartTime().getTime());
        pStmt.setTimestamp(5, ts);
        pStmt.setString(6, vo.getProductCatagory());
        pStmt.setLong(7, vo.getProductId());
        return pStmt.executeUpdate() > 0;
    }
    public boolean doDelete(ProductDO vo) throws SQLException {
        String sql =String.format("DELETE FROM  product WHERE product_id=",vo.getProductId());
        pStmt = conn.prepareStatement(sql);
        return pStmt.execute();
    }
    public String findProductSku(ProductDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select product_sku from product  where product_id='%s'",vo.getProductId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("product_sku"); 
            return result;
        }
    public String findProductDescribe(ProductDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select product_describe from product  where product_id='%s'",vo.getProductId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("product_describe"); 
            return result;
        }
    public Date findProductSaleEndTime(ProductDO  vo) throws SQLException {
            Date result = null;
           String sql =String.format("select product_sale_end_time from product  where product_id='%s'",vo.getProductId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getDate("product_sale_end_time"); 
            return result;
        }
    public Date findProductSaleStartTime(ProductDO  vo) throws SQLException {
            Date result = null;
           String sql =String.format("select product_sale_start_time from product  where product_id='%s'",vo.getProductId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getDate("product_sale_start_time"); 
            return result;
        }
    public String findProductCatagory(ProductDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select product_catagory from product  where product_id='%s'",vo.getProductId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("product_catagory"); 
            return result;
        }   
}
