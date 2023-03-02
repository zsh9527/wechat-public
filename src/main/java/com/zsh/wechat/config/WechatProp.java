package com.zsh.wechat.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * WechatProp
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "wechat.config")
public class WechatProp {

    /**
     * token
     */
    private String token;

    /**
     * token
     */
    private String appId;

    /**
     * aesKey
     */
    private String aesKey;
}
