package com.zsh.wechat.service;

import com.zsh.wechat.client.HttpClient;
import com.zsh.wechat.mapper.TokenMapper;
import com.zsh.wechat.pojo.TokenDTO;
import com.zsh.wechat.pojo.TokenPO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * TokenService
 *
 * @author zsh
 * @version 1.0.0
 * @date 2023/03/04 14:51
 */
@Service
@RequiredArgsConstructor
@Lazy
@Slf4j
public class TokenService {

    private final TokenMapper tokenMapper;
    private final HttpClient httpClient;

    private String accessToken = null;

    /**
     * 初始化token
     */
    @PostConstruct
    void initToken() {
        TokenPO tokenPO = tokenMapper.selectById(1);
        long poor = 0;
        if (tokenPO == null) {
            tokenPO = refreshAndSave();
        } else {
            // 通过poor配置延迟任务, 还差1分钟过期的token直接刷新
            poor = tokenPO.getCreateTime().plusSeconds(tokenPO.getExpiresIn()).toEpochSecond(ZoneOffset.ofHours(0))
                - LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8)) - 60;
            if (poor < 0) {
                // token已过期
                tokenPO = refreshAndSave();
                poor = 0;
            }

        }
        accessToken = tokenPO.getAccessToken();
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleWithFixedDelay(
            this::refreshAndSave,
            poor,
            tokenPO.getExpiresIn() - 60,
            TimeUnit.SECONDS);
    }

    /**
     * 获取token
     */
    public String getAccessToken() {
        return this.accessToken;
    }

    /**
     * 获取token
     */
    private TokenPO refreshAndSave() {
        log.info("刷新token");
        TokenDTO newToken = httpClient.getToken();
        TokenPO tokenPO = new TokenPO();
        tokenPO.setAccessToken(newToken.getAccessToken());
        tokenPO.setExpiresIn(newToken.getExpiresIn());
        int changeCount = tokenMapper.update(tokenPO);
        if (changeCount < 1) {
            tokenMapper.insert(tokenPO);
        }
        return tokenPO;
    }
}
