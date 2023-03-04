package com.zsh.wechat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zsh.wechat.client.HttpClient;
import com.zsh.wechat.pojo.TokenDTO;
import com.zsh.wechat.service.TokenService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * token 管理器测试
 */
@SpringBootTest
public class TokenServiceTest {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpClient httpClient;

    @SneakyThrows
    @Test
    void testDeserialize() {
        String content = "{\"access_token\":\"token_info\",\"expires_in\":7200}";
        TokenDTO dto = objectMapper.readValue(content, TokenDTO.class);
        Assertions.assertEquals("token_info", dto.getAccessToken());
    }

    /**
     * 测试获取access_token
     */
    @Test
    void testGetAccessToken() {
        Assertions.assertDoesNotThrow(() -> tokenService.getAccessToken());
    }

    /**
     * 创建菜单 -- 从resources/menu读取目录
     *
     * 个人账号无法开通微信认证 -- 无法使用该API
     */
    @SneakyThrows
    @Test
    void createMenu() {
        ClassPathResource resource = new ClassPathResource("menu.json");
        String content = Files.readString( Paths.get(resource.getURI()));
        String token = tokenService.getAccessToken();
        httpClient.createMenu(token, content);
    }
}
