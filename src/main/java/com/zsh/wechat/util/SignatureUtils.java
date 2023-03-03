package com.zsh.wechat.util;

import com.zsh.wechat.config.WechatProp;
import com.zsh.wechat.pojo.resp.EncryptReplyDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Random;

import static com.zsh.wechat.util.PKCS7Encoder.CHARSET;

/**
 * 微信签名工具类
 *
 * @author zsh
 * @version 1.0.0
 * @date 2023/02/23 23:34
 */
@Component
@RequiredArgsConstructor
public class SignatureUtils {

    private final WechatProp wechatProp;
    private final XmlUtils xmlUtils;

    private static final char[] CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 验证微信请求数据 -- 非法数据抛出异常
     */
    public void checkSignature(String signature, String timestamp, String nonce){
        String sha1Code = generateSha1Code(timestamp, nonce, "");
        // 与微信提供的signature进行匹对
        if (!signature.equals(sha1Code)) {
            throw new RuntimeException("非法请求");
        }
    }

    /**
     * 检验消息的真实性，并且获取解密后的明文
     *
     * @param msgSignature 签名串，对应URL参数的msg_signature
     * @param timestamp 时间戳，对应URL参数的timestamp
     * @param nonce 随机串，对应URL参数的nonce
     * @param encryptContent 密文
     *
     * @return 解密后的原文
     */
    public String decryptMsg(String msgSignature, String timestamp, String nonce, String encryptContent) {
        String sha1Code = generateSha1Code(timestamp, nonce, encryptContent);
        // 与微信提供的msgSignature进行匹对
        if (!msgSignature.equals(sha1Code)) {
            throw new RuntimeException("非法请求内容");
        }
        return decrypt(encryptContent);
    }

    /**
     * 解密
     *
     * @param content 密文
     * @return 明文
     */
    @SneakyThrows
    public String decrypt(String content) {
        byte[] aesKey = getAesKey();
        // 设置解密模式为AES的CBC模式
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
        IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);

        // 使用BASE64对密文进行解码
        byte[] encrypted = org.apache.commons.codec.binary.Base64.decodeBase64(content);
        // 解密
        byte[] original = cipher.doFinal(encrypted);
        // 去除补位字符
        byte[] bytes = PKCS7Encoder.decode(original);
        // 分离16位随机字符串,网络字节序和AppId
        byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);

        int xmlLength = recoverNetworkBytesOrder(networkOrder);

        String xmlContent = new String(Arrays.copyOfRange(bytes, 20, 20 + xmlLength), CHARSET);
        String fromAppId = new String(Arrays.copyOfRange(bytes, 20 + xmlLength, bytes.length), CHARSET);
        // appid不相同的情况
        if (!fromAppId.equals(wechatProp.getAppId())) {
            throw new RuntimeException("error appId");
        }
        return xmlContent;
    }

    /**
     * 将公众平台回复用户的消息加密打包
     *
     * @param replyMsg 公众平台待回复用户的消息，xml格式的字符串
     * @param timeStamp 时间戳，使用URL参数的timestamp
     * @param nonce 随机串，使用URL参数的nonce
     *
     * @return 加密后的可以直接回复用户的密文，包括msg_signature, timestamp, nonce, encrypt的xml格式的字符串
     */
    public String encryptMsg(String replyMsg, String timeStamp, String nonce) {
        // 加密
        String encryptContent = encrypt(replyMsg, getRandomStr());
        String msgSignature = generateSha1Code(timeStamp, nonce, encryptContent);
        // 生成发送的xml
        EncryptReplyDTO encryptReplyDTO = new EncryptReplyDTO(encryptContent, msgSignature, timeStamp, nonce);
        return xmlUtils.generateXml(encryptReplyDTO);
    }

    /**
     * 加密
     *
     * @return 加密后base64编码的字符串
     */
    @SneakyThrows
    public String encrypt(String content, String randomStr) {
        byte[] aesKey = getAesKey();

        ByteGroup byteCollector = new ByteGroup();
        byte[] randomStrBytes = randomStr.getBytes(CHARSET);
        byte[] textBytes = content.getBytes(CHARSET);
        byte[] networkBytesOrder = getNetworkBytesOrder(textBytes.length);
        byte[] appidBytes = wechatProp.getAppId().getBytes(CHARSET);

        // randomStr + networkBytesOrder + text + appid
        byteCollector.addBytes(randomStrBytes);
        byteCollector.addBytes(networkBytesOrder);
        byteCollector.addBytes(textBytes);
        byteCollector.addBytes(appidBytes);

        // ... + pad: 使用自定义的填充方式对明文进行补位填充
        byte[] padBytes = PKCS7Encoder.encode(byteCollector.size());
        byteCollector.addBytes(padBytes);

        // 获得最终的字节流, 未加密
        byte[] unencrypted = byteCollector.toBytes();

        // 设置加密模式为AES的CBC模式
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
        IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
        // 加密
        byte[] encrypted = cipher.doFinal(unencrypted);

        // 使用BASE64对加密后的字符串进行编码
        return org.apache.commons.codec.binary.Base64.encodeBase64String(encrypted);
    }

    /**
     * sha1加密
     *
     * @param encryptContent 加密内容, 可为空字符串
     */
    private String generateSha1Code(String timestamp, String nonce, String encryptContent) {
        String[] arr = new String[]{wechatProp.getToken(), timestamp, nonce, encryptContent};
        Arrays.sort(arr);
        StringBuilder sb = new StringBuilder();
        for (String str : arr) {
            sb.append(str);
        }
        // sha1加密
        return sha1(sb.toString());
    }

    /*
     * sha1加密
     */
    @SneakyThrows
    private String sha1(String str) {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digest = md.digest(str.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(CHARS[(b >> 4) & 0b1111]);
            sb.append(CHARS[b & 0b1111]);
        }
        return sb.toString();
    }

    private byte[] getAesKey() {
        if (wechatProp.getAesKey().length() != 43) {
            throw new RuntimeException("error aes key length");
        }
        return Base64.decodeBase64(wechatProp.getAesKey() + "=");
    }

    // 还原4个字节的网络字节序
    private int recoverNetworkBytesOrder(byte[] orderBytes) {
        int sourceNumber = 0;
        for (int i = 0; i < 4; i++) {
            sourceNumber <<= 8;
            sourceNumber |= orderBytes[i] & 0xff;
        }
        return sourceNumber;
    }

    // 生成4个字节的网络字节序
    private byte[] getNetworkBytesOrder(int sourceNumber) {
        byte[] orderBytes = new byte[4];
        orderBytes[3] = (byte) (sourceNumber & 0xFF);
        orderBytes[2] = (byte) (sourceNumber >> 8 & 0xFF);
        orderBytes[1] = (byte) (sourceNumber >> 16 & 0xFF);
        orderBytes[0] = (byte) (sourceNumber >> 24 & 0xFF);
        return orderBytes;
    }

    // 随机生成16位字符串
    String getRandomStr() {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
