package com.zsh.wechat.pojo.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zsh.wechat.enums.EventEnum;
import com.zsh.wechat.pojo.convertor.MessageDTOConvertor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 所有类型消息体
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllMessageDTO extends MessageReqDTO {

    private String Content;

    private String PicUrl;

    private String MediaId;

    private String ThumbMediaId;

    /**
     * 语音格式，如amr，speex等
     */
    private String Format;

    /**
     * 语音识别结果，UTF8编码 (开通语音识别后有该参数)
     */
    private String Recognition;

    private String Title;

    private String Description;

    private String Url;

    @JsonProperty("Location_X")
    private String Location_X;

    @JsonProperty("Location_Y")
    private String Location_Y;

    private String Scale;

    private String Label;

    private EventEnum Event;

    /**
     * 事件KEY值，点击事件与自定义菜单接口中KEY值对应
     * 菜单跳转链接事件为设置的跳转URL
     */
    private String EventKey;

    private String Ticket;

    /**
     * 地理位置相关
     */
    private String Latitude;

    /**
     * 地理位置相关
     */
    private String Longitude;

    /**
     * 地理位置相关
     */
    private String Precision;

    /**
     * 转换messageDTO
     */
    public MessageReqDTO converteMessageDTO() {
        switch (this.getMsgType()) {
            case EVENT: return MessageDTOConvertor.INSTANCE.toEventMessageDTO(this);
            case LINK: return MessageDTOConvertor.INSTANCE.toLinkMessageDTO(this);
            case PicUrl: return MessageDTOConvertor.INSTANCE.toPicMessageDTO(this);
            case LOCATION: return MessageDTOConvertor.INSTANCE.toLocationMessageDTO(this);
            case TEXT: return MessageDTOConvertor.INSTANCE.toTextMessageDTO(this);
            case VIDEO: return MessageDTOConvertor.INSTANCE.toVideoMessageDTO(this);
            case VOICE: return MessageDTOConvertor.INSTANCE.toVoiceMessageDTO(this);
            default: throw new RuntimeException("error msg type");
        }
    }
}
