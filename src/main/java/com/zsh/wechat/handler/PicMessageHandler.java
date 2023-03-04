package com.zsh.wechat.handler;

import com.zsh.wechat.enums.MsgTypeEnum;
import com.zsh.wechat.pojo.convertor.MessageDTOConvertor;
import com.zsh.wechat.pojo.req.AllMessageDTO;
import com.zsh.wechat.pojo.req.PicMessageDTO;
import com.zsh.wechat.pojo.resp.MediaReply;
import com.zsh.wechat.pojo.resp.ReplyDTO;
import org.springframework.stereotype.Component;

@Component
public class PicMessageHandler implements MessageHandler {

    @Override
    public MsgTypeEnum supportType() {
        return MsgTypeEnum.PicUrl;
    }

    /**
     * 返回相同消息内容
     */
    @Override
    public ReplyDTO handlerAction(AllMessageDTO req) {
        PicMessageDTO messageDTO = MessageDTOConvertor.INSTANCE.toPicMessageDTO(req);
        ReplyDTO replyDTO = messageDTO.buildReplyDTO();
        replyDTO.setMsgType(MsgTypeEnum.PicUrl);
        replyDTO.setImage(new MediaReply(messageDTO.getMediaId()));
        return replyDTO;
    }
}
