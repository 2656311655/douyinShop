package com.auto.modal.craw;

import com.auto.dao.DefaultDatabaseConnect;
import com.auto.dao.DouyinAccountDO;
import com.auto.dao.DouyinLiveDO;
import com.auto.dao.DouyinLiveDao;
import com.auto.modal.craw.CrawChanMama;
import com.auto.modal.selenium.WebDriverUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname PotenAcount
 * @Description 获取潜力账号
 * @Date 2021/3/15 13:28
 * @Created by Administrator
 */
public class CrawPotentialAccount {
    public static void main(String[] args) throws Exception {
        DouyinLiveDao liveDao=new DouyinLiveDao(DefaultDatabaseConnect.getConn());
        CrawChanMama crawChanMama=new CrawChanMama();
        crawChanMama.init();
        //crawChanMama.crawPotentialityAccount();
        //crawChanMama.batch();
        List<String> accountIds=liveDao.findPetentialAccount("2021-03-10","2021-03-16");
        List<DouyinAccountDO> liveDOS=new ArrayList<>();
        for(String s:accountIds){

            try {
                crawChanMama.crawAccount(s);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }

        }
        crawChanMama.batch();


    }
}
