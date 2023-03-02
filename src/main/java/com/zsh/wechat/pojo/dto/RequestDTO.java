package com.zsh.wechat.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 请求数据
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {

    @JsonProperty("ToUserName")
    private String ToUserName;

    @JsonProperty("FromUserName")
    private String FromUserName;

    @JsonProperty("CreateTime")
    private String CreateTime;

    @JsonProperty("MsgType")
    private String MsgType;

    @JsonProperty("Content")
    private String Content;

    @JsonProperty("MsgId")
    private String MsgId;
}
