package com.zsh.wechat.config;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;

/**
 * 自定义创建native-image的动态配置， 需要@ImportRuntimeHints将该类引入
 *
 * @author zsh
 * @version 1.0.0
 * @date 2023/05/24 16:19
 */
public class MyRuntimeHints implements RuntimeHintsRegistrar {

    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
    }
}
