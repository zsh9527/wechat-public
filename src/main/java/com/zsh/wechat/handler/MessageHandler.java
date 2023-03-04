package com.zsh.wechat.handler;

import com.zsh.wechat.enums.MsgTypeEnum;
import com.zsh.wechat.pojo.req.AllMessageDTO;
import com.zsh.wechat.pojo.resp.ReplyDTO;

/**
 * 消息处理器
 */
public interface MessageHandler {

    // 获取支持的消息类型
    MsgTypeEnum supportType();

    // 消息处理action
    ReplyDTO handlerAction(AllMessageDTO req);
}
