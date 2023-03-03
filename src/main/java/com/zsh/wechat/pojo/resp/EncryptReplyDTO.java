package com.zsh.wechat.pojo.resp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

/**
 * 加密返回信息
 *
 * 注解@JacksonXmlCData 生成<![CDATA[text]]>
 * @attention @JacksonXmlCData生效的属性字段名称不能以大写字母开头
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "xml")
public class EncryptReplyDTO {

    @NonNull
    @JacksonXmlCData
    private String encrypt;

    @NonNull
    @JacksonXmlCData
    private String msgSignature;

    @NonNull
    private String timeStamp;

    @NonNull
    @JacksonXmlCData
    private String nonce;
}

