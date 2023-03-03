package com.zsh.wechat.pojo.req;

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

    private String ToUserName;

    private String Encrypt;
}
