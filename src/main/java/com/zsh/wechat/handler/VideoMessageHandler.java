package com.zsh.wechat.handler;

import com.zsh.wechat.enums.MsgTypeEnum;
import com.zsh.wechat.pojo.convertor.MessageDTOConvertor;
import com.zsh.wechat.pojo.req.AllMessageDTO;
import com.zsh.wechat.pojo.req.VideoMessageDTO;
import com.zsh.wechat.pojo.req.VoiceMessageDTO;
import com.zsh.wechat.pojo.resp.MediaReply;
import com.zsh.wechat.pojo.resp.MediaWithContentReply;
import com.zsh.wechat.pojo.resp.ReplyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class VideoMessageHandler implements MessageHandler {

    @Override
    public MsgTypeEnum supportType() {
        return MsgTypeEnum.VIDEO;
    }

    /**
     * 返回相同消息内容
     */
    @Override
    public ReplyDTO handlerAction(AllMessageDTO req) {
        VideoMessageDTO messageDTO = MessageDTOConvertor.INSTANCE.toVideoMessageDTO(req);
        ReplyDTO replyDTO = messageDTO.buildReplyDTO();
        replyDTO.setMsgType(MsgTypeEnum.VIDEO);
        MediaWithContentReply reply = new MediaWithContentReply("title", "description");
        reply.setMediaId(messageDTO.getMediaId());
        replyDTO.setVideo(reply);
        return replyDTO;
    }
}
