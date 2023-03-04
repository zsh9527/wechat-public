package com.zsh.wechat.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * token
 */
@Getter
@Setter
@NoArgsConstructor
public class TokenPO {

    /**
     * token
     */
    private String accessToken;

    /**
     * 过期时间
     */
    private Integer expiresIn;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
