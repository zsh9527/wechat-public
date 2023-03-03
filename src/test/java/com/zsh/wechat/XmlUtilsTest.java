package com.zsh.wechat;

import com.zsh.wechat.enums.EventEnum;
import com.zsh.wechat.enums.MsgTypeEnum;
import com.zsh.wechat.pojo.req.*;
import com.zsh.wechat.pojo.resp.EncryptReplyDTO;
import com.zsh.wechat.pojo.resp.ReplyDTO;
import com.zsh.wechat.util.XmlUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;

/**
 * xml解析测试
 */
@SpringBootTest
public class XmlUtilsTest {

    @Autowired
    private XmlUtils xmlUtils;

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
    public static String generateContent(MessageReqDTO request, String content) {
        return String.format(replyFormat, request.getFromUserName(), request.getToUserName(),
            new Date().getTime() / 1000,
            request.getMsgType(), content);
    }

    /**
     * 测试解析文本消息
     */
    @Test
    public void testParseTextMessage() {
        String requestBody = "<xml><URL><![CDATA[http://chat.lefenglian.top]]></URL><ToUserName><![CDATA[to]]></ToUserName><FromUserName><![CDATA[from]]></FromUserName><CreateTime>12345</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[content]]></Content><MsgId>111</MsgId></xml>";
        MessageReqDTO requestDTO = xmlUtils.parseReqMessage(requestBody);
        Assertions.assertEquals("from", requestDTO.getFromUserName());
        Assertions.assertEquals("to", requestDTO.getToUserName());
        Assertions.assertEquals(12345, requestDTO.getCreateTime());
        Assertions.assertEquals(111, requestDTO.getMsgId());
        Assertions.assertEquals(MsgTypeEnum.TEXT, requestDTO.getMsgType());
        Assertions.assertEquals("content", ((TextMessageDTO)requestDTO).getContent());
    }

    /**
     * 测试解析图片消息
     */
    @Test
    public void testParsePicMessage() {
        String requestBody = "<xml><URL><![CDATA[http://chat.lefenglian.top]]></URL><ToUserName><![CDATA[to]]></ToUserName><FromUserName><![CDATA[from]]></FromUserName><CreateTime>12345</CreateTime><MsgType><![CDATA[image]]></MsgType><PicUrl><![CDATA[pic_url]]></PicUrl><MsgId>111</MsgId></xml>\n";
        MessageReqDTO requestDTO = xmlUtils.parseReqMessage(requestBody);
        Assertions.assertEquals(MsgTypeEnum.PicUrl, requestDTO.getMsgType());
        Assertions.assertEquals("pic_url", ((PicMessageDTO)requestDTO).getPicUrl());
    }

    /**
     * 测试解析语音消息
     */
    @Test
    public void testParseVoiceMessage() {
        String requestBody = "<xml><URL><![CDATA[http://chat.lefenglian.top]]></URL><ToUserName><![CDATA[to]]></ToUserName><FromUserName><![CDATA[from]]></FromUserName><CreateTime>12345</CreateTime><MsgType><![CDATA[voice]]></MsgType><MediaId><![CDATA[media_id]]></MediaId><Format><![CDATA[amr]]></Format><MsgId>111</MsgId></xml>";
        MessageReqDTO requestDTO = xmlUtils.parseReqMessage(requestBody);
        Assertions.assertEquals(MsgTypeEnum.VOICE, requestDTO.getMsgType());
        Assertions.assertEquals("media_id", ((VoiceMessageDTO)requestDTO).getMediaId());
        Assertions.assertEquals("amr", ((VoiceMessageDTO)requestDTO).getFormat());
    }

    /**
     * 测试解析视频消息
     */
    @Test
    public void testParseVideoMessage() {
        String requestBody = "<xml><URL><![CDATA[http://chat.lefenglian.top]]></URL><ToUserName><![CDATA[to]]></ToUserName><FromUserName><![CDATA[from]]></FromUserName><CreateTime>12345</CreateTime><MsgType><![CDATA[video]]></MsgType><MediaId><![CDATA[media_id2]]></MediaId><ThumbMediaId><![CDATA[thumb_media_id2]]></ThumbMediaId><MsgId>111</MsgId></xml>";
        MessageReqDTO requestDTO = xmlUtils.parseReqMessage(requestBody);
        Assertions.assertEquals(MsgTypeEnum.VIDEO, requestDTO.getMsgType());
        Assertions.assertEquals("media_id2", ((VideoMessageDTO)requestDTO).getMediaId());
        Assertions.assertEquals("thumb_media_id2", ((VideoMessageDTO)requestDTO).getThumbMediaId());
    }

