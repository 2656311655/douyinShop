package com.auto.modal.product;

import java.util.HashMap;
import java.util.Map;

/**
 * @Classname GoodsFilterKeys
 * @Description 过滤关键字
 * @Date 2021/3/10 11:10
 * @Created by Administrator
 */
public class GoodsFilterKeys {
    private   Map<String,String> firstLevelMaps; // 一级区分关键字hash表 大类区分分类
    private   Map<String,String> secondLevelMaps; // 二级区分

    GoodsFilterKeys(){
        init();
    }

    public void setFirstLevelMaps() {
        firstLevelMaps=new HashMap<>();
        firstLevelMaps.put("T恤","T恤");
        firstLevelMaps.put("卫衣","卫衣");
        firstLevelMaps.put("POLO衫","Polo衫");
        firstLevelMaps.put("百褶半裙","半身裙");
        firstLevelMaps.put("束身衣","束身衣");
        firstLevelMaps.put("家居服","家居服");
        firstLevelMaps.put("打底衣","打底衣");
        firstLevelMaps.put("防晒衣","防晒衣");
        firstLevelMaps.put("开衫","开衫");
        firstLevelMaps.put("棉服","棉服");

        firstLevelMaps.put("V领彩衫","V领彩衫");
        firstLevelMaps.put("短袖","短袖");
        firstLevelMaps.put("护膝","护膝");

        firstLevelMaps.put("西装","西装");
        firstLevelMaps.put("防晒帽","防晒帽");
        firstLevelMaps.put("哈衣","婴儿哈衣");
        firstLevelMaps.put("连体","连体衣");
        firstLevelMaps.put("连体衣","连体衣");
        firstLevelMaps.put("防尘罩","防尘罩");
        firstLevelMaps.put("棒球服","棒球服");
        firstLevelMaps.put("半身裙","半身裙");
        firstLevelMaps.put("运动裤","运动裤");

        firstLevelMaps.put("休闲裤","休闲裤");
        firstLevelMaps.put("polo","Polo衫");
        firstLevelMaps.put("连衣裙","连衣裙");
        firstLevelMaps.put("打底衫","打底衫");
        firstLevelMaps.put("睡衣","睡衣");
        firstLevelMaps.put("护肩","护肩");
        firstLevelMaps.put("文胸","文胸");
        firstLevelMaps.put("方巾","方巾");
        firstLevelMaps.put("Polo衫","Polo衫");
        firstLevelMaps.put("马甲","马甲");
        firstLevelMaps.put("背心","背心");
        firstLevelMaps.put("卫衣","卫衣");
        firstLevelMaps.put("袜","袜子");
        firstLevelMaps.put("T恤","T恤");
        firstLevelMaps.put("夹克","夹克");
        firstLevelMaps.put("棉衣","棉衣");
        firstLevelMaps.put("棉裤","棉裤");
        firstLevelMaps.put("皮草","皮草");
        firstLevelMaps.put("皮衣","皮衣");
        firstLevelMaps.put("皮裤","皮裤");
        firstLevelMaps.put("短裤","短裤");
        firstLevelMaps.put("衬衫","衬衫");
        firstLevelMaps.put("西服","西服");
        firstLevelMaps.put("风衣","风衣");
        firstLevelMaps.put("牛仔裤","牛仔裤");
        firstLevelMaps.put("羊毛衫","羊毛衫");
        firstLevelMaps.put("羽绒服","羽绒服");
        firstLevelMaps.put("羽绒裤","羽绒裤");
        firstLevelMaps.put("毛呢大衣","毛呢大衣");
        firstLevelMaps.put("抹胸","抹胸");
        firstLevelMaps.put("毛衣","毛衣");
        firstLevelMaps.put("打底裤","打底裤");
        firstLevelMaps.put("西装裤","西装裤");
        firstLevelMaps.put("针织衫","针织衫");
        firstLevelMaps.put("肚兜","肚兜");
        firstLevelMaps.put("吊带","吊带");
        firstLevelMaps.put("吊袜带","吊袜带");
        firstLevelMaps.put("塑身衣","塑身衣");
        firstLevelMaps.put("保暖内衣","保暖内衣");
        firstLevelMaps.put("保暖裤","保暖裤");
        firstLevelMaps.put("胸垫","胸垫");
        firstLevelMaps.put("文胸","文胸");
        firstLevelMaps.put("手帕","手帕");
        firstLevelMaps.put("耳套","耳套");
        firstLevelMaps.put("袖扣","袖扣");
        firstLevelMaps.put("围巾","围巾");
        firstLevelMaps.put("丝巾","丝巾");
        firstLevelMaps.put("披肩","披肩");
        firstLevelMaps.put("腰带","腰带");
        firstLevelMaps.put("皮带","皮带");
        firstLevelMaps.put("腰链","腰链");
        firstLevelMaps.put("手套","手套");
        firstLevelMaps.put("帽子","帽子");
        firstLevelMaps.put("领带夹","领带夹");
        firstLevelMaps.put("口袋巾","口袋巾");
        firstLevelMaps.put("卫裤","卫裤");
        firstLevelMaps.put("冲锋衣","冲锋衣");
    }

    public Map<String, String> getFirstLevelMaps() {
        return firstLevelMaps;
    }

    public void init(){
        setFirstLevelMaps();
        setSecondLevelMaps();
    }

