package com.zsh.wechat.handler;

import com.zsh.wechat.enums.MsgTypeEnum;
import com.zsh.wechat.pojo.convertor.MessageDTOConvertor;
import com.zsh.wechat.pojo.req.AllMessageDTO;
import com.zsh.wechat.pojo.req.EventMessageDTO;
import com.zsh.wechat.pojo.req.LinkMessageDTO;
import com.zsh.wechat.pojo.resp.MediaWithPicReply;
import com.zsh.wechat.pojo.resp.ReplyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class EventMessageHandler implements MessageHandler {

    @Override
    public MsgTypeEnum supportType() {
        return MsgTypeEnum.EVENT;
    }

    /**
     * 回复图文消息
     */
    @Override
    public ReplyDTO handlerAction(AllMessageDTO req) {
        EventMessageDTO messageDTO = MessageDTOConvertor.INSTANCE.toEventMessageDTO(req);
        log.info("事件消息" + messageDTO.getEvent());
        ReplyDTO replyDTO = messageDTO.buildReplyDTO();
        replyDTO.setMsgType(MsgTypeEnum.NEWS);
        replyDTO.setArticleCount(1);
        MediaWithPicReply picReply = new MediaWithPicReply("pic_url", "url");
        picReply.setTitle("title");
        picReply.setDescription("description");
        replyDTO.setArticles(List.of(picReply));
        return replyDTO;
    }
}
