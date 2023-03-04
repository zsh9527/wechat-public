package com.zsh.wechat.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 微信API结果
 */
@Getter
@Setter
@NoArgsConstructor
public class ResultDTO {

    // 0代表成功
    private String errcode;

    private String errmsg;
}
