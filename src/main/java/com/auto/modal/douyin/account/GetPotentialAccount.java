package com.auto.modal.douyin.account;

import com.auto.dao.DefaultDatabaseConnect;
import com.auto.dao.DouyinAccountDO;
import com.auto.dao.DouyinAccountDao;
import com.auto.dao.DouyinLiveDao;

import java.util.List;

/**
 * @Classname GetPotentialAccount
 * @Description 获取潜力账号
 * @Date 2021/3/20 10:29
 * @Created by Administrator
 */
public class GetPotentialAccount {
    public static void main(String[] args) throws Exception {
        DouyinLiveDao liveDao=new DouyinLiveDao(DefaultDatabaseConnect.getConn());
        DouyinAccountDao accountDao=new DouyinAccountDao(DefaultDatabaseConnect.getConn());
        List<String> accountIds=liveDao.findPetentialAccount("2021-03-16","2021-03-19");
        for(String s:accountIds){
            DouyinAccountDO douyinAccountDO=new DouyinAccountDO();
            douyinAccountDO.setDouyinAccountUrl(s);
            Integer i=accountDao.findAccountLivesCount(douyinAccountDO);
            if(i==0){
                System.out.println(s);
                continue;
            }
            if(accountDao.findAccountLivesCount(douyinAccountDO)<30){
                System.out.println(douyinAccountDO.getDouyinId());
            }

        }
    }
}
