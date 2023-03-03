package com.zsh.wechat.pojo.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

/**
 * 链接消息
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LinkMessageDTO extends MessageReqDTO {

    @NonNull
    private String Title;

    @NonNull
    private String Description;

    @NonNull
    private String Url;
}
