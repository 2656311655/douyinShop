package com.auto.modal.douyin.sendAdvice;


import com.auto.modal.excel.ReadExcelUtils;
import com.auto.modal.excel.WriteExcelUtils;
import com.auto.modal.vo.SendAdviceVO;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 导出发货单EXCEL // 数据来源-抖音商家后台
 * @Param:
 * @Auther: zl
 * @Date: 2020/9/2 01:13
 */
public class ExportSendAdviceExcelDou {
    private static DateFormat df = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
    public static void main(String[] args) throws Exception {
        String excelFilePath = "D:\\zili\\data\\douyinData\\抖音\\供应商\\备货单\\1.xlsx";
        List<HashMap<Integer, String>> cellList = ReadExcelUtils.readHash(excelFilePath);
        for (HashMap map : cellList) {
            System.out.println(map.get(30));
            System.out.println(map.get(31));
            System.out.println(map.get(32));
            System.out.println(map.get(33));
            break;
        }
        HashMap<String, Integer> tMaps = new HashMap<>();
        //备注
        for (int k = 1; k < cellList.size(); k++) {
            HashMap<Integer, String> map = cellList.get(k);
            String s2 = map.get(46);
            if (s2.equals("***")) {
                continue;
            }
            s2 = map.get(39);
            if(s2.equals("待支付")||s2.equals("已关闭")){
                continue;
            }
            String remark = map.get(12);
            String goodsName = map.get(30);

            //String goodsName=(String)map.get(29);
            if (goodsName.indexOf("保罗")>0) {
                continue;
            }
            goodsName = GoodsRemark.getMaxSubString(goodsName, Config.goodsRemark.clothesSkuList.toString());

            /*
            try {
                Integer j=Integer.parseInt(goodsName);

            }
            catch (Exception ex){
                continue;
            }

             */

            if (true) {
                //System.out.println("");
                String goodsSku = map.get(31).replace("；", "");
                String goodsCode = goodsName;
                goodsCode=map.get(34);
                String goodsNumber = map.get(32);
                if(goodsCode.equals("BL008")){
                    System.out.println(goodsCode);
                }
                if (remark == null) {
                    remark = "";
                }
                remark = remark.toUpperCase();
                if (goodsNumber.equals("")) {
                    System.out.println("11");
                }

                Integer iGoodsNumber = 0;
                try {
                    iGoodsNumber = Integer.parseInt(goodsNumber);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String goodsColor = "";
                String goodsSize = "";
                if (!goodsSku.equals("")) {
                    String s1 = GoodsRemark.getMaxSubString(goodsSku, Config.goodsRemark.clothesColorList.toString());
                    if (!s1.equals("")) {
                        goodsColor = s1.trim();
                    }
                    if(goodsSku.indexOf("(")>0) {
                        if(goodsSku.indexOf("S")>0){
                            System.out.println();
                        }
                        goodsSku = goodsSku.replace(goodsSku.substring(goodsSku.indexOf("("), goodsSku.indexOf(")")+1), "");
                    }
                    s1 = GoodsRemark.getMaxSubString(goodsSku, Config.goodsRemark.clothesSizeList.toString());
                    if (!s1.equals("")) {
                        goodsSize = s1.trim();
                    }
                } else {
                    continue;
                }
                if(goodsColor.equals("色")){
                    System.out.println("");
                }
                if (remark != null) {
                    if (!remark.equals(" ")) {
                        System.out.println(remark);
                    }

                    SendAdviceVO vo = new SendAdviceVO();
                    Config.goodsRemark.reviseSku(remark, vo);
                    if (vo.getGoodsColor() != null) {
                        goodsColor = vo.getGoodsColor();
                    }
                    if (vo.getGoodsSize() != null) {
                        goodsSize = vo.getGoodsSize();
                    }
                    if (vo.getGoodsCode() != null) {
                        if (vo.getGoodsCode().equals("X")) {
                            System.out.println("11");
                        }
                        goodsCode = vo.getGoodsCode();
                    }
                }

                if(goodsSize.equals("7")){
                    System.out.println();
                }
                if (iGoodsNumber > 1) {
                    System.out.println("11");
                }
               if(goodsName.equals("1")){
                   goodsName="maozi";
                   goodsSize="M";
                   goodsCode="maozi";
               }
                for (int i = 0; i < iGoodsNumber; i++) {
                    if(goodsName.equals("")||goodsName.equals("F")||goodsName.equals("C")){
                        goodsName="maozi";
                        goodsSize="M";
                        goodsCode="maozi";
                    }
                    String string = String.format("%s;%s;%s;%s", goodsName, goodsColor, goodsSize, goodsCode);

                    if(string.equals(";黑色;;")){
                        System.out.println();
                    }
                    if (tMaps.containsKey(string)) {
                        tMaps.put(string, tMaps.get(string) + 1);
                    } else {
                        tMaps.put(string, 1);
                    }
                }
            }
        }
        // 导入excel
        exportExcel(tMaps);
    }

    public static void excelProcess(String path) {
        //生成Excel -规则 报单减半

    }

    public static void exportExcel(HashMap<String, Integer> tMaps) throws Exception {
        List<SendAdviceVO> sendAdviceVOList=new ArrayList<>();
        for (Map.Entry<String, Integer> entry : tMaps.entrySet()) {
            String s = entry.getKey();
            SendAdviceVO vo = new SendAdviceVO();
            if (s.equals(";;;")) {
                continue;
            } else {
                String[] strings = s.split(";");
                if (strings.length != 4) {
                    System.out.println("1");
                }

                vo.setGoodsName(strings[0]);
                if(vo.getGoodsName().equals("8813")){
                    System.out.println();
                }
                vo.setGoodsColor(strings[1]);
                try {
                    if(strings[2].indexOf("7")>0){
                        System.out.println();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                vo.setGoodsSize(strings[2]);
                vo.setGoodsCode(strings[3]);
            }
            vo.setGoodsNumber(entry.getValue());
            if (vo.getGoodsCode().equals("X")) {
                System.out.println("11");
            }
            sendAdviceVOList.add(vo);
        }
        /*
        //添加扫描的快递单号。
        String path = "D:\\zili\\data\\douyinData\\抖音\\供应商\\备货单\\扫单.txt";
        List<String> expressList = ScanExpressIdInfo.getExpressList(path);
        List<GoodsInfoVO> goodsInfoVOS = ScanExpressIdInfo.getGoodsInfoList(expressList, Config.goodsRemark);
        for (GoodsInfoVO goodsInfoVO : goodsInfoVOS) {
            //System.out.println(goodsInfoVO.getExpressId()+"面单包裹内容");
            // System.out.println("商品型号："+goodsInfoVO.getGoodsCode());
            // System.out.println("商品SKu："+goodsInfoVO.getGoodsSku());
        }
        //HashMap<String,Integer>  goodsMaps=new HashMap<>();





        int j = 0;
        int sum = 0;
        //添加数据库信息
        ExpressHDao expressHDao=new ExpressHDao(DefaultDatabaseConnect.getConn());
        List<SendAdviceVO> sendAdviceVOLists = expressHDao.findSenAdvices("1");
        HashMap<String, Integer> goodsMap = ScanExpressIdInfo.reviseRemark(goodsInfoVOS);
        HashMap<String, Integer> map = new HashMap<>();
        for (SendAdviceVO sendAdviceVO : sendAdviceVOLists) {
            sum = sum + sendAdviceVO.getGoodsNumber();
            j++;
            String key = sendAdviceVO.getGoodsCode() + " " + sendAdviceVO.getGoodsColor() + " " + sendAdviceVO.getGoodsSize();
            if (map.containsKey(key)) {
                map.put(key, map.get(key) + sendAdviceVO.getGoodsNumber());
            } else {
                map.put(key, sendAdviceVO.getGoodsNumber());
            }
        }

        for (SendAdviceVO sendAdviceVO : sendAdviceVOList) {
            sum = sum + sendAdviceVO.getGoodsNumber();
            j++;
            String key = sendAdviceVO.getGoodsCode() + " " + sendAdviceVO.getGoodsColor() + " " + sendAdviceVO.getGoodsSize();
            if (map.containsKey(key)) {
                map.put(key, map.get(key) + sendAdviceVO.getGoodsNumber());
            } else {
                map.put(key, sendAdviceVO.getGoodsNumber());
            }
        }
        System.out.println(sum);
        sum = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            Integer value = entry.getValue();
            sum = sum + value;
        }
        System.out.println(sum);
        sum = 0;
        for (Map.Entry<String, Integer> entry : goodsMap.entrySet()) {
            String key = entry.getKey();

            Integer value = entry.getValue();
            Integer v = map.get(key);
            if (map.containsKey(key)) {

                sum = value + sum;
                map.put(key, value + v);
            } else {
                sum = sum + value;
                map.put(key, value);
            }
        }
        System.out.println(sum);
        sum = 0;

        ArrayList<SendAdviceVO> sendAdviceVOArrayList = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            String[] keys = key.split(" ");
            Integer value = entry.getValue();
            if (value < 0) {
                System.out.println();
            }
            sum = sum + value;
            SendAdviceVO vo = new SendAdviceVO();
            try {
                vo.setGoodsCode(keys[0]);
                vo.setGoodsColor(keys[1]);
                vo.setGoodsSize(keys[2]);
            } catch (Exception e) {
                continue;
            }
            vo.setGoodsNumber(value);
            sendAdviceVOArrayList.add(vo);
        }
        System.out.println(sum);
        sum = 0;
        for (SendAdviceVO vo : sendAdviceVOArrayList) {
            sum = sum + vo.getGoodsNumber();
        }

        */
       // System.out.println(sum);
        List<HashMap<Integer, String>> addCellList = new ArrayList<>();

        addCellList = exportSendAdviceExcel(sendAdviceVOList);
        List<HashMap<Integer, String>> sList = sortCellList(addCellList);
        String saveExcelFilePath = "D:\\1.xlsx";
        WriteExcelUtils obj = new WriteExcelUtils(saveExcelFilePath);
        obj.writeHashExcel(sList);
    }

    public static List<HashMap<Integer, String>> sortCellList(List<HashMap<Integer, String>> cellList) {
        List<HashMap<Integer, String>> result = new ArrayList<>();
        List<HashMap<Integer, String>> sCellList = new ArrayList<>();
        Map<String, Integer> clothes = new HashMap<>();
        int k = 1;
        clothes.put("S", k++);
        clothes.put("M", k++);
        clothes.put("L", k++);
        clothes.put("XL", k++);
        clothes.put("2XL", k++);
        clothes.put("3XL", k++);
        clothes.put("4XL", k++);
        clothes.put("5XL", k++);
        clothes.put("6XL", k++);
        clothes.put("7XL", k++);
        for (HashMap<Integer, String> map : cellList) {
            if (map.size() > 1) {
                int j = 0;
                List<String> list = new ArrayList<>();
                HashMap<Integer, String> map1 = new HashMap<>();
                for (Map.Entry<Integer, String> entry : map.entrySet()) {
                    String value = entry.getValue();
                    if (j > 0) {
                        String[] values = value.split("/");
                        if(value.indexOf("216")>0){
                            System.out.println();
                        }
                        Integer clothesSize = clothes.get(values[0]);
                        list.add(clothesSize + value);
                        j++;
                    } else {
                        map1.put(j++, value);
                    }
                }
                Collections.sort(list);
                int l = 1;
                for (String s : list) {
                    s = s.substring(1);
                    map1.put(l++, s);
                }
                sCellList.add(map1);


            } else {
                HashMap<Integer, String> map2 = new HashMap<>();
                map2.put(0, "");
                sCellList.add(map2);
                sCellList.add(map);
            }

        }
        return sCellList;
    }

    public static List<HashMap<Integer, String>> exportSendAdviceExcel(List<SendAdviceVO> sendAdviceVOList) {

        Map<String, List<SendAdviceVO>> collect = sendAdviceVOList.stream().collect(Collectors.groupingBy(SendAdviceVO::getGoodsCode));
        List<HashMap<Integer, String>> addCellList = new ArrayList<>();
        int sumCount = 0;
        HashMap<Integer, String> ttMap = null;
        for (Map.Entry<String, List<SendAdviceVO>> entry : collect.entrySet()) {
            String key = entry.getKey();
            List<SendAdviceVO> list = entry.getValue();
            ttMap = new HashMap<>();
            ttMap.put(0, key);
            addCellList.add(ttMap);
            Map<String, List<SendAdviceVO>> colorList = list.stream().collect(Collectors.groupingBy(SendAdviceVO::getGoodsColor));

            for (Map.Entry<String, List<SendAdviceVO>> tEntry : colorList.entrySet()) {
                ttMap = new HashMap<>();
                String tKey = tEntry.getKey();
                List<SendAdviceVO> tList = tEntry.getValue();

                ttMap.put(0, tKey);
                Map<String, List<SendAdviceVO>> sizeList = tList.stream().collect(Collectors.groupingBy(SendAdviceVO::getGoodsSize));
                int j = 1;
                for (Map.Entry<String, List<SendAdviceVO>> ttEntry : sizeList.entrySet()) {
                    String ttKey = ttEntry.getKey();
                    List<SendAdviceVO> ttList = ttEntry.getValue();
                    int sum = 0;
                    for (int i = 0; i < ttList.size(); i++) {
                        SendAdviceVO vo = ttList.get(i);
                        sum = sum + vo.getGoodsNumber();
                    }
                    String temp = ttKey + '/' + sum;
                    if(temp.equals("7/216")){
                        //System.out.println();
                    }
                    sumCount = sumCount + sum;
                    ttMap.put(j++, temp);
                }
                addCellList.add(ttMap);
            }

        }
        System.out.println(sumCount);
        return addCellList;
    }

    public static List<HashMap<Integer, String>> orderSpilt(String startTime, String endTime, String keyword, List<HashMap<Integer, String>> cellList) throws ParseException {
        if (startTime.equals("") || endTime.equals("")) {
            return cellList;
        }
        Date st = df.parse(startTime);
        Date et = df.parse(endTime);
        List<HashMap<Integer, String>> nCellList = new ArrayList<>();
        for (int i = 1; i < cellList.size(); i++) {
            HashMap<Integer, String> map = cellList.get(i);
            String time = map.get(26);
            time = time.replace("'", "");
            String goodsName = map.get(1);
            if (time.equals(""))
                continue;
            time = time.trim();
            Date date = df.parse(time);

            if (date.getTime() >= st.getTime() && date.getTime() <= et.getTime()) {
                if ((goodsName.indexOf(keyword) < 0))
                    nCellList.add(map);
                else {
                    System.out.println(goodsName);
                }
            }
        }
        return nCellList;
    }

}
