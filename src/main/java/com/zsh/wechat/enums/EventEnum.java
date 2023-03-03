package com.zsh.wechat.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 事件枚举
 * subscribe(订阅)、unsubscribe(取消订阅), SCAN(扫描带参数二维码事件), LOCATION（上报位置）， CLICK(点击菜单）, VIEW(点击菜单跳转链接）
 */
@Getter
public enum EventEnum {
    SUBSCRIBE("subscribe"),
    UNSUBSCRIBE("unsubscribe"),
    SCAN("SCAN"),
    LOCATION("LOCATION"),
    CLICK("CLICK"),
    VIEW("VIEW");

    @JsonValue
    private final String value;

    EventEnum(String value) {
        this.value = value;
    }
}
