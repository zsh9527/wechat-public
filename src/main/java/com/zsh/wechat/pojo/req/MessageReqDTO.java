package com.zsh.wechat.pojo.req;

import com.zsh.wechat.enums.MsgTypeEnum;
import com.zsh.wechat.pojo.resp.ReplyDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * message请求数据
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageReqDTO {

    /**
     * 加密数据时为空
     */
    @Nullable
    private String ToUserName;

    /**
     * 发送方帐号（一个OpenID）
     */
    @NonNull
    private String FromUserName;

    @NotNull
    private Long CreateTime;

    @NotNull
    private MsgTypeEnum MsgType;

    @NotNull
    private Long MsgId;

    /**
     * 消息的数据ID（消息如果来自文章时才有）
     */
    @Nullable
    private String MsgDataId;

    /**
     * 多图文时第几篇文章，从1开始（消息如果来自文章时才有）
     */
    @Nullable
    private Integer Idx;

    /**
     * 生成回复消息体
     * from - to
     * to - from
     * createTime - createTime
     */
    public ReplyDTO buildReplyDTO() {
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setToUserName(this.getFromUserName());
        replyDTO.setFromUserName(this.getToUserName());
        replyDTO.setCreateTime(this.getCreateTime());
        return replyDTO;
    }
}