    /**
     * 测试解析位置消息
     */
    @Test
    public void testParseLocationMessage() {
        String requestBody = "<xml><URL><![CDATA[http://chat.lefenglian.top]]></URL><ToUserName><![CDATA[to]]></ToUserName><FromUserName><![CDATA[from]]></FromUserName><CreateTime>12345</CreateTime><MsgType><![CDATA[location]]></MsgType><Location_X>60</Location_X><Location_Y>123</Location_Y><Scale>1</Scale><Label><![CDATA[label]]></Label><MsgId>111</MsgId></xml>";
        MessageReqDTO requestDTO = xmlUtils.parseReqMessage(requestBody);
        Assertions.assertEquals(MsgTypeEnum.LOCATION, requestDTO.getMsgType());
        Assertions.assertEquals("60", ((LocationMessageDTO)requestDTO).getLocation_X());
        Assertions.assertEquals("123", ((LocationMessageDTO)requestDTO).getLocation_Y());
        Assertions.assertEquals("1", ((LocationMessageDTO)requestDTO).getScale());
        Assertions.assertEquals("label", ((LocationMessageDTO)requestDTO).getLabel());
    }

    /**
     * 测试解析链接消息
     */
    @Test
    public void testParseLinkMessage() {
        String requestBody = "<xml><URL><![CDATA[http://chat.lefenglian.top]]></URL><ToUserName><![CDATA[to]]></ToUserName><FromUserName><![CDATA[from]]></FromUserName><CreateTime>12345</CreateTime><MsgType><![CDATA[link]]></MsgType><Title><![CDATA[title]]></Title><Description><![CDATA[descript]]></Description><Url><![CDATA[url]]></Url><MsgId>111</MsgId></xml>";
        MessageReqDTO requestDTO = xmlUtils.parseReqMessage(requestBody);
        Assertions.assertEquals(MsgTypeEnum.LINK, requestDTO.getMsgType());
        Assertions.assertEquals("title", ((LinkMessageDTO)requestDTO).getTitle());
        Assertions.assertEquals("descript", ((LinkMessageDTO)requestDTO).getDescription());
        Assertions.assertEquals("url", ((LinkMessageDTO)requestDTO).getUrl());
    }

    /**
     * 测试解析事件消息
     */
    @Test
    public void testParseEventMessage() {
        String requestBody = "<xml><URL><![CDATA[http://chat.lefenglian.top]]></URL><ToUserName><![CDATA[to]]></ToUserName><FromUserName><![CDATA[from]]></FromUserName><CreateTime>12345</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[LOCATION]]></Event><Latitude>80</Latitude><Longitude>90</Longitude><Precision>999</Precision><MsgId>111</MsgId></xml>";
        MessageReqDTO requestDTO = xmlUtils.parseReqMessage(requestBody);
        Assertions.assertEquals(MsgTypeEnum.EVENT, requestDTO.getMsgType());
        Assertions.assertEquals(EventEnum.LOCATION, ((EventMessageDTO)requestDTO).getEvent());
        Assertions.assertEquals("80", ((EventMessageDTO)requestDTO).getLatitude());
        Assertions.assertEquals("90", ((EventMessageDTO)requestDTO).getLongitude());
        Assertions.assertEquals("999", ((EventMessageDTO)requestDTO).getPrecision());
    }

    /**
     * 测试生成xml消息
     */
    @Test
    public void testGenerateXml() {
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setToUserName("to");
        replyDTO.setFromUserName("from");
        replyDTO.setCreateTime(12345L);
        replyDTO.setMsgType(MsgTypeEnum.TEXT);
        replyDTO.setContent("content");
        String content = xmlUtils.generateXml(replyDTO);
        String exceptContent =  String.format(replyFormat, replyDTO.getToUserName(), replyDTO.getFromUserName(),
            replyDTO.getCreateTime(), replyDTO.getMsgType().getValue(), replyDTO.getContent());
        String[] arr = exceptContent.split("\n");
        Arrays.stream(arr).forEach(obj -> Assertions.assertTrue(content.contains(obj)));
    }

    /**
     * 测试生成加密xml消息
     */
    @Test
    public void testGenerateEncryptXml() {
        EncryptReplyDTO replyDTO = new EncryptReplyDTO();
        replyDTO.setEncrypt("encrypt");
        replyDTO.setMsgSignature("msg_signature");
        replyDTO.setTimeStamp("12345");
        replyDTO.setNonce("nonce");
        String content = xmlUtils.generateXml(replyDTO);
        String exceptContent = String.format(encryptFormat, replyDTO.getEncrypt(), replyDTO.getMsgSignature(),
            replyDTO.getTimeStamp(), replyDTO.getNonce());
        String[] arr = exceptContent.split("\n");
        Arrays.stream(arr).forEach(obj -> Assertions.assertTrue(content.contains(obj)));
    }
}
