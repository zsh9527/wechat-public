package com.zsh.wechat.pojo.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

/**
 * 地理位置消息
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationMessageDTO extends MessageReqDTO {

    @NonNull
    @JsonProperty("Location_X")
    private String Location_X;

    @NonNull
    @JsonProperty("Location_Y")
    private String Location_Y;

    @NonNull
    private String Scale;

    @NonNull
    private String Label;
}
