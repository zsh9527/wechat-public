package com.zsh.wechat.pojo.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 图片消息
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PicMessageDTO extends MessageReqDTO {

    @NonNull
    private String PicUrl;

    @Nullable
    private String MediaId;
}
