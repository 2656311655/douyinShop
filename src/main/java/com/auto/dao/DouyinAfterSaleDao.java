package com.auto.dao;
import java.sql.*;
import com.auto.dao.DouyinAfterSaleDO;
/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020-12-19
 */
public class DouyinAfterSaleDao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private String tableName = "douyin_after_sale";
    public DouyinAfterSaleDao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;
    }
    public boolean doInsert(DouyinAfterSaleDO vo) throws SQLException {
        String sql = "insert into douyin_after_sale(fare,after_sale_type,buyer_name,refund_goods_sum,business_refund_phone,refund_fare_sum,refund_send_time,after_sale_id,auto_apply_final_time,refund_reason,agree_after_sale_apply_time,goods_status,refund_way,business_refund_address,goods_name,refund_sus_time,refund_voucher,after_sale_close_time,history_record,goods_id,refund_express_company,after_apply_time,actual_cheque,goods_number,after_sale_status,refund_express_id,order_id)values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getFare());
        pStmt.setString(2, vo.getAfterSaleType());
        pStmt.setString(3, vo.getBuyerName());
        pStmt.setString(4, vo.getRefundGoodsSum());
        pStmt.setString(5, vo.getBusinessRefundPhone());
        pStmt.setString(6, vo.getRefundFareSum());
        pStmt.setString(7, vo.getRefundSendTime());
        pStmt.setString(8, vo.getAfterSaleId());
        pStmt.setString(9, vo.getAutoApplyFinalTime());
        pStmt.setString(10, vo.getRefundReason());
        pStmt.setString(11, vo.getAgreeAfterSaleApplyTime());
        pStmt.setString(12, vo.getGoodsStatus());
        pStmt.setString(13, vo.getRefundWay());
        pStmt.setString(14, vo.getBusinessRefundAddress());
        pStmt.setString(15, vo.getGoodsName());
        pStmt.setString(16, vo.getRefundSusTime());
        pStmt.setString(17, vo.getRefundVoucher());
        pStmt.setString(18, vo.getAfterSaleCloseTime());
        pStmt.setString(19, vo.getHistoryRecord());
        pStmt.setString(20, vo.getGoodsId());
        pStmt.setString(21, vo.getRefundExpressCompany());
        pStmt.setString(22, vo.getAfterApplyTime());
        pStmt.setString(23, vo.getActualCheque());
        pStmt.setString(24, vo.getGoodsNumber());
        pStmt.setString(25, vo.getAfterSaleStatus());
        pStmt.setString(26, vo.getRefundExpressId());
        pStmt.setString(27, vo.getOrderId());
        return pStmt.execute();
    }
    public boolean doUpdate(DouyinAfterSaleDO vo) throws SQLException {
        String sql = "update  douyin_after_sale SET  fare=? , after_sale_type=? , buyer_name=? , refund_goods_sum=? , business_refund_phone=? , refund_fare_sum=? , refund_send_time=? , after_sale_id=? , auto_apply_final_time=? , refund_reason=? , agree_after_sale_apply_time=? , goods_status=? , refund_way=? , business_refund_address=? , goods_name=? , refund_sus_time=? , refund_voucher=? , after_sale_close_time=? , history_record=? , goods_id=? , refund_express_company=? , after_apply_time=? , actual_cheque=? , goods_number=? , after_sale_status=? , refund_express_id=? , order_id=?WHERE fare=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getFare());
        pStmt.setString(2, vo.getAfterSaleType());
        pStmt.setString(3, vo.getBuyerName());
        pStmt.setString(4, vo.getRefundGoodsSum());
        pStmt.setString(5, vo.getBusinessRefundPhone());
        pStmt.setString(6, vo.getRefundFareSum());
        pStmt.setString(7, vo.getRefundSendTime());
        pStmt.setString(8, vo.getAfterSaleId());
        pStmt.setString(9, vo.getAutoApplyFinalTime());
        pStmt.setString(10, vo.getRefundReason());
        pStmt.setString(11, vo.getAgreeAfterSaleApplyTime());
        pStmt.setString(12, vo.getGoodsStatus());
        pStmt.setString(13, vo.getRefundWay());
        pStmt.setString(14, vo.getBusinessRefundAddress());
        pStmt.setString(15, vo.getGoodsName());
        pStmt.setString(16, vo.getRefundSusTime());
        pStmt.setString(17, vo.getRefundVoucher());
        pStmt.setString(18, vo.getAfterSaleCloseTime());
        pStmt.setString(19, vo.getHistoryRecord());
        pStmt.setString(20, vo.getGoodsId());
        pStmt.setString(21, vo.getRefundExpressCompany());
        pStmt.setString(22, vo.getAfterApplyTime());
        pStmt.setString(23, vo.getActualCheque());
        pStmt.setString(24, vo.getGoodsNumber());
        pStmt.setString(25, vo.getAfterSaleStatus());
        pStmt.setString(26, vo.getRefundExpressId());
        pStmt.setString(27, vo.getOrderId());
        pStmt.setString(28, vo.getFare());
        return pStmt.executeUpdate() > 0;
    }
    public boolean doDelete(DouyinAfterSaleDO vo) throws SQLException {
        String sql =String.format("DELETE FROM  douyin_after_sale WHERE fare=",vo.getFare());
        pStmt = conn.prepareStatement(sql);
        return pStmt.execute();
    }
    public String findAfterSaleType(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select after_sale_type from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("after_sale_type"); 
            return result;
        }
    public String findBuyerName(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select buyer_name from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("buyer_name"); 
            return result;
        }
    public String findRefundGoodsSum(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select refund_goods_sum from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("refund_goods_sum"); 
            return result;
        }
    public String findBusinessRefundPhone(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select business_refund_phone from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("business_refund_phone"); 
            return result;
        }
    public String findRefundFareSum(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select refund_fare_sum from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("refund_fare_sum"); 
            return result;
        }
    public String findRefundSendTime(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select refund_send_time from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("refund_send_time"); 
            return result;
        }
    public String findAfterSaleId(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select after_sale_id from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("after_sale_id"); 
            return result;
        }
    public String findAutoApplyFinalTime(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select auto_apply_final_time from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("auto_apply_final_time"); 
            return result;
        }
    public String findRefundReason(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select refund_reason from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("refund_reason"); 
            return result;
        }
    public String findAgreeAfterSaleApplyTime(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select agree_after_sale_apply_time from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("agree_after_sale_apply_time"); 
            return result;
        }
    public String findGoodsStatus(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select goods_status from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("goods_status"); 
            return result;
        }
    public String findRefundWay(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select refund_way from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("refund_way"); 
            return result;
        }
    public String findBusinessRefundAddress(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select business_refund_address from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("business_refund_address"); 
            return result;
        }
    public String findGoodsName(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select goods_name from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("goods_name"); 
            return result;
        }
    public String findRefundSusTime(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select refund_sus_time from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("refund_sus_time"); 
            return result;
        }
    public String findRefundVoucher(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select refund_voucher from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("refund_voucher"); 
            return result;
        }
    public String findAfterSaleCloseTime(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select after_sale_close_time from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("after_sale_close_time"); 
            return result;
        }
    public String findHistoryRecord(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select history_record from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("history_record"); 
            return result;
        }
    public String findGoodsId(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select goods_id from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("goods_id"); 
            return result;
        }
    public String findRefundExpressCompany(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select refund_express_company from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("refund_express_company"); 
            return result;
        }
    public String findAfterApplyTime(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select after_apply_time from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("after_apply_time"); 
            return result;
        }
    public String findActualCheque(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select actual_cheque from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("actual_cheque"); 
            return result;
        }
    public String findGoodsNumber(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select goods_number from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("goods_number"); 
            return result;
        }
    public String findAfterSaleStatus(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select after_sale_status from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("after_sale_status"); 
            return result;
        }
    public String findRefundExpressId(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select refund_express_id from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("refund_express_id"); 
            return result;
        }
    public String findOrderId(DouyinAfterSaleDO  vo) throws SQLException {
            String result = null;
           String sql =String.format("select order_id from douyin_after_sale  where fare='%s'",vo.getFare());
            pStmt = conn.prepareStatement(sql);
            ResultSet set = pStmt.executeQuery();
            set.next();
            if(set.getRow()==1)
                result = set.getString("order_id"); 
            return result;
        }   
}
