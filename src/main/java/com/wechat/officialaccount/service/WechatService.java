package com.wechat.officialaccount.service;

import javax.servlet.http.HttpServletRequest;

public interface WechatService {

    String checkToken(HttpServletRequest request);

    String acceptWechatMessage(HttpServletRequest request);
}
