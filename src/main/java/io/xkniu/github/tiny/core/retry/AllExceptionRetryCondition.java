package io.xkniu.github.tiny.core.retry;

public class AllExceptionRetryCondition implements RetryCondition {
    @Override
    public boolean shouldRetry(Exception e) {
        return e != null;
    }
}
