package io.xkniu.github.tiny.core.ratelimit;

/**
 * 令牌桶限流器. 按固定间隔补充令牌，请求消耗令牌，桶满则不再累积.
 */
public class TokenBucketRateLimiter implements RateLimiter {

    private final int capacity;               // 令牌桶容量
    private final long refillIntervalInMills; // 补充令牌的时间间隔（毫秒）
    private final int refillTokensCount;      // 每次补充的令牌数

    private int tokens;                       // 当前令牌数
    private long lastRefillTimestamp;         // 上次补充令牌的时间

    public TokenBucketRateLimiter(int capacity, long refillIntervalInMills, int refillTokensCount) {
        this.capacity = capacity;
        this.refillIntervalInMills = refillIntervalInMills;
        this.refillTokensCount = refillTokensCount;
        this.lastRefillTimestamp = System.currentTimeMillis();
        this.tokens = capacity;
    }

    @Override
    public synchronized boolean tryAcquire(int permits) {
        if (permits > capacity) {
            return false;
        }
        long now = System.currentTimeMillis();
        refill(now);
        if (this.tokens >= permits) {
            this.tokens -= permits;
            return true;
        }
        return false;
    }

    private void refill(long now) {
        long elapsed = now - lastRefillTimestamp;
        if (elapsed >= refillIntervalInMills) {
            long tokensToAdd = elapsed / refillIntervalInMills * refillTokensCount;
            long newTokens = (long) tokens + tokensToAdd;
            tokens = (int) Math.min(capacity, newTokens);
            lastRefillTimestamp = now;
        }
    }
}
