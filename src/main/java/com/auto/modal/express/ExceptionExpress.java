package com.auto.modal.express;

import com.auto.dao.DefaultDatabaseConnect;
import com.auto.dao.ExpressLogisticsDO;
import com.auto.dao.ExpressPrintDO;
import com.auto.dao.handle.ExpressHDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Classname ExceptionExpress
 * @Description 获取异常快递单号。
 * @Date 2021/3/5 9:31
 * @Created by Administrator
 */
public class ExceptionExpress {
    public static void main(String[] args) throws Exception {
        List<ExpressLogisticsDO> unknownExpressLogistics=new ArrayList<>(); //未知件
        List<ExpressLogisticsDO> unUpdateExpressLogistics=new ArrayList<>();//48小时不更新
        List<ExpressPrintDO> expressPrintDOS =new ArrayList<>();
        ExpressHDao expressHDao=new ExpressHDao(DefaultDatabaseConnect.getConn());
        Date now=new Date();
        Calendar calendar =Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH,-4);
        Date before=calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String startTime=df.format(before);
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH,-0);
        Date after=calendar.getTime();
        String endTime= df1.format(after);
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH,-2);
        Date yesterday=calendar.getTime();
        List<ExpressLogisticsDO> expressLogisticsDOS=expressHDao.findExpressLogistics(startTime,endTime);
        for(ExpressLogisticsDO expressLogisticsDO:expressLogisticsDOS){
            if(expressLogisticsDO.getExpressId().equals("YT5274710800914")){
                System.out.println(expressLogisticsDO.getExpressRecord().length());
            }
            if(expressLogisticsDO.getExpressRecord()==null){
                unknownExpressLogistics.add(expressLogisticsDO);
            }
            else if(expressLogisticsDO.getExpressRecord().equals(""))
                unknownExpressLogistics.add(expressLogisticsDO);
            else if(expressLogisticsDO.getExpressRecord().length()<45){
                unknownExpressLogistics.add(expressLogisticsDO);
            }
            else   {
                String s=expressLogisticsDO.getExpressRecord();
                if(s.indexOf("签收")>=0)
                    continue;
                s=s.substring(0,19);
                Date date=df1.parse(s);
                if(date.getTime()<yesterday.getTime())
                    unUpdateExpressLogistics.add(expressLogisticsDO);
            }
        }
        System.out.println("24小时内无物流快递单号");
        for(ExpressLogisticsDO expressLogisticsDO: unknownExpressLogistics){
            System.out.println(expressLogisticsDO.getExpressId());
        }
        System.out.println("48小时以上物流未更新快递单号");
        for(ExpressLogisticsDO expressLogisticsDO: unUpdateExpressLogistics){
            ExpressPrintDO vo=new ExpressPrintDO();
            vo.setExpressId(expressLogisticsDO.getExpressId());
            expressHDao.findExpressPrint(vo);
            expressPrintDOS.add(vo);
        }
        for(ExpressPrintDO vo:expressPrintDOS){
            String s1=String.format("%s %s %s %s %s",vo.getExpressId(),vo.getExpressPrintTime(),vo.getRecipient(),vo.getRecipientPhone() ,vo.getRecipientAddress().replace("\n\n",""));
            System.out.println(s1);
        }


    }

}
