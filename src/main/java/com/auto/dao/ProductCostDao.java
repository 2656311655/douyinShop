package com.auto.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.auto.dao.ProductCostDO;

/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020-12-04
 */
public class ProductCostDao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private String tableName = "product_cost";

    public ProductCostDao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;
    }

    //handle
    public List<ProductCostDO> findAll(String sku) throws SQLException {
        List<ProductCostDO> result = new ArrayList<>();
        String sql = String.format("select * from product_cost  where product_sku='%s'", sku);
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        while (set.next()) {
            ProductCostDO productCostDO = new ProductCostDO();
            productCostDO.setProductId(set.getLong("product_id"));
            productCostDO.setProductSku(set.getString("product_sku"));
            productCostDO.setProductCost(set.getDouble("product_cost"));
            productCostDO.setProductCostType(set.getString("product_cost_type"));
            productCostDO.setProductSaleStartTime(set.getDate("product_sale_start_time"));
            productCostDO.setProductSaleEndTime(set.getDate("product_sale_end_time"));
            result.add(productCostDO);
        }
        return result;
    }

    public boolean doInsert(ProductCostDO vo) throws SQLException {
        String sql = "insert into product_cost(product_cost_type,product_sku,product_id,product_sale_end_time,product_cost,product_sale_start_time)values (?,?,?,?,?,?)";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getProductCostType());
        pStmt.setString(2, vo.getProductSku());
        pStmt.setLong(3, vo.getProductId());
        Timestamp ts = null;
        ts.setTime(vo.getProductSaleEndTime().getTime());
        pStmt.setTimestamp(4, ts);
        pStmt.setDouble(5, vo.getProductCost());
        ts.setTime(vo.getProductSaleStartTime().getTime());
        pStmt.setTimestamp(6, ts);
        return pStmt.execute();
    }

    public boolean doUpdate(ProductCostDO vo) throws SQLException {
        String sql = "update  product_cost SET  product_cost_type=? , product_sku=? , product_id=? , product_sale_end_time=? , product_cost=? , product_sale_start_time=?WHERE product_cost_type=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getProductCostType());
        pStmt.setString(2, vo.getProductSku());
        pStmt.setLong(3, vo.getProductId());
        Timestamp ts = null;
        ts.setTime(vo.getProductSaleEndTime().getTime());
        pStmt.setTimestamp(4, ts);
        pStmt.setDouble(5, vo.getProductCost());

        ts.setTime(vo.getProductSaleStartTime().getTime());
        pStmt.setTimestamp(6, ts);
        pStmt.setString(7, vo.getProductCostType());
        return pStmt.executeUpdate() > 0;
    }

    public boolean doDelete(ProductCostDO vo) throws SQLException {
        String sql = String.format("DELETE FROM  product_cost WHERE product_cost_type=", vo.getProductCostType());
        pStmt = conn.prepareStatement(sql);
        return pStmt.execute();
    }

    public String findProductSku(ProductCostDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select product_sku from product_cost  where product_cost_type='%s'", vo.getProductCostType());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("product_sku");
        return result;
    }

    public Long findProductId(ProductCostDO vo) throws SQLException {
        Long result = null;
        String sql = String.format("select product_id from product_cost  where product_cost_type='%s'", vo.getProductCostType());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getLong("product_id");
        return result;
    }

    public Date findProductSaleEndTime(ProductCostDO vo) throws SQLException {
        Date result = null;
        String sql = String.format("select product_sale_end_time from product_cost  where product_cost_type='%s'", vo.getProductCostType());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getDate("product_sale_end_time");
        return result;
    }

    public Double findProductCost(ProductCostDO vo) throws SQLException {
        Double result = null;
        String sql = String.format("select product_cost from product_cost  where product_cost_type='%s'", vo.getProductCostType());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getDouble("product_cost");
        return result;
    }

    public Date findProductSaleStartTime(ProductCostDO vo) throws SQLException {
        Date result = null;
        String sql = String.format("select product_sale_start_time from product_cost  where product_cost_type='%s'", vo.getProductCostType());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getDate("product_sale_start_time");
        return result;
    }
}
