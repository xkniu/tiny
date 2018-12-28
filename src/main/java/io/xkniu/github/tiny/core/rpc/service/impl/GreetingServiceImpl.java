package io.xkniu.github.tiny.rpc.service.impl;

import io.xkniu.github.tiny.rpc.service.GreetingService;

import java.util.Date;

/**
 * 问候服务实现
 */
public class GreetingServiceImpl implements GreetingService {

    /**
     * 问候语
     * @param word 问候关键词
     * @return 问候
     */
    @Override
    public String greeting(String word) {
        return String.format("Hi, %s. It's %s now", word, new Date());
    }
}
