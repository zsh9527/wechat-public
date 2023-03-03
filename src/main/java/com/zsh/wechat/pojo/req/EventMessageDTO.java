package com.zsh.wechat.pojo.req;

import com.zsh.wechat.enums.EventEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 事件消息
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventMessageDTO extends MessageReqDTO {

    @NonNull
    private EventEnum Event;

    /**
     * 事件KEY值，点击事件与自定义菜单接口中KEY值对应
     * 菜单跳转链接事件为设置的跳转URL
     */
    @Nullable
    private String EventKey;

    @Nullable
    private String Ticket;

    /**
     * 地理位置相关
     */
    @Nullable
    private String Latitude;

    /**
     * 地理位置相关
     */
    @Nullable
    private String Longitude;

    /**
     * 地理位置相关
     */
    @Nullable
    private String Precision;
}
