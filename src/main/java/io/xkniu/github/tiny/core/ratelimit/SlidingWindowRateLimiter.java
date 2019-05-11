package io.xkniu.github.tiny.core.ratelimit;

/**
 * 滑动窗口限流器. 通过上个窗口的请求数和当前窗口的请求数加权计算出当前的请求数.
 */
public class SlidingWindowRateLimiter implements RateLimiter {

    private final long windowSizeInMills;   // 窗口大小（毫秒）
    private final int windowMaxRequest;     // 窗口最大请求数

    private long windowStartTime;           // 窗口开始时间
    private int windowRequest;              // 窗口请求数量
    private int preWindowRequest;           // 上个窗口请求量

    public SlidingWindowRateLimiter(long windowSizeInMills, int windowMaxRequest) {
        this.windowSizeInMills = windowSizeInMills;
        this.windowMaxRequest = windowMaxRequest;
        this.windowStartTime = System.currentTimeMillis();
    }

    @Override
    public synchronized boolean tryAcquire(int permits) {
        if (permits > windowMaxRequest) {
            return false;
        }

        long now = System.currentTimeMillis();
        rollWindow(now);
        double elapsedRatio = (double) (now - windowStartTime) / windowSizeInMills;
        double effectiveCount = windowRequest + preWindowRequest * (1 - elapsedRatio);

        if (effectiveCount + permits <= windowMaxRequest) {
            windowRequest += permits;
            return true;
        }
        return false;
    }

    private void rollWindow(long now) {
        long elapsed = now - windowStartTime;
        if (elapsed >= windowSizeInMills) {
            if (elapsed >= 2 * windowSizeInMills) {
                preWindowRequest = 0;
            } else {
                preWindowRequest = windowRequest;
            }

            windowRequest = 0;
            windowStartTime = now - elapsed % windowSizeInMills;
        }
    }
}
