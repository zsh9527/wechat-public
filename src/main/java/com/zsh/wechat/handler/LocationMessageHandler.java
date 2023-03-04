package com.zsh.wechat.handler;

import com.zsh.wechat.enums.MsgTypeEnum;
import com.zsh.wechat.pojo.convertor.MessageDTOConvertor;
import com.zsh.wechat.pojo.req.AllMessageDTO;
import com.zsh.wechat.pojo.req.EventMessageDTO;
import com.zsh.wechat.pojo.req.LocationMessageDTO;
import com.zsh.wechat.pojo.resp.ReplyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LocationMessageHandler implements MessageHandler {

    @Override
    public MsgTypeEnum supportType() {
        return MsgTypeEnum.LOCATION;
    }

    /**
     * 返回相同消息内容
     */
    @Override
    public ReplyDTO handlerAction(AllMessageDTO req) {
        LocationMessageDTO messageDTO = MessageDTOConvertor.INSTANCE.toLocationMessageDTO(req);
        log.info("定位消息" + messageDTO.getLocation_X());
        ReplyDTO replyDTO = messageDTO.buildReplyDTO();
        replyDTO.setMsgType(MsgTypeEnum.TEXT);
        replyDTO.setContent(messageDTO.getLocation_X() + "\n" + messageDTO.getLocation_Y());
        return replyDTO;
    }
}
