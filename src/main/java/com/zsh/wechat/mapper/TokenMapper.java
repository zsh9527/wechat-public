package com.zsh.wechat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsh.wechat.pojo.TokenPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * token mapper

 */
@Mapper
public interface TokenMapper extends BaseMapper<TokenPO> {

    /**
     * 新增或更新token --h2不支持语法
     *
     * @return 更新数据行
     */
    Integer insertOrUpdate(@Param("tokenPO") TokenPO tokenPO);

    @Update("update `token` set access_token = #{tokenPO.accessToken}, expires_in = #{tokenPO.expiresIn} where id = #{tokenPO.id}")
    int update(@Param("tokenPO") TokenPO tokenPO);
}