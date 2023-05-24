package com.zsh.wechat.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zsh.wechat.config.WechatProp;
import com.zsh.wechat.pojo.ResultDTO;
import com.zsh.wechat.pojo.TokenDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.stereotype.Component;

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
    private final String createMenuUrl = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";

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

    /**
     * 创建菜单
     */
    @SneakyThrows
    public void createMenu(String token, String content) {
        Request.Builder requestBuilder = new Request.Builder()
            .url(String.format(createMenuUrl, token));
        RequestBody body =  RequestBody.create(MediaType.parse("application/json"), content);
        requestBuilder.post(body);
        Response response = requestContent(requestBuilder.build());
        ResultDTO result = objectMapper.readValue(response.body().byteStream(), ResultDTO.class);
        if (!"0".equals(result.getErrcode())) {
            throw new RuntimeException("创建菜单失败：" + result.getErrcode() + "-" + result.getErrmsg());
        }
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
