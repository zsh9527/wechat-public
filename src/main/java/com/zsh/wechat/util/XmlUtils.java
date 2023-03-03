package com.zsh.wechat.util;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.zsh.wechat.pojo.req.AllMessageDTO;
import com.zsh.wechat.pojo.req.MessageReqDTO;
import com.zsh.wechat.pojo.resp.ReplyDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

/**
 * xml数据解析工具
 *
 * @author zsh
 * @version 1.0.0
 * @date 2023/02/23 23:34
 */
@Component
@RequiredArgsConstructor
public class XmlUtils {

    private final XmlMapper xmlMapper;

    /**
     * 生成xml消息
     */
    @SneakyThrows
    public String generateXml(Object object) {
        return xmlMapper.writeValueAsString(object);
    }

    /**
     * 解析请求体消息
     */
    @SneakyThrows
    public MessageReqDTO parseReqMessage(String content) {
        AllMessageDTO allMessageDTO = parseXml(content, AllMessageDTO.class);
        return allMessageDTO.converteMessageDTO();
    }

    /**
     * 解析请求体消息
     */
    @SneakyThrows
    public <T> T parseXml(String content, Class<T> tClass) {
        return xmlMapper.readValue(content, tClass);
    }
}
