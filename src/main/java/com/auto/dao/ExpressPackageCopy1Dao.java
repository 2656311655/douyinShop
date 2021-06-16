package com.auto.dao;
import java.sql.*;
import com.auto.dao.ExpressPackageCopy1DO;
/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020-12-19
 */
public class ExpressPackageCopy1Dao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private String tableName = "express_package_copy1";
    public ExpressPackageCopy1Dao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;
    }
    public boolean doInsert(ExpressPackageCopy1DO vo) throws SQLException {
        String sql = "insert into express_package_copy1(express_id,after_sale_id,express_status,express_package,after_sale_status,express_remark,shop_name,order_id,express_type)values (?,?,?,?,?,?,?,?,?)";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getExpressId());
        pStmt.setString(2, vo.getAfterSaleId());
        pStmt.setString(3, vo.getExpressStatus());
        pStmt.setString(4, vo.getExpressPackage());
        pStmt.setString(5, vo.getAfterSaleStatus());
        pStmt.setString(6, vo.getExpressRemark());
        pStmt.setString(7, vo.getShopName());
        pStmt.setString(8, vo.getOrderId());
        pStmt.setString(9, vo.getExpressType());
        return pStmt.execute();
    }
    public boolean doUpdate(ExpressPackageCopy1DO vo) throws SQLException {
        String sql = "update  express_package_copy1 SET  express_id=? , after_sale_id=? , express_status=? , express_package=? , after_sale_status=? , express_remark=? , shop_name=? , order_id=? , express_type=?WHERE express_id=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getExpressId());
        pStmt.setString(2, vo.getAfterSaleId());
        pStmt.setString(3, vo.getExpressStatus());
        pStmt.setString(4, vo.getExpressPackage());
        pStmt.setString(5, vo.getAfterSaleStatus());
        pStmt.setString(6, vo.getExpressRemark());
        pStmt.setString(7, vo.getShopName());
        pStmt.setString(8, vo.getOrderId());
        pStmt.setString(9, vo.getExpressType());
        pStmt.setString(10, vo.getExpressId());
        return pStmt.executeUpdate() > 0;
    }
    public boolean doDelete(ExpressPackageCopy1DO vo) throws SQLException {
        String sql =String.format("DELETE FROM  express_package_copy1 WHERE express_id=",vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        return pStmt.execute();
    }
    public String findAfterSaleId(ExpressPackageCopy1DO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select after_sale_id from express_package_copy1  where express_id='%s'",vo.getExpressId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("after_sale_id"); 
            return result;
        }
    public String findExpressStatus(ExpressPackageCopy1DO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select express_status from express_package_copy1  where express_id='%s'",vo.getExpressId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("express_status"); 
            return result;
        }
    public String findExpressPackage(ExpressPackageCopy1DO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select express_package from express_package_copy1  where express_id='%s'",vo.getExpressId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("express_package"); 
            return result;
        }
    public String findAfterSaleStatus(ExpressPackageCopy1DO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select after_sale_status from express_package_copy1  where express_id='%s'",vo.getExpressId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("after_sale_status"); 
            return result;
        }
    public String findExpressRemark(ExpressPackageCopy1DO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select express_remark from express_package_copy1  where express_id='%s'",vo.getExpressId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("express_remark"); 
            return result;
        }
    public String findShopName(ExpressPackageCopy1DO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select shop_name from express_package_copy1  where express_id='%s'",vo.getExpressId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("shop_name"); 
            return result;
        }
    public String findOrderId(ExpressPackageCopy1DO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select order_id from express_package_copy1  where express_id='%s'",vo.getExpressId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("order_id"); 
            return result;
        }
    public String findExpressType(ExpressPackageCopy1DO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select express_type from express_package_copy1  where express_id='%s'",vo.getExpressId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("express_type"); 
            return result;
        }   
}
