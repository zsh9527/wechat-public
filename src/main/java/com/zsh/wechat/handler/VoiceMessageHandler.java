package com.zsh.wechat.handler;

import com.zsh.wechat.enums.MsgTypeEnum;
import com.zsh.wechat.pojo.convertor.MessageDTOConvertor;
import com.zsh.wechat.pojo.req.AllMessageDTO;
import com.zsh.wechat.pojo.req.PicMessageDTO;
import com.zsh.wechat.pojo.req.VoiceMessageDTO;
import com.zsh.wechat.pojo.resp.MediaReply;
import com.zsh.wechat.pojo.resp.ReplyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class VoiceMessageHandler implements MessageHandler {

    @Override
    public MsgTypeEnum supportType() {
        return MsgTypeEnum.VOICE;
    }

    /**
     * 返回相同消息内容
     */
    @Override
    public ReplyDTO handlerAction(AllMessageDTO req) {
        VoiceMessageDTO messageDTO = MessageDTOConvertor.INSTANCE.toVoiceMessageDTO(req);
        ReplyDTO replyDTO = messageDTO.buildReplyDTO();
        replyDTO.setMsgType(MsgTypeEnum.VOICE);
        String recoResult = messageDTO.getRecognition() == null ? "识别失败" : messageDTO.getRecognition();
        log.info("语音识别结果:" + recoResult);
        replyDTO.setVoice(new MediaReply(messageDTO.getMediaId()));
        return replyDTO;
    }
}