    public void setSecondLevelMaps(){
        secondLevelMaps=new HashMap<>();
        secondLevelMaps.put("t恤","T恤");
        secondLevelMaps.put("t恤","T恤");
        secondLevelMaps.put("T恤","T恤");
        secondLevelMaps.put("帽","帽子");
        secondLevelMaps.put("上衣","上衣");
        secondLevelMaps.put("衬衣","衬衣");
        secondLevelMaps.put("发箍","发箍");
        secondLevelMaps.put("腕表","腕表");
        secondLevelMaps.put("耳环","耳环");
        secondLevelMaps.put("项链","项链");
        secondLevelMaps.put("配饰","配饰");
        secondLevelMaps.put("包包","包包");
        secondLevelMaps.put("包","包包");
        secondLevelMaps.put("手镯","手镯");
        secondLevelMaps.put("面膜","面膜");
        secondLevelMaps.put("鞋","鞋");
        secondLevelMaps.put("日历","日历");
        //
        secondLevelMaps.put("POLO衫","Polo衫");
        secondLevelMaps.put("百褶半裙","半身裙");
        secondLevelMaps.put("束身衣","束身衣");
        secondLevelMaps.put("家居服","家居服");
        secondLevelMaps.put("打底衣","打底衣");
        secondLevelMaps.put("防晒衣","防晒衣");
        secondLevelMaps.put("开衫","开衫");
        secondLevelMaps.put("棉服","棉服");

        secondLevelMaps.put("V领彩衫","V领彩衫");
        secondLevelMaps.put("短袖","短袖");
        secondLevelMaps.put("护膝","护膝");
        secondLevelMaps.put("长裤","长裤");
        secondLevelMaps.put("西装","西装");
        secondLevelMaps.put("防晒帽","防晒帽");
        secondLevelMaps.put("哈衣","婴儿哈衣");
        secondLevelMaps.put("连体","连体衣");
        secondLevelMaps.put("连体衣","连体衣");
        secondLevelMaps.put("防尘罩","防尘罩");
        secondLevelMaps.put("棒球服","棒球服");
        secondLevelMaps.put("半身裙","半身裙");
        secondLevelMaps.put("运动裤","运动裤");

        secondLevelMaps.put("休闲裤","休闲裤");
        secondLevelMaps.put("polo","Polo衫");
        secondLevelMaps.put("连衣裙","连衣裙");
        secondLevelMaps.put("打底衫","打底衫");
        secondLevelMaps.put("睡衣","睡衣");
        secondLevelMaps.put("护肩","护肩");
        secondLevelMaps.put("外套","外套");
        secondLevelMaps.put("方巾","方巾");
        secondLevelMaps.put("Polo衫","Polo衫");
        secondLevelMaps.put("马甲","马甲");
        secondLevelMaps.put("背心","背心");
        secondLevelMaps.put("卫衣","卫衣");
        secondLevelMaps.put("袜","袜子");
        secondLevelMaps.put("T恤","T恤");
        secondLevelMaps.put("夹克","夹克");
        secondLevelMaps.put("棉衣","棉衣");
        secondLevelMaps.put("棉裤","棉裤");
        secondLevelMaps.put("皮草","皮草");
        secondLevelMaps.put("皮衣","皮衣");
        secondLevelMaps.put("皮裤","皮裤");
        secondLevelMaps.put("短裤","短裤");
        secondLevelMaps.put("衬衫","衬衫");
        secondLevelMaps.put("西服","西服");
        secondLevelMaps.put("风衣","风衣");
        secondLevelMaps.put("牛仔裤","牛仔裤");
        secondLevelMaps.put("羊毛衫","羊毛衫");
        secondLevelMaps.put("羽绒服","羽绒服");
        secondLevelMaps.put("羽绒裤","羽绒裤");
        secondLevelMaps.put("毛呢大衣","毛呢大衣");
        secondLevelMaps.put("抹胸","抹胸");
        secondLevelMaps.put("毛衣","毛衣");
        secondLevelMaps.put("打底裤","打底裤");
        secondLevelMaps.put("西装裤","西装裤");
        secondLevelMaps.put("针织衫","针织衫");
        secondLevelMaps.put("肚兜","肚兜");
        secondLevelMaps.put("吊带","吊带");
        secondLevelMaps.put("吊袜带","吊袜带");
        secondLevelMaps.put("塑身衣","塑身衣");
        secondLevelMaps.put("保暖内衣","保暖内衣");
        secondLevelMaps.put("保暖裤","保暖裤");
        secondLevelMaps.put("胸垫","胸垫");
        secondLevelMaps.put("文胸","文胸");
        secondLevelMaps.put("手帕","手帕");
        secondLevelMaps.put("耳套","耳套");
        secondLevelMaps.put("袖扣","袖扣");
        secondLevelMaps.put("围巾","围巾");
        secondLevelMaps.put("丝巾","丝巾");
        secondLevelMaps.put("披肩","披肩");
        secondLevelMaps.put("腰带","腰带");
        secondLevelMaps.put("皮带","皮带");
        secondLevelMaps.put("腰链","腰链");
        secondLevelMaps.put("手套","手套");
        secondLevelMaps.put("帽子","帽子");

        secondLevelMaps.put("领带夹","领带夹");
        secondLevelMaps.put("口袋巾","口袋巾");
    }
    public Map<String, String> getSecondLevelMaps() {
        return secondLevelMaps;
    }

}
