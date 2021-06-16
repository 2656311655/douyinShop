package com.auto.modal.prory;

/**
 * @Classname ChromeDriver
 * @Description TODO
 * @Date 2021/4/11 13:48
 * @Created by Administrator
 */

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ChromedriverNetwork {
        public static final String port = "9223";
        public static final String driverPath = "C:\\\\Program Files\\\\Google\\\\Chrome\\\\Application\\\\chromedriver.exe";
        public static void main(String[] args) {
            System.setProperty("webdriver.chrome.driver", driverPath);
            String sessionId ;
            String url = "https://www.baidu.com";
            //System.setProperty("webdriver.chrome.driver",filePath);
            ChromeDriver driver = null;
            try {
                ChromeDriverService service = new ChromeDriverService.Builder().usingPort(Integer.valueOf(port))
                        .usingDriverExecutable(new File(driverPath))
                        .build();
                ChromeOptions options = new ChromeOptions();
                String port="127.0.0.1:9223";
                options.setExperimentalOption("debuggerAddress", port);
                options.addArguments("disable-infobars");
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
                driver = new ChromeDriver(cap);
                //or driver.get(url)
                driver.navigate().to(url);
                System.out.println("session id :" + driver.getSessionId());
                sessionId = driver.getSessionId().toString();
                LogEntries logs = driver.manage().logs().get(LogType.PERFORMANCE);
                for (Iterator<LogEntry> it = logs.iterator(); it.hasNext();) {
                    LogEntry entry = it.next();
                    try {
                        //System.out.println(entry.toString());
                        JSONObject json = new JSONObject(Boolean.parseBoolean(entry.getMessage()));
                        System.out.println(json.toString());
                        JSONObject message = json.getJSONObject("message");
                        String method = message.getString("method");
                        //如果是响应
                        if (method != null && "Network.responseReceived".equals(method)) {
                            JSONObject params = message.getJSONObject("params");
                            JSONObject response = params.getJSONObject("response");
                            String messageUrl = response.getString("url");
                            System.out.println("-----------------------------");
                            System.err.println("url:" + url + " params: " + params.toString() + " response: " + response.toString());
                            //打印调用chromedriver 调chrome httpapi 结果
                            System.out.println(getResponseBody(params.getString("requestId"),port,sessionId));
                            System.out.println("-----------------------------");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
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
