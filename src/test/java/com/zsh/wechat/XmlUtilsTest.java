package com.zsh.wechat;

import com.zsh.wechat.pojo.dto.RequestDTO;
import com.zsh.wechat.util.XmlUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * xml解析测试
 */
public class XmlUtilsTest {

    /**
     * 测试验证签名
     */
    @Test
    public void testParseXml() {
        String requestBody = "<xml><URL><![CDATA[http://chat.lefenglian.top]]></URL><ToUserName><![CDATA[to]]></ToUserName><FromUserName><![CDATA[from]]></FromUserName><CreateTime>12345</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[content]]></Content><MsgId>111</MsgId></xml>";
        RequestDTO requestDTO = XmlUtils.parseXml(requestBody, RequestDTO.class);
        Assertions.assertEquals("from", requestDTO.getFromUserName());
        Assertions.assertEquals("to", requestDTO.getToUserName());
        Assertions.assertEquals("12345", requestDTO.getCreateTime());
        Assertions.assertEquals("text", requestDTO.getMsgType());
        Assertions.assertEquals("content", requestDTO.getContent());
        Assertions.assertEquals("111", requestDTO.getMsgId());
    }
}
