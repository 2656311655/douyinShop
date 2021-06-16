package com.auto.dao;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2021-01-03
 */
public class OrderDao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private String tableName = "order";
    private SimpleDateFormat sf=null;
    public OrderDao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;
        sf=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    }

    //handle
    public List<String> findUnkownCustomer(String shopName) throws SQLException {
        List<String> strings = new ArrayList<>();
        String sql = String.format("select order_id  FROM order_info where shop_name='%s' and recipent_id=1",shopName);
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        while (set.next())
            strings.add(set.getString("order_id"));
        return strings;
    }
    public List<String> findExpress() throws SQLException {
        List<String> strings = new ArrayList<>();
        String sql = String.format("select * from express_process");
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        while (set.next())
            strings.add(set.getString("express_id"));
        return strings;
    }

    public boolean doUpdateOrderStatus(OrderDO vo) throws SQLException {
        String sql = "update  order_info SET  order_status=?  WHERE order_id=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getOrderStatus());
        pStmt.setLong(2, vo.getOrderId());
        return pStmt.executeUpdate() > 0;
    }

    public boolean doUpdateCustomerId(OrderDO vo) throws SQLException {
        String sql = "update  order_info SET  recipent_id=?  WHERE order_id=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setLong(1, vo.getRecipentId());
        pStmt.setLong(2, vo.getOrderId());
        return pStmt.executeUpdate() > 0;
    }
    public Integer findOrderBackCount(OrderDO vo) throws SQLException {
        Integer result=0;
        String sql =String.format("select count(*) from order_info  where order_status='备货中'and  shop_name='%s'",vo.getShopName());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if(set.getRow()==1)
            result = set.getInt("count");
        return result;
    }
    public Timestamp findMinOrderBackTime(OrderDO vo) throws SQLException {
        Timestamp result = null;
        String sql = String.format("select min(order_submit_time) from order_info  where order_status='备货中' and shop_name='%s'", vo.getShopName());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getTimestamp("min");
        return  result;
    }
    public Timestamp findMaxOrderSubmitTime(OrderDO vo) throws SQLException {
        Timestamp result = null;
        Timestamp temp = null;
        String sql = String.format("select max(order_submit_time) from order_info  where shop_name='%s'", vo.getShopName());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getTimestamp("max");
        sql = String.format("select min(order_submit_time) from order_info where order_status='待支付' and shop_name='%s'",vo.getShopName());
        pStmt = conn.prepareStatement(sql);
        set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1) {
            temp = set.getTimestamp("min");

        } else {
            return result;
        }
        if (temp == null) {
            return result;
        }
        if (temp.before(result)) {
            return temp;
        } else
            return result;
    }
    //end
    public boolean doInsert(OrderDO vo) throws SQLException, ParseException {
        String sql = "insert into order_info(order_status,express_id,business_remarks,actual_cheque,goods_code,platform_name,customer_remarks,shop_name,order_id,recipent_id,main_order_id,sender_id,order_submit_time,goods_number)values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)"+
                "ON CONFLICT (order_id) \n" +
                "DO UPDATE  SET order_status = ? , express_id = ?, business_remarks = ?, customer_remarks = ?,shop_name=?,order_submit_time=?, goods_code=?, goods_number=?,after_sale_status=?";
        pStmt = conn.prepareStatement(sql);
        int i=1;
        pStmt.setString(i++, vo.getOrderStatus());
        pStmt.setString(i++, vo.getExpressId());
        pStmt.setString(i++, vo.getBusinessRemarks());
        pStmt.setDouble(i++, vo.getActualCheque());
        pStmt.setString(i++, vo.getGoodsCode());
        pStmt.setString(i++, vo.getPlatformName());
        pStmt.setString(i++, vo.getCustomerRemarks());
        pStmt.setString(i++, vo.getShopName());
        pStmt.setLong(i++, vo.getOrderId());
        pStmt.setLong(i++, vo.getRecipentId());
        pStmt.setLong(i++, vo.getMainOrderId());
        pStmt.setLong(i++, vo.getSenderId());
        pStmt.setTimestamp(i++, new Timestamp(vo.getOrderSubmitTime().getTime()));
        pStmt.setInt(i++, vo.getGoodsNumber());
        pStmt.setString(i++, vo.getOrderStatus());
        pStmt.setString(i++, vo.getExpressId());
        pStmt.setString(i++, vo.getBusinessRemarks());
        pStmt.setString(i++, vo.getCustomerRemarks());
        pStmt.setString(i++, vo.getShopName());
        pStmt.setTimestamp(i++, new Timestamp(vo.getOrderSubmitTime().getTime()));
        pStmt.setString(i++, vo.getGoodsCode());
        pStmt.setInt(i++, vo.getGoodsNumber());
        pStmt.setString(i++, vo.getAfterSaleStatus());
        return pStmt.execute();
    }
    public boolean doUpdate(OrderDO vo) throws SQLException {
        String sql = "update  order SET  order_status=? , express_id=? , business_remarks=? , actual_cheque=? , goods_code=? , platform_name=? , customer_remarks=? , shop_name=? , order_id=? , recipent_id=? , main_order_id=? , sender_id=?WHERE order_id=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getOrderStatus());
        pStmt.setString(2, vo.getExpressId());
        pStmt.setString(3, vo.getBusinessRemarks());
        pStmt.setDouble(4, vo.getActualCheque());
        pStmt.setString(5, vo.getGoodsCode());
        pStmt.setString(6, vo.getPlatformName());
        pStmt.setString(7, vo.getCustomerRemarks());
        pStmt.setString(8, vo.getShopName());
        pStmt.setLong(9, vo.getOrderId());
        pStmt.setLong(10, vo.getRecipentId());
        pStmt.setLong(11, vo.getMainOrderId());
        pStmt.setLong(12, vo.getSenderId());
        pStmt.setLong(13, vo.getOrderId());
        return pStmt.executeUpdate() > 0;
    }
    public boolean doDelete(OrderDO vo) throws SQLException {
        String sql =String.format("DELETE FROM  order WHERE order_id=",vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        return pStmt.execute();
    }
    public String findOrderStatus(OrderDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select order_status from order  where order_id='%s'",vo.getOrderId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("order_status"); 
            return result;
        }
    public String findExpressId(OrderDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select express_id from order  where order_id='%s'",vo.getOrderId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("express_id"); 
            return result;
        }
    public String findBusinessRemarks(OrderDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select business_remarks from order  where order_id='%s'",vo.getOrderId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("business_remarks"); 
            return result;
        }
    public Double findActualCheque(OrderDO  vo) throws SQLException {
            Double result = null;
           String sql =String.format("select actual_cheque from order  where order_id='%s'",vo.getOrderId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getDouble("actual_cheque"); 
            return result;
        }
    public String findGoodsCode(OrderDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select goods_code from order  where order_id='%s'",vo.getOrderId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("goods_code"); 
            return result;
        }
    public String findPlatformName(OrderDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select platform_name from order  where order_id='%s'",vo.getOrderId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("platform_name"); 
            return result;
        }
    public String findCustomerRemarks(OrderDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select customer_remarks from order  where order_id='%s'",vo.getOrderId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("customer_remarks"); 
            return result;
        }
    public String findShopName(OrderDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select shop_name from order  where order_id='%s'",vo.getOrderId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("shop_name"); 
            return result;
        }
    public Long findRecipentId(OrderDO  vo) throws SQLException {
            Long result = null;
           String sql =String.format("select recipent_id from order  where order_id='%s'",vo.getOrderId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getLong("recipent_id"); 
            return result;
        }
    public Long findMainOrderId(OrderDO  vo) throws SQLException {
            Long result = null;
           String sql =String.format("select main_order_id from order  where order_id='%s'",vo.getOrderId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getLong("main_order_id"); 
            return result;
        }
    public Long findSenderId(OrderDO  vo) throws SQLException {
            Long result = null;
           String sql =String.format("select sender_id from order  where order_id='%s'",vo.getOrderId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getLong("sender_id"); 
            return result;
        }   
}
