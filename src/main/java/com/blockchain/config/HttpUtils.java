package com.blockchain.config;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author hu_xuanhua_hua
 * @ClassName: HttpUtils
 * @Description: TODO
 * @date 2018-12-10 16:13
 * @versoin 1.0
 **/
public class HttpUtils {
    public static String HttpPost(String param1, String param2, String url) throws Exception {
        Map<String, String> personMap = new HashMap<String, String>();
        personMap.put("kw", param1);
//        personMap.put("param1",param2);
        List<NameValuePair> list = new LinkedList<NameValuePair>();
        for (Map.Entry<String, String> entry : personMap.entrySet()) {
            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        HttpPost httpPost = new HttpPost(url);
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list, "utf-8");
        httpPost.setEntity(formEntity);
        HttpClient httpCient = HttpClients.createDefault();
        HttpResponse httpresponse = null;
        try {
            httpresponse = httpCient.execute(httpPost);
            HttpEntity httpEntity = httpresponse.getEntity();
            String response = EntityUtils.toString(httpEntity, "utf-8");
            JSONObject json = JSONObject.parseObject(response);
            return response;
        } catch (ClientProtocolException e) {
            System.out.println("http请求失败，uri{},exception{}");
        } catch (IOException e) {
            System.out.println("http请求失败，uri{},exception{}");
        }
        return null;
    }

    public static String HttpPost(Map<String, String> map, String url) throws Exception {

        List<NameValuePair> list = new LinkedList<NameValuePair>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        HttpPost httpPost = new HttpPost(url);

        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(list, "utf-8");
        httpPost.addHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        httpPost.addHeader("Accept-Encoding", "gzip, deflate");
        httpPost.addHeader("Accept-Language", "zh-CN,zh;q=0.9");
        httpPost.addHeader("Connection", "keep-alive");
//        httpPost.addHeader("Content-Length", "254");
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpPost.addHeader("Cookie", "ASP.NET_SessionId=sbenhj2upitwn4cnwu3tjkn5; www.simei.cc-logincode=datong; www.simei.cc-pwd=Mir123456");
        httpPost.addHeader("Host", "y.xlchina.cn:2002");
        httpPost.addHeader("Origin", "http://y.xlchina.cn:2002");
        httpPost.addHeader("Referer", "http://y.xlchina.cn:2002/orm/emptypoint.aspx");
        httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
        httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
        httpPost.setEntity(formEntity);
//        httpPost.setHeader("Content-Length","10000000");
        HttpClient httpCient = HttpClients.createDefault();
        HttpResponse httpresponse = null;
        try {
            httpresponse = httpCient.execute(httpPost);
            HttpEntity httpEntity = httpresponse.getEntity();
            String response = EntityUtils.toString(httpEntity, "utf-8");
            JSONObject json = JSONObject.parseObject(response);
            return response;
        } catch (ClientProtocolException e) {
            System.out.println("http请求失败，uri{},exception{}");
        } catch (IOException e) {
            System.out.println("http请求失败，uri{},exception{}");
        }
        return null;
    }

    public  static String  httpGet(String url){
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpresponse = null;
        try {
            httpresponse = httpCilent.execute(httpGet);
            HttpEntity httpEntity = httpresponse.getEntity();
            String response = EntityUtils.toString(httpEntity, "utf-8");
            return response;

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpCilent.close();//释放资源
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }
}
