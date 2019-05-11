package io.xkniu.github.tiny.core.ratelimit;

/**
 * 限流器接口. 尝试在限流约束内获取许可.
 */
public interface RateLimiter {

    /**
     * 尝试获取指定数量的许可。
     *
     * @param permits 本次申请的许可数（通常为 1）
     * @return 若在限流约束内则返回 true，否则返回 false
     */
    boolean tryAcquire(int permits);
}
