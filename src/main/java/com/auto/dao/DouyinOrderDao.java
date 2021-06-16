package com.auto.dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.auto.modal.vo.AfterSaleVO;
import com.auto.dao.DouyinOrderDO;
import com.auto.modal.vo.AfterSaleVO;
import com.auto.modal.vo.CustomerVO;
import com.auto.modal.vo.ExpressVO;

/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020-12-04
 */
public class DouyinOrderDao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private String tableName = "douyin_order";

    public DouyinOrderDao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;
    }

    //handle
    public boolean updateOrderStatus(DouyinOrderDO vo) throws SQLException {
        String sql = "update  douyin_order SET   business_remarks=?, order_status=? WHERE order_id=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getBusinessRemarks());
        pStmt.setString(2, vo.getOrderStatus());
        pStmt.setString(3, vo.getOrderId());
        return pStmt.executeUpdate() > 0;
    }
    public boolean updateMainOrderId(String orderId,String mainOrderId) throws SQLException {
        String sql = "update  douyin_order SET   main_order_id=?   WHERE order_id=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, mainOrderId);
        pStmt.setString(2, orderId);
        return pStmt.executeUpdate() > 0;
    }

    public List<ExpressPackageDO> findAllExpress(String startTime ) throws SQLException {

        List<ExpressPackageDO> expressVOList = new ArrayList<>();
        String sql = String.format("select  after_sale_express_id,goods_sku,goods_size,shop_id,order_id,after_sale_id,after_sale_status,business_type,user_remarks from douyin_order\n" +
                "where  after_sale_express_id is not null and after_sale_express_id!=' 'and order_sumbit_time> '%s'  ",startTime);
        //sql="select * from douyin_order where  after_sale_express_id='4310834450145'";
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        while (set.next()) {
            ExpressPackageDO vo = new ExpressPackageDO();
            vo.setExpressId(set.getString("after_sale_express_id"));
            if(vo.getExpressId().equals("776240066837383")){
                System.out.println("");
            }
            String s=set.getString("goods_sku")+set.getString("goods_size");
            vo.setExpressPackage(s);
            vo.setExpressType("退货件");
            vo.setExpressStatus("");
            vo.setShopName(set.getString("shop_id"));
            vo.setOrderId(set.getString("order_id"));
            s=set.getString("after_sale_id");
            if(s==null)
                s="";
            vo.setAfterSaleId(s);
            s=set.getString("after_sale_status");
            if(s==null)
                s="";
            vo.setAfterSaleStatus(s);
            s="商家留言："+set.getString("business_type")+"客户留言: "+set.getString("user_remarks");
            vo.setExpressRemark(s);
            expressVOList.add(vo);
        }
        return expressVOList;
    }

    public void find(DouyinOrderDO vo) throws SQLException {
        String sql = String.format("select * from douyin_order where order_ud='%s' ", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1) {
            vo.setOrderSumbitTime(set.getString("order_sumbit_time"));
            vo.setActualCheque(set.getString("actual_cheque"));
        }


    }
    public List<ExpressPackageDO> findGoodsInfo(String expressId) throws SQLException {
        String result = null;
        String sql = String.format("select shop_id,order_Id,after_sale_id,after_sale_status,  goods_sku,goods_size,order_status from douyin_order  where express_id='%s'", expressId);
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        List<ExpressPackageDO> voList = new ArrayList<>();
        while (set.next()) {
            ExpressPackageDO vo = new ExpressPackageDO();
            vo.setOrderId(set.getString("order_id"));
            vo.setExpressPackage(set.getString("goods_sku")+set.getString("goods_size"));
            vo.setAfterSaleStatus(set.getString("after_sale_status"));
            vo.setAfterSaleId(set.getString("after_sale_id"));
            voList.add(vo);
        }
        return voList;
    }
    public List<ExpressVO> findGoodsSku(String phone) throws SQLException {
        String result = null;
        String sql = String.format("select goods,shop_id,order_Id,after_sale_id,after_sale_status,  goods_sku,goods_size,order_status from douyin_order  where recipient_phone='%s' and (after_sale_status is null or after_sale_status!='同意退款，退款成功')", phone);
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        List<ExpressVO> voList = new ArrayList<>();
        while (set.next()) {
            ExpressVO vo = new ExpressVO();
            vo.setShopName(set.getString("shop_id"));
            vo.setOrderId(set.getString("order_id"));
            vo.setGoodsSku(set.getString("goods")+"##"+ set.getString("goods_size"));
            vo.setAfterSaleStatus(set.getString("after_sale_status"));
            vo.setAfterSaleId(set.getString("after_sale_id"));
            vo.setExpressType("");
            voList.add(vo);
        }
        return voList;
    }
    public List<DouyinOrderDO> findProductSku(String productSku, Date startTime, Date endTime) throws SQLException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String tProductSku = productSku;
        if (productSku.indexOf("GJ221") > 0) {
            productSku = "GJ221";
        }
        List<DouyinOrderDO> result = new ArrayList<>();
        String s = "\''" + sf.format(startTime);
        String s1 = "\''" + sf.format(endTime);
        String sql = String.format("select * from douyin_order  where order_status='已完成' and goods_sku='%s' and order_sumbit_time>='%s' and order_sumbit_time<='%s'", productSku, s, s1);
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        while (set.next()) {
            DouyinOrderDO vo = new DouyinOrderDO();
            vo.setRecipientPhone(set.getString("order_id"));
            vo.setGoods(set.getString("goods"));
            vo.setRecipientPhone(set.getString("recipient_phone"));
            vo.setGoodsSize(set.getString("goods_size"));
            vo.setGoodsId(set.getString("goods_id"));
            vo.setGoodsSku(set.getString("goods_sku"));
            vo.setGoodsPrice(set.getString("goods_price"));
            vo.setNumber(set.getString("number"));
            vo.setFare(set.getString("fare"));
            vo.setPreferentSum(set.getString("preferent_sum"));
            vo.setPlaformCouponSum(set.getString("plaform_coupon_sum"));
            vo.setShopCouponSum(set.getString("shop_coupon_sum"));
            vo.setShopCouponName(set.getString("shop_coupon_name"));
            vo.setActualCheque(set.getString("actual_cheque"));
            vo.setPayWay(set.getString("pay_way"));
            vo.setRecipient(set.getString("recipient"));
            vo.setRecipientPhone(set.getString("recipient_phone"));
            vo.setRecipientAddress(set.getString("recipient_address"));
            vo.setProvince(set.getString("province"));
            vo.setCity(set.getString("city"));
            vo.setArea(set.getString("area"));
            vo.setDetailedAddress(set.getString("detailed_address"));
            vo.setUserRemarks(set.getString("user_remarks"));
            vo.setExpressCompany(set.getString("express_company"));
            vo.setExpressId(set.getString("express_id"));
            vo.setOrderSumbitTime(set.getString("order_sumbit_time"));
            vo.setBusinessRemarks(set.getString("business_remarks"));
            vo.setAchieveTime(set.getString("achieve_time"));
            vo.setApp(set.getString("app"));
            vo.setBusinessType(set.getString("business_type"));
            vo.setDistributor(set.getString("distributor"));
            vo.setSupplier(set.getString("supplier"));
            vo.setOrderStatus(set.getString("order_status"));
            vo.setFinalLatestDeliveryTime(set.getString("final_latest_delivery_time"));
            vo.setOrderType(set.getString("oder_type"));
            vo.setIsAd(set.getString("is_ad"));
            vo.setAdId(set.getString("ad_id"));
            vo.setAuthorId(set.getString("author_id"));
            vo.setShopId(set.getString("shop_id"));
            vo.setRefundSum(set.getString("refund_sum"));
            vo.setRefundApplySum(set.getString("refund_apply_sum"));
            if (productSku.indexOf("GJ221") > 0) {
                if (vo.getGoodsSize().indexOf("厚") > 0) {
                    result.add(vo);
                } else if (vo.getGoodsSize().indexOf("薄") > 0) {
                    result.add(vo);
                } else
                    continue;
            } else {
                result.add(vo);
            }

        }
        return result;
    }
    public void findAll(List<DouyinOrderDO> douyinOrderDOList) throws SQLException {
        String sql = String.format("select * from douyin_order ");
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        while (set.next()) {
            DouyinOrderDO vo = new DouyinOrderDO();
            vo.setRecipientPhone(set.getString("order_id"));
            vo.setGoods(set.getString("goods"));
            vo.setRecipientPhone(set.getString("recipient_phone"));
            vo.setGoodsSize(set.getString("goods_size"));
            vo.setGoodsId(set.getString("goods_id"));
            vo.setGoodsSku(set.getString("goods_sku"));
            vo.setGoodsPrice(set.getString("goods_price"));
            vo.setNumber(set.getString("number"));
            vo.setFare(set.getString("fare"));
            vo.setPreferentSum(set.getString("preferent_sum"));
            vo.setPlaformCouponSum(set.getString("plaform_coupon_sum"));
            vo.setShopCouponSum(set.getString("shop_coupon_sum"));
            vo.setShopCouponName(set.getString("shop_coupon_name"));
            vo.setActualCheque(set.getString("actual_cheque"));
            vo.setPayWay(set.getString("pay_way"));
            vo.setRecipient(set.getString("recipient"));
            vo.setRecipientPhone(set.getString("recipient_phone"));
            vo.setRecipientAddress(set.getString("recipient_address"));
            vo.setProvince(set.getString("province"));
            vo.setCity(set.getString("city"));
            vo.setArea(set.getString("area"));
            vo.setDetailedAddress(set.getString("detailed_address"));
            vo.setUserRemarks(set.getString("user_remarks"));
            vo.setExpressCompany(set.getString("express_company"));
            vo.setExpressId(set.getString("express_id"));
            vo.setOrderSumbitTime(set.getString("order_sumbit_time"));
            vo.setBusinessRemarks(set.getString("business_remarks"));
            vo.setAchieveTime(set.getString("achieve_time"));
            vo.setApp(set.getString("app"));
            vo.setBusinessType(set.getString("business_type"));
            vo.setDistributor(set.getString("distributor"));
            vo.setSupplier(set.getString("supplier"));
            vo.setOrderStatus(set.getString("order_status"));
            vo.setFinalLatestDeliveryTime(set.getString("final_latest_delivery_time"));
            vo.setOrderType(set.getString("oder_type"));
            vo.setIsAd(set.getString("is_ad"));
            vo.setAdId(set.getString("ad_id"));
            vo.setAuthorId(set.getString("author_id"));
            vo.setShopId(set.getString("shop_id"));
            vo.setRefundSum(set.getString("refund_sum"));
            vo.setRefundApplySum(set.getString("refund_apply_sum"));
            douyinOrderDOList.add(vo);
        }

    }
    public void findShop(List<DouyinOrderDO> douyinOrderDOList) throws SQLException {
        String s = "CHEAGUIBIN";
        String sql = "select * from douyin_order where shop_id='彭小九的店'";
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        while (set.next()) {
            DouyinOrderDO vo = new DouyinOrderDO();
            vo.setRecipientPhone(set.getString("order_id"));
            vo.setGoods(set.getString("goods"));
            vo.setRecipientPhone(set.getString("recipient_phone"));
            vo.setGoodsSize(set.getString("goods_size"));
            vo.setGoodsId(set.getString("goods_id"));
            vo.setGoodsSku(set.getString("goods_sku"));
            vo.setGoodsPrice(set.getString("goods_price"));
            vo.setNumber(set.getString("number"));
            vo.setFare(set.getString("fare"));
            vo.setPreferentSum(set.getString("preferent_sum"));
            vo.setPlaformCouponSum(set.getString("plaform_coupon_sum"));
            vo.setShopCouponSum(set.getString("shop_coupon_sum"));
            vo.setShopCouponName(set.getString("shop_coupon_name"));
            vo.setActualCheque(set.getString("actual_cheque"));
            vo.setPayWay(set.getString("pay_way"));
            vo.setRecipient(set.getString("recipient"));
            vo.setRecipientPhone(set.getString("recipient_phone"));
            vo.setRecipientAddress(set.getString("recipient_address"));
            vo.setProvince(set.getString("province"));
            vo.setCity(set.getString("city"));
            vo.setArea(set.getString("area"));
            vo.setDetailedAddress(set.getString("detailed_address"));
            vo.setUserRemarks(set.getString("user_remarks"));
            vo.setExpressCompany(set.getString("express_company"));
            vo.setExpressId(set.getString("express_id"));
            vo.setOrderSumbitTime(set.getString("order_sumbit_time"));
            vo.setBusinessRemarks(set.getString("business_remarks"));
            vo.setAchieveTime(set.getString("achieve_time"));
            vo.setApp(set.getString("app"));
            vo.setBusinessType(set.getString("business_type"));
            vo.setDistributor(set.getString("distributor"));
            vo.setSupplier(set.getString("supplier"));
            vo.setOrderStatus(set.getString("order_status"));
            vo.setFinalLatestDeliveryTime(set.getString("final_latest_delivery_time"));
            vo.setOrderType(set.getString("oder_type"));
            vo.setIsAd(set.getString("is_ad"));
            vo.setAdId(set.getString("ad_id"));
            vo.setAuthorId(set.getString("author_id"));
            vo.setShopId(set.getString("shop_id"));
            vo.setRefundSum(set.getString("refund_sum"));
            vo.setRefundApplySum(set.getString("refund_apply_sum"));
            douyinOrderDOList.add(vo);
        }
    }
    public void findAllTime(List<DouyinOrderDO> douyinOrderDOList) throws SQLException {
        String s = "CHEAGUIBIN";
        String sql = "select * from douyin_order where (after_sale_type !='未发货仅退款' or after_sale_type is NULL) and goods like'%" + s + "%'";
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        while (set.next()) {
            DouyinOrderDO vo = new DouyinOrderDO();
            vo.setRecipientPhone(set.getString("order_id"));
            vo.setGoods(set.getString("goods"));
            vo.setRecipientPhone(set.getString("recipient_phone"));
            vo.setGoodsSize(set.getString("goods_size"));
            vo.setGoodsId(set.getString("goods_id"));
            vo.setGoodsSku(set.getString("goods_sku"));
            vo.setGoodsPrice(set.getString("goods_price"));
            vo.setNumber(set.getString("number"));
            vo.setFare(set.getString("fare"));
            vo.setPreferentSum(set.getString("preferent_sum"));
            vo.setPlaformCouponSum(set.getString("plaform_coupon_sum"));
            vo.setShopCouponSum(set.getString("shop_coupon_sum"));
            vo.setShopCouponName(set.getString("shop_coupon_name"));
            vo.setActualCheque(set.getString("actual_cheque"));
            vo.setPayWay(set.getString("pay_way"));
            vo.setRecipient(set.getString("recipient"));
            vo.setRecipientPhone(set.getString("recipient_phone"));
            vo.setRecipientAddress(set.getString("recipient_address"));
            vo.setProvince(set.getString("province"));
            vo.setCity(set.getString("city"));
            vo.setArea(set.getString("area"));
            vo.setDetailedAddress(set.getString("detailed_address"));
            vo.setUserRemarks(set.getString("user_remarks"));
            vo.setExpressCompany(set.getString("express_company"));
            vo.setExpressId(set.getString("express_id"));
            vo.setOrderSumbitTime(set.getString("order_sumbit_time"));
            vo.setBusinessRemarks(set.getString("business_remarks"));
            vo.setAchieveTime(set.getString("achieve_time"));
            vo.setApp(set.getString("app"));
            vo.setBusinessType(set.getString("business_type"));
            vo.setDistributor(set.getString("distributor"));
            vo.setSupplier(set.getString("supplier"));
            vo.setOrderStatus(set.getString("order_status"));
            vo.setFinalLatestDeliveryTime(set.getString("final_latest_delivery_time"));
            vo.setOrderType(set.getString("oder_type"));
            vo.setIsAd(set.getString("is_ad"));
            vo.setAdId(set.getString("ad_id"));
            vo.setAuthorId(set.getString("author_id"));
            vo.setShopId(set.getString("shop_id"));
            vo.setRefundSum(set.getString("refund_sum"));
            vo.setRefundApplySum(set.getString("refund_apply_sum"));
            douyinOrderDOList.add(vo);
        }
    }
    public boolean updateAfterSale(AfterSaleVO vo) throws SQLException {
        //'4722266458341220002
        String sql = "update  douyin_order SET   after_sale_id=? ,after_sale_type=? ,after_sale_express_id=?,after_sale_status=? WHERE order_id=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getAfterSaleId());
        pStmt.setString(2, vo.getAfterSaleType());
        pStmt.setString(3, vo.getAfterSaleExpressId().trim().toUpperCase());
        pStmt.setString(4, vo.getAfterSaleStatus());
        pStmt.setString(5, vo.getOrderId());
        return pStmt.executeUpdate() > 0;
    }
    public boolean updateCustomerInfo(CustomerVO vo) throws SQLException {
        String sql = "update  douyin_order SET   recipient=? ,recipient_phone=? ,recipient_address=? WHERE order_id=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getRealName());
        pStmt.setString(2, vo.getUserPhone());
        pStmt.setString(3, vo.getAddress());
        pStmt.setString(4, vo.getOrderId());
        return pStmt.executeUpdate() > 0;
    }

    public boolean batchUpdateAfterSale(List<AfterSaleVO> voList) throws SQLException {
        conn.setAutoCommit(false);
        String sql = "update  douyin_order SET   after_sale_id=? ,after_sale_type=? ,after_sale_express_id=?,after_sale_status=?  WHERE order_id=?";
        pStmt = conn.prepareStatement(sql);
        for (AfterSaleVO vo : voList) {  //i=1000  2000
            pStmt.setString(1, vo.getAfterSaleId());
            pStmt.setString(2, vo.getAfterSaleType());
            pStmt.setString(3, vo.getAfterSaleExpressId());
            pStmt.setString(4, vo.getAfterSaleStatus());
            pStmt.setString(5, vo.getOrderId());
            pStmt.addBatch();
        }
        pStmt.executeBatch();
        pStmt.clearBatch();
        return true;
    }

    public boolean doBatchInsert(List<DouyinOrderDO> voList) throws SQLException {
        conn.setAutoCommit(false);
        String sql = "insert into douyin_order(fare,detailed_address,user_remarks,city,after_sale_type,goods_price,plaform_coupon_sum,goods,refund_sum,express_company,refund_apply_sum,pay_way,goods_sku,number,order_status,business_remarks,achieve_time,province,order_sumbit_time,after_sale_id,plaform_coupon_name,business_type,supplier,user_nickname,final_latest_delivery_time,order_type,area,app,is_ad,shop_coupon_name,oder_type,goods_id,recipient_address,preferent_sum,distributor,express_id,shop_id,goods_size,actual_cheque,ad_id,shop_coupon_sum,recipient,author_id,order_id,recipient_phone)values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        pStmt = conn.prepareStatement(sql);
        for (DouyinOrderDO vo : voList) {
            pStmt.setString(1, vo.getFare());
            pStmt.setString(2, vo.getDetailedAddress());
            pStmt.setString(3, vo.getUserRemarks());
            pStmt.setString(4, vo.getCity());
            pStmt.setString(5, vo.getAfterSaleType());
            pStmt.setString(6, vo.getGoodsPrice());
            pStmt.setString(7, vo.getPlaformCouponSum());
            pStmt.setString(8, vo.getGoods());
            pStmt.setString(9, vo.getRefundSum());
            pStmt.setString(10, vo.getExpressCompany());
            pStmt.setString(11, vo.getRefundApplySum());
            pStmt.setString(12, vo.getPayWay());
            pStmt.setString(13, vo.getGoodsSku());
            pStmt.setString(14, vo.getNumber());
            pStmt.setString(15, vo.getOrderStatus());
            pStmt.setString(16, vo.getBusinessRemarks());
            pStmt.setString(17, vo.getAchieveTime());
            pStmt.setString(18, vo.getProvince());
            pStmt.setString(19, vo.getOrderSumbitTime());
            pStmt.setString(20, vo.getAfterSaleId());
            pStmt.setString(21, vo.getPlaformCouponName());
            pStmt.setString(22, vo.getBusinessType());
            pStmt.setString(23, vo.getSupplier());
            pStmt.setString(24, vo.getUserNickname());
            pStmt.setString(25, vo.getFinalLatestDeliveryTime());
            pStmt.setString(26, vo.getOrderType());
            pStmt.setString(27, vo.getArea());
            pStmt.setString(28, vo.getApp());
            pStmt.setString(29, vo.getIsAd());
            pStmt.setString(30, vo.getShopCouponName());
            pStmt.setString(31, vo.getOderType());
            pStmt.setString(32, vo.getGoodsId());
            pStmt.setString(33, vo.getRecipientAddress());
            pStmt.setString(34, vo.getPreferentSum());
            pStmt.setString(35, vo.getDistributor());
            pStmt.setString(36, vo.getExpressId());
            pStmt.setString(37, vo.getShopId());
            pStmt.setString(38, vo.getGoodsSize());
            pStmt.setString(39, vo.getActualCheque());
            pStmt.setString(40, vo.getAdId());
            pStmt.setString(41, vo.getShopCouponSum());
            pStmt.setString(42, vo.getRecipient());
            pStmt.setString(43, vo.getAuthorId());
            pStmt.setString(44, vo.getOrderId());
            pStmt.setString(45, vo.getRecipientPhone());
            pStmt.addBatch();
        }
        pStmt.executeBatch();
        pStmt.clearBatch();
        return true;
    }

    //end
    public boolean doInsert(DouyinOrderDO vo) throws SQLException {
        String sql = "insert into douyin_order(fare,detailed_address,user_remarks,city,after_sale_type,goods_price,plaform_coupon_sum,goods,refund_sum,express_company,refund_apply_sum,pay_way,goods_sku,number,order_status,business_remarks,achieve_time,province,order_sumbit_time,after_sale_id,after_sale_express_id,plaform_coupon_name,business_type,supplier,user_nickname,final_latest_delivery_time,order_type,area,app,is_ad,shop_coupon_name,oder_type,goods_id,recipient_address,preferent_sum,distributor,express_id,shop_id,goods_size,actual_cheque,ad_id,shop_coupon_sum,after_sale_status,recipient,author_id,order_id,recipient_phone,main_order_id)values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" +
                "ON CONFLICT (order_id) DO UPDATE  SET order_status = ? , business_remarks=?,express_id=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getFare());
        pStmt.setString(2, vo.getDetailedAddress());
        pStmt.setString(3, vo.getUserRemarks());
        pStmt.setString(4, vo.getCity());
        pStmt.setString(5, vo.getAfterSaleType());
        pStmt.setString(6, vo.getGoodsPrice());
        pStmt.setString(7, vo.getPlaformCouponSum());
        pStmt.setString(8, vo.getGoods());
        pStmt.setString(9, vo.getRefundSum());
        pStmt.setString(10, vo.getExpressCompany());
        pStmt.setString(11, vo.getRefundApplySum());
        pStmt.setString(12, vo.getPayWay());
        pStmt.setString(13, vo.getGoodsSku());
        pStmt.setString(14, vo.getNumber());
        pStmt.setString(15, vo.getOrderStatus());
        pStmt.setString(16, vo.getBusinessRemarks());
        pStmt.setString(17, vo.getAchieveTime());
        pStmt.setString(18, vo.getProvince());
        pStmt.setString(19, vo.getOrderSumbitTime());
        pStmt.setString(20, vo.getAfterSaleId());
        pStmt.setString(21, vo.getAfterSaleExpressId());
        pStmt.setString(22, vo.getPlaformCouponName());
        pStmt.setString(23, vo.getBusinessType());
        pStmt.setString(24, vo.getSupplier());
        pStmt.setString(25, vo.getUserNickname());
        pStmt.setString(26, vo.getFinalLatestDeliveryTime());
        pStmt.setString(27, vo.getOrderType());
        pStmt.setString(28, vo.getArea());
        pStmt.setString(29, vo.getApp());
        pStmt.setString(30, vo.getIsAd());
        pStmt.setString(31, vo.getShopCouponName());
        pStmt.setString(32, vo.getOderType());
        pStmt.setString(33, vo.getGoodsId());
        pStmt.setString(34, vo.getRecipientAddress());
        pStmt.setString(35, vo.getPreferentSum());
        pStmt.setString(36, vo.getDistributor());
        pStmt.setString(37, vo.getExpressId());
        pStmt.setString(38, vo.getShopId());
        pStmt.setString(39, vo.getGoodsSize());
        pStmt.setString(40, vo.getActualCheque());
        pStmt.setString(41, vo.getAdId());
        pStmt.setString(42, vo.getShopCouponSum());
        pStmt.setString(43, vo.getAfterSaleStatus());
        pStmt.setString(44, vo.getRecipient());
        pStmt.setString(45, vo.getAuthorId());
        pStmt.setString(46, vo.getOrderId());
        pStmt.setString(47, vo.getRecipientPhone());
        pStmt.setString(48, vo.getOrderStatus());
        pStmt.setString(49, vo.getBusinessRemarks());
        pStmt.setString(50, vo.getExpressId());
        pStmt.setString(51, vo.getMainOrderId());
        return pStmt.execute();
    }

    public boolean doUpdate(DouyinOrderDO vo) throws SQLException {
        String sql = "update  douyin_order SET  fare=? , detailed_address=? , user_remarks=? , city=? , after_sale_type=? , goods_price=? , plaform_coupon_sum=? , goods=? , refund_sum=? , express_company=? , refund_apply_sum=? , pay_way=? , goods_sku=? , number=? , order_status=? , business_remarks=? , achieve_time=? , province=? , order_sumbit_time=? , after_sale_id=? , after_sale_express_id=? , plaform_coupon_name=? , business_type=? , supplier=? , user_nickname=? , final_latest_delivery_time=? , order_type=? , area=? , app=? , is_ad=? , shop_coupon_name=? , oder_type=? , goods_id=? , recipient_address=? , preferent_sum=? , distributor=? , express_id=? , shop_id=? , goods_size=? , actual_cheque=? , ad_id=? , shop_coupon_sum=? , after_sale_status=? , recipient=? , author_id=? , order_id=? , recipient_phone=?WHERE order_id=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getFare());
        pStmt.setString(2, vo.getDetailedAddress());
        pStmt.setString(3, vo.getUserRemarks());
        pStmt.setString(4, vo.getCity());
        pStmt.setString(5, vo.getAfterSaleType());
        pStmt.setString(6, vo.getGoodsPrice());
        pStmt.setString(7, vo.getPlaformCouponSum());
        pStmt.setString(8, vo.getGoods());
        pStmt.setString(9, vo.getRefundSum());
        pStmt.setString(10, vo.getExpressCompany());
        pStmt.setString(11, vo.getRefundApplySum());
        pStmt.setString(12, vo.getPayWay());
        pStmt.setString(13, vo.getGoodsSku());
        pStmt.setString(14, vo.getNumber());
        pStmt.setString(15, vo.getOrderStatus());
        pStmt.setString(16, vo.getBusinessRemarks());
        pStmt.setString(17, vo.getAchieveTime());
        pStmt.setString(18, vo.getProvince());
        pStmt.setString(19, vo.getOrderSumbitTime());
        pStmt.setString(20, vo.getAfterSaleId());
        pStmt.setString(21, vo.getAfterSaleExpressId());
        pStmt.setString(22, vo.getPlaformCouponName());
        pStmt.setString(23, vo.getBusinessType());
        pStmt.setString(24, vo.getSupplier());
        pStmt.setString(25, vo.getUserNickname());
        pStmt.setString(26, vo.getFinalLatestDeliveryTime());
        pStmt.setString(27, vo.getOrderType());
        pStmt.setString(28, vo.getArea());
        pStmt.setString(29, vo.getApp());
        pStmt.setString(30, vo.getIsAd());
        pStmt.setString(31, vo.getShopCouponName());
        pStmt.setString(32, vo.getOderType());
        pStmt.setString(33, vo.getGoodsId());
        pStmt.setString(34, vo.getRecipientAddress());
        pStmt.setString(35, vo.getPreferentSum());
        pStmt.setString(36, vo.getDistributor());
        pStmt.setString(37, vo.getExpressId());
        pStmt.setString(38, vo.getShopId());
        pStmt.setString(39, vo.getGoodsSize());
        pStmt.setString(40, vo.getActualCheque());
        pStmt.setString(41, vo.getAdId());
        pStmt.setString(42, vo.getShopCouponSum());
        pStmt.setString(43, vo.getAfterSaleStatus());
        pStmt.setString(44, vo.getRecipient());
        pStmt.setString(45, vo.getAuthorId());
        pStmt.setString(46, vo.getOrderId());
        pStmt.setString(47, vo.getRecipientPhone());
        pStmt.setString(48, vo.getOrderId());
        return pStmt.executeUpdate() > 0;
    }

    public boolean doDelete(DouyinOrderDO vo) throws SQLException {
        String sql = String.format("DELETE FROM  douyin_order WHERE order_id=", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        return pStmt.execute();
    }

    public String findFare(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select fare from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("fare");
        return result;
    }

    public String findDetailedAddress(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select detailed_address from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("detailed_address");
        return result;
    }

    public String findUserRemarks(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select user_remarks from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("user_remarks");
        return result;
    }

    public String findCity(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select city from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("city");
        return result;
    }

    public String findAfterSaleType(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select after_sale_type from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("after_sale_type");
        return result;
    }

    public String findGoodsPrice(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select goods_price from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("goods_price");
        return result;
    }

    public String findPlaformCouponSum(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select plaform_coupon_sum from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("plaform_coupon_sum");
        return result;
    }

    public String findGoods(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select goods from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("goods");
        return result;
    }

    public String findRefundSum(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select refund_sum from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("refund_sum");
        return result;
    }

    public String findExpressCompany(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select express_company from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("express_company");
        return result;
    }

    public String findRefundApplySum(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select refund_apply_sum from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("refund_apply_sum");
        return result;
    }

    public String findPayWay(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select pay_way from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("pay_way");
        return result;
    }
    public DouyinOrderDO findGoodss(String orderId) throws SQLException {
        String result = null;
        String sql = String.format("select goods,goods_size,after_sale_id,after_sale_status from douyin_order  where order_id='%s'", orderId);
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        DouyinOrderDO douyinOrderDO=new DouyinOrderDO();
        if (set.getRow() == 1) {
            douyinOrderDO.setGoods(set.getString("goods"));
            douyinOrderDO.setGoodsSize(set.getString("goods_size"));
            douyinOrderDO.setAfterSaleId(set.getString("after_sale_id"));
            douyinOrderDO.setAfterSaleStatus(set.getString("after_sale_status"));
        }
        return douyinOrderDO;
    }
    public String findGoodsSku(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select goods_sku from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("goods_sku");
        return result;
    }

    public String findNumber(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select number from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("number");
        return result;
    }

    public String findOrderStatus(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select order_status from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("order_status");
        return result;
    }

    public String findBusinessRemarks(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select business_remarks from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("business_remarks");
        return result;
    }

    public String findAchieveTime(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select achieve_time from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("achieve_time");
        return result;
    }

    public String findProvince(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select province from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("province");
        return result;
    }

    public String findOrderSumbitTime(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select order_sumbit_time from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("order_sumbit_time");
        return result;
    }

    public String findAfterSaleId(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select after_sale_id from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("after_sale_id");
        return result;
    }

    public String findAfterSaleExpressId(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select after_sale_express_id from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("after_sale_express_id");
        return result;
    }

    public String findPlaformCouponName(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select plaform_coupon_name from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("plaform_coupon_name");
        return result;
    }

    public String findBusinessType(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select business_type from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("business_type");
        return result;
    }

    public String findSupplier(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select supplier from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("supplier");
        return result;
    }

    public String findUserNickname(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select user_nickname from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("user_nickname");
        return result;
    }

    public String findFinalLatestDeliveryTime(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select final_latest_delivery_time from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("final_latest_delivery_time");
        return result;
    }

    public String findOrderType(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select order_type from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("order_type");
        return result;
    }

    public String findArea(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select area from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("area");
        return result;
    }

    public String findApp(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select app from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("app");
        return result;
    }

    public String findIsAd(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select is_ad from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("is_ad");
        return result;
    }

    public String findShopCouponName(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select shop_coupon_name from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("shop_coupon_name");
        return result;
    }

    public String findOderType(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select oder_type from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("oder_type");
        return result;
    }

    public String findGoodsId(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select goods_id from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("goods_id");
        return result;
    }

    public String findRecipientAddress(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select recipient_address from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("recipient_address");
        return result;
    }

    public String findPreferentSum(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select preferent_sum from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("preferent_sum");
        return result;
    }

    public String findDistributor(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select distributor from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("distributor");
        return result;
    }

    public String findExpressId(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select express_id from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("express_id");
        return result;
    }

    public String findShopId(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select shop_id from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("shop_id");
        return result;
    }

    public String findGoodsSize(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select goods_size from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("goods_size");
        return result;
    }

    public String findActualCheque(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select actual_cheque from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("actual_cheque");
        return result;
    }

    public String findAdId(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select ad_id from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("ad_id");
        return result;
    }

    public String findShopCouponSum(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select shop_coupon_sum from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("shop_coupon_sum");
        return result;
    }

    public String findAfterSaleStatus(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select after_sale_status from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("after_sale_status");
        return result;
    }

    public String findRecipient(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select recipient from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("recipient");
        return result;
    }

    public String findAuthorId(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select author_id from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("author_id");
        return result;
    }

    public String findRecipientPhone(DouyinOrderDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select recipient_phone from douyin_order  where order_id='%s'", vo.getOrderId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("recipient_phone");
        return result;
    }
}
