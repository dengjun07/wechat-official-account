package com.wechat.officialaccount.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wechat.officialaccount.redis.RedisClient;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WechatUtil {

    @Autowired
    private RedisClient redisClient;

    /**
     * 获取ACCESS_TOKEN
     * @param url
     * @return
     */
    public static Map getAccessToken(String url) {

        //获取HttpClient对象,用于发送请求
        CloseableHttpClient client = HttpClients.createDefault();
        //获取HttpGet对象,用于编辑请求信息
        HttpGet get = new HttpGet(url);
        try {
            //执行请求,获取返回值
            CloseableHttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();   //获取HTTP实体
            if (entity != null) {
                //获取返回值内容
                String content = EntityUtils.toString(entity, "UTF-8");
                //String转Map对象,便于获取
                ObjectMapper mapper = new ObjectMapper();
                HashMap map = mapper.readValue(content, HashMap.class);
                //将ACCESS_TOKEN和EXPIRES_IN存入Redis

                return map;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 响应微信服务器
     * 提交参数
     */
    public static Map commitWechatServer(String url, String parameter) {

        //获取HttpClient对象,用于发送请求
        CloseableHttpClient client = HttpClients.createDefault();
        //获取HttpGet对象,用于编辑请求信息
        HttpPost post = new HttpPost(url);
        try {
            //设置请求实体
            post.setEntity(new StringEntity(parameter, "UTF-8"));
            //执行请求,获取返回值
            CloseableHttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();   //获取HTTP实体
            if (entity != null) {
                //获取返回值内容
                String content = EntityUtils.toString(entity, "UTF-8");
                //String转Map对象,便于获取
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(content, HashMap.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
