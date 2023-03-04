package com.zsh.wechat.pojo.resp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.zsh.wechat.enums.MsgTypeEnum;
import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * 返回信息
 *
 * 注解@JacksonXmlCData 生成<![CDATA[text]]>
 * @attention @JacksonXmlCData生效的属性字段名称不能以大写字母开头
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "xml")
public class ReplyDTO {

    @NonNull
    @JacksonXmlCData
    private String toUserName;

    @NonNull
    @JacksonXmlCData
    private String fromUserName;

    @NonNull
    private Long createTime;

    @Nullable
    @JacksonXmlCData
    private MsgTypeEnum msgType;

    /**
     * 文本消息
     */
    @Nullable
    @JacksonXmlCData
    private String content;

    /**
     * 图片消息
     */
    @Nullable
    private MediaReply Image;

    /**
     * 语音消息
     */
    @Nullable
    private MediaReply Voice;

    /**
     * 视频消息
     */
    @Nullable
    private MediaWithContentReply Video;

    /**
     * 音乐消息
     */
    @Nullable
    private MediaWithContentReply Music;

    /**
     * 图文消息个数
     */
    @Nullable
    private Integer articleCount;

    @Nullable
    @JacksonXmlElementWrapper(localName = "Articles")
    @JacksonXmlProperty(localName = "item")
    private List<MediaWithPicReply> Articles;
}

