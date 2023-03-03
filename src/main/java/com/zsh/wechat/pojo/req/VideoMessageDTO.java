package com.zsh.wechat.pojo.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

/**
 * 视频消息 | 小视屏消息
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoMessageDTO extends MessageReqDTO {

    @NonNull
    private String MediaId;

    @NonNull
    private String ThumbMediaId;
}
