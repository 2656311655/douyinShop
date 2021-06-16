package com.auto.dao;
import java.sql.*;
import com.auto.dao.CustomerDO;
/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020-12-19
 */
public class CustomerDao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private String tableName = "customer";
    public CustomerDao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;
    }
    //handle
    public Long findCustomerId(CustomerDO vo) throws SQLException{
        Long result = null;
        String sql =String.format("select customer_id from customer  where customer_phone=?and customer_name=?");
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1,vo.getCustomerPhone());
        pStmt.setString(2,vo.getCustomerName());
        ResultSet set = pStmt.executeQuery();
        set.next();
        if(set.getRow()==1)
            result = set.getLong("customer_id");
        return result;
    }
    //end
    public boolean doInsert(CustomerDO vo) throws SQLException {
        String sql = "insert into customer(customer_type,customer_address,customer_tag,customer_session_id,customer_phone,customer_name,customer_id,order_id,customer_nickname)values (?,?,?,?,?,?,?,?,?)";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getCustomerType());
        pStmt.setString(2, vo.getCustomerAddress());
        pStmt.setString(3, vo.getCustomerTag());
        pStmt.setLong(4, vo.getCustomerSessionId());
        pStmt.setString(5, vo.getCustomerPhone());
        pStmt.setString(6, vo.getCustomerName());
        pStmt.setLong(7, vo.getCustomerId());
        pStmt.setString(8, vo.getOrderId());
        pStmt.setString(9, vo.getCustomerNickName());
        return pStmt.execute();
    }
    public boolean doUpdate(CustomerDO vo) throws SQLException {
        String sql = "update  customer SET  customer_type=? , customer_address=? , customer_tag=? , customer_session_id=? , customer_phone=? , customer_name=? , customer_id=? , order_id=? ,customer_nickname=?WHERE customer_id=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getCustomerType());
        pStmt.setString(2, vo.getCustomerAddress());
        pStmt.setString(3, vo.getCustomerTag());
        pStmt.setLong(4, vo.getCustomerSessionId());
        pStmt.setString(5, vo.getCustomerPhone());
        pStmt.setString(6, vo.getCustomerName());
        pStmt.setLong(7, vo.getCustomerId());
        pStmt.setString(8, vo.getOrderId());
        pStmt.setString(9, vo.getCustomerNickName());
        pStmt.setLong(10,vo.getCustomerId());
        return pStmt.executeUpdate() > 0;
    }
    public boolean doDelete(CustomerDO vo) throws SQLException {
        String sql =String.format("DELETE FROM  customer WHERE customer_id=",vo.getCustomerId());
        pStmt = conn.prepareStatement(sql);
        return pStmt.execute();
    }
    public String findCustomerType(CustomerDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select customer_type from customer  where customer_id='%s'",vo.getCustomerId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("customer_type"); 
            return result;
        }
    public String findCustomerAddress(CustomerDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select customer_address from customer  where customer_id='%s'",vo.getCustomerId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("customer_address"); 
            return result;
        }
    public String findCustomerTag(CustomerDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select customer_tag from customer  where customer_id='%s'",vo.getCustomerId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("customer_tag"); 
            return result;
        }
    public Long findCustomerSessionId(CustomerDO  vo) throws SQLException {
            Long result = null;
           String sql =String.format("select customer_session_id from customer  where customer_id='%s'",vo.getCustomerId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getLong("customer_session_id"); 
            return result;
        }
    public Integer findCustomerPhone(CustomerDO  vo) throws SQLException {
            Integer result = null;
           String sql =String.format("select customer_phone from customer  where customer_id='%s'",vo.getCustomerId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getInt("customer_phone"); 
            return result;
        }
    public String findCustomerName(CustomerDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select customer_name from customer  where customer_id='%s'",vo.getCustomerId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("customer_name"); 
            return result;
        }
    public String findOrderId(CustomerDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select order_id from customer  where customer_id='%s'",vo.getCustomerId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("order_id"); 
            return result;
        }   
}
