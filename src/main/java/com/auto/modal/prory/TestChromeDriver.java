package com.auto.modal.prory;


import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import org.json.JSONObject;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.Iterator;
import java.util.logging.Level;

import static com.auto.modal.prory.ChromedriverNetwork.getResponseBody;

/**
 * @Classname TestChromeDriver
 * @Description TODO
 * @Date 2021/4/11 12:34
 * @Created by Administrator
 */
public class TestChromeDriver {
    public static void main(String[] args) throws Exception {
        String driverPath="C:\\\\Program Files\\\\Google\\\\Chrome\\\\Application\\\\chromedriver.exe";
        String jsFilePath="D:\\zili\\work\\it\\project\\servenStars\\data\\stealth.min.js";
        ChromeOptions options = new ChromeOptions();
        String ip="127.0.0.1:9223";
        options.setExperimentalOption("debuggerAddress", ip);
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(ChromeOptions.CAPABILITY, options);
        //配置日志
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        /**flash 扩展*/
        cap.setCapability("profile.managed_default_content_settings.images",1);
        cap.setCapability("profile.content_settings.plugin_whitelist.adobe-flash-player",1);
        cap.setCapability("profile.content_settings.exceptions.plugins.*,*.per_resource.adobe-flash-player",1);
        cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        System.setProperty("webdriver.chrome.driver", driverPath);

        ChromeDriver driver = new ChromeDriver(cap);
        String url="https://www.chanmama.com/report/detail/6949776101191256846";
        driver.get(url);
        LogEntries logs = driver.manage().logs().get(LogType.PERFORMANCE);
        for (Iterator<LogEntry> it = logs.iterator(); it.hasNext();) {
            LogEntry entry = it.next();
            try {
                //System.out.println(entry.toString());
                JSONObject json = new JSONObject(entry.getMessage());
                System.out.println(json.toString());
                JSONObject message = json.getJSONObject("message");
                String method = message.getString("method");
                //如果是响应
                String s="";
                if (method != null && "Network.responseReceived".equals(method)) {
                    JSONObject params = message.getJSONObject("params");
                    JSONObject response = params.getJSONObject("response");
                   // String messageUrl = response.getString("url");
                   // System.out.println("-----------------------------");
                   // System.err.println("url:" + url + " params: " + params.toString() + " response: " + response.toString());
                    //打印调用chromedriver 调chrome httpapi 结果
                    s=params.getString("requestId");
                    s=getResponseBody(params.getString("requestId"),"9223",driver.getSessionId().toString());
                    System.out.println();
                    System.out.println("-----------------------------");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    // 根据请求ID获取返回内容
    public static String getResponseBody(String requestId,String port,String sessionId) {
        try {
            // CHROME_DRIVER_PORT chromeDriver提供的端口
            String url = String.format("http://localhost:%s/session/%s/goog/cdp/execute",
                    port, sessionId);

            HttpPost httpPost = new HttpPost(url);
            JSONObject object = new JSONObject();
            JSONObject params = new JSONObject();
            params.put("requestId", requestId);
            //api https://chromedevtools.github.io/devtools-protocol/tot/Network
            object.put("cmd", "Network.getResponseBody");

            object.put("params", params);

            httpPost.setEntity(new StringEntity(object.toString()));

            RequestConfig requestConfig = RequestConfig
                    .custom()
                    .setSocketTimeout(60 * 1000)
                    .setConnectTimeout(60 * 1000).build();

            CloseableHttpClient httpClient = HttpClientBuilder.create()
                    .setDefaultRequestConfig(requestConfig).build();

            HttpResponse response = httpClient.execute(httpPost);

            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
