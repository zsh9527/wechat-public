package com.zsh.wechat.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.zsh.wechat.pojo.req.AllMessageDTO;
import jakarta.annotation.PostConstruct;
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

    private XmlMapper xmlMapper;

    /**
     * 不使用单例, 影响objectMapper初始化
     */
    @PostConstruct
    void init () {
        JacksonXmlModule module = new JacksonXmlModule();
        XmlMapper xmlMapper = new XmlMapper(module);
        // 忽略反序列化时若实体类没有对应的属性的JsonMappingException异常
        xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 序列化是否绕根元素
        xmlMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        // 忽略空属性
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 使用骆驼命名的属性名
        xmlMapper.setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE);
        // 生成内容分行
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.xmlMapper = xmlMapper;
    }

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
    public AllMessageDTO parseReqMessage(String content) {
        return parseXml(content, AllMessageDTO.class);
    }

    /**
     * 解析请求体消息
     */
    @SneakyThrows
    public <T> T parseXml(String content, Class<T> tClass) {
        return xmlMapper.readValue(content, tClass);
    }
}
