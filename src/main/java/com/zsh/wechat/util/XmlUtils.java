package com.zsh.wechat.util;

import com.zsh.wechat.pojo.dto.RequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * xml数据解析工具
 *
 * @author zsh
 * @version 1.0.0
 * @date 2023/02/23 23:34
 */
@Component
@RequiredArgsConstructor
public class XmlUtils {

    private static final DocumentBuilder documentBuilder;

    static {
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String replyFormat = "<xml>\n"
        + "<ToUserName><![CDATA[%s]]></ToUserName>\n"
        + "<FromUserName><![CDATA[%s]]></FromUserName>\n"
        + "<CreateTime>%s</CreateTime>\n"
        + "<MsgType><![CDATA[%s]]></MsgType>\n"
        + "<Content><![CDATA[%s]]></Content>\n"
        + "</xml>";

    private static final String encryptFormat = "<xml>\n"
        + "<Encrypt><![CDATA[%s]]></Encrypt>\n"
        + "<MsgSignature><![CDATA[%s]]></MsgSignature>\n"
        + "<TimeStamp>%s</TimeStamp>\n"
        + "<Nonce><![CDATA[%s]]></Nonce>\n"
        + "</xml>";

    /**
     * 生成xml消息
     */
    public static String generateContent(RequestDTO request, String content) {
        return String.format(replyFormat, request.getFromUserName(), request.getToUserName(),
            new Date().getTime() / 1000,
            request.getMsgType(), content);
    }

    /**
     * 生成加密xml消息
     * @param encrypt 加密后的消息密文
     * @param signature 安全签名
     * @param timestamp 时间戳
     * @param nonce 随机字符串
     * @return 生成的xml字符串
     */
    public static String generateEncryptContent(String encrypt, String signature, String timestamp, String nonce) {
        return String.format(encryptFormat, encrypt, signature, timestamp, nonce);
    }

    /**
     * 解析xml
     */
    @SneakyThrows
    public static <T> T parseXml(String content, Class<T> tClass) {
        // j解析数据
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        Document document = documentBuilder.parse(new ByteArrayInputStream(bytes));
        // 获取数据
        T obj = tClass.newInstance();
        NodeList childNodes = document.getDocumentElement().getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            // 节点类型是 ELEMENT 才读取值
            // 进行此判断是因为如果xml不是一行，而是多行且有很好的格式的，就会产生一些文本的node，这些node内容只有换行符或空格
            // 所以排除这些换行符和空格。
            if (item.getNodeType() == Node.ELEMENT_NODE) {
                String key = item.getNodeName();
                String value = item.getTextContent();
                // 拿到设置值的set方法
                try {
                    Method declaredMethod = tClass.getDeclaredMethod("set" + key, String.class);
                    declaredMethod.setAccessible(true);
                    declaredMethod.invoke(obj, value); // 设置值
                } catch (NoSuchMethodException e) {
                    // 忽略
                }
            }
        }
        return obj;
    }
}
