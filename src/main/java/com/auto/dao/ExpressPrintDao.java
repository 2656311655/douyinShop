package com.auto.dao;

import java.sql.*;

/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020-12-19
 */
public class ExpressPrintDao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private String tableName = "express_print";

    public ExpressPrintDao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;
    }

    public boolean doInsert(ExpressPrintDO vo) throws SQLException {
        String sql = "insert into express_print(fare,goods_name,sender_phone,goods_sum,goods_code,weight,recipient_address,express_print_time,goods_sku,express_id,order_status,sender_address,goods_number,sender,express_status,douyin_shop,recipient,express_platform,express_remark,order_id,recipient_phone)values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
        +"ON CONFLICT (express_id) \n" +
                "DO UPDATE  SET fare = ? ,goods_name = ? ,sender_phone = ? ,goods_sum = ? ,goods_code = ? ,weight = ? ,recipient_address = ? ,express_print_time = ? ,goods_sku = ? ,express_id = ? ,order_status = ? ,sender_address = ? ,goods_number = ? ,sender = ?,express_status = ? ,douyin_shop = ? ,recipient = ? ,express_platform = ? ,express_remark = ? ,order_id = ?,recipient_phone = ? ";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getFare());
        pStmt.setString(2, vo.getGoodsName());
        pStmt.setString(3, vo.getSenderPhone());
        pStmt.setString(4, vo.getGoodsSum());
        pStmt.setString(5, vo.getGoodsCode());
        pStmt.setString(6, vo.getWeight());
        pStmt.setString(7, vo.getRecipientAddress());
        pStmt.setString(8, vo.getExpressPrintTime());
        pStmt.setString(9, vo.getGoodsSku());
        pStmt.setString(10, vo.getExpressId());
        pStmt.setString(11, vo.getOrderStatus());
        pStmt.setString(12, vo.getSenderAddress());
        pStmt.setString(13, vo.getGoodsNumber());
        pStmt.setString(14, vo.getSender());
        pStmt.setString(15, vo.getExpressStatus());
        pStmt.setString(16, vo.getDouyinShop());
        pStmt.setString(17, vo.getRecipient());
        pStmt.setString(18, vo.getExpressPlatform());
        pStmt.setString(19, vo.getExpressRemark());
        pStmt.setString(20, vo.getOrderId());
        pStmt.setString(21, vo.getRecipientPhone());
        pStmt.setString(22, vo.getFare());
        pStmt.setString(23, vo.getGoodsName());
        pStmt.setString(24, vo.getSenderPhone());
        pStmt.setString(25, vo.getGoodsSum());
        pStmt.setString(26, vo.getGoodsCode());
        pStmt.setString(27, vo.getWeight());
        pStmt.setString(28, vo.getRecipientAddress());
        pStmt.setString(29, vo.getExpressPrintTime());
        pStmt.setString(30, vo.getGoodsSku());
        pStmt.setString(31, vo.getExpressId());
        pStmt.setString(32, vo.getOrderStatus());
        pStmt.setString(33, vo.getSenderAddress());
        pStmt.setString(34, vo.getGoodsNumber());
        pStmt.setString(35, vo.getSender());
        pStmt.setString(36, vo.getExpressStatus());
        pStmt.setString(37, vo.getDouyinShop());
        pStmt.setString(38, vo.getRecipient());
        pStmt.setString(39, vo.getExpressPlatform());
        pStmt.setString(40, vo.getExpressRemark());
        pStmt.setString(41, vo.getOrderId());
        pStmt.setString(42, vo.getRecipientPhone());
        return pStmt.execute();
    }

    public boolean doUpdate(ExpressPrintDO vo) throws SQLException {
        String sql = "update  express_print SET  fare=? , goods_name=? , sender_phone=? , goods_sum=? , goods_code=? , weight=? , recipient_address=? , express_print_time=? , goods_sku=? , express_id=? , order_status=? , sender_address=? , goods_number=? , sender=? , express_status=? , douyin_shop=? , recipient=? , express_platform=? , express_remark=? , order_id=? , recipient_phone=?WHERE express_id=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getFare());
        pStmt.setString(2, vo.getGoodsName());
        pStmt.setString(3, vo.getSenderPhone());
        pStmt.setString(4, vo.getGoodsSum());
        pStmt.setString(5, vo.getGoodsCode());
        pStmt.setString(6, vo.getWeight());
        pStmt.setString(7, vo.getRecipientAddress());
        pStmt.setString(8, vo.getExpressPrintTime());
        pStmt.setString(9, vo.getGoodsSku());
        pStmt.setString(10, vo.getExpressId());
        pStmt.setString(11, vo.getOrderStatus());
        pStmt.setString(12, vo.getSenderAddress());
        pStmt.setString(13, vo.getGoodsNumber());
        pStmt.setString(14, vo.getSender());
        pStmt.setString(15, vo.getExpressStatus());
        pStmt.setString(16, vo.getDouyinShop());
        pStmt.setString(17, vo.getRecipient());
        pStmt.setString(18, vo.getExpressPlatform());
        pStmt.setString(19, vo.getExpressRemark());
        pStmt.setString(20, vo.getOrderId());
        pStmt.setString(21, vo.getRecipientPhone());
        pStmt.setString(22, vo.getExpressId());
        return pStmt.executeUpdate() > 0;
    }

    public boolean doDelete(ExpressPrintDO vo) throws SQLException {
        String sql = String.format("DELETE FROM  express_print WHERE express_id=", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        return pStmt.execute();
    }

    public Boolean find(ExpressPrintDO vo) throws SQLException {
        String result = null;

        String sql = String.format("select * from express_print  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();

        if (set.getRow() == 1){
            vo.setGoodsName(set.getString("goods_name"));
            vo.setGoodsSku(set.getString("goods_sku"));
            vo.setGoodsCode(set.getString("goods_code"));
            vo.setExpressRemark(set.getString("express_remark"));
        }
        return true;

    }

    public String findFare(ExpressPrintDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select fare from express_print  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("fare");
        return result;
    }

    public String findGoodsName(ExpressPrintDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select goods_name from express_print  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("goods_name");
        return result;
    }

    public String findSenderPhone(ExpressPrintDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select sender_phone from express_print  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("sender_phone");
        return result;
    }

    public String findGoodsSum(ExpressPrintDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select goods_sum from express_print  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("goods_sum");
        return result;
    }

    public String findGoodsCode(ExpressPrintDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select goods_code from express_print  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("goods_code");
        return result;
    }

    public String findWeight(ExpressPrintDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select weight from express_print  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("weight");
        return result;
    }

    public String findRecipientAddress(ExpressPrintDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select recipient_address from express_print  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("recipient_address");
        return result;
    }

    public String findExpressPrintTime(ExpressPrintDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select express_print_time from express_print  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("express_print_time");
        return result;
    }

    public String findGoodsSku(ExpressPrintDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select goods_sku from express_print  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("goods_sku");
        return result;
    }

    public String findOrderStatus(ExpressPrintDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select order_status from express_print  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("order_status");
        return result;
    }

    public String findSenderAddress(ExpressPrintDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select sender_address from express_print  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("sender_address");
        return result;
    }

    public String findGoodsNumber(ExpressPrintDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select goods_number from express_print  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("goods_number");
        return result;
    }

    public String findSender(ExpressPrintDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select sender from express_print  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("sender");
        return result;
    }

    public String findExpressStatus(ExpressPrintDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select express_status from express_print  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("express_status");
        return result;
    }

    public String findDouyinShop(ExpressPrintDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select douyin_shop from express_print  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("douyin_shop");
        return result;
    }

    public String findRecipient(ExpressPrintDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select recipient from express_print  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("recipient");
        return result;
    }

    public String findExpressPlatform(ExpressPrintDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select express_platform from express_print  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("express_platform");
        return result;
    }

    public String findExpressRemark(ExpressPrintDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select express_remark from express_print  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("express_remark");
        return result;
    }

    public String findOrderId(ExpressPrintDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select order_id from express_print  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("order_id");
        return result;
    }

    public String findRecipientPhone(ExpressPrintDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select recipient_phone from express_print  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("recipient_phone");
        return result;
    }
}
