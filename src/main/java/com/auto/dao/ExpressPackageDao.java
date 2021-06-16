package com.auto.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.auto.dao.ExpressPackageDO;
import com.auto.modal.vo.ExpressVO;

/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020-12-04
 */
public class ExpressPackageDao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private String tableName = "express_package";

    public ExpressPackageDao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;
    }

    //handle

    public List<ExpressVO> findExpress(String expressId) throws SQLException {
        List<ExpressVO> expressVOList=new ArrayList<>();
        String sql = String.format("select * from express_package  where express_id='%s'", expressId);
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        while (set.next()) {
            ExpressVO vo=new ExpressVO();
            vo.setAfterSaleExpressId(expressId);
            vo.setOrderId(set.getString("order_id"));
            vo.setAfterSaleId(set.getString("after_sale_id"));
            vo.setAfterSaleStatus(set.getString("after_sale_status"));
            vo.setGoodsSku(set.getString("express_package"));
            vo.setExpressType(set.getString("express_type"));
            vo.setShopName(set.getString("shop_name"));
            expressVOList.add(vo);
        }
        if(expressVOList.size()==0){
            //sql = String.format("select * from order_info,after_sale where express_id = '%s' and order_info.main_order_id =after_sale.order_id", expressId);
            sql = String.format("select * from order_info where express_id = '%s'",expressId);
            pStmt = conn.prepareStatement(sql);
            set = pStmt.executeQuery();
            while (set.next()) {
                ExpressVO vo=new ExpressVO();
                //vo.setAfterSaleId(set.getString("after_sale_id"));
                //vo.setAfterSaleStatus(set.getString("after_sale_status"));
                vo.setOrderId(set.getString("order_id"));
                sql="select * from after_sale where  order_id=?";
                pStmt = conn.prepareStatement(sql);
                pStmt.setString(1,vo.getOrderId());
                try {
                    ResultSet set1 = pStmt.executeQuery();
                    while (set1.next()){
                        vo.setAfterSaleId(set.getString("after_sale_id"));
                        vo.setAfterSaleStatus(set.getString("after_sale_status"));
                    }
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }


                vo.setAfterSaleId("");
                vo.setAfterSaleStatus("");
                vo.setAfterSaleExpressId(expressId);

                String s=set.getString("goods_code");
                vo.setGoodsSku(s);
                vo.setExpressType("拦截件");
                vo.setShopName(set.getString("shop_name"));
                expressVOList.add(vo);
            }

        }
        return expressVOList;

    }
    public Boolean doUpdateExpressType(String expressId,String expressType) throws SQLException{
        String sql = "update  express_package SET   express_type=? WHERE express_id=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, expressType);
        pStmt.setString(2, expressId);
        return pStmt.executeUpdate() > 0;
    }
    public boolean doInsert(ExpressPackageDO vo) throws SQLException {
        if(vo.getExpressId().equals("4310834450145")){
            System.out.println("");
        }
        String sql = "insert into express_package(express_id,after_sale_id,express_status,express_package,after_sale_status,shop_name,order_id,express_type,express_remark)values (?,?,?,?,?,?,?,?,?)";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getExpressId());
        pStmt.setString(2, vo.getAfterSaleId());
        pStmt.setString(3, vo.getExpressStatus());
        pStmt.setString(4, vo.getExpressPackage());
        pStmt.setString(5, vo.getAfterSaleStatus());
        pStmt.setString(6, vo.getShopName());
        pStmt.setString(7, vo.getOrderId());
        pStmt.setString(8, vo.getExpressType());
        pStmt.setString(9, vo.getExpressRemark());
        return pStmt.execute();
    }
    public String findExpressId(ExpressPackageDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select express_id from express_package  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("express_id");
        return result;
    }
    public boolean doUpdate(ExpressPackageDO vo) throws SQLException {
        String sql = "insert into express_package(express_id,after_sale_id,express_status,express_package,after_sale_status,shop_name,order_id,express_type,express_remark)values (?,?,?,?,?,?,?,?,?) on conflict(express_id,order_id) do update   SET  express_id=? , after_sale_id=? , express_status=? , express_package=? , after_sale_status=? , shop_name=? , order_id=? , express_type=?, express_remark=? where express_package.express_id=? and express_package.order_id=?";
       // sql="insert into express_package(express_id,after_sale_id,express_status,express_package,after_sale_status,shop_name,order_id,express_type,express_remark)values ('3104251359467','6900056217528434957','','GJ10 黑色；L','售后关闭','尤苛优品','4728989679884642053','','寄：null\n" +
      //          "null') on conflict(express_id,order_id) do update   SET  express_id='3104251359467' , after_sale_id='6900056217528434957' , express_status='' , express_package='GJ10 黑色；L' , after_sale_status='售后关闭' , shop_name='尤苛优品' , order_id='4728989679884642053' , express_type='', express_remark='寄：null\n" +
       //         "null' where express_id='3104251359467' and order_id='4728989679884642053'";
        pStmt = conn.prepareStatement(sql);
        if(vo.getOrderId().equals("4743869757290423456")){
            System.out.println("");
        }
        pStmt.setString(1, vo.getExpressId().trim().toUpperCase());
        pStmt.setString(2, vo.getAfterSaleId());
        pStmt.setString(3, vo.getExpressStatus());
        pStmt.setString(4, vo.getExpressPackage());
        pStmt.setString(5, vo.getAfterSaleStatus());
        pStmt.setString(6, vo.getShopName());
        pStmt.setString(7, vo.getOrderId());
        pStmt.setString(8, vo.getExpressType());
        pStmt.setString(9, vo.getExpressRemark());
        pStmt.setString(10, vo.getExpressId());
        pStmt.setString(11, vo.getAfterSaleId());
        pStmt.setString(12, vo.getExpressStatus());
        pStmt.setString(13, vo.getExpressPackage());
        pStmt.setString(14, vo.getAfterSaleStatus());
        pStmt.setString(15, vo.getShopName());
        pStmt.setString(16, vo.getOrderId());
        pStmt.setString(17, vo.getExpressType());
        pStmt.setString(18, vo.getExpressRemark());
        pStmt.setString(19, vo.getExpressId());
        pStmt.setString(20, vo.getOrderId());
        return pStmt.executeUpdate() > 0;
    }
    public boolean doUpdateExpressId(ExpressPackageDO vo) throws  SQLException{
        String sql = "update  SET  express_id=?  where express_id==?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getExpressId());
        pStmt.setString(2, vo.getExpressRemark());
        return pStmt.executeUpdate() > 0;
    }
    public boolean doDelete(ExpressPackageDO vo) throws SQLException {
        String sql = "DELETE FROM  express_package WHERE express_id=? and order_id=?";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1,vo.getExpressId());
        pStmt.setString(2,vo.getOrderId());
       // pStmt.setString(2,vo.getOrderId());
        return pStmt.execute();
    }

    public Long findAfterSaleId(ExpressPackageDO vo) throws SQLException {
        Long result = null;
        String sql = String.format("select after_sale_id from express_package  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getLong("after_sale_id");
        return result;
    }

    public String findExpressStatus(ExpressPackageDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select express_status from express_package  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("express_status");
        return result;
    }

    public String findExpressPackage(ExpressPackageDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select express_package from express_package  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("express_package");
        return result;
    }

    public String findAfterSaleStatus(ExpressPackageDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select after_sale_status from express_package  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("after_sale_status");
        return result;
    }

    public String findShopName(ExpressPackageDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select shop_name from express_package  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("shop_name");
        return result;
    }

    public String findOrderId(ExpressPackageDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select order_id from express_package  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("order_id");
        return result;
    }

    public String findExpressType(ExpressPackageDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select express_type from express_package  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("express_type");
        return result;
    }
}
