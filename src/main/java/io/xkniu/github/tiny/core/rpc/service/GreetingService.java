package io.xkniu.github.tiny.rpc.service;

/**
 * 问候服务
 */
public interface GreetingService {

    /**
     * 得到一句问候
     * @param word 问候关键词
     * @return 问候语
     */
    String greeting(String word);
}
