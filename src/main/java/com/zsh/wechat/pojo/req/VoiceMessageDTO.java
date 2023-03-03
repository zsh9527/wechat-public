package com.zsh.wechat.pojo.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 语音消息
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoiceMessageDTO extends MessageReqDTO {

    @NonNull
    private String MediaId;

    /**
     * 语音格式，如amr，speex等
     */
    @NonNull
    private String Format;

    /**
     * 语音识别结果，UTF8编码 (开通语音识别后有该参数)
     */
    @Nullable
    private String Recognition;
}
