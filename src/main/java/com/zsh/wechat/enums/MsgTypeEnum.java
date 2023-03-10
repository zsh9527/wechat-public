package com.zsh.wechat.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 消息类型
 * EVENT 事件
 * music和news用于消息回复
 */
@Getter
public enum MsgTypeEnum {
    TEXT("text"),
    PicUrl("image"),
    VOICE("voice"),
    VIDEO("video"),
    SHORTVIDEO("shortvideo"),
    LOCATION("location"),
    LINK("link"),
    EVENT("event"),
    MUSIC("music"),
    NEWS("news");

    @JsonValue
    private final String value;

    MsgTypeEnum(String value) {
        this.value = value;
    }
}
