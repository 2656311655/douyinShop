package com.auto.dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.auto.dao.ExpressProcessDO;
import com.auto.modal.vo.ExpressScanVO;
import com.auto.modal.vo.ExpressVO;

/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020-12-04
 */
public class ExpressProcessDao {
    private static int QueryTimeout = 30;
    private Connection conn;
    private Statement stmt = null;
    private PreparedStatement pStmt = null;
    private String tableName = "express_process";
    private  SimpleDateFormat sf=null;
    public ExpressProcessDao(Connection connection) throws SQLException {
        this.conn = connection;
        stmt = conn.createStatement();
        stmt.setQueryTimeout(QueryTimeout);  // set timeout to 30 sec;
        sf=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
    }
    //handle
    public void find(ExpressScanVO  expressScanVO) throws SQLException {
        String sql = String.format("select * from express_process  where  express_process.express_id like '%s'  limit 5", "%"+ expressScanVO.getExpressId()+"%");
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1) {
            expressScanVO.setFirstScanTime(set.getString("express_scan_time"));
            expressScanVO.setScanPerson(set.getString("express_scan_person"));
            expressScanVO.setBusinessProcessTime(set.getString("express_business_process_time"));
            expressScanVO.setBusinessProcessPerson(set.getString("express_business_process_person"));
        }

    }
    public List<ExpressScanVO> findExpressScanVO(String expressId) throws SQLException {
        List<ExpressScanVO> voList=new ArrayList<>();
        String sql = String.format("select * from express_process,express_package where express_package.express_id=express_process.express_id and express_process.express_id like '%s'  limit 5", "%"+expressId+"%");
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        String expressPackage="";
        String expressType="";
        String expressRemarks="";
        while (set.next()){
            expressPackage=set.getString("express_package");
            expressType=set.getString("express_type");
            expressRemarks=set.getString("express_remark");
            String expressIds=set.getString("express_id");
            ExpressScanVO expressScanVO = new ExpressScanVO();
            expressScanVO.setExpressId(expressIds);
            expressScanVO.setFirstScanTime(set.getString("express_scan_time"));
            expressScanVO.setScanPerson(set.getString("express_scan_person"));
            if(expressScanVO.getBusinessProcessTime()==null){
                expressScanVO.setBusinessProcessTime("");
                expressScanVO.setBusinessProcessPerson("");
            }
            else {
                expressScanVO.setBusinessProcessTime(set.getString("express_business_process_time"));
                expressScanVO.setBusinessProcessPerson(set.getString("express_business_process_person"));
            }
            expressScanVO.setExpressPackage(expressPackage);
            expressScanVO.setExpressType(expressType);
            expressScanVO.setExpressRemarks(expressRemarks);
            voList.add(expressScanVO);
        }
        if(voList.size()==0){
            ExpressScanVO vo =new ExpressScanVO();
            vo.setExpressId(expressId);
            find(vo);
            if(vo.getFirstScanTime()==null) {
                vo.setExpressType("未知件");
                vo.setExpressPackage("包裹内容为空，请手动填写快件内容");
            }
            voList.add(vo);
            return voList;
        }
        return voList;
    }

    public boolean doInsert(ExpressProcessDO vo) throws SQLException {
        String sql = "insert into express_process(express_id,express_unpack_person,express_scan_time,express_scan_person,express_unpack_time)values (?,?,?,?,?)";
        pStmt = conn.prepareStatement(sql);
        pStmt.setString(1, vo.getExpressId());
        pStmt.setString(2, vo.getExpressUnpackPerson());
        pStmt.setTimestamp(3, new Timestamp(vo.getExpressScanTime().getTime()));
        pStmt.setString(4, vo.getExpressScanPerson());
        pStmt.setTimestamp(5, new Timestamp(vo.getExpressUnpackTime().getTime()));
        return pStmt.execute();
    }

    public boolean doUpdate(ExpressProcessDO vo) throws SQLException {
        String sql = "update  express_process SET  express_unpack_person=? , express_business_process_person=? , express_business_process_time=? WHERE express_id=?";
        pStmt = conn.prepareStatement(sql);

        pStmt.setString(1, vo.getExpressUnpackPerson());
        pStmt.setString(2, vo.getExpressBusinessProcessPerson());
        Timestamp ts = null;
        ts.setTime(vo.getExpressBusinessProcessTime().getTime());
        pStmt.setTimestamp(3, ts);
        pStmt.setString(4, vo.getExpressId());
        return pStmt.executeUpdate() > 0;
    }

    public boolean doDelete(ExpressProcessDO vo) throws SQLException {
        String sql = String.format("DELETE FROM  express_process WHERE express_id=", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        return pStmt.execute();
    }

    public String findExpressUnpackPerson(ExpressProcessDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select express_unpack_person from express_process  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("express_unpack_person");
        return result;
    }

    public String findExpressBusinessProcessPerson(ExpressProcessDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select express_business_process_person from express_process  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("express_business_process_person");
        return result;
    }

    public Date findExpressBusinessProcessTime(ExpressProcessDO vo) throws SQLException {
        Date result = null;
        String sql = String.format("select express_business_process_time from express_process  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getDate("express_business_process_time");
        return result;
    }

    public Date findExpressScanTime(ExpressProcessDO vo) throws SQLException {
        Date result = null;
        String sql = String.format("select express_scan_time from express_process  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getDate("express_scan_time");
        return result;
    }

    public String findExpressScanPerson(ExpressProcessDO vo) throws SQLException {
        String result = null;
        String sql = String.format("select express_scan_person from express_process  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getString("express_scan_person");
        return result;
    }

    public Date findExpressUnpackTime(ExpressProcessDO vo) throws SQLException {
        Date result = null;
        String sql = String.format("select express_unpack_time from express_process  where express_id='%s'", vo.getExpressId());
        pStmt = conn.prepareStatement(sql);
        ResultSet set = pStmt.executeQuery();
        set.next();
        if (set.getRow() == 1)
            result = set.getDate("express_unpack_time");
        return result;
    }
}
