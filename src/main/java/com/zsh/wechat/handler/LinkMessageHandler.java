package com.zsh.wechat.handler;

import com.zsh.wechat.enums.MsgTypeEnum;
import com.zsh.wechat.pojo.convertor.MessageDTOConvertor;
import com.zsh.wechat.pojo.req.AllMessageDTO;
import com.zsh.wechat.pojo.req.LinkMessageDTO;
import com.zsh.wechat.pojo.req.VideoMessageDTO;
import com.zsh.wechat.pojo.resp.MediaWithContentReply;
import com.zsh.wechat.pojo.resp.ReplyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LinkMessageHandler implements MessageHandler {

    @Override
    public MsgTypeEnum supportType() {
        return MsgTypeEnum.LINK;
    }

    /**
     * 返回相同消息内容
     */
    @Override
    public ReplyDTO handlerAction(AllMessageDTO req) {
        LinkMessageDTO messageDTO = MessageDTOConvertor.INSTANCE.toLinkMessageDTO(req);
        ReplyDTO replyDTO = messageDTO.buildReplyDTO();
        replyDTO.setMsgType(MsgTypeEnum.TEXT);
        replyDTO.setContent(messageDTO.getUrl() + "\n" + messageDTO.getTitle() + "\n" + messageDTO.getDescription());
        return replyDTO;
    }
}
