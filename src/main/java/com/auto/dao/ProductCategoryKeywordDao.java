package com.auto.dao;
import java.sql.*;
/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2021-03-09
 */
public class ProductCategoryKeywordDao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private String tableName = "product_category_keyword";
    public ProductCategoryKeywordDao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;
    }
    public boolean doInsert(ProductCategoryKeywordDO vo) throws SQLException {
        String sql = "insert into product_category_keyword(product_title_keyword,product_category_id)values (?,?)";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getProductTitleKeywordName());
        pStmt.setInt(2, vo.getProductCategoryId());
        return pStmt.execute();
    }
    public boolean doUpdate(ProductCategoryKeywordDO vo) throws SQLException {
        String sql = "update  product_category_keyword SET  product_title_keyword_value=? , product_title_keyword_name=? , product_category_id=?WHERE product_title_keyword_value=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getProductTitleKeywordValue());
        pStmt.setString(2, vo.getProductTitleKeywordName());
        pStmt.setInt(3, vo.getProductCategoryId());
        pStmt.setString(4, vo.getProductTitleKeywordValue());
        return pStmt.executeUpdate() > 0;
    }
    public boolean doDelete(ProductCategoryKeywordDO vo) throws SQLException {
        String sql =String.format("DELETE FROM  product_category_keyword WHERE product_title_keyword_value=",vo.getProductTitleKeywordValue());
        pStmt = conn.prepareStatement(sql);
        return pStmt.execute();
    }
    public String findProductTitleKeywordName(ProductCategoryKeywordDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select product_title_keyword_name from product_category_keyword  where product_title_keyword_value='%s'",vo.getProductTitleKeywordValue());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("product_title_keyword_name"); 
            return result;
        }
    public Integer findProductCategoryId(ProductCategoryKeywordDO  vo) throws SQLException {
            Integer result = null;
           String sql =String.format("select product_category_id from product_category_keyword  where product_title_keyword_value='%s'",vo.getProductTitleKeywordValue());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getInt("product_category_id"); 
            return result;
        }   
}
