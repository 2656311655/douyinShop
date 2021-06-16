package com.auto.dao;

import java.sql.*;
import java.util.List;

import com.auto.dao.ExpressDO;

/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020-10-23
 */
public class ExpressDao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private String tableName = "express";

    public ExpressDao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;
    }

    // handle
    public void findGoodsInfo(List<ExpressDO> voList, String expressId) throws SQLException {
        String result = null;

        String sql = String.format("select * from express_print  where express_id='%s'", expressId);
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        while (set.next()) {
            ExpressDO vo = new ExpressDO();
            vo.setGoodsCode(set.getString("goods_code"));
            vo.setGoodsSku(set.getString("goods_sku"));
            vo.setExpressRemark(set.getString("express_remark"));
            voList.add(vo);
        }

    }

    public boolean doInsert(ExpressDO vo) throws SQLException {
        String sql = "insert into express(fare,goods_name,sender_phone,goods_sum,goods_code,weight,recipient_address,express_print_time,douYin_shop,goods_sku,express_id,order_status,sender_address,goods_number,sender,express_status,recipient,express_remark,order_id,recipient_phone)values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getFare());
        pStmt.setString(2, vo.getGoodsName());
        pStmt.setString(3, vo.getSenderPhone());
        pStmt.setString(4, vo.getGoodsSum());
        pStmt.setString(5, vo.getGoodsCode());
        pStmt.setString(6, vo.getWeight());
        pStmt.setString(7, vo.getRecipientAddress());
        pStmt.setString(8, vo.getExpressPrintTime());
        pStmt.setString(9, vo.getDouYinShop());
        pStmt.setString(10, vo.getGoodsSku());
        pStmt.setString(11, vo.getExpressId());
        pStmt.setString(12, vo.getOrderStatus());
        pStmt.setString(13, vo.getSenderAddress());
        pStmt.setString(14, vo.getGoodsNumber());
        pStmt.setString(15, vo.getSender());
        pStmt.setString(16, vo.getExpressStatus());
        pStmt.setString(17, vo.getRecipient());
        pStmt.setString(18, vo.getExpressRemark());
        pStmt.setString(19, vo.getOrderId());
        pStmt.setString(20, vo.getRecipientPhone());
        return pStmt.execute();
    }

    public boolean doUpdate(ExpressDO vo) throws SQLException {
        String sql = "update  express SET  fare=? , goods_name=? , sender_phone=? , goods_sum=? , goods_code=? , weight=? , recipient_address=? , express_print_time=? , douYin_shop=? , goods_sku=? , express_id=? , order_status=? , sender_address=? , goods_number=? , sender=? , express_status=? , recipient=? , express_remark=? , order_id=? , recipient_phone=?WHERE fare=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getFare());
        pStmt.setString(2, vo.getGoodsName());
        pStmt.setString(3, vo.getSenderPhone());
        pStmt.setString(4, vo.getGoodsSum());
        pStmt.setString(5, vo.getGoodsCode());
        pStmt.setString(6, vo.getWeight());
        pStmt.setString(7, vo.getRecipientAddress());
        pStmt.setString(8, vo.getExpressPrintTime());
        pStmt.setString(9, vo.getDouYinShop());
        pStmt.setString(10, vo.getGoodsSku());
        pStmt.setString(11, vo.getExpressId());
        pStmt.setString(12, vo.getOrderStatus());
        pStmt.setString(13, vo.getSenderAddress());
        pStmt.setString(14, vo.getGoodsNumber());
        pStmt.setString(15, vo.getSender());
        pStmt.setString(16, vo.getExpressStatus());
        pStmt.setString(17, vo.getRecipient());
        pStmt.setString(18, vo.getExpressRemark());
        pStmt.setString(19, vo.getOrderId());
        pStmt.setString(20, vo.getRecipientPhone());
        pStmt.setString(21, vo.getFare());
        return pStmt.executeUpdate() > 0;
    }

    public boolean doDelete(ExpressDO vo) throws SQLException {
        String sql = String.format("DELETE FROM  express WHERE fare=", vo.getFare());
        pStmt = conn.prepareStatement(sql);
        return pStmt.execute();
    }

    public String findGoodsName(ExpressDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select goods_name from express  where fare='%s'", vo.getFare());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("goods_name");
        return result;
    }

    public String findSenderPhone(ExpressDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select sender_phone from express  where fare='%s'", vo.getFare());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("sender_phone");
        return result;
    }

    public String findGoodsSum(ExpressDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select goods_sum from express  where fare='%s'", vo.getFare());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("goods_sum");
        return result;
    }

    public String findGoodsCode(ExpressDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select goods_code from express  where fare='%s'", vo.getFare());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("goods_code");
        return result;
    }

    public String findWeight(ExpressDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select weight from express  where fare='%s'", vo.getFare());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("weight");
        return result;
    }

    public String findRecipientAddress(ExpressDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select recipient_address from express  where fare='%s'", vo.getFare());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("recipient_address");
        return result;
    }

    public String findExpressPrintTime(ExpressDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select express_print_time from express  where fare='%s'", vo.getFare());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("express_print_time");
        return result;
    }

    public String findDouYinShop(ExpressDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select douYin_shop from express  where fare='%s'", vo.getFare());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("douYin_shop");
        return result;
    }

    public String findGoodsSku(ExpressDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select goods_sku from express  where fare='%s'", vo.getFare());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("goods_sku");
        return result;
    }

    public String findExpressId(ExpressDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select express_id from express  where fare='%s'", vo.getFare());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("express_id");
        return result;
    }

    public String findOrderStatus(ExpressDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select order_status from express  where fare='%s'", vo.getFare());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("order_status");
        return result;
    }

    public String findSenderAddress(ExpressDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select sender_address from express  where fare='%s'", vo.getFare());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("sender_address");
        return result;
    }

    public String findGoodsNumber(ExpressDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select goods_number from express  where fare='%s'", vo.getFare());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("goods_number");
        return result;
    }

    public String findSender(ExpressDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select sender from express  where fare='%s'", vo.getFare());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("sender");
        return result;
    }

    public String findExpressStatus(ExpressDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select express_status from express  where fare='%s'", vo.getFare());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("express_status");
        return result;
    }

    public String findRecipient(ExpressDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select recipient from express  where fare='%s'", vo.getFare());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("recipient");
        return result;
    }

    public String findExpressRemark(ExpressDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select express_remark from express  where fare='%s'", vo.getFare());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("express_remark");
        return result;
    }

    public String findOrderId(ExpressDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select order_id from express  where fare='%s'", vo.getFare());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("order_id");
        return result;
    }

    public String findRecipientPhone(ExpressDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select recipient_phone from express  where fare='%s'", vo.getFare());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("recipient_phone");
        return result;
    }
}
