package com.wechat.officialaccount.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:config/wechat.properties")//配置文件路径
@ConfigurationProperties(prefix = "wechat")
public class WechatConfig {

    private String token;   //凭证
    private String appid;   //开发者ID
    private String appsecret;   //开发者密码

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }
}
