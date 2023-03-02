package com.zsh.wechat.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 加密请求数据
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EncryptRequestDTO {
    @JsonProperty("ToUserName")
    private String ToUserName;

    @JsonProperty("Encrypt")
    private String encrypt;
}
