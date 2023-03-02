package com.zsh.wechat.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 登录成功信息
 *
 * @author zsh
 * @version 1.0.0
 * @since 2022-09-25 13:28
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDTO {

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

}
