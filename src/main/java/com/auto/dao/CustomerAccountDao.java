package com.auto.dao;
import java.sql.*;
import com.auto.dao.CustomerAccountDO;
/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020-12-19
 */
public class CustomerAccountDao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private String tableName = "customer_account";
    public CustomerAccountDao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;
    }
    public boolean doInsert(CustomerAccountDO vo) throws SQLException {
        String sql = "insert into customer_account(plaform_name,platform_uuid,customer_nickname,customer_account_type,customer_id)values (?,?,?,?,?)";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getPlaformName());
        pStmt.setString(2, vo.getPlatformUuid());
        pStmt.setString(3, vo.getCustomerNickname());
        pStmt.setString(4, vo.getCustomerAccountType());
        pStmt.setLong(5, vo.getCustomerId());
        return pStmt.execute();
    }
    public boolean doUpdate(CustomerAccountDO vo) throws SQLException {
        String sql = "update  customer_account SET  plaform_name=? , platform_uuid=? , customer_nickname=? , customer_account_type=? , customer_id=?WHERE customer_id=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getPlaformName());
        pStmt.setString(2, vo.getPlatformUuid());
        pStmt.setString(3, vo.getCustomerNickname());
        pStmt.setString(4, vo.getCustomerAccountType());
        pStmt.setLong(5, vo.getCustomerId());
        pStmt.setLong(6, vo.getCustomerId());
        return pStmt.executeUpdate() > 0;
    }
    public boolean doDelete(CustomerAccountDO vo) throws SQLException {
        String sql =String.format("DELETE FROM  customer_account WHERE customer_id=",vo.getCustomerId());
        pStmt = conn.prepareStatement(sql);
        return pStmt.execute();
    }
    public String findPlaformName(CustomerAccountDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select plaform_name from customer_account  where customer_id='%s'",vo.getCustomerId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("plaform_name"); 
            return result;
        }
    public String findPlatformUuid(CustomerAccountDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select platform_uuid from customer_account  where customer_id='%s'",vo.getCustomerId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("platform_uuid"); 
            return result;
        }
    public String findCustomerNickname(CustomerAccountDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select customer_nickname from customer_account  where customer_id='%s'",vo.getCustomerId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("customer_nickname"); 
            return result;
        }
    public String findCustomerAccountType(CustomerAccountDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select customer_account_type from customer_account  where customer_id='%s'",vo.getCustomerId());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("customer_account_type"); 
            return result;
        }   
}
