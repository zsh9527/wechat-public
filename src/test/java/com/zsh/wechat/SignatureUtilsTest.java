package com.zsh.wechat;

import com.zsh.wechat.config.WechatProp;
import com.zsh.wechat.util.SignatureUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 微信签名工具类测试
 */
@SpringBootTest
public class SignatureUtilsTest {

    @Autowired
    private WechatProp wechatProp;
    @Autowired
    private SignatureUtils signatureUtils;

	String replyMsg = "我是中文abcd123";
	String afterAesEncrypt = "jn1L23DB+6ELqJ+6bruv21Y6MD7KeIfP82D6gU39rmkgczbWwt5+3bnyg5K55bgVtVzd832WzZGMhkP72vVOfg==";
	String replyMsg2 = "<xml><ToUserName><![CDATA[oia2Tj我是中文jewbmiOUlr6X-1crbLOvLw]]></ToUserName><FromUserName><![CDATA[gh_7f083739789a]]></FromUserName><CreateTime>1407743423</CreateTime><MsgType><![CDATA[video]]></MsgType><Video><MediaId><![CDATA[eYJ1MbwPRJtOvIEabaxHs7TX2D-HV71s79GUxqdUkjm6Gs2Ed1KF3ulAOA9H1xG0]]></MediaId><Title><![CDATA[testCallBackReplyVideo]]></Title><Description><![CDATA[testCallBackReplyVideo]]></Description></Video></xml>";
	String afterAesEncrypt2 = "jn1L23DB+6ELqJ+6bruv23M2GmYfkv0xBh2h+XTBOKVKcgDFHle6gqcZ1cZrk3e1qjPQ1F4RsLWzQRG9udbKWesxlkupqcEcW7ZQweImX9+wLMa0GaUzpkycA8+IamDBxn5loLgZpnS7fVAbExOkK5DYHBmv5tptA9tklE/fTIILHR8HLXa5nQvFb3tYPKAlHF3rtTeayNf0QuM+UW/wM9enGIDIJHF7CLHiDNAYxr+r+OrJCmPQyTy8cVWlu9iSvOHPT/77bZqJucQHQ04sq7KZI27OcqpQNSto2OdHCoTccjggX5Z9Mma0nMJBU+jLKJ38YB1fBIz+vBzsYjrTmFQ44YfeEuZ+xRTQwr92vhA9OxchWVINGC50qE/6lmkwWTwGX9wtQpsJKhP+oS7rvTY8+VdzETdfakjkwQ5/Xka042OlUb1/slTwo4RscuQ+RdxSGvDahxAJ6+EAjLt9d8igHngxIbf6YyqqROxuxqIeIch3CssH/LqRs+iAcILvApYZckqmA7FNERspKA5f8GoJ9sv8xmGvZ9Yrf57cExWtnX8aCMMaBropU/1k+hKP5LVdzbWCG0hGwx/dQudYR/eXp3P0XxjlFiy+9DMlaFExWUZQDajPkdPrEeOwofJb";

    /**
     * 替换wechat配置
     */
    @BeforeEach
    void before() {
        String appId = "wxb11529c136998cb6";
        String token = "pamtest";
        String aesKey = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFG";
        wechatProp.setAppId(appId);
        wechatProp.setToken(token);
        wechatProp.setAesKey(aesKey);
    }

    /**
     * 测试验证签名
     */
    @Test
    public void testCheckSignature() {
        String timestamp = "1409304348";
        String nonce = "xxxxxx";
        Assertions.assertThrows(RuntimeException.class, () -> signatureUtils.checkSignature("singature", timestamp, nonce));
        Assertions.assertDoesNotThrow(() -> signatureUtils.checkSignature("76480565cbe296026c53aaacd1ad523a1ddba24f", timestamp, nonce));
    }

    /**
     * 测试解密
     */
	@Test
	public void testDecrypt() {
        String content = signatureUtils.decrypt(afterAesEncrypt);
        Assertions.assertEquals(replyMsg, content);
        String content2 = signatureUtils.decrypt(afterAesEncrypt2);
        Assertions.assertEquals(replyMsg2, content2);
	}

    /**
     * 测试加密
     */
    @Test
    public void testEncrypt() {
        String randomStr = "aaaabbbbccccdddd";
        String content = signatureUtils.encrypt(replyMsg, randomStr);
        Assertions.assertEquals(afterAesEncrypt, content);
        String content2 = signatureUtils.encrypt(replyMsg2, randomStr);
        Assertions.assertEquals(afterAesEncrypt2, content2);
    }
}
