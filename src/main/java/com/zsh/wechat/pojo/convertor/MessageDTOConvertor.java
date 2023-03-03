package com.zsh.wechat.pojo.convertor;

import com.zsh.wechat.pojo.req.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct
 */
@Mapper
public interface MessageDTOConvertor {

    MessageDTOConvertor INSTANCE = Mappers.getMapper(MessageDTOConvertor.class);

    EventMessageDTO toEventMessageDTO(AllMessageDTO allMessageDTO);

    LinkMessageDTO toLinkMessageDTO(AllMessageDTO allMessageDTO);

    PicMessageDTO toPicMessageDTO(AllMessageDTO allMessageDTO);

    LocationMessageDTO toLocationMessageDTO(AllMessageDTO allMessageDTO);

    TextMessageDTO toTextMessageDTO(AllMessageDTO allMessageDTO);

    VideoMessageDTO toVideoMessageDTO(AllMessageDTO allMessageDTO);

    VoiceMessageDTO toVoiceMessageDTO(AllMessageDTO allMessageDTO);
}
