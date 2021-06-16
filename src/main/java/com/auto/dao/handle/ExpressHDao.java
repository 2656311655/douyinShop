package com.auto.dao.handle;

import com.auto.dao.*;
import com.auto.modal.vo.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;

/**
 * @Classname ExpressDao
 * @Description TODO
 * @Date 2020/12/14 20:14
 * @Created by Administrator
 */
public class ExpressHDao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private String tableName = "douyin_after_sale";
    public static Set<String> clothesColorList = new HashSet<>();
    public static Set<String> clothesSizeList = new HashSet<>();
    public static Set<String> clothesSkuList = new HashSet<>();


    public ExpressHDao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;


    }
    public Boolean findExpressPrint(ExpressPrintDO vo) throws SQLException {
        String sql = String.format("select * from express_print  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();

        if (set.getRow() == 1){
            vo.setRecipient(set.getString("recipient"));
            vo.setRecipientPhone(set.getString("recipient_phone"));
            vo.setRecipientAddress(set.getString("recipient_address"));
            vo.setExpressPrintTime(set.getString("express_print_time"));
        }
        return true;
    }
    public List<ExpressLogisticsDO> findExpressLogistics(String startPrintTime,String endPrintTime) throws SQLException {
        List<ExpressLogisticsDO> result = new ArrayList();
        String sql = String.format("select  * FROM express_logistics  where express_record_time>'%s' and express_record_time<'%s'  ORDER BY express_record_time DESC ",startPrintTime,endPrintTime);
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        while (set.next()) {
            ExpressLogisticsDO expressLogisticsDO=new ExpressLogisticsDO();
            expressLogisticsDO.setExpressId(set.getString("express_id"));
            expressLogisticsDO.setExpressRecordTime(set.getString("express_record_time"));
            expressLogisticsDO.setExpressRecord(set.getString("express_record"));
            result.add(expressLogisticsDO);
        }
        return result;
    }
    public List<SendAdviceVO> findSenAdvices(String shopName) throws SQLException {
        List<SendAdviceVO> result = new ArrayList();
        String sql = String.format("select  * FROM order_info  where order_status='备货中' and shop_name='%s'",shopName);
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        while (set.next()) {
            SendAdviceVO vo = new SendAdviceVO();
            String goodsCode=set.getString("goods_code");

            String[] goodsCodes=goodsCode.split(" ");
            vo.setGoodsCode(goodsCodes[0]);
            vo.setGoodsName(goodsCodes[0]);
            String[] goodsSku=goodsCodes[1].split("/");
            vo.setGoodsColor(goodsSku[0]);
            if(goodsSku[1].indexOf("【")>=0)
                goodsSku[1]=goodsSku[1].replace(goodsSku[1].substring(goodsSku[1].indexOf("【"),goodsSku[1].length()-1),"");
            vo.setGoodsSize(goodsSku[1]);
            Integer i=set.getInt("goods_number");
            if(i==0)
                vo.setGoodsNumber(1);
            else
                vo.setGoodsNumber(set.getInt("goods_number"));
            result.add(vo);


        }
        return result;
    }
    public List<SenderVO> findShopOrderId(String shopName) throws SQLException {
        List<SenderVO> result = new ArrayList();
        String sql = String.format("SELECT order_info.express_id,express_print.express_print_time ,order_info.order_id, order_info.goods_number, express_print.recipient,express_print.recipient_phone,\n" +
                "express_print.recipient_address,order_info.goods_code FROM order_info,express_print where shop_name='%s'    \n" +
                "and express_print.express_print_time>'2020-01-01  00:00:00'  and  express_print.express_id=order_info.express_id\n" +
                "ORDER BY order_submit_time ",shopName);
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();

        while (set.next()) {
            SenderVO vo = new SenderVO();
            vo.setExpressId(set.getString("express_id"));
            vo.setExpressPrintTime(set.getString("express_print_time"));
            vo.setOrderId(set.getString("order_id"));
            if(set.getString("goods_number")==null){
                vo.setGoodsNumber("1");
            }
            else
                vo.setGoodsNumber(set.getString("goods_number"));
            vo.setRecipient(set.getString("recipient"));
            vo.setRecipientPhone(set.getString("recipient_phone"));
            vo.setRecipientAddress(set.getString("recipient_address"));
            String goodsCode=set.getString("goods_code");
            String[] goodsCodes=goodsCode.split(" ");

            if(goodsCode.indexOf("/")>=0){
                goodsCodes=goodsCode.split("/");
            }
            if(goodsCodes[0].equals("K06")){
                goodsCodes[0]="GJK06";
            }
            if(goodsCodes[0].equals("K02")){
                goodsCodes[0]="GJK02";
            }
            if(goodsCodes[0].equals("K01")){
                goodsCodes[0]="GJK01";
            }
            vo.setGoodsCode(goodsCodes[0]);

            result.add(vo);
        }
        return result;
    }
    public List<SenderVO> findShopOrder(String shopName) throws SQLException {
        List<SenderVO> result = new ArrayList();
        String sql = String.format("SELECT order_info.express_id,express_print.express_print_time ,order_info.order_id, order_info.goods_number, express_print.recipient,express_print.recipient_phone,\n" +
                "express_print.recipient_address,order_info.goods_code\n" +
                "FROM order_info,express_print,express_process where shop_name='%s'    \n" +
                "and express_print.express_print_time>'2020-01-01 00:00:00'  and  express_print.express_id=order_info.express_id\n" +
                "and express_process.express_id=order_info.express_id ",shopName);
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        while (set.next()) {
            SenderVO vo = new SenderVO();
            vo.setExpressId(set.getString("express_id"));
            vo.setExpressPrintTime(set.getString("express_print_time"));
            vo.setOrderId(set.getString("order_id"));
            vo.setGoodsNumber(set.getString("goods_number"));
            vo.setRecipient(set.getString("recipient"));
            vo.setRecipientPhone(set.getString("recipient_phone"));
            vo.setRecipientAddress(set.getString("recipient_address"));
            vo.setGoodsCode(set.getString("goods_code"));

            result.add(vo);
        }
        return result;
    }
    public List<GoodsInfoVO> findGoodsSkus() throws SQLException {
        List<GoodsInfoVO> goodsInfoVOList=new ArrayList<>();
        String sql = String.format("select * from  douyin_order where (goods_sku='GJK201' or goods_sku='GJ10') and  ((after_sale_type!='未发货仅退款' or after_sale_id is null) and order_status!='已关闭' )");
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        while (set.next()){
            GoodsInfoVO goodsInfoVO=new GoodsInfoVO();
            goodsInfoVO.setGoodsCode(set.getString("goods_sku"));
            goodsInfoVO.setGoodsSku(set.getString("goods_size"));
            goodsInfoVOList.add(goodsInfoVO);
        }
        return goodsInfoVOList;
    }
    public List<ExpressPrintVO> finExchangeExpress() throws SQLException {
        List<ExpressPrintVO> expressPrintVOS=new ArrayList<>();
        String sql = String.format("select * from express_package,express_process where \n" +
                "express_type='换货件' and shop_name='抖嘉' and order_id is  not null and order_id !='1' and express_process.express_id=express_package.express_id and express_scan_time>'2021-01-17 22:00:00'");
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        while (set.next()) {
            ExpressPrintVO expressPrintVO = new ExpressPrintVO();
            expressPrintVO.setExpressType(set.getString("express_type"));
            String[] s=set.getString("express_package").split(" ");
            if(s.length==3) {
                expressPrintVO.setGoodsCode(s[0]);
                expressPrintVO.setGoodsColor(s[1]);
                expressPrintVO.setGoodsSize(s[2]);
            }
            String orderId=set.getString("order_id");
            sql = String.format("select * from douyin_order WHERE order_id='%s' ",orderId);
            pStmt = conn.prepareStatement(sql);
            ResultSet set1 = pStmt.executeQuery();
            set1.next();

            if(set1.getRow()==1) {
                CustomerVO customer = new CustomerVO();
                customer.setOrderId(orderId);
                customer.setRealName(set1.getString("recipient"));
                customer.setUserPhone(set1.getString("recipient_phone"));
                customer.setAddress(set1.getString("recipient_address"));
                expressPrintVO.setCustomer(customer);
            }
            else
                continue;
            expressPrintVOS.add(expressPrintVO);
        }
        return  expressPrintVOS;
    }
    public int reviseExpressId()throws SQLException{
        //expressId 修正
        String sql = String.format("select express_id from express_package");
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        ExpressPackageDao expressPackageDao=new ExpressPackageDao(conn);
        while (set.next()) {
            ExpressPackageDO expressPackageDO=new ExpressPackageDO();
            expressPackageDO.setExpressRemark(set.getString("express_id"));
            expressPackageDO.setExpressId(set.getString("express_id").trim().toUpperCase());
            expressPackageDao.doUpdateExpressId(expressPackageDO);
        }
        return 0;
    }
    public List<ExpressPackageDO> findExpressOrderId(String shopName) throws SQLException {
        List<ExpressPackageDO> expressPackageDOList = new ArrayList<>();
        String sql = String.format("select * from express_package,express_process where express_package.express_id=express_process.express_id and (after_sale_status='待买家退货' or after_sale_status='待商家收货') and (express_business_status is null or (express_business_status !='退货退款成功' and express_business_status !='已备注') ) and shop_name='%s'and express_type!='换货件' ", shopName);
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        while (set.next()) {
            String s = set.getString("express_type");

            ExpressPackageDO expressPackageDO = new ExpressPackageDO();

            expressPackageDO.setOrderId(set.getString("order_id"));
            expressPackageDO.setShopName(set.getString("shop_name"));
            expressPackageDO.setExpressId(set.getString("express_id"));
            if (expressPackageDO.getExpressId().equals("YT5158335761773")) {
                System.out.println();
            }
            if (expressPackageDO.getOrderId().equals("undefined") || expressPackageDO.getOrderId().equals("")) {
                continue;
            }
            expressPackageDO.setAfterSaleId(set.getString("after_sale_id"));
            expressPackageDO.setExpressPackage(set.getString("express_package"));
            if (s.equals("换货件")) {
                System.out.println(expressPackageDO.getOrderId());
            }
            expressPackageDOList.add(expressPackageDO);
        }
        return expressPackageDOList;
    }
    public boolean updateExpressBusinessStatus(ExpressProcessDO vo) throws SQLException {
        String sql = "update  express_process SET express_business_process_person=?, express_business_process_time=?, express_business_status=? WHERE express_id=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getExpressBusinessProcessPerson());
        Timestamp ts = new Timestamp(new Date().getTime());
        pStmt.setTimestamp(2, ts);
        pStmt.setString(3, vo.getExpressBusinessStatus());
        pStmt.setString(4, vo.getExpressId());
        return pStmt.executeUpdate() > 0;
    }
    //handle
    public List<ExpressPackageDO> findAllExpress(String startTime ) throws SQLException {

        List<ExpressPackageDO> expressVOList = new ArrayList<>();
        String sql = String.format("select  express_id,goods_sku,goods_size,shop_id,order_id,after_sale_id,after_sale_status,business_type,user_remarks from douyin_order where  express_id =' ' and order_sumbit_time>? ");
        //sql="select * from douyin_order where  express_id='YT5126684654333'";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1,startTime);
        ResultSet set = pStmt.executeQuery();
        while (set.next()) {
            ExpressPackageDO vo = new ExpressPackageDO();
            vo.setExpressId(set.getString("express_id"));
            if(vo.getExpressId().equals("YT5094771782855")){
                System.out.println();
            }
            String s=set.getString("goods_sku")+set.getString("goods_size");
            vo.setExpressPackage(s);
            vo.setExpressType("发货件");
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
        //
        sql = String.format("select  after_sale_express_id,goods_sku,goods_size,shop_id,order_id,after_sale_id,after_sale_status,business_type,user_remarks from douyin_order\n" +
                "where  after_sale_express_id is not null and after_sale_express_id!=' '   ");
        //sql="select * from douyin_order where  after_sale_express_id='4310834450145'";
        pStmt = conn.prepareStatement(sql);
        set = pStmt.executeQuery();
        while (set.next()) {
            ExpressPackageDO vo = new ExpressPackageDO();
            vo.setExpressId(set.getString("after_sale_express_id"));
            if(vo.getExpressId().equals("YT5117170496765")){
                System.out.println();
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
            vo.setExpressPackage(set.getString("goods_sku") + set.getString("goods_size"));
            vo.setAfterSaleStatus(set.getString("after_sale_status"));
            vo.setAfterSaleId(set.getString("after_sale_id"));
            voList.add(vo);
        }
        return voList;
    }

    public List<ExpressVO> findGoodsSku(String phone) throws SQLException {
        String result = null;
        String sql = String.format("select   order_info.order_id,order_info.shop_name,goods_code,customer_name,customer_phone from order_info,customer where customer_id=recipent_id  and customer_phone ='%s'", phone);
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        List<ExpressVO> voList = new ArrayList<>();
        while (set.next()) {
            ExpressVO vo = new ExpressVO();
            vo.setShopName(set.getString("shop_name"));
            vo.setOrderId(set.getString("order_id"));
            vo.setGoodsSku(set.getString("goods_code"));
            vo.setAfterSaleStatus("");
            vo.setAfterSaleId("");
            vo.setExpressType("未知件");
            voList.add(vo);
        }
        return voList;
    }

    public List<ExpressVO> findGoodsSkuToOrderId(String orderId) throws SQLException {
        String result = null;
        String sql = String.format("select   order_id,shop_name,goods_code from order_info where   order_id like '%s'  limit 5", "%" + orderId + "%");
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        List<ExpressVO> voList = new ArrayList<>();
        while (set.next()) {
            ExpressVO vo = new ExpressVO();
            vo.setShopName(set.getString("shop_name"));
            vo.setOrderId(set.getString("order_id"));
            vo.setGoodsSku(set.getString("goods_code"));
            vo.setAfterSaleStatus("");
            vo.setAfterSaleId("");
            vo.setExpressType("未知件");
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
        String s = "''" + sf.format(startTime);
        String s1 = "''" + sf.format(endTime);
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

        String sql = "update  douyin_order SET   after_sale_id=? ,after_sale_type=? ,after_sale_express_id=?,after_sale_status=?  WHERE order_id=?";
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
    public static void main(String[] args) throws Exception {
    }
}
