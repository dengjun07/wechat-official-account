package com.wechat.officialaccount.controller;

import com.wechat.officialaccount.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 微信接口Controller
 */
@Controller
public class WechatController {

    @Autowired
    private WechatService wechatService;

    /**
     * 验证token
     */
    @GetMapping("/wechat")
    @ResponseBody
    public String weixin(HttpServletRequest request) {
        return wechatService.checkToken(request);
    }

    /**
     * 接受微信消息
     */
    @PostMapping("/wechat")
    @ResponseBody
    public String acceptMessage(HttpServletRequest request) {
        return wechatService.acceptWechatMessage(request);
    }

}
