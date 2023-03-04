package com.zsh.wechat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zsh.wechat.pojo.TokenDTO;
import com.zsh.wechat.service.TokenService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * token 管理器测试
 */
@SpringBootTest
public class TokenServiceTest {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ObjectMapper objectMapper;

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
}
