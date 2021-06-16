package com.auto.modal.mobile.totalcontrolls;

import com.auto.modal.vo.MessageVO;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Classname PhoneControlls
 * @Description
 * @Date 2021/3/12 18:14
 * @Created by Administrator
 */
public class PhoneControlls {
    public static final String deviceId = "device@32463010";
    public static HttpClient client = HttpClients.createDefault();
    public static JsonObject jsonObj = new JsonObject();
    public static BufferedReader in = null;
    public static HttpResponse response = null;
    public static HttpGet get = null;
    public static HttpPost post = null;
    public static StringEntity entity = null;
    public static void main(String[] args) throws Exception {
        PhoneControlls phoneControlls=new PhoneControlls();
        String encode= Base64.encodeBase64String("zili:dede".getBytes());
        //将账号密码BASE64编码
        get = new HttpGet("http://localhost:8090/TotalControl/v1/login");
        get.setHeader("Authorization", encode);
        //设置请求头
        response = client.execute(get);
        in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        String re=in.readLine();
        System.out.println(re);

        //读取返回数据
        in.close();
        Gson json=new Gson();
        JsonObject jsonObject=json.fromJson(re, JsonObject.class);
        String token=jsonObject.get("value").getAsJsonObject().get("token").getAsString();
        String url="http://localhost:8090/TotalControl/v2/devices/main?token="+token;
        get=new HttpGet(url);
        response = client.execute(get);
        in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        re=in.readLine();
        jsonObject=json.fromJson(re, JsonObject.class);
        JsonElement js=jsonObject.get("id");
        String deviceId=js.getAsString();
        System.out.println(deviceId);
        List<MessageVO> sendMessageList=new ArrayList<>();
        List<String> phoneList=new ArrayList<>();

        phoneList.add("17379239969");
        phoneList.add("13822116043");
        phoneList.add("17712869191");
        phoneList.add("18870288158");
        phoneList.add("17379239969");
        phoneList.add("13822116043");
        phoneList.add("17712869191");
        phoneList.add("18870288158");
        phoneList.add("17712869191");
        phoneList.add("18870288158");
        for(int i=0;i<10;i++){
            MessageVO vo=new MessageVO();
            vo.setDeviceId(deviceId);
            vo.setToken(token);
            vo.setPhone(phoneList.get(i));
            String s=String.format("自立发送的第%d条测试短信",i);
            vo.setMessage(s);
            sendMessageList.add(vo);
        }
        int i=0;
        for(MessageVO vo:sendMessageList){
            phoneControlls.sendMessage(vo);
        }

    }
    public void sendMessage(MessageVO vo) throws IOException, InterruptedException {
        String url=String.format("http://localhost:8090/TotalControl/v2/devices/%s",vo.getDeviceId());
        post= new HttpPost(url);

        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("token", vo.getToken()));
        params.add(new BasicNameValuePair("phone_number", vo.getPhone()));
        params.add(new BasicNameValuePair("message", vo.getMessage()));
        post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        response = client.execute(post);
        in = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        String re=in.readLine();
        System.out.println(re);
        Thread.sleep(5000);
    }
}
