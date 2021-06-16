package com.auto.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2021-01-03
 */
public class AfterSaleDao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private String tableName = "after_sale";
    public AfterSaleDao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;
    }
    //Handle
    public List<ExpressPackageDO> findAllExpress() throws SQLException {
        List<ExpressPackageDO> expressVOList = new ArrayList<>();
        String sql = String.format("SELECT after_sale_type,after_sale_express_id,shop_name,order_info.main_order_id,after_sale_id,after_sale_status,order_info.goods_code from after_sale,order_info\n" +
                "where after_sale_express_id !=' 'and after_sale_status!='同意退款，退款成功' and after_sale.order_id=order_info.main_order_id");
        //sql="select * from douyin_order where  after_sale_express_id='4310834450145'";
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        while (set.next()) {
            ExpressPackageDO vo = new ExpressPackageDO();
            vo.setExpressId(set.getString("after_sale_express_id"));
            if (vo.getExpressId().equals("776240066837383")) {
                System.out.println();
            }
            String s = set.getString("goods_code");
            vo.setExpressPackage(s);
            s = set.getString("after_sale_type");
            if (s.equals("退货退款"))
                vo.setExpressType("退货件");
            else if (s.equals("换货"))
                vo.setExpressType("换货件");
            vo.setExpressStatus("");
            vo.setShopName(set.getString("shop_name"));
            vo.setOrderId(set.getString("main_order_id"));
            s = set.getString("after_sale_id");
            if (s == null)
                s = "";
            vo.setAfterSaleId(s);
            s = set.getString("after_sale_status");
            if (s == null)
                s = "";
            vo.setAfterSaleStatus(s);
            s="商家留言：";
            vo.setExpressRemark(s);
            expressVOList.add(vo);
        }
        return expressVOList;
    }
    public Timestamp findMaxOrderSubmitTime() throws SQLException {
        Timestamp result = null;
        String sql =String.format("select max(after_sale_apple_time) from after_sale  ");
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if(set.getRow()==1)
            result= set.getTimestamp("max");
        return result;
    }
    public boolean doInsert(AfterSaleDO vo) throws SQLException {
        String sql = "insert into after_sale(after_sale_reason,after_sale_id,after_sale_type,after_sale_express_id,after_sale_status,order_id,after_sale_apple_time,after_sale_remarks)values (?,?,?,?,?,?,?,?)" +
                "ON CONFLICT (after_sale_id) \n" +
                "DO UPDATE  SET after_sale_reason = ? , after_sale_type = ?, after_sale_express_id = ?, after_sale_status = ?, order_id = ?, after_sale_apple_time = ?, after_sale_remarks = ?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getAfterSaleReason());
        pStmt.setLong(2, vo.getAfterSaleId());
        pStmt.setString(3, vo.getAfterSaleType());
        pStmt.setString(4, vo.getAfterSaleExpressId());
        pStmt.setString(5, vo.getAfterSaleStatus());
        pStmt.setLong(6, vo.getOrderId());
        pStmt.setTimestamp(7, new Timestamp(vo.getAfterSaleApplyTime().getTime()));
        pStmt.setString(8, vo.getAfterSaleRemarks());
        pStmt.setString(9, vo.getAfterSaleReason());
        pStmt.setString(10, vo.getAfterSaleType());
        pStmt.setString(11, vo.getAfterSaleExpressId());
        pStmt.setString(12, vo.getAfterSaleStatus());
        pStmt.setLong(13, vo.getOrderId());
        pStmt.setTimestamp(14, new Timestamp(vo.getAfterSaleApplyTime().getTime()));
        pStmt.setString(15, vo.getAfterSaleRemarks());
        return pStmt.execute();
    }
    public boolean doUpdate(AfterSaleDO vo) throws SQLException {
        String sql = "update  after_sale SET  after_sale_reason=? , after_sale_id=? , after_sale_type=? , after_sale_express_id=? , after_sale_status=? , order_id=?WHERE after_sale_id=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getAfterSaleReason());
        pStmt.setLong(2, vo.getAfterSaleId());
        pStmt.setString(3, vo.getAfterSaleType());
        pStmt.setString(4, vo.getAfterSaleExpressId());
        pStmt.setString(5, vo.getAfterSaleStatus());
        pStmt.setLong(6, vo.getOrderId());
        pStmt.setLong(7, vo.getAfterSaleId());
        return pStmt.executeUpdate() > 0;
    }
    public boolean doDelete(AfterSaleDO vo) throws SQLException {
        String sql =String.format("DELETE FROM  after_sale WHERE after_sale_id=",vo.getAfterSaleId());
        pStmt = conn.prepareStatement(sql);
        return pStmt.execute();
    }
    public String findAfterSaleReason(AfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select after_sale_reason from after_sale  where after_sale_id='%s'",vo.getAfterSaleId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("after_sale_reason"); 
            return result;
        }
    public String findAfterSaleType(AfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select after_sale_type from after_sale  where after_sale_id='%s'",vo.getAfterSaleId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("after_sale_type"); 
            return result;
        }
    public String findAfterSaleExpressId(AfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select after_sale_express_id from after_sale  where after_sale_id='%s'",vo.getAfterSaleId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("after_sale_express_id"); 
            return result;
        }
    public String findAfterSaleStatus(AfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select after_sale_status from after_sale  where after_sale_id='%s'",vo.getAfterSaleId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("after_sale_status"); 
            return result;
        }
    public Long findOrderId(AfterSaleDO  vo) throws SQLException {
            Long result = null;
           String sql =String.format("select order_id from after_sale  where after_sale_id='%s'",vo.getAfterSaleId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getLong("order_id"); 
            return result;
        }   
}
