package com.zsh.wechat.controller;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.zsh.wechat.pojo.dto.EncryptRequestDTO;
import com.zsh.wechat.pojo.dto.RequestDTO;
import com.zsh.wechat.util.SignatureUtils;
import com.zsh.wechat.util.XmlUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 微信公众号接口
 *
 * @author zsh
 * @version 1.0.0
 * @date 2023/2/23 19:40
 */
@RestController
@RequestMapping
@Slf4j
@RequiredArgsConstructor
public class WeChatController {

    private final SignatureUtils signatureUtils;

    private JacksonXmlModule module = new JacksonXmlModule();
    private XmlMapper mapper = new XmlMapper(module);

    /**
     * 微信公众号接入
     *
     * @param signature 微信加密签名
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param echostr 随机字符串 -- 验证成功的返回值
     */
    @GetMapping
    public String link(@RequestParam String signature,
                       @RequestParam String timestamp,
                       @RequestParam String nonce,
                       @RequestParam String echostr) {
        signatureUtils.checkSignature(signature, timestamp, nonce);
        return echostr;
    }

    /**
     * 接收微信用户向公众号的消息API
     *
     * @param openid 用户加密后的openid
     * @param encryptType 加密类型 | 不加密或者AES加密
     * @param msgSignature 消息签名
     *
     * @return 回复消息
     */
    @PostMapping(produces = "application/xml; charset=UTF-8")
    @SneakyThrows
    public String message(@RequestParam(required = false) String signature,
                       @RequestParam(required = false) String timestamp,
                       @RequestParam(required = false) String nonce,
                       @RequestParam(name = "openid", required = false) String openid,
                       @RequestBody String requestBody,
                       @RequestParam(name = "encrypt_type", required = false) String encryptType,
                       @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        //signatureUtils.checkSignature(signature, timestamp, nonce);
        RequestDTO requestDTO;
        if (StringUtils.isEmpty(encryptType)) {
            // 未加密数据
            requestDTO = XmlUtils.parseXml(requestBody, RequestDTO.class);
            return XmlUtils.generateContent(requestDTO, requestDTO.getContent());
        } else {
            // aes加密
            EncryptRequestDTO encryptRequestDTO = XmlUtils.parseXml(requestBody, EncryptRequestDTO.class);
            String content = signatureUtils
                .decryptMsg(msgSignature, timestamp, nonce, encryptRequestDTO.getEncrypt());
            // 获取解密后数据
            requestDTO = XmlUtils.parseXml(content, RequestDTO.class);
            requestDTO.setToUserName(encryptRequestDTO.getToUserName());
            //
            return signatureUtils.encryptMsg(XmlUtils.generateContent(requestDTO, requestDTO.getContent()), timestamp, nonce);
        }
    }

}
