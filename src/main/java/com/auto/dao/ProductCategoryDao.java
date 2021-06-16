package com.auto.dao;
import java.sql.*;
/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2021-03-09
 */
public class ProductCategoryDao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private String tableName = "product_category";
    public ProductCategoryDao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;
    }
    public boolean doInsert(ProductCategoryDO vo) throws SQLException {
        String sql = "insert into product_category(product_first_catagory_name,product_three_catagory_name,product_second_catagory_name,product_category_id)values (?,?,?,?)";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getProductFirstCatagoryName());
        pStmt.setString(2, vo.getProductThreeCatagoryName());
        pStmt.setString(3, vo.getProductSecondCatagoryName());
        pStmt.setInt(4, vo.getProductCategoryId());
        return pStmt.execute();
    }
    public boolean doUpdate(ProductCategoryDO vo) throws SQLException {
        String sql = "update  product_category SET  product_first_catagory_name=? , product_three_catagory_name=? , product_second_catagory_name=? , product_category_id=?WHERE product_category_id=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getProductFirstCatagoryName());
        pStmt.setString(2, vo.getProductThreeCatagoryName());
        pStmt.setString(3, vo.getProductSecondCatagoryName());
        pStmt.setInt(4, vo.getProductCategoryId());
        pStmt.setInt(5, vo.getProductCategoryId());
        return pStmt.executeUpdate() > 0;
    }
    public boolean doDelete(ProductCategoryDO vo) throws SQLException {
        String sql =String.format("DELETE FROM  product_category WHERE product_category_id=",vo.getProductCategoryId());
        pStmt = conn.prepareStatement(sql);
        return pStmt.execute();
    }
    public String findProductFirstCatagoryName(ProductCategoryDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select product_first_catagory_name from product_category  where product_category_id='%s'",vo.getProductCategoryId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("product_first_catagory_name"); 
            return result;
        }
    public String findProductThreeCatagoryName(ProductCategoryDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select product_three_catagory_name from product_category  where product_category_id='%s'",vo.getProductCategoryId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("product_three_catagory_name"); 
            return result;
        }
    public String findProductSecondCatagoryName(ProductCategoryDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select product_second_catagory_name from product_category  where product_category_id='%s'",vo.getProductCategoryId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("product_second_catagory_name"); 
            return result;
        }   
}
