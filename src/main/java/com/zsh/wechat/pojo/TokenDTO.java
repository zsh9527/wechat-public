package com.zsh.wechat.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * token
 */
@Getter
@Setter
@NoArgsConstructor
public class TokenDTO {

    /**
     * token
     */
    private String accessToken;

    /**
     * 过期时间 - 单位为s
     */
    private Integer expiresIn;
}
