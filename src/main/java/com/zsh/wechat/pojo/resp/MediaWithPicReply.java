package com.zsh.wechat.pojo.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * MediaReply
 *
 * @author zsh
 * @version 1.0.0
 * @date 2023/03/03 18:27
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MediaWithPicReply extends MediaWithContentReply {

    private String articleCount;

    private String articles;

    private String picUrl;

    private String url;
}
