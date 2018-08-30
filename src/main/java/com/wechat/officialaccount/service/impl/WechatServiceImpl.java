package com.wechat.officialaccount.service.impl;

import com.wechat.officialaccount.pojo.TextMessage;
import com.wechat.officialaccount.pojo.WechatConfig;
import com.wechat.officialaccount.service.WechatService;
import com.wechat.officialaccount.utils.Sha1;
import com.wechat.officialaccount.utils.XMLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@Service
public class WechatServiceImpl implements WechatService {

    @Autowired
    private WechatConfig wechatConfig;  //微信配置文件实体类

    private static final String MESSAGE_TEXT = "text";
    private static final String MESSAGE_IMAGE = "image";
    private static final String MESSAGE_VOICE = "voice";
    private static final String MESSAGE_VIDEO = "video";
    private static final String MESSAGE_SHORTVIDEO = "shortvideo";
    private static final String MESSAGE_LOCATION = "location";
    private static final String MESSAGE_LINK = "link";



    /**
     * 服务器地址(URL)
     * 微信传过来四个参数用于验证token
     * signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
     * timestamp	时间戳
     * nonce	    随机数
     * echostr	    随机字符串
     * 1.将token,timestamp,nonce进行字典排序
     * 2.将三个参数拼接成一个字符串进行sha1加密
     * 3.开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
     * 验证成功后原样返回echostr参数内容
     */
    @Override
    public String checkToken(HttpServletRequest request) {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        String[] str = new String[]{wechatConfig.getToken(), timestamp, nonce};
        Arrays.sort(str); //字典排序
        String encode = Sha1.encode(str[0] + str[1] + str[2]);//Sha1加密
        //验证encode和signature是否相等
        if (encode.equals(signature)) {
            return echostr; //验证成功,返回echostr
        }
        return null;
    }



    /**
     * 接受微信消息
     * 当普通微信用户向公众账号发消息时，微信服务器将POST消息的XML数据包到开发者填写的URL上
     * FromUserName	发送方帐号（一个OpenID）
     * CreateTime	消息创建时间 （整型）
     * MsgType	text
     * Content	文本消息内容
     * @return
     */
    @Override
    public String acceptWechatMessage(HttpServletRequest request) {
        try {
            //xml转map
            Map<String, String> map = XMLUtil.xmlToMap(request);
            //判断图片类型
            String msgType = map.get("MsgType");
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String content = map.get("Content");
            switch (msgType) {
                case MESSAGE_TEXT :
                    //文本
                    //设置回复消息
                    TextMessage textMessage = new TextMessage();
                    textMessage.setFromUserName(toUserName);
                    textMessage.setToUserName(fromUserName);
                    textMessage.setCreateTime(new Date().getTime());
                    textMessage.setMsgType("text");
                    if(content.contains("你好")) {
                        textMessage.setContent("你也好~");
                    } else {
                        textMessage.setContent("こんにちは");
                    }
                    //对象转xml
                    return XMLUtil.textMessageToXml(textMessage);
                case MESSAGE_IMAGE :
                    //图片
                    return null;
                case MESSAGE_VOICE :
                    //语音
                    return null;
                case MESSAGE_VIDEO :
                    //视频
                    return null;
                case MESSAGE_SHORTVIDEO :
                    //小视频
                    return null;
                case MESSAGE_LOCATION :
                    //位置
                    return null;
                case MESSAGE_LINK :
                    //超链接
                    return null;
                default:
                    return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
