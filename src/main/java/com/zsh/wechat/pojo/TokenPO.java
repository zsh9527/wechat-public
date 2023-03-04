package com.zsh.wechat.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * token
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("token")
public class TokenPO {

    /**
     * id -- 固定为1
     */
    @TableId
    private Integer id = 1;

    /**
     * token
     */
    private String accessToken;

    /**
     * 过期时间
     */
    private Integer expiresIn;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
