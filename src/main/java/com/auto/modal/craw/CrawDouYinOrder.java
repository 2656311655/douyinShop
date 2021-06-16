package com.auto.modal.craw;

import com.auto.dao.*;
import com.auto.modal.config.Config;
import com.auto.modal.excel.ReadExcelUtils;
import com.auto.modal.io.FileIo;
import com.auto.modal.selenium.WebDriverUtils;
import com.auto.modal.util.BeansUtils;
import com.auto.modal.util.UuidUtils;
import org.openqa.selenium.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Classname CrawDouyinOrder
 * @Description 爬取抖音订单页
 * @Date 2021/4/15 16:53
 * @Created by Administrator
 */
public class CrawDouYinOrder {
    private OrderDao orderDaO;
    private CustomerDao customerDao;
    private SimpleDateFormat sf = null;
    private List<OrderDO> OrderDOS;
    private List<CustomerDO> customerDOS;
    private WebDriver driver = null;
    private String shopName;
    private JavascriptExecutor jse;
    private WebDriverUtils webDriverUtils;
    CrawDouYinOrder(WebDriverUtils webDriverUtils,String shopName) throws SQLException {
        orderDaO = new OrderDao(DefaultDatabaseConnect.getConn());
        customerDao=new CustomerDao(DefaultDatabaseConnect.getConn());
        sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        OrderDOS = new ArrayList<>();
        customerDOS = new ArrayList<>();
        this.webDriverUtils=webDriverUtils;
        this.shopName = shopName;
        driver=webDriverUtils.getDriver();
        jse = (JavascriptExecutor) driver;
    }
    public void orderSynchronization() throws InterruptedException, ParseException, SQLException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        driver.get("https://fxg.jinritemai.com/ffa/morder/order/list");
        Thread.sleep(2000);
       // setShowLine();
       // Thread.sleep(2000);
        OrderDO OrderDO = new OrderDO();
        OrderDO.setShopName(shopName);
        Date date= orderDaO.findMaxOrderSubmitTime(OrderDO);
        int maxPage=100;
        if(date==null){
            WebElement element =driver.findElement(By.xpath("//*[@class=\"ant-pagination\"]"));
            List<WebElement> elements =element.findElements(By.xpath(".//li"));
            maxPage= Integer.parseInt(elements.get(elements.size()-3).getAttribute("title"));
        }
        //更新备货信息
        String xpath="//*[@data-kora=\"备货中\"]/span";
        String s=driver.findElement(By.xpath(xpath)).getText();
        s=s.replace("(","");
        s=s.replace(")","");
        int i=1;
        try {
            int j=Integer.parseInt(s);
            int k=orderDaO.findOrderBackCount(OrderDO);
            if(Math.abs(k-j)>50) {
                date = orderDaO.findMinOrderBackTime(OrderDO);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        Thread.sleep(2000);
        //更新全部记录
        while (i<=maxPage){
            xpath = String.format("//*[@class=\"ant-pagination-options-quick-jumper\"]/input");
            if (webDriverUtils.isJudgingElement(xpath)){
                WebElement e= driver.findElement(By.xpath(xpath));
                Thread.sleep(1000);
                if(!getPageOrderD0(date)){
                    break;
                }
                e.clear();
                e.sendKeys(Integer.toString(i++));
                e.sendKeys(Keys.ENTER);
                Thread.sleep(6000);
            }
            else{
                Config.logger.warn("售后工作台翻页异常");
            }

        }
        batchUpdate();
        OrderDOS.clear();
        customerDOS.clear();
    }
    private boolean getPageOrderD0(Date date) throws ParseException, SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, InterruptedException {
        String xpath="//*[@class=\"index_tableRow__3HGIM mortise-rich-table-row\"]";
        if(!webDriverUtils.isJudgingElement(xpath)){
            return false;
        }
        List<WebElement> webElements= driver.findElements(By.xpath(xpath));
        if(webElements==null){
            return false;
        }
        if(webElements.size()==0){
            return false;
        }
        for(int i=0;i<webElements.size();i++) {
            OrderDO OrderDO = new OrderDO();
            if (!getOrderLine(webElements.get(i), OrderDO, date)) {
                return false;
            }

            getCustomerInfo(webElements.get(i), OrderDO.getMainOrderId());
            try {
                getGoodsInfo(webElements.get(i), OrderDO);
            }
            catch (Exception ex){
                ex.printStackTrace();
                Config.logger.warn(OrderDO.getOrderId()+"更新失败");
            }
            if (OrderDOS.size() > 0)
                System.out.println(OrderDOS.get(OrderDOS.size() - 1).getOrderId() + " " + OrderDOS.get(OrderDOS.size() - 1).getGoodsCode());
        }
        return true;
    }
    private boolean  importFromExcel(String path) throws IOException {
        //从excel导入订单
        if (!FileIo.fileExists(path)) {
            Config.logger.warn(path+"文件不存在");
            return false;
        }
        List<Map<String, String>> cells = ReadExcelUtils.readSHash(path);
        try {
            List<OrderDO> OrderDOList = convert(cells);
            batchUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    private List<OrderDO> convert(List<Map<String, String>> cells) throws ParseException {
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int j = 1; j < cells.size(); j++) {
            Map<String, String> map = cells.get(j);
            OrderDO OrderDO = new OrderDO();
            String orderId = map.get("B").replace("'", "");
            OrderDO.setOrderId(Long.parseLong(orderId));
            orderId = map.get("A").replace("'", "");
            OrderDO.setMainOrderId(Long.parseLong(orderId));
            OrderDO.setOrderStatus(map.get("AP"));
            OrderDO.setOrderSubmitTime(sf.parse(map.get("AJ")));
            String goodsName= map.get("C");
            if(goodsName.equals("")){
                goodsName=map.get("G");
            }
            String goodsCode = goodsName + " " + map.get("D");
            OrderDO.setGoodsCode(goodsCode);
            OrderDO.setActualCheque(Double.parseDouble(map.get("O")));
            OrderDO.setRecipentId(1L);
            OrderDO.setSenderId(1L);
            OrderDO.setCustomerRemarks(map.get("AG"));
            OrderDO.setBusinessRemarks(map.get("AL"));
            OrderDO.setExpressId(map.get("AI"));
            OrderDO.setShopName(map.get("AV"));
            OrderDO.setPlatformName("抖音");
            OrderDO.setAfterSaleStatus(map.get("AW"));
            Integer i=Integer.parseInt(map.get("E"));
            OrderDO.setGoodsNumber(i);
            OrderDOS.add(OrderDO);
        }
        return OrderDOS;
    }
    private Boolean batchUpdate(){

        for (OrderDO vo : OrderDOS) {
            try {
                //debug
                if(vo.getOrderId().equals("4759740009342000738")){
                    System.out.println();
                }
                //end
                orderDaO.doInsert(vo);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(vo.getOrderId());
                // return false;
            }
        }
        for (CustomerDO vo : customerDOS) {
            OrderDO orderDO =new OrderDO();
            orderDO.setOrderId(Long.parseLong(vo.getOrderId()));
            try {
                if(customerDao.doInsert(vo)){
                    orderDO.setRecipentId(vo.getCustomerId());
                }
                orderDO.setRecipentId(vo.getCustomerId());
            }
            catch (Exception e) {
                //e.printStackTrace();
                //System.out.println(vo.getCustomerPhone());
                try {
                    Long customerId=customerDao.findCustomerId(vo);
                    orderDO.setRecipentId(customerId);
                }
                catch (Exception e1){
                    e1.printStackTrace();
                    return false;
                }
            }
            try {
                orderDaO.doUpdateCustomerId(orderDO);
            }
            catch (Exception e) {
                e.printStackTrace();
                System.out.println(vo.getCustomerPhone());
            }


        }
        return true;
    }
    private CustomerDO getCustomer(){
        CustomerDO customerDO=new CustomerDO();
        return customerDO;
    }
    private boolean getOrderLine(WebElement e,OrderDO OrderDO,Date date) throws ParseException {
        //获取主订单信息
        WebElement e2=null;
        String s="";
        String xpath="";
        //主订单号 下单时间
        xpath=".//*[@class=\"index_text__3y09K\"]";
        List<WebElement>e1=e.findElements(By.xpath(xpath));
        s=e1.get(0).getText();
        s=s.replace("订单号：","");
        s=s.replace("\"","").trim();
        OrderDO.setMainOrderId(Long.parseLong(s));
        s=e1.get(1).getText().trim();
        s=s.replace("下单时间：","");
        s=s.replace("\"","").trim();
        Date date1=sf.parse(s);
        if(date!=null) {
            if (date1.getTime() < date.getTime()) {
                return false;
            }
        }
        OrderDO.setOrderSubmitTime(date1);
        //orderStatus
        xpath=".//*[@style=\"width: 106px; text-align: left;\"]/div";
        e=e.findElement(By.xpath(xpath));
        s=e.getText();
        OrderDO.setOrderStatus(s);
        return true;
    }
    private void getGoodsInfo( WebElement e,OrderDO orderDO) throws InvocationTargetException, IllegalAccessException, InterruptedException {
        //获取订单商品信息
        String xpath;
        String s;
        xpath=".//*[@class=\"index_ellipsis__29MP5 table_secondary__264Bg\"]";
        List<WebElement> e2=e.findElements(By.xpath(xpath));
        if(e2.size()==0){
            xpath=".//*[@class=\"table_rows__2hcuX table_title__1XgBy table_rows-2__16_0R\"]/div";
            e2=e.findElements(By.xpath(xpath));
        }

        if(e2.size()==1){
            //price
            xpath=".//*[@class=\"table_comboAmount__2Sohw\"]";
            WebElement e1=e.findElement(By.xpath(xpath));
            s=e1.getText();
            s=s.replace("¥","");
            s=s.replace(",","");
            orderDO.setActualCheque(Double.parseDouble(s));
            //goodsNumber
            xpath=".//*[@class=\"table_comboNum__1pAh5\"]";
            e1=e.findElement(By.xpath(xpath));
            s=e1.getText();
            s=s.replace("x","");
            orderDO.setGoodsNumber(Integer.parseInt(s));
            orderDO.setActualCheque(orderDO.getActualCheque()*Integer.parseInt(s));
            s = e2.get(0).getText().replace("商家编码：", "").trim();
            xpath = ".//*[@class=\"table_sku__VJKJF table_rows__2hcuX table_rows-1__1Jsy6\"]";
            e1 = e.findElement(By.xpath(xpath));
            s = s + " " + e1.getText().replace("\\\\", ";").trim();
            orderDO.setGoodsCode(s);
            orderDO.setOrderId(orderDO.getMainOrderId());
            xpath=".//*[@class=\"index_footerContent__2CvsZ\"]";
            if(!webDriverUtils.isJudgingElement(xpath)){
                orderDO.setCustomerRemarks("");
                orderDO.setBusinessRemarks("");
            }
            else {
                List<WebElement> webElements = e.findElements(By.xpath(xpath));
                for(WebElement element:webElements){
                    xpath=".//*[@class=\"index_title__1tP29\"]";
                    String s2=element.findElement(By.xpath(xpath)).getText();
                    if(s2.equals("买家留言")){
                        xpath=".//*[@class=\"index_ellipsis__29MP5 undefined\"]";
                        s2=element.findElement(By.xpath(xpath)).getText();
                        orderDO.setCustomerRemarks(s2);

                    }
                    else if(s2.equals("商家备注")){
                        xpath=".//*[@class=\"index_ellipsis__29MP5 undefined\"]";
                        s2=element.findElement(By.xpath(xpath)).getText();
                        orderDO.setBusinessRemarks(s2);
                    }
                }
            }

            orderDO.setShopName(shopName);
            if(orderDO.getGoodsNumber()!=1){
                System.out.println("");
            }
            OrderDOS.add(orderDO);
        }
        else{
            List<OrderDO> OrderDOS1=new ArrayList<>();
            for(int i=0;i<e2.size();i++) {
                OrderDO OrderDO1=new OrderDO();
                OrderDO1 = BeansUtils.clone(orderDO);
                OrderDOS1.add(OrderDO1);
            }

            //price
            xpath=".//*[@class=\"table_comboAmount__2Sohw\"]";
            List<WebElement> e1=e.findElements(By.xpath(xpath));
            xpath=".//*[@class=\"table_comboNum__1pAh5\"]";
            List<WebElement> e4=e.findElements(By.xpath(xpath));
            int i=0;
            for(WebElement element:e1) {
                OrderDO OrderDO1=OrderDOS1.get(i);
                String price=element.getText();
                price=price.replace("¥","");
                String goodsNumber=e4.get(i++).getText();
                goodsNumber=goodsNumber.replace("x","");
                OrderDO1.setGoodsNumber(Integer.parseInt(goodsNumber));
                double v = Double.parseDouble(price) *OrderDO1.getGoodsNumber() ;
                OrderDO1.setActualCheque(v);
            }
            xpath=".//*[@class=\"index_ellipsis__29MP5 table_secondary__264Bg\"]";
            e1=e.findElements(By.xpath(xpath));
            xpath=".//*[@class=\"table_sku__VJKJF table_rows__2hcuX table_rows-1__1Jsy6\"]";
            e4=e.findElements(By.xpath(xpath));
            i=0;
            for (WebElement element : e1) {
                OrderDO OrderDO1 = OrderDOS1.get(i);
                s = element.getText().replace("商家编码：", "").trim();
                s = s + " " + e4.get(i).getText().replace("\\\\", ";").trim();
                OrderDO1.setGoodsCode(s);
                i++;
            }

            //分拆订单-备货状态
            xpath = ".//*[@style=\"flex: 1 1 0%; text-align: left;\"]/span[5]/a";

            if (!webDriverUtils.isElement(e, xpath)) {
                return;
            } else {
                try {
                    System.out.println(e.findElement(By.xpath(xpath)).getText());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println(orderDO.getMainOrderId() + "无法拆掉");
                    return;
                }

            }
            xpath = ".//*[@style=\"flex: 1 1 0%; text-align: left;\"]/span[5]/a";
            WebElement webElement1 = null;
            try {
                webElement1 = e.findElement(By.xpath(xpath));
                jse.executeScript("arguments[0].click();", webElement1);
                Thread.sleep(1000);
            } catch (Exception ex) {
                ex.printStackTrace();
                Config.logger.warn(orderDO.getMainOrderId() + "更新失败");
                return;
            }

            //获取orderId
            xpath = "//*[@class=\"index_tableRow__3HGIM mortise-rich-table-row index_hasRowHeader__2cAkJ\"]";
            List<WebElement>e3= driver.findElements(By.xpath(xpath));
            int j=0;
            for(WebElement webElement:e3){
                xpath=".//*[@class=\"index_cell__6jtb6\"]";
                List<WebElement> webElements1 =webElement.findElements(By.xpath(xpath));
                String orderId=webElements1.get(1).getText();
                OrderDOS1.get(j++).setOrderId(Long.parseLong(orderId));
            }
            //取消
            xpath = "//*[@class=\"drawer-close-icon\"]";
            e=driver.findElement(By.xpath(xpath));
            e.click();
            Thread.sleep(1000);
            for(OrderDO OrderDO1:OrderDOS1) {
                OrderDO1.setShopName(shopName);
                if(OrderDO1.getGoodsNumber()!=1){
                    System.out.println("");
                }
                OrderDOS.add(OrderDO1);
            }

        }


    }
    private void getCustomerInfo(WebElement e,Long mainOrderId){
        // 获取客户信息
        String s;
        String xpath=".//*[@class=\"index_locationDetail__2IqFq\"]";
        if(!webDriverUtils.isJudgingElement(xpath)){
            return;
        }
        else{
            s=e.findElement(By.xpath(".//*[@class=\"index_locationDetail__2IqFq\"]")).getText();
            if(s.equals("***")){
                return;
            }
        }
        String[]strings=s.split("，");
        CustomerDO customerDO =new CustomerDO();
        try {
            s=e.findElements(By.xpath(".//*[@class=\"table_contact__3izAk\"]/a/div")).get(0).getText();
        }
        catch (Exception e1){
            e1.printStackTrace();
            return;
        }
        customerDO.setCustomerNickName(s);
        if(strings.length==3){
            s=strings[0];
            customerDO.setCustomerName(s);
            s=strings[1];
            customerDO.setCustomerPhone(s);
            s=strings[2];
            customerDO.setCustomerAddress(s);
            customerDO.setCustomerType("");
            customerDO.setCustomerTag("");
            customerDO.setCustomerSessionId(1L);
            Long uuid= UuidUtils.getInstance().getUuidGUID(12);
            customerDO.setCustomerId(uuid);
            customerDO.setOrderId(String.valueOf(mainOrderId));
            customerDOS.add(customerDO);
        }
    }

    public void setShowLine() throws InterruptedException {
        try {
            if (!setPageSize()) {
                Config.logger.warn("调整页面订单数失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }


    }
    public void  closePopUP(){
        //关闭弹窗
        String xpath="//*[@alt=\"close\"]";
        if(webDriverUtils.isJudgingElement(xpath)){
            WebElement e = driver.findElement(By.xpath(xpath));
            e.click();
        }
    }
    public boolean  setPageSize() throws InterruptedException {
        String xpath = "//*[@title=\"10 条/页\"]";
        xpath="//*[@id=\"app-right-wrapper\"]/div[2]/div[1]/div[1]/div/div/div[2]/div/div[4]/div/div/div/ul/li[11]/div[1]/div";
        WebElement e=null;
        if(webDriverUtils.isJudgingElement(xpath)) {
            e = driver.findElement(By.xpath(xpath));
            System.out.println(e.getText());
            e.click();
            xpath = "//*[@class=\"ant-select-item-option-content\"]";
            List<WebElement> e1 = driver.findElements(By.xpath(xpath));
            System.out.println(e1.get(2).getText());
            jse.executeScript("arguments[0].click();", e1.get(2));
            Thread.sleep(1000);
            return true;
        }
        else{
            return false ;
        }
    }


}
