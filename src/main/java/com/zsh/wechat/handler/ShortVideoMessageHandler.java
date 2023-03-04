package com.zsh.wechat.handler;

import com.zsh.wechat.enums.MsgTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ShortVideoMessageHandler extends VideoMessageHandler {

    @Override
    public MsgTypeEnum supportType() {
        return MsgTypeEnum.SHORTVIDEO;
    }

}
