package com.auto.dao;
import java.sql.*;
import com.auto.dao.ProductFeaturesDO;
/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020-12-19
 */
public class ProductFeaturesDao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private String tableName = "product_features";
    public ProductFeaturesDao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;
    }
    public boolean doInsert(ProductFeaturesDO vo) throws SQLException {
        String sql = "insert into product_features(product_sku,product_resource_path,product_id,product_material,product_size,product_color,product_name,product_trademark)values (?,?,?,?,?,?,?,?)";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getProductSku());
        pStmt.setString(2, vo.getProductResourcePath());
        pStmt.setLong(3, vo.getProductId());
        pStmt.setString(4, vo.getProductMaterial());
        pStmt.setString(5, vo.getProductSize());
        pStmt.setString(6, vo.getProductColor());
        pStmt.setString(7, vo.getProductName());
        pStmt.setString(8, vo.getProductTrademark());
        return pStmt.execute();
    }
    public boolean doUpdate(ProductFeaturesDO vo) throws SQLException {
        String sql = "update  product_features SET  product_sku=? , product_resource_path=? , product_id=? , product_material=? , product_size=? , product_color=? , product_name=? , product_trademark=?WHERE product_id=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getProductSku());
        pStmt.setString(2, vo.getProductResourcePath());
        pStmt.setLong(3, vo.getProductId());
        pStmt.setString(4, vo.getProductMaterial());
        pStmt.setString(5, vo.getProductSize());
        pStmt.setString(6, vo.getProductColor());
        pStmt.setString(7, vo.getProductName());
        pStmt.setString(8, vo.getProductTrademark());
        pStmt.setLong(9, vo.getProductId());
        return pStmt.executeUpdate() > 0;
    }
    public boolean doDelete(ProductFeaturesDO vo) throws SQLException {
        String sql =String.format("DELETE FROM  product_features WHERE product_id=",vo.getProductId());
        pStmt = conn.prepareStatement(sql);
        return pStmt.execute();
    }
    public String findProductSku(ProductFeaturesDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select product_sku from product_features  where product_id='%s'",vo.getProductId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("product_sku"); 
            return result;
        }
    public String findProductResourcePath(ProductFeaturesDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select product_resource_path from product_features  where product_id='%s'",vo.getProductId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("product_resource_path"); 
            return result;
        }
    public String findProductMaterial(ProductFeaturesDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select product_material from product_features  where product_id='%s'",vo.getProductId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("product_material"); 
            return result;
        }
    public String findProductSize(ProductFeaturesDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select product_size from product_features  where product_id='%s'",vo.getProductId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("product_size"); 
            return result;
        }
    public String findProductColor(ProductFeaturesDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select product_color from product_features  where product_id='%s'",vo.getProductId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("product_color"); 
            return result;
        }
    public String findProductName(ProductFeaturesDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select product_name from product_features  where product_id='%s'",vo.getProductId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("product_name"); 
            return result;
        }
    public String findProductTrademark(ProductFeaturesDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select product_trademark from product_features  where product_id='%s'",vo.getProductId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("product_trademark"); 
            return result;
        }   
}
