package com.zsh.wechat.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zsh.wechat.config.WechatProp;
import com.zsh.wechat.pojo.TokenDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class HttpClient {

    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;
    private final WechatProp wechatProp;

    private String tokenUrl;

    @PostConstruct
    void init () {
        this.tokenUrl = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s",
            wechatProp.getAppId(), wechatProp.getAppSecret());
    }

    /**
     * 获取token
     */
    @SneakyThrows
    public TokenDTO getToken() {
        Request.Builder requestBuilder = new Request.Builder()
            .url(tokenUrl);
        requestBuilder.get();
        Response response = requestContent(requestBuilder.build());
        return objectMapper.readValue(response.body().byteStream(), TokenDTO.class);
    }

    private Response requestContent(Request request) throws IOException {
        Response response = okHttpClient.newCall(request).execute();
        if (!response.isSuccessful()) {
            log.warn(request.url() + ", 请求失败: " + response.message());
            throw new IOException("请求失败");
        }
        return response;
    }
}
