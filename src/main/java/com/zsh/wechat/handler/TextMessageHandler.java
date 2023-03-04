package com.zsh.wechat.handler;

import com.zsh.wechat.enums.MsgTypeEnum;
import com.zsh.wechat.pojo.convertor.MessageDTOConvertor;
import com.zsh.wechat.pojo.req.AllMessageDTO;
import com.zsh.wechat.pojo.req.TextMessageDTO;
import com.zsh.wechat.pojo.resp.ReplyDTO;
import org.springframework.stereotype.Component;

@Component
public class TextMessageHandler implements MessageHandler {

    @Override
    public MsgTypeEnum supportType() {
        return MsgTypeEnum.TEXT;
    }

    /**
     * 返回相同消息内容
     */
    @Override
    public ReplyDTO handlerAction(AllMessageDTO req) {
        TextMessageDTO messageDTO = MessageDTOConvertor.INSTANCE.toTextMessageDTO(req);
        ReplyDTO replyDTO = messageDTO.buildReplyDTO();
        replyDTO.setMsgType(MsgTypeEnum.TEXT);
        replyDTO.setContent(messageDTO.getContent());
        return replyDTO;
    }
}
