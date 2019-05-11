package io.xkniu.github.tiny.core.retry;

public class FixedDelayPolicy implements RetryPolicy {

    private final long fixedDelayMills;

    private final int maxAttempts;

    public FixedDelayPolicy(long fixedDelayMills, int maxAttempts) {
        this.fixedDelayMills = fixedDelayMills;
        this.maxAttempts = maxAttempts;
    }

    @Override
    public int getMaxAttempts() {
        return maxAttempts;
    }

    @Override
    public long getDelayInMills(int attempt) {
        return fixedDelayMills;
    }
}
