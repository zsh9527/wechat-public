package com.zsh.wechat.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * http连接配置项
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.http")
public class HttpProp {

    /**
     * 连接超时(秒)
     */
    private Integer connectTimeOut;

    /**
     * 读超时时间(秒)
     */
    private Integer readTimeOut;

    /**
     * 写超时时间(秒)
     */
    private Integer writeTimeOut;

    /**
     * 最大连接数
     */
    private Integer maxConnection;

    /**
     * 最大请求数
     */
    private Integer maxRequest;

    /**
     * 同一主机最大请求数
     */
    private Integer maxPerHostRequest;
}
