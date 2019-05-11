package io.xkniu.github.tiny.core.ratelimit;

/**
 * 固定窗口限流器. 在固定时间窗口内限制最大请求数，窗口按时间对齐滚动.
 */
public class FixedWindowRateLimiter implements RateLimiter {

    private final long windowSizeInMills;   // 窗口大小（毫秒）
    private final int windowMaxRequest;     // 窗口最大请求数

    private long windowStartTime;           // 当前窗口开始时间
    private int windowRequest;              // 当前窗口已请求数

    public FixedWindowRateLimiter(long windowSizeInMills, int windowMaxRequest) {
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

        if (windowRequest + permits <= windowMaxRequest) {
            windowRequest += permits;
            return true;
        }
        return false;
    }

    private void rollWindow(long now) {
        long elapsed = now - windowStartTime;
        if (elapsed >= windowSizeInMills) {
            // 进入新窗口：将窗口起点对齐到当前所在窗口的开始时间
            windowStartTime = now - elapsed % windowSizeInMills;
            windowRequest = 0;
        }
    }
}
