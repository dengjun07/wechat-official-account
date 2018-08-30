package com.wechat.officialaccount.utils;

import com.thoughtworks.xstream.XStream;
import com.wechat.officialaccount.pojo.TextMessage;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLUtil {

    /**
     * xml转map
     * @param request
     * @return
     * @throws Exception
     */
    public static Map<String,String> xmlToMap(HttpServletRequest request) throws Exception {
        Map<String, String> map = new HashMap<>();
        SAXReader reader = new SAXReader();

        InputStream in = request.getInputStream();
        Document doc = reader.read(in); //读取xml,获取文档对象

        Element root =doc.getRootElement(); //获取文档所有根元素
        List<Element> elements = root.elements();

        List<String> list = new ArrayList<>();
        for (Element element : elements) {
            map.put(element.getName() , element.getText());
        }

        in.close();
        return map;
    }

    /**
     * 对象转xml
     */
    public static String textMessageToXml(TextMessage textMessage) {
        XStream xStream = new XStream();
        //将根节点转为xml
        xStream.alias("xml",textMessage.getClass());
        return xStream.toXML(textMessage);
    }


}
