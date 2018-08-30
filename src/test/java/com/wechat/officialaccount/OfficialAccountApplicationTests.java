package com.wechat.officialaccount;

import com.wechat.officialaccount.pojo.WechatConfig;
import com.wechat.officialaccount.redis.RedisClient;
import com.wechat.officialaccount.utils.WechatUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OfficialAccountApplicationTests {

    @Autowired
    private WechatConfig wechatConfig;

    @Autowired
    private RedisClient redisClient;

    private static final String SERVER_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    @Test
    public void contextLoads() {
        String url = SERVER_URL.replace("APPID", wechatConfig.getAppid()).replace("APPSECRET", wechatConfig.getAppsecret());
        Map map = WechatUtil.getAccessToken(url);
        if (map != null) {
            Object access_token = map.get("access_token");
            Object expires_in = map.get("expires_in");
            System.out.println("access_token:   " + access_token.toString());
            System.out.println("expires_in:    " + expires_in.toString());
            //存入Redis
            redisClient.set("access_token",access_token.toString());
            redisClient.set("expires_in",expires_in.toString());
            //取出
            System.out.println("redis_access:   " + redisClient.get("access_token"));
            System.out.println("redis_expires:   " + redisClient.get("expires_in"));
        }

    }

}
