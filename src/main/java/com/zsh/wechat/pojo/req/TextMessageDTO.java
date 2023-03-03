package com.zsh.wechat.pojo.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

/**
 * 文本消息
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TextMessageDTO extends MessageReqDTO {

    @NonNull
    private String Content;
}
