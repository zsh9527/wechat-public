package com.zsh.wechat.handler;

import com.zsh.wechat.enums.MsgTypeEnum;
import com.zsh.wechat.pojo.req.AllMessageDTO;
import com.zsh.wechat.pojo.req.MessageReqDTO;
import com.zsh.wechat.pojo.resp.ReplyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 消息处理器
 */
@Component
public class MessageHandlerActor {

    private Map<MsgTypeEnum, MessageHandler> handlerMap;

    // 兼容不支持的消息类型， 返回SUCCESS表示不回复
    private final static MessageHandler defaultMessageHandler = new MessageHandler() {

        @Override
        public MsgTypeEnum supportType() {
            return null;
        }

        @Override
        public ReplyDTO handlerAction(AllMessageDTO req) {
            return null;
        }
    };

    /**
     * 消息处理器注入
     */
    @Autowired(required = false)
    public void setHandlers(Collection<MessageHandler> handlers) {
        this.handlerMap = handlers.stream()
            .collect(Collectors.toMap(MessageHandler::supportType, obj-> obj));
    }

    /**
     * 处理消息
     */
    public ReplyDTO handle(AllMessageDTO req) {
        MessageHandler messageHandler = handlerMap.getOrDefault(req.getMsgType(), defaultMessageHandler);
        return messageHandler.handlerAction(req);
    }
}
